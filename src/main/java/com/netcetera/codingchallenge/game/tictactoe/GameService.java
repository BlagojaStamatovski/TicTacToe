package com.netcetera.codingchallenge.game.tictactoe;

import java.security.Principal;

public interface GameService {
    void startGame();

    void makeMove(int x, int y, Principal principal);

    FieldState getFieldStateFromPrincipal(Principal principal);

    String getGameDisplayState();
}
