package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.Funcionario} entity.
 */
public class FuncionarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarFuncionario;

    @NotNull
    private String nVarNome;

    @NotNull
    private String nVarEmail;

    private String nVarDescricao;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String idnvarUsuario;

    private EmpresaDTO empresa;

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

    public String getnVarNome() {
        return nVarNome;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarEmail() {
        return nVarEmail;
    }

    public void setnVarEmail(String nVarEmail) {
        this.nVarEmail = nVarEmail;
    }

    public String getnVarDescricao() {
        return nVarDescricao;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarEmpresa() {
        return idnVarEmpresa;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return idnvarUsuario;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
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
        if (!(o instanceof FuncionarioDTO)) {
            return false;
        }

        FuncionarioDTO funcionarioDTO = (FuncionarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, funcionarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioDTO{" +
            "id=" + getId() +
            ", idnVarFuncionario='" + getIdnVarFuncionario() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarEmail='" + getnVarEmail() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
