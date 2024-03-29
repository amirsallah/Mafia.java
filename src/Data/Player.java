package Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    private Socket socket;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final String username;
    private Role role;
    private Boolean live;
    private Integer votes;
    private Boolean silence;

    public Player(String username, Socket socket) throws IOException {
        this.socket = socket;
        this.dataInputStream = new DataInputStream(socket.getInputStream());
        this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        this.username = username;
        this.silence = false;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes() {
        ++votes;
    }

    public String setVoteItself() throws IOException {
        dataOutputStream.writeUTF("Vote for one of them to exit by writing the player name:\n" +
                ShareData.getPlayers().toString() + "\n");
        String name = dataInputStream.readUTF();
        ShareData.allSendMassage(this.getUsername() + "Voted to->" + name);
        return name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Player selectPlayer() throws IOException {
        dataOutputStream.writeUTF("Type name the player you want:\n" +
                ShareData.getPlayers().toString() + "\n" +
                "If you do not want to select someone, type the word \"none:\"\n");
        while (true) {
            String str = dataInputStream.readUTF();
            if (str.equals("none"))
                return null;
            Player player = ShareData.givePlayerByName(str);
            if (player != null)
                return player;
            dataOutputStream.writeUTF("There is no such player, please try again !!\n" +
                    ShareData.getPlayers().toString() + "\n");
        }
    }

    public String getUsername() {
        return username;
    }

    public Boolean yesOrNo() throws IOException {
        dataOutputStream.writeUTF("yes or no?\n");
        while (true) {
            String str = dataInputStream.readUTF();
            if (str.equals("yes") || str.equals("Yes"))
                return true;
            if (str.equals("no") || str.equals("No"))
                return false;
            dataOutputStream.writeUTF("Input is incorrect!:(\n" +
                    "please try again\n" +
                    "yes or no?");
        }
    }

    public void send(String str) throws IOException {
        dataOutputStream.writeUTF(str);
    }

    public Boolean getSilence() {
        return silence;
    }

    public void setSilence(Boolean silence) {
        this.silence = silence;
    }

    public String read () throws IOException {
        return dataInputStream.readUTF();
    }


}
