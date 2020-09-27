package com.netcetera.codingchallenge.game.tictactoe;

import lombok.Getter;

public class GameBoard implements StringDisplayableGameBoard {
    private final GameBoardEntry[][] state;
    private final int size;
    private int movesMade = 0;
    @Getter
    private FieldState winner;

    public GameBoard(final int size) {
        this.size = size;
        this.state = new GameBoardEntry[this.size][this.size];

        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                this.state[x][y] = new GameBoardEntry();
            }
        }
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
                stringBuilder.append(this.state[x][y].toString());
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
        } else if (this.movesMade == this.size * this.size) {
            return true;
        } else {
            return this.isWon();
        }
    }

    public boolean makeMove(final int x, final int y, final FieldState fieldState) {
        if (x >= 0 && y >= 0 && x < this.size && y < this.size) {
            this.movesMade++;
            return this.state[x][y].mark(fieldState);
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
                vertical[y] = this.state[iterator][y];
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
            final GameBoardEntry[] horizontal = this.state[iterator];
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
            firstDiagonal[i] = this.state[i][i];
            secondDiagonal[i] = this.state[i][this.size - 1 - i];
        }
        if (this.checkSequence(firstDiagonal) || this.checkSequence(secondDiagonal)) {
            return true;
        }

        return false;
    }

    private boolean checkSequence(final GameBoardEntry[] sequence) {
        boolean matching = false;

        final FieldState first = sequence[0].getFieldState();
        if (first == FieldState.EMPTY) {
            return false;
        }

        int iterator = 1;
        while (iterator < sequence.length) {
            if ((sequence[iterator].getFieldState() == first)) {
                matching = true;
            } else {
                return false;
            }
            iterator++;
        }
        this.winner = first;
        return matching;
    }
}
