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
 * Criteria class for the {@link io.sld.riskcomplianceservice.domain.ClienteExterno} entity. This class is used
 * in {@link io.sld.riskcomplianceservice.web.rest.ClienteExternoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cliente-externos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ClienteExternoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarClienteExterno;

    private StringFilter nVarNome;

    private StringFilter nVarDescricao;

    private StringFilter idnVarEmpresa;

    private StringFilter idnvarUsuario;

    private LongFilter clienteExternoProcessoId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ClienteExternoCriteria() {}

    public ClienteExternoCriteria(ClienteExternoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarClienteExterno = other.idnVarClienteExterno == null ? null : other.idnVarClienteExterno.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.nVarDescricao = other.nVarDescricao == null ? null : other.nVarDescricao.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.clienteExternoProcessoId = other.clienteExternoProcessoId == null ? null : other.clienteExternoProcessoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ClienteExternoCriteria copy() {
        return new ClienteExternoCriteria(this);
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

    public LongFilter getClienteExternoProcessoId() {
        return clienteExternoProcessoId;
    }

    public LongFilter clienteExternoProcessoId() {
        if (clienteExternoProcessoId == null) {
            clienteExternoProcessoId = new LongFilter();
        }
        return clienteExternoProcessoId;
    }

    public void setClienteExternoProcessoId(LongFilter clienteExternoProcessoId) {
        this.clienteExternoProcessoId = clienteExternoProcessoId;
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
        final ClienteExternoCriteria that = (ClienteExternoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarClienteExterno, that.idnVarClienteExterno) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(nVarDescricao, that.nVarDescricao) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(clienteExternoProcessoId, that.clienteExternoProcessoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarClienteExterno,
            nVarNome,
            nVarDescricao,
            idnVarEmpresa,
            idnvarUsuario,
            clienteExternoProcessoId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteExternoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarClienteExterno != null ? "idnVarClienteExterno=" + idnVarClienteExterno + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (nVarDescricao != null ? "nVarDescricao=" + nVarDescricao + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (clienteExternoProcessoId != null ? "clienteExternoProcessoId=" + clienteExternoProcessoId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
