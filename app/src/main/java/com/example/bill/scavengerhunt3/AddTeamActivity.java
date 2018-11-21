package com.example.bill.scavengerhunt3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import android.util.Log;

public class AddTeamActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mTeamName;
    private EditText mPlayer1;
    private EditText mPlayer2;
    private EditText mPlayer3;
    private EditText mPlayer4;
    private EditText mPlayer5;
    private EditText mRecord;
    private Button mAddTeam;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_team);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTeamName = (EditText) findViewById(R.id.teamName);
        mPlayer1 = (EditText) findViewById(R.id.player1);
        mPlayer2 = (EditText) findViewById(R.id.player2);
        mPlayer3 = (EditText) findViewById(R.id.player3);
        mPlayer4 = (EditText) findViewById(R.id.player4);
        mPlayer5 = (EditText) findViewById(R.id.player5);
        mRecord = (EditText) findViewById(R.id.record);
        mAddTeam = (Button) findViewById(R.id.addTeam);
        mAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String teamName = mTeamName.getText().toString();
                Team team = new Team();
                //Adding values
                team.setName(teamName);
                team.setRecord(mRecord.getText().toString());
                team.addPlayer(mPlayer1.getText().toString());
                team.addPlayer(mPlayer2.getText().toString());
                team.addPlayer(mPlayer3.getText().toString());
                team.addPlayer(mPlayer4.getText().toString());
                team.addPlayer(mPlayer5.getText().toString());
                team.setRecord(mRecord.getText().toString());
                DatabaseReference newRef = mDatabase.child("Teams").push();
                Log.d("AddTeamActivity","adding team "+teamName);
                newRef.setValue(team);
                Intent myIntent = new Intent(AddTeamActivity.this, TeamLobbyActivity.class);
                AddTeamActivity.this.startActivity(myIntent);
            }
        });

        DatabaseReference teamsRef = FirebaseDatabase.getInstance().getReference("Teams");

        teamsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " teams");
                for (DataSnapshot teamSnapshot: dataSnapshot.getChildren()) {
                    String name = teamSnapshot.child("name").getValue(String.class);
                    if (name != null) {
                        Log.d("AddTeamActivity", "name from snapshot:" + name);
                        String record = teamSnapshot.child("record").getValue(String.class);
                        Log.d("AddTeamActivity", "record from snapshot: " + record);
                        String player1 = teamSnapshot.child("player1").getValue(String.class);
                        Log.d("AddTeamActivity", "player1 from snapshot: " + player1);
                        String player2 = teamSnapshot.child("player2").getValue(String.class);
                        Log.d("AddTeamActivity", "player2 from snapshot: " + player2);
                        String player3 = teamSnapshot.child("player3").getValue(String.class);
                        Log.d("AddTeamActivity", "player3 from snapshot: " + player3);
                        String player4 = teamSnapshot.child("player4").getValue(String.class);
                        Log.d("AddTeamActivity", "player4 from snapshot: " + player4);
                        String player5 = teamSnapshot.child("player5").getValue(String.class);
                        Log.d("AddTeamActivity", "player5 from snapshot: " + player5);
                        Team newTeam = new Team();
                        newTeam.setName(name);
                        newTeam.addPlayer(player1);
                        newTeam.addPlayer(player2);
                        newTeam.addPlayer(player3);
                        newTeam.addPlayer(player4);
                        newTeam.addPlayer(player5);
                        newTeam.setRecord(record);
                        newTeam.print();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddTeamActivity.this,
                        "onCanceled error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

