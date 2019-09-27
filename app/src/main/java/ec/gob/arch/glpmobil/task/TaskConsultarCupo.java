package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskConsultarCupo extends AsyncTask {

    Sincronizador sincronizador;

    @Override
    protected List<VwCupoHogar> doInBackground(Object... params) {
        Log.i("log_glp ---------->","INFO TaskConsultarCupo: Inicia ejecución asincrona que llama a consultarCupoWS()");
        List<VwCupoHogar> listaCupoHogares=null;
        try {
            sincronizador = new Sincronizador();
            listaCupoHogares = sincronizador.consultarCupoWS((VwCupoHogar) params[0]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return listaCupoHogares;
    }
}
