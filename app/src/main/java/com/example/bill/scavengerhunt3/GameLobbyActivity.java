package com.example.bill.scavengerhunt3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameLobbyActivity extends AppCompatActivity implements GameRecyclerViewAdapter.ItemClickListener {

    private ArrayList<Game> games;
    GameRecyclerViewAdapter adapter;
    private Button mTeamLobbyButton;
    private Button mAddTeamButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        games = new ArrayList<Game>();

        //Test data
        Game testGame = new Game(1);
        Game testGame2 = new Game(2);
        Game testGame3 = new Game(3);
        Game testGame4 = new Game(4);
        Game testGame5 = new Game(5);
        games.add(testGame);
        games.add(testGame2);
        games.add(testGame3);
        games.add(testGame4);
        games.add(testGame5);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameRecyclerViewAdapter(this, games);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        mTeamLobbyButton = (Button) findViewById(R.id.teamLobbyButton);
        mTeamLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GameLobbyActivity.this, TeamLobbyActivity.class);
                GameLobbyActivity.this.startActivity(myIntent);
            }
        });

        mAddTeamButton = (Button) findViewById(R.id.addTeamButton);
        mAddTeamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GameLobbyActivity.this, AddTeamActivity.class);
                GameLobbyActivity.this.startActivity(myIntent);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
