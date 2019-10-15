package ec.gob.arch.glpmobil.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.Result;

import ec.gob.arch.glpmobil.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_venta);
        textIdentificacion = findViewById(R.id.tvScan);
        edittextEscrita = findViewById(R.id.EtCe);
        Log.v("log_glp ---------->","Entro en ReistroVentaActivty");

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
}
