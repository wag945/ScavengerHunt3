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




    public ArrayList<Team> getTeamList() {
        return mTeamList;
    }

    public Game(String gameName) {
        mGameName = gameName;
        mGameState = GameState.NOT_STARTED;
        teamIndex = 0;
        this.timerView = "00:00";
        this.mScavengeList = createScavengeList();
        this.mTeamList = createTeamList();

    }



    public void setTeamList(ArrayList<Team> teamList) {
        mTeamList = teamList;
        teamIndex = mTeamList.size();
    }

    public void setGameName(String gameName) {mGameName = gameName;}

    public String getGameName() {
        return mGameName;
    }


    public void addTeam(Team team){
        mTeamList.add(team);
        teamIndex++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNumTeams() {

        return Integer.toString(teamIndex);
    }

    public void startGame() {
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

    public void setGameStatus(GameState gameState) {
        mGameState = gameState;
    }
    public GameState getGameStatus() {
        return mGameState;
    }

    public void print() {
        Log.d("Game::print",
                "name: "+mGameName+" Team1: "+ mTeamList);

    }


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

