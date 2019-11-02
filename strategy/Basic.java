package strategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import engine.BasicCardComparator;
import engine.CardDrawn;
import engine.Player;
import goods.Goods;
import goods.GoodsFactory;
import goods.GoodsType;


public class Basic implements Strategy {
    @Override
    public List<Goods> createBag(List<Integer> cardIDs) {
        List<Goods> ans = new ArrayList<>();
        List<CardDrawn> cardsDrawn = new ArrayList<>();
        GoodsFactory cardMap = GoodsFactory.getInstance();
        BasicCardComparator basicCardComparator = new BasicCardComparator();
        int[] freq = new int[30];
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
            while (ans.size() < 8 && cardFreq > 0) {
                ans.add(firstCard.getAsset());
                --cardFreq;
            }
        } else {
            ans.add(firstCard.getAsset());
        }
        return ans;
    }
    public Goods declareAsset(List<Goods> assets) {
        Goods ans;
        if (assets.get(0).getType() == GoodsType.Illegal) {
            // Declare apples
            ans = GoodsFactory.getInstance().getGoodsById(0);
        } else {
            ans = assets.get(0);
        }
        return ans;
    }
    // TODO: case of searching greedy / bribe
    // Returns penalty/earning after searching
    public int searchBasic(Player p) {
        int score = 0;
        // List<Integer> deck = GameInput.getAssetIds();
        List<Goods> playerAssets = p.getAssets();
        List<Goods> playerAssetsBrought = p.getAssetsBrought();
        int assetIndex = 0;
        for (Goods asset : playerAssets) {
            if (asset.getType() == GoodsType.Illegal || asset.getId() != p.getAssetDeclared().getId()) {
                score += asset.getPenalty();
                // Confiscation
                playerAssets.remove(assetIndex);
                ++assetIndex;
                break;
                // deck.add(assets.getId());
            } else {
                score -= asset.getPenalty();
                playerAssetsBrought.add(asset);
            }
        }
        p.addScore(-score);
        return score;
    }
    public String printStrategy() {
        return "BASIC";
    }
}
