package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ec.gob.arch.glpmobil.R;


public class ConsultarVentaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("log_glp_cupo ---->","INFO ConsultarVentaFragment --> onCreateView():" );
        return inflater.inflate(R.layout.fragment_consultar_venta, container, false);
    }

}
