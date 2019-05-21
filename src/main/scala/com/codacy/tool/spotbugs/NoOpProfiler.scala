package com.codacy.tool.spotbugs

import java.io.PrintStream
import java.util.Comparator

import edu.umd.cs.findbugs.log.Profiler
import edu.umd.cs.findbugs.xml.XMLOutput

class NoOpProfiler extends Profiler {

  override def startContext(context: Any): Unit = ()

  override def endContext(context: Any): Unit = ()

  override def start(c: Class[_]): Unit = ()

  override def end(c: Class[_]): Unit = ()

  override def report(): Unit = ()

  override def report(reportComparator: Comparator[Class[_]], filter: Profiler.Filter, stream: PrintStream): Unit = ()

  override def clear(): Unit = ()

  override def getProfile(c: Class[_]): Profiler.Profile = null

  override def writeXML(xmlOutput: XMLOutput): Unit = ()

}
