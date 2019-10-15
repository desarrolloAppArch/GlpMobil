package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.concurrent.ExecutionException;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwVentaPendiente;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.servicios.ServicioVwVentasPendientes;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskEnviarVentas;


public class EnviarVentasFragment extends Fragment {
   private ObjetoAplicacion objetosSesion;
   private ListView lvResumenVentas;
   private ResumenVentasAdapter resumenVentasAdapter;
   private ServicioVwVentasPendientes servicioVwVentasPendientes;
   private ServicioVenta servicioVenta;
   private List<VwVentaPendiente> listaResumenVentas;
   private List<Venta> listaVentasPorEnviar;
   private Button btnEnviarVentas;
   private String respuestaEnvio;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enviar_ventas, container,false);
        lvResumenVentas = (ListView)view.findViewById(R.id.lvResumenVentasEnviar);
        btnEnviarVentas=(Button)view.findViewById(R.id.btnEnviarVentas) ;
        //lvResumenVentas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Al ser el fragment parte del Activity, puedo obtener el Activity con getActivity() ya que no esta directamente relacionado
        objetosSesion= (ObjetoAplicacion) getActivity().getApplication();
        servicioVwVentasPendientes = new ServicioVwVentasPendientes(getContext());
        inicializarListaResumenVentas();
        listaResumenVentas = servicioVwVentasPendientes.buscarVentaPorVendedor("09GLP-D0715".toString());
//        listaResumenVentas = servicioVwVentasPendientes.buscarVentaPorVendedor(objetosSesion.getUsuario().getId().toString());
        for (VwVentaPendiente vt: listaResumenVentas) {
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getUsuario_venta());
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getFecha_venta());
        }
        llenarListaResumenVentas(listaResumenVentas);

btnEnviarVentas.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        try {
            servicioVenta = new ServicioVenta(getContext());
            inicializarListaVentasPorEnviar();
            listaVentasPorEnviar=servicioVenta.buscarVentaPorUsuarioVenta("09GLP-D0715".toString());
            Log.i("log_glp ---------->", "INFO listaVentasPorEnviar --> "+listaVentasPorEnviar.size());
            TaskEnviarVentas tarea= new TaskEnviarVentas();
            tarea.execute(listaVentasPorEnviar);

            respuestaEnvio= (String) tarea.get();
            if(respuestaEnvio.equals("1")){
                Log.i("log_glp ---------->", "INFO respuestaEnvio Completo--> "+respuestaEnvio);
            }else{
                Log.i("log_glp ---------->", "INFO respuestaEnvio No enviado--> "+respuestaEnvio);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
