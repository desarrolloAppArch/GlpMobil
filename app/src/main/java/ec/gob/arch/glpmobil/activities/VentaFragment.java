package ec.gob.arch.glpmobil.activities;


import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.servicios.ServiciosPersona;

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
    /**
     * SERVICIOS
     */
    ServiciosPersona serviciosPersona;


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
        btnEscanearFragment = vistaVentaFragment.findViewById(R.id.btnEscanearFragment);
        btnDigitarFragment = vistaVentaFragment.findViewById(R.id.btnDigitarFragment);
        btnBuscarFragment = vistaVentaFragment.findViewById(R.id.btnBuscarFragment);
        tvCodigoLeidoFragment = vistaVentaFragment.findViewById(R.id.tvCodigoLeidoFragment);
        etCodigoLeidoFragment = vistaVentaFragment.findViewById(R.id.etCodigoLeidoFragment);
        etCodigoLeidoFragment.setVisibility(View.GONE);
        serviciosPersona = new ServiciosPersona(getContext());


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
                String identificacion;
                if(seleccionoOpcionEscanear){
                    Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnBuscarFragment.setOnClickListener()");
                    identificacion=tvCodigoLeidoFragment.getText().toString();
                }else {
                    identificacion=etCodigoLeidoFragment.getText().toString();
                }
                //serviciosPersona.buscarPorIdentificacion(identificacion); PROBAR CUANDO VANE ACABE DE REGISTRAR AL MENOS UNA PERSONA


                Venta venta = new Venta();
                venta.setCupo(4);

                //Creo un objeto Bundle para enviarlo al siguiente fragment
                Bundle bundle = new Bundle();
                bundle.putSerializable(CtVenta.CLAVE_CUPO_VENTA,venta);
                VentaPaso2Fragment ventaPaso2Fragment = new VentaPaso2Fragment();
                ventaPaso2Fragment.setArguments(bundle);

                //Creo el siguiente fragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment, ventaPaso2Fragment).commit();

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
                    Log.v("log_glp ---------->", "INFO InicioActivity --> onActivityResult() --> codigo: "+codigo);

                }
            }
        }
    }



}
