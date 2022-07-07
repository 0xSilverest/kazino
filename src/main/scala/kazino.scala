package com.kazino

import zio.Console.*
import zio.*

object Main extends ZIOAppDefault {

  def run = myAppLogic

  val myAppLogic = 
    for {
      _ <- printLine("Hello world!")
    } yield ()
}
