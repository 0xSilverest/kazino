package com.kazino.engines.blackjack

import scala.math.BigDecimal
import com.kazino.common.*

case class Player (
  user: String,
  balance: BigDecimal,
  bet: BigDecimal = 0.0,
  hand: Hand = Hand.empty,
):

  def isBust = hand.isBust
  def isNotBust = !isBust

  def isBlackjack = hand.isBlackjack

