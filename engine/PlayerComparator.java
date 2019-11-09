package engine;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {

    @Override
    public final int compare(final Player p1, final Player p2) {
        if (p1.getScore() == p2.getScore()) {
            return p1.getID() - p2.getID();
        }
        return p2.getScore() - p1.getScore();
    }
}
