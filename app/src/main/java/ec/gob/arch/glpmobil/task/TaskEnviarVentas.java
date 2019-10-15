package ec.gob.arch.glpmobil.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;


import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.servicios.Sincronizador;

public class TaskEnviarVentas extends AsyncTask {
    Sincronizador sincronizador;
    @Override
    protected String  doInBackground(Object... params) {
        Log.i("log_glp ---------->","INFO TaskEnviarVentas: Inicia ejecución asincrona que llama a registrarVentasWs()");
       String respuesta= null;
        try {
            sincronizador = new Sincronizador();
            respuesta = sincronizador. registrarVentasWs((List<Venta>) params[0]);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }
}
