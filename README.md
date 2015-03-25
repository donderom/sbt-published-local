# sbt-published-local

sbt plugin that shows all the versions of current project published locally.

## Requirements

sbt 0.13.5+

## Installation

To install it as a global plugin add the following line to `~/.sbt/0.13/plugins/plugins.sbt` (or any other file under `~/.sbt/0.13/plugins` folder):

	addSbtPlugin("org.donderom" % "sbt-published-local" % "0.2.0")

To make it project-specific just add the the same line to any *.sbt file in the `project` folder.

## Usage

To show all the versions of a project just run the following task:

	> publishedLocal

If there are any versions published locally you will see something like this:

	> [info] Scala 2.11: 1.3.2
	> [info] Scala 2.11: 1.3.3

If your current project is a sbt plugin it will add sbt version to the output:

	> [info] Scala 2.10, sbt 0.13: 1.1.0