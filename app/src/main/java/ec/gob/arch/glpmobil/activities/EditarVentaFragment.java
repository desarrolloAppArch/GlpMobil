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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.servicios.ServiciosCupoHogar;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.utils.Convertidor;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

import static java.lang.String.valueOf;


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
    private TextView tvCupoDisponible;
    private Venta venta;
    private ObjetoAplicacion objetoSesion;

    private ServiciosCupoHogar serviciosCupoHogar;
    private ServicioVenta servicioVenta;
    private VwCupoHogar cupoHogar;
    private int numeroCilindrosAux;


    /**
     * Es el primero en ejecutarse, antes del onCreateView()
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ;

        //Verifico si tengo parametros enviados por el fragment anterior
        Bundle parametrosRecibidos = getArguments();
        if(parametrosRecibidos!=null){
            venta = (Venta) parametrosRecibidos.getSerializable(CtVenta.CLAVE_VENTA_EDITAR);
            cupoHogar = (VwCupoHogar) parametrosRecibidos.getSerializable(CtCupoHogar.CLAVE_CUPO_HOGAR);
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
        tvCupoDisponible = (TextView) view.findViewById(R.id.tvCupoDisponibleSelecionada);

        //LLeno la vista con la información de la venta seleccionada en el fragment anterior
        tvIdentificacion.setText(venta.getUsuario_compra());
        tvNombre.setText(venta.getNombre_compra());
        tvFecha.setText(venta.getFecha_venta());
        tvUsuarioVenta.setText(venta.getUsuario_venta());
        etCilindros.setText(venta.getCantidad().toString());
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());


        try {
            cupoHogar = serviciosCupoHogar.buscarPorCupo(venta.getCodigo_cupo_mes());
            tvCupoDisponible.setText(cupoHogar.getCmhDisponible().toString());//agrego el cupo disponible a la vista
        } catch (Exception e) {
            e.printStackTrace();
        }
        objetoSesion= (ObjetoAplicacion) getActivity().getApplication();//Objeto Sesion
        getActivity().setTitle(R.string.title_fragment_editar_venta);
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());
        servicioVenta = new ServicioVenta(getContext());

        //Valida la cantidad a la que cambia el número de cilindros
        numeroCilindrosAux = venta.getCantidad();




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

               /* cupoHogar.setCmhDisponible(24);
                try {
                    serviciosCupoHogar.actualizar(cupoHogar);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                long rangoEdicion= Convertidor.diferenciaEnSegundosFechas(venta.getFecha_venta(),Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
                int cantidadNueva;
                int diferencia = 0;
                Log.i("Log_glp------>", "rangoEdicion: " + rangoEdicion);
                if (rangoEdicion <=180) {
                    Log.i("Log_glp------>", " tiempo Edicion: " + rangoEdicion);
                    if (etCilindros.getText().toString().compareTo("") != 0) {
                        cantidadNueva = Integer.valueOf(etCilindros.getText().toString());
                        if (cantidadNueva > 0) {
                            diferencia = venta.getCantidad() - cantidadNueva;
                            if (diferencia < 0) {
                                //AUMENTA CILINDROS EN LA VENTA
                                diferencia = Math.abs(diferencia);
                                Log.i("Log_glp------>", "LA DIFERENCIA EN VALOR ABSOLUTO ES: " + diferencia);
                                Log.i("Log_glp------>", "EL CUPO DISPONIBLE DEL HOGAR ES: " + cupoHogar.getCmhDisponible());
                                try {
                                    //La nueva cantidad es superior a la de la venta original
                                    //Es necesario comprobar si dispone de cupo
                                    if (cupoHogar.getCmhDisponible() >= diferencia) {
                                        //permite la edidcion
                                        venta.setCantidad(Integer.valueOf(etCilindros.getText().toString()));
                                        venta.setFecha_modificacion(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
                                        //Disminuir al hogar
                                        cupoHogar.setCmhDisponible(cupoHogar.getCmhDisponible() - diferencia);
                                        actualizarVentaCupo();

                                    } else {
                                        UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_EXCEDE_PERMITIDO, TituloError.TITULO_ERROR, getContext());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    //mensaje mesa ayuda
                                    UtilMensajes.mostrarMsjError(MensajeError.CUPO_NO_REGISTRADO, TituloError.TITULO_ERROR, getContext());
                                }
                            } else if (diferencia > 0) {

                                //DISMINUYE CILINDROS en venta, por tanto aumento cupo al hogar
                                venta.setCantidad(cantidadNueva);
                                cupoHogar.setCmhDisponible(cupoHogar.getCmhDisponible() + diferencia);
                                actualizarVentaCupo();
                            } else if (diferencia == 0) {
                                UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_SIN_EDICION, TituloInfo.TITULO_INFO, getContext());
                            }
                        } else {
                            UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_NULL, TituloError.TITULO_ERROR, getContext());
                        }
                    } else {
                        UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_NULL, TituloError.TITULO_ERROR, getContext());
                    }
                }else{
                    Log.i("Log_glp------>", " excede  tiempo Edicion: " + rangoEdicion);
                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_TIEMPO_EDICION_PERMITIDO, TituloError.TITULO_ERROR, getContext());
                }
            }
        });

        return view;
    }


    //
    public void actualizarVentaCupo() {
        //Actualización en base de datos y en la interfaz del usuario
        try {
            servicioVenta.actualizar(venta);
            try {
                serviciosCupoHogar.actualizar(cupoHogar);
                UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_ACTUALIZADA_OK, TituloInfo.TITULO_INFO, getContext());
                tvCupoDisponible.setText(cupoHogar.getCmhDisponible().toString());
            } catch (Exception e) {
                e.printStackTrace();
                venta.setCantidad(numeroCilindrosAux);
                servicioVenta.actualizar(venta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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