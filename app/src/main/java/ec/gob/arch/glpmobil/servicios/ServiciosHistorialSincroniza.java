package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.util.Log;

import ec.gob.arch.glpmobil.constantes.CtHistorialSincroniza;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;

public class ServiciosHistorialSincroniza extends ServicioBase{

    /**
     * Columnas a recuperar de la tabla HistorialSincronizacion
     */
    String[] columnas = new String[]{CtHistorialSincroniza.ID_SQLITE,
            CtHistorialSincroniza.ID,
            CtHistorialSincroniza.FECHA_SINCRONIZA,
            CtHistorialSincroniza.USUARIO,
            CtHistorialSincroniza.NUMERO_REGISTROS,
            CtHistorialSincroniza.ESTADO,
            CtHistorialSincroniza.ACCION};

    /**
     * Constructor
     *
     * @param context
     * @author vanessa.ponce
     */
    public ServiciosHistorialSincroniza(Context context) {
        super(context);
        Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> call CONSTRUCTOR ");
    }

    /**
     * Constructor
     *
     * @param historial
     * @author vanessa.ponce
     */

    public void insertar(HistorialSincronizacion historial){
        try {
            abrir();

            ContentValues cv = new ContentValues();
            cv.put(CtHistorialSincroniza.ID, historial.getId());
            cv.put(CtHistorialSincroniza.FECHA_SINCRONIZA, historial.getFecha_sincroniza());
            cv.put(CtHistorialSincroniza.USUARIO, historial.getFecha_sincroniza());
            cv.put(CtHistorialSincroniza.NUMERO_REGISTROS, historial.getNumero_registros());
            cv.put(CtHistorialSincroniza.ESTADO, historial.getEstado());
            cv.put(CtHistorialSincroniza.ACCION, historial.getAccion());

            db.insert(CtHistorialSincroniza.TABLA_HISTORIAL, null, cv);
            Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> insertar() historial id: "+ historial.getId());
            cerrar();
        }catch (Exception e) {
            Log.v("log_glp ---------->", "ERROR: ServiciosHistorialSincroniza --> insertar() historial id: "+ historial.getId());
            e.printStackTrace();
        }

    }
}
