package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = new Game(StubDeck.createPlayerHitsAndGoesBust());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer());
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void playerDealtHandWithSameValueAsDealerThenPlayerPushesDealer() {
        Game game = new Game(StubDeck.playerStandsAndPushesDealer());
        game.initialDeal();

        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_PUSHES);
    }

    @Test
    void playerDealtBlackjackUponInitialDealThenWinsBlackjack() {
        StubDeck playerDealtBlackjack = new StubDeck(Rank.ACE, Rank.NINE,
                                                     Rank.JACK, Rank.EIGHT);
        Game game = new Game(playerDealtBlackjack);

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void playerWinsButNotWithBlackjackWhenHandValueIs21() {
        StubDeck playerDealtBlackjack = new StubDeck(Rank.SEVEN, Rank.NINE,
                                                     Rank.JACK, Rank.EIGHT,
                                                     Rank.FOUR);
        Game game = new Game(playerDealtBlackjack);
        game.initialDeal();

        game.playerHits();
        game.playerStands();
        game.dealerTurn();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void playerNotDealtBlackjackUponInitialDealAndDoesNotHitNorStandThenIsNotDone() {
        StubDeck playerDealtBlackjack = new StubDeck(Rank.SIX, Rank.NINE,
                                                     Rank.JACK, Rank.EIGHT);
        Game game = new Game(playerDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

}



