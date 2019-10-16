package ec.gob.arch.glpmobil.entidades;

public class HistorialSincronizacion {

    private int id_sqlite;
    private String id;
    private String fecha_sincroniza;
    private String usuario;
    private Integer numero_registros;
    private Integer estado; //1 exitoso, 0 fallido
    private String accion;

    public int getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(int id_sqlite) {
        this.id_sqlite = id_sqlite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha_sincroniza() {
        return fecha_sincroniza;
    }

    public void setFecha_sincroniza(String fecha_sincroniza) {
        this.fecha_sincroniza = fecha_sincroniza;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getNumero_registros() {
        return numero_registros;
    }

    public void setNumero_registros(Integer numero_registros) {
        this.numero_registros = numero_registros;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }
}
