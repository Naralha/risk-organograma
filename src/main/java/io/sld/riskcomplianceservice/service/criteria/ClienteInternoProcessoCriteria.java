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
 * Criteria class for the {@link io.sld.riskcomplianceservice.domain.ClienteInternoProcesso} entity. This class is used
 * in {@link io.sld.riskcomplianceservice.web.rest.ClienteInternoProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cliente-interno-processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClienteInternoProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarProcesso;

    private StringFilter idnVarOrganograma;

    private StringFilter idnvarUsuario;

    private LongFilter organogramaId;

    private LongFilter processoId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ClienteInternoProcessoCriteria() {}

    public ClienteInternoProcessoCriteria(ClienteInternoProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idnVarOrganograma = other.idnVarOrganograma == null ? null : other.idnVarOrganograma.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.organogramaId = other.organogramaId == null ? null : other.organogramaId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClienteInternoProcessoCriteria copy() {
        return new ClienteInternoProcessoCriteria(this);
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
        final ClienteInternoProcessoCriteria that = (ClienteInternoProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idnVarOrganograma, that.idnVarOrganograma) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(organogramaId, that.organogramaId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idnVarProcesso, idnVarOrganograma, idnvarUsuario, organogramaId, processoId, usuarioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteInternoProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idnVarOrganograma != null ? "idnVarOrganograma=" + idnVarOrganograma + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (organogramaId != null ? "organogramaId=" + organogramaId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
