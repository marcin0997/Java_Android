package es.upv.etsit.aatt.paco.trabajoaatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import org.w3c.dom.Text;

/**
 * Ventana final del juego
 * @author Marcin Kubiak
 */

public class Wrong extends AppCompatActivity {

    private TextView correct_numb;
    private TextView wrong_numb;
    private TextView win;
    Button reset;

    /**Herencia de miembros que almacenan un número incorrecto ingresado por el jugador y correcto*/
    int correct_number=0, wrong_number=0;

    /**
     * Mostrar en la ventana del juego
     * @param savedInstanceState Después de cambiar a una clase dada
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong);

        correct_numb = (TextView) findViewById(R.id.correct_numb);
        wrong_numb = (TextView) findViewById(R.id.wrong_numb);
        reset = (Button) findViewById(R.id.restart);
        win = (TextView) findViewById(R.id.win);


        Intent intent = getIntent();
        correct_number = intent.getIntExtra("correct_numb", 0);
        wrong_number = intent.getIntExtra("wrong_numb", 0);

        if(correct_number == wrong_number)
        {
            win.setVisibility(View.VISIBLE);
        }

        setText_numb();
    }

    /**
     * Muestra información sobre el número correcto e incorrecto almacenado
     */
    public void setText_numb()
    {
        correct_numb.setText(String.valueOf(correct_number));
        wrong_numb.setText(String.valueOf(wrong_number));
    }


    /**
     * El método responsable de reiniciar el juego
     * @param v Después de presionar el botón
     */
    public void restartApp(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
