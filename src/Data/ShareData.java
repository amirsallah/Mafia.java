package Data;

import java.io.IOException;
import java.util.ArrayList;

public class ShareData {
    private static ArrayList<Player> players;
    private static ArrayList<Role> roles;
    private static ArrayList<Player> deadPlayer;

    public static void AddPlayer(Player player) {
        players.add(player);
    }

    public static void AddRole(Role role) {
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

    public static Player givePlayerByName(String name) {
        for (Player p : players) {
            if (p.getUsername().equals(name))
                return p;
        }
        return null;
    }

    public static Player giveGoal(Player player) throws IOException {
        return player.selectPlayer();
    }

    public static Boolean yesOrNo(Player player) throws IOException {
        return player.yesOrNo();
    }

    public static ArrayList<Player> getDeadPlayer() {
        return deadPlayer;
    }

    public static void removePlayer(Player player) {
        players.remove(player);
    }

    public static void sendToPlayer(Player player, String str) throws IOException {
        player.send(str);
    }

    public static void allSendMassage(String str) throws IOException {
        for (Player p : players) {
            p.send(str);
        }
        for (Player p: deadPlayer) {
            p.send(str);
        }
    }

    public static void talkDown(Player player){
        player.setSilence(true);
    }

    public static void talkUp(Player player){
        player.setSilence(false);
    }

    public static void addDeadPlayer(Player player){
        deadPlayer.add(player);
    }

    public static void setDeadPlayer(ArrayList<Player> deadPlayer) {
        ShareData.deadPlayer = deadPlayer;
    }

    public static String readMassage(Player player) throws IOException {
        return player.read();
    }

    public static Player vote(Player player) throws IOException {
        String str = player.setVoteItself();
        for (Player p: players) {
            if (str.equals(p.getUsername()))
                return p;
        }
         return null;
    }

    public static void setItVotes(Player player){
        player.setVotes();
    }
}
