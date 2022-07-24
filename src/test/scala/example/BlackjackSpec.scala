package com.kazino.test.engines

import com.kazino.blackjack.{Dealer, Player, Table}
import org.scalatest.*
import flatspec.AnyFlatSpec
import matchers.should.Matchers
import com.kazino.common.*
import com.kazino.engines.blackjack.*

class BlackjackSpec extends AnyFlatSpec with Matchers {
    val defaultDealer: Dealer = Dealer()

    val defaultTable: Table = Table(defaultDealer)

    "Players" should "be able to join the table and leave the table" in {
        val player = new Player("John", 100, 0)
        val newTable = defaultTable.add(player)
        assert(newTable.players.contains(player))


        val newerTable = newTable.remove(player)
        assert(!newerTable.players.contains(player))
    }

    "Players on a table" should "all get cards" in {
        val player1 = new Player("John", 100, 0)
        val player2 = new Player("Johnny", 100, 0)
        val player3 = new Player("Jane", 100, 0)
        val player4 = new Player("Janice", 100, 0)

        val newTable = defaultTable
            .add(player1)
            .add(player2)
            .add(player3)
            .add(player4)

        val newTableWithCards = newTable.dealRound

        newTableWithCards.players.foreach { player =>
            assert(player.hand.size == 2)
        }
    }
}