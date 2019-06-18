package codacy

import codacy.findbugssec.FindBugsSec
import com.codacy.tools.scala.seed.DockerEngine

object Engine extends DockerEngine(FindBugsSec)()
