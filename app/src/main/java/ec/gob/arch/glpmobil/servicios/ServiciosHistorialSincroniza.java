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
import ec.gob.arch.glpmobil.utils.Convertidor;

public class ServiciosHistorialSincroniza extends ServicioBase{

    /**
     * Columnas a recuperar de la tabla HistorialSincronizacion
     */
    String[] columnas = new String[]{CtHistorialSincroniza.ID_SQLITE,
            CtHistorialSincroniza.FECHA_SINCRONIZA,
            CtHistorialSincroniza.USUARIO,
            CtHistorialSincroniza.NUMERO_REGISTROS,
            CtHistorialSincroniza.ESTADO,
            CtHistorialSincroniza.ACCION,
            CtHistorialSincroniza.NUMERO_CILINDROS};

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
            if (historial.getAccion()=="1"){
                cv.put(CtHistorialSincroniza.NUMERO_CILINDROS, historial.getNumero_cilindros());
            }


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
        historial.setNumero_cilindros(cursor.getInt(6));
        return historial;
    }

    public List<HistorialSincronizacion> buscarVentaPorUsuarioAcccion(String usuario, String accion)
    {
        String fechaInicio;
        String fechaFin;
        fechaInicio= Convertidor.dateAString(Convertidor.fechaInicio(Convertidor.restarDia(Convertidor.horafechaSistemaDate(),7)));
        fechaFin= Convertidor.dateAString(Convertidor.fechaFin(Convertidor.horafechaSistemaDate()));
        Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> fechaInicio: "+fechaInicio);
        Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> fechaFin: "+fechaFin);

        List<HistorialSincronizacion> listaHistorial = null;
        try {
            listaHistorial = new ArrayList<>();
            abrir();

            String condicion = CtHistorialSincroniza.USUARIO +"='"+usuario+"' and "+CtHistorialSincroniza.ACCION+ "='"+accion+"' and "+CtHistorialSincroniza.FECHA_SINCRONIZA +" BETWEEN '"+fechaInicio+ "' AND '"+ fechaFin +"'";
            Log.v("log_glp ---------->", "INFO ServiciosHistorialSincroniza --> condicion: "+condicion);
            String orderby = CtHistorialSincroniza.ID_SQLITE+" desc ";
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

    /**
     * Método que elimina historial de acuerdo a la acción (Ventas o Sincronización), usuario que se logea, excepto los registros de los ultimos 7 días.
     * @AUTHOR Blanca Yanguicela
     * @param usuario
     * @param accion
     * @throws Exception
     */
    public void eliminarHistorialPorUsuarioAccionExceptoUltimosSieteDia(String usuario, String accion)throws Exception{
        try {
            String fechaInicio;
            String fechaFin;
            fechaInicio= Convertidor.dateAString(Convertidor.fechaInicio(Convertidor.restarDia(Convertidor.horafechaSistemaDate(),7)));// fecha de inicio será 7 días antes de la fecha actual.
            fechaFin= Convertidor.dateAString(Convertidor.fechaFin(Convertidor.horafechaSistemaDate()));

            abrir();
            String condicion = CtHistorialSincroniza.USUARIO +"='"+usuario+"' and "+CtHistorialSincroniza.ACCION+ "='"+accion+"' and "+CtHistorialSincroniza.FECHA_SINCRONIZA +" NOT BETWEEN '"+fechaInicio+ "' AND '"+ fechaFin +"'";
            long response= db.delete(CtHistorialSincroniza.TABLA_HISTORIAL,condicion, null);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> eliminar() venta : "+ usuario);


        } catch (Exception e) {
            Log.v("log_glp ---------->", "ERROR: ServicioVenta --> eliminar() venta : "+ usuario);
            e.printStackTrace();
        }
        finally {

            cerrar();
        }
    }
}
