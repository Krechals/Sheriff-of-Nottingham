package strategy;
import java.util.List;

import engine.Player;
import goods.Goods;

public interface Strategy {
    List<Goods> createBag(List<Integer> cardIDs, int roundID);
    int searchBasic(Player p);
    Goods declareAsset(List<Goods> assets);
    String printStrategy();
}
