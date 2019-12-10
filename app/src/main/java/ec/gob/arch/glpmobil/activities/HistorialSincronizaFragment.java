package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.telephony.CarrierConfigManager;
import android.support.v4.app.FragmentManager;
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

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtHistorialSincroniza;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.VwPersonaAutorizada;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
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
    private VwPersonaAutorizada persona;
    private List<VwCupoHogar> listaCupoHogar;
    private List<VwPersonaAutorizada> listaPersonas;
    private Button btnSincronizar;
    private Button btnRegresar;
    private TextView tvTituloHistorial;
    private TextView tvFechaUltimaAct;
    private TextView txtFechaUltimaAct;
    private TextView tvEstadoUltimo;
    private TextView txtEstado;
    private ServiciosHistorialSincroniza serviciosHistorialSincroniza;
    private ServiciosPersona serviciosPersona;
    private ServiciosCupoHogar serviciosCupoHogar;
    List<HistorialSincronizacion> lsHistorialSincronizacion;
    private String accion= null;
    private String usuario= null;
    private int estado;
    private int numero_registros;
    private ServicioVenta servicioVenta;


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
        usuario="07GLP-D0045";
        //usuario=objetoSesion.getUsuario().getId();
        btnSincronizar = (Button)view.findViewById(R.id.btnSincronizar);
        btnRegresar = (Button)view.findViewById(R.id.btnRegresar);
        tvTituloHistorial= (TextView)view.findViewById(R.id.tvTituloHistorial);
        txtFechaUltimaAct = view.findViewById(R.id.txtFechaUltimaAct);
        tvFechaUltimaAct = view.findViewById(R.id.tvFechaUltimaAct);
        txtEstado = view.findViewById(R.id.txtEstado);
        tvEstadoUltimo = view.findViewById(R.id.tvEstadoUltimo);
        lvCupoHogar = (ListView) view.findViewById(R.id.lvCupoHogar);
        if(accion.equals(ACCION_VENTAS)){
            btnSincronizar.setVisibility(View.GONE);
            btnRegresar.setVisibility(View.VISIBLE);
            txtFechaUltimaAct.setVisibility(View.GONE);
            tvFechaUltimaAct.setVisibility(View.GONE);
            txtEstado.setVisibility(View.GONE);
            tvEstadoUltimo.setVisibility(View.GONE);
            tvTituloHistorial.setVisibility(view.VISIBLE);
            tvTituloHistorial.setText(CtHistorialSincroniza.TITULO_HISTORIAL_VENTAS);
            serviciosHistorialSincroniza= new ServiciosHistorialSincroniza(getContext());
            lsHistorialSincronizacion = serviciosHistorialSincroniza.buscarVentaPorUsuarioAcccion(usuario, accion);
            llenarListaHistorial(lsHistorialSincronizacion);
        }else{
            btnRegresar.setVisibility(View.GONE);
            btnSincronizar.setVisibility(View.VISIBLE);
            tvTituloHistorial.setVisibility(view.VISIBLE);
            tvTituloHistorial.setText(CtHistorialSincroniza.TITULO_HISTORIAL_CUPOS);
            txtFechaUltimaAct.setVisibility(View.VISIBLE);
            tvFechaUltimaAct.setVisibility(View.VISIBLE);
            txtEstado.setVisibility(View.VISIBLE);
            tvEstadoUltimo.setVisibility(View.VISIBLE);
            mostrarUltimoHistorialActualiza();

        }
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("accion", "1");
                EnviarVentasFragment enviarVentasFragment = new EnviarVentasFragment();
                enviarVentasFragment.setArguments(bundle);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment, enviarVentasFragment).commit();
            }
        });
        btnSincronizar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            try {
                if (ClienteWebServices.validarConexionRed(getContext())){
                    if (validarTablaVentaVacia()){
                        Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> ingresa a Onclick:" );
                        listaCupoHogar = obtenerCupos();
                        if (listaCupoHogar==null ){
                            estado=0;
                            insertarHistorial(accion, usuario,estado,0);
                        }else{
                            numero_registros=listaCupoHogar.size();
                            estado=1;
                            insertarCupoHogar(listaCupoHogar);
                            insertarHistorial(accion, usuario,estado,numero_registros);
                        }
                        lsHistorialSincronizacion = serviciosHistorialSincroniza.buscarTodos();
                        llenarListaHistorial(lsHistorialSincronizacion);
                        mostrarUltimoHistorialActualiza();
                        Log.i("log_glp_cupo ---->","INFO CupoFragment --> Sincronizar() --> después de ejecutar:"+objetoSesion.getListaCupoHogar().size() );
                    }else
                        {
                            UtilMensajes.mostrarMsjError(MensajeError.HISTORIAL_SINCRONIZA_VENTA_LLENA, TituloError.TITULO_ERROR, getContext());
                        }
                }else
                {
                    UtilMensajes.mostrarMsjError(MensajeError.CONEXION_NULL, TituloError.TITULO_ERROR, getContext());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            }
        });

        return view;
    }
    public void insertarHistorial(String accion, String usuario,  Integer estado, Integer numero_registros){
        try {
            serviciosHistorialSincroniza = new ServiciosHistorialSincroniza(getContext());
            HistorialSincronizacion historialNuevo = new HistorialSincronizacion();
            historialNuevo.setAccion(accion);
            historialNuevo.setUsuario(usuario);
            historialNuevo.setFecha_sincroniza(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
            historialNuevo.setEstado(estado);
            historialNuevo.setNumero_registros(numero_registros);
            serviciosHistorialSincroniza.insertar(historialNuevo);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void llenarListaHistorial(List<HistorialSincronizacion> lsHistorialSincronizacion){
        historialSincronizacionAdapter = new HistorialSincronizacionAdapter(getContext(), R.layout.fila_historial_linear, lsHistorialSincronizacion);
        lvCupoHogar.setAdapter(historialSincronizacionAdapter);
    }

    public List<VwCupoHogar> obtenerCupos(){
        vwCupoHogar = new VwCupoHogar();
        List<VwCupoHogar> listaResultado = new ArrayList<>();
        try {
            vwCupoHogar.setDisIdentifica(usuario);
            TaskConsultarCupo tarea = new TaskConsultarCupo();
            tarea.execute(vwCupoHogar);
            listaResultado = (List<VwCupoHogar>) tarea.get();
            //listaResultado=null;
            //listaResultado = new ArrayList<>();
            if (listaResultado==null){
                Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> obtenerCupo() --> RESULTADO: NULL "+listaResultado);
                UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESPUESTA_WS, TituloInfo.TITULO_INFO, getContext());
            }else  if (listaResultado.size()<=0 ){
                Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> obtenerCupo() --> RESULTADO: VACIO"+listaResultado.size());
                UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESULTADOS, TituloInfo.TITULO_INFO, getContext());
            }else {
                Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> obtenerCupo() --> RESULTADO:" +listaResultado.size());
                objetoSesion.setListaCupoHogar(listaResultado);
                UtilMensajes.mostrarMsjInfo(MensajeInfo.HISTORIAL_SINCRONIZA_OK, TituloInfo.TITULO_INFO, getContext());
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> obtenerCupo() --> CATCH:");
            UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESPUESTA_WS, TituloInfo.TITULO_INFO, getContext());
        }
        return listaResultado;
    }


    public void insertarCupoHogar(List<VwCupoHogar> listaCupoHogar){
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> insertarCupoHogar() --> intentando guardar en tabla VwPersonaAutorizada ");

        try
        {
            serviciosCupoHogar = new ServiciosCupoHogar(getContext());
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



    public void insertarPersona(List<VwPersonaAutorizada> listaPersonas){
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> insertarPersona() --> intentando guardar en tabla VwPersonaAutorizada ");
        serviciosPersona = new ServiciosPersona(getContext());
        try
        {
            if(listaPersonas.size()>0){
                for (VwPersonaAutorizada p:listaPersonas){
                    VwPersonaAutorizada personaAutorizada = new VwPersonaAutorizada();
                    personaAutorizada.setCodigo(p.getCodigo());
                    personaAutorizada.setHogCodigo(p.getHogCodigo());
                    personaAutorizada.setApellidoNombre(p.getApellidoNombre());
                    personaAutorizada.setNumeroDocumento(p.getNumeroDocumento());
                    personaAutorizada.setFechaEmisionDocumentoAnio(p.getFechaEmisionDocumentoAnio());
                    personaAutorizada.setFechaEmisionDocumentoMes(p.getFechaEmisionDocumentoMes());
                    personaAutorizada.setFechaEmisionDocumentoDia(p.getFechaEmisionDocumentoDia());
                    personaAutorizada.setPermitirDigitacionIden(p.getPermitirDigitacionIden());
                    serviciosPersona.insertar(personaAutorizada);
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

    public boolean validarTablaVentaVacia(){
        boolean estaVacia = true;
        servicioVenta = new ServicioVenta(getContext());
        List<Venta> venta =  servicioVenta.buscarTodos();
        if (venta.size()>0){
            estaVacia = false;
        }

        return estaVacia;
    }

    public void mostrarUltimoHistorialActualiza(){
        serviciosHistorialSincroniza= new ServiciosHistorialSincroniza(getContext());
        HistorialSincronizacion ultimaActualizacion = serviciosHistorialSincroniza.buscarUltimo();
        if( null!=ultimaActualizacion){
            tvFechaUltimaAct.setText(ultimaActualizacion.getFecha_sincroniza());
            tvEstadoUltimo.setText(ultimaActualizacion.getDescripcionEstado());
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
                 fila.tvEstado.setText(historialSincronizacion.getDescripcionEstado());
                 fila.tvNumeroRegistros.setText(historialSincronizacion.getNumero_registros().toString());

                 Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> despues del llenar la vista "+convertView);

                 return convertView;
             }

         }



}
