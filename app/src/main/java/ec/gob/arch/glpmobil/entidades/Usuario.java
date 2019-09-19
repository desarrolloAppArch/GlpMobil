package ec.gob.arch.glpmobil.entidades;

public class Usuario {
	
	private int id_sqlite;
	private String id;
	private String nombre;
	private String clave;
	private String ruc;
	private String correo;
	
	
	public int getId_sqlite() {
		return id_sqlite;
	}
	public void setId_sqlite(int id_sqlite) {
		this.id_sqlite = id_sqlite;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	
}
