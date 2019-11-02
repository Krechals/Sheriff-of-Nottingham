package main;

import engine.Player;
import goods.Goods;
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
        for (int round = 0; round < gameInput.getRounds(); ++round) {
            for (int subRound = 0; subRound < nrPlayers; ++subRound) {
                players.get(subRound).sherifOn();
                for (Player p : players) {
                    if (p.getID() != subRound) {
                        playerDeck = gameInput.getAssetIds().subList(cardIndex, cardIndex + 9);
                        System.out.println(playerDeck);
                        p.setCards(playerDeck);
                        cardIndex += 10;
                        List<Goods> test = players.get(0).getAssets();
                        System.out.println(p.getStrategy());
                    }
                }
            }
        }
    }
}
