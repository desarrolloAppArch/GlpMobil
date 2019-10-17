package ec.gob.arch.glpmobil.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentaFragment extends Fragment {

    private static final int CODIGO_INTENT = 2;
    private Button btnEscanearFragment;
    private TextView tvCodigoLeidoFragment;

    public VentaFragment() {
        Log.v("log_glp ----------> ", "INFO VentaFragment --> VentaFragment() CONSTRUCTOR");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Crear la vista del fragment
         */
        Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView()");
        View vistaVentaFragment = inflater.inflate(R.layout.fragment_venta, container, false);
        tvCodigoLeidoFragment = vistaVentaFragment.findViewById(R.id.tvCodigoLeidoFragment);
        btnEscanearFragment = vistaVentaFragment.findViewById(R.id.btnEscanearFragment);


        /**
         * Pongo a la escucha al botón btnEscanearFragment
         */
        btnEscanearFragment.setOnClickListener(new View.OnClickListener() {
            /**
             * Método al que llama al dar clic en el botón para escanear el código de barras o QR
             */
            @Override
            public void onClick(View v) {
                escanear();
                Log.v("log_glp ----------> ", "INFO VentaFragment --> onCreateView() --> btnEscanearFragment.setOnClickListener()");
            }
        });

        return vistaVentaFragment;
    }

    public void escanear(){
        Intent irEscanearActivity =  new Intent(getActivity(), EscanearActivity.class);
        startActivityForResult(irEscanearActivity, CODIGO_INTENT);

    }



    /**
     * Cuando el usuario termina de utilizar la actividad subsiguiente y muestra un resultado,
     * el sistema invoca al método onActivityResult() de tu actividad
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
