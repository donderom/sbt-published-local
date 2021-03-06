package org.donderom.sbt.published

import java.io.File
import scala.util.matching.Regex
import sbt._
import sbt.std.TaskStreams

class IvyRepo(name: String, org: String, ivyPaths: IvyPaths, sbtPlugin: Boolean, log: Logger) {
  def publishedLocal { artifacts foreach print }

  def artifacts: List[IvyArtifact] = if (sbtPlugin) pluginArtifacts else libraryArtifacts

  private val projectHome: File = {
    val ivyHome = ivyPaths.ivyHome getOrElse (Path.userHome / ".ivy2")
    val basePath = ivyHome / "local" / org
    if (sbtPlugin) basePath / name else basePath
  }

  private val versionRegexSuffix = "_(\\d\\.\\d{1,2}+\\z)"

  private def versionRegex(prefix: String): Regex = (prefix + versionRegexSuffix).r

  private def print(artifact: IvyArtifact): Unit = log.info(artifact.toString)

  private def version(prefix: String, dirName: String): String =
    regexGroup(versionRegex(prefix), dirName) getOrElse "?"

  private def regexGroup(regex: Regex, text: String): Option[String] =
    for (regex(group) <- regex findFirstIn text) yield group

  private def libraryArtifacts: List[IvyArtifact] =
    for {
      scalaVerDir <- projectHome.files
      versionDir <- scalaVerDir.files
    } yield IvyArtifact(version(name, scalaVerDir.name), versionDir.name)

  private def pluginArtifacts: List[IvyArtifact] =
    for {
      scalaDir <- projectHome.files
      sbtDir <- scalaDir.files
      versionDir <- sbtDir.files
    } yield IvyArtifact(version("scala", scalaDir.name), versionDir.name, Some(version("sbt", sbtDir.name)))
}

object IvyRepo {
  def publishedLocal(name: String, org: String, ivyPaths: IvyPaths, sbtPlugin: Boolean, streams: TaskStreams[_]): Unit =
    new IvyRepo(name, org, ivyPaths, sbtPlugin, streams.log).publishedLocal
}
