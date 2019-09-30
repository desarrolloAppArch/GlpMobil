package ec.gob.arch.glpmobil.entidades;


import java.util.Date;

public class Venta {

    private Integer id_sqlite;
    private Integer codigoCupoMes;
    private String usuarioVenta;
    private String usuarioCompra;
    private String nombreCompra;
    private String sincronizacion;
    private String latitud;
    private String longitud;
    private String fechaVenta;
    private String fechaModificacion;
    private Integer cantidad;

    public Integer getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(Integer id_sqlite) {
        this.id_sqlite = id_sqlite;
    }

    public Integer getCodigoCupoMes() {
        return codigoCupoMes;
    }

    public void setCodigoCupoMes(Integer codigoCupoMes) {
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

    public String getNombreCompra() {
        return nombreCompra;
    }

    public void setNombreCompra(String nombreCompra) {
        this.nombreCompra = nombreCompra;
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
