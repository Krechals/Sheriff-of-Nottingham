package strategy;

public class StrategyFactory {
    public static final StrategyFactory INSTANCE = new StrategyFactory();

    private StrategyFactory() { }

    public Strategy createStrategy(StrategyList strategy) {
        if (strategy == StrategyList.BASIC) {
            return new Basic();
        } else if (strategy == StrategyList.GREEDY) {
            return new Greedy();
        }
        return new Bribe();
    }

}
