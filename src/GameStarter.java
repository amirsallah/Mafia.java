import Data.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameStarter {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(2021);
        start(serverSocket);
        productionOfRoles();
        distributionOfRoles();
        GameLoop gameLoop = new GameLoop();
        gameLoop.dayAndNight();
    }

    public static void start(ServerSocket serverSocket) {
        int numberPlayer = 10;
        try {
            ExecutorService service = Executors.newCachedThreadPool();
//            service.execute(new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i = 0; true; ) {
//                        for (Player p : ShareData.getPlayers()) {
//                            if (p.getLive())
//                                ++i;
//                        }
//                        if (ShareData.getPlayers().size() >= 10 && i == ShareData.getPlayers().size())
//                            return;
//                    }
//                }
//            }));
            for (int i = 1; i != numberPlayer; ++i) {
                Socket connection = serverSocket.accept();
                service.execute(new Thread(() -> {

                    try (DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
                         DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream())) {
                        while (true) {
                            dataOutputStream.writeUTF("please enter your user name:\n");
                            String name = dataInputStream.readUTF();
                            if (nameExist(name)) {
                                dataOutputStream.writeUTF("This name exists Please select another name:\n");
                                continue;
                            }
                            Player player = new Player(name, connection);
                            ShareData.AddPlayer(player);
                            dataOutputStream.writeUTF("If you are ready, enter the word ((ready))\n");
                            String ready = dataInputStream.readUTF();
                            if (ready.equals("ready") || ready.equals("Ready"))
                                player.setLive(true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void productionOfRoles() {
        int numberMafia = ShareData.getPlayers().size() / 3;
        ShareData.AddRole(new GodFather());
        ShareData.AddRole(new Dr_Lector());
        for (int i = 0; i != (numberMafia - 2); ++i)
            ShareData.AddRole(new SimpleMafia());
        int numberCitizen = ShareData.getPlayers().size() - numberMafia;
        ShareData.AddRole(new Doctor());
        ShareData.AddRole(new Mayor());
        ShareData.AddRole(new Professional());
        ShareData.AddRole(new Psychologist());
        ShareData.AddRole(new DieHard());
        ShareData.AddRole(new Detective());
        for (int i = 0; i != (numberCitizen-6); ++i)
            ShareData.AddRole(new SimpleCitizen());
    }

    public static void distributionOfRoles() {
        Random random = new Random();
        ArrayList<Role> roles = ShareData.getRoles();
        ArrayList<Player> players = ShareData.getPlayers();
        for (Player p : players) {
            int num = random.nextInt(roles.size());
            Role role = roles.get(num);
            p.setRole(role);
            roles.remove(num);
        }
        ShareData.setPlayers(players);
    }

    public static boolean nameExist(String name) {
        for (Player p : ShareData.getPlayers()) {
            if (p.getUsername().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
