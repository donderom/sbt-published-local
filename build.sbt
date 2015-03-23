version := "0.2.0"

sbtPlugin := true

organization := "org.donderom"

name := "sbt-published-local"

scalacOptions := Seq("-deprecation", "-unchecked", "-feature")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
