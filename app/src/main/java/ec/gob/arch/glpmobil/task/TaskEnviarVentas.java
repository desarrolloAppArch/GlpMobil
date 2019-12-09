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
        String respuesta= null;
       // try {
            Log.i("log_glp ---------->","INFO ingresa sincronizador: ");
            sincronizador = new Sincronizador();
            respuesta = sincronizador. registrarVentasWs((List<Venta>) params[0]);
            Log.i("log_glp ---------->","INFO respuesta: "+ respuesta);
       // }catch(Exception e) {
          //  Log.i("log_glp ---------->","ERROR catch: "+ e.getMessage());
           // e.printStackTrace();

        //}
        Log.i("log_glp ---------->","ERROR respuesta: "+ respuesta);
        return respuesta;
    }
}
