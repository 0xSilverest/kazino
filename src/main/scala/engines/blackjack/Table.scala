package com.kazino.engines.blackjack

import com.kazino.common.*
import monocle.syntax.all.*

case class Table(
  dealer: Dealer,
  players: List[Player],
  gameState: GameState,
  roundState: RoundState, 
): 

  lazy val winners: List[Player] = 
    if dealer.isBlackjack then
      List.empty
    else if dealer.isBust then
      players.filter(_.isNotBust)
    else
      players.filter(player => player.isNotBust && player.hand.value > dealer.hand.value)

  lazy val losers: List[Player] = 
    if dealer.isBlackjack then
  players.filter(player => player.hand.value < 21 || player.isBust)
    else 
      players.filter(player =>
        player.isBust ||
        (dealer.isNotBust && player.hand.value < dealer.hand.value)
      )

  lazy val draws: List[Player] =
    if dealer.isBust then
      List.empty
    else
      players.filter(player => player.hand.value == dealer.hand.value)

  def isPlayerWinner(player: Player): Boolean = 
    winners.contains(player)

  def isPlayerLoser(player: Player): Boolean =
    losers.contains(player)

  def isPlayerDraw(player: Player): Boolean =
    draws.contains(player)

  def dealToPlayers: Table = 
    val (playersModified, restOfDeck): (List[Player], Deck) =
      players.foldLeft((List.empty[Player], dealer.deck)) {
        case ((receivers, deck), player) =>
          val (cards, rest) : (Hand, Deck) = deck.deal(2)
          (receivers :+
            (player.focus(_.hand).modify(_ ++ cards)
          ), rest)
      }
    this.focus(_.dealer.deck).set(restOfDeck)
      .focus(_.players).set(playersModified)

  def dealToDealer: Table =
    this.focus(_.dealer).modify(_.dealToSelf)

  def dealRound =
    this.focus(_.roundState).set(RoundState.Dealing)
      .dealToPlayers
      .dealToDealer

