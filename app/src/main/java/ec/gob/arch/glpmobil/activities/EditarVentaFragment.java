package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.net.Uri;
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


        /**
         * Le coloco escucha al botón btnAceptarEdicion
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

                //Setear datos venta para enviar a actualizar
                venta.setCantidad(Integer.valueOf(etCilindros.getText().toString()));
                venta.setFecha_modificacion(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));

                //Actualizar en la base de datos
                //Falta crear el servicio que permita actualizar



                UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_ACTUALIZADA_OK, TituloInfo.TITULO_INFO, getContext());
            }
        });

        return view;
    }




}
