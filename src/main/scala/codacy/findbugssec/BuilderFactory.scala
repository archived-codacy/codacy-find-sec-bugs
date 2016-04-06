package codacy.findbugssec

import java.nio.file.Path

import codacy.dockerApi.traits.{Builder, MavenBuilder, SBTBuilder}

object BuilderFactory {

  lazy val knownBuilders = Seq(
    MavenBuilder,
    SBTBuilder
  )

  def apply(path: Path): Option[Builder] = {
    val builders = knownBuilders.filter { case builder => builder.supported(path) }
    builders.headOption
  }

}
