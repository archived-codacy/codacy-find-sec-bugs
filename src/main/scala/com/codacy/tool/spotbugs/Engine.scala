package com.codacy.tool.spotbugs

import com.codacy.tools.scala.seed.DockerEngine

object Engine extends DockerEngine(SpotBugs)()
