package com.netcetera.codingchallenge.game.tictactoe;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MoveOutcome {
    private final boolean valid;
    private final String description;
}
