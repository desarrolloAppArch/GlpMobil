package ec.gob.arch.glpmobil.utils;

public enum TituloError {
	
	TITULO_ERROR("ERROR"),	
	TITULO_ERROR_ESPECIFICO("DESCRIPCION ESPECIFICO");	
	
	/**
	 * Varible titulo
	 */
	private String titulo;
	
	/**
	 * Constructor de TituloError 
	 * @param titulo
	 */
	private TituloError(String titulo){
		this.titulo=titulo;
	}
	
	/**
	 * Método get de la varibale título
	 * @return
	 */
	public String getTitulo(){
		return titulo;
	}
	
}
