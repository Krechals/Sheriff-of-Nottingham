package engine;
import goods.GoodsFactory;
import goods.GoodsType;
import goods.IllegalGoods;
import strategy.*;
import goods.Goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private int id;
    private int score;
    private boolean isSherif;
    private Strategy gameAproach;
    private Goods assetDeclared;
    private int assetCount;
    private List<Goods> assetsBrought = new ArrayList<>();
    private List<Goods> assets = new ArrayList<>();

    public Player(int id, StrategyList strategy) {
        this.score = 80;
        this.id = id;
        this.gameAproach = StrategyFactory.INSTANCE.createStrategy(strategy);
        this.assetDeclared = GoodsFactory.getInstance().getGoodsById(0); // Declare Apples by Default;
        this.isSherif = false;
    }
    public int getID() {
        return id;
    }
    public List<Goods> getAssets() {
        return assets;
    }
    public Strategy getStrategy() {
        return gameAproach;
    }
    public void setCards(List<Integer> cardIDs, int roundID) {
        assets = gameAproach.createBag(cardIDs, roundID);
        assetDeclared = gameAproach.declareAsset(assets);
        assetCount = assets.size();
    }
    public int getAssetCount() {
        return assetCount;
    }
    public Goods getAssetDeclared() {
        return assetDeclared;
    }
    public int getScore() {
        return score;
    }
    public List<Goods> getAssetsBrought() {
        return assetsBrought;
    }
    public void addAssetsBrought(Goods asset) {
        assetsBrought.add(asset);
    }
    public void addScore(int addedScore) {
        score += addedScore;
    }
    public void sherifOn() {
        isSherif = true;
    }
    public void sherifOff() {
        isSherif = false;
    }
    public void setGoodDeclared(Goods type) {
        assetDeclared = type;
    }
    public void removeAsset(int index) {
        assets.remove(index);
    }
    public void playerSearch(Player p) {
        if (p.getStrategy() instanceof Basic) {
            int searchScore = gameAproach.searchBasic(p);
            score += searchScore;
        }
        if (p.getStrategy() instanceof Greedy) {
            int searchScore = gameAproach.searchBasic(p);
            score += searchScore;
        }
        if (p.getStrategy() instanceof Bribe) {
            int searchScore = gameAproach.searchBasic(p);
            score += searchScore;
        }
    }
    public void finalScore() {
        for (Goods asset : assetsBrought) {
            if (asset.getType() == GoodsType.Illegal) {
                IllegalGoods illegalAsset = (IllegalGoods) asset;
                Map<Goods, Integer> illegalBonus = illegalAsset.getIllegalBonus();
                // TODO: define consts
                for (int id = 20; id <= 24; ++id) {
                    Goods assetBonus = GoodsFactory.getInstance().getGoodsById(id);
                    int bonusSize = illegalBonus.get(assetBonus);
                    while (bonusSize > 0) {
                        assetsBrought.add(assetBonus);
                        --bonusSize;
                    }
                }
            }
            score += asset.getProfit();
        }
    }
}
