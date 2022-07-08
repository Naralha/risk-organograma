package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.Organograma} entity.
 */
public class OrganogramaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String nVarNome;

    private String nVarDescricao;

    private String idnVarPaiOrganograma;

    @NotNull
    private String idnvarUsuario;

    @NotNull
    private String idnVarRoofTop;

    private EmpresaDTO empresa;

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

    public String getIdnVarEmpresa() {
        return idnVarEmpresa;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
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

    public String getIdnVarPaiOrganograma() {
        return idnVarPaiOrganograma;
    }

    public void setIdnVarPaiOrganograma(String idnVarPaiOrganograma) {
        this.idnVarPaiOrganograma = idnVarPaiOrganograma;
    }

    public String getIdnvarUsuario() {
        return idnvarUsuario;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public String getIdnVarRoofTop() {
        return idnVarRoofTop;
    }

    public void setIdnVarRoofTop(String idnVarRoofTop) {
        this.idnVarRoofTop = idnVarRoofTop;
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
        if (!(o instanceof OrganogramaDTO)) {
            return false;
        }

        OrganogramaDTO organogramaDTO = (OrganogramaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organogramaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganogramaDTO{" +
            "id=" + getId() +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarPaiOrganograma='" + getIdnVarPaiOrganograma() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", idnVarRoofTop='" + getIdnVarRoofTop() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
