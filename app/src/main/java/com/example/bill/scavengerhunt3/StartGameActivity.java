package com.example.bill.scavengerhunt3;

import android.Manifest;
import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    private static final int CAMERA_REQUEST_CODE = 1;
    private ProgressDialog mProgressDialog;
    private FloatingActionButton mSave;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.bill.scavengerhunt3";
    private Game mGame;
    private DatabaseReference gamesRef;
    private String gameName;
    private ProgressDialog mProgress;
    private StorageReference mStorage;
    private ImageView check1;
    private ImageView check2;
    private ImageView check3;
    private ImageView check4;
    private ImageView check5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();



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





        ImageView check1 = findViewById(R.id.check1);
        ImageView check2 = findViewById(R.id.check2);
        ImageView check3 = findViewById(R.id.check3);
        ImageView check4 = findViewById(R.id.check4);
        ImageView check5 = findViewById(R.id.check5);


        mProgressDialog = new ProgressDialog(this);


        timerText = (TextView) findViewById(R.id.textView2);
        timerText.setText("00:00");

        itemButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                check1.setVisibility(View.VISIBLE);




            }
        });



        itemButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                check2.setVisibility(View.VISIBLE);

            }
        });


        itemButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                check3.setVisibility(View.VISIBLE);

            }
        });

        itemButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                check4.setVisibility(View.VISIBLE);

            }
        });

        itemButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, CAMERA_REQUEST_CODE);

                check5.setVisibility(View.VISIBLE);

            }
        });



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {


            Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
            mProgressDialog.setMessage("Scavenving Item...");
            mProgressDialog.show();


            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://scavenge-40a75.appspot.com");

            StorageReference imagesRef = storageRef.child("img" + new Date().getTime());

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);



            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(), "Unable to Scavenge", Toast.LENGTH_SHORT).show();
                }


            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                    Toast.makeText(StartGameActivity.this, "ITEM SCAVENGED!", Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();


                }



            });


        }
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


