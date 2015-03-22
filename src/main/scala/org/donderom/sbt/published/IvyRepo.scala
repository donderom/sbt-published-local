package org.donderom.sbt.published

import java.io.File
import scala.util.matching.Regex
import sbt._
import sbt.std.TaskStreams

object IvyRepo {
  case class IvyArtifact(scalaVersion: String, version: String) {
    override def toString: String = s"$scalaVersion: $version"
  }

  def publishedLocal(name: String, org: String, ivyPaths: IvyPaths, streams: TaskStreams[_]): Unit = {
    implicit val log = streams.log
    val scalaVersionRegex = (name + "_(\\d\\.\\d{1,2}+\\z)").r
    for {
      scalaVersionDir <- projectHome(ivyPaths, org).listFiles
      versionDir <- scalaVersionDir.listFiles
    } print(IvyArtifact(scalaVersion(scalaVersionRegex, scalaVersionDir.getName), versionDir.getName))
  }

  def print(artifact: IvyArtifact)(implicit log: Logger): Unit =
    log.info(artifact.toString)

  def scalaVersion(regex: Regex, dirName: String): String =
    regexGroup(regex, dirName) getOrElse "?"

  def regexGroup(regex: Regex, text: String): Option[String] =
    for (regex(group) <- regex findFirstIn text) yield group

  def projectHome(ivyPaths: IvyPaths, org: String): File = {
    val ivyHome = ivyPaths.ivyHome getOrElse (Path.userHome / ".ivy2")
    ivyHome / "local" / org
  }
}
