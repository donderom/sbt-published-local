publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/donderom/sbt-published-local</url>
  <licenses>
    <license>
      <name>BSD-style</name>
      <url>http://opensource.org/licenses/BSD-3-Clause</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:donderom/sbt-published-local.git</url>
    <connection>scm:git:git@github.com:donderom/sbt-published-local.git</connection>
  </scm>
  <developers>
    <developer>
      <id>donderom</id>
      <name>Roman Parykin</name>
      <url>http://donderom.org</url>
    </developer>
  </developers>)
