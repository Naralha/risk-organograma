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
 * Criteria class for the {@link io.sld.riskcomplianceservice.domain.ComplianceExternoProcesso} entity. This class is used
 * in {@link io.sld.riskcomplianceservice.web.rest.ComplianceExternoProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /compliance-externo-processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ComplianceExternoProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarComplianceExterno;

    private StringFilter idnVarProcesso;

    private StringFilter idnvarUsuario;

    private LongFilter complianceExternoId;

    private LongFilter processoId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ComplianceExternoProcessoCriteria() {}

    public ComplianceExternoProcessoCriteria(ComplianceExternoProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarComplianceExterno = other.idnVarComplianceExterno == null ? null : other.idnVarComplianceExterno.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.complianceExternoId = other.complianceExternoId == null ? null : other.complianceExternoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ComplianceExternoProcessoCriteria copy() {
        return new ComplianceExternoProcessoCriteria(this);
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

    public LongFilter getComplianceExternoId() {
        return complianceExternoId;
    }

    public LongFilter complianceExternoId() {
        if (complianceExternoId == null) {
            complianceExternoId = new LongFilter();
        }
        return complianceExternoId;
    }

    public void setComplianceExternoId(LongFilter complianceExternoId) {
        this.complianceExternoId = complianceExternoId;
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
        final ComplianceExternoProcessoCriteria that = (ComplianceExternoProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarComplianceExterno, that.idnVarComplianceExterno) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(complianceExternoId, that.complianceExternoId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarComplianceExterno,
            idnVarProcesso,
            idnvarUsuario,
            complianceExternoId,
            processoId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceExternoProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarComplianceExterno != null ? "idnVarComplianceExterno=" + idnVarComplianceExterno + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (complianceExternoId != null ? "complianceExternoId=" + complianceExternoId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
