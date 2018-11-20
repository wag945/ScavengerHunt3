package com.example.bill.scavengerhunt3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private Button mLoginButton;
    private Button mSignUpButton;
    private TextView mUserName;
    private TextView mPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        mLoginButton = (Button) findViewById(R.id.login_button);
        mSignUpButton = (Button) findViewById(R.id.signup_button);
        mUserName = (TextView) findViewById(R.id.username);
        mPassword = (TextView) findViewById(R.id.password);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            signInUser(mUserName.getText().toString(), mPassword.getText().toString());



            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null) {
//            Toast.makeText(MainActivity.this, "Current user: " + currentUser.getEmail(),
//                    Toast.LENGTH_LONG).show();
//            Intent myIntent = new Intent(MainActivity.this, GameLobbyActivity.class);
//            MainActivity.this.startActivity(myIntent);
//
//        }
    }


    private void signInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signed in with email", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.i("USER", user.getEmail());
                            Toast.makeText(MainActivity.this, "User : "+user.getEmail(),
                                    Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(MainActivity.this, GameLobbyActivity.class);
                            MainActivity.this.startActivity(myIntent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("failed to sign in", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "authentication failed.",
                                    Toast.LENGTH_LONG).show();

                            Toast.makeText(MainActivity.this, "Please sign up, or sign in", Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });

    }
}
