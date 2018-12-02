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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import android.content.BroadcastReceiver;

public class joinGameActivity1 extends AppCompatActivity {

    private static final int ITEM1 = 1;
    private static final int ITEM2 = 2;
    private static final int ITEM3 = 3;
    private static final int ITEM4 = 4;
    private static final int ITEM5 = 5;
    private android.widget.Button itemButton1;
    private android.widget.Button itemButton2;
    private android.widget.Button itemButton3;
    private android.widget.Button itemButton4;
    private android.widget.Button itemButton5;
    private Button leaderBoardButton;
    private Button returnToGameLobbyButton;
    private android.widget.TextView textView;
    private android.widget.TextView timerText;
    private android.widget.TextView textView3;
    private CameraExecutor CameraExcutor;
    private ImageView imageView;
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
    private Button leaderboard;
    private StorageReference mStorage;
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
    private int count;
    private String timerTextStr= "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        gamesRef = database.getReference("Games");

        mProgressDialog = new ProgressDialog(this);

        itemButton1 = findViewById(R.id.itemButton1);
        itemButton2 = findViewById(R.id.itemButton2);
        itemButton3 = findViewById(R.id.itemButton3);
        itemButton4 = findViewById(R.id.itemButton4);
        itemButton5 = findViewById(R.id.itemButton5);

        leaderBoardButton = findViewById(R.id.leaderboardButton);

