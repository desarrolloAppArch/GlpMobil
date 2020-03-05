package ec.gob.arch.glpmobil.constantes;

/**
 * @author soraya.matute
 */
public interface ConstantesGenerales {
		
	public final String CODIGO_TIPO_TRANSPORTE_GLP = "58";
	public final String CODIGO_TIPO_DEPOSITO_GLP = "57";

	public final String CODIGO_RESPUESTA_LOGEADO_EXITOSAMENTE = "000";
	public final String CODIGO_RESPUESTA_NO_EXISTE_USUARIO_O_INACTIVO = "001";
	public final String CODIGO_RESPUESTA_NO_COINCIDE_CLAVE = "002";
	public final String CODIGO_RESPUESTA_NO_TIENE_PERMISOS = "003";

	public final String CODIGO_RESPUESTA_USUARIO_REGISTRADO_EXISTOSAMENTE = "000";
	public final String CODIGO_RESPUESTA_USUARIO_YA_EXISTE = "001";

	public static final int CODIGO_PERMISOS_CAMARA = 1, CODIGO_INTENT = 2;
	//Respuesta de usuarios
	public final String CODIGO_RESPUESTA_CLAVE_ACTUALIZADA_EXISTOSAMENTE = "000";
	public final String CODIGO_RESPUESTA_USUARIO_NO_ENCONTRADO = "001";
	public final String CODIGO_RESPUESTA_NO_TIENE_PERMISOS_RESETEO = "002";
	public final String CODIGO_RESPUESTA_ERROR_SERVIDOR = "003";;
//Cupos respuestas de cupos
	public final String CODIGO_RESPUESTA_CUPOS_ENCONTRADOS = "001";
	public final String CODIGO_RESPUESTA_CUPOS_NO_ENCONTRADOS = "002";
	public final String CODIGO_RESPUESTA_ERROR_SUJETO_ESTADO_INACTIVO = "004";
	public final String CODIGO_RESPUESTA_ERROR_SUJETO_NO_TIENE_BARRIO_ASIGNADO = "005";
	public final String CODIGO_RESPUESTA_ERROR_SUJETO_NO_TIENE_DEPOSITO_ASIGNADO = "006";
	public final String CODIGO_RESPUESTA_ERROR_DEPOSITO_VINCULADO_INACTIVO = "007";



//Respuestas del envio de ventas
	public final String CODIGO_RESPUESTA_REGISTRO_EXISTOSO_VENTAS = "000";
	public final String CODIGO_RESPUESTA_REGISTRO_VENTAS_EXISTE_PROCESO = "001";


	public final String TITULO_CABECERA = "Usuario: ";
	public final int CODIGO_PERMITIR_DIGITACION = 0;

	public final String TITULO_GENERAL_PROGRESS_DIALOG = "Proceso En Ejecución";
	public final String TITULO_BUSCANDO_SUJETO_PROGRESS_DIALOG = "Buscando Sujeto de Control en la ARCH";
	public final String TITULO_REGISTRO_USUARIO_PROGRESS_DIALOG = "Registrando usuario en la ARCH";
	public final String TITULO_CUPOS_PROGRESS_DIALOG = "Descargando Cupos de la ARCH";
	public final String TITULO_VENTAS_PROGRESS_DIALOG = "Enviando Ventas a la ARCH";
	public final String MENSAJE_PROGRESS_DIALOG_ESPERA = "Por favor, espera unos minutos!   No interrumpir.";
	public final String MENSAJE_PROGRESS_DIALOG_ESPERA_GUARDANDO_LOCALMENTE = "Por favor, espera unos minutos!    Guardando en la base de datos local del dispositivo.";

	public final Integer CODIGO_HISTORIAL_ESTADO_EXITOSO = 1;
	public final Integer CODIGO_HISTORIAL_ESTADO_FALLIDO = 0;

	public final String CODIGO_ESTADO_EQUIVALENTE_AUTORIZADO = "AUTORIZADO";

}
