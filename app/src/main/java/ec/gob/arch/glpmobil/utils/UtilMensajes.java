package ec.gob.arch.glpmobil.utils;

import ec.gob.arch.glpmobil.R;
import ec.gob.arch.glpmobil.constantes.ConstantesGenerales;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Utilitario para mostrar mensajes de error y de información en pantalla
 * @author soraya.matute
 *
 */
public class UtilMensajes {

	/**
	 * Muestra un mensaje informativo
	 * @author soraya.matute
	 * @param mensaje
	 * @param titulo
	 * @param context de la vista en la cual se va a mostrar el mensaje
	 */
	public static void mostrarMsjInfo(String mensaje, TituloInfo titulo,Context context) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(titulo.getTitulo());
		alertDialogBuilder.setMessage(mensaje).setCancelable(true).setIcon(R.drawable.icono_info);
		alertDialogBuilder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	
	/**
	 * Muestra un mensaje de error
	 * @author soraya.matute
	 * @param mensaje
	 * @param titulo
	 * @param context
	 */
	public static void mostrarMsjError(String mensaje, TituloError titulo, Context context) {
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setTitle(titulo.getTitulo());
		alertDialogBuilder.setMessage(mensaje).setCancelable(true).setIcon(R.drawable.icono_error);
		alertDialogBuilder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}


	public static ProgressDialog mostrarMsjProcesando(Context context, String titulo, String mensaje){
		//Configurar el progressDialog
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.setTitle(titulo);
		progressDialog.setMessage(mensaje);
		//Abro el progressDialog
		progressDialog.show();
		Log.i("log_glp ---------->","INFO UtilMensajes --> mostrarMsjProcesando()"+mensaje);
		return progressDialog;
	}

	public static void cerrarMsjProcesando(ProgressDialog progressDialog){
		//Cierro el progressDialog
		progressDialog.dismiss();
		Log.i("log_glp ---------->","INFO UtilMensajes --> cerrarMsjProcesando()");
	}


}