        returnToGameLobbyButton = findViewById(R.id.returnToGameLobbyButton);
        returnToGameLobbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(joinGameActivity1.this, GameLobbyActivity.class);
                joinGameActivity1.this.startActivity(myIntent);
            }
        });
        returnToGameLobbyButton.setVisibility(View.INVISIBLE);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);

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


        //getting the game timer from the database
        DatabaseReference dbRef = database.getReference("Games");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot gameSnapshot: dataSnapshot.getChildren()) {
                    String name = gameSnapshot.child("gameName").getValue(String.class);
                    if (name != null && name.equals(gameName)) {
                        Log.d("StartGameActivity","timer = " + gameSnapshot.child("timerView").getValue(String.class));
                        if (gameSnapshot.child("timerView").getValue(String.class).toString().equals("00:01")) {
                            timerText.setText("ENDED");
                        }
                        else {
                           timerText.setText(gameSnapshot.child("timerView").getValue(String.class));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageView = findViewById(R.id.imageView);
        //  mSave = findViewById(R.id.save);


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
            } else  {
                // Launch the camera if the permission exists

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, ITEM1);

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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, ITEM2);
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, ITEM3);
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, ITEM4);
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent, ITEM5);
            }
        });

        leaderboard = (Button) findViewById(R.id.leaderboardButton);

        leaderboard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent myIntent = new Intent(joinGameActivity1.this, LeaderboardActivity.class);
                myIntent.putExtra("gameName", gameName);
                joinGameActivity1.this.startActivity(myIntent);

            }
        });


  /*      mSave.setOnClickListener((View v) -> {
            CameraExcutor.diskIO().execute(() -> {
                // Delete the temporary image file
                BitmapUtils.deleteImageFile(this, mTempPhotoPath);

                // Save the image
                BitmapUtils.saveImage(this, mResultsBitmap);

            });

            Toast.makeText(this, "Scavenged Item", Toast.LENGTH_LONG).show();

        });

        */
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
    protected void onActivityResult ( int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // If the image capture activity was called and was successful

        switch (requestCode) {
            case ITEM1:

                if (requestCode == 1 && resultCode == RESULT_OK) ;
            {

                mProgressDialog.setMessage("Scavenving Item...");
                mProgressDialog.show();


                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataBAOS = baos.toByteArray();

                imageView.setImageBitmap(bitmap);

                StorageReference storageRef = FirebaseStorage.getInstance().getReference("Item 1");

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

                        Toast.makeText(joinGameActivity1.this, "ITEM SCAVENGED!", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        checkBox1.setChecked(true);


                        count++;


                        if (count == 5) {
                            completeGame();
                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0) {
                                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                    String key = nodeDataSnapshot.getKey();
                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                    HashMap<String, Object> result = new HashMap<>();
                                    nodeDataSnapshot.getRef().child("teamList").child("2").child("teamScavengeList").child("0").child("found").setValue(true);
                                    myRef.child(path).updateChildren(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }});

                break;

            }
            case ITEM2:

                if (requestCode == 2 && resultCode == RESULT_OK) ;
            {

                mProgressDialog.setMessage("Scavenving Item...");
                mProgressDialog.show();


                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataBAOS = baos.toByteArray();

                imageView.setImageBitmap(bitmap);

                StorageReference storageRef = FirebaseStorage.getInstance().getReference("Item 2");

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

                        Toast.makeText(joinGameActivity1.this, "ITEM SCAVENGED!", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        checkBox2.setChecked(true);

                        count ++;

                        if (count == 5){
                            completeGame();

                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0) {
                                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                    String key = nodeDataSnapshot.getKey();
                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                    HashMap<String, Object> result = new HashMap<>();
                                    nodeDataSnapshot.getRef().child("teamList").child("2").child("teamScavengeList").child("1").child("found").setValue(true);
                                    myRef.child(path).updateChildren(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }



                });

                break;
            }
            case ITEM3:

                if (requestCode == 3 && resultCode == RESULT_OK) ;
            {

                mProgressDialog.setMessage("Scavenving Item...");
                mProgressDialog.show();


                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataBAOS = baos.toByteArray();

                imageView.setImageBitmap(bitmap);

                StorageReference storageRef = FirebaseStorage.getInstance().getReference("Item 3");

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

                        Toast.makeText(joinGameActivity1.this, "ITEM SCAVENGED!", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        checkBox3.setChecked(true);

                        count ++;

                        if (count == 5){
                            completeGame();

                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0) {
                                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                    String key = nodeDataSnapshot.getKey();
                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                    HashMap<String, Object> result = new HashMap<>();
                                    nodeDataSnapshot.getRef().child("teamList").child("2").child("teamScavengeList").child("2").child("found").setValue(true);
                                    myRef.child(path).updateChildren(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }






                }); break;

            }

            case ITEM4:

                if (requestCode == 4 && resultCode == RESULT_OK) ;
            {

                mProgressDialog.setMessage("Scavenving Item...");
                mProgressDialog.show();


                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataBAOS = baos.toByteArray();

                imageView.setImageBitmap(bitmap);

                StorageReference storageRef = FirebaseStorage.getInstance().getReference("Item 4");

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

                        Toast.makeText(joinGameActivity1.this, "ITEM SCAVENGED!", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        checkBox4.setChecked(true);

                        count ++;

                        if (count == 5){
                            completeGame();

                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0) {
                                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                    String key = nodeDataSnapshot.getKey();
                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                    HashMap<String, Object> result = new HashMap<>();
                                    nodeDataSnapshot.getRef().child("teamList").child("2").child("teamScavengeList").child("3").child("found").setValue(true);
                                    myRef.child(path).updateChildren(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }




                }); break;

            }

            case ITEM5:

                if (requestCode == 5 && resultCode == RESULT_OK) ;
            {

                mProgressDialog.setMessage("Scavenving Item...");
                mProgressDialog.show();


                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataBAOS = baos.toByteArray();

                imageView.setImageBitmap(bitmap);

                StorageReference storageRef = FirebaseStorage.getInstance().getReference("Item 5");

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

                        Toast.makeText(joinGameActivity1.this, "ITEM SCAVENGED!", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                        checkBox5.setChecked(true);

                        count ++;

                        if (count == 5){
                            completeGame();

                        }

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference();

                        Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getChildrenCount() > 0)  {
                                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                                    String key = nodeDataSnapshot.getKey();
                                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                                    HashMap<String, Object> result = new HashMap<>();
                                    nodeDataSnapshot.getRef().child("teamList").child("2").child("teamScavengeList").child("4").child("found").setValue(true);
                                    myRef.child(path).updateChildren(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }

                        });

                    }




                }); break;

            }
        }

    }
    //* Creates a temporary image file and captures a picture to store in it.

    private void launchCamera () {

        // Create the capture image intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                // Launch the camera activity
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    /**
     * Method for processing the captured image and setting it to the TextView.
     */

    private void processAndSetImage () {

        // Toggle Visibility of the views

        imageView.setVisibility(View.VISIBLE);

        mResultsBitmap = BitmapUtils.resamplePic(this, mTempPhotoPath);

        // Set the new bitmap to the ImageView
        imageView.setImageBitmap(mResultsBitmap);
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

        //Change visibility of buttons
        itemButton1.setVisibility(View.VISIBLE);
        itemButton2.setVisibility(View.VISIBLE);
        itemButton3.setVisibility(View.VISIBLE);
        itemButton4.setVisibility(View.VISIBLE);
        itemButton5.setVisibility(View.VISIBLE);
        checkBox1.setVisibility(View.VISIBLE);
        checkBox2.setVisibility(View.VISIBLE);
        checkBox3.setVisibility(View.VISIBLE);
        checkBox4.setVisibility(View.VISIBLE);
        checkBox5.setVisibility(View.VISIBLE);
        leaderBoardButton.setVisibility(View.VISIBLE);
        returnToGameLobbyButton.setVisibility(View.INVISIBLE);
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
            else {
                seconds = secondsRemaining;
            }
            Log.d("StartGameActivity","minutes = "+minutes+" seconds = "+seconds);
//            String timerTextStr= "";

            if (seconds < 10) {
                if (minutes < 1) {
                    timerTextStr = "00:0" + Long.toString(seconds);
                }
                else {
                    timerTextStr = Long.toString(minutes) + ":0" + Long.toString(seconds);
                }
                timerText.setText(timerTextStr);
                if (minutes < 1 && secondsRemaining <= 1) {
                    completeGame();
                }
            } else {
                if (minutes >= 1) {
                    timerTextStr = Long.toString(minutes) + ":" + Long.toString(seconds);
                }
                else {
                    timerTextStr = "00:" + Long.toString(seconds);
                }
//                timerText.setText(timerTextStr);
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();

            Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount() > 0) {
                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                        String key = nodeDataSnapshot.getKey();
                        String path = "/" + dataSnapshot.getKey() + "/" + key;
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("timerView",timerTextStr);
                        myRef.child(path).updateChildren(result);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.i("StartGameActivity", ">>> Error:" + "find onCancelled:" + databaseError);
                }
            });
