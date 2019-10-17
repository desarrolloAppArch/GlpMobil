package ec.gob.arch.glpmobil.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import ec.gob.arch.glpmobil.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class EscanearActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView escanerZXing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        escanerZXing = new ZXingScannerView(this);
        // Hacer que el contenido de la actividad sea el escaner
        setContentView(escanerZXing);
        Log.v("log_glp ---------->", "INFO EscanearActivity --> onCreate() --> Hacer que el contenido de la actividad sea el escaner");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Le registramos como un manejador de resultados de escaneo
        escanerZXing.setResultHandler(this);
        escanerZXing.startCamera();
        Log.v("log_glp ---------->", "INFO EscanearActivity --> onResume() --> startCamera()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        escanerZXing.stopCamera();
        Log.v("log_glp ---------->", "INFO EscanearActivity --> onPause() --> stopCamera()");
    }

    /**
     * Escanea hasta obtener resultado
     * @param result
     */
    @Override
    public void handleResult(Result result) {
        String codigo = result.getText();
        Log.v("log_glp ---------->", "INFO EscanearActivity --> handleResult() --> result:"+codigo);
        Intent intentRegreso = new Intent();
        intentRegreso.putExtra("codigo", codigo);
        //setResult nos envía a la actividad que inicio esta actividad y le envía los datos obtenidos del escaneo
        setResult(Activity.RESULT_OK, intentRegreso);
        //finish() llama al método onDestroy()
        finish();
    }

    /**
     * Este método se llama antes de que la actividad sea totalmente destruida.
     * Es llamado cuando el usuario pulsa el botón de volver o cuando se llama al método finish()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("log_glp ---------->", "INFO EscanearActivity --> onDestroy()");
    }
}
