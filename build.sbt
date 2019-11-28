name := "PiScalaRestController"

version := "0.1.00"

scalaVersion := "2.13.1"

scalacOptions += "-deprecation"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.10"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.0"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "io.circe" %% "circe-generic" % "0.12.3"
libraryDependencies += "io.circe" %% "circe-core" % "0.12.3"
libraryDependencies += "io.circe" %% "circe-parser" % "0.12.3"
libraryDependencies += "de.heikoseeberger" %% "akka-http-circe" % "1.29.1"
libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.6.0"
