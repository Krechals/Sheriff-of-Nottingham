package engine;
import strategy.*;
import goods.Goods;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private int score;
    private boolean isSherif;
    private Strategy gameAproach;
    private List<Goods> assets = new ArrayList<>();

    public Player(int id, StrategyList strategy) {
        this.score = 0;
        this.id = id;
        this.gameAproach = StrategyFactory.INSTANCE.createStrategy(strategy);
        isSherif = false;
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
    public void setCards(List<Integer> cardIDs) {
        assets = gameAproach.createBag(cardIDs);
    }
    public void sherifOn() {
        isSherif = true;
    }
    public void sherifOff() {
        isSherif = false;
    }
}
