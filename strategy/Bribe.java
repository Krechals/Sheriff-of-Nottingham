package strategy;

import engine.Player;
import goods.Goods;
import goods.GoodsFactory;

import java.util.ArrayList;
import java.util.List;

public class Bribe implements Strategy {
    public List<Goods> createBag(List<Integer> cardIDs) {
        List<Goods> ans = new ArrayList<>();
        return ans;
    }
    public int searchBasic(Player p) {
        return 0;
    }
    public Goods declareAsset(List<Goods> assets) {
        return GoodsFactory.getInstance().getGoodsById(0);
    }
    public String printStrategy() {
        return "BRIBE";
    }
}
