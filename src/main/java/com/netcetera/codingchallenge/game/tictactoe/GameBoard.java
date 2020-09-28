package com.netcetera.codingchallenge.game.tictactoe;

import lombok.Getter;

public class GameBoard implements StringDisplayableGameBoard {
    private final GameBoardEntry[][] board;
    private final int size;
    private int movesMade = 0;
    @Getter
    private GameState state;

    public GameBoard(final int size) {
        this.size = size;
        this.board = new GameBoardEntry[this.size][this.size];

        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                this.board[x][y] = new GameBoardEntry();
            }
        }

        this.state = GameState.IN_PROGRESS;
    }

    @Override
    public String displayState() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int y = 0; y < this.size; y++) {
            if (y > 0 && y < this.size) {
                stringBuilder.append(this.getHorizontalSeparator());
            }
            for (int x = 0; x < this.size; x++) {
                if (x > 0 && x < this.size) {
                    stringBuilder.append("|");
                }
                stringBuilder.append(this.board[x][y].toString());
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private String getHorizontalSeparator() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.size * 2 - 1; i++) {
            if (i % 2 == 0) {
                stringBuilder.append("-");
            } else {
                stringBuilder.append("+");
            }
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public boolean isOver() {
        if (this.movesMade < this.size * 2 - 1) {
            return false;
        } else if (!this.isWon() && this.movesMade == this.size * this.size) {
            this.state = GameState.DRAW;
            return true;
        } else {
            return false;
        }
    }

    public boolean makeMove(final int x, final int y, final FieldMarking fieldMarking) {
        if (x >= 0 && y >= 0 && x < this.size && y < this.size) {
            this.movesMade++;
            return this.board[x][y].mark(fieldMarking);
        }
        return false;
    }

    private boolean isWon() {
        return this.checkVerticals() || this.checkHorizontals() || this.checkDiagonals();
    }

    private boolean checkVerticals() {
        final boolean foundWinner = false;
        int iterator = 0;
        while (iterator < this.size) {
            final GameBoardEntry[] vertical = new GameBoardEntry[this.size];

            for (int y = 0; y < this.size; y++) {
                vertical[y] = this.board[iterator][y];
            }
            if (this.checkSequence(vertical)) {
                return true;
            }
            iterator++;
        }

        return foundWinner;
    }

    private boolean checkHorizontals() {
        final boolean foundWinner = false;
        int iterator = 0;
        while (iterator < this.size) {
            final GameBoardEntry[] horizontal = this.board[iterator];
            if (this.checkSequence(horizontal)) {
                return true;
            }
            iterator++;
        }

        return foundWinner;
    }

    private boolean checkDiagonals() {
        final GameBoardEntry[] firstDiagonal = new GameBoardEntry[this.size];
        final GameBoardEntry[] secondDiagonal = new GameBoardEntry[this.size];
        for (int i = 0; i < this.size; i++) {
            firstDiagonal[i] = this.board[i][i];
            secondDiagonal[i] = this.board[i][this.size - 1 - i];
        }
        if (this.checkSequence(firstDiagonal) || this.checkSequence(secondDiagonal)) {
            return true;
        }

        return false;
    }

    private boolean checkSequence(final GameBoardEntry[] sequence) {
        boolean matching = false;

        final FieldMarking first = sequence[0].getFieldMarking();
        if (first == FieldMarking.EMPTY) {
            return false;
        }

        int iterator = 1;
        while (iterator < sequence.length) {
            if ((sequence[iterator].getFieldMarking() == first)) {
                matching = true;
            } else {
                return false;
            }
            iterator++;
        }
        if (first.equals(FieldMarking.O)) {
            this.state = GameState.WINNER_O;
        } else {
            this.state = GameState.WINNER_X;
        }
        return matching;
    }
}
