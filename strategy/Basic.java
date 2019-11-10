package strategy;

import common.Constants;
import engine.BasicCardComparator;
import engine.CardDrawn;
import engine.Player;
import goods.Goods;
import goods.GoodsFactory;
import goods.GoodsType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Basic implements Strategy {
    @Override
    public final List<Goods> createBag(final List<Integer> cardIDs,
                                       final int roundID, final Player p) {
        List<Goods> ans = new ArrayList<>();
        List<CardDrawn> cardsDrawn = new ArrayList<>();
        GoodsFactory cardMap = GoodsFactory.getInstance();
        BasicCardComparator basicCardComparator = new BasicCardComparator();
        int[] freq = new int[Constants.CARD_ID_RANGE];
        Arrays.fill(freq, 0);

        // Get frequency of cards
        for (Integer cardID : cardIDs) {
            ++freq[cardID];
        }
        for (Integer cardID : cardIDs) {
            if (freq[cardID] > 0) {
                CardDrawn card = new CardDrawn(freq[cardID], cardMap.getGoodsById(cardID));
                cardsDrawn.add(card);
                freq[cardID] = 0;
            }
        }
        // Sorting cards accoring to Basic's strategy of adding cards
        Collections.sort(cardsDrawn, basicCardComparator);

        // Basic's adding cards strategy
        CardDrawn firstCard = cardsDrawn.get(0);
        int cardFreq = firstCard.getFreq();
        if (firstCard.getAsset().getType() == GoodsType.Legal) {
            while (ans.size() < Constants.MAX_GOODS_ALLOWED && cardFreq > 0) {
                ans.add(firstCard.getAsset());
                --cardFreq;
            }
        } else {
            ans.add(firstCard.getAsset());
        }
        return ans;
    }
    public final Goods declareAsset(final List<Goods> assets) {
        Goods ans;
        if (assets.get(0).getType() == GoodsType.Illegal) {
            // Declare apples
            ans = GoodsFactory.getInstance().getGoodsById(0);
        } else {
            ans = assets.get(0);
        }
        return ans;
    }
    public final void dontSearch(final Player p) {
        List<Goods> playerAssets = p.getAssets();
        List<Goods> playerAssetsBrought = p.getAssetsBrought();

        for (Goods asset : playerAssets) {
            playerAssetsBrought.add(asset);
        }
    }
    // Returns penalty/earning after searching
    public final int search(final Player p, final int sheriffID,
                      final int playerNumber, final int sheriffScore) {
        int scoreNegative = 0;
        int scorePositive = 0;
        // List<Integer> deck = GameInput.getAssetIds();
        List<Goods> playerAssets = p.getAssets();
        List<Goods> playerAssetsBrought = p.getAssetsBrought();

        // Don't search a player if sheriff does NOT have enough money
        if (sheriffScore < Constants.BRIBE_MINIMUM_SCORE) {
            dontSearch(p);
            return 0;
        }
        for (Goods asset : playerAssets) {
            if (asset.getType() == GoodsType.Illegal
                    || asset.getId() != p.getAssetDeclared().getId()) {
                scoreNegative += asset.getPenalty();
                if (playerAssets.isEmpty()) {
                    break;
                }
            } else {
                scorePositive += asset.getPenalty();
                playerAssetsBrought.add(asset);
            }
        }
        if (scoreNegative > 0) {
            p.addScore(-scoreNegative);
            return scoreNegative;
        }
        p.addScore(scorePositive);
        return -scorePositive;
    }
    public final String printStrategy() {
        return "BASIC";
    }
}
