package com.example.bill.scavengerhunt3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import android.util.Log;

import java.util.ArrayList;

public class AddGameActivity extends AppCompatActivity {

    private EditText mGameName;
    private EditText mTeam1;
    private EditText mTeam2;
    private EditText mTeam3;
    private EditText mTeam4;
    private EditText mTeam5;
    private Button mAddGame;
    private DatabaseReference mDatabase;
    private DatabaseReference mTeamsRef;
    private Spinner spinnerTeam1;
    private Spinner spinnerTeam2;
    private Spinner spinnerTeam3;
    private Spinner spinnerTeam4;
    private Spinner spinnerTeam5;
    private ArrayList<Team> teams = new ArrayList<Team>();
    private ArrayList<String> teamList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mTeamsRef = FirebaseDatabase.getInstance().getReference("Teams");

        mGameName = (EditText) findViewById(R.id.gameName);
//        mTeam1 = (EditText) findViewById(R.id.team1);
//        mTeam2 = (EditText) findViewById(R.id.team2);
//        mTeam3 = (EditText) findViewById(R.id.team3);
//        mTeam4 = (EditText) findViewById(R.id.team4);
//        mTeam5 = (EditText) findViewById(R.id.team5);
        mAddGame = (Button) findViewById(R.id.addGame);
        spinnerTeam1 = (Spinner) findViewById(R.id.spinnerTeam1);
        spinnerTeam2 = (Spinner) findViewById(R.id.spinnerTeam2);
        spinnerTeam3 = (Spinner) findViewById(R.id.spinnerTeam3);
        spinnerTeam4 = (Spinner) findViewById(R.id.spinnerTeam4);
        spinnerTeam5 = (Spinner) findViewById(R.id.spinnerTeam5);


        mTeamsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot teamSnapshot: dataSnapshot.getChildren()){
                    System.out.println("ZEREGA dataSnapshot: " + teamSnapshot.child("name").getValue(String.class));
                    teamList.add(teamSnapshot.child("name").getValue(String.class));
                    //creating a Team object from the database to add to the Game object
                    Team newTeam = new Team(teamSnapshot.child("name").getValue(String.class),
                            teamSnapshot.child("player1").getValue(String.class),
                            teamSnapshot.child("player2").getValue(String.class),
                            teamSnapshot.child("player3").getValue(String.class),
                            teamSnapshot.child("player4").getValue(String.class),
                            teamSnapshot.child("player5").getValue(String.class));
                    teams.add(newTeam);
                }

                System.out.println("ZEREGA teamList: " + teamList.toString());

                //filling in drop box of current available teams
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddGameActivity.this,
                        android.R.layout.simple_spinner_item, teamList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinnerTeam1.setAdapter(adapter);
                spinnerTeam2.setAdapter(adapter);
                spinnerTeam3.setAdapter(adapter);
                spinnerTeam4.setAdapter(adapter);
                spinnerTeam5.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mAddGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String gameName = mGameName.getText().toString();
                Game game = new Game(gameName);

                for(Team tm : teams){
                    tm.setTeamScavengeList(game.getScavengeList());
                }
