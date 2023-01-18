package com.jitterted.ebp.blackjack;

import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleHand {
    // String displayFaceUpCard(Hand hand) <- transform DOMAIN to CONSOLE I/O
    static String displayFaceUpCard(Hand hand) {
        return ConsoleCard.display(hand.faceUpCard());
    }

    // String cardsAsString(Hand hand)
    public static String cardsAsString(Hand hand) {
        return hand.cards()
                   .map(ConsoleCard::display)
                   .collect(Collectors.joining(
                           ansi().cursorUp(6).cursorRight(1).toString()));
    }
}
