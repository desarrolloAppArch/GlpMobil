package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;

public class ServiciosCupoHogar extends ServicioBase{

    String[] columnas = new String[]{CtCupoHogar.ID_SQLITE,
            CtCupoHogar.ID,
            CtCupoHogar.CUPO_DISPONIBLE,
            CtCupoHogar.HOG_CODIGO,
            CtCupoHogar.ANIO,
            CtCupoHogar.MES
    };

    public ServiciosCupoHogar(Context context) {
        super(context);
        Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> call CONSTRUCTOR ");
    }

    public void insertar(VwCupoHogar cupoHogar) throws Exception {
        try {
            abrir();
            ContentValues cv = new ContentValues();
            cv.put(CtCupoHogar.ID, cupoHogar.getCmhCodigo());
            cv.put(CtCupoHogar.CUPO_DISPONIBLE, cupoHogar.getCmhDisponible());
            cv.put(CtCupoHogar.HOG_CODIGO, cupoHogar.getHogCodigo());
            cv.put(CtCupoHogar.ANIO, cupoHogar.getCmhAnio());
            cv.put(CtCupoHogar.MES, cupoHogar.getCmhMes());
            db.insert(CtCupoHogar.TABLA_CUPO_HOGAR, null,cv);
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> insertar() cupoHogar id: "+ cupoHogar.getCmhDisponible());
            cerrar();

        }catch (Exception e) {
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> insertar() cupoHogar id: "+ cupoHogar.getHogNumero());
            e.printStackTrace();
        }
    }


    /**
     * Método que busca el cupo de un hogar
     * @param hog_codigo
     * @return VwCupoHogar
     */
    public VwCupoHogar buscarPorHogar(Integer hog_codigo){
        VwCupoHogar cupoHogar = null;
        try {
            abrir();
            String condicion = CtCupoHogar.HOG_CODIGO+"='"+hog_codigo+"'";
            Cursor cursor = db.query(CtCupoHogar.TABLA_CUPO_HOGAR, columnas, condicion, null,null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                cupoHogar = obtenerCupoHogar(cursor);
                cursor.moveToNext();
            }
            cerrar();
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> buscarPorIdentificacion() --> RESULTADO ENCONTRADO DE: "+hog_codigo);
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR ServiciosCupoHogar --> buscarPorIdentificacion() --> EXCEPCION AL BUSCAR: "+hog_codigo);
            e.printStackTrace();
        }
        return  cupoHogar;
    }

    public VwCupoHogar obtenerCupoHogar(Cursor cursor){
        Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> obtenerCupoHogar()");
        VwCupoHogar cupoHogar = new VwCupoHogar();
        cupoHogar.setIdSqlite(cursor.getInt(0));
        cupoHogar.setCmhCodigo(cursor.getInt(1));
        cupoHogar.setCmhDisponible(cursor.getInt(2));
        cupoHogar.setHogCodigo(cursor.getInt(3));
        cupoHogar.setCmhAnio(cursor.getInt(4));
        cupoHogar.setCmhMes(cursor.getInt(5));
        return cupoHogar;
    }

    /**
     *
     * @param cupoHogar
     * @throws Exception
     * @autor
     */
    public void actualizar(VwCupoHogar cupoHogar) throws Exception{
        try{
            abrir();
            ContentValues cv = new ContentValues();
            cv.put(CtCupoHogar.CUPO_DISPONIBLE, cupoHogar.getCmhDisponible());
            db.update(CtCupoHogar.TABLA_CUPO_HOGAR,cv,CtCupoHogar.ID_SQLITE+"="+cupoHogar.getIdSqlite(),null);
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> actualizar() --> HOGAR: "+ cupoHogar.getIdSqlite()+" ACTUALIZANDO A CUPO DISPONIBLE DE: "+cupoHogar.getCmhDisponible());
            cerrar();
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR ServiciosCupoHogar --> actualizar() --> HOGAR: "+ cupoHogar.getIdSqlite());
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
