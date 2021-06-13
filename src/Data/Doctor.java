package Data;

import java.io.IOException;

public class Doctor extends Citizen implements Move{
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
