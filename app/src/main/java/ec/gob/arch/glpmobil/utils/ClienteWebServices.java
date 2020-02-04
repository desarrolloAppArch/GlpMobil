package ec.gob.arch.glpmobil.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class ClienteWebServices {

	
	/**
	 * Permite enviar y recibir objetos GSON con metodo POST	 *
	 * @param urlString url del web service que se quiere consumir
	 * @param requestJson request en formato json
	 * @return la respuesta como String en formato json
	 */
	public static String recuperarObjetoGson(String urlString, String requestJson) throws IOException {
		String respuesta=null;
		HttpURLConnection conexion=null;
		try{
			URL url = new URL(urlString);
			conexion = (HttpURLConnection) url.openConnection();

			//Configuro la conexión con los parámetros con los que se publica el web service
			// Se prepara para recibir y enviar datos del servidor
			conexion.setDoInput(true);
			conexion.setDoOutput(true);
			// Accion http para invocar al servicio
			conexion.setRequestMethod("POST");
			// Tipo de contenido que se va a recibir
			conexion.setRequestProperty("Accept", "application/json");
			// Tipo de contenido que se va a enviar
			conexion.setRequestProperty("Content-type", "application/json");
			conexion.setConnectTimeout(10000);// Expirar a los 10 segundos si la conexión no se establece
			conexion.setReadTimeout(600000);// Esperar solo 10 minutos para que finalice la lectura
			// Escribir en la conexion
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> UNO");
			OutputStream stream = conexion.getOutputStream();
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> DOS");
			OutputStreamWriter envia = new OutputStreamWriter(stream);
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> TRES");
			envia.write(requestJson);
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> CUATRO");
			envia.flush();
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> CINCO");
			envia.close();
			stream.close();
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> INICIANDO CONSUMO WS..."+Convertidor.dateAString(Convertidor.horafechaSistemaDate()));


			//Leo la respuesta
			InputStreamReader recibe = new InputStreamReader(conexion.getInputStream());
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> SEIS");
			BufferedReader lectura = new BufferedReader(recibe);
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> SIETE");
			respuesta = lectura.readLine();
			Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() --> OCHO");


			//Seteo el valor de la respuesta de la peticion del servidor, en caso de conexion nula queda el valor en el se inicializo la variable codigoRespuestaHTTP
			if(conexion!=null){
				Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() -- > RESPUESTA PETICION: " +urlString + " PARAMETRO ENVIADO: " + requestJson + " CODIGO RESPUESTA: "+conexion.getResponseCode() + " RESPUESTA: "+ respuesta);
			}else{
				Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson() -- > RESPUESTA PETICION: " +urlString + " PARAMETRO ENVIADO: " + requestJson + " CONEXION:"+conexion + " RESPUESTA: "+ respuesta);
			}
		}catch (Exception e){
			e.printStackTrace();
			respuesta=e.getMessage(); //Si el servidor de la ARCH esta abajo entra al catch
			Log.i("log_glp ---------->", "ERROR ClienteWebServices --> recuperarObjetoGson() -- > PETICION FALLO: " +urlString + " PARAMETRO ENVIADO: " + requestJson + " RESPUESTA: "+ respuesta);
		}finally {
			if(conexion!=null){
				Log.i("log_glp ---------->", "ERROR ClienteWebServices --> recuperarObjetoGson() -- > finally IF");
				conexion.disconnect();
			}else{
				Log.i("log_glp ---------->", "ERROR ClienteWebServices --> recuperarObjetoGson() -- > finally ESLE ");
			}
		}
		return respuesta;
	}

















	/**
	 * Permite recibir objetos gson sin enviar ningún parámetro con metodo GET	 *
	 * @param urlString
	 * @return
	 */
	public static String recuperarObjetoGson(String urlString) {
		String respuesta = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

			// Se prepara para recibir datos del servidor
			conexion.setDoInput(true);

			// Accion http para invocar al servicio
			conexion.setRequestMethod("GET");
			// Tipo de contenido que se va a recibir
			conexion.setRequestProperty("Accept", "application/json");

			conexion.setReadTimeout(999999999);

			//Leo la respuesta
			Log.i("","**** Codigo de respuesta del servidor " + conexion.getResponseCode());
			InputStreamReader recibe = new InputStreamReader(conexion.getInputStream());
			BufferedReader lectura = new BufferedReader(recibe);
			respuesta = lectura.readLine();
			Log.i("JSON", "Respuesta WEBSERVICE: " + respuesta);
			if (conexion!=null){
				Log.i("log_glp ---------->", "INFO ClienteWebServices --> recuperarObjetoGson -- > PETICION: " +urlString + " (SIN PARAMETROS ENVIADOS) CODIGO RESPUESTA: "+conexion.getResponseCode() + " RESPUESTA: "+ respuesta);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return respuesta;	}





	public static boolean validarConexionRed(Context context) {
		boolean existeConexion=true;
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		if(info == null)
		{
			existeConexion=false;
		}else if(!info.isConnected() || !info.isAvailable())
		{
			existeConexion=false;
		}
		return existeConexion;
	}

}