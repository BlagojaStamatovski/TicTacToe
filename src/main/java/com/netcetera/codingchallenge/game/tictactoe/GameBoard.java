package com.netcetera.codingchallenge.game.tictactoe;

public interface GameBoard extends GameStateDisplayable {
    boolean isOver();

    boolean makeMove(int x, int y, FieldMarking fieldMarking);

    GameState getState();
}
