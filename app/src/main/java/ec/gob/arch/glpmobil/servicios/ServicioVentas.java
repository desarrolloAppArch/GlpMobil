package ec.gob.arch.glpmobil.servicios;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.CtVentas;
import ec.gob.arch.glpmobil.constantes.CtVentas;
import ec.gob.arch.glpmobil.entidades.Ventas;
import ec.gob.arch.glpmobil.entidades.Ventas;

public class ServicioVentas extends ServicioBase {
    String[] columnas = new String[]{CtVentas.ID_SQLITE,
            CtVentas.CODIGO,
            CtVentas.CODIGOCUPOMES,
            CtVentas.USUARIOVENTA,
            CtVentas.USUARIOCOMPRA,
            CtVentas.SINCRONIZACION,
            CtVentas.LATITUD,
            CtVentas.LONGITUD,
            CtVentas.FECHAVENTA,
            CtVentas.FECHAMODIFICACION,
            CtVentas.CANTIDAD
    };
    /**
     * Constructor
     *
     * @param context
     * @author soraya.matute
     */
    public ServicioVentas(Context context) {
        super(context);
    }

    public List<Ventas> buscarTodos(){
        List<Ventas> listaVentas = new ArrayList<Ventas>();
        try {

            abrir();
            Cursor cursor = db.query(CtVentas.TABLA_VENTAS, columnas, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Ventas Ventas = obtenerVentas(cursor);
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
    private Ventas obtenerVentas(Cursor cursor){
        Ventas Ventas = new Ventas();
        Ventas.setId_sqlite(cursor.getInt(0));
        Ventas.setCodigo(cursor.getInt(1));
        Ventas.setCodigoCupoMes(cursor.getLong(2));
        Ventas.setUsuarioVenta(cursor.getString(3));
        Ventas.setUsuarioCompra(cursor.getString(4));
        Ventas.setSincronizacion(cursor.getString(5));
        Ventas.setLatitud(cursor.getString(6));
        Ventas.setLongitud(cursor.getString(7));
        Ventas.setFechaVenta(cursor.getString(8));
        Ventas.setFechaModificacion(cursor.getString(9));
        Ventas.setCantidad(cursor.getInt(10));
        return Ventas;

    }
}
