package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.resource.EmpresaResource;
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
 * Criteria class for the {@link Empresa} entity. This class is used
 * in {@link EmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class EmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarEmpresa;

    private StringFilter nVarNome;

    private StringFilter nVarDescricao;

    private LongFilter clienteExternoId;

    private LongFilter fornecedorExternoId;

    private LongFilter complianceExternoId;

    private LongFilter complianceInternoId;

    private LongFilter funcionarioId;

    private LongFilter macroProcessoId;

    private LongFilter macroProcessoOrganogramaId;

    private LongFilter organogramaId;

    private LongFilter processoId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public EmpresaCriteria() {}

    public EmpresaCriteria(EmpresaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.nVarDescricao = other.nVarDescricao == null ? null : other.nVarDescricao.copy();
        this.clienteExternoId = other.clienteExternoId == null ? null : other.clienteExternoId.copy();
        this.fornecedorExternoId = other.fornecedorExternoId == null ? null : other.fornecedorExternoId.copy();
        this.complianceExternoId = other.complianceExternoId == null ? null : other.complianceExternoId.copy();
        this.complianceInternoId = other.complianceInternoId == null ? null : other.complianceInternoId.copy();
        this.funcionarioId = other.funcionarioId == null ? null : other.funcionarioId.copy();
        this.macroProcessoId = other.macroProcessoId == null ? null : other.macroProcessoId.copy();
        this.macroProcessoOrganogramaId = other.macroProcessoOrganogramaId == null ? null : other.macroProcessoOrganogramaId.copy();
        this.organogramaId = other.organogramaId == null ? null : other.organogramaId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmpresaCriteria copy() {
        return new EmpresaCriteria(this);
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

    public LongFilter getMacroProcessoId() {
        return macroProcessoId;
    }

    public LongFilter macroProcessoId() {
        if (macroProcessoId == null) {
            macroProcessoId = new LongFilter();
        }
        return macroProcessoId;
    }

    public void setMacroProcessoId(LongFilter macroProcessoId) {
        this.macroProcessoId = macroProcessoId;
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
        final EmpresaCriteria that = (EmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(nVarDescricao, that.nVarDescricao) &&
            Objects.equals(clienteExternoId, that.clienteExternoId) &&
            Objects.equals(fornecedorExternoId, that.fornecedorExternoId) &&
            Objects.equals(complianceExternoId, that.complianceExternoId) &&
            Objects.equals(complianceInternoId, that.complianceInternoId) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(macroProcessoId, that.macroProcessoId) &&
            Objects.equals(macroProcessoOrganogramaId, that.macroProcessoOrganogramaId) &&
            Objects.equals(organogramaId, that.organogramaId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarEmpresa,
            nVarNome,
            nVarDescricao,
            clienteExternoId,
            fornecedorExternoId,
            complianceExternoId,
            complianceInternoId,
            funcionarioId,
            macroProcessoId,
            macroProcessoOrganogramaId,
            organogramaId,
            processoId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmpresaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (nVarDescricao != null ? "nVarDescricao=" + nVarDescricao + ", " : "") +
            (clienteExternoId != null ? "clienteExternoId=" + clienteExternoId + ", " : "") +
            (fornecedorExternoId != null ? "fornecedorExternoId=" + fornecedorExternoId + ", " : "") +
            (complianceExternoId != null ? "complianceExternoId=" + complianceExternoId + ", " : "") +
            (complianceInternoId != null ? "complianceInternoId=" + complianceInternoId + ", " : "") +
            (funcionarioId != null ? "funcionarioId=" + funcionarioId + ", " : "") +
            (macroProcessoId != null ? "macroProcessoId=" + macroProcessoId + ", " : "") +
            (macroProcessoOrganogramaId != null ? "macroProcessoOrganogramaId=" + macroProcessoOrganogramaId + ", " : "") +
            (organogramaId != null ? "organogramaId=" + organogramaId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
