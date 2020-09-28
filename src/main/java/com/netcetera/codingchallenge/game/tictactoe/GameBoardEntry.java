package com.netcetera.codingchallenge.game.tictactoe;

import java.io.Serializable;

public interface GameBoardEntry extends Serializable {
    boolean mark(FieldMarking fieldMarking);

    FieldMarking getFieldMarking();
}
