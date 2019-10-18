package ec.gob.arch.glpmobil.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.entidades.Venta;

/**
 * A simple {@link Fragment} subclass.
 */
public class VentaPaso2Fragment extends Fragment {


    public VentaPaso2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("log_glp ----------> ", "INFO VentaPaso2Fragment --> onCreate()");
        Bundle parametrosRecibidos = getArguments();
        if(parametrosRecibidos!=null){
            Venta venta = (Venta) parametrosRecibidos.getSerializable(CtVenta.CLAVE_CUPO_VENTA);
            Log.i("log_glp_cupo ---->","INFO VentaPaso2Fragment --> onCreate() --> parametrosRecibidos: "+venta.getCupo() );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("log_glp ----------> ", "INFO VentaPaso2Fragment --> onCreateView()");
        return inflater.inflate(R.layout.fragment_venta_paso2, container, false);
    }

}
