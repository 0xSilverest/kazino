package com.kazino.engines.blackjack

import scala.math.BigDecimal
import com.kazino.common.*
import monocle.macros.GenLens 

case class Player (
  user: String,
  balance: BigDecimal,
  bet: BigDecimal = 0.0,
  hand: Hand = Hand.empty,
  player: PlayerState = PlayerState.NotPlaying
) :

  lazy val isBust = hand.isBust

  lazy val isNotBust = !isBust

  lazy val isBlackjack = hand.isBlackjack

object Player:
  val user = GenLens[Player](_.user)
  val balance = GenLens[Player](_.balance)
  val bet = GenLens[Player](_.bet)
  val hand = GenLens[Player](_.hand)
  val player = GenLens[Player](_.player)