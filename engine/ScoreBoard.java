package engine;

import goods.Goods;
import goods.GoodsFactory;
import goods.GoodsType;
import goods.LegalGoods;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoard {

    public static void updateFinalScores(List<Player> players) {
        for (Player p : players) {
            p.finalScore();
        }
    }

    public static void finalBouns(List<Player> players) {

        // 0-9 -> number of all legal goods
        for (int goodID = 0; goodID <= 9; ++goodID) {
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
            LegalGoods assetBonus = (LegalGoods)GoodsFactory.getInstance().getGoodsById(goodID);
            if (kingGoodSize > 0) {
                players.get(kingID).addScore(assetBonus.getKingBonus());
            }
            if (queenGoodSize > 0) {
                players.get(queenID).addScore(assetBonus.getQueenBonus());
            }
        }
    }

    public static void printScoreBoard(List<Player> players) {
        PlayerComparator playerComparator = new PlayerComparator();
        Collections.sort(players, playerComparator);
        for (Player p : players) {
            System.out.println(p.getID() + " " + p.getStrategy().printStrategy() + " " + p.getScore());
        }
    }
}
