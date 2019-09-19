package ec.gob.arch.glpmobil.entidades;

import java.util.List;

public class CupoHogar {

    private Integer cmhAnio;

    private Integer cmhCodigo;
    private Integer cmhCupo;
    private Integer cmhDisponible;
    private Integer cmhMes;
    private Integer cmhOcupado;
    private Integer disCodigo;
    private Integer disEstado;
    private String disIdentifica;
    private Integer hogCodigo;
    private Integer hogNumeroIntegrantes;
    private String hogParroquia;
    private String hogNumero;
    private String hogRegional;
    private String disRegional;
    private List<PersonaAutorizada> lsPersonaAutorizada;

    public CupoHogar() {
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

    public Integer getCmhCupo() {
        return cmhCupo;
    }

    public void setCmhCupo(Integer cmhCupo) {
        this.cmhCupo = cmhCupo;
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

    public Integer getCmhOcupado() {
        return cmhOcupado;
    }

    public void setCmhOcupado(Integer cmhOcupado) {
        this.cmhOcupado = cmhOcupado;
    }

    public Integer getDisCodigo() {
        return disCodigo;
    }

    public void setDisCodigo(Integer disCodigo) {
        this.disCodigo = disCodigo;
    }

    public Integer getDisEstado() {
        return disEstado;
    }

    public void setDisEstado(Integer disEstado) {
        this.disEstado = disEstado;
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

    public Integer getHogNumeroIntegrantes() {
        return hogNumeroIntegrantes;
    }

    public void setHogNumeroIntegrantes(Integer hogNumeroIntegrantes) {
        this.hogNumeroIntegrantes = hogNumeroIntegrantes;
    }

    public String getHogParroquia() {
        return hogParroquia;
    }

    public void setHogParroquia(String hogParroquia) {
        this.hogParroquia = hogParroquia;
    }

    public String getHogNumero() {
        return hogNumero;
    }

    public void setHogNumero(String hogNumero) {
        this.hogNumero = hogNumero;
    }

    public String getHogRegional() {
        return hogRegional;
    }

    public void setHogRegional(String hogRegional) {
        this.hogRegional = hogRegional;
    }

    public String getDisRegional() {
        return disRegional;
    }

    public void setDisRegional(String disRegional) {
        this.disRegional = disRegional;
    }

    public List<PersonaAutorizada> getLsPersonaAutorizada() {
        return lsPersonaAutorizada;
    }

    public void setLsPersonaAutorizada(List<PersonaAutorizada> lsPersonaAutorizada) {
        this.lsPersonaAutorizada = lsPersonaAutorizada;
    }
}
