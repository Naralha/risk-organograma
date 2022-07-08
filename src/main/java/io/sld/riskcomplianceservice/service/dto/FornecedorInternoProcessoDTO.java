package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.FornecedorInternoProcesso} entity.
 */
public class FornecedorInternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private OrganogramaDTO organograma;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarOrganograma() {
        return idnVarOrganograma;
    }

    public void setIdnVarOrganograma(String idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
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

    public OrganogramaDTO getOrganograma() {
        return organograma;
    }

    public void setOrganograma(OrganogramaDTO organograma) {
        this.organograma = organograma;
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
        if (!(o instanceof FornecedorInternoProcessoDTO)) {
            return false;
        }

        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = (FornecedorInternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fornecedorInternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorInternoProcessoDTO{" +
            "id=" + getId() +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", organograma=" + getOrganograma() +
            ", processo=" + getProcesso() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
