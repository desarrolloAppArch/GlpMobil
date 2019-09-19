package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskLoguearUsuario extends AsyncTask {

    Sincronizador sincronizador;

    @Override
    protected GeVwClientesGlp doInBackground(Object... params) {
        Log.i("log_glp ---------->","INFO TaskLoguearUsuario: Inicia ejecución asincrona que llama a obtenerDistribuidoresWS()");
        GeVwClientesGlp cliente = null;
        try
        {
            sincronizador = new Sincronizador();
            cliente = sincronizador.loginUsuarioWS((GeVwClientesGlp) params[0]);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return cliente;
    }
}
