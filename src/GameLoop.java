import java.io.IOException;

public class GameLoop {
    private final GameManager gameManager;

    public GameLoop(){
        gameManager = new GameManager();
    }

    public void dayAndNight() throws IOException {
        while (true){
            gameFinish();
            day();
            vote();
            gameFinish();
            night();
        }
    }

    public void day() throws IOException {
        gameManager.dayHandler();
    }

    public void night() throws IOException {
        gameManager.nightHandler();
    }

    public void vote(){
        gameManager.voteHandler();
    }

    public void introductionNight() throws IOException {
        gameManager.introductionNightHandler();
    }

    public void gameFinish(){

    }

}
