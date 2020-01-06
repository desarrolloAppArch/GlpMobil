package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.MonthDisplayHelper;
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
import java.util.concurrent.ExecutionException;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.HistorialSincronizacion;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwVentaPendiente;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.servicios.ServicioVwVentasPendientes;
import ec.gob.arch.glpmobil.servicios.ServiciosCupoHogar;
import ec.gob.arch.glpmobil.servicios.ServiciosHistorialSincroniza;
import ec.gob.arch.glpmobil.servicios.ServiciosPersona;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskEnviarVentas;
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
                    TaskEnviarVentas tarea = new TaskEnviarVentas();
                    tarea.execute(listaVentasPorEnviar);
                    Log.i("log_glp ---------->", "INFO tarea.get() --> " + tarea.get());

                    respuestaEnvio = (String) tarea.get();

                    if (null == respuestaEnvio) {
                        Toast.makeText(getContext(), "No se a podido conectar con ARCH", Toast.LENGTH_LONG).show();
                    }else {
                        cargarHistorial();
                        if (respuestaEnvio.equals("1")) {
                            eliminarVentas(usuarioVenta);
                            eliminarCupos();
                            eliminarPersonas();

                            // crea variable para el envio de parametros
                            Bundle bundle = new Bundle();
                            bundle.putString("accion", "1");
                            HistorialSincronizaFragment historialSincronizaFragment = new HistorialSincronizaFragment();
                            historialSincronizaFragment.setArguments(bundle);

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.beginTransaction().replace(R.id.fragment, historialSincronizaFragment).commit();
                            Log.i("log_glp ---------->", "INFO respuestaEnvio Completo--> " + respuestaEnvio);
                            UtilMensajes.mostrarMsjInfo(MensajeInfo.ENVIO_VENTA_EXITOSO, TituloInfo.TITULO_INFO.TITULO_INFO, getContext());
                            Toast.makeText(getContext(), "Envio Exitoso, ", Toast.LENGTH_LONG).show();
                        } else {
                            Log.i("log_glp ---------->", "INFO respuestaEnvio No enviado--> " + respuestaEnvio);
                            Toast.makeText(getContext(), "Envio Fallido", Toast.LENGTH_LONG).show();
                        }
                    }
                }else{
                    Toast.makeText(getContext(), "No tiene, Venta pendientes de enviar", Toast.LENGTH_LONG).show();
                }
            }else
            {
                UtilMensajes.mostrarMsjError(MensajeError.CONEXION_NULL, TituloError.TITULO_ERROR, getContext());
            }

        } catch (ExecutionException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (InterruptedException e) {
            Toast.makeText(getContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getContext(),  e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
});




        Log.v("Log_Frang_Ventas ->", "INFO onCreateView --> onCreateView()");
        return view;
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
    public void cargarHistorial(){
        serviciosHistorialSincroniza= new ServiciosHistorialSincroniza(getContext());
        inicializarHistorialSincronizacionVenta();
        historialventa.setAccion(accionHistorial);
        historialventa.setEstado(Integer.parseInt(respuestaEnvio));//1: realizado 0: no realizado
        historialventa.setFecha_sincroniza(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
        historialventa.setNumero_registros(listaVentasPorEnviar.size());
        historialventa.setUsuario(usuarioVenta);

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
                convertView.setTag(fila);
            }else{
                fila = (Fila) convertView.getTag();
            }

            //Setear los valores de la fila de una determinada posicion
            VwVentaPendiente venta = listaVentas.get(position);
            fila.tvFecha.setText(venta.getFecha_venta());
            fila.tvNumeroRegistros.setText(venta.getNumero_registro().toString());
            fila.tvUsuarioVenta.setText(venta.getUsuario_venta());

            return convertView;
        }
    }



}