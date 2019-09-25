package ec.gob.arch.glpmobil.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.Result;

import ec.gob.arch.glpmobil.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RegistroVentaActivity extends AppCompatActivity implements OnClickListener {

    private TextView codigo;
    private ZXingScannerView vistaescaner ;    //importante dar permiso de camara en el manifest y en el build.gradle(module app) para la sincronizacion
    private String dato;
    private Button btnScaner;
    private Button btnIdentificar;
    private Button btnbusqueda;
    private TextView registro;
    private EditText Edyear;
    private Spinner spinnerD;
    private Spinner spinnerM;
    private TextView textIdentificacion;
    private EditText edittextEscrita;
    private TextView textscan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_venta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnScaner = (Button) findViewById(R.id.btnScanner);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new OnClickListener() {
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
        if (id == R.id.opcion_registrar_venta) {
            Log.v("log_glp ---------->", "INFO RegistroVentaActivity --> onOptionsItemSelected(): dio clic en la opción: Registrar venta");
            return true;
        }else if(id == R.id.opcion_editar_venta)
        {
            Log.v("log_glp ---------->", "INFO RegistroVentaActivity --> onOptionsItemSelected(): dio clic en la opción: Editar venta");
        }else if(id == R.id.opcion_actualizar_cupos)
        {
            Log.v("log_glp ---------->", "INFO RegistroVentaActivity --> onOptionsItemSelected(): dio clic en la opción: Actualizar cupos");
        }else if(id == R.id.opcion_enviar_ventas)
        {
            Log.v("log_glp ---------->", "INFO RegistroVentaActivity --> onOptionsItemSelected(): dio clic en la opción: Enviar ventas");
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnScanner:
                vistaescaner = new ZXingScannerView(this);
                vistaescaner.setResultHandler(new zxingscanner());
                setContentView(vistaescaner);
                vistaescaner.startCamera();
                Log.v("log_glp ---------->","Inicio Camara para Escanear");

                if(v.getId()== R.id.btnScanner){
                    edittextEscrita.setText("");
                    edittextEscrita.setVisibility(View.INVISIBLE);

                    Log.v("log_glp ---------->","Valor de la Cédula Escrita:"+edittextEscrita);

                }
                else{
                    new AlertDialog.Builder(this)
                            .setTitle("ERROR AL ESCANEAR")
                            .setMessage("Debe centrar el codigo de barras para poder escanear")
                            .setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int wich) {
                                    Log.d("Main Activity","Saliendio");
                                }
                            })

                            .show();
                }
                break;

            case R.id.btnIdentificar:
                if(v.getId()== R.id.btnIdentificar){
                    textIdentificacion = (TextView) findViewById(R.id.tvScan);
                    textIdentificacion.setText("");

                    //Comparacion para habilitar o deshabilitar el edittext donde se escribe la cédula de identidad



                }
        }

    }
    public class zxingscanner implements ZXingScannerView.ResultHandler{
        @Override
        public void handleResult(Result result) {
            dato = result.getText();
            setContentView(R.layout.fragment_registro_venta);
            Log.v("log_glp ---------->","Escanando el Codigo");
            vistaescaner.stopCamera();
            Log.v("log_glp ---------->","Se detuvo la camara");
            codigo = (TextView) findViewById(R.id.tvScan);
            codigo.setText(dato);
        }
    }
    public void confirmar (View view) {
        spinnerD = (Spinner) findViewById(R.id.spnDia);//capturar el dato seleccionado en el spinner
        String dia = spinnerD.getSelectedItem().toString();
        spinnerM = (Spinner) findViewById(R.id.spnMes);
        String mes = spinnerM.getSelectedItem().toString();
        Edyear = findViewById(R.id.edYear);
        String year = Edyear.getText().toString();

    }

    public void datosFecha () {
        spinnerD = (Spinner) findViewById(R.id.spnDia);//capturar el dato seleccionado en el spinner
        String dia = spinnerD.getSelectedItem().toString();
        spinnerM = (Spinner) findViewById(R.id.spnMes);
        String mes = spinnerM.getSelectedItem().toString();
        Edyear = findViewById(R.id.edYear);
        String year = Edyear.getText().toString();


        registro.setText(dia + "/" + mes + "/" + year);
    }

}
