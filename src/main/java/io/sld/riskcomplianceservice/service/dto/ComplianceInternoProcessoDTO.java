package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.ComplianceInternoProcesso} entity.
 */
public class ComplianceInternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarComplianceInterno;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private ComplianceInternoDTO complianceInterno;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarComplianceInterno() {
        return idnVarComplianceInterno;
    }

    public void setIdnVarComplianceInterno(String idnVarComplianceInterno) {
        this.idnVarComplianceInterno = idnVarComplianceInterno;
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

    public ComplianceInternoDTO getComplianceInterno() {
        return complianceInterno;
    }

    public void setComplianceInterno(ComplianceInternoDTO complianceInterno) {
        this.complianceInterno = complianceInterno;
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
        if (!(o instanceof ComplianceInternoProcessoDTO)) {
            return false;
        }

        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = (ComplianceInternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, complianceInternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceInternoProcessoDTO{" +
            "id=" + getId() +
            ", idnVarComplianceInterno='" + getIdnVarComplianceInterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", complianceInterno=" + getComplianceInterno() +
            ", processo=" + getProcesso() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
