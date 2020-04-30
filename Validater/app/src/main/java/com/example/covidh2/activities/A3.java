package com.example.covidh2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covidh2.R;
import com.example.covidh2.models.Person;


public class A3 extends AppCompatActivity {

    Person person;
    TextView etName, etState;
    String qrCodeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);


        if (getIntent().getExtras() != null) {
            person = (Person) getIntent().getSerializableExtra("valObject");
            qrCodeText = getIntent().getStringExtra("qrText");
        }
        etName = findViewById(R.id.txtName);
        etState = findViewById(R.id.txtState);

        etName.setText(person.getNid());
        etState.setText(person.getState());
        Toast.makeText(this, qrCodeText, Toast.LENGTH_SHORT).show();

    }

    public void back(View view) {
        Intent in = new Intent(A3.this, A2.class);
        startActivity(in);
        finish();
    }
}
