package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import ec.gob.arch.glpmobil.constantes.CtPersona;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.VwPersonaAutorizada;

public class ServiciosPersona extends ServicioBase {

    String[] columnas = new String[]{CtPersona.ID_SQLITE,
                                    CtPersona.ID,
                                    CtPersona.HOG_CODIGO,
                                    CtPersona.NOMBRE,
                                    CtPersona.IDENTIFICACION,
                                    CtPersona.FECHA_EMISION_DOCUMENTO_ANIO,
                                    CtPersona.FECHA_EMISION_DOCUMENTO_MES,
                                    CtPersona.FECHA_EMISION_DOCUMENTO_DIA,
                                    CtPersona.PERMITE_DIGITACION_IDEN};
    /**
     * Constructor
     * @param context
     * @author soraya.matute
     */
    public ServiciosPersona(Context context) {
        super(context);


    }

    /**
     * Método que busca una persona por su identificación
     * @param identificacion
     * @return PersonaAutorizada
     */
    public VwPersonaAutorizada buscarPorIdentificacion(String identificacion){

        VwPersonaAutorizada persona = null;
        try {
            abrir();
            String condicion = CtPersona.IDENTIFICACION+"='"+identificacion+"'";
            Cursor cursor = db.query(CtPersona.TABLA_PERSONA, columnas, condicion, null,null, null,null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                persona = obtenerPersona(cursor);
                cursor.moveToNext();
            }
            cerrar();
            Log.v("log_glp ---------->", "INFO ServiciosPersona --> buscarPorIdentificacion() --> RESULTADO ENCONTRADO DE: "+identificacion);
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR ServiciosPersona --> buscarPorIdentificacion() --> EXCEPCION AL BUSCAR: "+identificacion);
            e.printStackTrace();
        }
        return  persona;
    }


    public void insertar(VwPersonaAutorizada personaAutorizada) throws Exception {
        try {
            abrir();
            ContentValues cv = new ContentValues();
            
            cv.put(CtPersona.ID, personaAutorizada.getCodigo());
            cv.put(CtPersona.HOG_CODIGO, personaAutorizada.getHogCodigo());
            cv.put(CtPersona.NOMBRE, personaAutorizada.getApellidoNombre());
            cv.put(CtPersona.IDENTIFICACION, personaAutorizada.getNumeroDocumento());
            cv.put(CtPersona.FECHA_EMISION_DOCUMENTO_ANIO, personaAutorizada.getFechaEmisionDocumentoAnio());
            cv.put(CtPersona.FECHA_EMISION_DOCUMENTO_MES, personaAutorizada.getFechaEmisionDocumentoMes());
            cv.put(CtPersona.FECHA_EMISION_DOCUMENTO_DIA, personaAutorizada.getFechaEmisionDocumentoDia());
            cv.put(CtPersona.PERMITE_DIGITACION_IDEN, personaAutorizada.getPermitirDigitacionIden());
            db.insert(CtPersona.TABLA_PERSONA, null,cv);
            Log.v("log_glp ---------->", "INFO ServiciosPersona --> insertar() persona id: "+ personaAutorizada.getNumeroDocumento());

        }catch (Exception e) {
            Log.v("log_glp ---------->", "ERROR ServiciosPersona --> insertar() persona id: "+ personaAutorizada.getNumeroDocumento());
            e.printStackTrace();
        }finally {
            cerrar();
        }
    }

    public VwPersonaAutorizada obtenerPersona(Cursor cursor){
        Log.v("log_glp ---------->", "INFO ServiciosPersona --> obtenerPersona()");
        VwPersonaAutorizada persona = new VwPersonaAutorizada();
        persona.setIdSqlite(cursor.getInt(0));
        persona.setCodigo(cursor.getInt(1));
        persona.setHogCodigo(cursor.getInt(2));
        persona.setApellidoNombre(cursor.getString(3));
        persona.setNumeroDocumento(cursor.getString(4));
        persona.setFechaEmisionDocumentoAnio(cursor.getInt(5));
        persona.setFechaEmisionDocumentoMes(cursor.getInt(6));
        persona.setFechaEmisionDocumentoDia(cursor.getInt(7));
        persona.setPermitirDigitacionIden(cursor.getInt(8));
        return persona;
    }


}
