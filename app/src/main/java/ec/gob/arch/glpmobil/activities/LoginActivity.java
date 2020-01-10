package ec.gob.arch.glpmobil.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.entidades.Usuario;
import ec.gob.arch.glpmobil.servicios.ServiciosUsuario;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskLoguearUsuario;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etClave;
    private AutoCompleteTextView etAutoUsuario;
    private ServiciosUsuario serviciosUsuario;


    private TextView tvRegistrarse;
    private TextView tvSalir;
    private TextView tvOlvidoClave;

    private String PREFERENCIAS_APP_XML ="MisPreferenciasGlp";

    private ObjetoAplicacion objetosSesion;

    private List<Usuario> listaUsuarios;
    private static String[] USUARIOS = new String[]{};
    private String usuarioLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //Obtiene valor de preferencia (la primera ocasión en la app es por default true)
        boolean esPrimeraVez = getValuePrimeraVez(getApplicationContext());

        //Valida si muestra o no RegistroActivity
        if (esPrimeraVez){

            Log.v("log_glp ---------->", "INFO LoginActivity --> onCreate(): Es la  primera vez en la app");
            //Cambio de valor a false para que no vuelva a mostrar nunca más la pantalla de registro
            //setValuePrimeraVez(getApplicationContext(),false);
        }else {
            Log.v("log_glp ---------->", "INFO LoginActivity --> onCreate(): NO es la primera vez en la app");

        }

        etClave = (EditText) findViewById(R.id.etClave);
        tvRegistrarse = (TextView) findViewById(R.id.tvRegistrarse);
        tvSalir = (TextView) findViewById(R.id.tvSalirLogin);
        tvOlvidoClave = (TextView) findViewById(R.id.tvOlvidoClave);
        etAutoUsuario = (AutoCompleteTextView) findViewById(R.id.autoTvUsuario);

        tvRegistrarse.setOnClickListener(this);
        tvRegistrarse.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvRegistrarse.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvSalir.setOnClickListener(this);
        tvSalir.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvSalir.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvOlvidoClave.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvOlvidoClave.setOnClickListener(this);
        tvOlvidoClave.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        serviciosUsuario = new ServiciosUsuario(this);
        cargarUsuariosString();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, USUARIOS);
        etAutoUsuario.setAdapter(adapter);

        objetosSesion = (ObjetoAplicacion) getApplication();

        etAutoUsuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String texto = editable.toString();
                if(!texto.equals(texto.toUpperCase())){
                    texto = texto.toUpperCase();
                    etAutoUsuario.setText(texto);
                }
                etAutoUsuario.setSelection(etAutoUsuario.getText().length());
            }
        });
    }




    public void setValuePrimeraVez(Context context, boolean valor){
        /*Creo una colección de preferencias llamada “MisPreferenciasGlp” (se crea un archivo .xml),
         si ya existe solo la llama lo instancia*/
        SharedPreferences preferenciasApp = context.getSharedPreferences(PREFERENCIAS_APP_XML, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferenciasApp.edit();
        editor.putBoolean("primeravez",valor);
        editor.commit();
    }


    public boolean getValuePrimeraVez(Context context){
        /*Creo una colección de preferencias llamada “MisPreferenciasGlp” (se crea un archivo .xml),
         si ya existe solo la llama lo instancia*/
        SharedPreferences preferenciasApp = context.getSharedPreferences(PREFERENCIAS_APP_XML, Context.MODE_PRIVATE);
        /*El valor por defecto será el devuelto por el método getString()
          si la preferencia solicitada no existe en la colección MisPreferenciasGlp*/
        boolean esPrimeraVez = preferenciasApp.getBoolean("primeravez",true);
        Log.v("log_glp ---------->", "INFO LoginActivity --> getValuePrimeraVez(): "+esPrimeraVez);
        return  esPrimeraVez;
    }

    @Override
    public void onClick(View v) {
        //Identifico en donde dio clic
        if(v.getId()==findViewById(R.id.tvRegistrarse).getId()){
            Log.v("log_glp ---------->", "INFO LoginActivity --> onClick(): dio clic en el link Registrarse");
            Intent irPaginaRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(irPaginaRegistro);
        }else if(v.getId()==findViewById(R.id.tvSalirLogin).getId())
        {
            finish();
            Log.v("log_glp ---------->", "INFO LoginActivity --> onClick(): dio clic en el link Salir");
            Intent intentSalir = new Intent(Intent.ACTION_MAIN);
            intentSalir.addCategory(Intent.CATEGORY_HOME);
            intentSalir.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentSalir);


        }else if(v.getId()==findViewById(R.id.tvOlvidoClave).getId())
        {
            Intent irPaginaRecuperarClave =  new Intent(LoginActivity.this, RecuperarClaveActivity.class);
            startActivity(irPaginaRecuperarClave);
            Log.v("log_glp ---------->", "INFO LoginActivity --> onClick(): dio clic en el link Recuperar clave");
        }

    }

    /*
    public void irPaginaInicioPruebas(View v){
        Intent irPaginaInicio = new Intent(LoginActivity.this, InicioActivity.class);
        startActivity(irPaginaInicio);
    }
    */


    /**
     * Método que permitirá acceder a la aplicación luego de validar usuario y clave
     * @param v
     */
    public void ingresar(View v){
        try
        {
            usuarioLogin = etAutoUsuario.getText().toString().trim();
            etAutoUsuario.setText(usuarioLogin);
            if (usuarioLogin.compareTo("")!=0 && etClave.getText().toString().compareTo("")!=0) {
                //Primero intento hacer login buscando en la base del dispositivo movil
                Usuario usuario = serviciosUsuario.buscarUsuarioPorId(usuarioLogin);
                if(usuario!=null)
                {
                    if (usuario.getClave().compareTo(etClave.getText().toString())==0){
                        iniciarSesionSistema(usuario);
                    }else
                    {
                        /**
                         *  Si encontro un usuario en la base del app movil y la clave es incorrecta es posible que le hayan reseteado su clave desde el modulo de seguridades
                         *  por tanto es necesario hacer login en línea para actualizar clave
                         */
                        GeVwClientesGlp usuarioRespuestaWs =realizarLoginEnLinea();
                        if (usuarioRespuestaWs!=null){
                            if(usuarioRespuestaWs.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_LOGEADO_EXITOSAMENTE)==0)
                            {
                                usuario.setClave(usuarioRespuestaWs.getClave());
                                serviciosUsuario.actualizar(usuario);
                                Log.v("log_glp ---------->", "INFO LoginActivity --> ingresar() --> ACTUALIZANDO en la base de datos del movil, la clave del usuario que se ha logeado en línea, :"+usuarioRespuestaWs.getNombre());
                                iniciarSesionSistema(usuario);
                            }else {
                                UtilMensajes.mostrarMsjError(MensajeError.LOGIN_CLAVE_INCORRECTA, TituloError.TITULO_ERROR, this);
                            }
                        }else{
                            UtilMensajes.mostrarMsjError(MensajeError.LOGIN_FALLO_CLAVE_NO_COINCIDE, TituloError.TITULO_ERROR, this);
                        }

                    }
                }else{
                    /**Luego intento de hacer login en linea
                     * Si el web service me indica que nunca se ha registrado indicar que el usuario debe Registrarse**/
                    //UtilMensajes.mostrarMsjError(MensajeError.LOGIN_USUARIO_NO_EXISTE, TituloError.TITULO_ERROR, this);
                    GeVwClientesGlp usuarioRespuestaWs =realizarLoginEnLinea();
                    if (usuarioRespuestaWs!=null)
                    {
                        Log.v("log_glp ---------->", "INFO LoginActivity --> ingresar() --> codigoRespuesta:"+usuarioRespuestaWs.getCodigoRespuesta());
                        if(usuarioRespuestaWs.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_LOGEADO_EXITOSAMENTE)==0)
                        {
                            Usuario usuarioNuevo = new Usuario();
                            usuarioNuevo.setId(usuarioRespuestaWs.getCodigo());
                            usuarioNuevo.setNombre(usuarioRespuestaWs.getNombre());
                            usuarioNuevo.setClave(usuarioRespuestaWs.getClave());
                            serviciosUsuario.insertar(usuarioNuevo);
                            Log.v("log_glp ---------->", "INFO LoginActivity --> ingresar() --> INSERTANDO en la base de datos del movil, el usuario que se ha logeado en línea:"+usuarioNuevo.getNombre());
                            iniciarSesionSistema(usuarioNuevo);
                        }else if(usuarioRespuestaWs.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_NO_COINCIDE_CLAVE)==0)
                        {
                            UtilMensajes.mostrarMsjError(MensajeError.LOGIN_CLAVE_INCORRECTA, TituloError.TITULO_ERROR, this);
                        }else if(usuarioRespuestaWs.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_NO_TIENE_PERMISOS)==0)
                        {
                            UtilMensajes.mostrarMsjError(MensajeError.LOGIN_NO_TIENE_PERMISOS, TituloError.TITULO_ERROR, this);
                        }else if(usuarioRespuestaWs.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_NO_EXISTE_USUARIO_O_INACTIVO)==0)
                        {
                            UtilMensajes.mostrarMsjError(MensajeError.LOGIN_USUARIO_NO_EXISTE, TituloError.TITULO_ERROR, this);
                        }
                    }else{
                        UtilMensajes.mostrarMsjError(MensajeError.LOGIN_FALLO_OBLIGATORIO_LOGIN_EN_LINEA, TituloError.TITULO_ERROR, this);
                    }

                }
            }else {
                UtilMensajes.mostrarMsjError(MensajeError.LOGIN_USUARIO_CLAVE_NULOS, TituloError.TITULO_ERROR, this);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }


    public GeVwClientesGlp realizarLoginEnLinea(){
        Log.v("log_glp ---------->", "INFO LoginActivity --> ingresar() --> intentando login en linea ");
        GeVwClientesGlp usuarioRespuestaWs=null;
        try
        {
            if(ClienteWebServices.validarConexionRed(this)){
                GeVwClientesGlp usuarioABuscar = new GeVwClientesGlp();
                usuarioABuscar.setCodigo(usuarioLogin);
                usuarioABuscar.setClave(etClave.getText().toString());
                TaskLoguearUsuario tarea = new TaskLoguearUsuario();
                tarea.execute(usuarioABuscar);
                usuarioRespuestaWs =(GeVwClientesGlp) tarea.get();
            }
            else
            {
                UtilMensajes.mostrarMsjError(MensajeError.LOGIN_CONEXION_NULL, TituloError.TITULO_ERROR, this);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return usuarioRespuestaWs;
    }



    public void iniciarSesionSistema(Usuario usuario)
    {
        objetosSesion.setUsuario(usuario);
        Intent irPaginaRegistroVenta = new Intent(LoginActivity.this, InicioActivity.class);
        startActivity(irPaginaRegistroVenta);
        Log.v("log_glp ---------->", "INFO LoginActivity --> ingresar() --: El usuario "+ objetosSesion.getUsuario().getNombre()+ " ha iniciado SESION en la app");
    }







    public void cargarUsuariosString(){
        Log.v("log_glp ---------->", "INFO LoginActivity --> cargarUsuariosString()");
        listaUsuarios = serviciosUsuario.buscarTodos();
        if(listaUsuarios!=null && listaUsuarios.size()>0){
            int i=0;
            USUARIOS = new String[listaUsuarios.size()];
            for(Usuario usuario : listaUsuarios){
                USUARIOS[i] = usuario.getId();
                Log.v("log_glp ---------->", "INFO cargarUsuariosString --> USUARIOS["+i+"] = "+USUARIOS[i]);
                i++;
            }
        }

    }




}
