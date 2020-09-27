package com.netcetera.codingchallenge.game.tictactoe;

import com.netcetera.codingchallenge.game.tictactoe.rest.GameException;
import com.netcetera.codingchallenge.users.User;
import com.netcetera.codingchallenge.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {
    private GameBoard gameBoard;
    private final UserService userService;
    private final int gameBoardSize = 3;
    private FieldState lastPlayed;

    @Override
    public void startGame() {
        if (this.gameBoard != null && !this.gameBoard.isOver()) {
            throw new GameException("A game is already in progress");
        }
        this.gameBoard = new GameBoard(this.gameBoardSize);
        this.lastPlayed = null;
    }

    @Override
    public void makeMove(final int x, final int y, final Principal principal) {
        if (this.gameBoard == null) {
            throw new GameException("Game has not been started");
        }

        if (this.gameBoard.isOver()) {
            final FieldState winner = this.gameBoard.getWinner();
            throw new GameException("Game has already ended. Please start a new game");
        } else {
            final FieldState markWith = this.getFieldStateFromPrincipal(principal);

            if (this.lastPlayed == markWith) {
                throw new GameException("It is not your move yet.");
            }

            if (this.gameBoard.makeMove(x, y, markWith)) {
                this.lastPlayed = markWith;
            } else {
                throw new GameException("Invalid move attempted");
            }
        }
    }

    @Override
    public FieldState getFieldStateFromPrincipal(final Principal principal) {
        final User currentPlayer = this.userService.getUserByUsername(principal.getName());
        final String username = currentPlayer.getUsername();
        if (username.equals("X")) {
            return FieldState.X;
        } else {
            return FieldState.O;
        }
    }

    @Override
    public String getGameDisplayState() {
        return this.gameBoard.displayState();
    }
}
