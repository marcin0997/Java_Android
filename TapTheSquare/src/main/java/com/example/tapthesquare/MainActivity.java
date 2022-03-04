package com.example.tapthesquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Złap kwadrat");
    }


    public void Play_buttonClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), GameView.class);
        startActivity(intent);
    }

    public void Help_buttonClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Help.class);
        startActivity(intent);
    }

    public void Exit_buttonClick(View v)
    {

        finish();
        moveTaskToBack(true);
    }
}
