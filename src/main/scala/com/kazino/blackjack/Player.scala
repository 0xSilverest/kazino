package com.kazino.blackjack

import scala.math.BigDecimal
import com.kazino.common.*
import monocle.macros.GenLens
import monocle.syntax.all.*

case class Player (
  user: String,
  balance: BigDecimal,
  bet: BigDecimal = 0.0,
  hand: Hand = Hand.empty,
  splitHand: Hand = Hand.empty,
  decision: PlayerDecision = PlayerDecision.NotPlaying
) :

  lazy val isBust: Boolean = hand.isBust

  lazy val isNotBust: Boolean = !isBust

  lazy val isBlackjack: Boolean = hand.isBlackjack

  def hasSplittable: Boolean =
    hand.isSplittable
    && splitHand.isEmpty
    && bet <= balance

  def split: Player =
    if hasSplittable then
      val splitHand = hand.split
      this.focus(_.decision).set(PlayerDecision.Split)
        .focus(_.hand).set(splitHand._1)
        .focus(_.splitHand).set(splitHand._2)
        .focus(_.bet).modify(_ + bet)
        .focus(_.balance).modify(_ - bet)
    else
      this

  def hit: Player =
    this.focus(_.decision).set(PlayerDecision.Hit)

  def doubleDown: Player =
    if (bet <= balance)
      this.focus(_.decision).set(PlayerDecision.DoubleDown)
        .focus(_.bet).modify(_ + bet)
        .focus(_.balance).modify(_ - bet)
    else
      this

  def stand: Player =
    this.focus(_.decision).set(PlayerDecision.Stand)

  def surrender: Player = 
    this.focus(_.decision).set(PlayerDecision.Surrender)

  def insurance: Player = 
    this.focus(_.decision).set(PlayerDecision.Insurance)

object Player:
  val user = GenLens[Player](_.user)
  val balance = GenLens[Player](_.balance)
  val bet = GenLens[Player](_.bet)
  val hand = GenLens[Player](_.hand)
  val decision = GenLens[Player](_.decision)
