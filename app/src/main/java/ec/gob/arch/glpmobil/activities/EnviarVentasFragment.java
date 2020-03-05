package ec.gob.arch.glpmobil.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwVentaPendiente;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.servicios.ServicioVwVentasPendientes;
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


public class EnviarVentasFragment extends Fragment {
   private ObjetoAplicacion objetosSesion;
   private ListView lvResumenVentas;
   private ResumenVentasAdapter resumenVentasAdapter;

   private List<VwVentaPendiente> listaResumenVentas;
   private List<Venta> listaVentasPorEnviar;
   private HistorialSincronizacion historialventa;
   private Button btnEnviarVentas;
   private String respuestaEnvio; //1: realizado 0: no realizado
 //  private String usuarioVenta="07GLP-D0045";
    private String usuarioVenta;
   private String accionHistorial="1" ;//envio venta
    private Integer cilindrosVendidos;
    /**
     * Servicios
     */
    private ServicioVwVentasPendientes servicioVwVentasPendientes;
    private ServicioVenta servicioVenta;
    private ServiciosCupoHogar serviciosCupoHogar;
    private ServiciosHistorialSincroniza serviciosHistorialSincroniza;
    private ServiciosPersona serviciosPersona;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enviar_ventas, container,false);
        getActivity().setTitle(R.string.title_fragment_enviar_ventas);
        lvResumenVentas = (ListView)view.findViewById(R.id.lvResumenVentasEnviar);
        btnEnviarVentas=(Button)view.findViewById(R.id.btnEnviarVentas) ;
        //lvResumenVentas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Al ser el fragment parte del Activity, puedo obtener el Activity con getActivity() ya que no esta directamente relacionado
        objetosSesion= (ObjetoAplicacion) getActivity().getApplication();
        usuarioVenta=objetosSesion.getUsuario().getId();
        servicioVwVentasPendientes = new ServicioVwVentasPendientes(getContext());
        inicializarListaResumenVentas();
        try {
            listaResumenVentas = servicioVwVentasPendientes.buscarVentaPorVendedor(usuarioVenta.toString());
        } catch (Exception e) {
            Toast.makeText(getContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
//        listaResumenVentas = servicioVwVentasPendientes.buscarVentaPorVendedor(objetosSesion.getUsuario().getId().toString());
        for (VwVentaPendiente vt: listaResumenVentas) {
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getUsuario_venta());
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getNumero_registro());
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getFecha_venta());
        }
        llenarListaResumenVentas(listaResumenVentas);

