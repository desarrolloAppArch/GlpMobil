package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import ec.gob.arch.glpmobil.entidades.CupoHogar;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskConsultarCupo extends AsyncTask {

    Sincronizador sincronizador;

    @Override
    protected List<CupoHogar> doInBackground(Object... params) {
        Log.i("log_glp ---------->","INFO TaskConsultarCupo: Inicia ejecución asincrona que llama a consultarCupoWS()");
        List<CupoHogar> listaCupoHogares=null;
        try {
            sincronizador = new Sincronizador();
            listaCupoHogares = sincronizador.consultarCupoWS((CupoHogar) params[0]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return listaCupoHogares;
    }
}
