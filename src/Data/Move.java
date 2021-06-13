package Data;

import java.io.IOException;

public interface Move {
    default Player selectTarget(Player player) throws IOException {
        if (player == null)
            return null;
        return ShareData.giveGoal(player);
    }
}
