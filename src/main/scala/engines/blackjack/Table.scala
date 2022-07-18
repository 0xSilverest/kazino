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

  def playerTurn = 
    this.focus(_.roundState).set(RoundState.PlayerTurn)

  def playerDecision(player: Player): Player = 
    import PlayerDecision._
    player.decision match
      case NotPlaying => player
      case Surrender => player.surrender
      case Stand => player.stand
      case DoubleDown =>
        val (card, rest) = dealer.deck.hit
        player.doubleDown.focus(_.hand).modify(_ :+ card)
      case Insurance => player.insurance
      case Split =>
        val (twoCards, rest) = dealer.deck.deal(2)
        player.split
          .focus(_.hand).modify(_ :+ twoCards.first)
          .focus(_.splitHand).modify(_ :+ twoCards.last)
      case Hit => 
        val (card, rest) = dealer.deck.hit
        // this.focus(_.dealer.deck).set(rest)
        //   .focus(_.players).modify(_ :+ playerModified)
        //   .playerTurn
        player.focus(_.hand).modify(_ :+ card)
