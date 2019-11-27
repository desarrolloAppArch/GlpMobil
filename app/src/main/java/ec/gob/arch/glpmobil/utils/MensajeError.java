package ec.gob.arch.glpmobil.utils;

public interface MensajeError {
	 
	public static String LOGIN_USUARIO_CLAVE_NULOS ="Ingrese usuario y contraseña";
	public static String LOGIN_CLAVE_INCORRECTA ="Contraseña incorrecta";
	public static String LOGIN_NO_TIENE_PERMISOS ="Usuario NO tiene permisos para esta aplicación";
	public static String LOGIN_USUARIO_NO_EXISTE ="EL usuario NO existe o se encuentra INACTIVO";
	public static String REGISTRO_TIPO_SUJETO_NULL ="Seleccione el tipo de sujeto que desea registrar";
	public static String REGISTRO_RUC_SUJETO_NULL ="Ingrese el RUC del sujeto para realizar la búsqueda";
	public static String REGISTRO_CORREO_NO_IGUAL ="Los correos ingresados no coinciden";
	public static String REGISTRO_CORREO_NULL ="Los correos son campos obligatorios";

	public static String CONEXION_NULL ="Compruebe su conexión de Internet para poder realizar esta acción";
	public static String LOGIN_CONEXION_NULL ="Compruebe su conexión de Internet para poder logearse en línea";

	public static String VENTA_IDENTIFICACION_NULL ="Digitar o escanear la identificación";

	//SINCRONIZA CUPO

	public static String HISTORIAL_SINCRONIZA_ERROR ="La actualización NO fue realizada";
	public static String HISTORIAL_SINCRONIZA_VENTA_LLENA ="Para actualizar primero debe enviar las ventas pendientes";

}
