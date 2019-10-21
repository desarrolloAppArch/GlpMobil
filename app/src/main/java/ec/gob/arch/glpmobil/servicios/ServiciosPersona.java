package ec.gob.arch.glpmobil.servicios;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import ec.gob.arch.glpmobil.constantes.CtPersona;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.PersonaAutorizada;
import ec.gob.arch.glpmobil.entidades.Usuario;

public class ServiciosPersona extends ServicioBase {

    String[] columnas = new String[]{CtPersona.ID_SQLITE,
                                    CtPersona.HOG_CODIGO,
                                    CtPersona.NOMBRE,
                                    CtPersona.IDENTIFICACION,
                                    CtPersona.FECHA_EMISION_DOCUMENTO,
                                    CtPersona.PERMITE_DIGITACION_IDEN};
    /**
     * Constructor
     * @param context
     * @author soraya.matute
     */
    public ServiciosPersona(Context context) {
        super(context);


    }

    public void buscarPorIdentificacion(String identificacion){

        PersonaAutorizada persona = null;
        try {
            abrir();
            String condicion = CtPersona.IDENTIFICACION+"='"+identificacion+"'";
            Cursor cursor = db.query(CtVenta.TABLA_VENTA, columnas, condicion, null,null, null,null);
            cursor.moveToFirst();
            while (cursor.isAfterLast()){
                persona = obtenerVenta(cursor);
                cursor.moveToNext();
            }
            cerrar();
            Log.v("log_glp ---------->", "INFO ServiciosPersona --> buscarPorIdentificacion() --> RESULTADO ENCONTRADO DE: "+identificacion);
        }catch (Exception e){
            Log.v("log_glp ---------->", "ERROR ServiciosPersona --> buscarPorIdentificacion() --> EXCEPCION AL BUSCAR: "+identificacion);
            e.printStackTrace();
        }

        //continuar aqui
    }

    public void insertar(PersonaAutorizada personaAutorizada){
        try {
            abrir();
            ContentValues cv = new ContentValues();

            cv.put(CtPersona.ID, personaAutorizada.getCodigo());
            cv.put(CtPersona.HOG_CODIGO, personaAutorizada.getHogCodigo());
            cv.put(CtPersona.NOMBRE, personaAutorizada.getPerApellidoNombre());
            cv.put(CtPersona.IDENTIFICACION, personaAutorizada.getPerNumeroDocumento());
            cv.put(CtPersona.FECHA_EMISION_DOCUMENTO, personaAutorizada.getPerFechaEmisionDocumento());
            db.insert(CtPersona.TABLA_PERSONA, null,cv);
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> insertar() cupoHogar id: "+ personaAutorizada.getCodigo());
            cerrar();

        }catch (Exception e) {
            Log.v("log_glp ---------->", "INFO ServiciosCupoHogar --> insertar() cupoHogar id: "+ personaAutorizada.getCodigo());
            e.printStackTrace();
        }
    }

    public PersonaAutorizada obtenerVenta(Cursor cursor){
        PersonaAutorizada persona = new PersonaAutorizada();
        persona.setCodigo(cursor.getInt(0));
        persona.setHogCodigo(cursor.getInt(1));
        persona.setPerApellidoNombre(cursor.getString(2));
        persona.setPerNumeroDocumento(cursor.getString(3));
        //persona.setPerFechaEmisionDocumento(cursor.getString(4));
        persona.setPerPermitirDigitacionIden(cursor.getInt(5));
        return persona;
    }


}
