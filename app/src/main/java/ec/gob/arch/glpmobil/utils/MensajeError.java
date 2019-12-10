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

	public static String VENTA_NO_ACTUALIZADA ="Venta no actualizada";


	public static String VENTA_IDENTIFICACION_NULL ="Digitar o escanear la identificación";

	//SINCRONIZA CUPO

	public static String HISTORIAL_SINCRONIZA_ERROR ="La actualización NO fue realizada";
	public static String HISTORIAL_SINCRONIZA_VENTA_LLENA ="Para actualizar primero debe enviar las ventas pendientes";
    public static String SIN_RESULTADOS ="No hay cupos pendientes para descargar a la fecha actual";
    public static String SIN_RESPUESTA_WS ="El servicio No se encuentra disponible para descargas, comunicarse con soporte ARCH Telf. 023996500 ext. 5100";


	public static String VENTA_ACTIVAR_CAMARA_PARA_ESTA_APP ="Es necesario que permita que esta app acceda a la cámara, para la venta de GLP";

	public static String VENTA_FECHA_NULL ="Fecha de expedición es obligatoria";
	public static String VENTA_FECHA_NO_COINCIDE ="Ingrese el número de cilindros vendidos";
	public static String VENTA_NUMERO_CILINDROS_NULL ="Ingrese el número de cilindros vendidos";
	public static String VENTA_NUMERO_CILINDROS_EXCEDE_PERMITIDO ="Número de cilindros excede el cupo permitido";
	public static String VENTA_NO_REGISTRADA ="Venta no ha podido ser registrada, reportar error al 023 996 500 Ext. 5100";
	public static String CUPO_NO_REGISTRADO ="Cupo no ha podido ser actualizado, reportar error al 023 996 500 Ext. 5100";
	public static String VENTA_NO_ROLLBACK ="Problemas al realizar el ROLLBACK, reportar error al 023 996 500 Ext. 5100";
}
