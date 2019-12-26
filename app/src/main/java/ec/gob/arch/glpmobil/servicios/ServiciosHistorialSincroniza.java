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
            cv.put(CtHistorialSincroniza.FECHA_SINCRONIZA, historial.getFecha_sincroniza());
            cv.put(CtHistorialSincroniza.USUARIO, historial.getUsuario());
            cv.put(CtHistorialSincroniza.NUMERO_REGISTROS, historial.getNumero_registros());
            cv.put(CtHistorialSincroniza.ESTADO, historial.getEstado());
            cv.put(CtHistorialSincroniza.ACCION, historial.getAccion());

            db.insert(CtHistorialSincroniza.TABLA_HISTORIAL, null, cv);
            Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> insertar() historial id: "+ historial.getUsuario());
            cerrar();
        }catch (Exception e) {
            Log.v("log_glp ---------->", "ERROR: ServiciosHistorialSincroniza --> insertar() historial id: "+ historial.getUsuario());
            e.printStackTrace();
        }

    }

    public List<HistorialSincronizacion> buscarTodos(){
        List<HistorialSincronizacion> lsHistorial = new ArrayList<>();
        try {
            abrir();
            String orderby = CtHistorialSincroniza.ID_SQLITE+" desc LIMIT 5";
            Cursor cursor = db.query(CtHistorialSincroniza.TABLA_HISTORIAL, columnas, null, null, null, null, orderby);
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

    public HistorialSincronizacion buscarUltimo(String accion, String usuario ){
        HistorialSincronizacion historialUltimo = null;
        try {
            abrir();
            String orderby = CtHistorialSincroniza.ID_SQLITE+" desc LIMIT 1";
            String condicion = CtHistorialSincroniza.USUARIO +"='"+usuario+"' and "+CtHistorialSincroniza.ACCION+ "='"+accion+"'"  ;
            Cursor cursor = db.query(CtHistorialSincroniza.TABLA_HISTORIAL, columnas, condicion, null, null, null, orderby);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                historialUltimo = obtenerHistorial(cursor);
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
        return historialUltimo;
    }

    private HistorialSincronizacion obtenerHistorial(Cursor cursor){

        HistorialSincronizacion historial = new HistorialSincronizacion();

        historial.setId_sqlite(cursor.getInt(0));
        historial.setFecha_sincroniza(cursor.getString(1));
        historial.setUsuario(cursor.getString(2));
        historial.setNumero_registros(cursor.getInt(3));
        historial.setEstado(cursor.getInt(4));
        historial.setAccion(cursor.getString(5));
        return historial;
    }

    public List<HistorialSincronizacion> buscarVentaPorUsuarioAcccion(String usuario, String accion)
    {
        List<HistorialSincronizacion> listaHistorial = null;
        try {
            listaHistorial = new ArrayList<>();
            abrir();
            String condicion = CtHistorialSincroniza.USUARIO +"='"+usuario+"' and "+CtHistorialSincroniza.ACCION+ "='"+accion+"'" ;
            String orderby = CtHistorialSincroniza.ID_SQLITE+" desc LIMIT 5";
                    Cursor cursor = db.query(CtHistorialSincroniza.TABLA_HISTORIAL, columnas, condicion, null, null, null,orderby);
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                HistorialSincronizacion venta = obtenerHistorial(cursor);
                listaHistorial.add(venta);
                cursor.moveToNext();
            }
            Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> buscarVentaPorUsuarioAcccion(): "+usuario);
            cerrar();
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServiciosHistorialSincroniza --> buscarVentaPorUsuarioAcccion(): "+usuario);
        }
        return  listaHistorial;

    }
}
