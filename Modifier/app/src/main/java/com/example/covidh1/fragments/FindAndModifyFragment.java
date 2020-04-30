package com.example.covidh1.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covidh1.R;
import com.example.covidh1.activities.MainActivity;
import com.example.covidh1.models.Person;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class FindAndModifyFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference userRef;

    String TAG = "MainActivity";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_find_and_modify, container, false);
        Button btnCheck = v.findViewById(R.id.btnCheck);
        Button btnSetInfected = v.findViewById(R.id.btnSetInfected);
        Button btnSetRecovered = v.findViewById(R.id.btnSetRecovered);
        Button btnLogout = v.findViewById(R.id.btnLogout);
        final EditText etNid = v.findViewById(R.id.etNid);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("user");
                editor.apply();


                Intent in = new Intent(getActivity(), MainActivity.class);
                startActivity(in);
                getActivity().finish();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                final String nid = etNid.getText().toString();
                if (nid.isEmpty()) {
                    Toast.makeText(getActivity(), "NID is required", Toast.LENGTH_SHORT);
                    return;
                }
                if (!nid.matches("[0-9]+")) {
                    Toast.makeText(getActivity(), "NID must be all numbers", Toast.LENGTH_SHORT);
                    return;
                }
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e(TAG, "What The F " + dataSnapshot.child(nid).exists());
                        if (dataSnapshot.child(nid).exists()) {
                            Log.e(TAG, "What The F " + dataSnapshot.child(nid).getValue(Person.class));
                            Person person = dataSnapshot.child(nid).getValue(Person.class);
                            Toast.makeText(getActivity(), nid + " State is" + person.getState(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), nid + " not existed in the system", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


        btnSetInfected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nid = etNid.getText().toString();
                if (nid.isEmpty()) {
                    Toast.makeText(getActivity(), "NID is required", Toast.LENGTH_SHORT);
                    return;
                }
                if (!nid.matches("[0-9]+")) {
                    Toast.makeText(getActivity(), "NID must be all numbers", Toast.LENGTH_SHORT);
                    return;
                }
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e(TAG, "What The F " + dataSnapshot.child(nid).exists());
                        if (dataSnapshot.child(nid).exists()) {
                            Log.e(TAG, "What The F " + dataSnapshot.child(nid).getValue(Person.class));
                            Person person = dataSnapshot.child(nid).getValue(Person.class);
                            person.setState("Infected");
                            if(person.getDea().size() == 0){
                                person.setDea(new ArrayList<String>());
                            }
                            userRef.child(nid).setValue(person);
                            Toast.makeText(getActivity(), nid + " become infected successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), nid + " not existed in the system", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


        btnSetRecovered.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                final String nid = etNid.getText().toString();
                if (nid.isEmpty()) {
                    Toast.makeText(getActivity(), "NID is required", Toast.LENGTH_SHORT);
                    return;
                }
                if (!nid.matches("[0-9]+")) {
                    Toast.makeText(getActivity(), "NID must be all numbers", Toast.LENGTH_SHORT);
                    return;
                }
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.e(TAG, "What The F " + dataSnapshot.child(nid).exists());
                        if (dataSnapshot.child(nid).exists()) {
                            Log.e(TAG, "What The F " + dataSnapshot.child(nid).getValue(Person.class));
                            Person person = dataSnapshot.child(nid).getValue(Person.class);
                            person.setState("Recovered");
                            if(person.getDea().size() == 0){
                                person.setDea(new ArrayList<String>());
                            }
                            userRef.child(nid).setValue(person);
                            Toast.makeText(getActivity(), nid + " become recovered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), nid + " not existed in the system", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });


        return v;
    }
}
