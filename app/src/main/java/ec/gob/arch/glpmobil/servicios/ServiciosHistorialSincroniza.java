package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public List<HistorialSincronizacion> buscarTodos(){
        List<HistorialSincronizacion> lsHistorial = new ArrayList<>();
        try {
            abrir();
            Cursor cursor = db.query(CtHistorialSincroniza.TABLA_HISTORIAL, columnas, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                HistorialSincronizacion historial = obtenerHistorial(cursor);
                lsHistorial.add(historial);
                cursor.moveToNext();
            }
            /**if (cursor!=null) {
             cursor.close();
             }**/
            Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> buscarTodos()");
            cerrar();

        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR ServiciosHistorialSincroniza --> buscarTodos()");
            e.printStackTrace();
        }
        return lsHistorial;
    }

    private HistorialSincronizacion obtenerHistorial(Cursor cursor){

        Log.v("log_glp ---------->", "ERROR ServiciosHistorialSincroniza --> obtenerHistorial()");
        HistorialSincronizacion historial = new HistorialSincronizacion();

        historial.setId_sqlite(cursor.getInt(0));
        historial.setAccion(cursor.getString(1));
        historial.setUsuario(cursor.getString(2));
        historial.setFecha_sincroniza(cursor.getString(3));
        historial.setEstado(cursor.getInt(4));
        historial.setNumero_registros(cursor.getInt(5));

        return historial;
    }
}
