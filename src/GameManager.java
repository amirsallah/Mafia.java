import Data.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameManager {
    private ArrayList<Role> roles;

    public GameManager(){
        roles = ShareData.getRoles();
    }

    public void dayHandler(){

    }

    public void nightHandler(){

    }

    public void voteHandler(){

    }

    public void introductionNightHandler() throws IOException {
        ArrayList<Role> mafias = findMafia();
        ArrayList<Player> players = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (Role r: mafias) {
            players.add(r.GiveThePlayerRole());
             str.append(r.GiveThePlayerRole().getUsername()).append(r.GiveThePlayerRole().getRole().toString()).append("\n");
        }
        for (Player p: players) {
            ShareData.sendToPlayer(p,str);
        }
        }


    public Role findGodFather(){
        for (Role r: roles) {
            if (r instanceof GodFather)
                return r;
        }
        return null;
    }

    public Role findDr_Lecter(){
        for (Role r: roles) {
            if (r instanceof Dr_Lecter)
                return r;
        }
        return null;
    }

    public ArrayList<Role> findMafia(){
        ArrayList<Role> Mafia = new ArrayList<>();
        for (Role r: roles) {
            if (r instanceof SimpleMafia || r instanceof Dr_Lecter || r instanceof GodFather){
                Mafia.add(r);
            }
        }
        return Mafia;
    }
}
