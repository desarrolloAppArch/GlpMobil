package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;


public class ConsultarVentaFragment extends Fragment {

    EditText etIdentificacion;
    Button btnBuscar;
    private ObjetoAplicacion objetosSesion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("log_glp_cupo ---->","INFO ConsultarVentaFragment --> onCreateView():" );
        View view = inflater.inflate(R.layout.fragment_consultar_venta, container, false);
        etIdentificacion =  (EditText)view.findViewById(R.id.etIdentificacionBuscar);
        btnBuscar = (Button)view.findViewById(R.id.btnBuscarVentas);
        //Al ser el fragment parte del Activity, puedo obtener el Activity con getActivity() ya que no esta directamente relacionado
        objetosSesion = (ObjetoAplicacion) getActivity().getApplication();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Buscar ventas de: "+etIdentificacion.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }





}
