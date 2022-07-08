package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.ComplianceInterno} entity.
 */
public class ComplianceInternoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarComplianteInterno;

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

    public String getIdnVarComplianteInterno() {
        return idnVarComplianteInterno;
    }

    public void setIdnVarComplianteInterno(String idnVarComplianteInterno) {
        this.idnVarComplianteInterno = idnVarComplianteInterno;
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
        if (!(o instanceof ComplianceInternoDTO)) {
            return false;
        }

        ComplianceInternoDTO complianceInternoDTO = (ComplianceInternoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, complianceInternoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceInternoDTO{" +
            "id=" + getId() +
            ", idnVarComplianteInterno='" + getIdnVarComplianteInterno() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
