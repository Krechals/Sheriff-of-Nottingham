package engine;

abstract class CardComparator {
    // Illegal cards comparison
    final int compareIllegal(final CardDrawn c1, final CardDrawn c2) {
        if (c1.getAsset().getProfit() == c2.getAsset().getProfit()) {
            return c2.getAsset().getId() - c1.getAsset().getId();
        }
        return c2.getAsset().getProfit() - c1.getAsset().getProfit();
    }
    // Legal cards comparison
    abstract int compareLegal(CardDrawn c1, CardDrawn c2);
}
