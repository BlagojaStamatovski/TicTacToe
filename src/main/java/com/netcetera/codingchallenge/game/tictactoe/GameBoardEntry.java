package com.netcetera.codingchallenge.game.tictactoe;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@EqualsAndHashCode
public class GameBoardEntry implements Serializable {
    private static final long serialVersionUID = 5638513410322147931L;

    @Getter
    private FieldState fieldState = FieldState.EMPTY;

    public boolean mark(final FieldState fieldState) {
        if (!this.isPlayed()) {
            this.fieldState = fieldState;
            return true;
        }
        return false;
    }

    private boolean isPlayed() {
        return !(this.fieldState == FieldState.EMPTY);
    }

    @Override
    public String toString() {
        switch (this.fieldState) {
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
