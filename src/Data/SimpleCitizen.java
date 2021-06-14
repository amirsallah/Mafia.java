package Data;

public class SimpleCitizen extends Citizen{
    public Player GiveThePlayerRole(){
        for (Player p: ShareData.getPlayers()) {
            if (p.getRole() instanceof SimpleCitizen)
                return p;
        }
        return null;
    }
}
