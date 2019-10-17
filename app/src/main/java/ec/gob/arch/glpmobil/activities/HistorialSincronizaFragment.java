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

import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.PersonaAutorizada;
import ec.gob.arch.glpmobil.servicios.ServiciosHistorialSincroniza;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskConsultarCupo;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;
import ec.gob.arch.glpmobil.utils.Convertidor;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;


public class HistorialSincronizaFragment extends Fragment {

    private ObjetoAplicacion objetoSesion;
    private ListView lvCupoHogar;
    //private VwCupoHogarAdapter vwCupoHogarAdapter;
    private HistorialSincronizacionAdapter historialSincronizacionAdapter;
    private VwCupoHogar vwCupoHogar;
    private PersonaAutorizada persona;
    private List<VwCupoHogar> listaCupoHogar;
    private List<PersonaAutorizada> listaPersonas;
    private Button btnSincronizar;
    private ServiciosHistorialSincroniza serviciosHistorialSincroniza;
    List<HistorialSincronizacion> lsHistorialSincronizacion;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_historial_sincroniza, container, false);
        // Inflate the layout for this fragment

        lvCupoHogar = (ListView) view.findViewById(R.id.lvCupoHogar);
        objetoSesion = (ObjetoAplicacion) getActivity().getApplication();
        Log.i("log_glp_cupo ---->","INFO CupoFragment --> Usuario() --> RESULTADO:" + objetoSesion.getUsuario().getId());


        btnSincronizar = (Button)view.findViewById(R.id.btnSincronizar);
        btnSincronizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {
                    Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> ingresa a Onclick:" );
                    // vwCupoHogarAdapter.obtenerListaPersonas();
                    listaCupoHogar = obtenerCupos();
                    if(listaCupoHogar.size()>0){
                        serviciosHistorialSincroniza = new ServiciosHistorialSincroniza(getContext());
                        HistorialSincronizacion historialNuevo = new HistorialSincronizacion();
                        historialNuevo.setAccion("ConsultaCupo");
                        historialNuevo.setUsuario(objetoSesion.getUsuario().getNombre());
                        historialNuevo.setFecha_sincroniza(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
                        historialNuevo.setEstado(1);
                        historialNuevo.setNumero_registros(listaCupoHogar.size());
                        serviciosHistorialSincroniza.insertar(historialNuevo);
                    }else{
                        serviciosHistorialSincroniza = new ServiciosHistorialSincroniza(getContext());
                        HistorialSincronizacion historialNuevo = new HistorialSincronizacion();
                        historialNuevo.setAccion("ConsultaCupo");
                        historialNuevo.setUsuario(objetoSesion.getUsuario().getNombre());
                        historialNuevo.setFecha_sincroniza(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
                        historialNuevo.setEstado(0);
                        historialNuevo.setNumero_registros(listaCupoHogar.size());
                        serviciosHistorialSincroniza.insertar(historialNuevo);
                    }
                    lsHistorialSincronizacion = serviciosHistorialSincroniza.buscarTodos();
                    llenarListaPersonas(lsHistorialSincronizacion);
                    Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> después de ejecutar:"+objetoSesion.getListaCupoHogar().size() );
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        return view;
    }

    public void llenarListaPersonas(List<HistorialSincronizacion> lsHistorialSincronizacion){
        historialSincronizacionAdapter = new HistorialSincronizacionAdapter(getContext(), R.layout.fila_historial_linear, lsHistorialSincronizacion);
        lvCupoHogar.setAdapter(historialSincronizacionAdapter);
        Log.i("log_glp_cupo ---->","INFO CupoFragment --> llenarListaPersonas() --> lvCupoHogar despues:"+lsHistorialSincronizacion.size());
    }

    public List<VwCupoHogar> obtenerCupos(){
        vwCupoHogar = new VwCupoHogar();

        try {
            if (ClienteWebServices.validarConexionRed(getContext())){
                vwCupoHogar.setDisIdentifica(this.objetoSesion.getUsuario().getId());
                TaskConsultarCupo tarea = new TaskConsultarCupo();
                tarea.execute(vwCupoHogar);
                List<VwCupoHogar> listaResultado = (List<VwCupoHogar>) tarea.get();
                if (listaResultado!=null && listaResultado.size()>0 ){
                    Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> obtenerCupo() --> RESULTADO:" +listaResultado.size());
                    objetoSesion.setListaCupoHogar(listaResultado);
                }else {
                    Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> obtenerCupo() --> RESULTADO: NINGUNO");
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESULTADOS, TituloInfo.TITULO_INFO, getContext());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return objetoSesion.getListaCupoHogar();
    }

    /****
     * Clase VwCupoHogarAdapter


    class VwCupoHogarAdapter extends ArrayAdapter<VwCupoHogar> {
        List<VwCupoHogar> lsVwCupoHogar;

        /**
         * Constructor de VwCupoHogarAdapter
         * @param context
         * @param resource
         * @param lsVwCupoHogar


        public VwCupoHogarAdapter(@NonNull Context context, int resource, @NonNull List<VwCupoHogar> lsVwCupoHogar){
            super(context, resource, lsVwCupoHogar);
            this.lsVwCupoHogar = lsVwCupoHogar;
        }

        /**
         * Método que declara las variables para las filas de la tabla
         *

        private class Fila{
            TextView tvUsuario;
            TextView tvFechaSincroniza;
            TextView tvEstado;
            TextView tvNumeroHogares;

        }

        /**
         * Método que obtiene la vista del listado
         * @param posicion
         * @param convertView
         * @param parent
         * @return

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(final int posicion, View convertView, ViewGroup parent){

            Fila fila = null;

            if(convertView == null){
                LayoutInflater layout = (LayoutInflater) getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layout.inflate(R.layout.fila_historial_linear, null);
                fila = new Fila();
                fila.tvUsuario = (TextView) convertView.findViewById(R.id.tvUsuario);
                fila.tvFechaSincroniza = (TextView) convertView.findViewById(R.id.tvFechaSincroniza);
                fila.tvEstado = (TextView) convertView.findViewById(R.id.tvEstado);
                fila.tvNumeroHogares = (TextView) convertView.findViewById(R.id.tvNumeroHogares);
                convertView.setTag(fila);
                Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> entro al if");
            }else{
                Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> entra al else");
                fila = (Fila) convertView.getTag();
            }

            VwCupoHogar cupoHogar = lsVwCupoHogar.get(posicion);
            fila.tvParroquia.setText(cupoHogar.getDisIdentifica());
            //fila.tvFechaDescarga.set( new Date());
            fila.tvNombre.setText(cupoHogar.getHogNumero());
            Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> despues del llenar la vista "+convertView);

            return convertView;
        }

    } */



    /**
     * Clase HistorialSincronizacionAdapter
     */

    class  HistorialSincronizacionAdapter extends ArrayAdapter<HistorialSincronizacion> {
        List<HistorialSincronizacion> lsHistorialSincronizacion;

        /**
         * Constructor de VwCupoHogarAdapter
         * @param context
         * @param resource
         * @param lsHistorialSincronizacion
         */

        public HistorialSincronizacionAdapter(@NonNull Context context, int resource, @NonNull List<HistorialSincronizacion> lsHistorialSincronizacion){
            super(context, resource, lsHistorialSincronizacion);
            this.lsHistorialSincronizacion = lsHistorialSincronizacion;
        }

        /**
         * Método que declara las variables para las filas de la tabla
         *
         */
        private class Fila{
            TextView tvUsuario;
            TextView tvFechaSincroniza;
            TextView tvEstado;
            TextView tvNumeroHogares;

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
                     LayoutInflater layout = (LayoutInflater) getActivity().getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                     convertView = layout.inflate(R.layout.fila_historial_linear, null);
                     fila = new Fila();
                     fila.tvFechaSincroniza = (TextView) convertView.findViewById(R.id.tvFechaSincroniza);
                     fila.tvUsuario = (TextView) convertView.findViewById(R.id.tvUsuario);
                     fila.tvEstado = (TextView) convertView.findViewById(R.id.tvEstado);
                     fila.tvNumeroHogares = (TextView) convertView.findViewById(R.id.tvNumeroHogares);
                     convertView.setTag(fila);
                     Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> entro al if");
                 }else{
                     Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> entra al else");
                     fila = (Fila) convertView.getTag();
                 }

                 HistorialSincronizacion historialSincronizacion = lsHistorialSincronizacion.get(posicion);
                 fila.tvFechaSincroniza.setText(historialSincronizacion.getFecha_sincroniza());
                 fila.tvUsuario.setText(historialSincronizacion.getUsuario());
                 fila.tvEstado.setText(historialSincronizacion.getEstado().toString());
                 fila.tvNumeroHogares.setText(historialSincronizacion.getNumero_registros().toString());

                 Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> despues del llenar la vista "+convertView);

                 return convertView;
             }

         }

    public void guardarPersona(){
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> guardarPersona() --> intentando guardar en tabla PersonaAutorizada ");

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
