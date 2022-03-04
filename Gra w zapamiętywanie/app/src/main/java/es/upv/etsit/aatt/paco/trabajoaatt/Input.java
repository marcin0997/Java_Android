package es.upv.etsit.aatt.paco.trabajoaatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;


/**
 * Ventana principal del juego
 * @author Marcin Kubiak
 */

public class Input extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView level;
    private TextView points;
    private TextView number2;
    private EditText editText;
    private Handler handler = new Handler();
    Random random = new Random();


    /** Creación de 10 botones que se muestran en la pantalla*/
    private Button buttons[] = new Button[10];
    private static final int[] idArray = {R.id.buttonZero, R.id.buttonOne, R.id.buttonTwo, R.id.buttonThree, R.id.buttonFour, R.id.buttonFive,
            R.id.buttonSix, R.id.buttonSeven, R.id.buttonEight, R.id.buttonNine};


    /** Establecer la barra de tiempo al 100%*/
    int progressBarStatus = 100;
    /** Declaración de objetos heredados.*/
    public int random_number, lvl=0, pkt=0, add_time=3;
    /** El número impreso en la pantalla.*/
    public String liczba="";


    /**
     * Mostrar el campo de juego en la pantalla
     * @param savedInstanceState declaración de barra de tiempo, nivel, número ingresado por el usuario y puntos
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        number2 = (TextView) findViewById(R.id.number);
        level = (TextView) findViewById(R.id.level);
        points = (TextView) findViewById(R.id.points);

        number2.setText("Ingrese el número recordado");

        clickButton();

        randButton();



        Intent intent = getIntent();
        random_number = intent.getIntExtra("random_number", 0);
        lvl = intent.getIntExtra("level", 0);
        pkt = intent.getIntExtra("points", 0);
        add_time = intent.getIntExtra("add_time", 0);


        System.out.println(random_number+" "+lvl+" "+pkt+" "+add_time);

        level.setText("Nivel: "+String.valueOf(lvl));
        points.setText("Puntos: "+String.valueOf(pkt));

        /**
         * Iniciar el reloj que mide el tiempo para ingresar el número
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressBarStatus!=0) {
                    progressBarStatus--;
                    if(lvl<=3)
                        android.os.SystemClock.sleep(45+lvl);   //cantidad de tiempo ~~7s
                    else if(lvl>3 && lvl <= 6)
                        android.os.SystemClock.sleep(80+lvl);   //cantidad de tiempo ~~7s
                    else
                        android.os.SystemClock.sleep(115+lvl);   //cantidad de tiempo ~~7s

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressBarStatus);     //tiempo de actualización
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);  //Fin del tiempo
                        //transición a otra

                        timesout();
                    }
                });
            }
        }).start();


    }


    /**
     * Después de completar el tiempo, ve a los datos de la ventana del juego
     */
    public void timesout()
    {
        if(liczba=="" ||number2.getText() == "" || liczba == null || String.valueOf(liczba)=="" || lvl == 9)
        {
            Intent intent = new Intent(this, Wrong.class);
            intent.putExtra("correct_numb",random_number);
            if(lvl==9) intent.putExtra("wrong_numb", Integer.valueOf(liczba));
            else
                intent.putExtra("wrong_numb", 0);
            startActivity(intent);
        }
        else if(Integer.valueOf(liczba) == random_number)
        {
            lvl++;
            pkt = pkt + 10*lvl;
            Intent intent = new Intent(this, Remember.class);
            intent.putExtra("level",lvl);
            intent.putExtra("points", pkt);
            intent.putExtra("add_time", add_time);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, Wrong.class);
            intent.putExtra("correct_numb",random_number);
            intent.putExtra("wrong_numb", Integer.valueOf(liczba));
            startActivity(intent);
        }
    }


    /**
     * Espaciado aleatorio de botones que muestra los números para ingresar
     * @param iLiczba el número sorteado
     * @param tab matriz que almacena números dibujados al azar
     * @param ile la cantidad de números sorteados
     * @return devuelve falso si el número no está en el conjunto de números aleatorios
     */
    boolean czyBylaWylosowana( int iLiczba, int[] tab, int ile )
    {
        if( ile <= 0 )
            return false;

        int i = 0;
        do
        {
            if( tab[ i ] == iLiczba )
                return true;

            i++;
        } while( i < ile );

        return false;
    }

    /**
     * Aleatoriza un número del 0 al 9.
     * @return devuelve el número extraído al azar
     */
    int wylosuj()
    {
        return random.nextInt(10);
    }

    /**
     * Asignación aleatoria de números a botones
     */
    public void randButton() {
        int[] wylosowane = new int[10];
        int wylosowanych = 0;
        do
        {
            int liczba = wylosuj();
            if(!czyBylaWylosowana(liczba, wylosowane, wylosowanych))
            {
                wylosowane[ wylosowanych ] = liczba;
                wylosowanych++;
            } //if
        } while( wylosowanych < 10 );

        wylosowanych = 0; int i=0;
        do
        {
            buttons[i].setText(String.valueOf(wylosowane[wylosowanych]));
            wylosowanych++;
            i++;
        } while( wylosowanych < 10 );

    }

    /**
     * Un método que actualiza el número ingresado por el jugador
     */
    public void onUpdate_displayNumber()
    {
        number2.setText(liczba);
    }

    /**
     * El método responsable de ingresar el número recordado por el jugador
     */
    public void clickButton()
    {
        for(int i=0;i<10;i++){
            buttons[i] = (Button) findViewById(idArray[i]);
            buttons[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    switch ( v.getId()){

                        case R.id.buttonZero:
                            liczba = liczba + buttons[0].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonOne:
                            liczba = liczba + buttons[1].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonTwo:
                            liczba = liczba + buttons[2].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonThree:
                            liczba = liczba + buttons[3].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonFour:
                            liczba = liczba + buttons[4].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonFive:
                            liczba = liczba + buttons[5].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonSix:
                            liczba = liczba + buttons[6].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonSeven:
                            liczba = liczba + buttons[7].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonEight:
                            liczba = liczba + buttons[8].getText();
                            onUpdate_displayNumber();
                            break;
                        case R.id.buttonNine:
                            liczba = liczba + buttons[9].getText();
                            onUpdate_displayNumber();
                            break;
                    }
                }
            });
        }
    }


    int counter=1;

    /**
     * El método responsable de deshacer el número ingresado
     * @param v haciendo clic en eliminar número
     */
    public void czysc(View v)
    {
        if(progressBarStatus<=50 && counter==1) {
            progressBarStatus += 10;
            counter--;
        }
        if(liczba.length()>=2)
            liczba = liczba.substring(0,liczba.length()-1);
        else
            liczba = "";
        number2.setText(liczba);
    }

    /**
     * Agregar tiempo
     * @param v después de hacer clic en el botón "+3"
     */
    public void dodaj_czas(View v)
    {
        add_to_timer();
    }

    /**
     * El método responsable de agregar tiempo a expensas de los puntos.
     */
    public void add_to_timer()
    {
        if(add_time!=0) {
            progressBarStatus += 30;
            add_time--;
        }
        pkt-=5;
    }
}
