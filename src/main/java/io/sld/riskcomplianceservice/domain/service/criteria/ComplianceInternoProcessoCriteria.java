package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.resource.ComplianceInternoProcessoResource;
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
 * Criteria class for the {@link ComplianceInternoProcesso} entity. This class is used
 * in {@link ComplianceInternoProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compliance-interno-processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ComplianceInternoProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarComplianceInterno;

    private StringFilter idnVarProcesso;

    private StringFilter idnvarUsuario;

    private LongFilter complianceInternoId;

    private LongFilter processoId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ComplianceInternoProcessoCriteria() {}

    public ComplianceInternoProcessoCriteria(ComplianceInternoProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarComplianceInterno = other.idnVarComplianceInterno == null ? null : other.idnVarComplianceInterno.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.complianceInternoId = other.complianceInternoId == null ? null : other.complianceInternoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ComplianceInternoProcessoCriteria copy() {
        return new ComplianceInternoProcessoCriteria(this);
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

    public StringFilter getIdnVarComplianceInterno() {
        return idnVarComplianceInterno;
    }

    public StringFilter idnVarComplianceInterno() {
        if (idnVarComplianceInterno == null) {
            idnVarComplianceInterno = new StringFilter();
        }
        return idnVarComplianceInterno;
    }

    public void setIdnVarComplianceInterno(StringFilter idnVarComplianceInterno) {
        this.idnVarComplianceInterno = idnVarComplianceInterno;
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

    public LongFilter getComplianceInternoId() {
        return complianceInternoId;
    }

    public LongFilter complianceInternoId() {
        if (complianceInternoId == null) {
            complianceInternoId = new LongFilter();
        }
        return complianceInternoId;
    }

    public void setComplianceInternoId(LongFilter complianceInternoId) {
        this.complianceInternoId = complianceInternoId;
    }

    public LongFilter getProcessoId() {
        return processoId;
    }

    public LongFilter processoId() {
        if (processoId == null) {
            processoId = new LongFilter();
        }
        return processoId;
    }

    public void setProcessoId(LongFilter processoId) {
        this.processoId = processoId;
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
        final ComplianceInternoProcessoCriteria that = (ComplianceInternoProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarComplianceInterno, that.idnVarComplianceInterno) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(complianceInternoId, that.complianceInternoId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarComplianceInterno,
            idnVarProcesso,
            idnvarUsuario,
            complianceInternoId,
            processoId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceInternoProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarComplianceInterno != null ? "idnVarComplianceInterno=" + idnVarComplianceInterno + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (complianceInternoId != null ? "complianceInternoId=" + complianceInternoId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
