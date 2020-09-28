package com.netcetera.codingchallenge.game.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardEntryUnitTest {

    GameBoardEntry entry;

    @BeforeEach
    void setUp() {
        this.entry = new GameBoardEntryImpl();
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
    void testGetFieldMarking() {
        assertTrue(this.entry.getFieldMarking() == FieldMarking.EMPTY);
    }

    @Test
    void getFieldState() {
        assertNotNull(this.entry.getFieldMarking());
    }

    @Test
    void testToString() {
        final GameBoardEntry x = new GameBoardEntryImpl();
        x.mark(FieldMarking.X);
        assertEquals(x.toString(), "x");

        final GameBoardEntry o = new GameBoardEntryImpl();
        o.mark(FieldMarking.O);
        assertEquals(o.toString(), "o");

        final GameBoardEntry empty = new GameBoardEntryImpl();
        assertEquals(empty.toString(), " ");
    }

    @Test
    void testEquals() {
        final GameBoardEntry one = new GameBoardEntryImpl();
        final GameBoardEntry other = new GameBoardEntryImpl();
        assertEquals(one, other);

        other.mark(FieldMarking.O);
        assertNotEquals(one, other);
    }
}