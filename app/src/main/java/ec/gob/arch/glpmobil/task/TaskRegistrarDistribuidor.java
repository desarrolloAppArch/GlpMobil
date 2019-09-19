package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskRegistrarDistribuidor extends AsyncTask {

    Sincronizador sincronizador;

    @Override
    protected GeVwClientesGlp doInBackground(Object... params) {
        Log.i("log_glp ---------->","INFO TaskRegistrarDistribuidor: Inicia ejecución asincrona que llama a obtenerDistribuidoresWS()");
        GeVwClientesGlp distribuidor=null;
        try {
            sincronizador = new Sincronizador();
            distribuidor = sincronizador.registrarUsuarioWS((GeVwClientesGlp) params[0]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return distribuidor;
    }
}
