package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.resource.OrganogramaResource;
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
 * Criteria class for the {@link Organograma} entity. This class is used
 * in {@link OrganogramaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /organogramas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class OrganogramaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarOrganograma;

    private StringFilter idnVarEmpresa;

    private StringFilter nVarNome;

    private StringFilter nVarDescricao;

    private StringFilter idnVarPaiOrganograma;

    private StringFilter idnvarUsuario;

    private StringFilter idnVarRoofTop;

    private LongFilter macroProcessoOrganogramaId;

    private LongFilter clienteInternoProcessoId;

    private LongFilter funcionarioOrganogramaId;

    private LongFilter fornecedorInternoProcessoId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public OrganogramaCriteria() {}

    public OrganogramaCriteria(OrganogramaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarOrganograma = other.idnVarOrganograma == null ? null : other.idnVarOrganograma.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.nVarDescricao = other.nVarDescricao == null ? null : other.nVarDescricao.copy();
        this.idnVarPaiOrganograma = other.idnVarPaiOrganograma == null ? null : other.idnVarPaiOrganograma.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.idnVarRoofTop = other.idnVarRoofTop == null ? null : other.idnVarRoofTop.copy();
        this.macroProcessoOrganogramaId = other.macroProcessoOrganogramaId == null ? null : other.macroProcessoOrganogramaId.copy();
        this.clienteInternoProcessoId = other.clienteInternoProcessoId == null ? null : other.clienteInternoProcessoId.copy();
        this.funcionarioOrganogramaId = other.funcionarioOrganogramaId == null ? null : other.funcionarioOrganogramaId.copy();
        this.fornecedorInternoProcessoId = other.fornecedorInternoProcessoId == null ? null : other.fornecedorInternoProcessoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public OrganogramaCriteria copy() {
        return new OrganogramaCriteria(this);
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

    public StringFilter getIdnVarPaiOrganograma() {
        return idnVarPaiOrganograma;
    }

    public StringFilter idnVarPaiOrganograma() {
        if (idnVarPaiOrganograma == null) {
            idnVarPaiOrganograma = new StringFilter();
        }
        return idnVarPaiOrganograma;
    }

    public void setIdnVarPaiOrganograma(StringFilter idnVarPaiOrganograma) {
        this.idnVarPaiOrganograma = idnVarPaiOrganograma;
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

    public StringFilter getIdnVarRoofTop() {
        return idnVarRoofTop;
    }

    public StringFilter idnVarRoofTop() {
        if (idnVarRoofTop == null) {
            idnVarRoofTop = new StringFilter();
        }
        return idnVarRoofTop;
    }

    public void setIdnVarRoofTop(StringFilter idnVarRoofTop) {
        this.idnVarRoofTop = idnVarRoofTop;
    }

    public LongFilter getMacroProcessoOrganogramaId() {
        return macroProcessoOrganogramaId;
    }

    public LongFilter macroProcessoOrganogramaId() {
        if (macroProcessoOrganogramaId == null) {
            macroProcessoOrganogramaId = new LongFilter();
        }
        return macroProcessoOrganogramaId;
    }

    public void setMacroProcessoOrganogramaId(LongFilter macroProcessoOrganogramaId) {
        this.macroProcessoOrganogramaId = macroProcessoOrganogramaId;
    }

    public LongFilter getClienteInternoProcessoId() {
        return clienteInternoProcessoId;
    }

    public LongFilter clienteInternoProcessoId() {
        if (clienteInternoProcessoId == null) {
            clienteInternoProcessoId = new LongFilter();
        }
        return clienteInternoProcessoId;
    }

    public void setClienteInternoProcessoId(LongFilter clienteInternoProcessoId) {
        this.clienteInternoProcessoId = clienteInternoProcessoId;
    }

    public LongFilter getFuncionarioOrganogramaId() {
        return funcionarioOrganogramaId;
    }

    public LongFilter funcionarioOrganogramaId() {
        if (funcionarioOrganogramaId == null) {
            funcionarioOrganogramaId = new LongFilter();
        }
        return funcionarioOrganogramaId;
    }

    public void setFuncionarioOrganogramaId(LongFilter funcionarioOrganogramaId) {
        this.funcionarioOrganogramaId = funcionarioOrganogramaId;
    }

    public LongFilter getFornecedorInternoProcessoId() {
        return fornecedorInternoProcessoId;
    }

    public LongFilter fornecedorInternoProcessoId() {
        if (fornecedorInternoProcessoId == null) {
            fornecedorInternoProcessoId = new LongFilter();
        }
        return fornecedorInternoProcessoId;
    }

    public void setFornecedorInternoProcessoId(LongFilter fornecedorInternoProcessoId) {
        this.fornecedorInternoProcessoId = fornecedorInternoProcessoId;
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
        final OrganogramaCriteria that = (OrganogramaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarOrganograma, that.idnVarOrganograma) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(nVarDescricao, that.nVarDescricao) &&
            Objects.equals(idnVarPaiOrganograma, that.idnVarPaiOrganograma) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(idnVarRoofTop, that.idnVarRoofTop) &&
            Objects.equals(macroProcessoOrganogramaId, that.macroProcessoOrganogramaId) &&
            Objects.equals(clienteInternoProcessoId, that.clienteInternoProcessoId) &&
            Objects.equals(funcionarioOrganogramaId, that.funcionarioOrganogramaId) &&
            Objects.equals(fornecedorInternoProcessoId, that.fornecedorInternoProcessoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarOrganograma,
            idnVarEmpresa,
            nVarNome,
            nVarDescricao,
            idnVarPaiOrganograma,
            idnvarUsuario,
            idnVarRoofTop,
            macroProcessoOrganogramaId,
            clienteInternoProcessoId,
            funcionarioOrganogramaId,
            fornecedorInternoProcessoId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OrganogramaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarOrganograma != null ? "idnVarOrganograma=" + idnVarOrganograma + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (nVarDescricao != null ? "nVarDescricao=" + nVarDescricao + ", " : "") +
            (idnVarPaiOrganograma != null ? "idnVarPaiOrganograma=" + idnVarPaiOrganograma + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (idnVarRoofTop != null ? "idnVarRoofTop=" + idnVarRoofTop + ", " : "") +
            (macroProcessoOrganogramaId != null ? "macroProcessoOrganogramaId=" + macroProcessoOrganogramaId + ", " : "") +
            (clienteInternoProcessoId != null ? "clienteInternoProcessoId=" + clienteInternoProcessoId + ", " : "") +
            (funcionarioOrganogramaId != null ? "funcionarioOrganogramaId=" + funcionarioOrganogramaId + ", " : "") +
            (fornecedorInternoProcessoId != null ? "fornecedorInternoProcessoId=" + fornecedorInternoProcessoId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
