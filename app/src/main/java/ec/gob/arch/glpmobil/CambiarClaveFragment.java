package ec.gob.arch.glpmobil;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.servicios.ServiciosUsuario;
import ec.gob.arch.glpmobil.sesion.ObjetoAplicacion;
import ec.gob.arch.glpmobil.task.TaskActualizarUsuario;
import ec.gob.arch.glpmobil.utils.MensajeError;
import ec.gob.arch.glpmobil.utils.MensajeInfo;
import ec.gob.arch.glpmobil.utils.TituloError;
import ec.gob.arch.glpmobil.utils.TituloInfo;
import ec.gob.arch.glpmobil.utils.UtilMensajes;


/**
 * A simple {@link Fragment} subclass.
 */
public class CambiarClaveFragment extends Fragment {

    private EditText etClaveNueva1;
    private EditText etClaveNueva2;
    private Button btnCambiarClave;

    private ObjetoAplicacion objetosSesion;

    private ServiciosUsuario serviciosUsuario;

    public CambiarClaveFragment() {
        Log.v("log_glp ----------> ", "INFO CambiarClaveFragment --> CONSTRUCTOR ");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("log_glp ----------> ", "INFO CambiarClaveFragment --> onCreateView() ");
        View vista = inflater.inflate(R.layout.fragment_cambiar_clave, container, false);
        etClaveNueva1 = (EditText) vista.findViewById(R.id.etClaveNueva1);
        etClaveNueva2 = (EditText) vista.findViewById(R.id.etClaveNueva2);
        btnCambiarClave = (Button) vista.findViewById(R.id.btnCambiarClave);
        objetosSesion = (ObjetoAplicacion) getActivity().getApplication();
        serviciosUsuario = new ServiciosUsuario(getContext());
        /**
         * Mótodo que cambia la clave del usuario logeado
         */
        btnCambiarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v("log_glp ----------> ", "INFO CambiarClaveFragment --> btnCambiarClave() ");
                String clave1 = etClaveNueva1.getText().toString();
                String clave2 = etClaveNueva2.getText().toString();

                if(clave1.compareTo("")!=0 && clave2.compareTo("")!=0){
                    if(clave1.equals(clave2)){
                        if(clave1.length()>4){
                            GeVwClientesGlp cliente = new GeVwClientesGlp();
                            cliente.setCodigo(objetosSesion.getUsuario().getId());
                            cliente.setClave(clave1);

                            try {
                                Log.v("log_glp ----------> ", "INFO CambiarClaveFragment --> btnCambiarClave --> "+objetosSesion.getUsuario());
                                TaskActualizarUsuario tarea = new TaskActualizarUsuario();
                                tarea.execute(cliente);
                                cliente = (GeVwClientesGlp) tarea.get();
                                if(cliente.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_CLAVE_ACTUALIZADA_EXISTOSAMENTE)==0)
                                {
                                    objetosSesion.getUsuario().setClave(clave1);
                                    serviciosUsuario.actualizar(objetosSesion.getUsuario());
                                    UtilMensajes.mostrarMsjInfo(MensajeInfo.CLAVE_ACTUALIZADA_EXITOSAMENTE, TituloInfo.TITULO_INFO, getContext());
                                }else if(cliente.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_USUARIO_NO_ENCONTRADO)==0)
                                {
                                    UtilMensajes.mostrarMsjInfo(MensajeInfo.USUARIO_NO_ENCONTRADO, TituloInfo.TITULO_INFO, getContext());
                                }else if(cliente.getCodigoRespuesta().compareTo(ConstantesGenerales.CODIGO_RESPUESTA_ERROR_SERVIDOR)==0)
                                {
                                    UtilMensajes.mostrarMsjError(MensajeError.WEB_SERVICE_ERROR_SERVIDOR, TituloError.TITULO_ERROR, getContext());
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }else{
                            UtilMensajes.mostrarMsjError(MensajeError.CLAVES_MINIMO_CINCO_CARACTERES, TituloError.TITULO_ERROR, getContext());
                        }
                    }else {
                        UtilMensajes.mostrarMsjError(MensajeError.CLAVES_NO_COINCIDEN, TituloError.TITULO_ERROR, getContext());
                    }
                }else{
                    UtilMensajes.mostrarMsjError(MensajeError.CLAVES_OBLIGATORIAS, TituloError.TITULO_ERROR, getContext());

                }
            }
        });

        return vista;
    }

}
