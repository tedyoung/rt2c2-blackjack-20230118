package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

    private Game game;

    @Autowired
    public BlackjackController(Game game) {
        this.game = game;
    }

    @GetMapping("/game")
    public String gameView(Model model) {
        GameView gameView = GameView.from(game);
        model.addAttribute("gameView", gameView);
        return "blackjack";
    }

    @GetMapping("/done")
    public String doneView(Model model) {
        GameView gameView = GameView.from(game);
        model.addAttribute("gameView", gameView);
        model.addAttribute("outcome", game.determineOutcome().displayString());
        return "done";
    }

    @PostMapping("/hit")
    public String hitCommand() {
        game.playerHits();
        return redirectBasedOnPlayerDone();
    }

    @PostMapping("/start-game")
    public String startGame() {
        game.initialDeal();
        return redirectBasedOnPlayerDone();
    }

    @PostMapping("/stand")
    public String standCommand() {
        game.playerStands();
        return redirectBasedOnPlayerDone();
    }

    private String redirectBasedOnPlayerDone() {
        if (game.isPlayerDone()) {
            return "redirect:/done";
        }
        return "redirect:/game";
    }

}
