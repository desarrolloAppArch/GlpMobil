package ec.gob.arch.glpmobil.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.entidades.Usuario;
import ec.gob.arch.glpmobil.servicios.ServiciosUsuario;
import ec.gob.arch.glpmobil.task.TaskRecuperarClave;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;

public class RecuperarClaveActivity extends AppCompatActivity {

    Button btnAceptarRecuperar;
    Button btnCancelarRecuperar;
    EditText etUsuarioReseteo;
    private ServiciosUsuario serviciosUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_clave);
        setTitle("RECUPERACIÓN DE CLAVE");

        btnAceptarRecuperar = (Button) findViewById(R.id.btnAceptarRecuperar);
        btnCancelarRecuperar = (Button) findViewById(R.id.btnCancelarRecuperar);
        etUsuarioReseteo = (EditText) findViewById(R.id.etUsuarioReseteo);

        serviciosUsuario = new ServiciosUsuario(this);


        btnCancelarRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irPaginaLogin =  new Intent(RecuperarClaveActivity.this, LoginActivity.class);
                startActivity(irPaginaLogin);
                finish();
            }
        });


        btnAceptarRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etUsuarioReseteo.getText().toString().compareTo("")!=0){
                    if(ClienteWebServices.validarConexionRed(view.getContext())){
                        GeVwClientesGlp usuario = new GeVwClientesGlp();
                        usuario.setCodigo(etUsuarioReseteo.getText().toString());

                        try {
                            //LLamada al web service
                            TaskRecuperarClave tarea = new TaskRecuperarClave();
                            tarea.execute(usuario);
                            GeVwClientesGlp usuarioRespuesta = (GeVwClientesGlp) tarea.get();
                            if(usuarioRespuesta!=null){
                                if(usuarioRespuesta.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_CLAVE_ACTUALIZADA_EXISTOSAMENTE)){
                                    UtilMensajes.mostrarMsjInfo(MensajeInfo.CLAVE_RESETEADA_EXITOSAMENTE+usuarioRespuesta.getCorreo(), TituloInfo.TITULO_INFO, view.getContext());

                                    try{
                                        //Actualizo en la base del teléfono
                                        Usuario usuarioMobil = serviciosUsuario.buscarUsuarioPorId(usuario.getCodigo());
                                        if(usuarioMobil!=null) {
                                            usuarioMobil.setClave(usuarioRespuesta.getClave());
                                            usuarioMobil.setCorreo(usuarioRespuesta.getCorreo());
                                            serviciosUsuario.actualizar(usuarioMobil);
                                        }
                                    }catch(Exception e){
                                        UtilMensajes.mostrarMsjError(MensajeError.ERROR_BASE_MOVIL+e.getMessage(), TituloError.TITULO_ERROR, view.getContext());
                                    }

                                }else if(usuarioRespuesta.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_USUARIO_NO_ENCONTRADO)){
                                    UtilMensajes.mostrarMsjInfo(MensajeInfo.USUARIO_NO_EXISTE, TituloInfo.TITULO_INFO, view.getContext());

                                }else if(usuarioRespuesta.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_NO_TIENE_PERMISOS_RESETEO)){
                                    UtilMensajes.mostrarMsjInfo(MensajeInfo.USUARIO_NO_TIENE_PERMISOS, TituloInfo.TITULO_INFO, view.getContext());

                                }else if(usuarioRespuesta.getCodigoRespuesta().equals(ConstantesGenerales.CODIGO_RESPUESTA_ERROR_SERVIDOR)){
                                    UtilMensajes.mostrarMsjError(MensajeError.WEB_SERVICE_ERROR_SERVIDOR, TituloError.TITULO_ERROR, view.getContext());
                                }

                            }else{
                                UtilMensajes.mostrarMsjError(MensajeError.CONEXION_SERVIDOR_NULL, TituloError.TITULO_ERROR, view.getContext());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            UtilMensajes.mostrarMsjError(MensajeError.CONEXION_SERVIDOR_NULL, TituloError.TITULO_ERROR, view.getContext());
                        }
                    }
                    else
                    {
                        UtilMensajes.mostrarMsjError(MensajeError.CONEXION_NULL, TituloError.TITULO_ERROR, view.getContext());
                    }

                }else{
                    UtilMensajes.mostrarMsjInfo(MensajeInfo.RESETEO_USUARIO_NULL, TituloInfo.TITULO_INFO, view.getContext());
                }
            }
        });


    }
}
