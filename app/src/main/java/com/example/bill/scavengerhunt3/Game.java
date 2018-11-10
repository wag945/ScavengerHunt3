package com.example.bill.scavengerhunt3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    private int mGameId;
    private Vector<Team> mTeams;
    //Replace this with the real ScavengedItem class
    private Vector<Integer> mScavengedItems;
    private Timer mTimer;
    private GameTimer mGameTimer;
    //Need a timer task to run the timer

    private String[] scavengeItemsBank = new String[]{"Red Sign", "Yellow Sign", "2 Right White Shoes", "2 Left Black Shoes",
            "Animal Statue", "Person Statue", "Flag", "Green Ball", "A Clock", "Person in Uniform", "Out of State License Plate",
            "A Book"};
    //creating a copy of the scavenge bank so that items can be removed as they are selected
    private List<String> scavengeItemsBankList = new ArrayList<>(Arrays.asList(scavengeItemsBank));
    private ArrayList<ScavengeItem> mScavengeList;

    enum GameState {
        NOT_STARTED,
        IN_PROGRESS,
        ENDED
    };
    GameState mGameState;
    private static final int mMaxTeams = 5;

    public Game(int gameId) {
        mGameId = gameId;
        mGameState = GameState.NOT_STARTED;
        mTeams = new Vector<Team>();
    }

    public int getGameId() {
        return mGameId;
    }

    public void addTeam(Team team) {
        if (mTeams.size() < mMaxTeams) {
            mTeams.add(team);
        }
    }

    public Vector<Team> getTeams() {
        return mTeams;
    }

    public void addScavengedItem(int item) {
        mScavengedItems.add(item);
    }

    public Vector<Integer> getScavengedItems() {
        return mScavengedItems;
    }

    public void startGame() {
        mTimer = new Timer();
        mGameTimer = new GameTimer();
        //Start a timer to run immediately and repeat every second
        mTimer.schedule(mGameTimer,0,1000);
        mGameState = GameState.IN_PROGRESS;

        //create 5 Scavenge items for the teams to find
        for(int i = 0; i < 5; i++){
            Random random = new Random();
            int index = random.nextInt(scavengeItemsBankList.size());
            String scavengeItemString = scavengeItemsBankList.get(index);
            //removing scavenge item from bank list copy to prevent doubles of same item
            scavengeItemsBankList.remove(index);
            ScavengeItem scavengeItem = new ScavengeItem(scavengeItemString);

            mScavengeList.add(i, scavengeItem);

        }
    }

    public void stopGame() {
        mGameTimer.cancel();
        mGameState = GameState.ENDED;
    }

    public GameState getGameStatus() {
        return mGameState;
    }
}

