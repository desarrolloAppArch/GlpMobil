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

    private Venta ventaSesion = null;

    private ObjetoAplicacion objetoSesion;

    private ServiciosCupoHogar serviciosCupoHogar;
    private ServicioVenta servicioVenta;
    private VwCupoHogar cupoHogar;

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
        tvCupoDisponible = (TextView) view.findViewById(R.id.cupoDisponible);

        //LLeno la vista con la información de la venta seleccionada en el fragment anterior
        tvIdentificacion.setText(venta.getUsuario_compra());
        tvNombre.setText(venta.getNombre_compra());
        tvFecha.setText(venta.getFecha_venta());
        tvUsuarioVenta.setText(venta.getUsuario_venta());
        etCilindros.setText(venta.getCantidad().toString());
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());


        try {
            tvCupoDisponible.setText(String.valueOf((serviciosCupoHogar.buscarPorCupo(venta.getCodigo_cupo_mes()))));//agrego el cupo disponible a la vista
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("log_glp_cupoDisponible", "EL CUPO DISPONIBLE ES: " + tvCupoDisponible.toString());

        objetoSesion= (ObjetoAplicacion) getActivity().getApplication();//Objeto Sesion
        ventaSesion = objetoSesion.getVenta();
        getActivity().setTitle(R.string.title_fragment_editar_venta);
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());
        servicioVenta = new ServicioVenta(getContext());


        //Valida la cantidad a la que cambia el número de cilindros
