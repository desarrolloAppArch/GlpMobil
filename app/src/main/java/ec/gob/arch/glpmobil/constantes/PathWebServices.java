package ec.gob.arch.glpmobil.constantes;
/**
 * 
 * @author soraya.matute
 *
 */
public interface PathWebServices {
	/**
	 * PATH_BASE DE PRUEBAS
	 * http://localhost:8080/webServiceGlpMobilWeb/rest
	 *
	 *  PATH_BASE DE PRODUCCION
	 * http://127.0.0.1:8080/webServiceGlpMobilWeb/rest
	 */

    //String PATH_BASE="http://172.16.15.45:8080/webServiceGlpMobilWeb/rest";
	String PATH_BASE="http://172.16.15.0:8080/webServiceGlpMobilWeb/rest";
	String WS_USUARIO = "/ws-usuario";
	String METODO_OBTENER_DISTRIBUIDORES="/metodo-obtenerDistribuidores";
	String METODO_LOGIN_USUARIO="/metodo-loginUsuario";
	String METODO_REGISTRAR_USUARIO="/metodo-registrarUsuario";
	String METODO_ACTUALIZAR_USUARIO="/metodo-cambiarClave";

    String WS_CUPO = "/ws-cupo";
    String METODO_CONSULTA_CUPO="/metodo-consultaCupo";

	String WS_VENTAS = "/ws-venta";
    String METODO_REGISTRO_VENTA ="/metodo-registroVenta";







}
