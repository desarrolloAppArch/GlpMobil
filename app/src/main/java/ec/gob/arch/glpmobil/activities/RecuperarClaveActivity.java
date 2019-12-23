package ec.gob.arch.glpmobil.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ec.gob.arch.glpmobil.R;

public class RecuperarClaveActivity extends AppCompatActivity {

    Button btnAceptarRecuperar;
    Button btnCancelarRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);
        setTitle("RECUPERAR CLAVE");
        btnAceptarRecuperar = (Button) findViewById(R.id.btnAceptarRecuperar);
        btnCancelarRecuperar = (Button) findViewById(R.id.btnCancelarRecuperar);




        btnCancelarRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irPaginaLogin =  new Intent(RecuperarClaveActivity.this, LoginActivity.class);
                startActivity(irPaginaLogin);
            }
        });


    }
}
