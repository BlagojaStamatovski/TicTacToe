package com.netcetera.codingchallenge.game.tictactoe.rest;

import com.netcetera.codingchallenge.game.tictactoe.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/tictactoe")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class GameRestController {

    private final GameService gameService;

    @PostMapping("/start")
    @PreAuthorize("hasAuthority('START_GAME')")
    public ResponseEntity<String> startGame() {
        this.gameService.startGame();
        return ResponseEntity.ok(this.gameService.getGameDisplayState());
    }

    @PutMapping("/move")
    public ResponseEntity<String> makeMove(@RequestBody final MoveRequest moveRequest, final Principal principal) {
        this.gameService.makeMove(moveRequest.getX(), moveRequest.getY(), principal);
        return ResponseEntity.ok(this.gameService.getGameDisplayState());
    }
}
