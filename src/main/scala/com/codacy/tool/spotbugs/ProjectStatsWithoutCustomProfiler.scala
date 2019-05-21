package com.codacy.tool.spotbugs

import edu.umd.cs.findbugs.ProjectStats
import edu.umd.cs.findbugs.log.Profiler

class ProjectStatsWithoutCustomProfiler(private val profiler: Profiler = new NoOpProfiler()) extends ProjectStats {

  override def getProfiler: Profiler = profiler

}
