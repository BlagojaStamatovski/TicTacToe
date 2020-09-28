package com.netcetera.codingchallenge.game.tictactoe.rest;

public class GameException extends RuntimeException {
    private static final long serialVersionUID = -3601311384412919508L;

    public GameException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public GameException(final String message) {
        super(message);
    }
}
