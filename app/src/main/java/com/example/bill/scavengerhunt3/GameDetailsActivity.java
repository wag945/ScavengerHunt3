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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class GameDetailsActivity extends AppCompatActivity {

    private Button mJoinGameButton;
    private Button mJoinGameButton1;
    private Button mJoinGameButton2;
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
        mJoinGameButton1 = (Button) findViewById(R.id.joinGameButton1);
        mJoinGameButton2 = (Button) findViewById(R.id.joinGameButton2);
        mJoinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(GameDetailsActivity.this, BroadcastService.class));
                Log.i("GameDetailsActivity", "Started service");

                Intent myIntent = new Intent(GameDetailsActivity.this, joinGameActivity.class);
                myIntent.putExtra("gameName", detailGameName);
                startActivity(myIntent);
                mJoinGameButton.setVisibility(View.GONE);
                mJoinGameButton1.setVisibility(View.VISIBLE);
            }
        });

        mJoinGameButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(GameDetailsActivity.this, BroadcastService.class));
                Log.i("GameDetailsActivity", "Started service");
                Intent myIntent = new Intent(GameDetailsActivity.this, joinGameActivity1.class);
                myIntent.putExtra("gameName", detailGameName);
                startActivity(myIntent);
               mJoinGameButton1.setVisibility(View.GONE);
               mJoinGameButton2.setVisibility(View.VISIBLE);
            }
        });

        mJoinGameButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(GameDetailsActivity.this, BroadcastService.class));
                Log.i("GameDetailsActivity", "Started service");
                Intent myIntent = new Intent(GameDetailsActivity.this, joinGameActivity2.class);
                myIntent.putExtra("gameName", detailGameName);
                startActivity(myIntent);
                mJoinGameButton2.setVisibility(View.GONE);
                mJoinGameButton1.setVisibility(View.GONE);
                mJoinGameButton.setVisibility(View.GONE);


            }
        });


        mStartGameButton = (Button) findViewById(R.id.startGameButton);
        mStartGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                Query query = myRef.child("Games").orderByChild("gameName").equalTo(detailGameName);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() > 0) {
                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            String key = nodeDataSnapshot.getKey();
                            String path = "/" + dataSnapshot.getKey() + "/" + key;
                            HashMap<String, Object> result = new HashMap<>();
                            result.put("gameStatus","IN_PROGRESS");
                            myRef.child(path).updateChildren(result);

//                            startService(new Intent(GameDetailsActivity.this, BroadcastService.class));
//                            Log.i("GameDetailsActivity", "Started service");

                            Intent myIntent = new Intent(GameDetailsActivity.this, StartGameActivity.class);
                            myIntent.putExtra("gameName", detailGameName);
                            startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.i("MainActivity", ">>> Error:" + "find onCancelled:" + databaseError);

                    }
                });

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
                        //Set visibility of Join Game button
                        if (teamSnapshot.child("gameStatus").getValue().toString().equals("IN_PROGRESS")) {
                            mJoinGameButton.setVisibility(View.VISIBLE);
                        }
                        else {
                            mJoinGameButton.setVisibility(View.INVISIBLE);
                        }
                        //Set visibility of Start Game button
                        if (teamSnapshot.child("gameStatus").getValue().toString().equals("NOT_STARTED") ||
                                teamSnapshot.child("gameStatus").getValue().toString().equals("ENDED")) {
                            mStartGameButton.setVisibility(View.VISIBLE);
                        }
                        else {
                            mStartGameButton.setVisibility(View.INVISIBLE);
                        }


                        Log.d("GameDetailsActivity","teamSnapshot = "+teamSnapshot.toString());
                        String numTeamsStr = teamSnapshot.child("numTeams").getValue().toString();
                        int numTeams = Integer.parseInt(numTeamsStr);
                        Log.d("GameDetailsActivity","numTeams = "+numTeams);
                        for (int i = 0; i < numTeams; i++) {
                            Log.d("GameDetailsActivity","team"+i+" = "+teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").getValue().toString());
                            switch(i) {
                                case 0:
                                    mTeam1TextView.setText(teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").getValue().toString());
                                    break;
                                case 1:
                                    mTeam2TextView.setText(teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").getValue().toString());
                                    break;
                                case 2:
                                    mTeam3TextView.setText(teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").getValue().toString());
                                    break;
                                case 3:
                                    mTeam4TextView.setText(teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").getValue().toString());
                                    break;
                                case 4:
                                    mTeam5TextView.setText(teamSnapshot.child("teamList").child(Integer.toString(i)).child("name").getValue().toString());
                                    break;
                            }
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
