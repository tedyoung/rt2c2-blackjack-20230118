package com.jitterted.ebp.blackjack;

public class ConsoleHand {
    // String displayFaceUpCard(Hand hand) <- transform DOMAIN to CONSOLE I/O
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.faceUpCard());
    }
}
