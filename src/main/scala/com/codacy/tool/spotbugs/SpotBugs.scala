package com.codacy.tool.spotbugs

import java.io._
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.{Files, _}

import com.codacy.plugins.api.results.{Pattern, Result, Tool}
import com.codacy.plugins.api.{Options, Source, _}
import com.codacy.tools.scala.seed.utils.FileHelper
import com.codacy.tools.scala.seed.utils.ToolHelper._
import edu.umd.cs.findbugs
import edu.umd.cs.findbugs._

import scala.annotation.tailrec
import scala.collection.JavaConverters._
import scala.compat.java8.OptionConverters._
import scala.util.{Failure, Success, Try}

object SpotBugs extends Tool {

  private val appLocation: String = "/opt/docker"
  private val includesFilenames = Set("findbugs.xml", "findbugs-includes.xml")
  private val excludesFilenames = Set("findbugs-excludes.xml")

  override def apply(
      source: Source.Directory,
      configuration: Option[List[Pattern.Definition]],
      files: Option[Set[Source.File]],
      options: Map[Options.Key, Options.Value]
  )(implicit specification: Tool.Specification): Try[List[Result]] = Try {
    val sourcePath = Paths.get(source.path)
    val filesPath = files.map(_.map(s => Paths.get(s.path)))

    val (includesFile, excludesFile) = configuration.withDefaultParameters
      .map(generateIncludeXMLFile)
      .map { rules =>
        (Option(rules.toAbsolutePath), Option.empty[Path])
      }
      .getOrElse {
        (
          FileHelper.findConfigurationFile(sourcePath, includesFilenames),
          FileHelper.findConfigurationFile(sourcePath, excludesFilenames)
        )
      }

    val modulesConfiguration = options
      .get(Options.Key("modules"))
      .map(optionsValueToJsValue)
      .map(_.as[Set[ModuleConfiguration]])
      .getOrElse(discoverProjectModules(sourcePath))

    loadPlugins()

    val results: List[Try[List[Result]]] = modulesConfiguration.map { moduleConfiguration =>
      runTool(sourcePath, filesPath, moduleConfiguration, includesFile, excludesFile)
    }(collection.breakOut)

    /* Skip modules if classes where not found but fail if other error occurs
     * e.g.: Some modules can contain only resources and not have any actual compiled code to analyse
     */
    results.flatMap {
      case Success(res) => res
      case Failure(e: IOException) if e.getMessage == "No files to analyze could be opened" => List.empty[Result]
      case Failure(e) =>
        throw e
    }
  }

  /*
   * Tries to discover the modules inside `root`.
   * Starts by searching patterns looking like the path where the build systems place the compiled classes,
   * and then for each location creates a module with the sources bases on this location.
   *
   *
   * # Examples of locations
   *
   * ## Sources
   *
   *  - src/main (Maven)
   *  - src/main/scala (SBT, Gradle)
   *  - src/main/java (SBT, Gradle)
   *  - js/src/main/scala (SBT) [not relevant since it is treated as a module of its own called js]
   *  - jvm/src/main/scala (SBT) [not relevant since it is treated as a module of its own called jvm]
   *  - shared/src/main/scala (SBT) [not relevant since it is treated as a module of its own called shared]
   *
   *  ## Compiled Classes
   *
   *  - target/classes (Maven)
   *  - target/scala-2.10/classes (SBT)
   *  - target/scala-2.11/classes (SBT)
   *  - target/scala-2.12/classes (SBT)
   *  - target/scala-2.13/classes (SBT)
   *  - build/classes/java/main (Gradle)
   */
  private def discoverProjectModules(root: Path): Set[ModuleConfiguration] = {
    // val gradleClassesMatcher = FileSystems.getDefault.getPathMatcher("glob:**build/classes/*/main")
    // val sbtClassesMatcher = FileSystems.getDefault.getPathMatcher("glob:**target/*/classes")
    // val mavenClassesMatcher = FileSystems.getDefault.getPathMatcher("glob:**target/classes")
    val classesMatcher =
      FileSystems.getDefault.getPathMatcher("glob:**{target,build}/{classes,*/classes,classes/*/main}")
    val sourcesMatcher = FileSystems.getDefault.getPathMatcher("glob:**src/{main,main/*}")

    val modules = Files
      .find(
        root,
        Int.MaxValue,
        (t: Path, _: BasicFileAttributes) => classesMatcher.matches(t),
        FileVisitOption.FOLLOW_LINKS
      )
      .iterator()
      .asScala
      .flatMap { classesDirectory =>
        val moduleRoot = findModuleRootFromClasses(classesDirectory)
        val sourcesRoot: Set[Path] = Files
          .find(
            moduleRoot,
            Int.MaxValue,
            (t: Path, _: BasicFileAttributes) => sourcesMatcher.matches(t),
            FileVisitOption.FOLLOW_LINKS
          )
          .iterator()
          .asScala
          .to[Set]

        // Remove modules where the sources directory could not be detected
        Option(sourcesRoot)
          .filter(_.nonEmpty)
          .map(srcs => ModuleConfiguration(Set(classesDirectory.toString), srcs.map(_.toString)))
      }
      .to[Set]

    if (modules.nonEmpty) {
      modules
    } else {
      /* Let's consider the root a big project
       * This is not a good behaviour:
       *  e.g.: If a two modules have files with same paths relative to their root,
       *        we don't have a way to match the issues correctly to the files.
       */
      Set(ModuleConfiguration(Set(root.toString), Set(root.toString)))
    }
  }

  @tailrec
  private def findModuleRootFromClasses(path: Path): Path = {
    if (// Maven and SBT root for compilation
      path.getFileName.toString == "target" ||
      // Gradle root for compilation
      path.getFileName.toString == "build") {
      path.getParent
    } else {
      findModuleRootFromClasses(path.getParent)
    }
  }

