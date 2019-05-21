package com.codacy.tool.spotbugs

import play.api.libs.json.{Format, Json}

case class ModuleConfiguration(classesDirectories: Set[String], sourceDirectories: Set[String])

object ModuleConfiguration {
  implicit val moduleConfigurationFmt: Format[ModuleConfiguration] = Json.format[ModuleConfiguration]
}
