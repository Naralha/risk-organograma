package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.MacroProcesso} entity.
 */
public class MacroProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarMacroProcesso;

    @NotNull
    private String nVarNomeMacroProcesso;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String idnVarUsuario;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;

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

    public String getnVarNomeMacroProcesso() {
        return nVarNomeMacroProcesso;
    }

    public void setnVarNomeMacroProcesso(String nVarNomeMacroProcesso) {
        this.nVarNomeMacroProcesso = nVarNomeMacroProcesso;
    }

    public String getIdnVarEmpresa() {
        return idnVarEmpresa;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnVarUsuario() {
        return idnVarUsuario;
    }

    public void setIdnVarUsuario(String idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
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
        if (!(o instanceof MacroProcessoDTO)) {
            return false;
        }

        MacroProcessoDTO macroProcessoDTO = (MacroProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, macroProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MacroProcessoDTO{" +
            "id=" + getId() +
            ", idnVarMacroProcesso='" + getIdnVarMacroProcesso() + "'" +
            ", nVarNomeMacroProcesso='" + getnVarNomeMacroProcesso() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnVarUsuario='" + getIdnVarUsuario() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
