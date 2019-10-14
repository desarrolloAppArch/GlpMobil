package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;

public class ServicioVenta extends ServicioBase {
    String[] columnas = new String[]{CtVenta.ID_SQLITE,
            CtVenta.CODIGO_CUPO_MES,
            CtVenta.USUARIO_VENTA,
            CtVenta.USUARIO_COMPRA,
            CtVenta.NOMBRE_COMPRA,
            CtVenta.LATITUD,
            CtVenta.LONGITUD,
            CtVenta.FECHA_VENTA,
            CtVenta.FECHA_MODIFICACION,
            CtVenta.CANTIDAD
    };

    /**
     * Constructor
     *
     * @param context
     * @author soraya.matute
     */
    public ServicioVenta(Context context) {
        super(context);
    }


    public void insertarVenta(Venta venta){
        try {
            abrir();
            ContentValues cv = new ContentValues();
            cv.put(CtVenta.CODIGO_CUPO_MES, venta.getCodigo_cupo_mes());
            cv.put(CtVenta.USUARIO_VENTA, venta.getUsuario_venta());
            cv.put(CtVenta.USUARIO_COMPRA, venta.getUsuario_compra());
            cv.put(CtVenta.NOMBRE_COMPRA, venta.getNombre_compra());
            cv.put(CtVenta.LATITUD, venta.getLatitud());
            cv.put(CtVenta.LONGITUD, venta.getLongitud());
            cv.put(CtVenta.FECHA_VENTA, venta.getFecha_venta());
            cv.put(CtVenta.CANTIDAD, venta.getCantidad());

            db.insert(CtVenta.TABLA_VENTA, null,cv);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> insertar() venta : "+ venta.getUsuario_compra());
            cerrar();
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR: ServicioVenta --> insertar() venta : "+ venta.getUsuario_compra());
            e.printStackTrace();
        }
    }


    public List<Venta> buscarTodos(){
        List<Venta> listaVentas = new ArrayList<Venta>();
        try {

            abrir();
            Cursor cursor = db.query(CtVenta.TABLA_VENTA, columnas, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Venta venta = obtenerVenta(cursor);
                listaVentas.add(venta);
                cursor.moveToNext();
            }
            /**if (cursor!=null) {
             cursor.close();
             }**/
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarTodos()");
            cerrar();
        }catch(Exception e){
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarTodos()");
            e.printStackTrace();
        }
        return listaVentas;

    }
    private Venta obtenerVenta(Cursor cursor){
        Log.v("log_glp ---------->", "ERROR ServicioVenta --> obtenerVenta()");
        Venta venta = new Venta();
        
        venta.setId_sqlite(cursor.getInt(0));
        venta.setCodigo_cupo_mes(cursor.getInt(1));
        venta.setUsuario_venta(cursor.getString(2));
        venta.setUsuario_compra(cursor.getString(3));
        venta.setNombre_compra(cursor.getString(4));
        venta.setLatitud(cursor.getString(5));
        venta.setLongitud(cursor.getString(6));
        venta.setFecha_venta(cursor.getString(7));
        venta.setFecha_modificacion(cursor.getString(8));
        venta.setCantidad(cursor.getInt(9));
        return venta;

    }



    /**
     * Busca las ventas por cliente
     * @param identificacion
     * @return
     */
    public List<Venta> buscarVentaPorIdentificacion(String identificacion)
    {
        List<Venta> listaVentas = null;
        try {
            listaVentas = new ArrayList<>();
            abrir();
            String condicion = CtVenta.USUARIO_COMPRA +"='"+identificacion+"'";
            Cursor cursor = db.query(CtVenta.TABLA_VENTA, columnas, condicion, null, null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                Venta venta = obtenerVenta(cursor);
                listaVentas.add(venta);
                cursor.moveToNext();
            }
            Log.v("log_glp ---------->", "INFO ServicioVenta --> buscarVentaPorIdentificacion(): "+identificacion);
            cerrar();
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServicioVenta --> buscarVentaPorIdentificacion(): "+identificacion);
        }
        return  listaVentas;

    }


}
