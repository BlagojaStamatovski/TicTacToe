package com.netcetera.codingchallenge.game.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardImplUnitTest {

    GameBoard gameBoard;

    @BeforeEach
    void setUp() {
        this.gameBoard = new GameBoardImpl(3);
    }

    @Test
    void displayState() {
        final GameStateDisplayable gameStateDisplayable = (GameStateDisplayable) this.gameBoard;
        assertNotNull(gameStateDisplayable.displayState());
    }

    @Test
    void isOverBeforeEnoughMoves() {
        this.gameBoard.makeMove(0, 0, FieldMarking.X);
        this.gameBoard.makeMove(1, 2, FieldMarking.O);
        this.gameBoard.makeMove(0, 1, FieldMarking.X);
        assertFalse(this.gameBoard.isOver());
    }

    @Test
    void isOverAndXWins() {
        this.gameBoard.makeMove(0, 0, FieldMarking.X);
        this.gameBoard.makeMove(1, 2, FieldMarking.O);
        this.gameBoard.makeMove(0, 1, FieldMarking.X);
        this.gameBoard.makeMove(1, 1, FieldMarking.O);
        this.gameBoard.makeMove(0, 2, FieldMarking.X);

        assertTrue(this.gameBoard.isOver() && this.gameBoard.getState() == GameState.WINNER_X);
    }

    @Test
    void isOverAndOWins() {
        this.gameBoard.makeMove(1, 0, FieldMarking.O);
        this.gameBoard.makeMove(2, 1, FieldMarking.X);
        this.gameBoard.makeMove(1, 1, FieldMarking.O);
        this.gameBoard.makeMove(2, 2, FieldMarking.X);
        this.gameBoard.makeMove(1, 2, FieldMarking.O);

        assertTrue(this.gameBoard.isOver() && this.gameBoard.getState() == GameState.WINNER_O);
    }

    @Test
    void isOverAndDraw() {
        this.gameBoard.makeMove(2, 2, FieldMarking.X);
        this.gameBoard.makeMove(0, 0, FieldMarking.O);
        this.gameBoard.makeMove(0, 1, FieldMarking.X);
        this.gameBoard.makeMove(0, 2, FieldMarking.O);
        this.gameBoard.makeMove(1, 2, FieldMarking.X);
        this.gameBoard.makeMove(1, 0, FieldMarking.O);
        this.gameBoard.makeMove(1, 1, FieldMarking.X);
        this.gameBoard.makeMove(2, 0, FieldMarking.O);
        this.gameBoard.makeMove(2, 1, FieldMarking.X);

        assertTrue(this.gameBoard.isOver() && this.gameBoard.getState() == GameState.DRAW);
    }

    @Test
    void makeInvalidMove() {
        this.gameBoard.makeMove(0, 0, FieldMarking.X);
        assertFalse(this.gameBoard.makeMove(0, 0, FieldMarking.X));
        assertFalse(this.gameBoard.makeMove(-1, -1, FieldMarking.X));
        assertFalse(this.gameBoard.makeMove(4, 4, FieldMarking.X));
    }

    @Test
    void makeValidMove() {
        assertTrue(this.gameBoard.makeMove(0, 0, FieldMarking.X));
    }
}