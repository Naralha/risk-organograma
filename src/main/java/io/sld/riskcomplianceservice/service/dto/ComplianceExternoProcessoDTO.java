package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.ComplianceExternoProcesso} entity.
 */
public class ComplianceExternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarComplianceExterno;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private ComplianceExternoDTO complianceExterno;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarComplianceExterno() {
        return idnVarComplianceExterno;
    }

    public void setIdnVarComplianceExterno(String idnVarComplianceExterno) {
        this.idnVarComplianceExterno = idnVarComplianceExterno;
    }

    public String getIdnVarProcesso() {
        return idnVarProcesso;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdnvarUsuario() {
        return idnvarUsuario;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public ComplianceExternoDTO getComplianceExterno() {
        return complianceExterno;
    }

    public void setComplianceExterno(ComplianceExternoDTO complianceExterno) {
        this.complianceExterno = complianceExterno;
    }

    public ProcessoDTO getProcesso() {
        return processo;
    }

    public void setProcesso(ProcessoDTO processo) {
        this.processo = processo;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplianceExternoProcessoDTO)) {
            return false;
        }

        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = (ComplianceExternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, complianceExternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceExternoProcessoDTO{" +
            "id=" + getId() +
            ", idnVarComplianceExterno='" + getIdnVarComplianceExterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", complianceExterno=" + getComplianceExterno() +
            ", processo=" + getProcesso() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
