package engine;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public int compare(Player p1, Player p2) {
        if (p1.getScore() == p2.getScore()) {
            return p1.getID() - p2.getID();
        }
        return p2.getScore() - p1.getScore();
    }
}
