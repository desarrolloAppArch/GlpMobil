package ec.gob.arch.glpmobil.activities;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
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
    private CheckBox cbIdentificador;

    private VwPersonaAutorizada persona;
    private ObjetoAplicacion objetosSesion;
    private String identificacion;
    private TextView txtEtiquetaIdent;

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
        cbIdentificador = vistaVentaFragment.findViewById(R.id.cbIdentificador);
        txtEtiquetaIdent = vistaVentaFragment.findViewById(R.id.txtEtiquetaIdent);
        txtEtiquetaIdent.setVisibility(View.GONE);

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
                identificacion=null;
                if(seleccionoOpcionEscanear!=null){
                    if(seleccionoOpcionEscanear){
                        Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnBuscarFragment.setOnClickListener()");
                        //Se cambia de tipo a tvCodigoLeidoFragment por una variale de tipo password, y se valida que tenga 10 dígitos
                        identificacion=tvCodigoLeidoFragment.getText().toString();
                    }else {
                        txtEtiquetaIdent.setVisibility(View.VISIBLE);
                        identificacion=etCodigoLeidoFragment.getText().toString();
                    }
                    if(identificacion.compareTo("")!=0){
                        if (etFechaExpedicion.getText().toString().compareTo("")!=0){
                            iniciarVenta(identificacion);
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
        etFechaExpedicion.setText("");
        etCodigoLeidoFragment.setVisibility(View.VISIBLE);
        tvCodigoLeidoFragment.setVisibility(View.GONE);
        cbIdentificador.setVisibility(View.GONE);
        txtEtiquetaIdent.setVisibility(View.VISIBLE);
    }

    public void inicializarEscaneo(){
        tvCodigoLeidoFragment.setText("");
        etCodigoLeidoFragment.setText("");
        etFechaExpedicion.setText("");
        cbIdentificador.setChecked(false);
        etCodigoLeidoFragment.setVisibility(View.GONE);
        tvCodigoLeidoFragment.setVisibility(View.VISIBLE);
        cbIdentificador.setVisibility(View.VISIBLE);
        txtEtiquetaIdent.setVisibility(View.GONE);
    }

    /**
     * Método que valida si es dato escaneado de la cédula, devuelve el núemero de identificación con 10 dígitos
     */

    public void validarCodigoEscaneo(){
        identificacion=tvCodigoLeidoFragment.getText().toString();
        if (identificacion.length()==10){
            Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnBuscarFragment.setOnClickListener()--largo identificacion entra al if "+identificacion.length()+"dato "+identificacion);
            cbIdentificador.setText("Escaneo Correcto");
            cbIdentificador.setTextColor(Color.parseColor("#0000CC"));
            cbIdentificador.setChecked(true);
        }else{
            Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnBuscarFragment.setOnClickListener()--largo identificacion entra al else"+identificacion.length());
            cbIdentificador.setText("Escaneo Incorrecto");
            cbIdentificador.setTextColor(Color.RED);
            cbIdentificador.setChecked(false);
            UtilMensajes.mostrarMsjError(MensajeError.VENTA_ESCANEO_INVALIDO, TituloError.TITULO_ERROR, getContext());
        }
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
                    validarCodigoEscaneo();
                }
            }
        }
    }

    /**
     * Método que busca el cupo de una persona autorizada a retirar
     * @param identificacion
     */
    public void iniciarVenta(String identificacion){
        try {
            persona = serviciosPersona.buscarPorIdentificacion(identificacion);
            if(persona!=null){
                if(!seleccionoOpcionEscanear){
                    Log.v("log_glp ---------->", "INFO VentaFragment --> iniciarVenta() --> DIGITACION: "+persona.getPermitirDigitacionIden());
                    if (persona.getPermitirDigitacionIden().equals(ConstantesGenerales.CODIGO_PERMITIR_DIGITACION)){
                        validarCupo();
                    }else{
                        UtilMensajes.mostrarMsjInfo(MensajeInfo.PERSONA_NO_AUTORIZADA_DIGITAR, TituloInfo.TITULO_INFO, getContext());
                    }
                }else{
                    validarCupo();
                }
            }else{

                List<VwPersonaAutorizada> listaPersonas = serviciosPersona.buscarTodas();
                Log.v("log_glp ---------->", "INFO VentaFragment --> iniciarVenta() --> listaPersonas: "+listaPersonas);
                if(listaPersonas==null){
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.BASE_CUPOS_VACIA, TituloInfo.TITULO_INFO, getContext());
                }else{
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_IDENTIFICACION_NO_ENCONTRADA, TituloInfo.TITULO_INFO, getContext());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            UtilMensajes.mostrarMsjError(MensajeError.PROBLEMAS_CONSULTAR_BASE+e.getMessage(), TituloError.TITULO_ERROR, getContext());
        }

    }

    public void validarCupo(){
        if(fechaExpedicionAceptada(persona)){
            VwCupoHogar cupoHogar = null;
            try {
                cupoHogar = serviciosCupoHogar.buscarPorHogar(persona.getHogCodigo(),objetosSesion.getUsuario().getId());
                if(cupoHogar!=null){
                    if(cupoHogar.getCmhDisponible()>0){
                        Log.v("log_glp ---------->", "INFO VentaFragment --> validarCupo() --> CUPO DISPONIBLE: "+cupoHogar.getCmhDisponible());
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
                        UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_HOGAR_SIN_CUPO_DISPONIBLE+cupoHogar.getCmhDisponible(), TituloInfo.TITULO_INFO, getContext());
                    }
                }else{
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.VENTA_HOGAR_NO_EXISTE+objetosSesion.getUsuario().getId(), TituloInfo.TITULO_INFO, getContext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                UtilMensajes.mostrarMsjError(MensajeError.PROBLEMAS_CONSULTAR_BASE+e.getMessage(), TituloError.TITULO_ERROR, getContext());
            }
        }else{
            UtilMensajes.mostrarMsjError(MensajeError.VENTA_FECHA_NO_COINCIDE, TituloError.TITULO_ERROR, getContext());
        }
    }

    public boolean fechaExpedicionAceptada(VwPersonaAutorizada persona){
        boolean aceptada=false;
        Log.v("log_glp ---------->", "INFO VentaFragment --> fechaExpedicionAceptada() --> año: "+persona.getFechaEmisionDocumentoAnio());
        Log.v("log_glp ---------->", "INFO VentaFragment --> fechaExpedicionAceptada() --> mes: "+persona.getFechaEmisionDocumentoMes());
        Log.v("log_glp ---------->", "INFO VentaFragment --> fechaExpedicionAceptada() --> día: "+persona.getFechaEmisionDocumentoDia());
            String fechaConcatenada = persona.getFechaEmisionDocumentoAnio().toString()+persona.getFechaEmisionDocumentoMes().toString()+persona.getFechaEmisionDocumentoDia().toString();
            if(etFechaExpedicion.getText().toString().equals(fechaConcatenada)){
                aceptada=true;
            }

        return aceptada;
    }

}
