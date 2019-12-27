package ec.gob.arch.glpmobil.utils;

public interface MensajeInfo {
	
	public static String LOGIN_USUARIO_ENCONTRADO ="Usuario encontrado";
	public static String SIN_RESULTADOS ="No se encontraron resultados";
	public static String REGISTRO_EXITOSO ="Usuario registrado exitosamente, su clave ha sido enviada a su correo electrónico";
	public static String REGISTRO_FALLO ="Usuario no pudo ser registrado";
	public static String ACTUALIZACION_EXITOSA ="El usuario ya existe, no es posible volver a registrarlo";
	public static String LOGIN_USUARIO_REGISTRADO ="Usuario registrado exitosamente en la base del telefono";
	public static String LOGIN_O ="Usuario encontrado";

	public static String VENTA_ACTUALIZADA_OK ="Venta actualizada exitosamente";

	//SINCRONIZA CUPO

	public static String HISTORIAL_SINCRONIZA_OK ="La actualización fue realizada exitosamente";

	public static String BASE_CUPOS_VACIA ="La base de datos se encuentra vacía, para vender debe descargar los cupos de la ARCH";
	public static String VENTA_IDENTIFICACION_NO_ENCONTRADA ="Identificación no existe en la base de datos de cupos descargada";
	public static String VENTA_HOGAR_SIN_CUPO_DISPONIBLE ="El hogar no tiene cupo disponible: ";
	public static String VENTA_REGISTRADA_EXITOSAMENTE ="Venta registrada exitosamente";
	public static String VENTA_SIN_EDICION = "Venta no actualizada, la cantidad de cilindros no a variado";
	public static String PERSONA_NO_AUTORIZADA_DIGITAR ="No se autoriza la digitación, debe escanear el documento de identidad";


	public static String CLAVE_ACTUALIZADA_EXITOSAMENTE ="Clave actualizada exitosamente";
	public static String USUARIO_NO_ENCONTRADO ="El usuario no se encontró en la base de la ARCH";

	public static String RESETEO_USUARIO_NULL ="Ingrese el usuario del que desea recuperar la clave";
	public static String CLAVE_RESETEADA_EXITOSAMENTE ="Su nueva clave a sido enviada a su correo electrónico: ";
	public static String USUARIO_NO_EXISTE ="Usuario no se encuentra registrado en la ARCH";
	public static String USUARIO_NO_TIENE_PERMISOS ="Usuario no tiene permisos para esta app";

	public static String VENTA_HOGAR_NO_EXISTE ="El hogar no existe, verifique que haya descargado cupos luego de su último envío de ventas";

}
