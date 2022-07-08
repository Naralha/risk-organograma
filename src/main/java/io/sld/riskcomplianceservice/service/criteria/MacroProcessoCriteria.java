package io.sld.riskcomplianceservice.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.sld.riskcomplianceservice.domain.MacroProcesso} entity. This class is used
 * in {@link io.sld.riskcomplianceservice.web.rest.MacroProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /macro-processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class MacroProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarMacroProcesso;

    private StringFilter nVarNomeMacroProcesso;

    private StringFilter idnVarEmpresa;

    private StringFilter idnVarUsuario;

    private LongFilter macroProcessoOrganogramaId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public MacroProcessoCriteria() {}

    public MacroProcessoCriteria(MacroProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarMacroProcesso = other.idnVarMacroProcesso == null ? null : other.idnVarMacroProcesso.copy();
        this.nVarNomeMacroProcesso = other.nVarNomeMacroProcesso == null ? null : other.nVarNomeMacroProcesso.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnVarUsuario = other.idnVarUsuario == null ? null : other.idnVarUsuario.copy();
        this.macroProcessoOrganogramaId = other.macroProcessoOrganogramaId == null ? null : other.macroProcessoOrganogramaId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MacroProcessoCriteria copy() {
        return new MacroProcessoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getIdnVarMacroProcesso() {
        return idnVarMacroProcesso;
    }

    public StringFilter idnVarMacroProcesso() {
        if (idnVarMacroProcesso == null) {
            idnVarMacroProcesso = new StringFilter();
        }
        return idnVarMacroProcesso;
    }

    public void setIdnVarMacroProcesso(StringFilter idnVarMacroProcesso) {
        this.idnVarMacroProcesso = idnVarMacroProcesso;
    }

    public StringFilter getnVarNomeMacroProcesso() {
        return nVarNomeMacroProcesso;
    }

    public StringFilter nVarNomeMacroProcesso() {
        if (nVarNomeMacroProcesso == null) {
            nVarNomeMacroProcesso = new StringFilter();
        }
        return nVarNomeMacroProcesso;
    }

    public void setnVarNomeMacroProcesso(StringFilter nVarNomeMacroProcesso) {
        this.nVarNomeMacroProcesso = nVarNomeMacroProcesso;
    }

    public StringFilter getIdnVarEmpresa() {
        return idnVarEmpresa;
    }

    public StringFilter idnVarEmpresa() {
        if (idnVarEmpresa == null) {
            idnVarEmpresa = new StringFilter();
        }
        return idnVarEmpresa;
    }

    public void setIdnVarEmpresa(StringFilter idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public StringFilter getIdnVarUsuario() {
        return idnVarUsuario;
    }

    public StringFilter idnVarUsuario() {
        if (idnVarUsuario == null) {
            idnVarUsuario = new StringFilter();
        }
        return idnVarUsuario;
    }

    public void setIdnVarUsuario(StringFilter idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
    }

    public LongFilter getMacroProcessoOrganogramaId() {
        return macroProcessoOrganogramaId;
    }

    public LongFilter macroProcessoOrganogramaId() {
        if (macroProcessoOrganogramaId == null) {
            macroProcessoOrganogramaId = new LongFilter();
        }
        return macroProcessoOrganogramaId;
    }

    public void setMacroProcessoOrganogramaId(LongFilter macroProcessoOrganogramaId) {
        this.macroProcessoOrganogramaId = macroProcessoOrganogramaId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public LongFilter empresaId() {
        if (empresaId == null) {
            empresaId = new LongFilter();
        }
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public LongFilter getUsuarioId() {
        return usuarioId;
    }

    public LongFilter usuarioId() {
        if (usuarioId == null) {
            usuarioId = new LongFilter();
        }
        return usuarioId;
    }

    public void setUsuarioId(LongFilter usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MacroProcessoCriteria that = (MacroProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarMacroProcesso, that.idnVarMacroProcesso) &&
            Objects.equals(nVarNomeMacroProcesso, that.nVarNomeMacroProcesso) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnVarUsuario, that.idnVarUsuario) &&
            Objects.equals(macroProcessoOrganogramaId, that.macroProcessoOrganogramaId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarMacroProcesso,
            nVarNomeMacroProcesso,
            idnVarEmpresa,
            idnVarUsuario,
            macroProcessoOrganogramaId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MacroProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarMacroProcesso != null ? "idnVarMacroProcesso=" + idnVarMacroProcesso + ", " : "") +
            (nVarNomeMacroProcesso != null ? "nVarNomeMacroProcesso=" + nVarNomeMacroProcesso + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnVarUsuario != null ? "idnVarUsuario=" + idnVarUsuario + ", " : "") +
            (macroProcessoOrganogramaId != null ? "macroProcessoOrganogramaId=" + macroProcessoOrganogramaId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
