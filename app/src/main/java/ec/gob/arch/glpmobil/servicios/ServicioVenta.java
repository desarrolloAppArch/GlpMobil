package ec.gob.arch.glpmobil.servicios;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;

public class ServicioVenta extends ServicioBase {
    String[] columnas = new String[]{CtVenta.ID_SQLITE,
            CtVenta.CODIGO,
            CtVenta.CODIGOCUPOMES,
            CtVenta.USUARIOVENTA,
            CtVenta.USUARIOCOMPRA,
            CtVenta.SINCRONIZACION,
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
            Log.v("log_glp ---------->", "INFO ServiciosVentas --> buscarTodos()");
            cerrar();
        }catch(Exception e){
            Log.v("log_glp ---------->", "ERROR ServiciosVentas --> buscarTodos()");
            e.printStackTrace();
        }
        return listaVentas;

    }
    private Venta obtenerVenta(Cursor cursor){

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
}
