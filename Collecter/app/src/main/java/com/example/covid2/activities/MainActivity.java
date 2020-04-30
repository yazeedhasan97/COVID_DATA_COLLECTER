package com.example.covid2.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.covid2.R;
import com.example.covid2.models.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    EditText etNID, etCardID;
    CheckBox cbRem;
    Button btnSubmit;
    public static int REQUEST_LOCATION_PERMISSION = 1;


    private double longitude, latitude;
    LocationManager locationManager;
    LocationListener locationListener;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference userRef;
    User user;
    String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");


        etNID = findViewById(R.id.et_nid);
        etCardID = findViewById(R.id.et_cid);
        btnSubmit = findViewById(R.id.btn_submit);
        cbRem = findViewById(R.id.cbRem);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("onLocationChanged: ", location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        setManyPermissions();
        user = getPreference();
        if (user != null) {
            Intent in = new Intent(this, A3.class);
            in.putExtra("valObject", user);
            startActivity(in);
            finish();
        }

    }

    public void nextPage(View v) {
        String nid = etNID.getText().toString();
        String cardid = etCardID.getText().toString().toUpperCase();
        if ((nid.trim().equals(""))) {
            Toast.makeText(getApplicationContext(), "National ID number is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if ((nid.trim().length() != 10)) {
            Toast.makeText(getApplicationContext(), "National ID number is incorrect", Toast.LENGTH_LONG).show();
            return;
        }
        if (!(nid.matches("[0-9]+"))) {
            Toast.makeText(getApplicationContext(), "National ID must be all numbers", Toast.LENGTH_LONG).show();
            return;
        }
        if ((cardid.trim().equals(""))) {
            Toast.makeText(getApplicationContext(), "Card ID number is empty", Toast.LENGTH_LONG).show();
            return;
        }
        if ((cardid.trim().length() != 8)) {
            Toast.makeText(getApplicationContext(), "Card ID number is incorrect", Toast.LENGTH_LONG).show();
            return;
        }
        if (!(cardid.matches("[A-Z]+[0-9]+"))) {
            Toast.makeText(getApplicationContext(), "Card ID number must have letters and numbers", Toast.LENGTH_LONG).show();
            return;
        }


        // Read from the database
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                if (dataSnapshot.child(nid).exists()) {
                    if (!dataSnapshot.child(nid).getValue().toString().contains(cardid.toUpperCase())) {
                        Toast.makeText(MainActivity.this, "Password is incorrect", Toast.LENGTH_SHORT);
                        return;
                    }
                    userRef.child(nid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            User user = dataSnapshot.getValue(User.class);
                            Log.e(TAG, "IF " + user);

                            if (cbRem.isChecked()) {
                                setPreference(user);
                            }

                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent in = new Intent(MainActivity.this, A3.class);
                            in.putExtra("valObject", user);
                            startActivity(in);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                } else {
                    user = new User(nid, cardid);
                    user.setLang(longitude);
                    user.setLat(latitude);
                    user.setState("Healthy");
                    Log.e(TAG, "Else " + user);
                    Toast.makeText(MainActivity.this, "Saving successful!", Toast.LENGTH_LONG).show();
                    if (cbRem.isChecked()) {
                        setPreference(user);
                    }
                    Intent in = new Intent(MainActivity.this, A2.class);
                    in.putExtra("valObject", user);
                    startActivity(in);


                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void setPreference(User user) {
        Gson gson = new Gson();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        String json = gson.toJson(user);
        editor.putString("user", json);
        editor.apply();
    }

    public User getPreference() {
        Gson gson = new Gson();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
        String json = pref.getString("user", "");
        User user = gson.fromJson(json, User.class);
        return user;
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                assert locationManager != null;
                List<String> providers = locationManager.getAllProviders();

                Location l = null;
                for (int i = providers.size() - 1; i >= 0; i--) {
                    l = locationManager.getLastKnownLocation(providers.get(i));
                    Log.w("LO1", "" + l);
                    if (l != null) break;
                }

                if (l != null) {
                    latitude = l.getLatitude();
                    longitude = l.getLongitude();
                }
            }
        }
    }

    private void setManyPermissions() {
        String[] PERMISSIONS = {
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        Context context = MainActivity.this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            for (String permission : PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    requestLocationPermission(context, permission);
                } else {
                    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                    assert locationManager != null;
                    List<String> providers = locationManager.getAllProviders();

                    Location l = null;
                    for (int i = providers.size() - 1; i >= 0; i--) {
                        l = locationManager.getLastKnownLocation(providers.get(i));
                        Log.w("LO1", "" + l);
                        if (l != null) break;
                    }

                    if (l != null) {
                        latitude = l.getLatitude();
                        longitude = l.getLongitude();
                    }
                }
            }
        }
    }

    private void requestLocationPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Permission is needed for app to work correctly")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, REQUEST_LOCATION_PERMISSION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_LOCATION_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, REQUEST_LOCATION_PERMISSION);
        }
    }


}
