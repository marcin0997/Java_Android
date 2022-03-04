package com.example.tapthesquare;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class result extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Złap kwadrat");

        Scores();
    }
/*
    @Override
    public void onBackPressed()
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
*/
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == android.R.id.home)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void Scores()
    {
        TextView scoreText = (TextView) findViewById(R.id.wynik);
        TextView highscoreText = (TextView) findViewById(R.id.hiscore);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreText.setText(score + "");

        SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highscoreText.setText("Najlepszy wynik : " + score);

            // Update High Score
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();

        } else {
            highscoreText.setText("Najlepszy wynik : " + highScore);

        }
    }


    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), GameView.class));
    }
}

