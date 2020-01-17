package ec.gob.arch.glpmobil.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;

/**
 * RegistroActivity manejará al XML activity_registro
 * @autor Gabriela Matute
 */
public class RegistroPaso2Activity extends AppCompatActivity {

    private ObjetoAplicacion objetosSesion;
    private ListView lvSujetos;
    private SujetosAdapter sujetosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paso2);
        setTitle(R.string.titulo_activity_registro_paso2);

        lvSujetos = (ListView) findViewById(R.id.lvSujetos);
        lvSujetos.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        lvSujetos.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("log_glp ---------->", "click en el elemento " + position + " de mi ListView");

                GeVwClientesGlp sujetoSeleccionado = (GeVwClientesGlp) lvSujetos.getItemAtPosition(position);
                objetosSesion.setSujetoSeleccionado(sujetoSeleccionado);
                Log.i("log_glp ---------->","INFO RegistroPaso2Activity --> onCreate() --> SUJETO SELECCIONADO:" +sujetoSeleccionado.getCodigo());
                Intent irActivityRegistroPaso2 = new Intent(RegistroPaso2Activity.this, RegistroPaso3Activity.class);
                startActivity(irActivityRegistroPaso2);
            }
        });
        objetosSesion = (ObjetoAplicacion)getApplication();
        llenarListaSujetos(objetosSesion.getListaSujetos());

    }


    public void llenarListaSujetos(List<GeVwClientesGlp> listaSujetosGlp){
        sujetosAdapter = new SujetosAdapter(this, R.layout.fila_sujeto_linear, listaSujetosGlp);
        lvSujetos.setAdapter(sujetosAdapter);
    }




    /**
     * Clase SujetosAdapter que permitirá agregar
     */
    class SujetosAdapter extends ArrayAdapter<GeVwClientesGlp> {

        //Variables
        private List<GeVwClientesGlp> listaSujetos;

        /**
         * Constructor de SujetosAdapter
         * @param context
         * @param resource
         * @param listaSujetos
         */
        public SujetosAdapter(@NonNull Context context, int resource, @NonNull List<GeVwClientesGlp> listaSujetos)       {
            super(context, resource, listaSujetos);
            this.listaSujetos = listaSujetos;
        }


        private class Fila{
            TextView tvCodigoArch;
            TextView tvNombre;
            TextView tvDireccion;
            TextView tvPlaca;
        }

        /**
         * Método que obtiene la vista del listado
         * @param posicion
         * @param convertView
         * @param parent
         * @return
         */
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public View getView(final int posicion, View convertView, ViewGroup parent){

            Fila fila =  null;

            if (convertView == null){
                LayoutInflater layout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layout.inflate(R.layout.fila_sujeto_linear, null);
                fila = new Fila();
                fila.tvCodigoArch = (TextView) convertView.findViewById(R.id.tvCodigoArch);
                fila.tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
                fila.tvDireccion = (TextView) convertView.findViewById(R.id.tvDireccion);
                fila.tvPlaca = (TextView) convertView.findViewById(R.id.tvPlaca) ;
                convertView.setTag(fila);
            }else
                {
                    fila  = (Fila) convertView.getTag();
                }

                GeVwClientesGlp sujeto = listaSujetos.get(posicion);
                fila.tvCodigoArch.setText(sujeto.getCodigo());
                fila.tvNombre.setText(sujeto.getNombre());
                fila.tvDireccion.setText(sujeto.getDireccion());
                fila.tvDireccion.setPadding(0,0,0,20);

                if(sujeto.getCodigoTipo().equals(ConstantesGenerales.CODIGO_TIPO_TRANSPORTE_GLP))
                {
                    fila.tvPlaca.setPadding(0,20,0,0);
                    fila.tvPlaca.setTextAppearance(R.style.TextAppearance_AppCompat_Medium);
                    fila.tvCodigoArch.setTextAppearance(R.style.TextAppearance_AppCompat_Small);

                    fila.tvPlaca.setTextColor(getResources().getColor(R.color.colorPrimary));
                    fila.tvPlaca.setText(sujeto.getPlaca());
                }else if(sujeto.getCodigoTipo().equals(ConstantesGenerales.CODIGO_TIPO_DEPOSITO_GLP))
                {
                    fila.tvCodigoArch.setPadding(0,20,0,0);
                    fila.tvCodigoArch.setTextColor(getResources().getColor(R.color.colorPrimary));
                    fila.tvPlaca.setVisibility(View.GONE);
                }

            return  convertView;
        }
    }




}
