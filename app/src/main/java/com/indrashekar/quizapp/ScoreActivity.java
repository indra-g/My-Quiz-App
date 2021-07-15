package com.indrashekar.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class ScoreActivity extends AppCompatActivity {
    TextView score;
    MaterialButton btn_restart;
    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure you want to go back");
            dialog.setTitle("Conformation");
            dialog.setIcon(android.R.drawable.ic_dialog_alert);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog alertDialog = dialog.create();
            alertDialog.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        score=(TextView)findViewById(R.id.score);
        btn_restart=(MaterialButton)findViewById(R.id.btn_restart);
        Intent intent=getIntent();
        String sco=intent.getStringExtra("score");
        score.setText(sco+"/10");
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ScoreActivity.this,StartActivity.class));
                finish();
            }
        });

    }
}