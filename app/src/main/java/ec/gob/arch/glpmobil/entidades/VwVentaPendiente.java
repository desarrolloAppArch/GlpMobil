package ec.gob.arch.glpmobil.entidades;

public class VwVentaPendiente {
    private String fecha_venta;
    private Integer numero_registro;
    private String usuario_venta;
    private Integer numero_cilindros;

    public Integer getNumero_cilindros() {
        return numero_cilindros;
    }

    public void setNumero_cilindros(Integer numero_cilindros) {
        this.numero_cilindros = numero_cilindros;
    }



    public String getFecha_venta() {
        return fecha_venta;
    }

    public void setFecha_venta(String fecha_venta) {
        this.fecha_venta = fecha_venta;
    }

    public Integer getNumero_registro() {
        return numero_registro;
    }

    public void setNumero_registro(Integer numero_registro) {
        this.numero_registro = numero_registro;
    }

    public String getUsuario_venta() {
        return usuario_venta;
    }

    public void setUsuario_venta(String usuario_venta) {
        this.usuario_venta = usuario_venta;
    }
}