/*        int numeroCilindrosAux = venta.getCantidad();
        numeroCilindrosAux = numeroCilindrosAux+venta.getCupoDisponible();*/



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


                int cantidadNueva;
                int diferencia = 0;
                cantidadNueva = Integer.valueOf(etCilindros.getText().toString());
                diferencia = venta.getCantidad() - cantidadNueva;
                if (etCilindros.getText().toString().compareTo("") != 0) {

                    if (diferencia < 0) {
                        //AUMENTA CILINDROS
                        // venta.setCupoDisponible(cantidadNueva);
                        //cupoHogar.setCmhDisponible(venta.getCupoDisponible()+cantidadNueva);


                        try {
                            //La nueva cantidad es superior a la de la venta original
                            //Es necesario comprobar si dispone de cupo
                            if (cantidadNueva > venta.getCupoDisponible()) {
                                Log.i("log_glp_cupo ---->", "PUEDE HACER LA VENTA PORQUE TODAVIA DISPONE DE CUPO");
                                Log.i("log_glp_cupo ---->", "LA CANTIDAD NUEVA ES:" + etCilindros.getText().toString());
                                Log.i("log_glp_cupo ---->", "EL CUPO DISPONIBLE ES:" + venta.getCupoDisponible());
                            } else {
                                Log.i("log_glp_cupo ---->", "NO PUEDE HACER LA VENTA PORQUE LA CANTIDAD ES MAYOR A LA DEL CUPO DISPONIBLE");
                            }

                            VwCupoHogar cupoHogar = serviciosCupoHogar.buscarPorCupo(venta.getCodigo_cupo_mes());
                            if (cupoHogar.getCmhDisponible() <= diferencia) {
                                //permite la edidcion
                                venta.setCantidad(Integer.valueOf(etCilindros.getText().toString()));
                                venta.setFecha_modificacion(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));

                                //Disminuir al hogar
                                //  cupoHogar.setCmhDisponible(venta.getCupoDisponible() - cantidadNueva);


                            } else {
                                //mensaje
                                //hacer la comparación para que no exceda el cupo disponible
                                if (cantidadNueva > venta.getCupoDisponible()) {
                                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_EXCEDE_PERMITIDO, TituloError.TITULO_ERROR, getContext());
                                } else {
                                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_EXCEDE_PERMITIDO, TituloError.TITULO_ERROR, getContext());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //mensaje mesa ayuda
                            UtilMensajes.mostrarMsjError(MensajeError.CUPO_NO_REGISTRADO, TituloError.TITULO_ERROR, getContext());
                        }
                    } else {
                        //DISMINUYE CILINDROS, por tanto aumento al hogar
                        //   cupoHogar.setCmhDisponible(venta.getCupoDisponible()-cantidadNueva);

                    }

                    //Setear datos venta para enviar a actualizar


                    //Actualizar en la base de datos
                    try {
                        servicioVenta.actualizar(venta);
                        Log.i("log_glp ---------->", "INFO: EditarVentaFragment --> actualizar() : " + servicioVenta);

                        //actualizar hogar


                    } catch (Exception e) {
                        Log.e("log_glp ---------->", "ERROR: EditarVentaFragment --> actualizar() : " + venta.getId_sqlite());
                        e.printStackTrace();
                    }

                    // Imprimo en consola la fecha de la venta (falta probar si me imprime la fecha en la que se ingresa el registro de la venta)
                    Log.v("Log_glp_reloj------->", "La fecha de la venta es igual:" + venta.getFecha_venta());

                    //Regresa al fragment EditarVentaFragment
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment, new ConsultarVentaFragment()).commit();
                } else {
                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_NULL, TituloError.TITULO_ERROR, getContext());
                }

                //comprueba la hora en la que se quiere editar la venta
                //variables para la validación de la fecha
                String fechaVenta = venta.getFecha_venta().substring(0, 10);//Asigno a la variable fechaVenta la fecha de la venta para poder extraer la fecha
                String fechaLimite = fechaVenta;
                Log.i("Log_glp------>", "LA FECHA DE LA VENTA ES: " + fechaVenta);

                //variables para la validación de los 3 minutos
                /* Eliminar para las variables
                int tiempoEspera = 3;//tiempo de espera en minutos para que pueda hacer el cambio de la venta
                String horaActual = Convertidor.horafechaSistemaTimestamp().toString().substring(10,19);
                String horaVenta;
                String horaLimite;
                horaVenta=venta.getFecha_venta().substring(10,19);
                Log.i("Log_glp------>","LA HORA DE LA VENTA ES IGUAL: "+horaVenta);

                Calendar cal = Calendar.getInstance();
                Date tempDate = cal.getTime();
                Log.i("Log_glp------>","LA FECHA DEL TEMPDATE ES IGUAL: "+tempDate);
                cal.setTime(new Date());
                cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+ tiempoEspera);
                tempDate = cal.getTime();
                horaLimite = tempDate.toString().substring(10,19);
                eliminar para las variables*/


                //Valida la fecha
                /* eliminar para las sentencias
                if (fechaLimite == fechaVenta){
                        Log.i("Log_glp------>","PUEDE ACTUALIZAR PORQUE LA FECHA DE LA VENTA ESTA DENTRO DEL RANGO");
                        Log.i("Log_glp------>","LA FECHA DE LA VENTA ES IGUAL: "+fechaVenta);
                        //valida la hora
                    try {
                        if(Convertidor.comprobar(horaActual,horaVenta,horaLimite)== true){
                            UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_ACTUALIZADA_OK, TituloInfo.TITULO_INFO, getContext());
                            Log.i("Log_glp------>","LA HORA ESTA DENTRO DEL LIMITE Y PUEDE HACER LA EDICION DE LA VENTA");
                            Log.i("Log_glp------>","LA HORA ACTUAL ES:"+horaActual);
                            Log.i("Log_glp------>","LA HORA LIMITE PARA REALIZAR LA EDICION ES: "+horaLimite);
                        }
                        else{
                            UtilMensajes.mostrarMsjError(MensajeError.VENTA_NO_ACTUALIZADA_HORA, TituloError.TITULO_ERROR,getContext());
                            Log.i("Log_glp------>","LA HORA ESTA FUERA DEL LIMITE DE TIEMPO NO PUEDE HACER LA EDICION DE LA VENTA");
                            Log.i("Log_glp------>","LA HORA ACTUAL ES:"+horaActual);
                            Log.i("Log_glp------>","LA HORA LIMITE PARA REALIZAR LA EDICION ES: "+horaLimite);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else{
                    Log.i("Log_glp------>","NO PUEDE ACTUALIZAR PORQUE LA FECHA Actual NO ES IGUAL A LA FECHA DE LA VENTA");
                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_NO_ACTUALIZADA_FECHA, TituloError.TITULO_ERROR,getContext());
                }
                eliminar para las sentencias */

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