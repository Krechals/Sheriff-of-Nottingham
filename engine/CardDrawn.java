package engine;

import goods.Goods;

public class CardDrawn {
    private int freq;
    private Goods asset;

    public CardDrawn(final int freq, final Goods type) {
        this.freq = freq;
        this.asset = type;
    }
    public final int getFreq() {
        return freq;
    }
    public final Goods getAsset() {
        return asset;
    }
    public final void setFreq(final int freq) {
        this.freq = freq;
    }

}
