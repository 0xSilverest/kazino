package com.kazino.common

opaque type Hand = List[Card]

object Hand:
  def apply(cards: Card*): Hand = cards.toList

  def empty: Hand = List.empty

  extension (h: Hand)
    def foldLeft[A](init: A)(f: (A, Card) => A): A = h.foldLeft(init)(f)
