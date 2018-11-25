package com.example.bill.scavengerhunt3;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity implements LeaderboardRecylcerViewAdapter.ItemClickListener{
    private ArrayList<Game> games = new ArrayList<Game>();
    private DatabaseReference gamesRef;
    private ArrayList<Team> teamsBig = new ArrayList<Team>();
    LeaderboardRecylcerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        FirebaseDatabase database = FirebaseDatabase.getInstance();



        String gameName = getIntent().getStringExtra("gameName");

        //getting the game from the database
       gamesRef = database.getReference("Games");

        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot gameSnapshot: dataSnapshot.getChildren()) {
                    String name = gameSnapshot.child("gameName").getValue(String.class);
                    System.out.println("ZEREGA leaderboardActivity Gamename: " + name);
                    System.out.println("ZEREGA gameName from leaderboard Intent: " + gameName);
                    if (name != null && name.equals(gameName)){
                        Game game = new Game(name);
                        ArrayList<Team> teams = new ArrayList<Team>();
                        ArrayList<ScavengeItem> scavengeItems = new ArrayList<ScavengeItem>();
                        ArrayList<ScavengeItem> scavengeItemsTeams = new ArrayList<ScavengeItem>();

                        //cycle through the scavenge items and store in a list for hte game constructor
                        for(int i = 0; i < 5; i++){
                            ScavengeItem newItem = new ScavengeItem(gameSnapshot.child("scavengeList").child(Integer.toString(i))
                                    .child("name").getValue().toString());
                            if(gameSnapshot.child("scavengeList").child(Integer.toString(i))
                                    .child("found").getValue().equals("true")){
                                newItem.setFound(true);
                            }
                            scavengeItems.add(newItem);
                        }

                        //outer loop for each team
                        for(int j=0; j < gameSnapshot.child("teamList").getChildrenCount();j++ ){

                            //clear the items list then build a new one for each team
                            scavengeItemsTeams.clear();
                            for (int i = 0; i < 5; i++) {
                                ScavengeItem newItem = new ScavengeItem(gameSnapshot.child("teamList").child(Integer.toString(j))
                                        .child("teamScavengeList").child(Integer.toString(i)).child("name").getValue().toString());
                                if (gameSnapshot.child("teamList").child(Integer.toString(j))
                                        .child("teamScavengeList").child(Integer.toString(i)).child("found").getValue().equals("true")) {
                                    newItem.setFound(true);
                                }
                                scavengeItemsTeams.add(newItem);
                            }
                            Team newTeam = new Team(
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("name").getValue().toString(),
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("player1").getValue().toString(),
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("player2").getValue().toString(),
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("player3").getValue().toString(),
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("player4").getValue().toString(),
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("player5").getValue().toString(),
                                    gameSnapshot.child("teamList").child(Integer.toString(j)).child("record").getValue().toString(),
                                    scavengeItems
                            );
                            teams.add(newTeam);
                        }


                        //rebuild hte game object from teh data taken from the database
                        game.setTeamList(teams);
                        game.setScavengeList(scavengeItems);
                        teamsBig = game.getTeamList();
                        games.add(game);
                        System.out.println("ZEREGA game.getTeamList line 99 LeaderboardActivity: " + game.getTeamList().get(0).getName());
                        System.out.println("ZEREGA games line100 LeaderboardActivity: " + games.get(0).getTeamList().get(0).getName());
                    }
                }
               adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LeaderboardActivity.this,
                        "onCanceled error",
                        Toast.LENGTH_LONG).show();
            }
        });

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewTeamsLeaderboard);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //System.out.println("ZEREGA games line 116 LeaderboardActivity: " + games.get(0).getTeamList().get(0).getName());
       //for some reason the arraylist of teams teamsBig is not getting hte list of teams from line 98
        adapter = new LeaderboardRecylcerViewAdapter(this, teamsBig);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
