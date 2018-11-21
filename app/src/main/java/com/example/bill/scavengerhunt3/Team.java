package com.example.bill.scavengerhunt3;

import java.util.ArrayList;
import android.util.Log;

public class Team {
    private String name;
    private String player1;
    private String player2;
    private String player3;
    private String player4;
    private String player5;
    private String record;
    private int playerIndex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void addPlayer(String playerName) {
        switch (playerIndex) {
            case 1:
                player1 = playerName;
                break;
            case 2:
                player2 = playerName;
                break;
            case 3:
                player3 = playerName;
                break;
            case 4:
                player4 = playerName;
                break;
            default:
                player5 = playerName;
                break;
        }

        playerIndex++;
    }

    public void print() {
        Log.d("Team::print",
                "name: "+name+" player1: "+player1+" player2: "+player2+" player3: "+player3+" player4: "+player4+" player5: "+player5+" record: "+record);
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getPlayer3() {
        return player3;
    }

    public String getPlayer4() {
        return player4;
    }

    public String getPlayer5() {
        return player5;
    }

    public String getNumPlayers() {
        int numPlayers = 0;
        if (player1.length() > 0) {
            numPlayers++;
        }
        if (player2.length() > 0) {
            numPlayers++;
        }
        if (player3.length() > 0) {
            numPlayers++;
        }
        if (player4.length() > 0) {
            numPlayers++;
        }
        if (player5.length() > 0) {
            numPlayers++;
        }
        return Integer.toString(numPlayers);
    }

    Team() {
        this.name = "";
        this.record = "";
        player1 = "";
        player2 = "";
        player3 = "";
        player4 = "";
        player5 = "";
        playerIndex = 1;
    }

    Team(String name,
         String player1,
         String player2,
         String player3,
         String player4,
         String player5,
         String record) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = player5;
        this.record = record;
        Log.d("MyFirstFireBaseApp","new team name = "+ this.name);
    }

    Team(String name,
         String player1,
         String player2,
         String player3,
         String player4,
         String player5
         ) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = player5;
        this.record = "";
    }

    Team(String name,
         String player1,
         String player2,
         String player3) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = "";
        this.player5 = "";
        this.record = "";
    }

    Team(String name,
         String player1,
         String player2,
         String player3,
         String player4
    ) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.player4 = player4;
        this.player5 = "";
        this.record = "";
    }
    Team(String name,
         String player1,
         String player2
    ) {
        this.name = name;
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = "";
        this.player4 = "";
        this.player5 = "";
        this.record = "";
    }
    Team(String name,
         String player1
    ) {
        this.name = name;
        this.player1 = player1;
        this.player2 = "";
        this.player3 = "";
        this.player4 = "";
        this.player5 = "";
        this.record = "";
    }
}
