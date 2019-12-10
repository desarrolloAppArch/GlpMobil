package ec.gob.arch.glpmobil.servicios;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ec.gob.arch.glpmobil.database.BaseGlp;

/**
 * 
 * @author soraya.matute
 *
 */
public class ServicioBase {
	
	protected BaseGlp dbHelper;
	protected SQLiteDatabase db;
	
	/**
	 * Constructor
	 * @param context
	 * @author soraya.matute
	 */
	public ServicioBase (Context context){
		Log.v("log_glp ---------->", "INFO ServicioBase --> CONSTRUCTOR ");
		dbHelper = BaseGlp.getInstance(context);

	}
	
	
	/**
	 * Abre la conexión a la base de datos
	 * @throws SQLException si existe un error al conectarse
	 */
	public void abrir() throws SQLException {
		db = dbHelper.getWritableDatabase();
		//Log.v("log_glp ---------->", "INFO ServicioBase --> abrir(): Abriendo conexion con la base de datos SQLITE");
	}
	
	
	/**
	 * Cierra la conexión a la base de datos
	 */
	public void cerrar(){
		dbHelper.close();
		//Log.v("log_glp ---------->", "INFO ServicioBase --> cerrar(): Cerrando conexion con la base de datos SQLITE");
	}

}
