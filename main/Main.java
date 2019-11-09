package main;

import common.Constants;
import engine.Player;
import engine.ScoreBoard;
import strategy.StrategyList;

import java.util.ArrayList;
import java.util.List;

public final class Main {
    private Main() {
        // just to trick checkstyle
    }

    public static void main(final String[] args) {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        //TODO implement homework logic

        int nrPlayers = gameInput.getPlayerNames().size();
        Player player;
        List<Player> players = new ArrayList<>();
        List<String> playerNames = gameInput.getPlayerNames();
        for (int id = 0; id < nrPlayers; ++id) {
            if (playerNames.get(id).equals("basic")) {
                player = new Player(id, StrategyList.BASIC);
            } else if (playerNames.get(id).equals("greedy")) {
                player = new Player(id, StrategyList.GREEDY);
            } else {
                player = new Player(id, StrategyList.BRIBE);
            }
            players.add(player);
        }

        List<Integer> playerDeck;
        int cardIndex = 0;
        for (int round = 1; round <= gameInput.getRounds(); ++round) {
            for (int sheriffID = 0; sheriffID < nrPlayers; ++sheriffID) {
                Player sheriff = players.get(sheriffID);
                sheriff.setRoundScore(0);
                for (Player p : players) {
                    if (p.getID() != sheriffID) {
                        // Cards drawn form the deck
                        playerDeck = gameInput.getAssetIds().subList(cardIndex,
                                                        cardIndex + Constants.CARDS_DRAWN);
                        // Cards chose by a player
                        p.setCards(playerDeck, round);
                        cardIndex += Constants.CARDS_DRAWN;
                        // Searching process
                        sheriff.playerSearch(p, players.size());
                    }
                }
                sheriff.addScore(sheriff.getRoundScore());
            }

        }
        ScoreBoard.updateFinalScores(players);
        ScoreBoard.finalBouns(players);
        ScoreBoard.printScoreBoard(players);
    }
}
