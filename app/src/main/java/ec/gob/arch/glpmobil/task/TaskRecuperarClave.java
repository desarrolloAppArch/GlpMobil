package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskRecuperarClave extends AsyncTask {

    Sincronizador sincronizador;

    @Override
    protected GeVwClientesGlp doInBackground(Object... params) {
        Log.v("log_glp ----------> ", "INFO TaskActualizarUsuario --> doInBackground --> INICIANDO EJECUCION ASINCRÓNICA");
        GeVwClientesGlp distribuidor=null;
        try {
            sincronizador = new Sincronizador();
            distribuidor = sincronizador.recuperarClaveWS((GeVwClientesGlp) params[0]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return distribuidor;
    }
}
