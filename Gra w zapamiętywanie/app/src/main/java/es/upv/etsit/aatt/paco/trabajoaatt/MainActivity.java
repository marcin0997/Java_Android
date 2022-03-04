package es.upv.etsit.aatt.paco.trabajoaatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



/**
 * Principal: el tablero inicial con la tarea de establecer el nivel al primero y restablecer los puntos del jugador
 * @author Marcin Kubiak
 */

public class MainActivity extends AppCompatActivity {

    /**Nivel inicial del juego.*/
    public int level=1;
    /**el número de puntos se establece en 0*/
    public int points=0;
    /**Cuantas veces puedes agregar + 3s al tiempo del juego*/
    public int add_time=3;


    /**
     * Vista inicial de la pantalla del juego.
     * {@inheritDoc}
     * @param savedInstanceState estado del juego
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * @param intent heredar de miembros a la ventana del juego
         */
        Intent intent = new Intent(this, Remember.class);
        intent.putExtra("level",level);
        intent.putExtra("points",points);
        intent.putExtra("add_time",add_time);
        startActivity(intent);
    }

    public void onClick(View v)
    {

    }
}
