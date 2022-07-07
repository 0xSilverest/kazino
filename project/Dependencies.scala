import sbt._

object Dependencies {
  lazy val zioV ="2.0.0" 
  lazy val testV = "3.2.12"
  lazy val zioOpticsV = "0.2.0"

  lazy val zio = "dev.zio" %% "zio" % zioV
  lazy val zioOptics = "dev.zio" %% "zio-optics" % zioOpticsV

  lazy val zioTest = "dev.zio" %% "zio-test" % zioV
  lazy val scalaTest = "org.scalatest" %% "scalatest" % testV
}
