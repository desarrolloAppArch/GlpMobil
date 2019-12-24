package ec.gob.arch.glpmobil.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.zxing.Result;
import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RegistroVentaActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView codigo;
    private ZXingScannerView vistaescaner ;    //importante dar permiso de camara en el manifest y en el build.gradle(module app) para la sincronizacion
    private String dato;
    private TextView registro;
    private EditText Edyear;
    private Spinner spinnerD;
    private Spinner spinnerM;
    private TextView textIdentificacion;
    private EditText edittextEscrita;

    //Variables para comprobar permisos de camara
    private boolean permisoCamaraConcedido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_venta);
        textIdentificacion = findViewById(R.id.tvScan);
        edittextEscrita = findViewById(R.id.EtCe);
        Log.v("log_glp ---------->","Entro en ReistroVentaActivty");
        verificarYPedirPermisosDeCamara();
    }

public class zxingscanner implements ZXingScannerView.ResultHandler{

    @Override
    public void handleResult(Result result) {
        try{
            dato = result.getText();
            setContentView(R.layout.activity_registro_venta);
            codigo = findViewById(R.id.tvScan);
            vistaescaner.stopCamera();
            codigo.setText(dato);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

    @Override
    public void onClick(View v) { // falta solucionar el problema de la accion de cada boton
    switch (v.getId()){
        case R.id.btnScanner:
            edittextEscrita.setText("");
            vistaescaner = new ZXingScannerView(this);
            vistaescaner.setResultHandler(new zxingscanner());
            setContentView(vistaescaner);
            vistaescaner.startCamera();
            if(v.getId()== R.id.btnScanner){


                Log.v("Lector Código--->","Valor de la Cédula Escrita:"+edittextEscrita.getTypeface());

            }

            break;
        case R.id.btnIdentificar:
            textIdentificacion.setText("");
            if(v.getId()== R.id.btnIdentificar){

                      if(edittextEscrita.getVisibility() == View.GONE){
                        edittextEscrita.setVisibility(View.VISIBLE);
                        codigo.setText("");
                        }
                      else{
                        edittextEscrita.setVisibility(View.VISIBLE);
                        }
                //Comparacion para habilitar o deshabilitar el edittext donde se escribe la cédula de identidad

            }
            break;
        }

    }


    private void verificarYPedirPermisosDeCamara() {
        int estadoDePermiso = ContextCompat.checkSelfPermission(RegistroVentaActivity.this, Manifest.permission.CAMERA);
        if (estadoDePermiso == PackageManager.PERMISSION_GRANTED) {
            // En caso de que haya dado permisos ponemos la bandera en true
            // y llamar al método
            permisoCamaraConcedido = true;
        } else {
            // Si no, pedimos permisos. Ahora mira onRequestPermissionsResult
            ActivityCompat.requestPermissions(RegistroVentaActivity.this,
                    new String[]{Manifest.permission.CAMERA}, ConstantesGenerales.CODIGO_PERMISOS_CAMARA);
        }
    }

}
