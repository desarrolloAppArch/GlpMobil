package ec.gob.arch.glpmobil.utils;

public enum TituloInfo {
	
	TITULO_INFO("INFO"),	
	TITULO_INFO_ESPECIFICO("DESCRIPCION ESPECIFICO");	
	
	/**
	 * Varible titulo
	 */
	private String titulo;
	
	/**
	 * Constructor de TituloInfo 
	 * @param titulo
	 */
	private TituloInfo(String titulo){
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
