package com.netcetera.codingchallenge.game.tictactoe.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GameRestControllerAdvice {
    @ExceptionHandler(GameException.class)
    public ResponseEntity<ErrorResponse> handleGameException(final GameException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
}
