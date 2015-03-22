package org.donderom.sbt.published

import sbt.Keys._

trait PublishedLocalTasks {
  def publishedLocalTask = (name, organization, ivyPaths, sbtPlugin, streams).map(IvyRepo.publishedLocal)
}
