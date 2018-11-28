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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        games = new ArrayList<Game>();


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
                        String gameStatus = teamSnapshot.child("gameStatus").getValue(String.class);
                        Game game = new Game(name);
                        Log.d("GameLobbyActivity","number of teams = "+teamSnapshot.child("teamList").getChildrenCount());
                        //Loop through all the teams associated with this game
                        for (int i = 0; i < teamSnapshot.child("teamList").getChildrenCount(); i++) {
                            Log.d("GameLobbyActivity","team ["+i+"] = "+teamSnapshot.child("teamList").child(Integer.toString(i)).child("name"));
                            Team team = new Team();
                            team.setName(teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").toString());
                            if (teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").toString().length() > 0) {
                                game.addTeam(team);
                            }
                        }
                        if (gameStatus.equals("NOT_STARTED")) {
                            game.setGameStatus(Game.GameState.NOT_STARTED);
                        }
                        else if (gameStatus.equals("IN_PROGRESS")) {
                            game.setGameStatus(Game.GameState.IN_PROGRESS);
                        }
                        else {
                            game.setGameStatus(Game.GameState.ENDED);
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
        Game selectedGame = adapter.getItem(position);
        Toast.makeText(this,selectedGame.getGameName()+ " was selected",Toast.LENGTH_LONG).show();
        //Need to start the game details activity that will have all of the game details, join game, and start game buttons
        Intent myIntent = new Intent(GameLobbyActivity.this, GameDetailsActivity.class);
        myIntent.putExtra("GameName",selectedGame.getGameName());
        GameLobbyActivity.this.startActivity(myIntent);
    }
}
