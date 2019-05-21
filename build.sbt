import com.typesafe.sbt.packager.docker.Cmd

name := "codacy-find-sec-bugs"

version := "1.0.0-SNAPSHOT"

scalaVersion := "2.12.8"

lazy val toolVersionKey = settingKey[String]("The version of the underlying tool retrieved from patterns.json")

toolVersionKey := {
  import better.files.File
  import play.api.libs.json.{JsString, JsValue, Json}

  val jsonFile = resourceDirectory.in(Compile).value / "docs" / "patterns.json"
  val patternsJsonValues = Json.parse(File(jsonFile.toPath).contentAsString).as[Map[String, JsValue]]

  patternsJsonValues
    .collectFirst { case ("version", JsString(ver)) => ver }
    .getOrElse(throw new Exception("Failed to retrieve version from docs/patterns.json"))
}

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
  "com.codacy" %% "codacy-engine-scala-seed" % "3.0.296",
  "com.github.spotbugs" % "spotbugs" % "3.1.12",
  "com.h3xstream.findsecbugs" % "findsecbugs-plugin" % toolVersionKey.value,
  "com.mebigfatguy.sb-contrib" % "sb-contrib" % "7.4.5",
  "org.scala-lang.modules" % "scala-java8-compat_2.12" % "0.9.0"
).map(_.withSources())

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(AshScriptPlugin)

version in Docker := "1.0.0-SNAPSHOT"

mappings.in(Universal) ++= resourceDirectory
  .in(Compile)
  .map { resourceDir: File =>
    val src = resourceDir / "docs"
    val dest = "/docs"

    (for {
      path <- better.files.File(src.toPath).listRecursively()
      if !path.isDirectory
    } yield path.toJava -> path.toString.replaceFirst(src.toString, dest)).toSeq
  }
  .value

val dockerUser = "docker"
val dockerUserId = "2004"

daemonUser in Docker := dockerUser
daemonUserUid in Docker := Option(dockerUserId)
daemonGroup in Docker := dockerUser
daemonGroupGid in Docker := Option(dockerUserId)

dockerBaseImage := "frolvlad/alpine-java:jre8-slim"

// TODO: Create Dockerfile
dockerCommands := dockerCommands.value.flatMap {
  case cmd @ Cmd("WORKDIR", "/opt/docker") =>
    List(cmd, Cmd("RUN", s"adduser", "-u", dockerUserId, "-D", dockerUser))
  case cmd @ Cmd("USER", "docker") =>
    List(Cmd("RUN", s"mv", "/opt/docker/docs", "/docs"), cmd)
  case other => List(other)
}
