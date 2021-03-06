package ec.gob.arch.glpmobil.servicios;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ec.gob.arch.glpmobil.constantes.PathWebServices;
import ec.gob.arch.glpmobil.entidades.Venta;
import ec.gob.arch.glpmobil.entidades.VwCupoHogar;
import ec.gob.arch.glpmobil.entidades.GeVwClientesGlp;
import ec.gob.arch.glpmobil.utils.ClienteWebServices;

public class Sincronizador {

    private String url_ws_obtenerDistribuidores;
    private String url_ws_loginUsuario;
    private String url_ws_registrarUsuario;
    private String url_ws_consultaCupo;
    private String url_ws_ventas;
    private String url_ws_actualizarUsuario;
    private String url_ws_recuperarClave;

    /**
     * Constructor
     */
    public Sincronizador(){
        url_ws_obtenerDistribuidores = getPathWsUsuario()+PathWebServices.METODO_OBTENER_DISTRIBUIDORES;
        url_ws_loginUsuario = getPathWsUsuario()+PathWebServices.METODO_LOGIN_USUARIO;
        url_ws_registrarUsuario = getPathWsUsuario()+PathWebServices.METODO_REGISTRAR_USUARIO;
        url_ws_actualizarUsuario = getPathWsUsuario()+PathWebServices.METODO_ACTUALIZAR_USUARIO;
        url_ws_consultaCupo = getPathWsCupo()+PathWebServices.METODO_CONSULTA_CUPO;
        url_ws_ventas=getPathWsVentas()+PathWebServices.METODO_REGISTRO_VENTA;
        url_ws_recuperarClave = getPathWsUsuario()+PathWebServices.METODO_RECUPERAR_CLAVE;
    }

    /**
     * Método que permite obtener la unión del path base y el path del ws usuario
     * @return
     */
    private String getPathWsUsuario() {
        return PathWebServices.PATH_BASE+PathWebServices.WS_USUARIO;
    }

    /**
     * @author Vanessa Ponce
     * Método que permite obtener la unión del path base y el path del ws cupo
     * @return
     */
    private String getPathWsCupo() {
        return PathWebServices.PATH_BASE+PathWebServices.WS_CUPO;
    }

    /**
     * @author Blanca Yanguicela
     * Método que permite la unión del path base y el path del ws ventas
     * @return
     */
    private String getPathWsVentas(){
        return PathWebServices.PATH_BASE+PathWebServices.WS_VENTAS;
    }

    /**
     * Método que invoca al web service
     * que busca un distribuidor en la base de datos de la ARCH con un RUC específico
     * @param cliente
     * @return
     */
    public List<GeVwClientesGlp> obtenerDistribuidoresWS(GeVwClientesGlp cliente){
        List<GeVwClientesGlp> listaDistribuidores=null;
        try
        {
            Gson gson = new GsonBuilder().create();
            String parametroPeticionWs = gson.toJson(cliente);
            String respuestaWs = ClienteWebServices.recuperarObjetoGson(url_ws_obtenerDistribuidores, parametroPeticionWs);

            Type type = new TypeToken<List<GeVwClientesGlp>>() {}.getType();
            listaDistribuidores = (List<GeVwClientesGlp>) gson.fromJson(respuestaWs,type);
            Log.i("log_glp ---------->","INFO Sincronizador --> obtenerDistribuidoresWS() --> transformando el resultado en formato JSON a List<GeVwClientesGlp>");
        }catch (Exception e)
        {
            e.printStackTrace();
            listaDistribuidores=null;
        }


        return listaDistribuidores;
    }



    /**
     * Método que invoca al ws
     * que valida si existe usuario y clave en la base de datos del módulo de seguridades del SISCOH
     * @param cliente
     * @return
     */
    public GeVwClientesGlp loginUsuarioWS(GeVwClientesGlp cliente){
        GeVwClientesGlp clienteResultado=null;
        try
        {
            Gson gson = new Gson();
            String parametroPeticionWs = gson.toJson(cliente);
            String respuestaWs = ClienteWebServices.recuperarObjetoGson(url_ws_loginUsuario,parametroPeticionWs);
            Log.i("log_glp ---------->","INFO Sincronizador --> loginUsuarioWS() --> INICIO transformación a GeVwClientesGlp");
            clienteResultado = gson.fromJson(respuestaWs, GeVwClientesGlp.class);
            Log.i("log_glp ---------->","INFO Sincronizador --> loginUsuarioWS() --> FIN transformación a GeVwClientesGlp");

        }catch (Exception e)
        {
            e.printStackTrace();
            clienteResultado=null;
        }
        return clienteResultado;
    }

