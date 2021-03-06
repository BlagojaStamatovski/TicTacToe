package com.netcetera.codingchallenge.game.tictactoe;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class GameBoardEntryImpl implements GameBoardEntry {
    private static final long serialVersionUID = 5638513410322147931L;

    @Getter
    private FieldMarking fieldMarking = FieldMarking.EMPTY;

    @Override
    public boolean mark(final FieldMarking fieldMarking) {
        if (!this.isPlayed()) {
            this.fieldMarking = fieldMarking;
            return true;
        }
        return false;
    }

    private boolean isPlayed() {
        return !(this.fieldMarking == FieldMarking.EMPTY);
    }

    @Override
    public String toString() {
        switch (this.fieldMarking) {
            case O:
                return "o";
            case X:
                return "x";
            case EMPTY:
                return " ";
            default:
                return " ";
        }
    }
}
