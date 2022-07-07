package com.kazino.common

opaque type Deck = List[Card]

object Deck:

  def apply(): Deck =
    Suit.values.toList
      .flatMap { s =>
        Rank.values.toList
          .map { r =>
            Card(s, r)
          }
        }

  def apply(numberOfDecks: Int): Deck =
    (1 to numberOfDecks)
      .flatMap(i => Deck())
      .toList

  extension (deck: Deck)
    def shuffle: Deck =
      scala.util.Random.shuffle(deck)
