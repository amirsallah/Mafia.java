package Data;

import java.io.IOException;
import java.util.ArrayList;

public class ShareData {
    private static ArrayList<Player> players;
    private static ArrayList<Role> roles;

    public static void AddPlayer(Player player) {
        players.add(player);
    }

    public static void AddRole(Role role){
        roles.add(role);
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public static void setPlayers(ArrayList<Player> players) {
        ShareData.players = players;
    }

    public static ArrayList<Role> getRoles() {
        return roles;
    }

    public static void setRoles(ArrayList<Role> roles) {
        ShareData.roles = roles;
    }

    public static Player givePlayerByName(String name){
        for (Player p: players) {
            if (p.getUsername().equals(name))
                return p;
        }
        return null;
    }

    public static Player giveGoal(Player player) throws IOException {return player.selectPlayer();}

    public static Boolean yesOrNo(Player player) throws IOException { return player.yesOrNo();}

    public static void removePlayer(){}

    public static void sendToPlayer(Player player, StringBuilder str) throws IOException {
        player.send(str);
    }

}
