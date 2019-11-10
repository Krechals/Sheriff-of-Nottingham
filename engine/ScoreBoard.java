package engine;

import common.Constants;
import goods.Goods;
import goods.GoodsFactory;
import goods.LegalGoods;

import java.util.Collections;
import java.util.List;

public final class ScoreBoard {
    private ScoreBoard() {
        // NOT CALLED
    }

    public static void updateFinalScores(final List<Player> players) {
        for (Player p : players) {
            p.finalScore();
        }
    }

    // Get King + Queen Bonus for each player
    public static void finalBouns(final List<Player> players) {

        for (int goodID = 0; goodID <= Constants.LEGAL_CARDS_ID_FINISH; ++goodID) {
            int kingID = 0;
            int kingGoodSize = 0;
            int queenID = 0;
            int queenGoodSize = 0;
            for (Player p : players) {
                int size = 0;
                for (Goods asset : p.getAssetsBrought()) {
                    if (asset.getId() == goodID) {
                        ++size;
                    }
                }
                if (size > kingGoodSize) {
                    queenGoodSize = kingGoodSize;
                    queenID = kingID;
                    kingGoodSize = size;
                    kingID = p.getID();
                } else if (size > queenGoodSize) {
                    queenGoodSize = size;
                    queenID = p.getID();
                }
            }
            LegalGoods assetBonus = (LegalGoods) GoodsFactory.getInstance().getGoodsById(goodID);
            if (kingGoodSize > 0) {
                players.get(kingID).addScore(assetBonus.getKingBonus());
            }
            if (queenGoodSize > 0) {
                players.get(queenID).addScore(assetBonus.getQueenBonus());
            }
        }
    }
    // Print ScoreBoard given all players at the table
    public static void printScoreBoard(final List<Player> players) {
        PlayerComparator playerComparator = new PlayerComparator();
        Collections.sort(players, playerComparator);
        for (Player p : players) {
            System.out.println(p.getID() + " " + p.getStrategy().printStrategy()
                                + " " + p.getScore());
        }
    }
}
