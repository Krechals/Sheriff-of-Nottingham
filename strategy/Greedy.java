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

public class Greedy implements Strategy {
    @Override
    public final List<Goods> createBag(final List<Integer> cardIDs, final int roundID,
                                 final int score, final Player p) {
        List<Goods> ans = new ArrayList<>();
        List<CardDrawn> cardsDrawn = new ArrayList<>();
        GoodsFactory cardMap = GoodsFactory.getInstance();
        BasicCardComparator basicCardComparator = new BasicCardComparator();
        int[] freq = new int[Constants.CARD_ID_RANGE];
        Arrays.fill(freq, 0);
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
        Collections.sort(cardsDrawn, basicCardComparator);
        CardDrawn firstCard = cardsDrawn.get(0);
        int cardFreq = firstCard.getFreq();
        if (firstCard.getAsset().getType() == GoodsType.Legal) {
            while (ans.size() < Constants.MAX_GOODS_ALLOWED && cardFreq > 0) {
                ans.add(firstCard.getAsset());
                --cardFreq;
            }
        } else {
            ans.add(firstCard.getAsset());
            --cardFreq;
        }
        // Greedy Strategy
        if (roundID % 2 == 0 && firstCard.getAsset().getType() == GoodsType.Legal) {
            for (CardDrawn card : cardsDrawn) {
                Goods asset = card.getAsset();
                if (asset.getType() == GoodsType.Illegal) {
                    ans.add(asset);
                    break;
                }
            }
        } else if (roundID % 2 == 0 && firstCard.getAsset().getType() == GoodsType.Illegal) {
            if (cardFreq > 0) {
                ans.add(cardsDrawn.get(0).getAsset());
            } else {
                ans.add(cardsDrawn.get(1).getAsset());
            }
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
        int scorePositive = 0;
        int scoreNegative = 0;
        // List<Integer> deck = GameInput.getAssetIds();
        List<Goods> playerAssets = p.getAssets();
        List<Goods> playerAssetsBrought = p.getAssetsBrought();

        // Search a player unless he gives bribe
        if (p.getBribe() > 0 || sheriffScore < Constants.BRIBE_MINIMUM_SCORE) {
            p.addScore(-p.getBribe());
            dontSearch(p);
            return p.getBribe();
        }
        for (Goods asset : playerAssets) {
            if (asset.getType() == GoodsType.Illegal
                    || asset.getId() != p.getAssetDeclared().getId()) {
                scoreNegative += asset.getPenalty();
                // Confiscation
                // playerAssets.remove(assetIndex);
                if (playerAssets.isEmpty()) {
                    break;
                }
                // deck.add(assets.getId());
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
        return "GREEDY";
    }
}
