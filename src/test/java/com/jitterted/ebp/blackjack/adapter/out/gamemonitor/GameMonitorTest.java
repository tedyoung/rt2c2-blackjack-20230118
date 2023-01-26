package com.jitterted.ebp.blackjack.adapter.out.gamemonitor;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameMonitorTest {

    @Test
    public void playerStandsThenGameIsOverAndResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerStandsAndBeatsDealer(), gameMonitorSpy);
        game.initialDeal();

        game.playerStands();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsAndGoesBustThenGameResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerHitsAndGoesBust(), gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy).roundCompleted(any(Game.class));
    }

    @Test
    public void playerHitsDoesNotGoBustThenNoResultsSentToMonitor() throws Exception {
        GameMonitor gameMonitorSpy = spy(GameMonitor.class);
        Game game = new Game(StubDeck.playerHitsAndDoesNotGoBust(), gameMonitorSpy);
        game.initialDeal();

        game.playerHits();

        verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
    }

}