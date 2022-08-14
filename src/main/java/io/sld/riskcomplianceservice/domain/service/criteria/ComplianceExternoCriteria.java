package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.ComplianceExterno;
import io.sld.riskcomplianceservice.resource.ComplianceExternoResource;
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
 * Criteria class for the {@link ComplianceExterno} entity. This class is used
 * in {@link ComplianceExternoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compliance-externos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ComplianceExternoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarComplianceExterno;

    private StringFilter nVarNome;

    private StringFilter nVarDescricao;

    private StringFilter idnVarEmpresa;

    private StringFilter idnvarUsuario;

    private LongFilter complianceExternoProcessoId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ComplianceExternoCriteria() {}

    public ComplianceExternoCriteria(ComplianceExternoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarComplianceExterno = other.idnVarComplianceExterno == null ? null : other.idnVarComplianceExterno.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.nVarDescricao = other.nVarDescricao == null ? null : other.nVarDescricao.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.complianceExternoProcessoId = other.complianceExternoProcessoId == null ? null : other.complianceExternoProcessoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ComplianceExternoCriteria copy() {
        return new ComplianceExternoCriteria(this);
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

    public StringFilter getIdnVarComplianceExterno() {
        return idnVarComplianceExterno;
    }

    public StringFilter idnVarComplianceExterno() {
        if (idnVarComplianceExterno == null) {
            idnVarComplianceExterno = new StringFilter();
        }
        return idnVarComplianceExterno;
    }

    public void setIdnVarComplianceExterno(StringFilter idnVarComplianceExterno) {
        this.idnVarComplianceExterno = idnVarComplianceExterno;
    }

    public StringFilter getnVarNome() {
        return nVarNome;
    }

    public StringFilter nVarNome() {
        if (nVarNome == null) {
            nVarNome = new StringFilter();
        }
        return nVarNome;
    }

    public void setnVarNome(StringFilter nVarNome) {
        this.nVarNome = nVarNome;
    }

    public StringFilter getnVarDescricao() {
        return nVarDescricao;
    }

    public StringFilter nVarDescricao() {
        if (nVarDescricao == null) {
            nVarDescricao = new StringFilter();
        }
        return nVarDescricao;
    }

    public void setnVarDescricao(StringFilter nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
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

    public StringFilter getIdnvarUsuario() {
        return idnvarUsuario;
    }

    public StringFilter idnvarUsuario() {
        if (idnvarUsuario == null) {
            idnvarUsuario = new StringFilter();
        }
        return idnvarUsuario;
    }

    public void setIdnvarUsuario(StringFilter idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public LongFilter getComplianceExternoProcessoId() {
        return complianceExternoProcessoId;
    }

    public LongFilter complianceExternoProcessoId() {
        if (complianceExternoProcessoId == null) {
            complianceExternoProcessoId = new LongFilter();
        }
        return complianceExternoProcessoId;
    }

    public void setComplianceExternoProcessoId(LongFilter complianceExternoProcessoId) {
        this.complianceExternoProcessoId = complianceExternoProcessoId;
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
        final ComplianceExternoCriteria that = (ComplianceExternoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarComplianceExterno, that.idnVarComplianceExterno) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(nVarDescricao, that.nVarDescricao) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(complianceExternoProcessoId, that.complianceExternoProcessoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarComplianceExterno,
            nVarNome,
            nVarDescricao,
            idnVarEmpresa,
            idnvarUsuario,
            complianceExternoProcessoId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceExternoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarComplianceExterno != null ? "idnVarComplianceExterno=" + idnVarComplianceExterno + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (nVarDescricao != null ? "nVarDescricao=" + nVarDescricao + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (complianceExternoProcessoId != null ? "complianceExternoProcessoId=" + complianceExternoProcessoId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
