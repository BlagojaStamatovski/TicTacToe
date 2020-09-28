package com.netcetera.codingchallenge.game.tictactoe.rest;

import com.netcetera.codingchallenge.game.tictactoe.GameService;
import com.netcetera.codingchallenge.game.tictactoe.MoveOutcome;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/tictactoe")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/start")
    @PreAuthorize("hasAuthority('START_GAME')")
    public ResponseEntity<String> startGame() {
        final MoveOutcome moveOutcome = this.gameService.startGame();
        return this.handleMoveOutcome(moveOutcome);
    }

    @PutMapping("/move")
    public ResponseEntity<String> makeMove(@RequestBody final MoveRequest moveRequest, final Principal principal) {
        final MoveOutcome moveOutcome = this.gameService.makeMove(moveRequest.getX(), moveRequest.getY(), principal);
        return this.handleMoveOutcome(moveOutcome);
    }

    private ResponseEntity<String> handleMoveOutcome(final MoveOutcome moveOutcome) {
        final String toReturn = this.gameService.getGameDisplayState() + "\n" + moveOutcome.getDescription();

        if (!moveOutcome.isValid()) {
            return ResponseEntity.badRequest().body(toReturn);
        }
        return ResponseEntity.ok(toReturn);
    }
}
