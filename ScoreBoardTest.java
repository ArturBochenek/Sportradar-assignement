package com.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {
    private ScoreBoard board;

    @BeforeEach
    void setup() {
        board = new ScoreBoard();
    }

    @Test
    void testStartGame() {
        board.startGame("Mexico", "Canada");
        List<Game> games = board.getSummary();
        assertEquals(1, games.size());
        assertEquals("Mexico", games.get(0).getHomeTeam());
    }

    @Test
    void testStartDuplicateGameThrows() {
        board.startGame("Mexico", "Canada");
        assertThrows(IllegalStateException.class, () -> board.startGame("Mexico", "Canada"));
    }

    @Test
    void testFinishGame() {
        board.startGame("Mexico", "Canada");
        board.finishGame("Mexico", "Canada");
        assertEquals(0, board.getSummary().size());
    }

    @Test
    void testFinishNonexistentGameThrows() {
        assertThrows(IllegalStateException.class, () -> board.finishGame("Mexico", "Canada"));
    }

    @Test
    void testUpdateScore() {
        board.startGame("Mexico", "Canada");
        board.updateScore("Mexico", "Canada", 3, 2);
        Game game = board.getSummary().get(0);
        assertEquals(3, game.getHomeScore());
        assertEquals(2, game.getAwayScore());
    }

    @Test
    void testUpdateNonexistentGameThrows() {
        assertThrows(IllegalStateException.class, () -> board.updateScore("X", "Y", 1, 1));
    }

    @Test
    void testGetSummaryOrder() {
        board.startGame("Mexico", "Canada");
        board.updateScore("Mexico", "Canada", 0, 5);

        board.startGame("Spain", "Brazil");
        board.updateScore("Spain", "Brazil", 10, 2);

        board.startGame("Germany", "France");
        board.updateScore("Germany", "France", 2, 2);

        board.startGame("Uruguay", "Italy");
        board.updateScore("Uruguay", "Italy", 6, 6);

        board.startGame("Argentina", "Australia");
        board.updateScore("Argentina", "Australia", 3, 1);

        List<Game> summary = board.getSummary();

        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Germany", summary.get(4).getHomeTeam());
    }
}