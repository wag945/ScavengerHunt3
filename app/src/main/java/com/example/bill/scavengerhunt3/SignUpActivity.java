package com.example.bill.scavengerhunt3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bill.scavengerhunt3.entity.dao.UserProfilePersistence;
import com.example.bill.scavengerhunt3.entity.entity.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private Button mConfirm;
    private EditText mBirthday;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mUserName;
    private EditText mPhoneNumber;
    private EditText mEmail;
    private EditText mCreatePassword;
    private UserProfilePersistence database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mConfirm = (Button) findViewById(R.id.confirm);
        mFirstName = (EditText) findViewById(R.id.firstName);
        mLastName = (EditText) findViewById(R.id.lastName);
        mBirthday = (EditText) findViewById(R.id.birthday);
        mUserName = (EditText) findViewById(R.id.userName);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mEmail = (EditText) findViewById(R.id.email);
        mCreatePassword = (EditText) findViewById(R.id.createPassword);

        mAuth = FirebaseAuth.getInstance();

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add users profile to the database

                database  = new UserProfilePersistence(SignUpActivity.this);

                String firstName = mFirstName.getText().toString();
                String lastName = mLastName.getText().toString();
                String birthday = mBirthday.getText().toString();
                String userName = mUserName.getText().toString();
                String phoneNumber = mPhoneNumber.getText().toString();
                String email = mEmail.getText().toString();
                String password = mCreatePassword.getText().toString();

                UserProfile newUserProfile = new UserProfile(firstName, lastName, userName, birthday, phoneNumber,
                        email, password);

                createAccount(newUserProfile.getEmail(), newUserProfile.getPassword());

                Log.d("inserting into database", newUserProfile.toString());
                database.insert(newUserProfile);


                //create the intent with extras for personalized greeting
                Intent logInIntent = new Intent(SignUpActivity.this, GameLobbyActivity.class);


                SignUpActivity.this.startActivity(logInIntent);


            }
        });


    }

    private void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("firebase create email", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast toast = Toast.makeText(getApplicationContext(), "User Successfully Created", Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("firebase email fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }
}