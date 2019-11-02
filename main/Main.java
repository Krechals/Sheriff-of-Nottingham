package main;

import engine.Player;
import engine.ScoreBoard;
import goods.Goods;
import strategy.StrategyList;

import java.lang.ref.SoftReference;
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
        for (int round = 0; round < gameInput.getRounds(); ++round) {
            for (int subRound = 0; subRound < nrPlayers; ++subRound) {
                players.get(subRound).sherifOn();
                int sheriffID = subRound;
                for (Player p : players) {
                    if (p.getID() != subRound) {
                        // Cards drawn form the deck
                        playerDeck = gameInput.getAssetIds().subList(cardIndex, cardIndex + 10);
                        // Cards chose by a player
                        p.setCards(playerDeck);
                        cardIndex += 10;
                        // Searching process
                        players.get(sheriffID).playerSearch(p);
                        List<Goods> test = players.get(0).getAssets();
                    }
                }
                players.get(subRound).sherifOff();
            }
        }
        ScoreBoard.updateFinalScores(players);
        ScoreBoard.finalBouns(players);
        ScoreBoard.printScoreBoard(players);

    }
}
