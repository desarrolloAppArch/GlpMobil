package ec.gob.arch.glpmobil.entidades;

import java.io.Serializable;
import java.util.List;

public class VwCupoHogar implements Serializable {
    private Integer idSqlite;
    private Integer cmhAnio;
    private Integer cmhCodigo;
    private Integer cmhDisponible;
    private Integer cmhMes;
    private String disIdentifica;
    private Integer hogCodigo;
    private List<VwPersonaAutorizada> lsPersonaAutorizada;
    private String codigoRespuesta;

    public VwCupoHogar() {
    }

    public Integer getIdSqlite() {
        return idSqlite;
    }

    public void setIdSqlite(Integer idSqlite) {
        this.idSqlite = idSqlite;
    }

    public Integer getCmhAnio() {
        return cmhAnio;
    }

    public void setCmhAnio(Integer cmhAnio) {
        this.cmhAnio = cmhAnio;
    }

    public Integer getCmhCodigo() {
        return cmhCodigo;
    }

    public void setCmhCodigo(Integer cmhCodigo) {
        this.cmhCodigo = cmhCodigo;
    }

    public Integer getCmhDisponible() {
        return cmhDisponible;
    }

    public void setCmhDisponible(Integer cmhDisponible) {
        this.cmhDisponible = cmhDisponible;
    }

    public Integer getCmhMes() {
        return cmhMes;
    }

    public void setCmhMes(Integer cmhMes) {
        this.cmhMes = cmhMes;
    }

    public String getDisIdentifica() {
        return disIdentifica;
    }

    public void setDisIdentifica(String disIdentifica) {
        this.disIdentifica = disIdentifica;
    }

    public Integer getHogCodigo() {
        return hogCodigo;
    }

    public void setHogCodigo(Integer hogCodigo) {
        this.hogCodigo = hogCodigo;
    }

    public List<VwPersonaAutorizada> getLsPersonaAutorizada() {
        return lsPersonaAutorizada;
    }

    public void setLsPersonaAutorizada(List<VwPersonaAutorizada> lsPersonaAutorizada) {
        this.lsPersonaAutorizada = lsPersonaAutorizada;
    }

    public String getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(String codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
}
