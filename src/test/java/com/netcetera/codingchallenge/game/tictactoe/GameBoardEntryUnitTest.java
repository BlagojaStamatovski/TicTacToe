package com.netcetera.codingchallenge.game.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardEntryUnitTest {

    GameBoardEntry entry;

    @BeforeEach
    void setUp() {
        this.entry = new GameBoardEntry();
    }

    @Test
    void testCreation() {
        assertEquals(this.entry.getFieldMarking(), FieldMarking.EMPTY);
    }

    @Test
    void markWithX() {
        this.entry.mark(FieldMarking.X);
        assertEquals(this.entry.getFieldMarking(), FieldMarking.X);
    }

    @Test
    void markWithO() {
        this.entry.mark(FieldMarking.O);
        assertEquals(this.entry.getFieldMarking(), FieldMarking.O);
    }

    @Test
    void markTwiceFails() {
        assertTrue(this.entry.mark(FieldMarking.X));
        assertFalse(this.entry.mark(FieldMarking.O));
        assertNotEquals(this.entry.getFieldMarking(), FieldMarking.O);
    }

    @Test
    void getFieldState() {
        assertNotNull(this.entry.getFieldMarking());
    }

    @Test
    void testToString() {
        final GameBoardEntry x = new GameBoardEntry();
        x.mark(FieldMarking.X);
        assertEquals(x.toString(), "x");

        final GameBoardEntry o = new GameBoardEntry();
        o.mark(FieldMarking.O);
        assertEquals(o.toString(), "o");

        final GameBoardEntry empty = new GameBoardEntry();
        assertEquals(empty.toString(), " ");
    }

    @Test
    void testEquals() {
        final GameBoardEntry one = new GameBoardEntry();
        final GameBoardEntry other = new GameBoardEntry();
        assertTrue(one.equals(other));

        other.mark(FieldMarking.O);
        assertFalse(one.equals(other));
    }
}