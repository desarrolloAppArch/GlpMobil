package ec.gob.arch.glpmobil.database;

import ec.gob.arch.glpmobil.constantes.CtCupoHogar;
import ec.gob.arch.glpmobil.constantes.CtPersona;
import ec.gob.arch.glpmobil.constantes.CtUsuario;
import ec.gob.arch.glpmobil.constantes.CtVenta;

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
	public static int VERSION_BASE = 7;
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
				+CtCupoHogar.HOG_NUM_INTEGRANTES+ " INTEGER, "
				+CtCupoHogar.HOG_NUMERO+ " TEXT, "
				+CtCupoHogar.HOG_PARROQUIA+ " TEXT, "
				+CtCupoHogar.MES+" INTEGER );");
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
				+CtPersona.FECHA_EMISION_DOCUMENTO+ " DATE, "
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
				+CtVenta.CODIGOCUPOMES+" INTEGER, "
				+CtVenta.USUARIOVENTA+ " STRING, "
				+CtVenta.USUARIOCOMPRA+ " STRING, "
				+CtVenta.NOMBRECOMPRA+ " STRING, "
				+CtVenta.LATITUD+ " STRING, "
				+CtVenta.LONGITUD+ " STRING, "
				+CtVenta.FECHAVENTA+ " STRING, "
				+CtVenta.FECHAMODIFICACION+ " STRING, "
				+CtVenta.CANTIDAD+ " INTEGER );");
		Log.v("log_glp ---------->", "INFO BaseGlp --> crearTablaVenta()");

	}

}
