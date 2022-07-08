package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.ClienteExterno} entity.
 */
public class ClienteExternoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarClienteExterno;

    @NotNull
    private String nVarNome;

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

    public String getIdnVarClienteExterno() {
        return idnVarClienteExterno;
    }

    public void setIdnVarClienteExterno(String idnVarClienteExterno) {
        this.idnVarClienteExterno = idnVarClienteExterno;
    }

    public String getnVarNome() {
        return nVarNome;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
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
        if (!(o instanceof ClienteExternoDTO)) {
            return false;
        }

        ClienteExternoDTO clienteExternoDTO = (ClienteExternoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteExternoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteExternoDTO{" +
            "id=" + getId() +
            ", idnVarClienteExterno='" + getIdnVarClienteExterno() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
