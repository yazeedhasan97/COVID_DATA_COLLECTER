package com.example.covid2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.covid2.R;
import com.example.covid2.models.User;
import com.google.zxing.WriterException;

import java.util.Objects;

public class A3 extends AppCompatActivity {


    TextView txtUserState;
    ImageView imCode;
    User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        txtUserState = findViewById(R.id.lblUserState);
        imCode = findViewById(R.id.imCode);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        try {
            if (getIntent().getExtras() != null) {
                u = (User) getIntent().getSerializableExtra("valObject");
                assert u != null;
                imCode.setImageBitmap(u.generateQRCode());
                txtUserState.setText(u.getState());
            }
        } catch (WriterException e) {
            e.printStackTrace();
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


    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}


