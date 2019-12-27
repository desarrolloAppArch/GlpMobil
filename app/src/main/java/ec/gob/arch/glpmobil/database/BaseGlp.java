package ec.gob.arch.glpmobil.database;

import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.constantes.CtPersona;
import ec.gob.arch.glpmobil.constantes.CtUsuario;
import ec.gob.arch.glpmobil.constantes.CtVenta;
import ec.gob.arch.glpmobil.constantes.CtHistorialSincroniza;
import ec.gob.arch.glpmobil.constantes.CtVwVentaPendiente;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author soraya.matute
 *
 */
public class BaseGlp extends SQLiteOpenHelper{
	
	/**
	 * Variables globales de BaseGlp 
	 */
	public static String NOMBRE_BASE = "baseGlp";
	public static int VERSION_BASE = 37;
	public static BaseGlp instanciaBaseGlp;

	
	/**
	 * Constructor
	 * @param context
	 * @author soraya.matute
	 */
	public BaseGlp(Context context) {
		super(context, NOMBRE_BASE, null, VERSION_BASE);
		Log.v("log_glp ---------->", "INFO BaseGlp --> CONSTRUCTOR nombre: "+NOMBRE_BASE+", versión: "+VERSION_BASE );
	}
	
	/**
	 * Método que crea una instancia de la base de datos
	 * @param context
	 * @return
	 * @author soraya.matute
	 */
	public static BaseGlp getInstance(Context context){
		if (instanciaBaseGlp==null) {
			instanciaBaseGlp=new BaseGlp(context);
            Log.v("log_glp ---------->", "INFO BaseGlp --> getInstance(): Creando nueva instancia");

		}else {
            Log.v("log_glp ---------->", "INFO BaseGlp --> getInstance(): Existe instancia no se creo una nueva "+instanciaBaseGlp.toString());
        }
		return instanciaBaseGlp;
	}

