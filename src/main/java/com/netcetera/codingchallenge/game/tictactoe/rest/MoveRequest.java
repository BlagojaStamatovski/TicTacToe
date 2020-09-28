package com.netcetera.codingchallenge.game.tictactoe.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveRequest {
    private int x;
    private int y;
}
