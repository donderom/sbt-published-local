package org.donderom.sbt.published

case class IvyArtifact(scalaVersion: String, version: String, sbtVersion: Option[String] = None) {
  override def toString: String = sbtVersion match {
    case Some(ver) => s"Scala $scalaVersion, sbt $ver: $version"
    case _ => s"Scala $scalaVersion: $version"
  }
}
