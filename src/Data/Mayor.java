package Data;

import java.io.IOException;

public class Mayor extends Citizen{
    public Player GiveThePlayerRole(){
        for (Player p: ShareData.getPlayers()) {
            if (p.getRole() instanceof Mayor)
                return p;
        }
        return null;
    }
    public Boolean yesOrNo() throws IOException {
        Player player =GiveThePlayerRole();
        return ShareData.yesOrNo(player);
    }
}
