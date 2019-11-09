package engine;

import common.Constants;
import goods.Goods;
import goods.GoodsFactory;
import goods.GoodsType;
import goods.IllegalGoods;
import strategy.Strategy;
import strategy.StrategyFactory;
import strategy.StrategyList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private int id;
    private int score;
    private int roundScore;
    private Strategy gameAproach;
    private Goods assetDeclared;
    private int bribe;
    private List<Goods> assetsBrought = new ArrayList<>();
    private List<Goods> assets = new ArrayList<>();

    public Player(final int id, final StrategyList strategy) {
        this.score = Constants.STARTING_SCORE;
        this.id = id;
        this.gameAproach = StrategyFactory.INSTANCE.createStrategy(strategy);
        // Declare Apples by Default
        this.assetDeclared = GoodsFactory.getInstance().getGoodsById(0);
        this.bribe = 0;
    }
    public final int getID() {
        return id;
    }
    public final void setRoundScore(final int roundScore) {
        this.roundScore = roundScore;
    }
    public final int getRoundScore() {
        return roundScore;
    }
    public final List<Goods> getAssets() {
        return assets;
    }
    final Strategy getStrategy() {
        return gameAproach;
    }
    public final void setCards(final List<Integer> cardIDs, final int roundID) {
        assets = gameAproach.createBag(cardIDs, roundID, score, this);
        assetDeclared = gameAproach.declareAsset(assets);
    }
    public final Goods getAssetDeclared() {
        return assetDeclared;
    }
    public final int getScore() {
        return score;
    }
    public final void setBribe(final int bribe) {
        this.bribe = bribe;
    }
    public final int getBribe() {
        return bribe;
    }
    public final List<Goods> getAssetsBrought() {
        return assetsBrought;
    }

    public final void addScore(final int addedScore) {
        score += addedScore;
    }
    public final void setGoodDeclared(final Goods type) {
        assetDeclared = type;
    }
    public final void playerSearch(final Player p, final int playerNumber) {
        int searchScore = gameAproach.search(p, id, playerNumber, score);
        roundScore += searchScore;
    }
    final void finalScore() {
        List<Goods> bonus = new ArrayList<>();
        for (Goods asset : assetsBrought) {
            if (asset.getType() == GoodsType.Illegal) {
                IllegalGoods illegalAsset = (IllegalGoods) asset;
                Map<Goods, Integer> illegalBonus = illegalAsset.getIllegalBonus();

                for (Goods assetBonus : illegalBonus.keySet()) {
                    int bonusGoodSize = illegalBonus.get(assetBonus);
                    for (int i = 0; i < bonusGoodSize; ++i) {
                        bonus.add(assetBonus);
                        score += assetBonus.getProfit();
                    }
                }
            }

            score += asset.getProfit();
        }
        // Add bonuses to the table
        for (Goods asset : bonus) {
            assetsBrought.add(asset);
        }
    }
}
