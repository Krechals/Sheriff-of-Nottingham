package strategy;

import common.Constants;
import engine.BasicCardComparator;
import engine.BribeCardComparator;
import engine.CardDrawn;
import engine.Player;
import goods.Goods;
import goods.GoodsFactory;
import goods.GoodsType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Bribe implements Strategy {
    public final List<Goods> createBag(final List<Integer> cardIDs, final int roundID,
                                 final int score, final Player p) {
        List<Goods> ans = new ArrayList<>();
        List<CardDrawn> cardsDrawn = new ArrayList<>();
        GoodsFactory cardMap = GoodsFactory.getInstance();
        BribeCardComparator bribeCardComparator = new BribeCardComparator();
        boolean illegalCardFlag = false;
        int[] freq = new int[Constants.CARD_ID_RANGE];
        Arrays.fill(freq, 0);
        for (Integer cardID : cardIDs) {
            ++freq[cardID];
            if (cardMap.getGoodsById(cardID).getType() == GoodsType.Illegal) {
                illegalCardFlag = true;
            }
        }
        for (Integer cardID : cardIDs) {
            if (freq[cardID] > 0) {
                CardDrawn card = new CardDrawn(freq[cardID], cardMap.getGoodsById(cardID));
                cardsDrawn.add(card);
                freq[cardID] = 0;
            }
        }
        /**
         * Do Basic Strategy if there are no illegal cards
         */
        if (!illegalCardFlag || p.getScore() <= Constants.BRIBE_MINIMUM_RISK) {
            BasicCardComparator basicCardComparator = new BasicCardComparator();
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
            }
            p.setGoodDeclared(firstCard.getAsset());
            p.setBribe(0);
            return ans;
        }
        /**
         * Do Bribe Strategy
         */
        Collections.sort(cardsDrawn, bribeCardComparator);

        int cardIndex = 0;
        int potentialScore = score;
        int nrIllegalCards = 0;
        while (ans.size() < Constants.MAX_GOODS_ALLOWED && cardIndex < cardsDrawn.size()) {
            Goods potentialAsset = cardsDrawn.get(cardIndex).getAsset();
            if (potentialAsset.getPenalty() < potentialScore) {
                ans.add(potentialAsset);
                potentialScore -= potentialAsset.getPenalty();
                if (potentialAsset.getId() >= Constants.ILLEGAL_CARDS_ID_START) {
                    ++nrIllegalCards;
                }
            }

            int cardFreq = cardsDrawn.get(cardIndex).getFreq();
            --cardFreq;
            cardsDrawn.get(cardIndex).setFreq(cardFreq);
            if (cardFreq == 0) {
                ++cardIndex;
            }
        }
        if (nrIllegalCards == 0) {
            p.setBribe(0);
        } else if (nrIllegalCards <= Constants.LOW_BRIBE_NUMBER) {
            p.setBribe(Constants.LOW_BRIBE_SUM);
        } else {
            p.setBribe(Constants.HIGH_BRIBE_SUM);
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
    public final int search(final Player p, final int sheriffID,
                      final int playerNumber, final int sheriffScore) {
        int scoreNegative = 0;
        int scorePositive = 0;
        // List<Integer> deck = GameInput.getAssetIds();
        List<Goods> playerAssets = p.getAssets();
        List<Goods> playerAssetsBrought = p.getAssetsBrought();

        // Search ONLY the player's near the sheriff
        int sheriffLeftID = sheriffID - 1;
        int sheriffRightID = sheriffID + 1;

        // The player table is round -> playerID = 0 has the leftPlyaerID = player.size()
        if (sheriffLeftID == -1) {
            sheriffLeftID = playerNumber - 1;
        }
        if (sheriffRightID == playerNumber) {
            sheriffRightID = 0;
        }
        // System.out.println(sheriffID + " " + sheriffScore);
        if ((p.getID() == sheriffLeftID || p.getID() == sheriffRightID)
                && sheriffScore >= Constants.BRIBE_MINIMUM_SCORE) {
            // Search a player unless he gives bribe
            // Use the basic strategy
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
        } else {
            dontSearch(p);
            // Get bribe from players
            if (sheriffScore >= Constants.BRIBE_MINIMUM_SCORE) {
                p.addScore(-p.getBribe());
                return p.getBribe();
            } else if (p.getID() != sheriffLeftID && p.getID() != sheriffRightID) {
                p.addScore(-p.getBribe());
                return p.getBribe();
            }
            return 0;
        }
    }
    public final Goods declareAsset(final List<Goods> assets) {
        Goods ans;

        if (assets.isEmpty() || assets.get(0).getType() == GoodsType.Illegal) {
            // Declare apples
            ans = GoodsFactory.getInstance().getGoodsById(0);
        } else {
            ans = assets.get(0);
        }
        return ans;
    }
    public final String printStrategy() {
        return "BRIBED";
    }
}
