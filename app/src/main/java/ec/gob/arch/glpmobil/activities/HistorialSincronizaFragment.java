package ec.gob.arch.glpmobil.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.constantes.CtHistorialSincroniza;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.VwPersonaAutorizada;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.servicios.ServiciosCupoHogar;
import ec.gob.arch.glpmobil.servicios.ServiciosHistorialSincroniza;
import ec.gob.arch.glpmobil.servicios.ServiciosPersona;
import ec.gob.arch.glpmobil.servicios.Sincronizador;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
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
        usuario=objetoSesion.getUsuario().getId();
        btnSincronizar = (Button)view.findViewById(R.id.btnSincronizar);
        btnRegresar = (Button)view.findViewById(R.id.btnRegresar);
        tvTituloHistorial= (TextView)view.findViewById(R.id.tvTituloHistorial);
        txtFechaUltimaAct = view.findViewById(R.id.txtFechaUltimaAct);
        tvFechaUltimaAct = view.findViewById(R.id.tvFechaUltimaAct);
        txtEstado = view.findViewById(R.id.txtEstado);
        tvEstadoUltimo = view.findViewById(R.id.tvEstadoUltimo);
        lvCupoHogar = (ListView) view.findViewById(R.id.lvCupoHogar);


        if(accion.equals(ACCION_VENTAS)){
            getActivity().setTitle(R.string.title_fragment_historial_ventas);
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
            getActivity().setTitle(R.string.title_activity_cupos);
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
                            Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> btnSincronizar.setOnClickListener()" );
                            vwCupoHogar = new VwCupoHogar();
                            vwCupoHogar.setDisIdentifica(usuario);
                            TaskConsultarCupoProgress tarea = new TaskConsultarCupoProgress();
                            tarea.execute(vwCupoHogar);
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







    /**
     * Tarea que permite consultar cupos en la ARCH e insertar en la base local del dispositivo
     */
    public class TaskConsultarCupoProgress extends AsyncTask{

        private Sincronizador sincronizador;
        private List<VwCupoHogar> listaCupoHogares;
        private ProgressDialog progressDialog;


        @Override
        protected List<VwCupoHogar> doInBackground(Object... params) {
            Log.i("log_glp ---------->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress --> doInBackground()");
            listaCupoHogares=null;
            try {
                sincronizador = new Sincronizador();
                listaCupoHogares = sincronizador.consultarCupoWS((VwCupoHogar) params[0]);
            }catch(Exception e) {
                e.printStackTrace();
            }
            return listaCupoHogares;
        }

        /**
         * Método que se ejecuta antes de llamar al doInBackground()
         */
        @Override
        protected void onPreExecute() {
            Log.i("log_glp ---------->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress --> onPreExecute()");
            progressDialog = UtilMensajes.mostrarMsjProcesando(getContext(),
                                                                ConstantesGenerales.TITULO_CUPOS_PROGRESS_DIALOG,
                                                                ConstantesGenerales.MENSAJE_PROGRESS_DIALOG_ESPERA);

        }


        /**
         * Método que se ejecuta una vez que termina de ejecutar el doInBackground()
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {

            int numero_registros =0;
            Log.i("log_glp ---------->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress --> onPostExecute()");
            //Actualizo en la base local del dispositivo
            if (listaCupoHogares==null ){
                insertarHistorial(accion, usuario,ConstantesGenerales.CODIGO_HISTORIAL_ESTADO_FALLIDO,0);
            }else{
                numero_registros=listaCupoHogares.size();
                if(numero_registros==1){
                    for (VwCupoHogar cupo :listaCupoHogares){
                        if (cupo.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_CUPOS_ENCONTRADOS)){
                            guardarResultados(numero_registros);
                        }
                    }
                }else{
                    guardarResultados(numero_registros);
                }
            }
            lsHistorialSincronizacion = serviciosHistorialSincroniza.buscarVentaPorUsuarioAcccion(usuario, accion);
            llenarListaHistorial(lsHistorialSincronizacion);
            mostrarUltimoHistorialActualiza();
            Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> después de insertar en base");

            //Cierro el progressDialog
            UtilMensajes.cerrarMsjProcesando(progressDialog);

            if (listaCupoHogares==null){
                Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> onPostExecute() --> RESULTADO: "+listaCupoHogares);
                UtilMensajes.mostrarMsjError(MensajeError.WEB_SERVICE_ERROR_APP, TituloError.TITULO_ERROR, getContext());
            }else if(numero_registros==1){
                for (VwCupoHogar cupo :listaCupoHogares){
                    if (cupo.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_CUPOS_ENCONTRADOS)){
                        Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> onPostExecute() --> RESULTADO:" +numero_registros);
                        objetoSesion.setListaCupoHogar(listaCupoHogares);
                        UtilMensajes.mostrarMsjInfo(MensajeInfo.HISTORIAL_SINCRONIZA_OK+numero_registros, TituloInfo.TITULO_INFO, getContext());
                    }else if(cupo.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_CUPOS_NO_ENCONTRADOS)){
                        Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> onPostExecute() --> RESULTADO: VACIO");
                        UtilMensajes.mostrarMsjError(MensajeError.CONNEXION_OK_SIN_RESULTADOS, TituloError.TITULO_ERROR, getContext());
                    }else if(cupo.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_ERROR_SERVIDOR)){
                        Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> onPostExecute() --> RESULTADO: VACIO");
                        UtilMensajes.mostrarMsjError(MensajeError.WEB_SERVICE_ERROR_SERVIDOR+" ERROR: "+cupo.getCodigoRespuesta(), TituloError.TITULO_ERROR, getContext());
                    }else{
                        Log.i("log_glp_cupo ---->","INFO INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> onPostExecute()) --> RESULTADO: VACIO");
                        UtilMensajes.mostrarMsjError(MensajeError.CONEXION_SERVIDOR_NULL+" ERROR: "+cupo.getCodigoRespuesta(), TituloError.TITULO_ERROR, getContext());
                    }
                }
            }else{
                Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> onPostExecute() --> RESULTADO:" +numero_registros);
                objetoSesion.setListaCupoHogar(listaCupoHogares);
                UtilMensajes.mostrarMsjInfo(MensajeInfo.HISTORIAL_SINCRONIZA_OK+numero_registros, TituloInfo.TITULO_INFO, getContext());
            }

        }

        public void guardarResultados(int numero_registros){
            //progressDialog.setMessage(ConstantesGenerales.MENSAJE_PROGRESS_DIALOG_GUARDANDO_BASE+numero_registros+ " registros.");
            Log.i("log_glp_cupo ---->","INFO HistorialSincronizaFragment --> TaskConsultarCupoProgress() --> guardarResultados() --> RESULTADO:" +numero_registros);
            insertarCupoHogar(listaCupoHogares);
            insertarHistorial(accion, usuario,ConstantesGenerales.CODIGO_HISTORIAL_ESTADO_EXITOSO,numero_registros);
        }
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



    public void insertarCupoHogar(List<VwCupoHogar> listaCupoHogar){
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> insertarCupoHogar() --> intentando guardar en tabla CupoHogar ");

        try
        {
            serviciosCupoHogar = new ServiciosCupoHogar(getContext());
            serviciosPersona = new ServiciosPersona(getContext());
            serviciosPersona.eliminarPersonas();
            serviciosCupoHogar.eliminarCupos();

            if(listaCupoHogar.size()>0){
                for (VwCupoHogar p:listaCupoHogar){
                    VwCupoHogar cupoHogarNuevo = new VwCupoHogar();
                    cupoHogarNuevo.setCmhCodigo(p.getCmhCodigo());
                    cupoHogarNuevo.setCmhDisponible(p.getCmhDisponible());
                    cupoHogarNuevo.setHogCodigo(p.getHogCodigo());
                    cupoHogarNuevo.setCmhAnio(p.getCmhAnio());
                    cupoHogarNuevo.setCmhMes(p.getCmhMes());
                    cupoHogarNuevo.setDisIdentifica(p.getDisIdentifica());
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

    /**
     * Método que inserta las personas autorizadas a retirar en la tabla TABLA_PERSONA
     * @autor vanessa.ponce
     */

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

    /**
     * Método que consulta en la tabla ventas, si existen ventas pendientes de enviar
     * @autor vanessa.ponce
     */

    public boolean validarTablaVentaVacia(){
        boolean estaVacia = false;
        servicioVenta = new ServicioVenta(getContext());
        List<Venta> venta =  servicioVenta.buscarTodos();
        Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> validarTablaVentaVacia() --> venta.size() "+venta.size());
        if (venta.isEmpty()){
            estaVacia = true;
        }else{
            for (Venta v:venta){
                Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> validarTablaVentaVacia() --> venta datos"+v.getUsuario_venta());
                if (!v.getUsuario_venta().equals(usuario)){
                    estaVacia = false;
                    UtilMensajes.mostrarMsjError(MensajeError.HISTORIAL_SINCRONIZA_VENTA_LLENA_OTRO_USUARIO
                            + v.getUsuario_venta(), TituloError.TITULO_ERROR, getContext());
                    break;
                }else{
                    UtilMensajes.mostrarMsjError(MensajeError.HISTORIAL_SINCRONIZA_VENTA_LLENA, TituloError.TITULO_ERROR, getContext());
                }
            }
        }
        return estaVacia;
    }

    /**
     * Método que consulta en la tabla historial, la [ultima actualizacion de cupos
     * @autor vanessa.ponce
     */
    public void mostrarUltimoHistorialActualiza(){
        serviciosHistorialSincroniza= new ServiciosHistorialSincroniza(getContext());
        HistorialSincronizacion ultimaActualizacion = serviciosHistorialSincroniza.buscarUltimo(ACCION_CUPOS, usuario);
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
            TextView tvNumeroCilindroVendidos;
            TextView txtVNumeroCilindrosVendidos;
            TextView txtVNumeroRegistros;

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
                     fila.tvNumeroCilindroVendidos = (TextView) convertView.findViewById(R.id.tvNumeroCilindroVendidos);
                     fila.txtVNumeroRegistros= (TextView)convertView.findViewById(R.id.txtVNumeroRegistros);
                     fila.txtVNumeroCilindrosVendidos= (TextView)convertView.findViewById(R.id.txtVNumeroCilindrosVendidos);

                     convertView.setTag(fila);
                 }else{
                     fila = (Fila) convertView.getTag();
                 }

                 HistorialSincronizacion historialSincronizacion = lsHistorialSincronizacion.get(posicion);
                 fila.tvFechaSincroniza.setText(historialSincronizacion.getFecha_sincroniza());
                 fila.tvUsuario.setText(historialSincronizacion.getUsuario());
                 fila.tvEstado.setText(historialSincronizacion.getDescripcionEstado());
                 fila.tvNumeroRegistros.setText(historialSincronizacion.getNumero_registros().toString());


                 if(accion.equals(ACCION_VENTAS)){

                     fila.tvNumeroCilindroVendidos.setText(historialSincronizacion.getNumero_cilindros().toString());
                     fila.tvNumeroCilindroVendidos.setVisibility(convertView.VISIBLE);
                     fila.txtVNumeroCilindrosVendidos.setVisibility(convertView.VISIBLE);
                     fila.txtVNumeroRegistros.setText("Número Ventas:");
                 }else{
                     fila.tvNumeroCilindroVendidos.setVisibility(convertView.GONE);
                     fila.txtVNumeroCilindrosVendidos.setVisibility(convertView.GONE);
                     fila.txtVNumeroRegistros.setText("Hogares:");
                 }

                 Log.v("log_glp ---------->", "INFO HistorialSincronizaFragment --> getView() --> despues del llenar la vista "+convertView);

                 return convertView;
             }


         }


}
