package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.resource.MacroProcessoOrganogramaResource;
import org.springdoc.api.annotations.ParameterObject;
import io.sld.riskcomplianceservice.domain.service.criteria.Criteria;
import io.sld.riskcomplianceservice.domain.service.filter.BooleanFilter;
import io.sld.riskcomplianceservice.domain.service.filter.DoubleFilter;
import io.sld.riskcomplianceservice.domain.service.filter.Filter;
import io.sld.riskcomplianceservice.domain.service.filter.FloatFilter;
import io.sld.riskcomplianceservice.domain.service.filter.IntegerFilter;
import io.sld.riskcomplianceservice.domain.service.filter.LongFilter;
import io.sld.riskcomplianceservice.domain.service.filter.StringFilter;

/**
 * Criteria class for the {@link MacroProcessoOrganograma} entity. This class is used
 * in {@link MacroProcessoOrganogramaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /macro-processo-organogramas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class MacroProcessoOrganogramaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarMacroProcesso;

    private StringFilter idnVarOrganograma;

    private StringFilter idnVarProcesso;

    private StringFilter idnVarUsuario;

    private StringFilter idnVarEmpresa;

    private StringFilter idnVarUsuarioCadastro;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private LongFilter macroProcessoId;

    private LongFilter organogramaId;

    private Boolean distinct;

    public MacroProcessoOrganogramaCriteria() {}

    public MacroProcessoOrganogramaCriteria(MacroProcessoOrganogramaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarMacroProcesso = other.idnVarMacroProcesso == null ? null : other.idnVarMacroProcesso.copy();
        this.idnVarOrganograma = other.idnVarOrganograma == null ? null : other.idnVarOrganograma.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idnVarUsuario = other.idnVarUsuario == null ? null : other.idnVarUsuario.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnVarUsuarioCadastro = other.idnVarUsuarioCadastro == null ? null : other.idnVarUsuarioCadastro.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.macroProcessoId = other.macroProcessoId == null ? null : other.macroProcessoId.copy();
        this.organogramaId = other.organogramaId == null ? null : other.organogramaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public MacroProcessoOrganogramaCriteria copy() {
        return new MacroProcessoOrganogramaCriteria(this);
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

    public StringFilter getIdnVarOrganograma() {
        return idnVarOrganograma;
    }

    public StringFilter idnVarOrganograma() {
        if (idnVarOrganograma == null) {
            idnVarOrganograma = new StringFilter();
        }
        return idnVarOrganograma;
    }

    public void setIdnVarOrganograma(StringFilter idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
    }

    public StringFilter getIdnVarProcesso() {
        return idnVarProcesso;
    }

    public StringFilter idnVarProcesso() {
        if (idnVarProcesso == null) {
            idnVarProcesso = new StringFilter();
        }
        return idnVarProcesso;
    }

    public void setIdnVarProcesso(StringFilter idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
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

    public StringFilter getIdnVarUsuarioCadastro() {
        return idnVarUsuarioCadastro;
    }

    public StringFilter idnVarUsuarioCadastro() {
        if (idnVarUsuarioCadastro == null) {
            idnVarUsuarioCadastro = new StringFilter();
        }
        return idnVarUsuarioCadastro;
    }

    public void setIdnVarUsuarioCadastro(StringFilter idnVarUsuarioCadastro) {
        this.idnVarUsuarioCadastro = idnVarUsuarioCadastro;
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

    public LongFilter getMacroProcessoId() {
        return macroProcessoId;
    }

    public LongFilter macroProcessoId() {
        if (macroProcessoId == null) {
            macroProcessoId = new LongFilter();
        }
        return macroProcessoId;
    }

    public void setMacroProcessoId(LongFilter macroProcessoId) {
        this.macroProcessoId = macroProcessoId;
    }

    public LongFilter getOrganogramaId() {
        return organogramaId;
    }

    public LongFilter organogramaId() {
        if (organogramaId == null) {
            organogramaId = new LongFilter();
        }
        return organogramaId;
    }

    public void setOrganogramaId(LongFilter organogramaId) {
        this.organogramaId = organogramaId;
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
        final MacroProcessoOrganogramaCriteria that = (MacroProcessoOrganogramaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarMacroProcesso, that.idnVarMacroProcesso) &&
            Objects.equals(idnVarOrganograma, that.idnVarOrganograma) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idnVarUsuario, that.idnVarUsuario) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnVarUsuarioCadastro, that.idnVarUsuarioCadastro) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(macroProcessoId, that.macroProcessoId) &&
            Objects.equals(organogramaId, that.organogramaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarMacroProcesso,
            idnVarOrganograma,
            idnVarProcesso,
            idnVarUsuario,
            idnVarEmpresa,
            idnVarUsuarioCadastro,
            empresaId,
            usuarioId,
            macroProcessoId,
            organogramaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MacroProcessoOrganogramaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarMacroProcesso != null ? "idnVarMacroProcesso=" + idnVarMacroProcesso + ", " : "") +
            (idnVarOrganograma != null ? "idnVarOrganograma=" + idnVarOrganograma + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idnVarUsuario != null ? "idnVarUsuario=" + idnVarUsuario + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnVarUsuarioCadastro != null ? "idnVarUsuarioCadastro=" + idnVarUsuarioCadastro + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (macroProcessoId != null ? "macroProcessoId=" + macroProcessoId + ", " : "") +
            (organogramaId != null ? "organogramaId=" + organogramaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
