package org.donderom.sbt.published

import java.io.File

class SaferFile(file: File) {
  def files: List[File] = {
    val descendants = file.listFiles
    if (descendants == null) Nil else descendants.toList
  }
}
