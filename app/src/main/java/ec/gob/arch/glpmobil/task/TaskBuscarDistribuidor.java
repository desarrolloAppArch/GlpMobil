package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskBuscarDistribuidor extends AsyncTask {

    Sincronizador sincronizador;

    @Override
    protected List<GeVwClientesGlp> doInBackground(Object... params) {
        Log.i("log_glp ---------->","INFO TaskBuscarDistribuidor: Inicia ejecución asincrona que llama a obtenerDistribuidoresWS()");
        List<GeVwClientesGlp> listaDistribuidores=null;
        try {
            sincronizador = new Sincronizador();
            listaDistribuidores = sincronizador.obtenerDistribuidoresWS((GeVwClientesGlp) params[0]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return listaDistribuidores;
    }
}
