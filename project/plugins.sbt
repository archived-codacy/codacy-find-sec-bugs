resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq("com.github.pathikrit" %% "better-files" % "3.6.0",
  "com.typesafe.play" %% "play-json" % "2.6.9")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.12")

addSbtPlugin("com.codacy" % "codacy-sbt-plugin" % "15.0.0")