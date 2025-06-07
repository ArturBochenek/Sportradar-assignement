package com.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoard {
    private final List<Game> games;

    public ScoreBoard() {
        this.games = new ArrayList<>();
    }

    public void startGame(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Teams must not be null.");
        }
        for (Game game : games) {
            if (game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam)) {
                throw new IllegalStateException("Game already started.");
            }
        }
        games.add(new Game(homeTeam, awayTeam));
    }

    public void finishGame(String homeTeam, String awayTeam) {
        Game toRemove = null;
        for (Game game : games) {
            if (game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam)) {
                toRemove = game;
                break;
            }
        }
        if (toRemove == null) {
            throw new IllegalStateException("Game not found.");
        }
        games.remove(toRemove);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Game game : games) {
            if (game.getHomeTeam().equals(homeTeam) && game.getAwayTeam().equals(awayTeam)) {
                game.updateScore(homeScore, awayScore);
                return;
            }
        }
        throw new IllegalStateException("Game not found.");
    }

    public List<Game> getSummary() {
        List<Game> summary = new ArrayList<>(games);
        summary.sort(Comparator
                .comparingInt(Game::totalScore)
                .thenComparing(games::indexOf)
                .reversed());
        return summary;
    }
}