package ec.gob.arch.glpmobil.entidades;

public class VwPersonaAutorizada {

    private Integer idSqlite;
    private Integer codigo;
    private Integer hogCodigo;
    private String apellidoNombre;
    private String numeroDocumento;
    private String fechaEmisionDocumentoAnio;
    private String fechaEmisionDocumentoMes;
    private String fechaEmisionDocumentoDia;
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

    public String getFechaEmisionDocumentoAnio() {
        return fechaEmisionDocumentoAnio;
    }

    public void setFechaEmisionDocumentoAnio(String fechaEmisionDocumentoAnio) {
        this.fechaEmisionDocumentoAnio = fechaEmisionDocumentoAnio;
    }

    public String getFechaEmisionDocumentoMes() {
        return fechaEmisionDocumentoMes;
    }

    public void setFechaEmisionDocumentoMes(String fechaEmisionDocumentoMes) {
        this.fechaEmisionDocumentoMes = fechaEmisionDocumentoMes;
    }

    public String getFechaEmisionDocumentoDia() {
        return fechaEmisionDocumentoDia;
    }

    public void setFechaEmisionDocumentoDia(String fechaEmisionDocumentoDia) {
        this.fechaEmisionDocumentoDia = fechaEmisionDocumentoDia;
    }
}
