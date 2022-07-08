package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.FornecedorExternoProcesso} entity.
 */
public class FornecedorExternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarFornecedorExterno;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private FornecedorExternoDTO fornecedorExterno;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarFornecedorExterno() {
        return idnVarFornecedorExterno;
    }

    public void setIdnVarFornecedorExterno(String idnVarFornecedorExterno) {
        this.idnVarFornecedorExterno = idnVarFornecedorExterno;
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

    public FornecedorExternoDTO getFornecedorExterno() {
        return fornecedorExterno;
    }

    public void setFornecedorExterno(FornecedorExternoDTO fornecedorExterno) {
        this.fornecedorExterno = fornecedorExterno;
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
        if (!(o instanceof FornecedorExternoProcessoDTO)) {
            return false;
        }

        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = (FornecedorExternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fornecedorExternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorExternoProcessoDTO{" +
            "id=" + getId() +
            ", idnVarFornecedorExterno='" + getIdnVarFornecedorExterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", fornecedorExterno=" + getFornecedorExterno() +
            ", processo=" + getProcesso() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
