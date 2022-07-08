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
 * Criteria class for the {@link io.sld.riskcomplianceservice.domain.FornecedorExternoProcesso} entity. This class is used
 * in {@link io.sld.riskcomplianceservice.web.rest.FornecedorExternoProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fornecedor-externo-processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class FornecedorExternoProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarFornecedorExterno;

    private StringFilter idnVarProcesso;

    private StringFilter idnvarUsuario;

    private LongFilter fornecedorExternoId;

    private LongFilter processoId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public FornecedorExternoProcessoCriteria() {}

    public FornecedorExternoProcessoCriteria(FornecedorExternoProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarFornecedorExterno = other.idnVarFornecedorExterno == null ? null : other.idnVarFornecedorExterno.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.fornecedorExternoId = other.fornecedorExternoId == null ? null : other.fornecedorExternoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FornecedorExternoProcessoCriteria copy() {
        return new FornecedorExternoProcessoCriteria(this);
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

    public StringFilter getIdnVarFornecedorExterno() {
        return idnVarFornecedorExterno;
    }

    public StringFilter idnVarFornecedorExterno() {
        if (idnVarFornecedorExterno == null) {
            idnVarFornecedorExterno = new StringFilter();
        }
        return idnVarFornecedorExterno;
    }

    public void setIdnVarFornecedorExterno(StringFilter idnVarFornecedorExterno) {
        this.idnVarFornecedorExterno = idnVarFornecedorExterno;
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

    public LongFilter getFornecedorExternoId() {
        return fornecedorExternoId;
    }

    public LongFilter fornecedorExternoId() {
        if (fornecedorExternoId == null) {
            fornecedorExternoId = new LongFilter();
        }
        return fornecedorExternoId;
    }

    public void setFornecedorExternoId(LongFilter fornecedorExternoId) {
        this.fornecedorExternoId = fornecedorExternoId;
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
        final FornecedorExternoProcessoCriteria that = (FornecedorExternoProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarFornecedorExterno, that.idnVarFornecedorExterno) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(fornecedorExternoId, that.fornecedorExternoId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarFornecedorExterno,
            idnVarProcesso,
            idnvarUsuario,
            fornecedorExternoId,
            processoId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorExternoProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarFornecedorExterno != null ? "idnVarFornecedorExterno=" + idnVarFornecedorExterno + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (fornecedorExternoId != null ? "fornecedorExternoId=" + fornecedorExternoId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
