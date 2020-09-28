package com.netcetera.codingchallenge.game.tictactoe;

import java.security.Principal;

public interface GameService extends GameStateDisplayable {
    MoveOutcome startGame(GameBoard gameBoard);

    MoveOutcome makeMove(int x, int y, Principal principal);
}
