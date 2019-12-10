package ec.gob.arch.glpmobil.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.servicios.ServicioVenta;
import ec.gob.arch.glpmobil.servicios.ServiciosCupoHogar;
import ec.gob.arch.glpmobil.utils.Convertidor;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentaPaso2Fragment extends Fragment {

    private TextView tvIdentificacion;
    private TextView tvNombre;
    private TextView tvCupo;
    private TextView tvFecha;
    private TextView tvUsuarioVenta;
    private EditText etCilindrosVenta;
    private TextView tvCilindrosVenta;
    private Button btnRegistrarVenta;
    private Button btnCancelarVenta;
    private Button btnNuevaVenta;
    private TableRow trBotonesVenta;
    private TableRow trBotonesNuevaVenta;

    private Venta venta;
    private VwCupoHogar cupoHogar;

    private ServicioVenta servicioVenta;
    private ServiciosCupoHogar serviciosCupoHogar;

    public VentaPaso2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("log_glp ----------> ", "INFO VentaPaso2Fragment --> onCreate()");
        Bundle parametrosRecibidos = getArguments();
        if(parametrosRecibidos!=null){
            venta = (Venta) parametrosRecibidos.getSerializable(CtVenta.CLAVE_CUPO_VENTA);
            cupoHogar = (VwCupoHogar) parametrosRecibidos.getSerializable(CtCupoHogar.CLAVE_CUPO_HOGAR);
            Log.i("log_glp_cupo ---->","INFO VentaPaso2Fragment --> onCreate() --> parametrosRecibidos venta: "+venta.getCupoDisponible() );
            Log.i("log_glp_cupo ---->","INFO VentaPaso2Fragment --> onCreate() --> parametrosRecibidos cupo: "+cupoHogar.getCmhDisponible());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("log_glp ----------> ", "INFO VentaPaso2Fragment --> onCreateView()");
        View view = inflater.inflate(R.layout.fragment_venta_paso2, container, false);
        tvIdentificacion = (TextView) view.findViewById(R.id.tvIdentificacionVentaRegistro);
        tvNombre = (TextView) view.findViewById(R.id.tvNombreVentaRegistro);
        tvCupo = (TextView) view.findViewById(R.id.tvCupoVentaRegistro);
        tvFecha = (TextView) view.findViewById(R.id.tvFechaVentaRegistro);
        tvUsuarioVenta = (TextView) view.findViewById(R.id.tvUsuarioVentaRegistro);
        etCilindrosVenta = (EditText) view.findViewById(R.id.etCilindrosVentaRegistro);
        tvCilindrosVenta = (TextView) view.findViewById(R.id.tvCilindrosVentaRegistro);
        btnRegistrarVenta = (Button) view.findViewById(R.id.btnRegistrarVenta);
        btnCancelarVenta = (Button) view.findViewById(R.id.btnCancelarVenta);
        btnNuevaVenta = (Button) view.findViewById(R.id.btnNuevaVenta);
        trBotonesVenta = (TableRow) view.findViewById(R.id.trBotonesVenta);
        trBotonesNuevaVenta = (TableRow) view.findViewById(R.id.trBotonesNuevaVenta);
        activarModoVenta();
        setearParametrosRecibidos();
        servicioVenta =  new ServicioVenta(getContext());
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());


        /**
         * Mótodo que registra una venta en la base de datos y actualiza el cupo disponible
         */
        btnRegistrarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer numeroCilindrosIngresados=0;
                int idUltimaVenta=0;
                if(etCilindrosVenta.getText().toString().compareTo("")!=0){
                    numeroCilindrosIngresados = Integer.valueOf(etCilindrosVenta.getText().toString());
                    if(numeroCilindrosIngresados>0){
                        if(numeroCilindrosIngresados<=venta.getCupoDisponible()){
                            try {
                                //Inserto venta
                                venta.setFecha_venta(Convertidor.dateAString(Convertidor.horafechaSistemaDate()));
                                tvFecha.setText(venta.getFecha_venta());
                                venta.setCantidad(numeroCilindrosIngresados);
                                servicioVenta.insertarVenta(venta);
                                idUltimaVenta = servicioVenta.buscarIdUltimaVenta();
                            } catch (Exception e) {
                                e.printStackTrace();
                                UtilMensajes.mostrarMsjError(MensajeError.VENTA_NO_REGISTRADA+e.getMessage(), TituloError.TITULO_ERROR, getContext());
                            }
                            try {
                                //Actualizo el cupo del hogar
                                cupoHogar.setCmhDisponible(venta.getCupoDisponible()-numeroCilindrosIngresados);
                                //cupoHogar.setCmhOcupado(cupoHogar.getCmhOcupado()+numeroCilindrosIngresados); Descomentar para que entre al catch
                                serviciosCupoHogar.actualizar(cupoHogar);
                                UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_REGISTRADA_EXITOSAMENTE, TituloInfo.TITULO_INFO, getContext());
                                tvCupo.setText(String.valueOf(cupoHogar.getCmhDisponible()));
                                tvCilindrosVenta.setText(etCilindrosVenta.getText());
                                activarModoVisualizacion();
                            } catch (Exception e) {
                                e.printStackTrace();
                                UtilMensajes.mostrarMsjError(MensajeError.CUPO_NO_REGISTRADO+e.getMessage(), TituloError.TITULO_ERROR, getContext());
                                try {
                                    if(idUltimaVenta!=0){
                                        Venta ultimaVenta = servicioVenta.buscarVentaPorIdSqlite(idUltimaVenta);
                                        if(ultimaVenta!=null){
                                            servicioVenta.eliminarVenta(ultimaVenta);
                                        }else{
                                            Log.v("log_glp ----------> ", "INFO VentaPaso2Fragment --> btnRegistrarVenta()--> NO SE ENCONTRO VENTA CON ID: "+idUltimaVenta);
                                        }
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_NO_ROLLBACK+e.getMessage(), TituloError.TITULO_ERROR, getContext());
                                }
                            }
                        }else{
                            UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_EXCEDE_PERMITIDO, TituloError.TITULO_ERROR, getContext());
                        }
                    }else{
                        UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_MAYOR_A_CERO, TituloError.TITULO_ERROR, getContext());
                    }
                }else{
                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_NUMERO_CILINDROS_NULL, TituloError.TITULO_ERROR, getContext());
                }
            }
        });


        /**
         * Método que regresa a cancela la venta y regresa a la pantalla anterior
         */
        btnCancelarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irFragmentAnterior();
            }
        });


        btnNuevaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                irFragmentAnterior();
            }
        });

        return view;
    }

    public void irFragmentAnterior(){
        VentaFragment ventaFragment = new VentaFragment();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment, ventaFragment).commit();
    }

    public void activarModoVenta(){
        trBotonesVenta.setVisibility(View.VISIBLE);
        etCilindrosVenta.setVisibility(View.VISIBLE);
        trBotonesNuevaVenta.setVisibility(View.GONE);
        tvCilindrosVenta.setVisibility(View.GONE);
    }

    public void activarModoVisualizacion(){
        trBotonesVenta.setVisibility(View.GONE);
        etCilindrosVenta.setVisibility(View.GONE);
        trBotonesNuevaVenta.setVisibility(View.VISIBLE);
        tvCilindrosVenta.setVisibility(View.VISIBLE);
    }

    public void setearParametrosRecibidos(){
        tvIdentificacion.setText(venta.getUsuario_compra());
        tvNombre.setText(venta.getNombre_compra());
        tvCupo.setText(venta.getCupoDisponible().toString());
        tvUsuarioVenta.setText(venta.getUsuario_venta());
    }


}
