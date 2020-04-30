package com.example.covidh1.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.covidh1.R;
import com.example.covidh1.adapters.RecycleAdapter;
import com.example.covidh1.models.Person;
import com.example.covidh1.utilities.EqualSpacingItemDecoration;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecoveredUsers extends Fragment {


    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference userRef;
    String TAG = "Infected Users";
    List<String> nids;
    RecyclerView rec;

    public RecoveredUsers() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        userRef = myRef.child("users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.recovered_users_fragment, container, false);

        nids = new ArrayList<>();
        rec = v.findViewById(R.id.recRes);
        int spacing = 5;
        EqualSpacingItemDecoration equalSpacing = new EqualSpacingItemDecoration(spacing);
        rec.addItemDecoration(equalSpacing);


        rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
        RecycleAdapter recycleAdapter = new RecycleAdapter(getActivity(), prepareArray());
        rec.getRecycledViewPool().setMaxRecycledViews(0, 0);
        rec.setAdapter(recycleAdapter);


        return v;
    }

    private ArrayList<String> prepareArray() {
        final ArrayList<String> nids = new ArrayList<>();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getValue(Person.class).getState().equals("Recovered")){
                        nids.add(data.getValue(Person.class).getNid());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        return nids;
    }


}
