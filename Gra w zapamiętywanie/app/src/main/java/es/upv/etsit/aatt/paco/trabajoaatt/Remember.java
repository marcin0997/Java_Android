package es.upv.etsit.aatt.paco.trabajoaatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Una clase que dibuja un número y presenta un número para recordar
 * @author Marcin Kubiak
 */

public class Remember extends AppCompatActivity {

    /** Un número pseudoaleatorio para recordar*/
    public int random_number;
    /**elemento herencia que almacena el nivel en el que se encuentra el jugador actualmente*/
    public int level=0;
    /**elemento herencia que almacena el número de puntos anotados por el jugador*/
    public int points=0;
    /**elemento herencia que almacena la cantidad de tiempo posible agregado*/
    public int add_time=3;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember);

        textView = (TextView) findViewById(R.id.random_number_gen);

        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        points = intent.getIntExtra("points", 0);
        add_time = intent.getIntExtra("add_time", 3);

        int potega = level + 1;
        Random random = new Random();
        int bound = (int) Math.pow(10, potega) - (int) Math.pow(10, level) - 1;
        int start = (int) Math.pow(10, level);
        random_number = random.nextInt(bound) + start;

        textView.setText(String.valueOf(random_number));

        /**
         * @param random_number número de n dígitos al azar dependiendo del nivel en el que se encuentre el jugador
         */

    }

    /**
     * Cuando el jugador recuerda el número
     * @param v Haciendo clic en el botón
     */
    public void onClick(View v)
    {
        Intent intent = new Intent(this, Input.class);
        intent.putExtra("random_number",random_number);
        intent.putExtra("level",level);
        intent.putExtra("points",points);
        intent.putExtra("add_time",add_time);
        startActivity(intent);
    }
}
