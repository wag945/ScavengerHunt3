package com.example.bill.scavengerhunt3;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameDetailsActivity extends AppCompatActivity {

    private Button mJoinGameButton;
    private Button mStartGameButton;
    private TextView mGameNameTextView;
    private TextView mGameStatusTextView;
    private TextView mTeam1TextView;
    private TextView mTeam2TextView;
    private TextView mTeam3TextView;
    private TextView mTeam4TextView;
    private TextView mTeam5TextView;
    private Game game;
    private String detailGameName;
    private TextView mTimerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);

        mGameNameTextView = (TextView)findViewById(R.id.gameNameText);
        mGameStatusTextView = (TextView)findViewById(R.id.gameStatusText);
        mTeam1TextView = (TextView)findViewById(R.id.gameTeam1Text);
        mTeam2TextView = (TextView)findViewById(R.id.gameTeam2Text);
        mTeam3TextView = (TextView)findViewById(R.id.gameTeam3Text);
        mTeam4TextView = (TextView)findViewById(R.id.gameTeam4Text);
        mTeam5TextView = (TextView)findViewById(R.id.gameTeam5Text);
       

        Intent intent = getIntent();
        detailGameName = intent.getStringExtra("GameName");

        mJoinGameButton = (Button) findViewById(R.id.joinGameButton);
        mJoinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mStartGameButton = (Button) findViewById(R.id.startGameButton);
        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //need to have this point to the current game activity
                startActivity(new Intent(GameDetailsActivity.this, StartGameActivity.class));

            }
        });

        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("Games");

        gamesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " teams");
                for (DataSnapshot teamSnapshot: dataSnapshot.getChildren()) {
                    String name = teamSnapshot.child("gameName").getValue(String.class);
                    if (name != null && name.equals(detailGameName)) {
                        Log.d("GameDetailsActivity","found match for "+detailGameName);
                        mGameNameTextView.setText(name);
                        mGameStatusTextView.setText(teamSnapshot.child("gameStatus").getValue(String.class));
                        String team1 = teamSnapshot.child("team1").getValue(String.class);
                        mTeam1TextView.setText(team1);
                        String team2 = teamSnapshot.child("team2").getValue(String.class);
                        mTeam2TextView.setText(team2);
                        String team3 = teamSnapshot.child("team3").getValue(String.class);
                        mTeam3TextView.setText(team3);
                        String team4 = teamSnapshot.child("team4").getValue(String.class);
                        mTeam4TextView.setText(team4);
                        String team5 = teamSnapshot.child("team5").getValue(String.class);
                        mTeam5TextView.setText(team5);
                        game = new Game(name);
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GameDetailsActivity.this,
                        "onCanceled error",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
