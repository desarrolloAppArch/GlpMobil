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
import java.util.List;

import ec.gob.arch.glpmobil.EditarVentaFragment;
import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.PersonaAutorizada;
import ec.gob.arch.glpmobil.servicios.ServiciosCupoHogar;
import ec.gob.arch.glpmobil.servicios.ServiciosHistorialSincroniza;
import ec.gob.arch.glpmobil.servicios.ServiciosPersona;
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
    private static final String ACCION_VENTAS = "1";
    private static final String ACCION_CUPOS = "0";
    private String mParam1;
    private ObjetoAplicacion objetoSesion;
    private ListView lvCupoHogar;
    private HistorialSincronizacionAdapter historialSincronizacionAdapter;
    private VwCupoHogar vwCupoHogar;
    private PersonaAutorizada persona;
    private List<VwCupoHogar> listaCupoHogar;
    private List<PersonaAutorizada> listaPersonas;
    private Button btnSincronizar;
    private TextView tvTituloHistorial;
    private ServiciosHistorialSincroniza serviciosHistorialSincroniza;
    private ServiciosPersona serviciosPersona;
    private ServiciosCupoHogar serviciosCupoHogar;
    List<HistorialSincronizacion> lsHistorialSincronizacion;
    private String accion= null;
    private String usuario= null;
    private int estado;
    private int numero_registros;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null!=getArguments()){
            accion = getArguments().getString("accion","0");
            Log.i("log_glp_historial ---->","INFO HistorialFragment --> accion() --> accion:" + accion);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_historial_sincroniza, container, false);
        // Inflate the layout for this fragment
        Log.i("log_glp_historial ---->","INFO HistorialFragment --> accion() --> accion:" + accion);
        objetoSesion = (ObjetoAplicacion) getActivity().getApplication();
        //usuario="09GLP-D0715";
        usuario=objetoSesion.getUsuario().getId();
        btnSincronizar = (Button)view.findViewById(R.id.btnSincronizar);
        tvTituloHistorial= (TextView)view.findViewById(R.id.tvTituloHistorial);
        if(accion.equals(ACCION_VENTAS)){
            btnSincronizar.setVisibility(View.GONE);
            tvTituloHistorial.setVisibility(view.VISIBLE);
            lsHistorialSincronizacion = serviciosHistorialSincroniza.buscarVentaPorUsuarioAcccion(usuario, accion);
            llenarListaHistorial(lsHistorialSincronizacion);
        }else{
            btnSincronizar.setVisibility(View.VISIBLE);
            tvTituloHistorial.setVisibility(view.GONE);
            lvCupoHogar = (ListView) view.findViewById(R.id.lvCupoHogar);
        }
        btnSincronizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                try {

                    Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> ingresa a Onclick:" );

                    listaCupoHogar = obtenerCupos();
                    numero_registros=listaCupoHogar.size();
                    if(listaCupoHogar.size()>0){
                        estado=1;insertarCupoHogar(listaCupoHogar);
                        insertarHistorial(accion, usuario,estado,numero_registros);
                    }else{
                        estado=0;
                        insertarHistorial(accion, usuario,estado,numero_registros);
                    }
                    lsHistorialSincronizacion = serviciosHistorialSincroniza.buscarTodos();
                    llenarListaHistorial(lsHistorialSincronizacion);
                    Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> después de ejecutar:"+objetoSesion.getListaCupoHogar().size() );
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        return view;
    }
    public void insertarHistorial(String accion, String usuario,  Integer estado, Integer numero_registros){
        serviciosHistorialSincroniza = new ServiciosHistorialSincroniza(getContext());
        HistorialSincronizacion historialNuevo = new HistorialSincronizacion();
        historialNuevo.setAccion(accion);
        historialNuevo.setUsuario(usuario);
        historialNuevo.setFecha_sincroniza(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
        historialNuevo.setEstado(estado);
        historialNuevo.setNumero_registros(numero_registros);
        serviciosHistorialSincroniza.insertar(historialNuevo);
    }

    public void llenarListaHistorial(List<HistorialSincronizacion> lsHistorialSincronizacion){
        historialSincronizacionAdapter = new HistorialSincronizacionAdapter(getContext(), R.layout.fila_historial_linear, lsHistorialSincronizacion);
        lvCupoHogar.setAdapter(historialSincronizacionAdapter);
    }

    public List<VwCupoHogar> obtenerCupos(){
        vwCupoHogar = new VwCupoHogar();

        try {
            if (ClienteWebServices.validarConexionRed(getContext())){
                vwCupoHogar.setDisIdentifica(usuario);
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


    public void insertarCupoHogar(List<VwCupoHogar> listaCupoHogar){
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> insertarCupoHogar() --> intentando guardar en tabla PersonaAutorizada ");
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());
        try
        {
            if(listaCupoHogar.size()>0){
                for (VwCupoHogar p:listaCupoHogar){
                    VwCupoHogar cupoHogarNuevo = new VwCupoHogar();
                    cupoHogarNuevo.setCmhCodigo(p.getCmhCodigo());
                    cupoHogarNuevo.setCmhDisponible(p.getCmhDisponible());
                    cupoHogarNuevo.setCmhCodigo(p.getHogCodigo());
                    cupoHogarNuevo.setCmhAnio(p.getCmhAnio());
                    cupoHogarNuevo.setCmhMes(p.getCmhMes());
                    insertarPersona(p.getLsPersonaAutorizada());
                    serviciosCupoHogar.insertar(cupoHogarNuevo);

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



    public void insertarPersona(List<PersonaAutorizada> listaPersonas){
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> insertarPersona() --> intentando guardar en tabla PersonaAutorizada ");
        serviciosPersona = new ServiciosPersona(getContext());
        try
        {
            if(listaPersonas.size()>0){
                for (PersonaAutorizada p:listaPersonas){
                    PersonaAutorizada personaAutorizada = new PersonaAutorizada();
                    personaAutorizada.setCodigo(p.getCodigo());
                    personaAutorizada.setHogCodigo(p.getHogCodigo());
                    personaAutorizada.setPerApellidoNombre(p.getPerApellidoNombre());
                    personaAutorizada.setPerNumeroDocumento(p.getPerNumeroDocumento());
                    personaAutorizada.setPerFechaEmisionDocumento(p.getPerFechaEmisionDocumento());
                    personaAutorizada.setPerPermitirDigitacionIden(p.getPerPermitirDigitacionIden());
                    serviciosPersona.insertar(personaAutorizada);
                }
                listaPersonas = new ArrayList<>();
                for (PersonaAutorizada p:listaPersonas){
                    p = new PersonaAutorizada();
                    Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> imprimirPersona() --> PersonaAutorizada "+p.getPerApellidoNombre());
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




    /**
     * Clase HistorialSincronizacionAdapter
     * @autor vanessa.ponce
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
            TextView tvNumeroRegistros;

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
                     fila.tvNumeroRegistros = (TextView) convertView.findViewById(R.id.tvNumeroRegistro);
                     convertView.setTag(fila);
                 }else{
                     fila = (Fila) convertView.getTag();
                 }

                 HistorialSincronizacion historialSincronizacion = lsHistorialSincronizacion.get(posicion);
                 fila.tvFechaSincroniza.setText(historialSincronizacion.getFecha_sincroniza());
                 fila.tvUsuario.setText(historialSincronizacion.getUsuario());
                 fila.tvEstado.setText(historialSincronizacion.getEstado().toString());
                 fila.tvNumeroRegistros.setText(historialSincronizacion.getNumero_registros().toString());

                 Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> despues del llenar la vista "+convertView);

                 return convertView;
             }

         }



}