	/**
	 * Método que crea la base de datos, se ejecuta una sola vez la primera vez que se instala la app,
	 * si desinstalo la app no se borra la base, es decir, nunca vuelve a entrar al oncreate() a menos que borre la base de datos del teléfono
	 * @author soraya.matute
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
        Log.v("log_glp ---------->", "INFO BaseGlp --> onCreate()");
		crearTablaUsuario(db);
		crearTablaCupoHogar(db);
		crearTablaPersona(db);
		crearTablaVenta(db);
		crearTablaHistorialSincronizacion(db);
		crearViewVwVentaPendiente(db);
		
	}
	
	/**
	 * Método que llama si detecta un cambio de versión en la base de datos
	 * @author soraya.matute
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		Log.v("log_glp ---------->", "INFO BaseGlp --> onUpgrade(): iniciando actualizacion base de datos");
		db.execSQL("DROP TABLE IF EXISTS "+CtUsuario.TABLA_USUARIO);
		db.execSQL("DROP TABLE IF EXISTS "+CtCupoHogar.TABLA_CUPO_HOGAR);
		db.execSQL("DROP TABLE IF EXISTS "+CtPersona.TABLA_PERSONA);
		db.execSQL("DROP TABLE IF EXISTS "+CtVenta.TABLA_VENTA);
		db.execSQL("DROP TABLE IF EXISTS "+CtHistorialSincroniza.TABLA_HISTORIAL);
		db.execSQL("DROP VIEW IF EXISTS "+CtVwVentaPendiente.VIEW_VENTAS_PENDIENTES);
		Log.v("log_glp ---------->", "INFO BaseGlp --> onUpgrade(): Eliminando tablas");
		onCreate(db);
	}
	
	
	/**
	 * Método que permite crear la tabla USUARIO en la base SQLite
	 * @param db
	 * @author soraya.matute
	 */
	public void crearTablaUsuario(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+ CtUsuario.TABLA_USUARIO 
				  +" ("+ CtUsuario.ID_SQLITE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				  +CtUsuario.ID+ " INTEGER, "
				  +CtUsuario.NOMBRE+ " TEXT, "
				  +CtUsuario.CLAVE+ " TEXT, "
				  +CtUsuario.RUC+ " TEXT, "
				  +CtUsuario.CORREO+" TEXT );");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearTablaUsuario()");
		
	}

	/**
	 * Método que permite crear la tabla CUPO_HOGAR en la base SQLite
	 * @param db
	 * @author vanessa.ponce
	 */
	public void crearTablaCupoHogar(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+ CtCupoHogar.TABLA_CUPO_HOGAR
				+" ("+ CtCupoHogar.ID_SQLITE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+CtCupoHogar.ID+ " INTEGER, "
				+CtCupoHogar.CUPO_DISPONIBLE+ " INTEGER, "
				+CtCupoHogar.HOG_CODIGO+ " INTEGER, "
				+CtCupoHogar.ANIO+ " INTEGER, "
				+CtCupoHogar.MES+" INTEGER, "
				+CtCupoHogar.DIS_IDENTIFICA+" TEXT);");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearTablaCupoHogar()");

	}

	/**
	 * Método que permite crear la tabla PERSONA en la base SQLite
	 * @param db
	 * @author vanessa.ponce
	 */
	public void crearTablaPersona(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+ CtPersona.TABLA_PERSONA
				+" ("+ CtPersona.ID_SQLITE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+CtPersona.ID+ " INTEGER, "
				+CtPersona.HOG_CODIGO+ " INTEGER, "
				+CtPersona.NOMBRE+ " TEXT, "
				+CtPersona.IDENTIFICACION+ " TEXT, "
				+CtPersona.FECHA_EMISION_DOCUMENTO_ANIO+ " TEXT, "
				+CtPersona.FECHA_EMISION_DOCUMENTO_MES+ " TEXT, "
				+CtPersona.FECHA_EMISION_DOCUMENTO_DIA+ " TEXT, "
				+CtPersona.PERMITE_DIGITACION_IDEN+" INTEGER );");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearTablaPersona()");

	}

	/**
	 *
	 * Método de permite crear la tabla VENTA en la base SQLite
	 * @param db
	 * @author blanca.yanguicela
	 */
	public void crearTablaVenta(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+ CtVenta.TABLA_VENTA
				+" ("+CtVenta.ID_SQLITE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+CtVenta.CODIGO_CUPO_MES +" INTEGER, "
				+CtVenta.USUARIO_VENTA + " TEXT, "
				+CtVenta.USUARIO_COMPRA + " TEXT, "
				+CtVenta.NOMBRE_COMPRA + " TEXT, "
				+CtVenta.LATITUD+ " TEXT, "
				+CtVenta.LONGITUD+ " TEXT, "
				+CtVenta.FECHA_VENTA + " TEXT, "
				+CtVenta.FECHA_MODIFICACION + " TEXT, "
				+CtVenta.CANTIDAD+ " INTEGER );");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearTablaVenta()");

	}

	/**
	 * Método que permite crear la tabla HistorialSincronizacion en la base SQLite
	 * @param db
	 * @author vanessa.ponce
	 */
	public void crearTablaHistorialSincronizacion(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+ CtHistorialSincroniza.TABLA_HISTORIAL
				+" ("+ CtHistorialSincroniza.ID_SQLITE+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+CtHistorialSincroniza.FECHA_SINCRONIZA+ " TEXT, "
				+CtHistorialSincroniza.USUARIO+ " TEXT, "
				+CtHistorialSincroniza.NUMERO_REGISTROS+ " INTEGER, "
				+CtHistorialSincroniza.ESTADO+ " INTEGER, "
				+CtHistorialSincroniza.ACCION+ " TEXT);");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearTablaHistorialSincronizacion()");

	}

	public void crearViewVwVentaPendiente(SQLiteDatabase db){
		db.execSQL("CREATE VIEW " + CtVwVentaPendiente.VIEW_VENTAS_PENDIENTES +
				" AS SELECT date("+CtVenta.FECHA_VENTA+") AS "+CtVwVentaPendiente.FECHA_VENTA +","+CtVenta.USUARIO_VENTA+" AS "+CtVwVentaPendiente.USUARIO_VENTA+", COUNT ("+CtVenta.USUARIO_VENTA+") AS "+CtVwVentaPendiente.NUMERO_REGISTROS+" FROM "+CtVenta.TABLA_VENTA+" GROUP BY date(" +CtVenta.FECHA_VENTA +"),"+CtVenta.USUARIO_VENTA+";");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearViewVwVentaPendiente()");
	}

}
