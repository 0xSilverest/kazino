import Dependencies._

ThisBuild / scalaVersion     := "3.1.2"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.kazino"
ThisBuild / organizationName := "Kazino"

lazy val root = (project in file("."))
  .settings(
    name := "Kazino",
    libraryDependencies ++= Seq(
      zio, zioOptics,

      zioTest % Test,
      scalaTest % Test
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
enablePlugins(GraalVMNativeImagePlugin)
