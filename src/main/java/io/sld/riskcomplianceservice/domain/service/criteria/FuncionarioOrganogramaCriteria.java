package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.resource.FuncionarioOrganogramaResource;
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
 * Criteria class for the {@link FuncionarioOrganograma} entity. This class is used
 * in {@link FuncionarioOrganogramaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funcionario-organogramas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class FuncionarioOrganogramaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarFuncionario;

    private StringFilter idnVarOrganograma;

    private StringFilter idnvarUsuario;

    private LongFilter funcionarioId;

    private LongFilter organogramaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public FuncionarioOrganogramaCriteria() {}

    public FuncionarioOrganogramaCriteria(FuncionarioOrganogramaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarFuncionario = other.idnVarFuncionario == null ? null : other.idnVarFuncionario.copy();
        this.idnVarOrganograma = other.idnVarOrganograma == null ? null : other.idnVarOrganograma.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.funcionarioId = other.funcionarioId == null ? null : other.funcionarioId.copy();
        this.organogramaId = other.organogramaId == null ? null : other.organogramaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FuncionarioOrganogramaCriteria copy() {
        return new FuncionarioOrganogramaCriteria(this);
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

    public StringFilter getIdnVarFuncionario() {
        return idnVarFuncionario;
    }

    public StringFilter idnVarFuncionario() {
        if (idnVarFuncionario == null) {
            idnVarFuncionario = new StringFilter();
        }
        return idnVarFuncionario;
    }

    public void setIdnVarFuncionario(StringFilter idnVarFuncionario) {
        this.idnVarFuncionario = idnVarFuncionario;
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

    public LongFilter getFuncionarioId() {
        return funcionarioId;
    }

    public LongFilter funcionarioId() {
        if (funcionarioId == null) {
            funcionarioId = new LongFilter();
        }
        return funcionarioId;
    }

    public void setFuncionarioId(LongFilter funcionarioId) {
        this.funcionarioId = funcionarioId;
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
        final FuncionarioOrganogramaCriteria that = (FuncionarioOrganogramaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarFuncionario, that.idnVarFuncionario) &&
            Objects.equals(idnVarOrganograma, that.idnVarOrganograma) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(organogramaId, that.organogramaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idnVarFuncionario, idnVarOrganograma, idnvarUsuario, funcionarioId, organogramaId, usuarioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioOrganogramaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarFuncionario != null ? "idnVarFuncionario=" + idnVarFuncionario + ", " : "") +
            (idnVarOrganograma != null ? "idnVarOrganograma=" + idnVarOrganograma + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (funcionarioId != null ? "funcionarioId=" + funcionarioId + ", " : "") +
            (organogramaId != null ? "organogramaId=" + organogramaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
