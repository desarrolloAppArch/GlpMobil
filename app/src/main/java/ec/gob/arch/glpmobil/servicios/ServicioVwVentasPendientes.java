package ec.gob.arch.glpmobil.servicios;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.CtVwVentaPendiente;
import ec.gob.arch.glpmobil.entidades.VwVentaPendiente;

public class ServicioVwVentasPendientes extends ServicioBase {
    String[] columnas = new String[]{CtVwVentaPendiente.FECHA_VENTA,
            CtVwVentaPendiente.NUMERO_REGISTROS,
            CtVwVentaPendiente.USUARIO_VENTA
    };

    /**
     * Constructor
     *
     * @param context
     * @author soraya.matute
     */
    public ServicioVwVentasPendientes(Context context) {
        super(context);
    }

    /**
     * servicio que permite buscar las ventas pendientes de la vista VwVentaPendiente
     * @author blanca.yanguicela
     * @param identificacion identificación del usuario que realiza la venta
     * @return
     */
    public List<VwVentaPendiente> buscarVentaPorVendedor(String identificacion)
    {
        List<VwVentaPendiente> listaVentasPendientes = null;
        try {
            listaVentasPendientes = new ArrayList<>();
            abrir();
            String condicion = CtVwVentaPendiente.USUARIO_VENTA+"='"+identificacion+"'";
//            Cursor cursor = db.query(CtVwVentaPendiente.VIEW_VENTAS_PENDIENTES, columnas, null, null, null, null,null);
            Cursor cursor= db.query(CtVwVentaPendiente.VIEW_VENTAS_PENDIENTES,columnas, condicion,null,null,null,null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast())
            {
                VwVentaPendiente vwVentaPendiente = obtenerVentaPendiente(cursor);
                listaVentasPendientes.add(vwVentaPendiente);
                cursor.moveToNext();
            }
            Log.v("log_glp ---------->", "INFO ServicioVwVentasPendientes --> buscarVentaPorVendedor(): "+identificacion);
            cerrar();
        }catch (Exception e){
            e.printStackTrace();
            Log.v("log_glp ---------->", "ERROR ServicioVwVentasPendientes --> buscarVentaPorVendedor(): "+identificacion);
        }
        Log.v("log_glp ---------->", "INFO ServicioVwVentasPendientes --> listaVentasPendientes(): "+listaVentasPendientes.size());
        return  listaVentasPendientes;

    }
    private VwVentaPendiente obtenerVentaPendiente(Cursor cursor){
        Log.v("log_glp ---------->", "ERROR ServicioVenta --> obtenerVenta()");
        VwVentaPendiente ventaPendiente = new VwVentaPendiente();
        ventaPendiente.setFecha_venta(cursor.getString(0));
        ventaPendiente.setNumero_registro(cursor.getInt(1));
        ventaPendiente.setUsuario_venta(cursor.getString(2));
        return ventaPendiente;
    }

}
