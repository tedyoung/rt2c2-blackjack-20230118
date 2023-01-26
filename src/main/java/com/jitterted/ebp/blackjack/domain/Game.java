package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.application.port.GameMonitor;

public class Game {

    private final Deck deck;
    private final GameMonitor gameMonitor;

    private final Hand dealerHand = new Hand();
    private final Hand playerHand = new Hand();

    private boolean playerDone;

    public Game(Deck deck) {
        this.deck = deck;
        this.gameMonitor = (game) -> {
        };
    }

    public Game(Deck deck, GameMonitor gameMonitor) {
        this.deck = deck;
        this.gameMonitor = gameMonitor;
    }

    public void initialDeal() {
        dealRoundOfCards();
        dealRoundOfCards();
        if (playerHand.hasBlackjack()) {
            playerDone = true;
        }
    }

    private void dealRoundOfCards() {
        // why: players first because this is the rule
        playerHand.drawFrom(deck);
        dealerHand.drawFrom(deck);
    }

    public GameOutcome determineOutcome() {
        // precondition: playerDone == true, i.e., player must have completed their turn to ask this question
        // could implement the precondition check using this guard clause:
        //        if (!playerDone) {
        //            throw new IllegalStateException();
        //        }
        if (playerHand.isBusted()) {
            return GameOutcome.PLAYER_BUSTED;
        } else if (playerHand.hasBlackjack()) {
            return GameOutcome.PLAYER_WINS_BLACKJACK;
        } else if (dealerHand.isBusted()) {
            return GameOutcome.DEALER_BUSTED;
        } else if (playerHand.beats(dealerHand)) {
            return GameOutcome.PLAYER_BEATS_DEALER;
        } else if (playerHand.pushes(dealerHand)) {
            return GameOutcome.PLAYER_PUSHES;
        } else {
            return GameOutcome.PLAYER_LOSES;
        }
    }


    public Hand playerHand() {
        return playerHand;
    }

    // QUERY METHOD RULE
    // 1. Hand: mutable, not a snapshot, internal information
    // 1a. Copy of Hand: mutable (confusing to the client)
    // 1b. "Read Only" interface for Hand: HandReadOnly (no command methods?)
    // 2. New class, a Value Object: HandValue - list of cards, face up card (dealt), and score (value)
    // 2a. HandView: Data Transfer Object - getters/setters for the cards, face-up card, and value
    //          DTOs belong in Adapters, do NOT belong in the Domain
    public Hand dealerHand() {
        return dealerHand;
    }

    public void playerHits() {
        playerHand.drawFrom(deck);
        playerDone = playerHand.isBusted();
    }

    public void playerStands() {
        playerDone = true;
        dealerTurn();
        gameMonitor.roundCompleted(this);
    }

    public boolean isPlayerDone() {
        return playerDone;
    }

    private void dealerTurn() {
        // Dealer makes its choice automatically based on a simple heuristic (<=16 must hit, =>17 must stand)
        if (!playerHand.isBusted()) {
            while (dealerHand.dealerMustDrawCard()) {
                dealerHand.drawFrom(deck);
            }
        }
    }

}
