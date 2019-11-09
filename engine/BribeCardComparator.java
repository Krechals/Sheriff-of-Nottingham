package engine;

import goods.GoodsType;

import java.util.Comparator;

public class BribeCardComparator implements Comparator<CardDrawn> {

    @Override
    public final int compare(final CardDrawn c1, final CardDrawn c2) {
        if (c1.getAsset().getType() == GoodsType.Legal
                && c2.getAsset().getType() == GoodsType.Legal) {
            if (c1.getAsset().getProfit() == c2.getAsset().getProfit()) {
                return c2.getAsset().getId() - c1.getAsset().getId();
            }
            return c2.getAsset().getProfit() - c1.getAsset().getProfit();
        }
        if (c1.getAsset().getType() == GoodsType.Illegal
                && c2.getAsset().getType() == GoodsType.Illegal) {
            if (c1.getAsset().getProfit() == c2.getAsset().getProfit()) {
                return c2.getAsset().getId() - c1.getAsset().getId();
            }
            return c2.getAsset().getProfit() - c1.getAsset().getProfit();
        }
        if (c1.getAsset().getType() == GoodsType.Legal
                && c2.getAsset().getType() == GoodsType.Illegal) {
            return 1;
        }
        if (c1.getAsset().getType() == GoodsType.Illegal
                && c2.getAsset().getType() == GoodsType.Legal) {
            return -1;
        }
        return 1;
    }
}
