package ec.gob.arch.glpmobil.entidades;


import java.util.Date;

public class Ventas {

    private int id_sqlite;
    private Integer codigo;
    private long codigoCupoMes;
    private String usuarioVenta;
    private String usuarioCompra;
    private String sincronizacion;
    private String latitud;
    private String longitud;
    private String fechaVenta;
    private String fechaModificacion;
    private Integer cantidad;

    public int getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(int id_sqlite) {
        this.id_sqlite = id_sqlite;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public long getCodigoCupoMes() {
        return codigoCupoMes;
    }

    public void setCodigoCupoMes(long codigoCupoMes) {
        this.codigoCupoMes = codigoCupoMes;
    }

    public String getUsuarioVenta() {
        return usuarioVenta;
    }

    public void setUsuarioVenta(String usuarioVenta) {
        this.usuarioVenta = usuarioVenta;
    }

    public String getUsuarioCompra() {
        return usuarioCompra;
    }

    public void setUsuarioCompra(String usuarioCompra) {
        this.usuarioCompra = usuarioCompra;
    }

    public String getSincronizacion() {
        return sincronizacion;
    }

    public void setSincronizacion(String sincronizacion) {
        this.sincronizacion = sincronizacion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
