package com.example.bill.scavengerhunt3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.Button;

import java.util.ArrayList;

public class TeamLobbyActivity extends AppCompatActivity {

    private ListView listViewTeams;
    private TeamAdapter teamAdapter;
    private ArrayList<Team> teams;
    private Button mAddTeamButton;
    private Button mGameLobbyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_lobby);
        listViewTeams = (ListView) findViewById(R.id.list_view_teams);

        mAddTeamButton = (Button)findViewById(R.id.addTeam);
        mGameLobbyButton = (Button) findViewById(R.id.gameLobbyButton);
        mGameLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(TeamLobbyActivity.this, GameLobbyActivity.class);
                TeamLobbyActivity.this.startActivity(myIntent);
            }
        });

        teams = new ArrayList<>();

        //Need to populate the teams from the DB
        DatabaseReference teamsRef = FirebaseDatabase.getInstance().getReference("Teams");

        teamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " teams");
                //Clear the ArrayList
                teams.clear();
                for (DataSnapshot teamSnapshot: dataSnapshot.getChildren()) {
                    String name = teamSnapshot.child("name").getValue(String.class);
                    if (name != null) {
                        Log.d("TeamLobbyActivity", "name from snapshot:" + name);
                        String record = teamSnapshot.child("record").getValue(String.class);
                        Log.d("TeamLobbyActivity", "record from snapshot: " + record);
                        String player1 = teamSnapshot.child("player1").getValue(String.class);
                        Log.d("TeamLobbyActivity", "player1 from snapshot: " + player1);
                        String player2 = teamSnapshot.child("player2").getValue(String.class);
                        Log.d("TeamLobbyActivity", "player2 from snapshot: " + player2);
                        String player3 = teamSnapshot.child("player3").getValue(String.class);
                        Log.d("TeamLobbyActivity", "player3 from snapshot: " + player3);
                        String player4 = teamSnapshot.child("player4").getValue(String.class);
                        Log.d("TeamLobbyActivity", "player4 from snapshot: " + player4);
                        String player5 = teamSnapshot.child("player5").getValue(String.class);
                        Log.d("TeamLobbyActivity", "player5 from snapshot: " + player5);
                        Team newTeam = new Team();
                        newTeam.setName(name);
                        newTeam.addPlayer(player1);
                        newTeam.addPlayer(player2);
                        newTeam.addPlayer(player3);
                        newTeam.addPlayer(player4);
                        newTeam.addPlayer(player5);
                        if (null != record) {
                            newTeam.setRecord(record);
                        }
                        else {
                            newTeam.setRecord("0-0");
                        }
                        newTeam.print();
                        teams.add(newTeam);
                    }
                }

                //Update the adapter
                teamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TeamLobbyActivity.this,
                        "onCanceled error",
                        Toast.LENGTH_LONG).show();
            }
        });

        teamAdapter = new TeamAdapter(this,
                R.layout.content_list_item,
                teams);

        listViewTeams.setAdapter(teamAdapter);

        listViewTeams.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}

