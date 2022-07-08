package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.FuncionarioOrganograma} entity.
 */
public class FuncionarioOrganogramaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarFuncionario;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnvarUsuario;

    private FuncionarioDTO funcionario;

    private OrganogramaDTO organograma;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarFuncionario() {
        return idnVarFuncionario;
    }

    public void setIdnVarFuncionario(String idnVarFuncionario) {
        this.idnVarFuncionario = idnVarFuncionario;
    }

    public String getIdnVarOrganograma() {
        return idnVarOrganograma;
    }

    public void setIdnVarOrganograma(String idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
    }

    public String getIdnvarUsuario() {
        return idnvarUsuario;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public FuncionarioDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioDTO funcionario) {
        this.funcionario = funcionario;
    }

    public OrganogramaDTO getOrganograma() {
        return organograma;
    }

    public void setOrganograma(OrganogramaDTO organograma) {
        this.organograma = organograma;
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
        if (!(o instanceof FuncionarioOrganogramaDTO)) {
            return false;
        }

        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = (FuncionarioOrganogramaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, funcionarioOrganogramaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioOrganogramaDTO{" +
            "id=" + getId() +
            ", idnVarFuncionario='" + getIdnVarFuncionario() + "'" +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", funcionario=" + getFuncionario() +
            ", organograma=" + getOrganograma() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
