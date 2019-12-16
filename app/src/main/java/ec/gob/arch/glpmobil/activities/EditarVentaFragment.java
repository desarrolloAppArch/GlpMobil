package ec.gob.arch.glpmobil.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.utils.Convertidor;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;


public class EditarVentaFragment extends Fragment {

    /**
     * VARIABLES GLOBALES
     */
    private Button btnCancelarEdicion;
    private Button btnAceptarEdicion;
    private TextView tvIdentificacion;
    private TextView tvNombre;
    private TextView tvFecha;
    private TextView tvUsuarioVenta;
    private TextView etCilindros;
    private Venta venta;

    private Venta ventaSesion = null;

    private ObjetoAplicacion objetoSesion;



    /**
     * Es el primero en ejecutarse, antes del onCreateView()
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> onCreate()" );

        //Verifico si tengo parametros enviados por el fragment anterior
        Bundle parametrosRecibidos = getArguments();
        if(parametrosRecibidos!=null){
            venta = (Venta) parametrosRecibidos.getSerializable(CtVenta.CLAVE_VENTA_EDITAR);
            Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> onCreate() --> venta de = "+venta.getNombre_compra());
        }else{
            Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> onCreate() --> NO EXISTEN PARAMETROS RECIBIDOS");
        }
   }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> onCreateView()" );

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_venta, container, false);
        btnCancelarEdicion = (Button) view.findViewById(R.id.btnCancelarEdicion);
        btnAceptarEdicion = (Button) view.findViewById(R.id.btnAceptarEdicion);
        tvIdentificacion = (TextView) view.findViewById(R.id.tvIdentificacionSeleccionada);
        tvNombre = (TextView) view.findViewById(R.id.tvNombreSeleccionada);
        tvFecha = (TextView) view.findViewById(R.id.tvFechaSeleccionada);
        tvUsuarioVenta = (TextView) view.findViewById(R.id.tvUsuarioVentaSeleccionada);
        etCilindros = (EditText) view.findViewById(R.id.etCilindrosSeleccionada);

        //LLeno la vista con la información de la venta seleccionada en el fragment anterior
        tvIdentificacion.setText(venta.getUsuario_compra());
        tvNombre.setText(venta.getNombre_compra());
        tvFecha.setText(venta.getFecha_venta());
        tvUsuarioVenta.setText(venta.getUsuario_venta());
        etCilindros.setText(venta.getCantidad().toString());

        objetoSesion= (ObjetoAplicacion) getActivity().getApplication();//Objeto Sesion
        ventaSesion = objetoSesion.getVenta();
        getActivity().setTitle(R.string.title_fragment_editar_venta);


        /**
         * Le coloco escucha al botón btnCancelarEdicion
         */
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> btnCancelarEdicion.setOnClickListener(()" );
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment, new ConsultarVentaFragment()).commit();

            }
        });


        /**
         * Le coloco escucha al botón btnAceptarEdicion
         */
        btnAceptarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> btnAceptarEdicion.setOnClickListener(()" );
                //Falta validar que no hayan pasado 5 min de la fecha de venta
                //Falta validar que nunca haya sido actualizado, es decir, que la Fecha_modificacion este vacía
               //actualizar(ventaSesion);
                Log.v("log_glp_cupo ---->","INFO EditarVentaFragment --> "+ venta.getCantidad() );


                //Setear datos venta para enviar a actualizar

                venta.setCantidad(Integer.valueOf(etCilindros.getText().toString()));
                venta.setFecha_modificacion(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));

                //Actualizar en la base de datos
                try {
                    ServicioVenta servicioVenta = new ServicioVenta(getContext()); // instanciamos un nuevo objeto de tipo servicioVenta
                    servicioVenta.actualizar(venta);

                }catch (Exception e){
                    Log.e("log_glp ---------->", "ERROR: EditarVentaFragment --> actualizar() : "+ venta.getId_sqlite());
                    e.printStackTrace();
                }

                // Imprimo en consola la fecha de la venta (falta probar si me imprime la fecha en la que se ingresa el registro de la venta)
                Log.v("Log_glp_reloj------->","La fecha de la venta es igual:"+venta.getFecha_venta());

                //Regresa al fragment EditarVentaFragment
                UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_ACTUALIZADA_OK, TituloInfo.TITULO_INFO, getContext());
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment, new ConsultarVentaFragment()).commit();


                    //comprueba la hora en la que se quiere editar la venta
                //falta traer la hora en la que se registra la venta y hacer la comparación del tiempo
                    venta.setFecha_venta(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));

                // prueba con la fecha
                    Log.i("Log_glp------>","Hora y Fecha del sistema: "+Convertidor.horafechaSistemaDate());
                    if (venta.getFecha_venta().equals(Convertidor.horafechaSistemaDate())){
                        Log.i("Log_glp------>","LA FECHA ES IGUAL");
                        Log.i("Log_glp------>","LA FECHA DEL SISTEMA ES: "+Convertidor.horafechaSistemaTimestamp());
                        Log.i("Log_glp------>","LA FECHA DE LA VENTA ES IGUAL: "+venta.getFecha_venta());


                        Convertidor.compararFechas(null,Convertidor.horafechaSistemaTimestamp());



                    }
                    else{
                        Log.i("Log_glp------>","LA FECHA NO ES IGUAL"+"\n LA FECHA DEL SISTEMA: "+Convertidor.horafechaSistemaTimestamp()+"\n FECHA DE LA VENTA: "+venta.getFecha_venta());

                    }

            }
        });

        return view;
    }
    // método que actualiza la tabla venta
    public void actualizar(Venta venta){
        try {
            ServicioVenta servicioVenta = new ServicioVenta(getContext()); // instanciamos un nuevo objeto de tipo servicioVenta
            Venta ventaActualizar = new Venta();//instanciamos un objeto de tipo ventaActualizar
            ventaActualizar.setCantidad(venta.getCantidad());//seteamos la cantidad
            servicioVenta.actualizar(ventaActualizar);
            Log.e("log_glp ---------->", "INFO: EditarVentaFragment --> actualizar() : "+ venta.getId_sqlite());
        }catch (Exception e){
            Log.e("log_glp ---------->", "ERROR: EditarVentaFragment --> actualizar() : "+ venta.getId_sqlite());
            e.printStackTrace();
        }
    }
}