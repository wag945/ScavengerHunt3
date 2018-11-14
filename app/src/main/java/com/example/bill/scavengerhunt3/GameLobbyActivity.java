package com.example.bill.scavengerhunt3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameLobbyActivity extends AppCompatActivity implements GameRecyclerViewAdapter.ItemClickListener {

    private ArrayList<Game> games;
    GameRecyclerViewAdapter adapter;
    private Button mAddGameButton;
    private Button mTeamLobbyButton;
    private Button mStartButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        games = new ArrayList<Game>();

        //Test data
//        Game testGame = new Game(1);
//        Game testGame2 = new Game(2);
//        Game testGame3 = new Game(3);
//        Game testGame4 = new Game(4);
//        Game testGame5 = new Game(5);
//        games.add(testGame);
//        games.add(testGame2);
//        games.add(testGame3);
//        games.add(testGame4);
//        games.add(testGame5);

        mAddGameButton = (Button) findViewById(R.id.addGameButton);
        mAddGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GameLobbyActivity.this, AddGameActivity.class);
                GameLobbyActivity.this.startActivity(myIntent);
            }
        });

        mTeamLobbyButton = (Button) findViewById(R.id.teamLobbyButton);
        mTeamLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GameLobbyActivity.this, TeamLobbyActivity.class);
                GameLobbyActivity.this.startActivity(myIntent);
            }
        });
        mStartButton = (Button) findViewById(R.id.startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GameLobbyActivity.this, StartGameActivity.class);
                GameLobbyActivity.this.startActivity(myIntent);
            }
        });

        //Need to populate the games from the DB
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " teams");
                //Clear the ArrayList
                games.clear();
                for (DataSnapshot teamSnapshot: dataSnapshot.getChildren()) {
                    String name = teamSnapshot.child("gameName").getValue(String.class);
                    if (name != null) {
                        Log.d("GameLobbyActivity", "name from snapshot:" + name);
                        String team1 = teamSnapshot.child("team1").getValue(String.class);
                        Log.d("GameLobbyActivity", "team1 from snapshot: " + team1);
                        String team2 = teamSnapshot.child("team2").getValue(String.class);
                        Log.d("GameLobbyActivity", "team2 from snapshot: " + team2);
                        String team3 = teamSnapshot.child("team3").getValue(String.class);
                        Log.d("GameLobbyActivity", "team3 from snapshot: " + team3);
                        String team4 = teamSnapshot.child("team4").getValue(String.class);
                        Log.d("GameLobbyActivity", "team4 from snapshot: " + team4);
                        String team5 = teamSnapshot.child("team5").getValue(String.class);
                        Log.d("GameLobbyActivity", "team5 from snapshot: " + team5);
                        Game game = new Game(name);
                        if (team1.length() > 0) {
                            game.addTeam(team1);
                        }
                        if (team2.length() > 0) {
                            game.addTeam(team2);
                        }
                        if (team3.length() > 0) {
                            game.addTeam(team3);
                        }
                        if (team4.length() > 0) {
                            game.addTeam(team4);
                        }
                        if (team5.length() > 0) {
                            game.addTeam(team5);
                        }
                        games.add(game);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GameLobbyActivity.this,
                        "onCanceled error",
                        Toast.LENGTH_LONG).show();
            }
        });

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GameRecyclerViewAdapter(this, games);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onItemClick (View view,int position){
//        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Game selectedGame = adapter.getItem(position);
        Toast.makeText(this,selectedGame.getGameName()+ " was selected",Toast.LENGTH_LONG).show();
        //Need to start the game details activity that will have all of the game details, join game, and start game buttons
        Intent myIntent = new Intent(GameLobbyActivity.this, GameDetailsActivity.class);
        myIntent.putExtra("GameName",selectedGame.getGameName());
        GameLobbyActivity.this.startActivity(myIntent);
    }
}
