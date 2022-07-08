package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.ClienteExternoProcesso} entity.
 */
public class ClienteExternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarClienteExterno;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private ClienteExternoDTO clienteExterno;

    private ProcessoDTO processo;

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

    public ClienteExternoDTO getClienteExterno() {
        return clienteExterno;
    }

    public void setClienteExterno(ClienteExternoDTO clienteExterno) {
        this.clienteExterno = clienteExterno;
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
        if (!(o instanceof ClienteExternoProcessoDTO)) {
            return false;
        }

        ClienteExternoProcessoDTO clienteExternoProcessoDTO = (ClienteExternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteExternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteExternoProcessoDTO{" +
            "id=" + getId() +
            ", idnVarClienteExterno='" + getIdnVarClienteExterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", clienteExterno=" + getClienteExterno() +
            ", processo=" + getProcesso() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
