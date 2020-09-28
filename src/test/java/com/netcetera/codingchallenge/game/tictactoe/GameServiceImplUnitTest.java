package com.netcetera.codingchallenge.game.tictactoe;

import com.netcetera.codingchallenge.users.User;
import com.netcetera.codingchallenge.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceImplUnitTest {
    @Mock
    private UserService userService;
    @Mock
    private Principal mockPrincipalX;
    @Mock
    private Principal mockPrincipalO;

    private GameService gameService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.gameService = new GameServiceImpl(this.userService);

        Mockito.when(this.mockPrincipalO.getName()).thenReturn("O");
        Mockito.when(this.mockPrincipalX.getName()).thenReturn("X");
        Mockito.when(this.userService.getUserByUsername("O")).thenReturn(User.builder().username("O").password("testPassword").build());
        Mockito.when(this.userService.getUserByUsername("X")).thenReturn(User.builder().username("X").password("testPassword").build());
    }

    @Test
    void startGame() {
        final MoveOutcome outcome = this.startNewGame();

        assertTrue(outcome.isValid());
    }

    @Test
    void startGameBeforeItEnds() {
        final MoveOutcome firstOutcome = this.startNewGame();
        final MoveOutcome secondOutcome = this.startNewGame();

        assertTrue(firstOutcome.isValid());
        assertFalse(secondOutcome.isValid());
    }

    @Test
    void makeMoveBeforeGameIsStarted() {
        final MoveOutcome firstOutcome = this.gameService.makeMove(0, 0, this.mockPrincipalO);

        assertFalse(firstOutcome.isValid());
    }

    @Test
    void makeMoveAfterGameIsStarted() {
        this.startNewGame();
        final MoveOutcome firstOutcome = this.gameService.makeMove(0, 1, this.mockPrincipalX);
        final MoveOutcome secondOutcome = this.gameService.makeMove(0, 2, this.mockPrincipalO);

        assertTrue(firstOutcome.isValid());
        assertTrue(secondOutcome.isValid());
    }

    @Test
    void makeMoveOutOfBounts() {
        this.startNewGame();

        final MoveOutcome firstOutcome = this.gameService.makeMove(-1, 1, this.mockPrincipalX);
        final MoveOutcome secondOutcome = this.gameService.makeMove(0, 4, this.mockPrincipalX);

        assertFalse(firstOutcome.isValid());
        assertFalse(secondOutcome.isValid());
    }

    @Test
    void makeTwoConsecutiveMovesWithSamePlayer() {
        this.gameService.startGame(new GameBoardImpl(3));
        final MoveOutcome firstOutcome = this.gameService.makeMove(0, 1, this.mockPrincipalX);
        final MoveOutcome secondOutcome = this.gameService.makeMove(0, 2, this.mockPrincipalX);

        assertTrue(firstOutcome.isValid());
        assertFalse(secondOutcome.isValid());
    }

    @Test
    void makeMoveAfterGameIsFinished() {
        this.gameService.startGame(new GameBoardImpl(3));

        assertTrue(this.gameService.makeMove(0, 1, this.mockPrincipalX).isValid());
        assertTrue(this.gameService.makeMove(2, 1, this.mockPrincipalO).isValid());
        assertTrue(this.gameService.makeMove(0, 2, this.mockPrincipalX).isValid());
        assertTrue(this.gameService.makeMove(2, 2, this.mockPrincipalO).isValid());
        assertTrue(this.gameService.makeMove(0, 0, this.mockPrincipalX).isValid());
        final MoveOutcome outcome = this.gameService.makeMove(1, 1, this.mockPrincipalO);

        assertFalse(outcome.isValid());
    }

    @Test
    void getGameDisplayState() {
        this.startNewGame();

        assertNotNull(this.gameService.displayState());
    }

    MoveOutcome startNewGame() {
        return this.gameService.startGame(new GameBoardImpl(3));
    }
}