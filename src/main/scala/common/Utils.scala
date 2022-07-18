package com.kazino.common

import scala.annotation.targetName

case class Card(suit: Suit, rank: Rank)

enum Suit:
  case Spades, Hearts, Diamonds, Clubs

enum Rank:
  case Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King

opaque type Deck = List[Card]

object Deck:

  def apply(): Deck =
    Suit.values.toList
      .flatMap { s =>
        Rank.values.toList
          .map { r =>
            Card(s, r)
          }
        }.shuffle

  def apply(numberOfDecks: Int): Deck =
    (1 to numberOfDecks)
      .flatMap(i => Deck())
      .toList.shuffle

  extension (deck: Deck)
    def shuffle: Deck =
      scala.util.Random.shuffle(deck)
  
    def deal(numberOfCards: Int): (Hand, Deck) =
      deck.splitAt(numberOfCards)

    def hit: (Card, Deck) = (deck.head, deck.tail)

opaque type Hand = List[Card]

object Hand:
  def apply(cards: Card*): Hand = cards.toList

  def empty: Hand = List.empty

  extension (h: Hand)
    def foldLeft[A](init: A)(f: (A, Card) => A): A = h.foldLeft(init)(f)

    @targetName("appendCard")
    def :+ (card: Card) : Hand = h :+ card

    @targetName("appendCards")
    def ++ (cards: Hand) : Hand = h ++ cards

    def resetHand() : Hand = List()

    def size : Int = h.size

    def first: Card = h.head

    def last: Card = h.last

    def isEmpty : Boolean = h.isEmpty
