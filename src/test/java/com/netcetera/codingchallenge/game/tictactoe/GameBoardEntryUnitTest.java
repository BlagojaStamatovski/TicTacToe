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
        assertEquals(this.entry.getFieldState(), FieldState.EMPTY);
    }

    @Test
    void markWithX() {
        this.entry.mark(FieldState.X);
        assertEquals(this.entry.getFieldState(), FieldState.X);
    }

    @Test
    void markWithO() {
        this.entry.mark(FieldState.O);
        assertEquals(this.entry.getFieldState(), FieldState.O);
    }

    @Test
    void markTwiceFails() {
        assertTrue(this.entry.mark(FieldState.X));
        assertFalse(this.entry.mark(FieldState.O));
        assertNotEquals(this.entry.getFieldState(), FieldState.O);
    }

    @Test
    void getFieldState() {
        assertNotNull(this.entry.getFieldState());
    }

    @Test
    void testToString() {
        final GameBoardEntry x = new GameBoardEntry();
        x.mark(FieldState.X);
        assertEquals(x.toString(), "x");

        final GameBoardEntry o = new GameBoardEntry();
        o.mark(FieldState.O);
        assertEquals(o.toString(), "o");

        final GameBoardEntry empty = new GameBoardEntry();
        assertEquals(empty.toString(), " ");
    }

    @Test
    void testEquals() {
        final GameBoardEntry one = new GameBoardEntry();
        final GameBoardEntry other = new GameBoardEntry();
        assertTrue(one.equals(other));

        other.mark(FieldState.O);
        assertFalse(one.equals(other));
    }
}