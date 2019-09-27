package ec.gob.arch.glpmobil.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ec.gob.arch.glpmobil.R;

public class InicioActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fm = getSupportFragmentManager();
        if (id == R.id.opcion_registrar_venta) {
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Registrar venta");
            return true;
        }else if(id == R.id.opcion_editar_venta)       {
            fm.beginTransaction().replace(R.id.fragment, new ConsultarVentaFragment()).commit();
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Editar venta");
        }else if(id == R.id.opcion_actualizar_cupos)
        {
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Actualizar cupos");
            fm.beginTransaction().replace(R.id.fragment, new CuposFragment()).commit();
        }else if(id == R.id.opcion_enviar_ventas)
        {
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Enviar ventas");
            Log.v("log_glp ---------->", "INFO RegistroVentaActivity --> onOptionsItemSelected(): dio clic en la opción: Enviar ventas");
            fm.beginTransaction().replace(R.id.fragment, new EnviarVentasFragment()).commit();
        }


        return super.onOptionsItemSelected(item);
    }

  }
