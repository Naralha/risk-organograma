package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.resource.ClienteExternoProcessoResource;
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
 * Criteria class for the {@link ClienteExternoProcesso} entity. This class is used
 * in {@link ClienteExternoProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cliente-externo-processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClienteExternoProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarClienteExterno;

    private StringFilter idnVarProcesso;

    private StringFilter idnvarUsuario;

    private LongFilter clienteExternoId;

    private LongFilter processoId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ClienteExternoProcessoCriteria() {}

    public ClienteExternoProcessoCriteria(ClienteExternoProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarClienteExterno = other.idnVarClienteExterno == null ? null : other.idnVarClienteExterno.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.clienteExternoId = other.clienteExternoId == null ? null : other.clienteExternoId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClienteExternoProcessoCriteria copy() {
        return new ClienteExternoProcessoCriteria(this);
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

    public StringFilter getIdnVarClienteExterno() {
        return idnVarClienteExterno;
    }

    public StringFilter idnVarClienteExterno() {
        if (idnVarClienteExterno == null) {
            idnVarClienteExterno = new StringFilter();
        }
        return idnVarClienteExterno;
    }

    public void setIdnVarClienteExterno(StringFilter idnVarClienteExterno) {
        this.idnVarClienteExterno = idnVarClienteExterno;
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

    public LongFilter getClienteExternoId() {
        return clienteExternoId;
    }

    public LongFilter clienteExternoId() {
        if (clienteExternoId == null) {
            clienteExternoId = new LongFilter();
        }
        return clienteExternoId;
    }

    public void setClienteExternoId(LongFilter clienteExternoId) {
        this.clienteExternoId = clienteExternoId;
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
        final ClienteExternoProcessoCriteria that = (ClienteExternoProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarClienteExterno, that.idnVarClienteExterno) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(clienteExternoId, that.clienteExternoId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idnVarClienteExterno, idnVarProcesso, idnvarUsuario, clienteExternoId, processoId, usuarioId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteExternoProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarClienteExterno != null ? "idnVarClienteExterno=" + idnVarClienteExterno + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (clienteExternoId != null ? "clienteExternoId=" + clienteExternoId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
