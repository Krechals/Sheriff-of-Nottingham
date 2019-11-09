package strategy;

import engine.Player;
import goods.Goods;

import java.util.List;

public interface Strategy {
    List<Goods> createBag(List<Integer> cardIDs, int roundID, int score, Player p);
    int search(Player p, int sheriffID, int playerNumber, int sheriffScore);
    Goods declareAsset(List<Goods> assets);
    String printStrategy();
    void dontSearch(Player p);
}
