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
import android.widget.Toast;


public class ClienteWebServices {

	
	/**
	 * Permite enviar y recibir objetos GSON con metodo POST	 *
	 * @param urlString url del web service que se quiere consumir
	 * @param requestJson request en formato json
	 * @return la respuesta como String en formato json
	 */
	public static String recuperarObjetoGson(String urlString, String requestJson) throws IOException {
		String respuesta ;
		Log.i("log_glp ---------->", "INFO ingresa recuperarObjetoGson ");

			URL url = new URL(urlString);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

			// Se prepara para recibir y enviar datos del servidor
			conexion.setDoInput(true);
			conexion.setDoOutput(true);

			// Accion http para invocar al servicio
			conexion.setRequestMethod("POST");
			// Tipo de contenido que se va a recibir
			conexion.setRequestProperty("Accept", "application/json");
			// Tipo de contenido que se va a enviar
			conexion.setRequestProperty("Content-type", "application/json");

			conexion.setReadTimeout(999999999);


			// Escribir en la conexion
			OutputStream stream = conexion.getOutputStream();
		Log.i("log_glp ---------->", "INFO recuperarObjetoGson stream"+ stream);
			OutputStreamWriter envia = new OutputStreamWriter(stream);
			envia.write(requestJson);
			envia.close();

			//Leo la respuesta
			InputStreamReader recibe = new InputStreamReader(conexion.getInputStream());
			BufferedReader lectura = new BufferedReader(recibe);
			respuesta = lectura.readLine();
		Log.i("log_glp ---------->", "INFO recuperarObjetoGson respuesta"+ respuesta);
			if (conexion!=null){
				Log.i("log_glp ---------->", "ERROR ClienteWebServices --> recuperarObjetoGson -- > PETICION: " +urlString + " PARAMETRO ENVIADO: " + requestJson + " CODIGO RESPUESTA: "+conexion.getResponseCode() + " RESPUESTA: "+ respuesta);

			}
		Log.i("log_glp ---------->", "INFO respuesta "+ respuesta);


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

			conexion.setReadTimeout(10000);

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
		return respuesta;
	}



	public static void toast(Context context, String mensaje) {
		Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	

	public static void toastError(Context context, String mensaje) {
		Toast toast = Toast.makeText(context, mensaje, Toast.LENGTH_LONG);
		toast.show();
	}

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