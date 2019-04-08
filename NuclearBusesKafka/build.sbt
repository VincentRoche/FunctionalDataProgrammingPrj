name := "NuclearBusesKafka"

version := "0.1"

scalaVersion := "2.12.8"
val circeVersion = "0.7.0"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "2.1.0"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.12"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)