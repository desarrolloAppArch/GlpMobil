package ec.gob.arch.glpmobil.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.Sincronizador;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

/**
 * RegistroActivity manejará al XML activity_registro
 * @autor Gabriela Matute
 */
public class RegistroActivity extends AppCompatActivity {

    private EditText etRuc;
    private GeVwClientesGlp sujetoGlp;
    private RadioGroup rgTipoSujeto;
    private ObjetoAplicacion objetosSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle(R.string.titulo_activity_registro);

        rgTipoSujeto = findViewById(R.id.radioTipoSujeto);
        etRuc = findViewById(R.id.etRuc);

        objetosSesion = (ObjetoAplicacion) getApplication();

    }

    public void nuevoRegistro(){
        sujetoGlp =new GeVwClientesGlp();
    }

    /**
     * Método que permite la búsqueda de un sujeto de control de la base del SICOHI
     * a través de un web service
     * @autor Gabriela Matute
     * @param v
     */
    public void buscarDistribuidores(View v){
        try {
            nuevoRegistro();
            if (ClienteWebServices.validarConexionRed(this)){
                if (validarTipo()){
                    if(etRuc.getText().toString().compareTo("")!=0){
                        sujetoGlp.setRuc(etRuc.getText().toString());
                        TaskBuscarDistribuidorProgress tarea = new TaskBuscarDistribuidorProgress();
                        tarea.execute(sujetoGlp);
                    }else
                    {
                        UtilMensajes.mostrarMsjError(MensajeError.REGISTRO_RUC_SUJETO_NULL, TituloError.TITULO_ERROR, this);
                    }
                }else
                {
                    UtilMensajes.mostrarMsjError(MensajeError.REGISTRO_TIPO_SUJETO_NULL, TituloError.TITULO_ERROR, this);
                }
            }else
            {
                UtilMensajes.mostrarMsjError(MensajeError.CONEXION_NULL, TituloError.TITULO_ERROR, this);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public class TaskBuscarDistribuidorProgress extends AsyncTask {

        private Sincronizador sincronizador;
        private List<GeVwClientesGlp> listaResultados;
        private ProgressDialog progressDialog;

        @Override
        protected List<GeVwClientesGlp> doInBackground(Object... params) {
            Log.i("log_glp ---------->","INFO RegistroActivity --> TaskBuscarDistribuidorProgress --> doInBackground()");
            try {
                sincronizador = new Sincronizador();
                listaResultados = sincronizador.obtenerDistribuidoresWS((GeVwClientesGlp) params[0]);
            }catch(Exception e) {
                e.printStackTrace();
            }
            return listaResultados;
        }


        /**
         * Método que se ejecuta antes de llamar al doInBackground()
         */
        @Override
        protected void onPreExecute() {
            Log.i("log_glp ---------->","INFO TaskConsultarCupoProgress --> onPreExecute()");
            progressDialog = UtilMensajes.mostrarMsjProcesando(RegistroActivity.this,
                    ConstantesGenerales.TITULO_BUSCANDO_SUJETO_PROGRESS_DIALOG,
                    ConstantesGenerales.MENSAJE_PROGRESS_DIALOG_ESPERA);
        }


        /**
         * Método que se ejecuta una vez que termina de ejecutar el doInBackground()
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            UtilMensajes.cerrarMsjProcesando(progressDialog);
            if (listaResultados!=null && listaResultados.size()>0){
                Log.i("log_glp ---------->","INFO RegistroActivity --> buscarDistribuidores() --> RESULTADO:" +listaResultados.size());
                objetosSesion.setListaSujetos(listaResultados);
                Intent irActivityRegistroPaso2 = new Intent(RegistroActivity.this, RegistroPaso2Activity.class);
                startActivity(irActivityRegistroPaso2);
            }else {
                Log.i("log_glp ---------->","INFO RegistroActivity --> buscarDistribuidores() --> RESULTADO: NINGUNO");
                UtilMensajes.mostrarMsjInfo(MensajeInfo.SIN_RESULTADOS, TituloInfo.TITULO_INFO, RegistroActivity.this);
            }
        }
    }




    /**
     * Método que valida si ha seleccionado uno de los tipo de sujeto Depósito Glp o Transporte Glp
     * @autor Gabriela Matute
     * @return true si esta seleccionado uno de los radio button
     */
    private boolean validarTipo(){
        boolean tipoSeleccionado=false;
        if (rgTipoSujeto.getCheckedRadioButtonId() == R.id.radioDepositoGlp)
        {
            sujetoGlp.setCodigoTipo(ConstantesGenerales.CODIGO_TIPO_DEPOSITO_GLP);
            tipoSeleccionado=true;
        }else if(rgTipoSujeto.getCheckedRadioButtonId() == R.id.radioTransporteGlp)
        {
            sujetoGlp.setCodigoTipo(ConstantesGenerales.CODIGO_TIPO_TRANSPORTE_GLP);
            tipoSeleccionado=true;
        }
        return  tipoSeleccionado;
    }


}
