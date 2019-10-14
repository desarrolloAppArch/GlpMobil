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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.entidades.VwVentaPendiente;
import ec.gob.arch.glpmobil.servicios.ServicioVwVentasPendientes;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;


public class EnviarVentasFragment extends Fragment {
   private ObjetoAplicacion objetosSesion;
   private ListView lvVentas;
   private  VentasAdapter ventasAdapter;
   private ServicioVwVentasPendientes servicioVwVentasPendientes;
   private List<VwVentaPendiente> listaVentas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_enviar_ventas, container,false);
        lvVentas= (ListView)view.findViewById(R.id.lvResumenVentasEnviar);
        //lvVentas.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //Al ser el fragment parte del Activity, puedo obtener el Activity con getActivity() ya que no esta directamente relacionado
        objetosSesion= (ObjetoAplicacion) getActivity().getApplication();
        servicioVwVentasPendientes = new ServicioVwVentasPendientes(getContext());
        inicializarListaVentas();
        listaVentas = servicioVwVentasPendientes.buscarVentaPorVendedor("09GLP-D0715".toString());
//        listaVentas = servicioVwVentasPendientes.buscarVentaPorVendedor(objetosSesion.getUsuario().getId().toString());
        for (VwVentaPendiente vt:listaVentas) {
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getUsuario_venta());
            Log.i("log_glp ---------->", "INFO setOnClickListener --> "+vt.getFecha_venta());
        }
        llenarListaVentasPendientes(listaVentas);






        Log.v("Log_Frang_Ventas ->", "INFO onCreateView --> onCreateView()");
        return view;
    }
    public void llenarListaVentasPendientes(List<VwVentaPendiente> ventasPendientes){
        ventasAdapter= new VentasAdapter(getActivity().getApplicationContext(), R.layout.fragment_enviar_ventas, ventasPendientes);
        lvVentas.setAdapter(ventasAdapter);


    }
    public void inicializarListaVentas(){
        listaVentas = new ArrayList<>();
    }
    /**
     * Clase que permitirá adaptar una lista de java.util en un ListView
     */
    class VentasAdapter extends ArrayAdapter<VwVentaPendiente> {
        /**
         * variable para manipular la lista de ventas pendientes
         */
        private  List<VwVentaPendiente>listaVentas;

        /**
         *Constructor de VentasAdapter
         * @param context
         * @param resource
         * @param listaVentas
         */
        public VentasAdapter(@NonNull Context context, int resource, List<VwVentaPendiente> listaVentas) {
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
