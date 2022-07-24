package com.kazino

import zio.Console.*
import zio.*

import java.io.IOException
import scala.annotation.targetName

object Main extends ZIOAppDefault {

  def run: ZIO[Any, IOException, Unit] = myAppLogic

  val myAppLogic: ZIO[Any, IOException, Unit] =
    for {
      _ <- printLine("Hello world!")
    } yield ()
}
