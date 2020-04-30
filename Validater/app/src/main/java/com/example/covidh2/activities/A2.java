package com.example.covidh2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.widget.Toast;


import com.example.covidh2.R;
import com.example.covidh2.models.Person;
import com.example.covidh2.models.User;
import com.example.covidh2.utilities.MyGLRenderer;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class A2 extends AppCompatActivity {

    GLSurfaceView glSurfaceView;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference userRef;

    String TAG = "A2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        glSurfaceView = findViewById(R.id.camera_preview);
//        textViewQR = findViewById(R.id.txtQR);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();


        glSurfaceView.setRenderer(new MyGLRenderer());
        glSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    cameraSource.start(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0) {
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(500);

                    String nid = qrCodes.valueAt(0).displayValue.split(",")[0];
                    String state = qrCodes.valueAt(0).displayValue.split(",")[1];
//                    Intent in = new Intent(A2.this, A3.class);
//                    in.putExtra("valObject", p);
//                    in.putExtra("qrText", qrText);
//                    startActivity(in);
//                    finish();

                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                if (data.child(nid).getValue() != null) {
                                    userRef.child(nid).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            Person user = dataSnapshot.getValue(Person.class);
                                            Log.e(TAG, "IF " + user);
                                            Intent in = new Intent(A2.this, A3.class);
                                            in.putExtra("valObject", user);
                                            startActivity(in);
                                            finish();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                } else {
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                }

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("user");
        editor.apply();
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(myIntent);
        finish();
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}
