package strategy;

import engine.Player;
import goods.Goods;

import java.util.List;

public interface Strategy {
    /**
     * Create player's bag.
     *
     * @param cardIDs : List of cards in the player's hand
     * @param roundID : Index of the current round
     * @param p : The searched player
     * @return List of assets in the bag
     */
    List<Goods> createBag(List<Integer> cardIDs, int roundID, Player p);

    /**
     * Serach player's bag.
     * @param p : Player to be searched
     * @param sheriffID : Sheriff's player ID
     * @param playerNumber : Player number at the table
     * @param sheriffScore : Sheriff's score before the sub-round
     * @return Penalty/Profit of the sheriff during the sub-round
     */
    int search(Player p, int sheriffID, int playerNumber, int sheriffScore);

    /**
     * Declare player's asset.
     * @param assets Assets in the bag
     * @return Asset delcared
     */
    Goods declareAsset(List<Goods> assets);

    // Returns strategy name
    String printStrategy();

    // Don't search a player
    void dontSearch(Player p);
}
