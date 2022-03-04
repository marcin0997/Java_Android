package pg.weti.efektstoopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class HiScore extends AppCompatActivity {

    /**
     * Wczytanie podstawowych tresci graficznych jako zmiennych z okna graficznego
     * @param savedInstanceState Stan poczatkowy
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_score);

        Scores();
    }

    /**
     * Metoda pobierajaca wynik z poprzedniej instancji i wyswietlajaca na ekran uzytkownika oraz zapisujaca najlepszy wynik w pamieci telefonu
     */
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

    /**
     * Metoda uruchamiajaca gre ponownie
     * @param v Wcisniecie przycisku
     */
    public void nowa(View v) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}