package pg.weti.efektstoopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    /**
     * Wczytanie podstawowych tresci graficznych jako zmiennych z okna graficznego
     * @param savedInstanceState Start okna
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Po wisnieciu przycisku START -> rozpoczecie gry
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void startClick(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Game.class);
        startActivity(intent);
    }

    /**
     * Po wisnieciu przycisku KONIEC -> wyjscie z programu
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void exitClick(View v)
    {
        finish();
        moveTaskToBack(true);
    }
}