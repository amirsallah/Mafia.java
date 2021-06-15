package Data;

import java.io.IOException;

public class Dr_Lector extends Mafia implements Move {
    public Player GiveThePlayerRole(){
        for (Player p: ShareData.getPlayers()) {
            if (p.getRole() instanceof Dr_Lector)
                return p;
        }
        return null;
    }
    public Player selectGoal() throws IOException {
        Player player = GiveThePlayerRole();
        return selectTarget(player);
    }
}
