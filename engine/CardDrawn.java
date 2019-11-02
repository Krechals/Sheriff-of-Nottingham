package engine;

import goods.Goods;

public class CardDrawn {
    private int freq;
    private Goods asset;

    public CardDrawn(int freq, Goods type) {
        this.freq = freq;
        this.asset = type;
    }
    public int getFreq() {
        return freq;
    }
    public Goods getAsset() {
        return asset;
    }

}
