package com.example.bill.scavengerhunt3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.BroadcastReceiver;

public class StartGameActivity extends AppCompatActivity {

    private android.widget.Button itemButton1;
    private android.widget.Button itemButton2;
    private android.widget.Button itemButton3;
    private android.widget.Button itemButton4;
    private android.widget.Button itemButton5;
    private android.widget.TextView textView;
    private android.widget.TextView timerText;
    private android.widget.TextView textView3;
    private CameraExecutor CameraExcutor;
    private ImageView mImageView;
    private Bitmap mResultsBitmap;
    private String mTempPhotoPath;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private FloatingActionButton mSave;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.bill.scavengerhunt3";
    private Game mGame;
    private DatabaseReference gamesRef;
    private String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        gamesRef = database.getReference("Games");

        itemButton1 = findViewById(R.id.itemButton1);
        itemButton2 = findViewById(R.id.itemButton2);
        itemButton3 = findViewById(R.id.itemButton3);
        itemButton4 = findViewById(R.id.itemButton4);
        itemButton5 = findViewById(R.id.itemButton5);
        CameraExcutor = new CameraExecutor();

        gameName = getIntent().getStringExtra("gameName");

        //getting the game from the database
        DatabaseReference gamesRef = database.getReference("Games");

        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot gameSnapshot: dataSnapshot.getChildren()) {
                    String name = gameSnapshot.child("gameName").getValue(String.class);
                    System.out.println("ZEREGA gameSnapshot Gamename: " + name);
                    System.out.println("ZEREGA gameName from Intent: " + gameName);
                    if (name != null && name.equals(gameName)){
                        //String team1 = teamSnapshot.child("team1").getValue(String.class);
                        itemButton1.setText(gameSnapshot.child("scavengeList").child("0").child("name").getValue(String.class));
                        itemButton2.setText(gameSnapshot.child("scavengeList").child("1").child("name").getValue(String.class));
                        itemButton3.setText(gameSnapshot.child("scavengeList").child("2").child("name").getValue(String.class));
                        itemButton4.setText(gameSnapshot.child("scavengeList").child("3").child("name").getValue(String.class));
                        itemButton5.setText(gameSnapshot.child("scavengeList").child("4").child("name").getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        itemButton1.setText(scavengeItems.get(0).toString());
//        itemButton2.setText(scavengeItems.get(1).toString());
//        itemButton3.setText(scavengeItems.get(2).toString());
//        itemButton4.setText(scavengeItems.get(3).toString());
//        itemButton5.setText(scavengeItems.get(4).toString());

        mImageView = findViewById(R.id.imageView);
        mSave = findViewById(R.id.save);

        mImageView.setVisibility(View.GONE);

        timerText = (TextView) findViewById(R.id.textView2);
        timerText.setText("00:00");

        itemButton1.setOnClickListener(v -> {
            // Check for the external storage permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                // Launch the camera if the permission exists
                launchCamera();
            }
        });
        itemButton2.setOnClickListener(v -> {
            // Check for the external storage permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                // Launch the camera if the permission exists
                launchCamera();
            }
        });

        itemButton3.setOnClickListener(v -> {
            // Check for the external storage permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                // Launch the camera if the permission exists
                launchCamera();
            }
        });

        itemButton4.setOnClickListener(v -> {
            // Check for the external storage permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                // Launch the camera if the permission exists
                launchCamera();
            }
        });

        itemButton5.setOnClickListener(v -> {
            // Check for the external storage permission
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // If you do not have permission, request it
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_STORAGE_PERMISSION);
            } else {
                // Launch the camera if the permission exists
                launchCamera();
            }
        });


        mSave.setOnClickListener((View v) -> {
            CameraExcutor.diskIO().execute(() -> {
                // Delete the temporary image file
                BitmapUtils.deleteImageFile(this, mTempPhotoPath);

                // Save the image
                BitmapUtils.saveImage(this, mResultsBitmap);

            });

            Toast.makeText(this, "Scavenged Item", Toast.LENGTH_LONG).show();

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
    @NonNull int[] grantResults){
        // Called when you request permission to read and write to external storage
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If you get permission, launch the camera
                    launchCamera();
                } else {
                    // If you do not get permission, show a Toast
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        // If the image capture activity was called and was successful
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Process the image and set it to the TextView
            processAndSetImage();
        } else {

            // Otherwise, delete the temporary image file
            BitmapUtils.deleteImageFile(this, mTempPhotoPath);
        }
    }

     //* Creates a temporary image file and captures a picture to store in it.

    private void launchCamera () {

        // Create the capture image intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the temporary File where the photo should go
            File photoFile = null;
            try {
                photoFile = BitmapUtils.createTempImageFile(this);
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                // Get the path of the temporary file
                mTempPhotoPath = photoFile.getAbsolutePath();

                // Get the content URI for the image file
                Uri photoURI = FileProvider.getUriForFile(this,
                        FILE_PROVIDER_AUTHORITY,
                        photoFile);

                // Add the URI so the camera can store the image
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */

    private void processAndSetImage () {

        // Toggle Visibility of the views

        mImageView.setVisibility(View.VISIBLE);

        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);

        // Set the new bitmap to the ImageView
        mImageView.setImageBitmap(mResultsBitmap);
    }

    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i("StartGameActivity", "Registered broacast receiver");
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);
        Log.i("StartGameActivity", "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
        }
        super.onStop();
    }
    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i("StartGameActivity", "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i("StartGameActivity", "Countdown seconds remaining: " +  millisUntilFinished / 1000);
            long secondsRemaining = millisUntilFinished/1000;
            long minutes = 0;
            long seconds = 0;
            if (secondsRemaining >= 60)
            {
                minutes = secondsRemaining / 60;
                seconds = secondsRemaining % 60;
            }

            String timerTextStr= "";

            if (seconds < 10) {
                timerTextStr = Long.toString(minutes)+":0"+Long.toString(seconds);
                timerText.setText(timerTextStr);
            }
            else {
                timerTextStr = Long.toString(minutes)+":"+Long.toString(seconds);
                timerText.setText(timerTextStr);
            }

            gamesRef.child("gameTimer").setValue(timerTextStr);
        }
    }
}


