package com.jitterted.ebp.blackjack.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class GameOutcomeTest {

    @Test
    void playerHitsAndGoesBustThenOutcomeIsPlayerLoses() {
        Game game = new Game(StubDeck.playerHitsAndGoesBust());
        game.initialDeal();

        game.playerHits();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BUSTED);
    }

    @Test
    void playerDealtBetterHandThanDealerAndStandsThenPlayerBeatsDealer() {
        Game game = new Game(StubDeck.playerNotDealtBlackjackStandsAndBeatsDealer());
        game.initialDeal();

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void playerDealtHandWithSameValueAsDealerThenPlayerPushesDealer() {
        Game game = new Game(StubDeck.playerStandsAndPushesDealer());
        game.initialDeal();

        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_PUSHES);
    }

    @Test
    void playerDealtBlackjackUponInitialDealThenWinsBlackjack() {
        Game game = new Game(StubDeck.playerDealtBlackjack());

        game.initialDeal();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_WINS_BLACKJACK);
        assertThat(game.isPlayerDone())
                .isTrue();
    }

    @Test
    void playerWinsButNotWithBlackjackWhenHandValueIs21() {
        StubDeck playerNotDealtBlackjackGets21UponHit = new StubDeck(Rank.SEVEN, Rank.NINE,
                                                                     Rank.JACK, Rank.EIGHT,
                                                                     Rank.FOUR);
        Game game = new Game(playerNotDealtBlackjackGets21UponHit);
        game.initialDeal();

        game.playerHits();
        game.playerStands();

        assertThat(game.determineOutcome())
                .isEqualByComparingTo(GameOutcome.PLAYER_BEATS_DEALER);
    }

    @Test
    void playerNotDealtBlackjackUponInitialDealAndDoesNotHitNorStandThenIsNotDone() {
        StubDeck playerNotDealtBlackjack = new StubDeck(Rank.SIX, Rank.NINE,
                                                        Rank.JACK, Rank.EIGHT);
        Game game = new Game(playerNotDealtBlackjack);

        game.initialDeal();

        assertThat(game.isPlayerDone())
                .isFalse();
    }

    @Test
    public void standResultsInDealerDrawingCardOnTheirTurn() throws Exception {
        Deck dealerDrawsAdditionalCardOnTheirTurnDeck =
                new StubDeck(Rank.TEN, Rank.QUEEN,
                             Rank.NINE, Rank.FIVE,
                             Rank.SIX);
        Game game = new Game(dealerDrawsAdditionalCardOnTheirTurnDeck);
        game.initialDeal();

        game.playerStands();

        assertThat(game.dealerHand().cards())
                .hasSize(3);
    }


}



