package com.kazino.blackjack

import com.kazino.common.*
import monocle.syntax.all.*

case class Dealer(
  hand: Hand = Hand.empty,
  deck: Deck = Deck()
) :

  lazy val isBust: Boolean = hand.isBust

  lazy val isNotBust: Boolean = !isBust

  lazy val isBlackjack: Boolean = hand.isBlackjack

  def dealToSelf: Dealer =
    val (card, rest) = deck.deal(1)
    this.focus(_.deck).set(rest)
      .focus(_.hand).modify(_ ++ card)
