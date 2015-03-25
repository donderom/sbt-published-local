seq(lsSettings :_*)

(LsKeys.tags in LsKeys.lsync) := Seq("sbt", "published", "local")

(description in LsKeys.lsync) := "Show your project's versions published locally."
