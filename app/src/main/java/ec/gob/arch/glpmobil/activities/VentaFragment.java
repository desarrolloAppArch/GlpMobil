package ec.gob.arch.glpmobil.activities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.VwPersonaAutorizada;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwPersonaAutorizada;
import ec.gob.arch.glpmobil.servicios.ServiciosCupoHogar;
import ec.gob.arch.glpmobil.servicios.ServiciosPersona;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentaFragment extends Fragment{

    /**
     * VARIABLES
     */
    private static final int CODIGO_INTENT = 2;
    private Button btnEscanearFragment;
    private Button btnDigitarFragment;
    private TextView tvCodigoLeidoFragment;
    private EditText etCodigoLeidoFragment;
    private Button btnBuscarFragment;
    private Boolean seleccionoOpcionEscanear;
    //Variable para guardar un número cualquiera, para identificar el permiso de la cámara
    private static final int CODIGO_PERMISOS_CAMARA = 1001;
    private EditText etFechaExpedicion;

    private ObjetoAplicacion objetosSesion;

    /**
     * SERVICIOS
     */
    ServiciosPersona serviciosPersona;
    ServiciosCupoHogar serviciosCupoHogar;


    /**
     * CONSTRUCTOR DE LA CLASE
     */
    public VentaFragment() {
        Log.v("log_glp ----------> ", "INFO VentaFragment --> VentaFragment() CONSTRUCTOR");
    }




    /**
     * CONSTRUCTOR DE LA VISTA
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Relaciono componentes gráficos del .xml con las variables de la clase,
         * para que puedan ser manipulados como objetos
         */
        Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView()");
        View vistaVentaFragment = inflater.inflate(R.layout.fragment_venta, container, false);
        getActivity().setTitle(R.string.title_fragment_registrar_venta);

        btnEscanearFragment = vistaVentaFragment.findViewById(R.id.btnEscanearFragment);
        btnDigitarFragment = vistaVentaFragment.findViewById(R.id.btnDigitarFragment);
        btnBuscarFragment = vistaVentaFragment.findViewById(R.id.btnBuscarFragment);
        tvCodigoLeidoFragment = vistaVentaFragment.findViewById(R.id.tvCodigoLeidoFragment);
        etCodigoLeidoFragment = vistaVentaFragment.findViewById(R.id.etCodigoLeidoFragment);
        etCodigoLeidoFragment.setVisibility(View.GONE);
        etFechaExpedicion = vistaVentaFragment.findViewById(R.id.etFechaExpedicion);
        serviciosPersona = new ServiciosPersona(getContext());
        serviciosCupoHogar = new ServiciosCupoHogar(getContext());
        objetosSesion = (ObjetoAplicacion) getActivity().getApplication();

        validarPermisoCamara();


        /**
         * Pongo a la escucha al botón btnEscanearFragment
         */
        btnEscanearFragment.setOnClickListener(new View.OnClickListener() {
            /**
             * Método al que llama al dar clic en el botón para escanear el código de barras o QR
             */
            @Override
            public void onClick(View v) {
                Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnEscanearFragment.setOnClickListener()");
                inicializarEscaneo();
                escanear();
                seleccionoOpcionEscanear =true;
            }
        });



        /**
         * Pongo a la escucha al botón btnEscanearFragment
         */
        btnDigitarFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnDigitarFragment.setOnClickListener()");
               inicializarDigitacion();
               seleccionoOpcionEscanear =false;
            }
        });



        /**
         *Pongo a la escucha al botón btnBuscarFragment
         */
        btnBuscarFragment.setOnClickListener(new View.OnClickListener() {
            /**
             * Método que permite buscar si la identificación tiene cupo disponible en un hogar
             * @param v
             */
            @Override
            public void onClick(View v) {
                Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnBuscarFragment.setOnClickListener()");
                //Valido identificación de la persona que retira el GLP
                String identificacion=null;
                if(seleccionoOpcionEscanear!=null){
                    if(seleccionoOpcionEscanear){
                        Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnBuscarFragment.setOnClickListener()");
                        identificacion=tvCodigoLeidoFragment.getText().toString();
                    }else {
                        identificacion=etCodigoLeidoFragment.getText().toString();
                    }
                    if(identificacion.compareTo("")!=0){
                        if (etFechaExpedicion.getText().toString().compareTo("")!=0){
                            buscarCupo(identificacion);
                        }else{
                            UtilMensajes.mostrarMsjError(MensajeError.VENTA_FECHA_NULL, TituloError.TITULO_ERROR, getContext());
                        }

                    }else{
                        UtilMensajes.mostrarMsjError(MensajeError.VENTA_IDENTIFICACION_NULL, TituloError.TITULO_ERROR, getContext());
                    }
                }else {
                    UtilMensajes.mostrarMsjError(MensajeError.VENTA_IDENTIFICACION_NULL, TituloError.TITULO_ERROR, getContext());
                }



            }
        });
        return vistaVentaFragment;
    }



    public void inicializarDigitacion(){
        tvCodigoLeidoFragment.setText("");
        etCodigoLeidoFragment.setText("");
        etCodigoLeidoFragment.setVisibility(View.VISIBLE);
        tvCodigoLeidoFragment.setVisibility(View.GONE);
    }

    public void inicializarEscaneo(){
        tvCodigoLeidoFragment.setText("");
        etCodigoLeidoFragment.setText("");
        etCodigoLeidoFragment.setVisibility(View.GONE);
        tvCodigoLeidoFragment.setVisibility(View.VISIBLE);
    }

    public void validarPermisoCamara(){
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            //Entra si la app no tiene permisos a la cámara
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)){
                //En caso de haber rechazado la solicitud de permisos a la cámara y haber seleccionado "No volver a preguntar"
                //Aquí debemos indicar que es necesario la activación de la cámara para escanear
                Log.v("log_glp ----------> ", "INFO VentaFragment --> validarPermisoCamara() --> if)");
                UtilMensajes.mostrarMsjError(MensajeError.VENTA_ACTIVAR_CAMARA_PARA_ESTA_APP, TituloError.TITULO_ERROR, getContext());
            }else{
                //Solicita al usuario permisos para la cámara para esta app
                Log.v("log_glp ----------> ", "INFO VentaFragment --> validarPermisoCamara() --> else");
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},CODIGO_PERMISOS_CAMARA);

            }
        }
    }


    /**
     * Método que nos lleva al Activity en donde se abrirá el escáner de la cámara,
     * e indica que al obtener un resultado volverá a este activity y llamará a onActivityResult()
     */
    public void escanear(){
        Intent irEscanearActivity =  new Intent(getActivity(), EscanearActivity.class);
        startActivityForResult(irEscanearActivity, CODIGO_INTENT);

    }



    /**
     * Cuando el usuario termina de utilizar la actividad subsiguiente y muestra un resultado,
     * el sistema invoca al método onActivityResult() de tu actividad (en este cado de este fragment)
     * Método utilizado para iteractuar con otras apps como la cámara
     * @param requestCode El código de solicitud que transferiste a startActivityForResult()
     * @param resultCode Un código de resultado especificado por la segunda actividad
     * @param data Un Intent con la información del resultado
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("log_glp ---------->", "INFO VentaFragment --> onActivityResult() --> requestCode: "+requestCode);
        Log.v("log_glp ---------->", "INFO VentaFragment --> onActivityResult() --> resultCode: "+resultCode);
        if(requestCode==CODIGO_INTENT) {
            if(resultCode== Activity.RESULT_OK){
                if(data!=null){
                    String codigo = data.getStringExtra("codigo");
                    tvCodigoLeidoFragment.setText(codigo);
                    Log.v("log_glp ---------->", "INFO VentaFragment --> onActivityResult() --> codigo: "+codigo);

                }
            }
        }
    }

    /**
     * Método que busca el cupo de una persona autorizada a retirar
     * @param identificacion
     */
    public void buscarCupo(String identificacion){
        VwPersonaAutorizada persona = serviciosPersona.buscarPorIdentificacion(identificacion);
        if(persona!=null){
            if(!seleccionoOpcionEscanear){
                Log.v("log_glp ---------->", "INFO VentaFragment --> buscarCupo() --> DIGITACION: "+persona.getPermitirDigitacionIden());
                if (persona.getPermitirDigitacionIden().equals(1)){
                    if(fechaExpedicionAceptada(persona)){
                        VwCupoHogar cupoHogar = serviciosCupoHogar.buscarPorHogar(persona.getHogCodigo());
                        if(cupoHogar!=null && cupoHogar.getCmhDisponible()>0){
                            Log.v("log_glp ---------->", "INFO VentaFragment --> buscarCupo() --> CUPO DISPONIBLE: "+cupoHogar.getCmhDisponible());
                            //Envio la venta seteado algunos datos, para completarlos en el siguiente fragment
                            Venta venta = new Venta();
                            venta.setUsuario_compra(persona.getNumeroDocumento());
                            venta.setUsuario_venta(objetosSesion.getUsuario().getId());
                            //venta.setUsuario_venta("07GLP-D0045");
                            venta.setCupoDisponible(cupoHogar.getCmhDisponible());
                            venta.setCodigo_cupo_mes(cupoHogar.getCmhCodigo());
                            venta.setNombre_compra(persona.getApellidoNombre());

                            //Creo un objeto Bundle para enviarlo al siguiente fragment
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(CtVenta.CLAVE_CUPO_VENTA,venta);
                            bundle.putSerializable(CtCupoHogar.CLAVE_CUPO_HOGAR, cupoHogar);

                            VentaPaso2Fragment ventaPaso2Fragment = new VentaPaso2Fragment();
                            ventaPaso2Fragment.setArguments(bundle);

                            //Creo el siguiente fragment
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.beginTransaction().replace(R.id.fragment, ventaPaso2Fragment).commit();
                        }else{
                            UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_HOGAR_SIN_CUPO_DISPONIBLE, TituloInfo.TITULO_INFO, getContext());
                        }
                    }else{
                        UtilMensajes.mostrarMsjError(MensajeError.VENTA_FECHA_NO_COINCIDE, TituloError.TITULO_ERROR, getContext());
                    }
                }else{
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.PERSONA_NO_AUTORIZADA_DIGITAR, TituloInfo.TITULO_INFO, getContext());
                }
            }

        }else{
            UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_IDENTIFICACION_NO_ENCONTRADA, TituloInfo.TITULO_INFO, getContext());
        }
    }

    public boolean fechaExpedicionAceptada(VwPersonaAutorizada persona){
        boolean aceptada=false;
            String fechaConcatenada = persona.getFechaEmisionDocumentoAnio().toString()+persona.getFechaEmisionDocumentoMes().toString()+persona.getFechaEmisionDocumentoDia().toString();
            if(etFechaExpedicion.getText().toString().equals(fechaConcatenada)){
                aceptada=true;
            }

        return aceptada;
    }

}
