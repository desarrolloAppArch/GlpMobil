package ec.gob.arch.glpmobil.utils;

public interface MensajeError {
	 
	public static String LOGIN_USUARIO_CLAVE_NULOS ="Ingrese usuario y contraseña";
	public static String LOGIN_CLAVE_INCORRECTA ="Contraseña incorrecta";
	public static String LOGIN_NO_TIENE_PERMISOS ="Usuario NO tiene permisos para esta aplicación";
	public static String LOGIN_USUARIO_NO_EXISTE ="EL usuario NO existe o se encuentra INACTIVO";
	public static String REGISTRO_FALLO ="Usuario no pudo ser registrado. ";

	public static String REGISTRO_TIPO_SUJETO_NULL ="Seleccione el tipo de sujeto que desea registrar";
	public static String REGISTRO_RUC_SUJETO_NULL ="Ingrese el RUC del sujeto para realizar la búsqueda";
	public static String REGISTRO_CORREO_NO_IGUAL ="Los correos ingresados no coinciden";
	public static String REGISTRO_CORREO_NULL ="Los correos son campos obligatorios";
	public static String REGISTRO_USUARIO_NO_AUTORIZADO ="El sujeto de control no se puede registrar, ya que se encuentra en estado NO AUTORIZADO";

	public static String CONEXION_SERVIDOR_NULL ="Error al intentar conectarse con el servidor, intente nuevamente si el problema continua reportar a la mesa de ayuda de ARCH.   ";
	public static String CONEXION_NULL ="Compruebe su conexión de Internet para poder realizar esta acción";
	public static String LOGIN_CONEXION_NULL ="Compruebe su conexión de Internet para poder logearse en línea";
	public static String LOGIN_FALLO_CLAVE_NO_COINCIDE ="La clave no coincide con la de base local del dispositivo, error al intentar validar la clave en línea con la ARCH. Si el problema continua reportar a la mesa de ayuda de ARCH";
	public static String LOGIN_FALLO_OBLIGATORIO_LOGIN_EN_LINEA ="Usuario a registrarse por primera vez en este dispositivo, compruebe su conexión de Internet y/o que los servicios de la ARCH se encuentren disponibles";
	public static String WEB_SERVICE_ERROR_SERVIDOR ="Error en el servidor, reportar a la mesa de ayuda de ARCH. ";
	public static String WEB_SERVICE_ERROR_APP ="Error al procesar en el dispositivo, cierre la app y vuelva a intentarlo. ";
	public static String ERROR_BASE_MOVIL ="No se pudo escribir ni leer información en el móvil, comprobar que disponga de espacio en memoria en su dispositivo. ";
	public static String PROBLEMAS_CONSULTAR_BASE ="No se a podido realizar la consulta en la base de datos, reportar a la mesa de ayuda de ARCH";

	public static String VENTA_NO_ACTUALIZADA ="Venta no actualizada";
    public static String VENTA_NO_ACTUALIZADA_HORA = "Venta no actualizada porque excedió el límite de tiempo de 3 minutos";
    public static String VENTA_NO_ACTUALIZADA_FECHA = "Venta no actualizada porque no es la misma fecha de la venta";

	public static String VENTA_IDENTIFICACION_NULL ="Digitar o escanear la identificación";

	//SINCRONIZA CUPO

	public static String HISTORIAL_SINCRONIZA_ERROR ="La actualización NO fue realizada";
	public static String HISTORIAL_SINCRONIZA_VENTA_LLENA ="Para actualizar primero debe enviar las ventas pendientes";
    public static String CONNEXION_OK_SIN_RESULTADOS ="Consulta se realizó exitosamente en la ARCH, no hay cupos pendientes para descargar a la fecha actual";
	public static String HISTORIAL_SINCRONIZA_VENTA_LLENA_OTRO_USUARIO ="Para actualizar primero debe enviar las ventas pendientes del usuario: ";
	public static String SUJETO_DE_CONTROL_INACTIVO ="El sujeto de control no puede actualizar cupos, se encuentra en estado INACTIVO";
	public static String SUJETO_DE_CONTROL_NO_TIENE_ASIGNADO_BARRIO ="El sujeto de control no puede actualizar cupos, el Depósito no tiene asignado ningún Barrio";
	public static String SUJETO_DE_CONTROL_NO_TIENE_ASIGNADO_NINGUN_DEPOSITO="El sujeto de control no puede actualizar cupos, no tiene asignado ningún Depósito";
	public static String TRANSPORTE_VINCULADO_AL_SUJETO_DE_CONTROL_INACTIVO_="El sujeto de control al que está vinculado el transporte se encuentra en estado INACTIVO";

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
	public static String VENTA_ESCANEO_INVALIDO ="Error al escanear, intente nuevamente";

	public static String CLAVES_OBLIGATORIAS ="Ingresar la nueva clave y repetir la misma";
	public static String CLAVES_NO_COINCIDEN ="Las claves ingresadas no coinciden, vuelva a digitar";
	public static String CLAVES_MINIMO_CINCO_CARACTERES ="La nueva clave debe contener al menos 5 caracteres";

	public static String PERMISOS_TELEFONO ="Haz negado el permiso para realizar y gestionar llamadas, el mismo que permitirá el envío de ventas de GLP. Habilite permiso en AJUSTES --> APLICACIONES --> Buscar la app --> PERMISOS --> TELÉFONO";

	public static String ENVIO_VENTAS_EXISTE_PROCESO = "Ya existe un proceso de envío de ventas en ejecución, espere unos minutos para que finalice el proceso";
}
