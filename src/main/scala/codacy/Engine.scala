package codacy

import codacy.dockerApi.DockerEngine
import codacy.findbugssec.FindBugsSec

object Engine extends DockerEngine(FindBugsSec)
