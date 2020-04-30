package com.example.covid2.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.covid2.R;
import com.example.covid2.adapters.RecycleAdapter;
import com.example.covid2.models.Deas;
import com.example.covid2.models.User;
import com.example.covid2.utilities.EqualSpacingItemDecoration;
import com.example.covid2.utilities.Utility;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class A2 extends AppCompatActivity {

    User user;
    FirebaseDatabase database;
    DatabaseReference userRef;

    List<String> dea;
    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("users");

        dea = new ArrayList<>();
        rec = findViewById(R.id.myRec);


        int spacing = 5;
        EqualSpacingItemDecoration equalSpacing = new EqualSpacingItemDecoration(spacing);
        rec.addItemDecoration(equalSpacing);
//        int mNoOfColumns = Utility.calculateNoOfColumns(this);

        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        RecycleAdapter recycleAdapter = new RecycleAdapter(this, prepareArray());
        rec.getRecycledViewPool().setMaxRecycledViews(0, 0);
        rec.setAdapter(recycleAdapter);

        if (getIntent().getExtras() != null) {
            user = (User) getIntent().getSerializableExtra("valObject");
            assert user != null;
        }


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

    public void submit(View view) {

        user.setDea(RecycleAdapter.index);
        if(user.getDea().size() == 0){
            List<String> e = new ArrayList<>();
            e.add("None");
            user.setDea(e);
        }
        userRef.child(user.getNid()).setValue(user);
        Intent in = new Intent(A2.this, A3.class);
        in.putExtra("valObject", user);
        startActivity(in);
        finish();
    }

    private ArrayList<Deas> prepareArray() {
        ArrayList<Deas> deasa = new ArrayList<>();
        Deas p1 = new Deas("Other", R.drawable.unchecked);
        deasa.add(p1);

        p1 = new Deas("Allergic", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Asthma", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Addiction", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Alzheimer's", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Alopecia", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Arthritis", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Chronic Kidney", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Cancer", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Crohn's", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Diabetes", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Dengue", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Epilepsy", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Fatal familial insomnia", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Hepatitis B", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("HIV/AIDS", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Irritable bowel syndrome", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Joint pain", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Leukemia", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Systemic lupus erythematosus", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Rabies", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Polio", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Psoriasis", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("Pulmonary fibrosis", R.drawable.unchecked);
        deasa.add(p1);
        p1 = new Deas("None", R.drawable.unchecked);
        deasa.add(p1);


        return deasa;
    }
}
