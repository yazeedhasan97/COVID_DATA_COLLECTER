package com.example.covidh1.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.covidh1.R;
import com.example.covidh1.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity {


    EditText etNID, etPass;
    Button btnSubmit;
    CheckBox cbRem;

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference userRef;

    String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNID = findViewById(R.id.et_nid);
        etPass = findViewById(R.id.et_pass);
        btnSubmit = findViewById(R.id.btn_submit);
        cbRem = findViewById(R.id.cb_rem);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("modifiers");


        User user = getPreference();
        if (user != null) {
            Intent in = new Intent(this, A2.class);
            startActivity(in);
            finish();
        }
    }

    public void nextPage(View view) {
        final String userName = etNID.getText().toString();
        final String pass = etPass.getText().toString().toUpperCase();
        if ((userName.trim().equals(""))) {
            Toast.makeText(getApplicationContext(), "User Name is required", Toast.LENGTH_LONG).show();
            return;
        }
        if ((userName.matches(".*[0-9]+.*"))) {
            Toast.makeText(getApplicationContext(), "User Name must not have numbers", Toast.LENGTH_LONG).show();
            return;
        }
        if ((pass.trim().equals(""))) {
            Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_LONG).show();
            return;
        }
        if ((pass.trim().length() < 5)) {
            Toast.makeText(getApplicationContext(), "Password is more than 5 character length", Toast.LENGTH_LONG).show();
            return;
        }

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue() != null) {
                        if (data.getKey().equals(userName) && String.valueOf(data.getValue()).equals(pass)) {
                            found = true;
                            break;
                        }
                    }
                }
                if (found) {
                    User user = new User(userName, pass);
                    if (cbRem.isChecked()) {
                        setPreference(user);
                    }
                    Intent in = new Intent(MainActivity.this, A2.class);
                    startActivity(in);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "User Not Found!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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

}
