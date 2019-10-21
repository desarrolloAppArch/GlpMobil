package ec.gob.arch.glpmobil.servicios;

import android.content.Context;
import android.database.Cursor;

import ec.gob.arch.glpmobil.constantes.CtPersona;
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

        PersonaAutorizada personaAutorizada = null;
        String condicion = CtPersona.IDENTIFICACION+"='"+identificacion+"'";
       // Cursor cursor = db.query()

        //continuar aqui
    }


}
