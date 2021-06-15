import java.io.IOException;

public class GameLoop {
    private final GameManager gameManager;

    public GameLoop(){
        gameManager = new GameManager();
    }

    public void dayAndNight() throws IOException, InterruptedException {
        while (true){
            night();
            gameFinish();
            day();
            vote();
            gameFinish();
        }
    }

    public void day() throws IOException, InterruptedException {
        gameManager.dayHandler();
    }

    public void night() throws IOException {
        gameManager.nightHandler();
    }

    public void vote() throws InterruptedException, IOException {
        gameManager.voteHandler();
    }

    public void introductionNight() throws IOException {
        gameManager.introductionNightHandler();
    }

    public boolean gameFinish() throws IOException {
        return gameManager.gameFinish();
    }

}
