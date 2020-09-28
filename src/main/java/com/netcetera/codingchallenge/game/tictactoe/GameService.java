package com.netcetera.codingchallenge.game.tictactoe;

import java.security.Principal;

public interface GameService {
    MoveOutcome startGame();

    MoveOutcome makeMove(int x, int y, Principal principal);

    String getGameDisplayState();
}
