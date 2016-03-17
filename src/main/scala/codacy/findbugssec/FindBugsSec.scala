package codacy.findbugssec

import java.nio.file.Path
import java.io.File

import codacy.dockerApi._
import codacy.dockerApi.utils.{CommandRunner, FileHelper, ToolHelper}

import scala.util.{Failure, Success, Try}
import scala.xml.{XML, Node}


private class Occurence(val lineno: Integer, val path: String) {
  lazy val packageName = path.split(File.separatorChar).headOption.getOrElse("")
  lazy val components = path.split(File.separatorChar)
}

private class BugInstance(val name: String, val message: String, val occurence: Occurence)

private class SourceDirectory(val absolutePath: File) {

  lazy val absoluteStringPath = absolutePath.getAbsolutePath

  def subdirectoryExists(components: Seq[String]): Boolean = {
    val pathComponents = Seq(absoluteStringPath) ++ components
    new File(pathComponents.mkString(File.separator)).exists()
  }
}

object FindBugsSec extends Tool {

  override def apply(path: Path, conf: Option[List[PatternDef]], files: Option[Set[Path]])(implicit spec: Spec): Try[List[Result]] = {
    val someBuilder = BuilderFactory(path)
    someBuilder match {
      case Some(builder) =>
        val completeConf = ToolHelper.getPatternsToLint(conf)
        builder.build(path) match {
          case Success(value) => processTool(path, completeConf, files, builder)
          case Failure(throwable) => Failure(throwable)
        }
      case _ => Failure(new Exception("Could not support project compilation."))
    }
  }

  private def toolCommand(path: Path, conf: Option[List[PatternDef]], builder: Builder) = {
    val defaultCmd = List("java", "-jar", "findbugssec.jar",
      "-xml:withMessages", "-output", "/tmp/output.xml")
    val configuredPatterns = conf match {
      case Some(conf) if conf.nonEmpty =>
        val configurationFile = patternIncludeXML(conf)
        List("-include", configurationFile)
      case _ =>
        List()
    }
    val componentProjects = collectTargets(path, builder)
    val targets = componentProjects.map(builder.targetOfDirectory)
    (defaultCmd ++ configuredPatterns ++ targets, componentProjects)
  }

  private def processTool(path: Path,
                          conf: Option[List[PatternDef]],
                          files: Option[Set[Path]],
                          builder: Builder): Try[List[Result]] = {

    val (command, componentProjects) = toolCommand(path, conf, builder)
    CommandRunner.exec(command) match {
      case Left(failure) => Failure(failure)
      case Right(output) if output.exitCode != 0 =>
        Failure(new Exception("Can't execute tool."))

      case Right(_) =>
        Try {
          val bugs = parseOutputFile()
          resultsFromBugInstances(bugs, componentProjects, files, builder).toList
        }
    }
  }

  private def elementPathAndLine(elem: Node): Option[Seq[Occurence]] = {
    for {
      start      <- elem.attribute("start")
      sourcepath <- elem.attribute("sourcepath")
    } yield {
      (start zip sourcepath).map { case (startNode, sourcePathNode) =>
        new Occurence(startNode.text.toInt, sourcePathNode.text)
      }
    }
  }

  private def sourceFileName(directory: SourceDirectory, bug: BugInstance) = {
    Seq(directory.absoluteStringPath, bug.occurence.path).mkString(File.separator)
  }

  private def isFileEnabled(path: String, files: Option[Set[Path]]): Boolean = {
    files.fold(true) { case files => files.exists(_.toAbsolutePath.toFile.getAbsolutePath == path) }
  }

  private def resultsFromBugInstances(bugs: Seq[BugInstance],
                                      directories: Array[File],
                                      files: Option[Set[Path]],
                                      builder: Builder): Seq[Result] = {
    val sourceDirectories = directories.map { file =>
      val components = Seq(file.getAbsolutePath) ++ builder.pathComponents
      new SourceDirectory(new File(components.mkString(File.separator)))
    }

    bugs.flatMap { case bug =>
      val foundOccurences = sourceDirectories.filter(_.subdirectoryExists(bug.occurence.components))

      val results: Seq[Result] = foundOccurences.collect {
        case directory if foundOccurences.size == 1 && isFileEnabled(sourceFileName(directory, bug), files) =>
          val filename = sourceFileName(directory, bug)
          Issue(SourcePath(filename),
                ResultMessage(bug.message),
                PatternId(bug.name),
                ResultLine(bug.occurence.lineno))
        case directory if foundOccurences.size > 1 && isFileEnabled(sourceFileName(directory, bug), files) =>
          val filename = sourceFileName(directory, bug)
          FileError(SourcePath(filename),
                    Option(ErrorMessage("File duplicated in multiple directories.")))

      }
      results
    }
  }

  private def parseOutputFile(): Seq[BugInstance] = {
    val xmlOutput = XML.loadFile("/tmp/output.xml")
    val bugInstances = xmlOutput \ "BugInstance"
    bugInstances.flatMap { case bugInstance =>
      // If the there is a SourceLine under the BugInstance, then that is
      // the line that will reported. The only issue though is only the first
      // occurence is emitted, even though there can be many more, that's why we're
      // using .head down here.
      val sourceLineOccurences = bugInstance \ "SourceLine"
      val patternName = bugInstance \@ "type"
      val message = (bugInstance \ "LongMessage").head.text
      val occurences = (sourceLineOccurences.nonEmpty match {
        case true => elementPathAndLine(sourceLineOccurences.head)
        case false =>
          val methodSourceLine = bugInstance \ "Method"
          methodSourceLine.nonEmpty match {
            case true => elementPathAndLine(methodSourceLine.head)
            case false => Option.empty
          }
      }) getOrElse Seq()
      occurences.map(new BugInstance(patternName, message, _))
    }
  }

  private def patternIncludeXML(conf: List[PatternDef]): String = {
    val xmlLiteral = <FindBugsFilter>{
      conf.map( pattern =>
        <Match>
          <Bug pattern={pattern.patternId.value}/>
        </Match>
      )
    }
    </FindBugsFilter>.toString
    val tmp = FileHelper.createTmpFile(xmlLiteral, "findsecbugs", "")
    tmp.toAbsolutePath.toString
  }

  private def collectTargets(path: Path, builder: Builder): Array[File] = {
    // Get the directories that can be projects (including subprojects and the current directory).
    val directories = path.toFile.listFiles.filter(_.isDirectory) ++ Seq(path.toFile)
    directories.filter {
      case directory =>
        val target = new File(builder.targetOfDirectory(directory))
        target.exists
    }
  }
}