    /**
     * Método que invoca al ws
     * que valida y registra un usuario en el módulo de seguridades del SISCOH
     * @param cliente
     * @return
     */
    public GeVwClientesGlp registrarUsuarioWS(GeVwClientesGlp cliente){
        GeVwClientesGlp clienteResultado=null;
        try
        {
            Gson gson = new Gson();
            String parametroPeticionWs = gson.toJson(cliente);
            String respuestaWs = ClienteWebServices.recuperarObjetoGson(url_ws_registrarUsuario,parametroPeticionWs);
            clienteResultado = gson.fromJson(respuestaWs, GeVwClientesGlp.class);
            Log.i("log_glp ---------->","INFO Sincronizador --> registrarUsuarioWS() --> transformando el resultado en formato JSON a objeto GeVwClientesGlp");

        }catch (Exception e)
        {
            e.printStackTrace();
            clienteResultado=null;
        }
        return clienteResultado;
    }


    /**
     * Método que invoca al ws que actualiza la clave
     * @param cliente
     * @return
     */
    public GeVwClientesGlp actualizarUsuarioWS(GeVwClientesGlp cliente){
        GeVwClientesGlp clienteResultado=null;
        try
        {
            Gson gson = new Gson();
            String parametroPeticionWs = gson.toJson(cliente);
            String respuestaWs = ClienteWebServices.recuperarObjetoGson(url_ws_actualizarUsuario,parametroPeticionWs);
            clienteResultado = gson.fromJson(respuestaWs, GeVwClientesGlp.class);
            Log.i("log_glp ---------->","INFO Sincronizador --> actualizarUsuarioWS() --> transformando el resultado en formato JSON a objeto GeVwClientesGlp");
        }catch (Exception e)
        {
            e.printStackTrace();
            clienteResultado=null;
        }
        return clienteResultado;
    }


    /**
     * Método que invoca al ws que actualiza la clave
     * @param cliente
     * @return
     */
    public GeVwClientesGlp recuperarClaveWS(GeVwClientesGlp cliente){
        GeVwClientesGlp clienteResultado=null;
        try
        {
            Gson gson = new Gson();
            String parametroPeticionWs = gson.toJson(cliente);
            String respuestaWs = ClienteWebServices.recuperarObjetoGson(url_ws_recuperarClave,parametroPeticionWs);
            clienteResultado = gson.fromJson(respuestaWs, GeVwClientesGlp.class);
            Log.i("log_glp ---------->","INFO Sincronizador --> actualizarUsuarioWS() --> transformando el resultado en formato JSON a objeto GeVwClientesGlp");
        }catch (Exception e)
        {
            e.printStackTrace();
            clienteResultado=null;
        }
        return clienteResultado;
    }



    /**
     * @author Vanessa Ponce
     * Método que invoca al web service
     * que busca el listado de hogares y cupos en la base de datos de la ARCH con un usuario de distribuidor o vehículo
     * @param usuario
     * @return
     */
    public List<VwCupoHogar> consultarCupoWS(VwCupoHogar usuario){
        ArrayList<VwCupoHogar> listahogares=null;
        String respuestaWs=null;
        try
        {
            Gson gson = new Gson();
            String parametroPeticionWs = gson.toJson(usuario);
            respuestaWs = ClienteWebServices.recuperarObjetoGson(url_ws_consultaCupo,parametroPeticionWs);
            //Si la respuesta retorna null, si lo transforma en una lista null
            Type type = new TypeToken<ArrayList<VwCupoHogar>>() {}.getType();
            Log.i("log_glp ---------->","INFO Sincronizador --> consultarCupoWS() --> INICIO transformado a List<VwCupoHogar>");
            listahogares = gson.fromJson(respuestaWs,type);
            Log.i("log_glp ---------->","INFO Sincronizador --> consultarCupoWS() --> FIN transformado a List<VwCupoHogar> listahogares:"+listahogares);
        }catch (Exception e)
        {
            e.printStackTrace();
            VwCupoHogar cupoConError = new VwCupoHogar();
            cupoConError.setCodigoRespuesta(respuestaWs);
            listahogares= new ArrayList<>();
            listahogares.add(cupoConError);
            Log.i("log_glp ---------->","ERROR Sincronizador --> consultarCupoWS() --> Exception: "+e.getMessage());
        }
        return listahogares;
    }

    public String registrarVentasWs(List<Venta> ventas){
        String respuesta= null;
        try{
            Log.i("log_glp ---------->","INFO ingresa registrarVentasWs: ");
            Gson gson = new GsonBuilder().create();
            String parametroPeticionWs = gson.toJson(ventas);
            String respuestaWs= ClienteWebServices.recuperarObjetoGson(url_ws_ventas,parametroPeticionWs);

            Log.i("log_glp ---------->","INFO Sincronizador --> respuestaWs() "+respuestaWs);
            respuesta=gson.fromJson(respuestaWs,String.class);
            Log.i("log_glp ---------->","INFO Sincronizador --> registrarVentasWs() respuesta-->"+respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("log_glp ---------->","ERROR catch --> registrarVentasWs() -->"+e.getMessage());
            respuesta = e.getMessage();
        }
        return respuesta;
    }



}
