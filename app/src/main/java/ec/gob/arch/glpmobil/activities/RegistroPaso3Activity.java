package ec.gob.arch.glpmobil.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.entidades.Usuario;
import ec.gob.arch.glpmobil.servicios.ServiciosUsuario;
import ec.gob.arch.glpmobil.servicios.Sincronizador;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskRegistrarDistribuidor;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

/**
 * RegistroActivity manejará al XML activity_registro
 * @autor Gabriela Matute
 */
public class RegistroPaso3Activity extends AppCompatActivity {

    private ObjetoAplicacion objetosSesion;
    private GeVwClientesGlp sujetoSeleccionado;

    private TextView tvTipoSujeto;
    private TextView tvCodigoArch;
    private TextView tvRuc;
    private TextView tvNombre;
    private TextView tvDireccion;
    private TextView tvEstado;
    private TextView tvRegional;

    //Campos de adicionales para un Transporte de GLP
    private TextView tvEtiquetaPlaca;
    private TextView tvPlaca;
    private TextView tvEtiquetaMarca;
    private TextView tvMarca;
    private TextView tvEtiquetaModelo;
    private TextView tvModelo;
    private TextView tvEtiquetaCapacidad;
    private TextView tvCapacidad;
    private TextView tvEtiquetaDeposito;
    private TextView tvDeposito;

    //Link para ir al Login
    private TextView tvIrLogin;

    private EditText etCorreo;
    private EditText etCorreoRepetido;
    private TextView tvCorreo;
    private TextView tvEtiquetaCorreoRepetir;
    private Button btnAceptarRegistroUsuario;

