package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma} entity.
 */
public class MacroProcessoOrganogramaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarMacroProcesso;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnVarUsuario;

    @NotNull
    private String idnVarEmpresa;

    private String idnVarUsuarioCadastro;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;

    private MacroProcessoDTO macroProcesso;

    private OrganogramaDTO organograma;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarMacroProcesso() {
        return idnVarMacroProcesso;
    }

    public void setIdnVarMacroProcesso(String idnVarMacroProcesso) {
        this.idnVarMacroProcesso = idnVarMacroProcesso;
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

    public String getIdnVarUsuario() {
        return idnVarUsuario;
    }

    public void setIdnVarUsuario(String idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
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

    public MacroProcessoDTO getMacroProcesso() {
        return macroProcesso;
    }

    public void setMacroProcesso(MacroProcessoDTO macroProcesso) {
        this.macroProcesso = macroProcesso;
    }

    public OrganogramaDTO getOrganograma() {
        return organograma;
    }

    public void setOrganograma(OrganogramaDTO organograma) {
        this.organograma = organograma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MacroProcessoOrganogramaDTO)) {
            return false;
        }

        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = (MacroProcessoOrganogramaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, macroProcessoOrganogramaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MacroProcessoOrganogramaDTO{" +
            "id=" + getId() +
            ", idnVarMacroProcesso='" + getIdnVarMacroProcesso() + "'" +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnVarUsuario='" + getIdnVarUsuario() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnVarUsuarioCadastro='" + getIdnVarUsuarioCadastro() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            ", macroProcesso=" + getMacroProcesso() +
            ", organograma=" + getOrganograma() +
            "}";
    }
}
