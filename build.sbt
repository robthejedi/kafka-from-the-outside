name := "kafkatest"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka_2.11" % "0.9.0.0",
  "com.typesafe" % "config" % "1.2.1",
  "com.typesafe.play" % "play-json_2.11" % "2.4.0-M2",
  "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test",
  "org.twitter4j" % "twitter4j-core" % "4.0.2",
  "org.twitter4j" % "twitter4j-stream" % "4.0.2"
)