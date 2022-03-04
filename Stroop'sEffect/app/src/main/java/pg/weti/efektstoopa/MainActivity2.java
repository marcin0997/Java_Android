package pg.weti.efektstoopa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    Button color1, color2, color3;
    Button[] kolory = new Button[3];

    public ImageView serce1;
    public ImageView serce2;
    public ImageView serce3;
    private ImageView[] serca = new ImageView[3];
    private int zycie=3;

    TextView odgadnij;

    TextView punktyT;
    private int pkt =0;

    //nazwy kolorow
    private String[] nazwy_kolorow = {"CZERWONY", "NIEBIESKI", "ZIELONY", "RÓŻOWY", "SZARY", "CZARNY"};
    //nazwy farb koloru tekstu
    private int[] id_kolor = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.GRAY, Color.BLACK};

    //nazwa koloru
    private int kolor_farba;
    private int losuj_poprawny_button;


    private long time = 0;
    private CountDownTimer countDownTimer;
    private TextView timer_txt;
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
        setContentView(R.layout.activity_main2);

        color1 = (Button) findViewById(R.id.kolor1);
        color2 = (Button) findViewById(R.id.kolor2);
        color3 = (Button) findViewById(R.id.kolor3);

        kolory[0] = color1;
        kolory[1] = color2;
        kolory[2] = color3;

        serce1 = (ImageView) findViewById(R.id.heart1);
        serce2 = (ImageView) findViewById(R.id.heart2);
        serce3 = (ImageView) findViewById(R.id.heart3);
        serca[0] = serce1;
        serca[1] = serce2;
        serca[2] = serce3;

        odgadnij = (TextView) findViewById(R.id.odgadnij_kolor2);

        timer_txt = (TextView) findViewById(R.id.czasomierzText);

        punktyT = (TextView) findViewById(R.id.punktyTekst);

        random = new Random();

        timeLeft = 30000;
        cnt = 0;
        startStop2();
        updateTimer2();

        losuj();

    }

    /**
     * Metoda losujaca kolor oraz tekst dla poszczegolnych elementow gry
     */
    public void losuj()
    {
        kolor_farba = random.nextInt(6);    //wyznacznik (tlo dla glownego tekstu i nazwa dla buttona)
        int kolor_tekst = random.nextInt(6);

        odgadnij.setText(nazwy_kolorow[kolor_tekst]);
        odgadnij.setTextColor(id_kolor[kolor_farba]);

        losuj_poprawny_button = random.nextInt(3);

        kolory[losuj_poprawny_button].setText(nazwy_kolorow[kolor_farba]);  //poprawny

        int[] losuj = new int[5];
        for(int i=0;i<5;i++)
        {
            do {
                losuj[i] = random.nextInt(6);
            }while(losuj[i] == kolor_farba);
        }

        if(losuj_poprawny_button == 0)
        {
            kolory[losuj_poprawny_button].setTextColor(id_kolor[losuj[0]]);

            kolory[1].setText(nazwy_kolorow[losuj[1]]);
            kolory[1].setTextColor(id_kolor[losuj[2]]);

            kolory[2].setText(nazwy_kolorow[losuj[3]]);
            kolory[2].setTextColor(id_kolor[losuj[4]]);


        }else if(losuj_poprawny_button == 1)
        {
            kolory[losuj_poprawny_button].setTextColor(id_kolor[losuj[0]]);

            kolory[0].setText(nazwy_kolorow[losuj[1]]);
            kolory[0].setTextColor(id_kolor[losuj[2]]);

            kolory[2].setText(nazwy_kolorow[losuj[3]]);
            kolory[2].setTextColor(id_kolor[losuj[4]]);

        }else
        {
            kolory[losuj_poprawny_button].setTextColor(id_kolor[losuj[0]]);

            kolory[0].setText(nazwy_kolorow[losuj[1]]);
            kolory[0].setTextColor(id_kolor[losuj[2]]);

            kolory[1].setText(nazwy_kolorow[losuj[3]]);
            kolory[1].setTextColor(id_kolor[losuj[4]]);
        }

        System.out.println(losuj_poprawny_button);

    }

    /**
     * Metoda sprawdzajaca czy czas juz dobiegl konca
     */
    public void startStop2() {
        if (isTimerRun)
            stopTimer2();
        else
            startTimer2();
    }

    /**
     * Metoda rozpoczynajaca odliczanie w dol dla licznika czasu
     */
    public void startTimer2() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer2();
            }

            @Override
            public void onFinish() {
                isTimerRun = false;

                //Po skonczeniu odliczanie
                zycie=0;
                koniec_gry2();


            }
        }.start();
        isTimerRun = true;
    }

    /**
     * Metoda, ktora co kazde 1000ms aktualizuje licznik
     */
    public void updateTimer2() {
        int sec = (int) (timeLeft / 1000) % 60;

        String timeLeftText;
        timeLeftText = "" + sec;

        timer_txt.setText(timeLeftText);
        System.out.println(timeLeft + " " + time);

    }

    /**
     * Metoda zatrzymujaca stoper, gdy czas sie skonczy lub uzytkownik przejdzie do kolejnego poziomu
     */
    public void stopTimer2() {
        countDownTimer.cancel();
        isTimerRun = false;
    }

    public void koniec_gry2()
    {
        if(zycie == 0)
        {
            stopTimer2();
            odgadnij.setText("Tracisz wszystkie zycia");
            System.out.println("PRZEGRALES");
            Intent intent = new Intent(this, HiScore.class);
            intent.putExtra("SCORE", pkt);
            startActivity(intent);
        }

    }

    /**
     * Po wisnieciu przycisku gry-> sprawdzenie czy uzytkownik udzielil poprawnej odpowiedzi oraz warunkow zakonczenia rozgrywki
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void kol1(View v)
    {
        if(losuj_poprawny_button == 0)
        {
            pkt++;
        }
        else
        {
            zycie--;
            serca[zycie].setVisibility(View.INVISIBLE);
        }
        punktyT.setText("Runda na punkty: "+String.valueOf(pkt));
        koniec_gry2();
        losuj();
    }

    /**
     * Po wisnieciu przycisku gry-> sprawdzenie czy uzytkownik udzielil poprawnej odpowiedzi oraz warunkow zakonczenia rozgrywki
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void kol2(View v)
    {
        if(losuj_poprawny_button == 1)
        {
            pkt++;
        }
        else
        {
            zycie--;
            serca[zycie].setVisibility(View.INVISIBLE);
        }
        punktyT.setText("Runda na punkty: "+String.valueOf(pkt));
        koniec_gry2();
        losuj();
    }

    /**
     * Po wisnieciu przycisku gry-> sprawdzenie czy uzytkownik udzielil poprawnej odpowiedzi oraz warunkow zakonczenia rozgrywki
     * @param v Sprawdzenie czy przycisk zostal wcisniety
     */
    public void kol3(View v)
    {
        if(losuj_poprawny_button == 2)
        {
            pkt++;
        }
        else
        {
            zycie--;
            serca[zycie].setVisibility(View.INVISIBLE);
        }
        punktyT.setText("Runda na punkty: "+String.valueOf(pkt));
        koniec_gry2();
        losuj();
    }
}