  private def runTool(
      root: Path,
      files: Option[Set[Path]],
      moduleConfiguration: ModuleConfiguration,
      includesFile: Option[Path],
      excludesFile: Option[Path]
  ): Try[List[Result]] = Try {
    val detector = DetectorFactoryCollection.instance()
    val projectStats = new ProjectStatsWithoutCustomProfiler()
    val project = new Project()
    val userPreferences = project.getConfiguration

    project.addWorkingDir(root.toString)
    resolve(moduleConfiguration.classesDirectories, root).map(cd => project.addFile(cd.toString))

    // TODO: check if we can we get the jars to pass here
    // project.addAuxClasspathEntry(???)

    includesFile.map(path => project.getConfiguration.getIncludeFilterFiles.put(path.toString, true))
    excludesFile.map(path => project.getConfiguration.getExcludeFilterFiles.put(path.toString, true))

    val bugReporter = {
      // TODO: check this workaround for Scala code
      // val inMemoryBugReporter = new InMemoryBugReporter(project, projectStats)
      // new CategoryFilteringBugReporter(inMemoryBugReporter, Set("SECURITY").asJava)
      new InMemoryBugReporter(project, projectStats)
    }
    bugReporter.setPriorityThreshold(Priorities.NORMAL_PRIORITY)

    val findBugs = new FindBugs2()
    findBugs.setProject(project)
    findBugs.setBugReporter(bugReporter)
    findBugs.setDetectorFactoryCollection(detector)
    findBugs.setUserPreferences(userPreferences)

    project.setPluginStatusTrinary("com.h3xstream.findsecbugs", true)
    project.setPluginStatusTrinary("com.mebigfatguy.fbcontrib", true)

    findBugs.execute()
    findBugs.close()

    val bugReports = bugReporter.getBugCollection.getCollection.asScala.to[Set]
    convertBugsToResults(root, bugReports, files, moduleConfiguration)
  }

  private def loadPlugins(): Unit = {
    Plugin.loadCustomPlugin(new File(s"$appLocation/lib/com.h3xstream.findsecbugs.findsecbugs-plugin-1.9.0.jar"), null)
    val findsecbugsPlugin = DetectorFactoryCollection.instance.getPluginById("com.h3xstream.findsecbugs")
    findsecbugsPlugin.setGloballyEnabled(true)

    Plugin.loadCustomPlugin(new File(s"$appLocation/lib/com.mebigfatguy.sb-contrib.sb-contrib-7.4.5.jar"), null)
    val fbContribPlugin = DetectorFactoryCollection.instance.getPluginById("com.mebigfatguy.fbcontrib")
    fbContribPlugin.setGloballyEnabled(true)
  }

  private def isFileEnabled(path: String, filesOpt: Option[Set[Path]]): Boolean = {
    filesOpt.fold(true) { files =>
      files.exists(_.toString == path)
    }
  }

  private def convertBugsToResults(
      root: Path,
      bugs: Set[findbugs.BugInstance],
      files: Option[Set[Path]],
      moduleConfiguration: ModuleConfiguration
  ): List[Result] = {
    (for {
      (shortPath, bugs) <- bugs.groupBy(_.getPrimarySourceLineAnnotation.getSourcePath)
      sourceDirectories = resolve(moduleConfiguration.sourceDirectories, root)
      filePath <- find(sourceDirectories, shortPath).to[List]
      if isFileEnabled(filePath.toString, files)
      bug <- bugs if bug.getPrimarySourceLineAnnotation.getStartLine > 0
    } yield {
      Result.Issue(
        Source.File(filePath.toString),
        Result.Message(bug.getMessageWithoutPrefix),
        Pattern.Id(bug.getType),
        Source.Line(bug.getPrimarySourceLineAnnotation.getStartLine)
      )
    })(collection.breakOut)
  }

  // Tries to resolve the strings in `paths` if they are absolute, or relative to `root`
  private def resolve(paths: Set[String], root: Path): Set[Path] = {
    paths
      .map(Paths.get(_))
      .flatMap {
        case path if Files.exists(path) => Option(path)

        case path if !path.isAbsolute =>
          val pathInRoot = root.resolve(path)
          if (Files.exists(pathInRoot)) {
            Option(pathInRoot)
          } else {
            Option.empty[Path]
          }

        case _ =>
          Option.empty[Path]
      }
  }

  /* Finds the first path inside any root in `roots` that ends with `suffix`
   * Searches each root in `roots` individually
   */
  @tailrec
  private def find(roots: Set[Path], suffix: String): Option[Path] = {
    roots.headOption match {
      case None => None

      case Some(root) =>
        val directPath = root.resolve(suffix)
        if (Files.exists(directPath)) {
          Some(directPath)
        } else {
          val matchingPath = Files
            .find(
              root,
              Int.MaxValue,
              (t: Path, _: BasicFileAttributes) => t.endsWith(suffix),
              FileVisitOption.FOLLOW_LINKS
            )
            .findFirst()
            .asScala

          matchingPath match {
            case Some(path) => Some(path)

            case None => find(roots.drop(1), suffix)
          }
        }
    }
  }

  private def generateIncludeXMLFile(conf: List[Pattern.Definition]): Path = {
    val xmlLiteral = <FindBugsFilter>
      {conf.map(pattern =>
        <Match>
          <Bug pattern={pattern.patternId.value}/>
        </Match>
      )}
    </FindBugsFilter>

    // TODO: change to spotbugs after the tool is renamed
    FileHelper.createTmpFile(xmlLiteral.toString, "findsecbugs", "")
  }

}
