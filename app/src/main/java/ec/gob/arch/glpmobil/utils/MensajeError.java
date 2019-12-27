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
	public static String LOGIN_FALLO_CLAVE_NO_COINCIDE ="La clave no coincide. Compruebe su conexión de Internet y/o que los servicios de la ARCH se encuentren disponibles para validar si hubo reseteo clave";
	public static String LOGIN_FALLO_OBLIGATORIO_LOGIN_EN_LINEA ="Usuario a registrarse por primera vez en este dispositivo, compruebe su conexión de Internet y/o que los servicios de la ARCH se encuentren disponibles";
	public static String WEB_SERVICE_ERROR_SERVIDOR ="Error en el servidor, reportar a la mesa de ayuda de ARCH";

	public static String VENTA_NO_ACTUALIZADA ="Venta no actualizada";
    public static String VENTA_NO_ACTUALIZADA_HORA = "Venta no actualizada porque excedió el límite de tiempo de 3 minutos";
    public static String VENTA_NO_ACTUALIZADA_FECHA = "Venta no actualizada porque no es la misma fecha de la venta";

	public static String VENTA_IDENTIFICACION_NULL ="Digitar o escanear la identificación";

	//SINCRONIZA CUPO

	public static String HISTORIAL_SINCRONIZA_ERROR ="La actualización NO fue realizada";
	public static String HISTORIAL_SINCRONIZA_VENTA_LLENA ="Para actualizar primero debe enviar las ventas pendientes";
    public static String SIN_RESULTADOS ="No hay cupos pendientes para descargar a la fecha actual";
    public static String SIN_RESPUESTA_WS ="El servicio No se encuentra disponible para descargas, comunicarse con soporte ARCH Telf. 023996500 ext. 5100";
	public static String HISTORIAL_SINCRONIZA_VENTA_LLENA_OTRO_USUARIO ="Para actualizar primero debe enviar las ventas pendientes del usuario: ";

	public static String VENTA_ACTIVAR_CAMARA_PARA_ESTA_APP ="Es necesario que permita que esta app acceda a la cámara, para la venta de GLP";
	public static String VENTA_TIEMPO_EDICION_PERMITIDO ="El tiempo de edición a excedido";
	public static String VENTA_FECHA_NULL ="Fecha de expedición es obligatoria";
	public static String VENTA_FECHA_NO_COINCIDE ="Fecha de expedición no coincide con la registrada en la ARCH";
	public static String VENTA_NUMERO_CILINDROS_NULL ="Ingrese el número de cilindros vendidos";
	public static String VENTA_NUMERO_CILINDROS_EXCEDE_PERMITIDO ="Número de cilindros excede el cupo permitido";
	public static String VENTA_NUMERO_CILINDROS_MAYOR_A_CERO ="Número de cilindros debe ser mayor a 0";
	public static String VENTA_NO_REGISTRADA ="Venta no ha podido ser registrada, reportar a la mesa de ayuda de ARCH";
	public static String CUPO_NO_REGISTRADO ="Cupo no ha podido ser actualizado, reportar a la mesa de ayuda de ARCH";
	public static String VENTA_NO_ROLLBACK ="Problemas al realizar el ROLLBACK, reportar a la mesa de ayuda de ARCH";

	public static String CLAVES_OBLIGATORIAS ="Ingresar la nueva clave y repetir la misma";
	public static String CLAVES_NO_COINCIDEN ="Las claves ingresadas no coinciden, vuelva a digitar";
	public static String CLAVES_MINIMO_CINCO_CARACTERES ="La nueva clave debe contener al menos 5 caracteres";
}
