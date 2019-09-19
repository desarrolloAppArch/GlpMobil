package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.CupoHogar;
import ec.gob.arch.glpmobil.entidades.PersonaAutorizada;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskConsultarCupo;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

/**
 * CuposActivity manejará al XML activity_cupos
 * @autor Vanessa Ponce
 */
public class CuposActivity extends AppCompatActivity {

    private ObjetoAplicacion objetoSesion;
    private ListView lvPersonas;
    private PersonasAdapter personasAdapter;
    private CupoHogar cupoHogar;
    private PersonaAutorizada persona;
    private List<CupoHogar> listaCupoHogar;
    List<PersonaAutorizada> listaPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cupos);
        setTitle(R.string.title_activity_cupos);

        lvPersonas = (ListView) findViewById(R.id.lvPersonas);

        objetoSesion = (ObjetoAplicacion) getApplication();
        llenarListaPersonas(objetoSesion.getListaPersonas());

    }

    public void llenarListaPersonas(List<PersonaAutorizada> listaPersonas){
        personasAdapter = new PersonasAdapter(this, R.layout.fila_persona_linear, listaPersonas);
        lvPersonas.setAdapter(personasAdapter);
    }

    public List<CupoHogar> obtenerCupos(){
        cupoHogar = new CupoHogar();
        try {
            if (ClienteWebServices.validarConexionRed(this)){
                TaskConsultarCupo tarea = new TaskConsultarCupo();
                tarea.execute(this.objetoSesion.getUsuario().getId());
                List<CupoHogar> listaResultado = (List<CupoHogar>) tarea.get();
                if (listaResultado!=null && listaResultado.size()>0 ){
                    Log.i("log_glp_cupo ---->","INFO CupoActivity --> obtenerCupo() --> RESULTADO:" +listaResultado.size());
                    objetoSesion.setListaCupoHogar(listaResultado);
                }else {
                    Log.i("log_glp_cupo ---->","INFO CupoActivity --> obtenerCupo() --> RESULTADO: NINGUNO");
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESULTADOS, TituloInfo.TITULO_INFO, this);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return objetoSesion.getListaCupoHogar();
    }

    /**
     * Clase PersonasAdapter
     */

    class PersonasAdapter extends ArrayAdapter<PersonaAutorizada>{
        List<PersonaAutorizada> listaPersonas;

        /**
         * Constructor de PersonasAdapter
         * @param context
         * @param resource
         * @param listaPersonas
         */

        public PersonasAdapter(@NonNull Context context, int resource, @NonNull List<PersonaAutorizada> listaPersonas){
            super(context, resource, listaPersonas);
            this.listaPersonas = listaPersonas;
        }

        /**
         * Método que obtiene el listado de personas de los hogares
         * @return listaPersonas
         */

        public List<PersonaAutorizada> obtenerListaPersonas() {
            listaPersonas= new ArrayList<>();
            listaCupoHogar = obtenerCupos();
            for (CupoHogar c : listaCupoHogar){
                listaPersonas = c.getLsPersonaAutorizada();
            }
            return listaPersonas;
        }

        private class Fila{
            TextView tvParroquia;
            TextView tvFechaDescarga;
            TextView tvNombre;

        }

        /**
         * Método que obtiene la vista del listado
         * @param posicion
         * @param convertView
         * @param parent
         * @return
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(final int posicion, View convertView, ViewGroup parent){
            Fila fila = null;

            if(convertView == null){
                LayoutInflater layout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layout.inflate(R.layout.fila_persona_linear, null);
                fila = new Fila();
                fila.tvParroquia = (TextView) convertView.findViewById(R.id.tvParroquia);
                fila.tvFechaDescarga = (TextView) convertView.findViewById(R.id.tvFechaDescarga);
                fila.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
                convertView.setTag(fila);
            }else{
                fila = (Fila) convertView.getTag();
            }

            PersonaAutorizada persona = listaPersonas.get(posicion);
            fila.tvParroquia.setText(persona.getPerParroquia());
            fila.tvFechaDescarga.setText((CharSequence) new Date());
            fila.tvNombre.setText(persona.getPerApellidoNombre());

            return convertView;
        }

    }

    public void guardarPersona(){
        Log.v("log_glp ---------->", "INFO CuposActivity --> guardarPersona() --> intentando guardar en tabla PersonaAutorizada ");

        try
        {
            if(listaPersonas.size()>0){
                for (PersonaAutorizada p:listaPersonas){
                    p.setCodigo(persona.getCodigo());
                    p.setHogCodigo(persona.getHogCodigo());
                    p.setPerApellidoNombre(persona.getPerApellidoNombre());
                    p.setPerNumeroDocumento(persona.getPerNumeroDocumento());
                    p.setPerFechaEmisionDocumento(persona.getPerFechaEmisionDocumento());
                    p.setPerParroquia(persona.getPerParroquia());
                    p.setPerPermitirDigitacionIden(persona.getPerPermitirDigitacionIden());

                }

            }
            else
            {
                UtilMensajes.mostrarMsjError(MensajeError.LOGIN_CONEXION_NULL, TituloError.TITULO_ERROR, this);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