    private ServiciosUsuario serviciosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_paso3);
        setTitle(R.string.titulo_activity_registro_paso3);

        objetosSesion = (ObjetoAplicacion)getApplication();
        sujetoSeleccionado = objetosSesion.getSujetoSeleccionado();

        tvTipoSujeto = (TextView) findViewById(R.id.tvTipoSujetoInfo);
        tvCodigoArch = (TextView) findViewById(R.id.tvCodigoArchInfo);
        tvRuc=(TextView) findViewById(R.id.tvRucInfo);
        tvNombre = (TextView)findViewById(R.id.tvNombreInfo);
        tvDireccion = (TextView) findViewById(R.id.tvDireccionInfo);
        tvEstado = (TextView) findViewById(R.id.tvEstadoInfo);
        tvRegional = (TextView) findViewById(R.id.tvRegionalInfo);

        tvEtiquetaPlaca = (TextView) findViewById(R.id.tvEtiquetaPlaca);
        tvPlaca = (TextView) findViewById(R.id.tvPlacaInfo);
        tvEtiquetaMarca = (TextView) findViewById(R.id.tvEtiquetaMarca);
        tvMarca = (TextView) findViewById(R.id.tvMarcaInfo);
        tvEtiquetaModelo = (TextView) findViewById(R.id.tvEtiquetaModelo);
        tvModelo = (TextView) findViewById(R.id.tvModeloInfo);
        tvEtiquetaCapacidad = (TextView) findViewById(R.id.tvEtiquetaCapacidad);
        tvCapacidad = (TextView) findViewById(R.id.tvCapacidadInfo);
        tvEtiquetaDeposito=(TextView) findViewById(R.id.tvEtiquetaDeposito);
        tvDeposito = (TextView) findViewById(R.id.tvDepositoInfo);

        tvIrLogin = (TextView) findViewById(R.id.tvIrLogin);
        tvIrLogin.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvIrLogin.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        etCorreo = (EditText) findViewById(R.id.etCorreo);
        etCorreoRepetido = (EditText) findViewById(R.id.etCorreoRepetido);
        tvCorreo = (TextView) findViewById(R.id.tvCorreo);
        tvEtiquetaCorreoRepetir = (TextView)  findViewById(R.id.tvEtiquetaCorreoRepetir);
        btnAceptarRegistroUsuario = (Button) findViewById(R.id.btnAceptarRegistroUsuario);

        etCorreo.setVisibility(View.VISIBLE);
        etCorreoRepetido.setVisibility(View.VISIBLE);
        tvEtiquetaCorreoRepetir.setVisibility(View.VISIBLE);
        btnAceptarRegistroUsuario.setVisibility(View.VISIBLE);
        tvCorreo.setVisibility(View.GONE);


        mostrarSujetoSeleccionado();

        serviciosUsuario = new ServiciosUsuario(this);
    }


    public void mostrarSujetoSeleccionado(){
        tvTipoSujeto.setText(sujetoSeleccionado.getTipo());
        tvCodigoArch.setText(sujetoSeleccionado.getCodigo());
        tvRuc.setText(sujetoSeleccionado.getRuc());
        tvNombre.setText(sujetoSeleccionado.getNombre());
        tvDireccion.setText(sujetoSeleccionado.getProvincia() + " / " +sujetoSeleccionado.getCanton()+ " / " +sujetoSeleccionado.getParroquia()+ " / " +sujetoSeleccionado.getDireccion());
        tvEstado.setText(sujetoSeleccionado.getEstadoEquivalente());
        tvRegional.setText(sujetoSeleccionado.getRegional());
        if(sujetoSeleccionado.getCodigoTipo().equals(ConstantesGenerales.CODIGO_TIPO_TRANSPORTE_GLP)){
            mostrarCamposTransporte();
        }else if(sujetoSeleccionado.getCodigoTipo().equals(ConstantesGenerales.CODIGO_TIPO_DEPOSITO_GLP))
        {
            mostrarCamposDeposito();
        }

    }

    public void mostrarCamposTransporte(){
        tvPlaca.setText(sujetoSeleccionado.getPlaca());
        tvMarca.setText(sujetoSeleccionado.getVehMarca());
        tvModelo.setText(sujetoSeleccionado.getVehModelo());
        tvDeposito.setText(sujetoSeleccionado.getCentroDistribGlp());

        tvEtiquetaPlaca.setVisibility(View.VISIBLE);
        tvPlaca.setVisibility(View.VISIBLE);

        tvEtiquetaMarca.setVisibility(View.VISIBLE);
        tvMarca.setVisibility(View.VISIBLE);

        tvEtiquetaModelo.setVisibility(View.VISIBLE);
        tvModelo.setVisibility(View.VISIBLE);

        tvEtiquetaCapacidad.setVisibility(View.VISIBLE);
        tvCapacidad.setVisibility(View.VISIBLE);

        tvEtiquetaDeposito.setVisibility(View.VISIBLE);
        tvDeposito.setVisibility(View.VISIBLE);
    }

    public void  mostrarCamposDeposito(){
        tvEtiquetaPlaca.setVisibility(View.GONE);
        tvPlaca.setVisibility(View.GONE);

        tvEtiquetaMarca.setVisibility(View.GONE);
        tvMarca.setVisibility(View.GONE);

        tvEtiquetaModelo.setVisibility(View.GONE);
        tvModelo.setVisibility(View.GONE);

        tvEtiquetaCapacidad.setVisibility(View.GONE);
        tvCapacidad.setVisibility(View.GONE);

        tvEtiquetaDeposito.setVisibility(View.GONE);
        tvDeposito.setVisibility(View.GONE);
    }


    public void registrarUsuario(View v){
        if(esCorreoNoNulo())
        {
            if(esMismoCorreo())
            {
                if(sujetoSeleccionado.getEstadoEquivalente().equals(ConstantesGenerales.CODIGO_ESTADO_EQUIVALENTE_AUTORIZADO))
                {
                    TaskRegistrarDistribuidorProgress tareaRegistrar = new TaskRegistrarDistribuidorProgress();
                    sujetoSeleccionado.setCorreo(etCorreo.getText().toString());
                    tareaRegistrar.execute(sujetoSeleccionado);

                }else
                {
                    UtilMensajes.mostrarMsjError(MensajeError.REGISTRO_USUARIO_NO_AUTORIZADO, TituloError.TITULO_ERROR, this);
                }
            }else
            {
                UtilMensajes.mostrarMsjError(MensajeError.REGISTRO_CORREO_NO_IGUAL, TituloError.TITULO_ERROR, this);
            }
        }else
        {
            UtilMensajes.mostrarMsjError(MensajeError.REGISTRO_CORREO_NULL, TituloError.TITULO_ERROR, this);

        }

    }



    public class TaskRegistrarDistribuidorProgress extends AsyncTask {

        private Sincronizador sincronizador;
        private ProgressDialog progressDialog;

        @Override
        protected GeVwClientesGlp doInBackground(Object... params) {
            Log.i("log_glp ---------->","INFO RegistroPaso3Activity --> TaskRegistrarDistribuidorProgress --> doInBackground() ");
            try {
                sincronizador = new Sincronizador();
                sujetoSeleccionado = sincronizador.registrarUsuarioWS((GeVwClientesGlp) params[0]);
            }catch(Exception e) {
                e.printStackTrace();
            }
            return sujetoSeleccionado;
        }


        /**
         * Método que se ejecuta antes de llamar al doInBackground()
         */
        @Override
        protected void onPreExecute() {
            Log.i("log_glp ---------->","INFO RegistroPaso3Activity --> TaskRegistrarDistribuidorProgress --> onPreExecute()");
            progressDialog = UtilMensajes.mostrarMsjProcesando(RegistroPaso3Activity.this,
                                                            ConstantesGenerales.TITULO_REGISTRO_USUARIO_PROGRESS_DIALOG,
                                                            ConstantesGenerales.MENSAJE_PROGRESS_DIALOG_ESPERA);

        }


        /**
         * Método que se ejecuta una vez que termina de ejecutar el doInBackground()
         * @param o
         */
        @Override
        protected void onPostExecute(Object o) {
            Log.i("log_glp ---------->","INFO RegistroPaso3Activity --> TaskRegistrarDistribuidorProgress --> onPostExecute()");
            UtilMensajes.cerrarMsjProcesando(progressDialog);
            try
            {
                if(sujetoSeleccionado.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_USUARIO_REGISTRADO_EXISTOSAMENTE)==0)
                {
                    insertar();
                    tvCorreo.setText(etCorreo.getText());
                    etCorreo.setVisibility(View.GONE);
                    etCorreoRepetido.setVisibility(View.GONE);
                    tvEtiquetaCorreoRepetir.setVisibility(View.GONE);
                    btnAceptarRegistroUsuario.setVisibility(View.GONE);
                    tvCorreo.setVisibility(View.VISIBLE);

                    UtilMensajes.mostrarMsjInfo(MensajeInfo.REGISTRO_EXITOSO, TituloInfo.TITULO_INFO, RegistroPaso3Activity.this);
                }else if(sujetoSeleccionado.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_USUARIO_YA_EXISTE)==0)
                {
                    guardarEnElMobil();
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.ACTUALIZACION_EXITOSA, TituloInfo.TITULO_INFO, RegistroPaso3Activity.this);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
                UtilMensajes.mostrarMsjError(MensajeError.REGISTRO_FALLO+e.getMessage(), TituloError.TITULO_ERROR, RegistroPaso3Activity.this);
            }

        }


    }





    /**
     * Método que antes de guardar la información de un usuario , valida si el usuario ya existe en la base de datos del móvil
     */
    public void guardarEnElMobil(){
        if(serviciosUsuario.esUsuarioUnico(sujetoSeleccionado.getCodigo()))
        {
            Log.v("log_glp ---------->", "ERROR RegistroPaso3Activity --> guardarEnElMobil() --> usuario insertado en el móvil por primera vez ");
            insertar();
        }else
        {
            /**Si obtuvo en algun momento usuario y clave y quedo registrado en el dispositivo movil
             en este punto deberia actualizar los datos que le llegan en el web service **/
            Log.v("log_glp ---------->", "ERROR RegistroPaso3Activity --> guardarEnElMobil() --> usuario ya existe en el móvil, actualizando datos ");
            actualizar();

        }
    }

    public boolean esCorreoNoNulo()
    {
        boolean tieneCaracteres=true;
        if(etCorreo.getText().toString().trim().compareTo("")==0 || etCorreoRepetido.getText().toString().trim().compareTo("")==0)
        {
            tieneCaracteres=false;
        }
        return tieneCaracteres;
    }

    public boolean esMismoCorreo(){
        boolean esValido=true;
        if (etCorreo.getText().toString().compareTo(etCorreoRepetido.getText().toString())!=0)
        {
            esValido=false;
        }
        return  esValido;
    }

    /**
     * Método que permitirá acceder a la aplicación luego de validar usuario y clave
     */
    public void insertar(){
            Usuario usuario = new Usuario();
            usuario.setId(sujetoSeleccionado.getCodigo());
            usuario.setClave(sujetoSeleccionado.getClave());
            usuario.setNombre(sujetoSeleccionado.getNombre());
            usuario.setRuc(sujetoSeleccionado.getRuc());
            usuario.setCorreo(sujetoSeleccionado.getCorreo());
            serviciosUsuario.insertar(usuario);
            Log.v("log_glp ---------->", "INFO RegistroPaso3Activity --> ingresar(): insertando usuario en el móvil)"+usuario.getNombre()+"clave: "+sujetoSeleccionado.getClave());
    }

    public void actualizar()
    {
        Usuario usuario = serviciosUsuario.buscarUsuarioPorId(sujetoSeleccionado.getCodigo());
        usuario.setClave(sujetoSeleccionado.getClave());
        usuario.setNombre(sujetoSeleccionado.getNombre());
        usuario.setRuc(sujetoSeleccionado.getRuc());
        usuario.setCorreo(sujetoSeleccionado.getCorreo());
        serviciosUsuario.actualizar(usuario);
        Log.v("log_glp ---------->", "INFO RegistroPaso3Activity --> actualizar(): actualizando usuario en el móvil"+usuario.getNombre()+"clave: "+sujetoSeleccionado.getClave());
    }

    public void irPaginaLogin(View v){
        Intent irPaginaLogin = new Intent(RegistroPaso3Activity.this, LoginActivity.class);
        startActivity(irPaginaLogin);
    }





}
