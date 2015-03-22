package org.donderom.sbt

import java.io.File
import scala.language.implicitConversions

package object published {
  implicit def file2saferFile(file: File): SaferFile = new SaferFile(file)
}