//                game.setTeamList(teams);
                Log.d("AddGameActivity","team1 = "+spinnerTeam1.getSelectedItem().toString());
                Team team = new Team();
                team.setName(spinnerTeam1.getSelectedItem().toString());
                //Populate the players and scavenge list for this team
                for(Team tm : teams){
                    if (tm.getName().equals(spinnerTeam1.getSelectedItem().toString())) {
                        team.addPlayer(tm.getPlayer1());
                        team.addPlayer(tm.getPlayer2());
                        team.addPlayer(tm.getPlayer3());
                        team.addPlayer(tm.getPlayer4());
                        team.addPlayer(tm.getPlayer5());
                        team.setTeamScavengeList(tm.getTeamScavengeList());
                    }
                }
                game.addTeam(team);
                Log.d("AddGameActivity","team2 = "+spinnerTeam2.getSelectedItem().toString());
                boolean teamNameComparison = spinnerTeam2.getSelectedItem().toString().equals("Marios Mates");
                Log.d("AddGameActivity","teamNameComparison = "+teamNameComparison);
                //Skip the defaulted team name "Marios Mates"
                if (false == teamNameComparison) {
                    team.setName(spinnerTeam2.getSelectedItem().toString());
                    //Populate the players and scavenge list for this team
                    for(Team tm : teams){
                        if (tm.getName().equals(spinnerTeam2.getSelectedItem().toString())) {
                            team.addPlayer(tm.getPlayer1());
                            team.addPlayer(tm.getPlayer2());
                            team.addPlayer(tm.getPlayer3());
                            team.addPlayer(tm.getPlayer4());
                            team.addPlayer(tm.getPlayer5());
                            team.setTeamScavengeList(tm.getTeamScavengeList());
                        }
                    }
                    game.addTeam(team);
                }
                Log.d("AddGameActivity","team3 = "+spinnerTeam3.getSelectedItem().toString());
                teamNameComparison = spinnerTeam3.getSelectedItem().toString().equals("Marios Mates");
                Log.d("AddGameActivity","teamNameComparison = "+teamNameComparison);
                //Skip the defaulted team name "Marios Mates"
                if (false == teamNameComparison) {
                    team.setName(spinnerTeam3.getSelectedItem().toString());
                    //Populate the players and scavenge list for this team
                    for(Team tm : teams){
                        if (tm.getName().equals(spinnerTeam3.getSelectedItem().toString())) {
                            team.addPlayer(tm.getPlayer1());
                            team.addPlayer(tm.getPlayer2());
                            team.addPlayer(tm.getPlayer3());
                            team.addPlayer(tm.getPlayer4());
                            team.addPlayer(tm.getPlayer5());
                            team.setTeamScavengeList(tm.getTeamScavengeList());
                        }
                    }
                    game.addTeam(team);
                }
                Log.d("AddGameActivity","team4 = "+spinnerTeam4.getSelectedItem().toString());
                teamNameComparison = spinnerTeam4.getSelectedItem().toString().equals("Marios Mates");
                Log.d("AddGameActivity","teamNameComparison = "+teamNameComparison);
                //Skip the defaulted team name "Marios Mates"
                if (false == teamNameComparison) {
                    team.setName(spinnerTeam4.getSelectedItem().toString());
                    //Populate the players and scavenge list for this team
                    for(Team tm : teams){
                        if (tm.getName().equals(spinnerTeam4.getSelectedItem().toString())) {
                            team.addPlayer(tm.getPlayer1());
                            team.addPlayer(tm.getPlayer2());
                            team.addPlayer(tm.getPlayer3());
                            team.addPlayer(tm.getPlayer4());
                            team.addPlayer(tm.getPlayer5());
                            team.setTeamScavengeList(tm.getTeamScavengeList());
                        }
                    }
                    game.addTeam(team);
                }
                Log.d("AddGameActivity","team5 = "+spinnerTeam5.getSelectedItem().toString());
                teamNameComparison = spinnerTeam5.getSelectedItem().toString().equals("Marios Mates");
                Log.d("AddGameActivity","teamNameComparison = "+teamNameComparison);
                //Skip the defaulted team name "Marios Mates"
                if (false == teamNameComparison) {
                    team.setName(spinnerTeam5.getSelectedItem().toString());
                    //Populate the players and scavenge list for this team
                    for(Team tm : teams){
                        if (tm.getName().equals(spinnerTeam5.getSelectedItem().toString())) {
                            team.addPlayer(tm.getPlayer1());
                            team.addPlayer(tm.getPlayer2());
                            team.addPlayer(tm.getPlayer3());
                            team.addPlayer(tm.getPlayer4());
                            team.addPlayer(tm.getPlayer5());
                            team.setTeamScavengeList(tm.getTeamScavengeList());
                        }
                    }
                    game.addTeam(team);
                }

                DatabaseReference newRef = mDatabase.child("Games").push();
                Log.d("AddGameActivity","adding game "+gameName);
                Log.d("AddGameActivity","number of teams = "+game.getNumTeams());
                newRef.setValue(game);
                Intent myIntent = new Intent(AddGameActivity.this, GameLobbyActivity.class);
                AddGameActivity.this.startActivity(myIntent);
            }
        });

        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " games");
                for (DataSnapshot gameSnapshot: dataSnapshot.getChildren()) {
                    String name = gameSnapshot.child("name").getValue(String.class);
                    if (name != null) {
                        Log.d("AddGameActivity", "name from snapshot:" + name);
                        String team1 = gameSnapshot.child("team1").getValue(String.class);
                        Log.d("AddGameActivity", "team1 from snapshot: " + team1);
                        String team2 = gameSnapshot.child("team2").getValue(String.class);
                        Log.d("AddGameActivity", "team2 from snapshot: " + team2);
                        String team3 = gameSnapshot.child("team3").getValue(String.class);
                        Log.d("AddGameActivity", "team3 from snapshot: " + team3);
                        String team4 = gameSnapshot.child("team4").getValue(String.class);
                        Log.d("AddGameActivity", "team4 from snapshot: " + team4);
                        String team5 = gameSnapshot.child("team5").getValue(String.class);
                        Log.d("AddGameActivity", "team5 from snapshot: " + team5);
                        Game newGame = new Game(name);
                        newGame.setTeamList(teams);
                        newGame.print();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddGameActivity.this,
                        "onCanceled error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
