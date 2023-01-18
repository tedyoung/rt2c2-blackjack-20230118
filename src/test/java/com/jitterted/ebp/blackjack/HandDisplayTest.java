package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class HandDisplayTest {
    @Test
    public void displayFaceUpCard() throws Exception {
        Hand hand = new Hand(List.of(new Card(Suit.HEARTS, Rank.ACE)));

        assertThat(ConsoleHand.displayFaceUpCard(hand))
                .isEqualTo("[31m┌─────────┐[1B[11D│A        │[1B[11D│         │[1B[11D│    ♥    │[1B[11D│         │[1B[11D│        A│[1B[11D└─────────┘");
    }

    @Test
    void cardsAsString() {
        Hand hand = new Hand(List.of(new Card(Suit.DIAMONDS, Rank.JACK),
                                     new Card(Suit.SPADES, Rank.FIVE)));

        String output = ConsoleHand.cardsAsString(hand);

        assertThat(output)
                .isEqualTo("[31m┌─────────┐[1B[11D│J        │[1B[11D│         │[1B[11D│    ♦    │[1B[11D│         │[1B[11D│        J│[1B[11D└─────────┘[6A[1C[30m┌─────────┐[1B[11D│5        │[1B[11D│         │[1B[11D│    ♠    │[1B[11D│         │[1B[11D│        5│[1B[11D└─────────┘");
    }
}