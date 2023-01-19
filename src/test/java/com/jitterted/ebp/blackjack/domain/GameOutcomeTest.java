package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Deck playerHitsAndGoesBustDeck = new StubDeck(Rank.TEN, Rank.EIGHT,
                                                      Rank.QUEEN, Rank.JACK,
                                                      Rank.THREE);
        Game game = new Game(playerHitsAndGoesBustDeck);
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualTo("You Busted, so you lose.  💸");
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Deck playerStandsAndBeatsDealer = new StubDeck(Rank.TEN, Rank.EIGHT,
                                                       Rank.QUEEN, Rank.JACK);
        Game game = new Game(playerStandsAndBeatsDealer);
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualTo("You beat the Dealer! 💵");
    }
}



