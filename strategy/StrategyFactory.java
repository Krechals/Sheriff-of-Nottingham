package strategy;

public final class StrategyFactory {

    // Singleton Design Pattern
    public static final StrategyFactory INSTANCE = new StrategyFactory();

    private StrategyFactory() { }

    // Factory Design Pattern
    public Strategy createStrategy(final StrategyList strategy) {
        if (strategy == StrategyList.BASIC) {
            return new Basic();
        } else if (strategy == StrategyList.GREEDY) {
            return new Greedy();
        }
        return new Bribe();
    }

}
