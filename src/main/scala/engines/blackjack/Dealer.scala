package com.kazino.engines.blackjack

import com.kazino.common.*
import monocle.syntax.all.*

case class Dealer(
  hand: Hand = Hand.empty,
  deck: Deck = Deck()
) :

  lazy val isBust = hand.isBust

  lazy val isNotBust = !isBust

  lazy val isBlackjack = hand.isBlackjack

  def dealToSelf = 
    val (card, rest) = deck.deal(1)
    this.focus(_.deck).set(rest)
      .focus(_.hand).modify(_ ++ card)
