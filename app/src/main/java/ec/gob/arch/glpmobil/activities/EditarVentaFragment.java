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
import android.widget.TextView;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;


public class EditarVentaFragment extends Fragment {

    private Button btnCancelarEdicion;
    private TextView tvNombre;
    private Venta venta;

    /**
     * Es el primero en ejecutarse, antes del onCreateView()
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("log_glp_cupo ---->","INFO EditarVentaFragment --> onCreate()" );
        //Verifico si tengo parametros enviados por otro fragment o activity
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
        tvNombre = view.findViewById(R.id.tvNombreSeleccionada);
        tvNombre.setText(venta.getNombre_compra());

        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment, new ConsultarVentaFragment()).commit();

            }
        });

        return view;
    }


}
