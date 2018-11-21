package com.example.bill.scavengerhunt3;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

//have to make this object serializable to pass between different activities
public class Game {
    private String mGameName;
    private String team1;
    private String team2;
    private String team3;
    private String team4;
    private String team5;
    private String timerView;
    private int id;

    private String[] scavengeItemsBank = new String[]{"Red Sign", "Yellow Sign", "2 R Shoes",
            "2 L Shoes", "Animal Statue", "Person Statue", "Flag", "Green Ball", "Clock",
            "Teacher", "Notebook", "Book", "Waterbottle", "Mug", "Computer"};
    //creating a copy of the scavenge bank so that items can be removed as they are selected
    private List<String> scavengeItemsBankList = new ArrayList<>(Arrays.asList(scavengeItemsBank));
    private ArrayList<ScavengeItem> mScavengeList = new ArrayList<ScavengeItem>();
    private ArrayList<Team> mTeamList = new ArrayList<Team>();

    enum GameState {
        NOT_STARTED,
        IN_PROGRESS,
        ENDED
    };
    GameState mGameState;
    private int teamIndex;
    private static final int mMaxTeams = 5;
    private static final int mMaxScavengeItems = 5;

    public ArrayList<ScavengeItem> getScavengeList() {
        return mScavengeList;
    }

    public void setScavengeList(ArrayList<ScavengeItem> scavengeList) {
        mScavengeList = scavengeList;
    }

    public Game(String gameName) {
        mGameName = gameName;
        mGameState = GameState.NOT_STARTED;
        teamIndex = 1;
        this.team1 = "";
        this.team2 = "";
        this.team3 = "";

        this.team4 = "";
        this.team5 = "";
        this.timerView = "00:00";
        this.mScavengeList = createScavengeList();
        this.mTeamList = createTeamList();

    }

    public void setGameName(String gameName) {mGameName = gameName;}

    public String getGameName() {
        return mGameName;
    }

    public void addTeam(String teamName) {
        switch (teamIndex) {
            case 1:
                team1 = teamName;
                break;
            case 2:
                team2 = teamName;
                break;
            case 3:
                team3 = teamName;
                break;
            case 4:
                team4 = teamName;
                break;
            default:
                team5 = teamName;
                break;
        }
        teamIndex++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getTeam3() {
        return team3;
    }

    public String getTeam4() {
        return team4;
    }

    public String getTeam5() {
        return team5;
    }

    public String getNumTeams() {
        int numTeams = 0;
        if (team1.length() > 0) {
            numTeams++;
        }
        if (team2.length() > 0) {
            numTeams++;
        }
        if (team3.length() > 0) {
            numTeams++;
        }
        if (team4.length() > 0) {
            numTeams++;
        }
        if (team5.length() > 0) {
            numTeams++;
        }
        return Integer.toString(numTeams);
    }

    public void startGame() {
        //mTimer = new Timer();

        //Start a timer to run immediately and repeat every second
       // startTimer();
        //mTimer.schedule(mGameTimer,0,1000);
        mGameState = GameState.IN_PROGRESS;


    }

    public ArrayList<ScavengeItem> createScavengeList(){
        ArrayList<ScavengeItem> tempScavengeList = new ArrayList<ScavengeItem>();
        //create 5 Scavenge items for the teams to find
        for(int i = 0; i < mMaxScavengeItems; i++){
            Random random = new Random();
            int index = random.nextInt(scavengeItemsBankList.size());
            String scavengeItemString = scavengeItemsBankList.get(index);
            //removing scavenge item from bank list copy to prevent doubles of same item
            scavengeItemsBankList.remove(index);
            ScavengeItem scavengeItem = new ScavengeItem(scavengeItemString);
            tempScavengeList.add(i, scavengeItem);
        }
        return tempScavengeList;
    }

    public ArrayList<Team> createTeamList(){
        ArrayList<Team> tempTeamList = new ArrayList<Team>();


        return tempTeamList;

    }

    public void stopGame() {
      //  mGameTimer.cancel();
        mGameState = GameState.ENDED;
    }

    public GameState getGameStatus() {
        return mGameState;
    }

    public void print() {
        Log.d("Game::print",
                "name: "+mGameName+" Team1: "+this.getTeam1()+" Team2: "+this.getTeam2()
        +" Team3: "+this.getTeam3()+" Team4: "+this.getTeam4()+" Team5: "+this.getTeam5());

    }

//    public void startTimer(){
//
//        //new game timer set to 15 min, 60 sec * 15 = 900 x milliseconds, interval set to 1 second, 1000 miliseconds
//
//        mGameTimer = new CountDownTimer(900 * 1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                //setting the timer to be viewed in minute:seconds
//                timerView = millisUntilFinished / 1000 + " : " + millisUntilFinished % 60;
//            }
//
//            @Override
//            public void onFinish() {
//                timerView = "Out of Time!";
//            }
//        };
//    }

    public String getTimerView() {
        return timerView;
    }

    @Override
    public String toString() {
        String fullScavengeList = new String();

        for(ScavengeItem si : mScavengeList){
            fullScavengeList += si.getName() + ", ";
        }
        return fullScavengeList;
    }
}

