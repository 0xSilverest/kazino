package com.kazino.engines.blackjack

import com.kazino.common.*

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

  def > (other: Hand) : Boolean = value > other.value

  def < (other: Hand) : Boolean = value < other.value

  def == (other: Hand) : Boolean  = value == other.value
