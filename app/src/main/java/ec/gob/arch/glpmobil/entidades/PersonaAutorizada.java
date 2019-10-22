package ec.gob.arch.glpmobil.entidades;

import java.util.Date;

public class PersonaAutorizada {

    private Integer codigo;
    private Integer hogCodigo;
    private String perApellidoNombre;
    private String perNumeroDocumento;
    private String perFechaEmisionDocumento;
    private Integer perPermitirDigitacionIden;
    private String perParroquia;

    public PersonaAutorizada() {
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getHogCodigo() {
        return hogCodigo;
    }

    public void setHogCodigo(Integer hogCodigo) {
        this.hogCodigo = hogCodigo;
    }

    public String getPerApellidoNombre() {
        return perApellidoNombre;
    }

    public void setPerApellidoNombre(String perApellidoNombre) {
        this.perApellidoNombre = perApellidoNombre;
    }

    public String getPerNumeroDocumento() {
        return perNumeroDocumento;
    }

    public void setPerNumeroDocumento(String perNumeroDocumento) {
        this.perNumeroDocumento = perNumeroDocumento;
    }

    public Integer getPerPermitirDigitacionIden() {
        return perPermitirDigitacionIden;
    }

    public void setPerPermitirDigitacionIden(Integer perPermitirDigitacionIden) {
        this.perPermitirDigitacionIden = perPermitirDigitacionIden;
    }

    public String getPerParroquia() {
        return perParroquia;
    }

    public void setPerParroquia(String perParroquia) {
        this.perParroquia = perParroquia;
    }


    public String getPerFechaEmisionDocumento() {
        return perFechaEmisionDocumento;
    }

    public void setPerFechaEmisionDocumento(String perFechaEmisionDocumento) {
        this.perFechaEmisionDocumento = perFechaEmisionDocumento;
    }
}
