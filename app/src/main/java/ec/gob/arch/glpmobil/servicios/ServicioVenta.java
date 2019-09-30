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
            CtVenta.CODIGOCUPOMES,
            CtVenta.USUARIOVENTA,
            CtVenta.USUARIOCOMPRA,
            CtVenta.NOMBRECOMPRA,
            CtVenta.LATITUD,
            CtVenta.LONGITUD,
            CtVenta.FECHAVENTA,
            CtVenta.FECHAMODIFICACION,
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
            cv.put(CtVenta.CODIGOCUPOMES, venta.getCodigoCupoMes());
            cv.put(CtVenta.USUARIOVENTA, venta.getUsuarioVenta());
            cv.put(CtVenta.USUARIOCOMPRA, venta.getUsuarioCompra());
            cv.put(CtVenta.NOMBRECOMPRA, venta.getNombreCompra());
            cv.put(CtVenta.LATITUD, venta.getLatitud());
            cv.put(CtVenta.LONGITUD, venta.getLongitud());
            cv.put(CtVenta.FECHAVENTA, venta.getFechaVenta());
            cv.put(CtVenta.CANTIDAD, venta.getCantidad());

            db.insert(CtVenta.TABLA_VENTA, null,cv);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> insertar() venta : "+ venta.getUsuarioCompra());
            cerrar();
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR: ServicioVenta --> insertar() venta : "+ venta.getUsuarioCompra());
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
                Venta Ventas = obtenerVenta(cursor);
                listaVentas.add(Ventas);
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
        venta.setCodigo(cursor.getInt(1));
        venta.setCodigoCupoMes(cursor.getLong(2));
        venta.setUsuarioVenta(cursor.getString(3));
        venta.setUsuarioCompra(cursor.getString(4));
        venta.setSincronizacion(cursor.getString(5));
        venta.setLatitud(cursor.getString(6));
        venta.setLongitud(cursor.getString(7));
        venta.setFechaVenta(cursor.getString(8));
        venta.setFechaModificacion(cursor.getString(9));
        venta.setCantidad(cursor.getInt(10));
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
            abrir();
            String condicion = CtVenta.USUARIOCOMPRA+"='"+identificacion+"'";
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
        }
        return  listaVentas;

    }

}
