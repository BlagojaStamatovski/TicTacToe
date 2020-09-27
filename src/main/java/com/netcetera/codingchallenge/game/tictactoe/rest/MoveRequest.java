package com.netcetera.codingchallenge.game.tictactoe.rest;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoveRequest {
    int x;
    int y;
}
