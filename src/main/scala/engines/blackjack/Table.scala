package com.kazino.engines.blackjack

case class Table(
  dealer: Dealer,
  players: List[Player],
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

