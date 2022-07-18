package com.kazino.engines.blackjack

import com.kazino.common.*
import monocle.syntax.all.*

case class Table(
  dealer: Dealer,
  players: Set[Player] = Set.empty[Player],
  gameState: GameState = GameState.GameStarting,
  roundState: RoundState = RoundState.RoundStarting, 
): 

  lazy val winners: Set[Player] = 
    if dealer.isBlackjack then
      Set.empty
    else if dealer.isBust then
      players.filter(_.isNotBust)
    else
      players.filter(player => player.isNotBust && player.hand.value > dealer.hand.value)

  lazy val losers: Set[Player] = 
    if dealer.isBlackjack then
  players.filter(player => player.hand.value < 21 || player.isBust)
    else 
      players.filter(player =>
        player.isBust ||
        (dealer.isNotBust && player.hand.value < dealer.hand.value)
      )

  lazy val draws: Set[Player] =
    if dealer.isBust then
      Set.empty
    else
      players.filter(_.hand.value == dealer.hand.value)

  def add(player: Player): Table =
    this.focus(_.players).modify(_ + player)

  def remove(player: Player): Table =
    this.focus(_.players).modify(_ - player)

  def isPlayerWinner(player: Player): Boolean = 
    winners.contains(player)

  def isPlayerLoser(player: Player): Boolean =
    losers.contains(player)

  def isPlayerDraw(player: Player): Boolean =
    draws.contains(player)

  def dealToPlayers: Table = 
    val (playersModified, restOfDeck): (Set[Player], Deck) =
      players.foldLeft((Set.empty[Player], dealer.deck)) {
        case ((receivers, deck), player) =>
          val (cards, rest) : (Hand, Deck) = deck.deal(2)
          (receivers + player.focus(_.hand).modify(_ ++ cards), rest)
      }
    this.focus(_.dealer.deck).set(restOfDeck)
      .focus(_.players).set(playersModified)

  def dealToDealer: Table =
    this.focus(_.dealer).modify(_.dealToSelf)

  def dealRound =
    this.focus(_.roundState).set(RoundState.Dealing)
      .dealToPlayers
      .dealToDealer