btnEnviarVentas.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            if (ClienteWebServices.validarConexionRed(getContext())){
                servicioVenta = new ServicioVenta(getContext());
                inicializarListaVentasPorEnviar();
                listaVentasPorEnviar=servicioVenta.buscarVentaPorUsuarioVenta(usuarioVenta.toString());
                if(listaVentasPorEnviar.size()>0) {
                    Log.i("log_glp ---------->", "INFO listaVentasPorEnviar --> " + listaVentasPorEnviar.size());
                    TaskEnviarVentasProgress tarea = new TaskEnviarVentasProgress();
                    for (Venta venta:listaVentasPorEnviar) {
                        venta.setImei(objetosSesion.getImei());
                    }
                    tarea.execute(listaVentasPorEnviar);
                }else{
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.NO_TIENE_VENTAS, TituloInfo.TITULO_INFO, getContext());
                }
            }else
            {
                UtilMensajes.mostrarMsjError(MensajeError.CONEXION_NULL, TituloError.TITULO_ERROR, getContext());
            }

        } catch (Exception e) {
            Toast.makeText(getContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
});




        Log.v("Log_Frang_Ventas ->", "INFO onCreateView --> onCreateView()");
        return view;
    }



    public class TaskEnviarVentasProgress extends AsyncTask {

        private Sincronizador sincronizador;
        private ProgressDialog progressDialog;


        @Override
        protected String  doInBackground(Object... params) {
            Log.i("log_glp ---------->","INFO EnviarVentasFragment --> TaskEnviarVentasProgress --> doInBackground()");
            sincronizador = new Sincronizador();
            respuestaEnvio = sincronizador. registrarVentasWs((List<Venta>) params[0]);
            return respuestaEnvio;
        }


        /**
         * Método que se ejecuta antes de llamar al doInBackground()
         */
        @Override
        protected void onPreExecute() {
            Log.i("log_glp ---------->","INFO EnviarVentasFragment --> TaskEnviarVentasProgress --> onPreExecute()");
            progressDialog = UtilMensajes.mostrarMsjProcesando(getContext(),
                                                        ConstantesGenerales.TITULO_VENTAS_PROGRESS_DIALOG,
                                                        ConstantesGenerales.MENSAJE_PROGRESS_DIALOG_ESPERA);
        }


        /**
         * Método que se ejecuta una vez que termina de ejecutar el doInBackground()
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            Log.i("log_glp ---------->","INFO EnviarVentasFragment --> TaskEnviarVentasProgress --> onPostExecute() --> RESPUESTA: "+ respuestaEnvio);
            if (respuestaEnvio!=null) {
                cilindrosVendidos= contarCilindrosVendidos(listaVentasPorEnviar);
                cargarHistorial();
                if (respuestaEnvio.equals(ConstantesGenerales.CODIGO_RESPUESTA_REGISTRO_EXISTOSO_VENTAS)) {
                    eliminarVentas(usuarioVenta);
                    eliminarCupos();
                    eliminarPersonas();
                }
            }
            //Cierro el progressDialog
            UtilMensajes.cerrarMsjProcesando(progressDialog);

            if (null == respuestaEnvio) {
                UtilMensajes.mostrarMsjError(MensajeError.WEB_SERVICE_ERROR_APP, TituloError.TITULO_ERROR, getContext());
                Log.i("log_glp ---------->","INFO EnviarVentasFragment --> TaskEnviarVentasProgress --> onPostExecute() --> RESPUESTA: "+ respuestaEnvio);
            }else {
                if (respuestaEnvio.equals(ConstantesGenerales.CODIGO_RESPUESTA_REGISTRO_EXISTOSO_VENTAS)) {

                    // crea variable para el envio de parametros
                    Bundle bundle = new Bundle();
                    bundle.putString("accion", "1");
                    HistorialSincronizaFragment historialSincronizaFragment = new HistorialSincronizaFragment();
                    historialSincronizaFragment.setArguments(bundle);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment, historialSincronizaFragment).commit();

                    Log.i("log_glp ---------->","INFO EnviarVentasFragment --> TaskEnviarVentasProgress --> onPostExecute() --> RESPUESTA: "+ respuestaEnvio);
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.ENVIO_VENTAS_EXITOSO, TituloInfo.TITULO_INFO.TITULO_INFO, getContext());

                }else if(respuestaEnvio.equals(ConstantesGenerales.CODIGO_RESPUESTA_ERROR_SERVIDOR)){
                    Log.i("log_glp ---------->","ERROR EnviarVentasFragment --> TaskEnviarVentasProgress --> onPostExecute() --> RESPUESTA: "+ respuestaEnvio);
                    UtilMensajes.mostrarMsjError(MensajeError.WEB_SERVICE_ERROR_SERVIDOR+" ERROR: "+respuestaEnvio, TituloError.TITULO_ERROR, getContext());
                }else if(respuestaEnvio.equals(ConstantesGenerales.CODIGO_RESPUESTA_REGISTRO_VENTAS_EXISTE_PROCESO)){
                    Log.i("log_glp ---------->","ERROR EnviarVentasFragment --> TaskEnviarVentasProgress --> onPostExecute() --> RESPUESTA: "+ respuestaEnvio);
                    UtilMensajes.mostrarMsjError(MensajeError.ENVIO_VENTAS_EXISTE_PROCESO+" ERROR: "+respuestaEnvio, TituloError.TITULO_ERROR, getContext());
                }else {
                    Log.i("log_glp ---------->","ERROR EnviarVentasFragment --> TaskEnviarVentasProgress --> onPostExecute() --> RESPUESTA: "+ respuestaEnvio);
                    UtilMensajes.mostrarMsjError(MensajeError.CONEXION_SERVIDOR_NULL+" ERROR: "+respuestaEnvio, TituloError.TITULO_ERROR, getContext());
                }
            }
        }



    }



    public void llenarListaResumenVentas(List<VwVentaPendiente> ventasPendientes){
        resumenVentasAdapter = new ResumenVentasAdapter(getActivity().getApplicationContext(), R.layout.fragment_enviar_ventas, ventasPendientes);
        lvResumenVentas.setAdapter(resumenVentasAdapter);


    }
    public void llenarVentasPorEnviar(String usuarioVenta){


    }

    public void inicializarListaResumenVentas(){
        listaResumenVentas = new ArrayList<>();

    }
    public void inicializarListaVentasPorEnviar(){
        listaVentasPorEnviar = new  ArrayList<>();

    }
    public void inicializarHistorialSincronizacionVenta(){
         historialventa= new HistorialSincronizacion();
    }

    public Integer contarCilindrosVendidos(List<Venta> lista){
        int cilindros=0;
        for (Venta v: lista) {
            cilindros= cilindros+v.getCantidad();
        }
        return cilindros;
    }
    public void cargarHistorial(){
        serviciosHistorialSincroniza= new ServiciosHistorialSincroniza(getContext());
        inicializarHistorialSincronizacionVenta();
        historialventa.setAccion(accionHistorial);
        if (respuestaEnvio.equals(ConstantesGenerales.CODIGO_RESPUESTA_REGISTRO_EXISTOSO_VENTAS)){
            historialventa.setEstado(ConstantesGenerales.CODIGO_HISTORIAL_ESTADO_EXITOSO);
        }else{
            historialventa.setEstado(ConstantesGenerales.CODIGO_HISTORIAL_ESTADO_FALLIDO);
        }
        historialventa.setFecha_sincroniza(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
        historialventa.setNumero_registros(listaVentasPorEnviar.size());
        historialventa.setUsuario(usuarioVenta);
        historialventa.setNumero_cilindros(cilindrosVendidos);


        serviciosHistorialSincroniza.insertar(historialventa);
    }
    public void eliminarVentas(String usuario){
        servicioVenta = new ServicioVenta(getContext());
        try {
            servicioVenta.eliminarVentasEnviadasPorUsuario(usuario);
        } catch (Exception e) {
            Toast.makeText(getContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
    public void eliminarCupos(){
        serviciosCupoHogar= new ServiciosCupoHogar(getContext());
        serviciosCupoHogar.eliminarCupos();

    }

    public void eliminarPersonas(){
        serviciosPersona =  new ServiciosPersona(getContext());
        serviciosPersona.eliminarPersonas();

    }




    /**
     * Clase que permitirá adaptar una lista de java.util en un ListView
     */
    class ResumenVentasAdapter extends ArrayAdapter<VwVentaPendiente> {
        /**
         * variable para manipular la lista de ventas pendientes
         */
        private  List<VwVentaPendiente>listaVentas;

        /**
         *Constructor de ResumenVentasAdapter
         * @param context
         * @param resource
         * @param listaVentas
         */
        public ResumenVentasAdapter(@NonNull Context context, int resource, List<VwVentaPendiente> listaVentas) {
            super(context, resource,listaVentas);
            this.listaVentas= listaVentas;
        }
        private class Fila{
            TextView tvFecha;
            TextView tvNumeroRegistros;
            TextView tvUsuarioVenta;
            TextView tvNumeroCilindros;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Fila fila= null;
            // valida si la vista es null
            if(convertView==null){
                //Si la vista en null debo obtener la vista y atar los componentes
                LayoutInflater layout=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView= layout.inflate(R.layout.fila_venta_pendiente_enviar,null);
                fila= new Fila();
                fila.tvFecha= convertView.findViewById(R.id.tvFechaVenta);
                fila.tvNumeroRegistros= convertView.findViewById(R.id.tvNumeroRegistro);
                fila.tvUsuarioVenta= convertView.findViewById(R.id.tvUsuarioVenta);
                fila.tvNumeroCilindros= convertView.findViewById(R.id.tvNumeroCilindros);
                convertView.setTag(fila);
            }else{
                fila = (Fila) convertView.getTag();
            }

            //Setear los valores de la fila de una determinada posicion
            VwVentaPendiente venta = listaVentas.get(position);
            fila.tvFecha.setText(venta.getFecha_venta());
            fila.tvNumeroRegistros.setText(venta.getNumero_registro().toString());
            fila.tvUsuarioVenta.setText(venta.getUsuario_venta());
            fila.tvNumeroCilindros.setText(venta.getNumero_cilindros().toString());

            return convertView;
        }
    }



}