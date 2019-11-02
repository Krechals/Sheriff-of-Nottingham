package strategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

import engine.CardComparator;
import engine.CardDrawn;
import goods.Goods;
import goods.GoodsFactory;
import goods.GoodsType;

import javax.smartcardio.Card;

public class Basic implements Strategy {
    @Override
    public List<Goods> createBag(List<Integer> cardIDs) {
        List<Goods> ans = new ArrayList<>();
        List<CardDrawn> cardsDrawn = new ArrayList<>();
        GoodsFactory cardMap = GoodsFactory.getInstance();
        CardComparator cardComparator = new CardComparator();
        int[] freq = new int[30];
        Arrays.fill(freq, 0);
        for (Integer cardID : cardIDs) {
            ++freq[cardID];
        }
        for (Integer cardID : cardIDs) {
            if (freq[cardID] > 0) {
                CardDrawn card = new CardDrawn(freq[cardID], cardMap.getGoodsById(cardID));
                cardsDrawn.add(card);
                freq[cardID] = 0;
            }
        }
        Collections.sort(cardsDrawn, cardComparator);

        // TODO : case of no legal card
        CardDrawn firstCard = cardsDrawn.get(0);
        int cardFreq = firstCard.getFreq();
        while (ans.size() < 8 && cardFreq > 0) {
            ans.add(firstCard.getAsset());
            --cardFreq;
        }

        return ans;
    }
}
