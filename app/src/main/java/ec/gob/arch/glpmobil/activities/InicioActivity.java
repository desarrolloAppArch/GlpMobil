package ec.gob.arch.glpmobil.activities;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

public class InicioActivity extends AppCompatActivity {

    private ObjetoAplicacion objetosSesion;
    private TextView tvUsuarioEnSesion;
    private TextView tvNombreUsuarioEnSesion;

    //Variables para obtener el IMEI
    static final Integer PHONESTATS = 0x1;


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

        consultarPermiso(Manifest.permission.READ_PHONE_STATE, PHONESTATS);
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
            onDestroy();
            Log.v("log_glp ---------->", "INFO InicioActivity --> onOptionsItemSelected(): dio clic en la opción: CERRAR SESIÓN");
        }
        return super.onOptionsItemSelected(item);

    }

    // Con este método mostramos en un Toast con un mensaje que el usuario ha concedido los permisos a la aplicación
    private void consultarPermiso(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(InicioActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            Log.v("log_glp ---------->", "INFO InicioActivity --> consultarPermiso(): Asignando permisos");
            if (ActivityCompat.shouldShowRequestPermissionRationale(InicioActivity.this, permission)) {
                ActivityCompat.requestPermissions(InicioActivity.this, new String[]{permission}, requestCode);
            } else {
                ActivityCompat.requestPermissions(InicioActivity.this, new String[]{permission}, requestCode);
            }
        } else {
            Log.v("log_glp ---------->", "INFO InicioActivity --> consultarPermiso(): Ya tenía permisos");
            objetosSesion.setImei(obtenerIMEI());

            //Toast.makeText(this,permission + " El permiso a la aplicación esta concedido.", Toast.LENGTH_SHORT).show();
        }
    }


    // Con este método consultamos al usuario si nos puede dar acceso a leer los datos internos del móvil
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case 1: {
                // Validamos si el usuario acepta el permiso para que la aplicación acceda a los datos internos del equipo, si no denegamos el acceso
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //imei = obtenerIMEI();
                    objetosSesion.setImei(obtenerIMEI());

                } else {
                    Log.v("log_glp ---------->", "INFO InicioActivity --> consultarPermiso(): Haz negado el permiso a la aplicación");
                    UtilMensajes.mostrarMsjError(MensajeError.PERMISOS_TELEFONO, TituloError.TITULO_ERROR, this);
                }
                return;
            }
        }
    }


    @SuppressLint("MissingPermission")
    private String obtenerIMEI() {
        final TelephonyManager telephonyManager= (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
            imei = telephonyManager.getImei();
        }else {
            imei= telephonyManager.getDeviceId();
        }
        Log.v("log_glp ---------->", "INFO InicioActivity --> obtenerIMEI() --> IMEI: "+imei);
        return imei;
    }



}



