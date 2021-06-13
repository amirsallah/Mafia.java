package Data;

import java.io.IOException;

public class Dr_Lecter extends Mafia implements Move {
    public Player GiveThePlayerRole(){
        for (Player p: ShareData.getPlayers()) {
            if (p.getRole() instanceof Doctor)
                return p;
        }
        return null;
    }
    public Player selectGoal() throws IOException {
        Player player = GiveThePlayerRole();
        return selectTarget(player);
    }
}
