package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.constantes.CtUsuario;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;

public class ServiciosCupoHogar extends ServicioBase{

    String[] columnas = new String[]{CtCupoHogar.ID_SQLITE,
            CtCupoHogar.ID,
            CtCupoHogar.CUPO_DISPONIBLE,
            CtCupoHogar.HOG_CODIGO,
            CtCupoHogar.HOG_NUM_INTEGRANTES,
            CtCupoHogar.HOG_NUMERO,
            CtCupoHogar.HOG_PARROQUIA,
            CtCupoHogar.MES
    };

    public ServiciosCupoHogar(Context context) {
        super(context);
        Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> call CONSTRUCTOR ");
    }

    public void insertar(VwCupoHogar cupoHogar){
        try {
            abrir();
            ContentValues cv = new ContentValues();
            cv.put(CtCupoHogar.ID, cupoHogar.getCmhCodigo());
            cv.put(CtCupoHogar.CUPO_DISPONIBLE, cupoHogar.getCmhDisponible());
            cv.put(CtCupoHogar.HOG_CODIGO, cupoHogar.getHogCodigo());
            cv.put(CtCupoHogar.ANIO, cupoHogar.getCmhAnio());
            cv.put(CtCupoHogar.MES, cupoHogar.getCmhMes());
            db.insert(CtCupoHogar.TABLA_CUPO_HOGAR, null,cv);
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> insertar() cupoHogar id: "+ cupoHogar.getCmhCodigo());
            cerrar();

        }catch (Exception e) {
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> insertar() cupoHogar id: "+ cupoHogar.getHogNumero());
            e.printStackTrace();
        }
    }
    public void eliminarCupos(){
        try {
            abrir();
            long response= db.delete(CtCupoHogar.TABLA_CUPO_HOGAR,null, null);
            Log.v("log_glp ---------->", "INFO ServicioVenta --> eliminar() CtCupoHogar : "+ response);
            cerrar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
