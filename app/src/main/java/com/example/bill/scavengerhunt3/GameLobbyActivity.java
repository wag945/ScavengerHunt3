package com.example.bill.scavengerhunt3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameLobbyActivity extends AppCompatActivity {

    private Button mTeamLobbyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        mTeamLobbyButton = (Button) findViewById(R.id.teamLobbyButton);
        mTeamLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(GameLobbyActivity.this, TeamLobbyActivity.class);
                GameLobbyActivity.this.startActivity(myIntent);
            }
        });
    }
}
