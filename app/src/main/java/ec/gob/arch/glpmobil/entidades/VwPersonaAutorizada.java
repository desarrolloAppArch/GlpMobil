package ec.gob.arch.glpmobil.entidades;

public class VwPersonaAutorizada {

    private Integer idSqlite;
    private Integer codigo;
    private Integer hogCodigo;
    private String apellidoNombre;
    private String numeroDocumento;
    private Integer fechaEmisionDocumentoAnio;
    private Integer fechaEmisionDocumentoMes;
    private Integer fechaEmisionDocumentoDia;
    private Integer permitirDigitacionIden;

    public VwPersonaAutorizada() {
    }

    public Integer getIdSqlite() {
        return idSqlite;
    }

    public void setIdSqlite(Integer idSqlite) {
        this.idSqlite = idSqlite;
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

    public String getApellidoNombre() {
        return apellidoNombre;
    }

    public void setApellidoNombre(String apellidoNombre) {
        this.apellidoNombre = apellidoNombre;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Integer getPermitirDigitacionIden() {
        return permitirDigitacionIden;
    }

    public void setPermitirDigitacionIden(Integer permitirDigitacionIden) {
        this.permitirDigitacionIden = permitirDigitacionIden;
    }

    public Integer getFechaEmisionDocumentoAnio() {
        return fechaEmisionDocumentoAnio;
    }

    public void setFechaEmisionDocumentoAnio(Integer fechaEmisionDocumentoAnio) {
        this.fechaEmisionDocumentoAnio = fechaEmisionDocumentoAnio;
    }

    public Integer getFechaEmisionDocumentoMes() {
        return fechaEmisionDocumentoMes;
    }

    public void setFechaEmisionDocumentoMes(Integer fechaEmisionDocumentoMes) {
        this.fechaEmisionDocumentoMes = fechaEmisionDocumentoMes;
    }

    public Integer getFechaEmisionDocumentoDia() {
        return fechaEmisionDocumentoDia;
    }

    public void setFechaEmisionDocumentoDia(Integer fechaEmisionDocumentoDia) {
        this.fechaEmisionDocumentoDia = fechaEmisionDocumentoDia;
    }

}
