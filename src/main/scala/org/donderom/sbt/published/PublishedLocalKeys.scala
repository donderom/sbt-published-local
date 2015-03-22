package org.donderom.sbt.published

import sbt.taskKey

trait PublishedLocalKeys {
  lazy val publishedLocal = taskKey[Unit]("Shows a list of project versions published locally.")
}

object PublishedLocalKeys extends PublishedLocalKeys
