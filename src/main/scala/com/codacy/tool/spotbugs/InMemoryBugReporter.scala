package com.codacy.tool.spotbugs

import edu.umd.cs.findbugs._
import edu.umd.cs.findbugs.classfile.ClassDescriptor

class InMemoryBugReporter(project: Project, projectStats: ProjectStats) extends AbstractBugReporter {

  private val bugCollection = new SortedBugCollection(projectStats, project)

  override def doReportBug(bugInstance: BugInstance): Unit = {
    bugCollection.add(bugInstance)
  }

  override def reportAnalysisError(error: AnalysisError): Unit = ()

  override def reportMissingClass(string: String): Unit = ()

  override def finish(): Unit = ()

  override def getBugCollection: BugCollection = bugCollection

  override def observeClass(classDescriptor: ClassDescriptor): Unit = ()

}
