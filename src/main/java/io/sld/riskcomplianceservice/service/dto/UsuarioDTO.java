package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.Usuario} entity.
 */
public class UsuarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarUsuario;

    @NotNull
    private String nVarNome;

    private String idnVarEmpresa;

    private String idnVarUsuarioCadastro;

    @NotNull
    private String nVarSenha;

    private EmpresaDTO empresa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarUsuario() {
        return idnVarUsuario;
    }

    public void setIdnVarUsuario(String idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
    }

    public String getnVarNome() {
        return nVarNome;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getIdnVarEmpresa() {
        return idnVarEmpresa;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnVarUsuarioCadastro() {
        return idnVarUsuarioCadastro;
    }

    public void setIdnVarUsuarioCadastro(String idnVarUsuarioCadastro) {
        this.idnVarUsuarioCadastro = idnVarUsuarioCadastro;
    }

    public String getnVarSenha() {
        return nVarSenha;
    }

    public void setnVarSenha(String nVarSenha) {
        this.nVarSenha = nVarSenha;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioDTO)) {
            return false;
        }

        UsuarioDTO usuarioDTO = (UsuarioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioDTO{" +
            "id=" + getId() +
            ", idnVarUsuario='" + getIdnVarUsuario() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnVarUsuarioCadastro='" + getIdnVarUsuarioCadastro() + "'" +
            ", nVarSenha='" + getnVarSenha() + "'" +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
