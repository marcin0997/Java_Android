package pg.weti.efektstoopa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Game extends AppCompatActivity {

    public ImageView s1;
    public ImageView s2;
    public ImageView s3;
    private ImageView[] serca = new ImageView[3];


    public ImageView p1;
    public ImageView p2;
    public ImageView p3;
    public ImageView p4;
    public ImageView p5;
    private ImageView[] pioruny = new ImageView[5];

    public TextView odgadnij;

    private String[] nazwy_kolorow = {"CZERWONY", "NIEBIESKI", "ZIELONY", "RÓŻOWY"};    //, "SZARY", "CZARNY"};
    private int[] id_kolor = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA};       //, Color.GRAY, Color.BLACK};

    private int kolor_tekst;
    private int kolor_kolor;

    private int punkty =0;
    private int zycia = 3;
    private int licznik_powtorzen=0;

    private long time = 0;
    private CountDownTimer countDownTimer;
    public TextView timer_txt;
    private long timeLeft;
    private boolean isTimerRun;
    private int cnt = 0;

    Random random;

    /**
     * Wczytanie podstawowych tresci graficznych jako zmiennych z okna graficznego
     * @param savedInstanceState Stan poczatkowy
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        s1 = (ImageView) findViewById(R.id.serce1);
        s2 = (ImageView) findViewById(R.id.serce2);
        s3 = (ImageView) findViewById(R.id.serce3);

        serca[0]=s1;
        serca[1]=s2;
        serca[2]=s3;


        p1 = (ImageView) findViewById(R.id.piorun1);
        p2 = (ImageView) findViewById(R.id.piorun2);
        p3 = (ImageView) findViewById(R.id.piorun3);
        p4 = (ImageView) findViewById(R.id.piorun4);
        p5 = (ImageView) findViewById(R.id.piorun5);

        pioruny[0]=p1;
        pioruny[1]=p2;
        pioruny[2]=p3;
        pioruny[3]=p4;
        pioruny[4]=p5;


        odgadnij = (TextView) findViewById(R.id.kolor);
        timer_txt = (TextView) findViewById(R.id.timer_tekst);
        random = new Random();

        timeLeft = 10000;
        cnt = 0;
        startStop();
        updateTimer();

        losuj_kolor();
    }

    /**
     * Metoda przekierowujaca uzytkownika do kolejnej instancji (nastepnego poziomu)
     */
    public void nastepny_poziom()
    {
        if(punkty == 5)
        {
            timeLeft+=10000;
            isTimerRun = false;
            stopTimer();
            Intent intent = new Intent(Game.this, MainActivity2.class);
            startActivity(intent);
            System.out.println("KONIEC");
        }
    }

    /**
     * Metoda sprawdzajaca czy nastapil koniec gry
     */
    public void koniec_gry()
    {
        if(zycia == 0)
        {
            stopTimer();
            odgadnij.setText("Tracisz wszystkie zycia");
            tworz_dialog();
            System.out.println("PRZEGRALES");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

    }

    /**
     * Metoda odpowiadajaca za losowanie nazwy koloru oraz koloru czcionki
     */
    public void losuj_kolor()
    {
        if(licznik_powtorzen%4==0)
        {
            kolor_tekst = random.nextInt(4);
            kolor_kolor = kolor_tekst;
            odgadnij.setText(nazwy_kolorow[kolor_tekst]);
            odgadnij.setTextColor(id_kolor[kolor_tekst]);
        }
        else {
            kolor_tekst = random.nextInt(4);
            kolor_kolor = random.nextInt(4);

            odgadnij.setText(nazwy_kolorow[kolor_tekst]);
            odgadnij.setTextColor(id_kolor[kolor_kolor]);
        }
        licznik_powtorzen++;
    }

    /**
     * Metoda uzywana po pomylce gracza, ktora usuwa wszystkie pioruny (punkty)
     */
    public void wyzeruj_pioruny()
    {
        p1.setVisibility(View.INVISIBLE);
        p2.setVisibility(View.INVISIBLE);
        p3.setVisibility(View.INVISIBLE);
        p4.setVisibility(View.INVISIBLE);
        p5.setVisibility(View.INVISIBLE);

    }

    /**
     * Po wisnieciu przycisku gry-> sprawdzenie czy uzytkownik udzielil poprawnej odpowiedzi oraz warunkow zakonczenia rozgrywki
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void poprawne (View v)
    {
        if(kolor_tekst == kolor_kolor)
        {
            pioruny[punkty].setVisibility(View.VISIBLE);
            punkty++;

        }
        else
        {
            zycia--;
            serca[zycia].setVisibility(View.INVISIBLE);
            punkty = 0;
            wyzeruj_pioruny();
        }
        koniec_gry();
        nastepny_poziom();
        losuj_kolor();
    }

    /**
     * Po wisnieciu przycisku gry-> sprawdzenie czy uzytkownik udzielil poprawnej odpowiedzi oraz warunkow zakonczenia rozgrywki
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void bledne (View v)
    {
        if(kolor_tekst != kolor_kolor)
        {
            pioruny[punkty].setVisibility(View.VISIBLE);
            punkty++;
        }
        else
        {
            zycia--;
            serca[zycia].setVisibility(View.INVISIBLE);
            punkty = 0;
            wyzeruj_pioruny();
        }
        koniec_gry();
        nastepny_poziom();
        losuj_kolor();
    }

    /**
     * Metoda sprawdzajaca czy czas juz dobiegl konca
     */
    public void startStop() {
        if (isTimerRun)
            stopTimer();
        else
            startTimer();
    }

    /**
     * Metoda rozpoczynajaca odliczanie w dol dla licznika czasu
     */
    public void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                isTimerRun = false;
                stopTimer();
                //Po skonczeniu odliczanie

                odgadnij.setText("KONIEC CZASU");

                zycia=0;
                koniec_gry();

            }
        }.start();
        isTimerRun = true;
    }

    /**
     * Metoda, ktora co kazde 1000ms aktualizuje licznik
     */
    public void updateTimer() {
        int sec = (int) (timeLeft / 1000) % 60;

        String timeLeftText;
        timeLeftText = "" + sec;

        timer_txt.setText(timeLeftText);
        System.out.println(timeLeft + " " + time);

    }

    /**
     * Metoda zatrzymujaca stoper, gdy czas sie skonczy lub uzytkownik przejdzie do kolejnego poziomu
     */
    public void stopTimer() {
        countDownTimer.cancel();
        isTimerRun = false;
    }

    /**
     * Metoda tworzaca informacje dla uzytkownika o koncu poziomu
     */
    public void tworz_dialog()
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
        alert.setTitle("Przegrana! :c");
        alert.setMessage("Nastąpi przeniesienie do Menu Głównego, gdzie będziesz mógł zagrać jeszcze raz! :)");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Game.this, "Ja chce jeszcze raz!", Toast.LENGTH_SHORT).show();
            }
        });
        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Game.this, "Chyba starczy na dzis", Toast.LENGTH_SHORT).show();
            }
        });
    }
}