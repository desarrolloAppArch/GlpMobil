package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.utils.Convertidor;


public class ConsultarVentaFragment extends Fragment {

    private EditText etIdentificacion;
    private Button btnBuscar;
    private ObjetoAplicacion objetosSesion;
    private ListView lvVentas;
    private VentasAdapter ventasAdapter;
    private List<Venta> listaVentas;
    private ServicioVenta servicioVenta;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i("log_glp_cupo ---->","INFO ConsultarVentaFragment --> onCreateView()" );

        //Para relacionar y manipular los componentes con findViewById en un Fragment es necesario obtener la vista del fragment
        View view = inflater.inflate(R.layout.fragment_consultar_venta, container, false);
        etIdentificacion =  (EditText) view.findViewById(R.id.etIdentificacionBuscar);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscarVentas);
        lvVentas = (ListView) view.findViewById(R.id.lvVentasEdicion);

        //Al ser el fragment parte del Activity, puedo obtener el Activity con getActivity() ya que no esta directamente relacionado
        objetosSesion = (ObjetoAplicacion) getActivity().getApplication();

        //Inicializar variables
        servicioVenta =  new ServicioVenta(getContext());


        //Método cuando seleccione un elemento del listado
        lvVentas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("log_glp ---------->", "click en el elemento " + position + " de mi ListView");
                //Obtengo la venta seleccionada de la lista
                Venta ventaSeleccionada = (Venta) lvVentas.getItemAtPosition(position);
                Log.i("log_glp ---------->", "parametrosEnvio: "+ventaSeleccionada.getNombre_compra());

                //Creo ub objeto Bundle para enviarlo al siguiente Fragment
                Bundle parametrosEnvio = new Bundle();
                parametrosEnvio.putSerializable(CtVenta.CLAVE_VENTA_EDITAR,ventaSeleccionada);//Para que esto funcione la clase Venta debe implementar la interfaz Serializable
                EditarVentaFragment editarVentaFragment = new EditarVentaFragment();
                editarVentaFragment.setArguments(parametrosEnvio);

                //Inicio el siguiente fragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment, editarVentaFragment).commit();

            }
        });

        /**
         * Coloco la escucha al botón btnBuscar
         */
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicializarListaVentas();
                //Busco las ventas de la base de datos
                try {
                    listaVentas = servicioVenta.buscarVentaPorIdentificacion(etIdentificacion.getText().toString());

                for (Venta vt:listaVentas) {
                    Log.i("log_glp ---------->", "INFO getCodigo_cupo_mes --> "+vt.getCodigo_cupo_mes());
                    Log.i("log_glp ---------->", "INFO getUsuario_venta --> "+vt.getUsuario_venta());
                    Log.i("log_glp ---------->", "INFO getUsuario_compra --> "+vt.getUsuario_compra());
                    Log.i("log_glp ---------->", "INFO getNombre_compra --> "+vt.getNombre_compra());
                    Log.i("log_glp ---------->", "INFO getFecha_venta --> "+vt.getFecha_venta());
                    Log.i("log_glp ---------->", "INFO getCantidad --> "+vt.getCantidad());
                    Log.i("log_glp ---------->", "--------------------------------------- ");
                }
                llenarListaVentas(listaVentas);

                Toast.makeText(getContext(),+listaVentas.size()+" Registros encontrado de: "+etIdentificacion.getText(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(),e.getMessage()+etIdentificacion.getText(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        return view;
    }



    public void llenarListaVentas(List<Venta> ventas){
        ventasAdapter = new VentasAdapter(getActivity().getApplicationContext(), R.layout.fila_venta_edicion_linear, ventas);
        lvVentas.setAdapter(ventasAdapter);
    }

    public void inicializarListaVentas(){
        listaVentas = new ArrayList<>();
    }





    /**
     * Clase que permitirá adaptar una lista de java.util en un ListView
     */
    private class VentasAdapter extends ArrayAdapter<Venta>{

        /**
         * variable para manipular la lista de ventas
         */
        private List<Venta> listaVentas;


        /**
         * Consatructor del Adaptador
         * @param context
         * @param resource
         * @param listaVentas
         */
        public VentasAdapter(@NonNull Context context, int resource, @NonNull List<Venta> listaVentas) {
            super(context, resource, listaVentas);
            this.listaVentas = listaVentas;
        }



        private class Fila{
            TextView tvIdentificacion;
            TextView tvNombre;
            TextView tvFecha;
            TextView tvCantidad;
        }



        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            Fila fila = null;

            //Valido si la vista es null
            if(convertView == null){
                //Si la vista en null debo obtener la vista y atar los componentes
                LayoutInflater layout = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layout.inflate(R.layout.fila_venta_edicion_linear,null);
                fila = new Fila();
                fila.tvIdentificacion = convertView.findViewById(R.id.tvIdentificacionEdicion);
                fila.tvNombre = convertView.findViewById(R.id.tvNombreEdicion);
                fila.tvFecha = convertView.findViewById(R.id.tvFechaEdicion);
                fila.tvCantidad = convertView.findViewById(R.id.tvCantidadEdicion);
                convertView.setTag(fila);
            }else{
                //Si la vista no es nula debo obtener las relaciones de los componenetes de la vista a mi objeto fila
                fila = (Fila) convertView.getTag();
            }




            //Setear los valores de la fila de una determinada posicion
            Venta venta = listaVentas.get(position);
            fila.tvIdentificacion.setText(venta.getUsuario_compra());
            fila.tvCantidad.setText(venta.getCantidad().toString());
            fila.tvNombre.setText(venta.getNombre_compra());
            fila.tvFecha.setText(venta.getFecha_venta());


            return convertView;
        }
    }


}