//            gamesRef.child("gameTimer").setValue(timerTextStr);
        }
    }

    private void completeGame() {
        //Update the timer to show game is ENDED
        timerText.setText("ENDED");

        //Change visibility of buttons
        itemButton1.setVisibility(View.INVISIBLE);
        itemButton2.setVisibility(View.INVISIBLE);
        itemButton3.setVisibility(View.INVISIBLE);
        itemButton4.setVisibility(View.INVISIBLE);
        itemButton5.setVisibility(View.INVISIBLE);
        checkBox1.setVisibility(View.INVISIBLE);
        checkBox2.setVisibility(View.INVISIBLE);
        checkBox3.setVisibility(View.INVISIBLE);
        checkBox4.setVisibility(View.INVISIBLE);
        checkBox5.setVisibility(View.INVISIBLE);

        leaderBoardButton.setVisibility(View.INVISIBLE);
        returnToGameLobbyButton.setVisibility(View.VISIBLE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        Query query = myRef.child("Games").orderByChild("gameName").equalTo(gameName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                    String key = nodeDataSnapshot.getKey();
                    String path = "/" + dataSnapshot.getKey() + "/" + key;
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("gameStatus","ENDED");
                    myRef.child(path).updateChildren(result);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("StartGameActivity", ">>> Error:" + "find onCancelled:" + databaseError);
            }
        });
    }
}


