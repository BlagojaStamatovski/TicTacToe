package com.netcetera.codingchallenge.game.tictactoe.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private final String message;
    private final Date timestamp = Date.from(Instant.now());
}
