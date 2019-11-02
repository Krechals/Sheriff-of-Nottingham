package strategy;
import java.util.List;
import goods.Goods;

public interface Strategy {
    List<Goods> createBag(List<Integer> cardIDs);
}
