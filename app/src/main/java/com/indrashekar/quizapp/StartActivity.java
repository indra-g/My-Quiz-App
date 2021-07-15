package com.indrashekar.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {
    MaterialButton btn_comp,btn_gk,btn_ani,btn_sports,btn_sciencenature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btn_comp=(MaterialButton)findViewById(R.id.btn_comp);
        btn_gk=(MaterialButton)findViewById(R.id.btn_gk);
        btn_ani=(MaterialButton)findViewById(R.id.btn_ani);
        btn_sports=(MaterialButton)findViewById(R.id.btn_sports);
        btn_sciencenature=(MaterialButton)findViewById(R.id.btn_sciencenature);
        btn_gk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        });
        btn_ani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,AnimalActivity.class));
                finish();
            }
        });
        btn_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,ComputerActivity.class));
                finish();
            }
        });
        btn_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,SportsActivity.class));
                finish();
            }
        });
        btn_sciencenature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,ScienceActivity.class));
                finish();
            }
        });
    }
}