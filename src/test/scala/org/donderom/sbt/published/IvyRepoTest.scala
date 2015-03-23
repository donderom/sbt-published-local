package org.donderom.sbt.published

import java.io.File
import java.nio.file.Files
import org.scalatest._
import sbt._

class IvyRepoTest extends FlatSpec with Matchers {
  val log = ConsoleLogger()
  val name = "sbt-published-local-test"
  val org = "org.donderom"
  val fakeBase = new File(".")

  "IvyRepo" should "return nothing if there is no published artifacts" in {
    val ivyPaths = new IvyPaths(fakeBase, Some(fakeBase))
    val ivyRepo = new IvyRepo(name, org, ivyPaths, false, log)
    ivyRepo.artifacts should be (Nil)
  }

  it should "return all the versions of a library for any particular scala version" in {
    val scalaVersion = "2.11"
    val versions = List("1.1.0", "1.1.1")
    val artifacts = libraryArtifacts(scalaVersion, versions)
    artifacts.size should be (versions.size)
    artifacts should be (versions.map(IvyArtifact(scalaVersion, _)))
  }

  it should "return all the versions of a library for different scala versions" in {
    val scalaVersions = List("2.10", "2.11")
    val versions = List("2.0.0-SNAPSHOT", "3.0")
    val expectedArtifacts = for {
      scalaVersion <- scalaVersions
      version <- versions
    } yield IvyArtifact(scalaVersion, version)
    val artifacts = scalaVersions.flatMap(libraryArtifacts(_, versions))
    artifacts.size should be (scalaVersions.size + versions.size)
    artifacts should be (expectedArtifacts)
  }

  it should "return all the versions of a plugin for any particular scala/sbt version" in {
    val scalaVersion = "2.9"
    val sbtVersion = "0.13"
    val versions = List("0.0.1", "0.1.0")
    val artifacts = pluginArtifacts(scalaVersion, sbtVersion, versions)
    artifacts.size should be (2)
    artifacts should be (versions.map(IvyArtifact(scalaVersion, _, Some(sbtVersion))))
  }

  it should "return all the versions of a plugin for different scala/sbt versions" in {
    val scalaVersions = List("2.9", "2.10")
    val sbtVersions = List("0.12", "0.13")
    val version = "3.1.123"
    val expectedArtifacts = for {
      scalaVersion <- scalaVersions
      sbtVersion <- sbtVersions
    } yield IvyArtifact(scalaVersion, version, Some(sbtVersion))
    val artifacts = (for {
      scalaVersion <- scalaVersions
      sbtVersion <- sbtVersions
    } yield pluginArtifacts(scalaVersion, sbtVersion, List(version))).flatten
    artifacts.size should be (scalaVersions.size + sbtVersions.size)
    artifacts should be (expectedArtifacts)
  }

  private def libraryArtifacts(scalaVersion: String, versions: List[String]): List[IvyArtifact] =
    artifacts(versions, s"${name}_$scalaVersion", false)

  private def pluginArtifacts(scalaVersion: String, sbtVersion: String, versions: List[String]): List[IvyArtifact] = {
    val path = s"$name${File.separator}scala_$scalaVersion${File.separator}sbt_$sbtVersion"
    artifacts(versions, path, true)
  }

  private def artifacts(versions: List[String], path: String, plugin: Boolean): List[IvyArtifact] = {
    val tempIvyHome = Files.createTempDirectory("ivy2").toFile
    tempIvyHome.deleteOnExit()
    versions foreach { version => (tempIvyHome / "local" / org / path / version).mkdirs() }
    val ivyPaths = new IvyPaths(fakeBase, Some(tempIvyHome))
    new IvyRepo(name, org, ivyPaths, plugin, log).artifacts
  }
}
