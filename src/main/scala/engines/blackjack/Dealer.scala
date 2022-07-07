package com.kazino.engines.blackjack

import com.kazino.common.*

case class Dealer(
  hand: Hand = Hand.empty,
):
  lazy val isBust = hand.isBust

  lazy val isNotBust = !isBust

  lazy val isBlackjack = hand.isBlackjack

