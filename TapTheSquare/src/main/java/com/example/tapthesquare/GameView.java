package com.example.tapthesquare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameView extends AppCompatActivity {

    long time = 0;
    Button button;
    TextView countdownText;
    CountDownTimer countDownTimer;

    long timeLeft;
    boolean isTimerRun;
    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        countdownText = findViewById(R.id.countdown_text);

        timeLeft = 31000;
        cnt = 0;

        startStop();    //po wcisnieciu buttona
        create_button();
        updateTimer();

        setTitle("Złap kwadrat");

    }

    public void create_button() {
        button = (Button) findViewById(R.id.button);

        AbsoluteLayout.LayoutParams absParams = (AbsoluteLayout.LayoutParams) button.getLayoutParams();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        //DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int a = displaymetrics.widthPixels;
        int b = displaymetrics.heightPixels;

        int Ry, Rx;

        do {
            Ry = new Random().nextInt(b - 650) + 300;
        } while (Ry < 300 && Ry > b - 300);

        do {
            Rx = new Random().nextInt(a - 165) + 40;
        } while (Rx < 50 && Rx > (a - 100));

        absParams.y = Ry;
        absParams.x = Rx;

        time = timeLeft - 900;

        button.setLayoutParams(absParams);

    }

    public void startStop() {
        if (isTimerRun)
            stopTimer();
        else
            startTimer();
    }

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

                Intent intent = new Intent(getApplicationContext(), result.class);
                intent.putExtra("SCORE", cnt);
                startActivity(intent);
            }
        }.start();
        isTimerRun = true;
    }

    public void stopTimer() {
        countDownTimer.cancel();
        isTimerRun = false;
    }

    public void updateTimer() {
        int sec = (int) (timeLeft / 1000) % 60;

        String timeLeftText;
        timeLeftText = "" + sec;

        countdownText.setText(timeLeftText);
        System.out.println(timeLeft + " " + time);


        if (timeLeft < time-500)
            destroyButton();
    }

    public void destroyButton(){
        create_button();
   }

    public void buttonClick(View v)
    {
        cnt++;
        create_button();
    }
}
