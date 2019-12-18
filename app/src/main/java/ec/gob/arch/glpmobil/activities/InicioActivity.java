package ec.gob.arch.glpmobil.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;

public class InicioActivity extends AppCompatActivity {

    private ObjetoAplicacion objetosSesion;
    private TextView tvUsuarioEnSesion;
    private TextView tvNombreUsuarioEnSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("log_glp ---------->", "INFO InicioActivity --> onCreate()");

        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvUsuarioEnSesion = (TextView) findViewById(R.id.tvUsuarioEnSesion);
        tvNombreUsuarioEnSesion = (TextView) findViewById(R.id.tvNombreUsuarioEnSesion);

        objetosSesion = (ObjetoAplicacion) getApplication();
        tvUsuarioEnSesion.setText(objetosSesion.getUsuario().getId());
        tvUsuarioEnSesion.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvNombreUsuarioEnSesion.setTextColor(getResources().getColor(R.color.colorPrimary));

        if (objetosSesion.getUsuario()!=null){
            this.setTitle(R.string.app_name);
            tvUsuarioEnSesion.setText(ConstantesGenerales.TITULO_CABECERA+objetosSesion.getUsuario().getId());
            tvNombreUsuarioEnSesion.setText(objetosSesion.getUsuario().getNombre());
        }else{
            this.setTitle(R.string.app_name);
            tvUsuarioEnSesion.setText(ConstantesGenerales.TITULO_CABECERA);
        }
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
            //Intent lector = new Intent(this,RegistroVentaActivity.class);
            //startActivity(lector);
            fm.beginTransaction().replace(R.id.fragment, new VentaFragment()).commit();
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Registrar venta");
            return true;
        }else if(id == R.id.opcion_editar_venta)       {
            fm.beginTransaction().replace(R.id.fragment, new ConsultarVentaFragment()).commit();
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Editar venta");
        }else if(id == R.id.opcion_actualizar_cupos)
        {
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Actualizar cupos");

            Bundle bundle = new Bundle();
            bundle.putString("accion","0");
            HistorialSincronizaFragment historialSincronizaFragment = new HistorialSincronizaFragment();
            historialSincronizaFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.fragment, historialSincronizaFragment).commit();

        }else if(id == R.id.opcion_historial_ventas)
        {
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Actualizar cupos");

            Bundle bundle = new Bundle();
            bundle.putString("accion","1");
            HistorialSincronizaFragment historialSincronizaFragment = new HistorialSincronizaFragment();
            historialSincronizaFragment.setArguments(bundle);
            fm.beginTransaction().replace(R.id.fragment, historialSincronizaFragment).commit();

        }else if(id == R.id.opcion_enviar_ventas)
        {
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: Enviar ventas");
            fm.beginTransaction().replace(R.id.fragment, new EnviarVentasFragment()).commit();// lleva al fragment
        }else if(id == R.id.opcion_cambiar_clave)
        {
            CambiarClaveFragment cambiarClaveFragment = new CambiarClaveFragment();
            fm.beginTransaction().replace(R.id.fragment,cambiarClaveFragment).commit();
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: CAMBIAR CLAVE");
        }
        else if(id == R.id.cerrar_sesion)
        {
            Intent irLogin = new Intent(InicioActivity.this, LoginActivity.class);
            startActivity(irLogin);
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: CERRAR SESIÓN");
        }
        return super.onOptionsItemSelected(item);

    }



}



