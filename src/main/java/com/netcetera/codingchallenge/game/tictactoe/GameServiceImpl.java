package com.netcetera.codingchallenge.game.tictactoe;

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
    private FieldMarking lastPlayed;

    @Override
    public MoveOutcome startGame(final GameBoard gameBoard) {
        if (this.gameBoard != null && !this.gameBoard.isOver()) {
            return new MoveOutcome(false, "A game is already in progress.");
        }
        this.gameBoard = gameBoard;
        this.lastPlayed = null;
        return new MoveOutcome(true, "A new game has been started.");
    }

    @Override
    public MoveOutcome makeMove(final int x, final int y, final Principal principal) {
        if (this.gameBoard == null) {
            return new MoveOutcome(false, "The game has not been started.");
        } else if (this.gameBoard.isOver()) {
            return new MoveOutcome(false, "Game has already ended. Outcome: ." + this.gameBoard.getState());
        } else {
            final FieldMarking markWith = this.getFieldStateFromPrincipal(principal);
            if (this.lastPlayed == markWith) {
                return new MoveOutcome(false, "It is not your turn yet.");
            }
            if (this.gameBoard.makeMove(x, y, markWith)) {
                this.lastPlayed = markWith;

                return new MoveOutcome(true, String.format("Successful move."));
            } else {
                return new MoveOutcome(false, "Invalid move attempted.");
            }
        }
    }

    private FieldMarking getFieldStateFromPrincipal(final Principal principal) {
        final User currentPlayer = this.userService.getUserByUsername(principal.getName());
        final String username = currentPlayer.getUsername();
        if (username.equals("X")) {
            return FieldMarking.X;
        } else {
            return FieldMarking.O;
        }
    }

    @Override
    public String displayState() {
        return this.gameBoard.displayState();
    }
}
