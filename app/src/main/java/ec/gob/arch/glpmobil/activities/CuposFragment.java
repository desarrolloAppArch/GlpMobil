package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.PersonaAutorizada;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskConsultarCupo;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;


public class CuposFragment extends Fragment {

    private ObjetoAplicacion objetoSesion;
    private ListView lvPersonas;
    private PersonasAdapter personasAdapter;
    private VwCupoHogar vwCupoHogar;
    private PersonaAutorizada persona;
    private List<VwCupoHogar> listaCupoHogar;
    private List<PersonaAutorizada> listaPersonas;
    private Button btnSincronizar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_cupos, container, false);
        // Inflate the layout for this fragment

        lvPersonas = (ListView) view.findViewById(R.id.lvPersonas);
        objetoSesion = (ObjetoAplicacion) getActivity().getApplication();
        Log.i("log_glp_cupo ---->","INFO CupoFragment --> Usuario() --> RESULTADO:" + objetoSesion.getUsuario().getId());
        llenarListaPersonas(objetoSesion.getListaPersonas());

        btnSincronizar = (Button)view.findViewById(R.id.btnSincronizar);
        btnSincronizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> ingresa a Onclick:" );
                personasAdapter.obtenerListaPersonas();
                Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> después de ejecutar:" );
            }
        });

        return view;
    }

    public void llenarListaPersonas(List<PersonaAutorizada> listaPersonas){
        personasAdapter = new PersonasAdapter(getActivity().getApplication().getApplicationContext(), R.layout.fila_persona_linear, listaPersonas);
        lvPersonas.setAdapter(personasAdapter);
    }

    public List<VwCupoHogar> obtenerCupos(){
        vwCupoHogar = new VwCupoHogar();
        try {
            if (ClienteWebServices.validarConexionRed(getActivity().getApplication().getApplicationContext())){
                TaskConsultarCupo tarea = new TaskConsultarCupo();
                tarea.execute(this.objetoSesion.getUsuario());
                List<VwCupoHogar> listaResultado = (List<VwCupoHogar>) tarea.get();
                if (listaResultado!=null && listaResultado.size()>0 ){
                    Log.i("log_glp_cupo ---->","INFO CuposFragment --> obtenerCupo() --> RESULTADO:" +listaResultado.size());
                    objetoSesion.setListaCupoHogar(listaResultado);
                }else {
                    Log.i("log_glp_cupo ---->","INFO CuposFragment --> obtenerCupo() --> RESULTADO: NINGUNO");
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESULTADOS, TituloInfo.TITULO_INFO, getActivity().getApplication().getApplicationContext());
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

    class PersonasAdapter extends ArrayAdapter<PersonaAutorizada> {
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
            for (VwCupoHogar c : listaCupoHogar){
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

            CuposFragment.PersonasAdapter.Fila fila = null;

            if(convertView == null){
                LayoutInflater layout = (LayoutInflater) getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layout.inflate(R.layout.fila_persona_linear, null);
                fila = new CuposFragment.PersonasAdapter.Fila();
                fila.tvParroquia = (TextView) convertView.findViewById(R.id.tvParroquia);
                fila.tvFechaDescarga = (TextView) convertView.findViewById(R.id.tvFechaDescarga);
                fila.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
                convertView.setTag(fila);
            }else{
                fila = (CuposFragment.PersonasAdapter.Fila) convertView.getTag();
            }

            PersonaAutorizada persona = listaPersonas.get(posicion);
            fila.tvParroquia.setText(persona.getPerParroquia());
            fila.tvFechaDescarga.setText((CharSequence) new Date());
            fila.tvNombre.setText(persona.getPerApellidoNombre());

            return convertView;
        }

    }

    public void guardarPersona(){
        Log.v("log_glp ---------->", "INFO CuposFragment --> guardarPersona() --> intentando guardar en tabla PersonaAutorizada ");

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
                UtilMensajes.mostrarMsjError(MensajeError.LOGIN_CONEXION_NULL, TituloError.TITULO_ERROR, getActivity().getApplication().getApplicationContext());
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
