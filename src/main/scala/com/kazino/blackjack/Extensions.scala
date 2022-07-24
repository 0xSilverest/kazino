package com.kazino.blackjack

import com.kazino.common.*

import scala.annotation.targetName

extension (card: Card)
  def value: Int =
    card.rank match
      case Rank.Ace => 1
      case Rank.Two => 2
      case Rank.Three => 3
      case Rank.Four => 4
      case Rank.Five => 5
      case Rank.Six => 6
      case Rank.Seven => 7
      case Rank.Eight => 8
      case Rank.Nine => 9
      case Rank.Ten => 10
      case Rank.Jack => 10
      case Rank.Queen => 10
      case Rank.King => 10

extension (hand: Hand)
  def value: Int = hand.foldLeft(0)(_ + _.value)

  def isBlackjack : Boolean = value == 21

  def isBust : Boolean = value > 21

  @targetName("superior")
  def >(other: Hand) : Boolean = value > other.value

  @targetName("inferior")
  def <(other: Hand) : Boolean = value < other.value

  @targetName("equals")
  def ==(other: Hand) : Boolean  = value == other.value

  def isSplittable : Boolean =
    hand.size == 2 && hand.first.rank == hand.last.rank

  def split: (Hand, Hand) =
    (Hand(hand.first), Hand(hand.last))
