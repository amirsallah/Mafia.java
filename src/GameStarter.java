import Data.Player;
import Data.ShareData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameStarter {
    public void start(ServerSocket serverSocket) {
        try {
            ExecutorService service = Executors.newCachedThreadPool();
            Thread allReady = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        ArrayList<Player> players = ShareData.getPlayers();
                    }
                }
            })
            for (int i = 1; true; ++i) {
                Socket connection = serverSocket.accept();
                service.execute(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
                            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                            dataInputStream.readUTF();//?????????????????????????????????????????:باید باشه یا نه؟
                            dataOutputStream.writeUTF("please enter your user name:\n");
                            String name = dataInputStream.readUTF();
                            Player player = new Player(name, connection);
                            ShareData.AddPlayer(player);
                            dataOutputStream.writeUTF("If you are ready, enter the word ready:)\n");
                            String ready = dataInputStream.readUTF();
                            if (ready.equals("ready") || ready.equals("Ready"))
                                player.setLive(true);
                            //تولید نقشها و دادن آنها به بازیکنان
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
