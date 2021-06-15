import Data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GameManager {
    private Integer statusesTime;
    private Boolean statuses;
    private Player playerKilled, playerShot, silent, playerLector, playerShow;

    public GameManager() {
        statusesTime = 0;
        playerKilled = null;
        playerShot = null;
        playerLector = null;
        playerShow = null;
        silent = null;
        statuses = false;
    }


    public void dayHandler() throws IOException, InterruptedException {
        ShareData.allSendMassage("It was morning\n" +
                "Open your eyes");
        declaration();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (Player p : ShareData.getPlayers()) {
            executorService.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i != 16; ++i)
                            ShareData.allSendMassage(ShareData.readMassage(p));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.MINUTES);
    }

    public void voteHandler() throws InterruptedException, IOException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (Player p : ShareData.getPlayers()) {
            executorService.execute(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Player player = ShareData.vote(p);
                        assert player != null;
                        ShareData.setItVotes(player);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        executorService.shutdown();
        executorService.awaitTermination(20, TimeUnit.SECONDS);
        int num = 0;
        Player selectedPlayer = null;
        for (Player p : ShareData.getPlayers()) {
            if (p.getVotes() > num) {
                num = p.getVotes();
                selectedPlayer = p;
            }else if (p.getVotes() == num){
                selectedPlayer = null;
            }
        }
        if (selectedPlayer != null) {
            ShareData.allSendMassage(selectedPlayer.getUsername() + "killed!!$");
            ShareData.removePlayer(selectedPlayer);
            ShareData.addDeadPlayer(selectedPlayer);
        }
    }

    public void nightHandler() throws IOException {
        playerKilled = null;
        ShareData.allSendMassage("It is night to close your eyes;)");
        //___________________________________________________________________________
        GodFather godFather = (GodFather) findGodFather();
        Dr_Lector dr_lector = (Dr_Lector) findDr_Lector();
        ArrayList<SimpleMafia> mafias = findMafia();
        for (SimpleMafia r : mafias) {
            godFather.GiveThePlayerRole().send(r.GiveThePlayerRole().getUsername() + "has chosen:" + r.selectGoal().getUsername() + "\n");
        }
        godFather.GiveThePlayerRole().send(dr_lector.selectGoal().getUsername() + "\n");
        playerKilled = godFather.selectGoal();
        //____________________________________________________________________________
        playerLector = dr_lector.selectGoal();
        //____________________________________________________________________________

        Doctor doctor = (Doctor) findDoctor();
        if (doctor.selectGoal() != playerKilled) {
            if (playerKilled.getRole() instanceof DieHard) {
                if (!(((DieHard) playerKilled.getRole()).getArmor()))
                    playerKilled.setLive(false);
                else
                    ((DieHard) playerKilled.getRole()).setArmor(false);
            }
            playerKilled.setLive(false);
        }
        //_____________________________________________________________________________

        Detective detective = (Detective) findDetective();
        playerShow = detective.selectGoal();
        if (playerShow.getRole() instanceof Citizen || playerShow.getRole() instanceof GodFather)
            ShareData.sendToPlayer(detective.GiveThePlayerRole(), "Citizen:(");
        if (playerShow.getRole() instanceof SimpleMafia || playerShow.getRole() instanceof Dr_Lector)
            ShareData.sendToPlayer(detective.GiveThePlayerRole(), "Mafia:)");


        //____________________________________________________________________________
        Psychologist psychologist = (Psychologist) findPsychologist();
        silent = psychologist.selectGoal();
        ShareData.talkDown(silent);

        //_____________________________________________________________________________
        Professional professional = (Professional) findProfessional();
        playerShot = professional.selectGoal();
        if (playerShot.getRole() instanceof Citizen)
            professional.GiveThePlayerRole().setLive(false);
        else {
            if (!playerShot.equals(playerLector) && !(playerShot.getRole() instanceof GodFather))
                playerShot.setLive(false);
        }

        //______________________________________________________________________________
        DieHard dieHard = (DieHard) findDieHard();
        statuses = dieHard.yesOrNo();
        if (statuses)
            ++statusesTime;
    }

    public void introductionNightHandler() throws IOException {
        ShareData.allSendMassage("It is night to close your eyes;)");
        GodFather godFather = (GodFather) findGodFather();
        Dr_Lector dr_lector = (Dr_Lector) findDr_Lector();
        ArrayList<SimpleMafia> mafias = findMafia();
        ArrayList<Player> players = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        for (SimpleMafia r : mafias) {
            players.add(r.GiveThePlayerRole());
            str.append(r.GiveThePlayerRole().getUsername()).append(r.GiveThePlayerRole().getRole().toString()).append("\n");
        }
        players.add(godFather.GiveThePlayerRole());
        players.add(dr_lector.GiveThePlayerRole());
        str.append(godFather.GiveThePlayerRole().getUsername()).append(godFather.GiveThePlayerRole().getRole().toString()).append("\n");
        str.append(dr_lector.GiveThePlayerRole().getUsername()).append(dr_lector.GiveThePlayerRole().getRole().toString()).append("\n");
        for (Player p : players) {
            ShareData.sendToPlayer(p, str.toString());
        }

        Mayor mayor = (Mayor) findMayor();
        Doctor doctor = (Doctor) findDoctor();
        ShareData.sendToPlayer(mayor.GiveThePlayerRole(), doctor.GiveThePlayerRole().getUsername() + "is doctor:)");
    }


    public void declaration() throws IOException {
        int citi = 0;
        int maf = 0;
        for (Player p : ShareData.getPlayers()) {
            if (!p.getLive()) {
                ShareData.allSendMassage(p.getUsername() + "killed!!$");
                ShareData.addDeadPlayer(p);
                ShareData.removePlayer(p);
            }
        }
        if (statuses && !(statusesTime > 2)) {
            for (Player p : ShareData.getDeadPlayer()) {
                if (p.getRole() instanceof Citizen)
                    ++citi;
                else
                    ++maf;
            }
            ShareData.allSendMassage("Players killed include:\n" + citi + "Citizen\n" + maf + "mafia\n");
            statuses = false;
        }
    }


    public Role findGodFather() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof GodFather)
                return r;
        }
        return null;
    }

    public Role findDr_Lector() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof Dr_Lector)
                return r;
        }
        return null;
    }

    public ArrayList<SimpleMafia> findMafia() {
        ArrayList<SimpleMafia> Mafia = new ArrayList<>();
        for (Role r : ShareData.getRoles()) {
            if (r instanceof SimpleMafia) {
                Mafia.add((SimpleMafia) r);
            }
        }
        return Mafia;
    }

    public ArrayList<Player> findAllRolePlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for (Role r : ShareData.getRoles()) {
            players.add(r.GiveThePlayerRole());
        }
        return players;
    }

    public Role findDoctor() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof Doctor)
                return r;
        }
        return null;
    }

    public Role findDieHard() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof DieHard)
                return r;
        }
        return null;
    }

    public Role findDetective() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof Detective)
                return r;
        }
        return null;
    }

    public Role findPsychologist() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof Psychologist)
                return r;
        }
        return null;
    }

    public Role findProfessional() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof Professional)
                return r;
        }
        return null;
    }

    public Role findMayor() {
        for (Role r : ShareData.getRoles()) {
            if (r instanceof Mayor)
                return r;
        }
        return null;
    }

    public ArrayList<Role> findCitizen() {
        ArrayList<Role> citizens = new ArrayList<>();
        for (Role r : ShareData.getRoles()) {
            if (r instanceof SimpleCitizen) {
                citizens.add(r);
            }
        }
        return citizens;
    }

    public Boolean gameFinish() throws IOException {
        int maf = 0,citi = 0;
        for (Player p: ShareData.getPlayers()) {
            if (p.getRole() instanceof Mafia)
                ++maf;
            if (p.getRole() instanceof Citizen)
                ++citi;
        }
        if (maf == citi) {
            ShareData.allSendMassage("mafia win!");
            return true;
        }
        if (maf == 0) {
            ShareData.allSendMassage("citizen win!");
            return true;
        }
        return false;
    }

}
