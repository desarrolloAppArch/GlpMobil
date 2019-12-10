package ec.gob.arch.glpmobil.entidades;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable {

    private Integer id_sqlite;
    private Integer codigo_cupo_mes;
    private String usuario_venta;
    private String usuario_compra;
    private String nombre_compra;
    private String latitud;
    private String longitud;
    private String fecha_venta;
    private String fecha_modificacion;
    private Integer cantidad;

    //variables temporales solo para mostrar información
    private Integer cupoDisponible;

    @Override
    public String toString() {
        return "VENTA [ id_sqlite:"+id_sqlite
                +"  codigo_cupo_mes:"+codigo_cupo_mes
                +"  usuario_venta:"+usuario_venta
                +"  usuario_compra:"+usuario_compra
                +"  nombre_compra:"+nombre_compra
                +"  latitud:"+latitud
                +"  longitud:"+longitud
                +"  fecha_venta:"+fecha_venta
                +"  fecha_modificacion:"+fecha_modificacion
                +"  cantidad:"+cantidad
                +" ]";
    }

    public Integer getId_sqlite() {
        return id_sqlite;
    }

    public void setId_sqlite(Integer id_sqlite) {
        this.id_sqlite = id_sqlite;
    }

    public Integer getCodigo_cupo_mes() {
        return codigo_cupo_mes;
    }

    public void setCodigo_cupo_mes(Integer codigo_cupo_mes) {
        this.codigo_cupo_mes = codigo_cupo_mes;
    }

    public String getUsuario_venta() {
        return usuario_venta;
    }

    public void setUsuario_venta(String usuario_venta) {
        this.usuario_venta = usuario_venta;
    }

    public String getUsuario_compra() {
        return usuario_compra;
    }

    public void setUsuario_compra(String usuario_compra) {
        this.usuario_compra = usuario_compra;
    }

    public String getNombre_compra() {
        return nombre_compra;
    }

    public void setNombre_compra(String nombre_compra) {
        this.nombre_compra = nombre_compra;
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

    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public String getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(String fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(Integer cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }
}
