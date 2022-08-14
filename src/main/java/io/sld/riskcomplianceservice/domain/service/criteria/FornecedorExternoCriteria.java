package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.resource.FornecedorExternoResource;
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
 * Criteria class for the {@link FornecedorExterno} entity. This class is used
 * in {@link FornecedorExternoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fornecedor-externos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class FornecedorExternoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarFornecedorExterno;

    private StringFilter nVarNome;

    private StringFilter nVarDescricao;

    private StringFilter idnVarEmpresa;

    private StringFilter idnvarUsuario;

    private LongFilter fornecedorExternoProcessoId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public FornecedorExternoCriteria() {}

    public FornecedorExternoCriteria(FornecedorExternoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarFornecedorExterno = other.idnVarFornecedorExterno == null ? null : other.idnVarFornecedorExterno.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.nVarDescricao = other.nVarDescricao == null ? null : other.nVarDescricao.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.fornecedorExternoProcessoId = other.fornecedorExternoProcessoId == null ? null : other.fornecedorExternoProcessoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FornecedorExternoCriteria copy() {
        return new FornecedorExternoCriteria(this);
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

    public LongFilter getFornecedorExternoProcessoId() {
        return fornecedorExternoProcessoId;
    }

    public LongFilter fornecedorExternoProcessoId() {
        if (fornecedorExternoProcessoId == null) {
            fornecedorExternoProcessoId = new LongFilter();
        }
        return fornecedorExternoProcessoId;
    }

    public void setFornecedorExternoProcessoId(LongFilter fornecedorExternoProcessoId) {
        this.fornecedorExternoProcessoId = fornecedorExternoProcessoId;
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
        final FornecedorExternoCriteria that = (FornecedorExternoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarFornecedorExterno, that.idnVarFornecedorExterno) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(nVarDescricao, that.nVarDescricao) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(fornecedorExternoProcessoId, that.fornecedorExternoProcessoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarFornecedorExterno,
            nVarNome,
            nVarDescricao,
            idnVarEmpresa,
            idnvarUsuario,
            fornecedorExternoProcessoId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorExternoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarFornecedorExterno != null ? "idnVarFornecedorExterno=" + idnVarFornecedorExterno + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (nVarDescricao != null ? "nVarDescricao=" + nVarDescricao + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (fornecedorExternoProcessoId != null ? "fornecedorExternoProcessoId=" + fornecedorExternoProcessoId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
