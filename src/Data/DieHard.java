package Data;

import java.io.IOException;

public class DieHard extends Citizen {
    private Boolean armor = true;
    public Player GiveThePlayerRole(){
        for (Player p: ShareData.getPlayers()) {
            if (p.getRole() instanceof DieHard)
                return p;
        }
        return null;
    }
    public Boolean yesOrNo() throws IOException {
        Player player =GiveThePlayerRole();
        return ShareData.yesOrNo(player);
    }

    public Boolean getArmor() {
        return armor;
    }

    public void setArmor(Boolean armor) {
        this.armor = armor;
    }
}
