package org.donderom.sbt.published

import org.donderom.sbt.published.PublishedLocalKeys._
import sbt.AutoPlugin

object PublishedLocal extends AutoPlugin with PublishedLocalTasks {
  object autoImport extends PublishedLocalKeys

  override val trigger = allRequirements

  override def projectSettings = Seq(
    publishedLocal <<= publishedLocalTask
  )
}
