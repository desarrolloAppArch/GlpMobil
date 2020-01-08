package ec.gob.arch.glpmobil.entidades;

public class HistorialSincronizacion {

    private int id_sqlite;
    private String fecha_sincroniza;
    private String usuario;
    private Integer numero_registros;
    private Integer estado; //1 exitoso, 0 fallido
    private String accion;
    private String descripcionEstado;
    private Integer numero_cilindros;

    public int getId_sqlite(int anInt) {
        return id_sqlite;
    }

    public void setId_sqlite(int id_sqlite) {
        this.id_sqlite = id_sqlite;
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
        if(estado==1){
            setDescripcionEstado("Exitoso");
        }else{
            setDescripcionEstado("Fallido");
        }
        this.estado = estado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcionEstado() {
        if(getEstado()==1){
            setDescripcionEstado("Exitoso");
        }else{
            setDescripcionEstado("Fallido");
        }
        return descripcionEstado;
    }

    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }

    public Integer getNumero_cilindros() {
        return numero_cilindros;
    }

    public void setNumero_cilindros(Integer numero_cilindros) {
        this.numero_cilindros = numero_cilindros;
    }
}
