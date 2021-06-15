import Data.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameManager {
    private final ArrayList<Role> roles;

    public GameManager() {
        roles = ShareData.getRoles();
    }

    public void dayHandler() throws IOException {
        ShareData.allSendMassage("It was morning\n" +
                "Open your eyes");

    }

    public void voteHandler() {

    }

    public void nightHandler() throws IOException {
        ShareData.allSendMassage("It is night to close your eyes;)");
        ArrayList<Player> playersRole = findAllRolePlayers();


    }

    public void introductionNightHandler() throws IOException {
        ShareData.allSendMassage("It is night to close your eyes;)");
        ArrayList<Role> mafias = findMafia();
        ArrayList<Player> players = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (Role r : mafias) {
            players.add(r.GiveThePlayerRole());
            str.append(r.GiveThePlayerRole().getUsername()).append(r.GiveThePlayerRole().getRole().toString()).append("\n");
        }
        for (Player p : players) {
            ShareData.sendToPlayer(p, str.toString());
        }
    }


    public Role findGodFather() {
        for (Role r : roles) {
            if (r instanceof GodFather)
                return r;
        }
        return null;
    }

    public Role findDr_Lector() {
        for (Role r : roles) {
            if (r instanceof Dr_Lector)
                return r;
        }
        return null;
    }

    public ArrayList<Role> findMafia() {
        ArrayList<Role> Mafia = new ArrayList<>();
        for (Role r : roles) {
            if (r instanceof SimpleMafia || r instanceof Dr_Lector || r instanceof GodFather) {
                Mafia.add(r);
            }
        }
        return Mafia;
    }

    public ArrayList<Player> findAllRolePlayers(){
        ArrayList<Player> players = new ArrayList<>();
        for (Role r: ShareData.getRoles()) {
            players.add(r.GiveThePlayerRole());
        }
        return players;
    }

}
