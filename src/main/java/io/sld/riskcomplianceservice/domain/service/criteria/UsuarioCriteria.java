package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.resource.UsuarioResource;
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
 * Criteria class for the {@link Usuario} entity. This class is used
 * in {@link UsuarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /usuarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class UsuarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarUsuario;

    private StringFilter nVarNome;

    private StringFilter idnVarEmpresa;

    private StringFilter idnVarUsuarioCadastro;

    private StringFilter nVarSenha;

    private LongFilter clienteExternoId;

    private LongFilter fornecedorExternoId;

    private LongFilter complianceExternoId;

    private LongFilter complianceInternoId;

    private LongFilter funcionarioId;

    private LongFilter macroProcessoId;

    private LongFilter macroProcessoOrganogramaId;

    private LongFilter organogramaId;

    private LongFilter processoId;

    private LongFilter clienteExternoProcessoId;

    private LongFilter complianceExternoProcessoId;

    private LongFilter fornecedorExternoProcessoId;

    private LongFilter clienteInternoProcessoId;

    private LongFilter funcionarioOrganogramaId;

    private LongFilter complianceInternoProcessoId;

    private LongFilter fornecedorInternoProcessoId;

    private LongFilter empresaId;

    private Boolean distinct;

    public UsuarioCriteria() {}

    public UsuarioCriteria(UsuarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarUsuario = other.idnVarUsuario == null ? null : other.idnVarUsuario.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnVarUsuarioCadastro = other.idnVarUsuarioCadastro == null ? null : other.idnVarUsuarioCadastro.copy();
        this.nVarSenha = other.nVarSenha == null ? null : other.nVarSenha.copy();
        this.clienteExternoId = other.clienteExternoId == null ? null : other.clienteExternoId.copy();
        this.fornecedorExternoId = other.fornecedorExternoId == null ? null : other.fornecedorExternoId.copy();
        this.complianceExternoId = other.complianceExternoId == null ? null : other.complianceExternoId.copy();
        this.complianceInternoId = other.complianceInternoId == null ? null : other.complianceInternoId.copy();
        this.funcionarioId = other.funcionarioId == null ? null : other.funcionarioId.copy();
        this.macroProcessoId = other.macroProcessoId == null ? null : other.macroProcessoId.copy();
        this.macroProcessoOrganogramaId = other.macroProcessoOrganogramaId == null ? null : other.macroProcessoOrganogramaId.copy();
        this.organogramaId = other.organogramaId == null ? null : other.organogramaId.copy();
        this.processoId = other.processoId == null ? null : other.processoId.copy();
        this.clienteExternoProcessoId = other.clienteExternoProcessoId == null ? null : other.clienteExternoProcessoId.copy();
        this.complianceExternoProcessoId = other.complianceExternoProcessoId == null ? null : other.complianceExternoProcessoId.copy();
        this.fornecedorExternoProcessoId = other.fornecedorExternoProcessoId == null ? null : other.fornecedorExternoProcessoId.copy();
        this.clienteInternoProcessoId = other.clienteInternoProcessoId == null ? null : other.clienteInternoProcessoId.copy();
        this.funcionarioOrganogramaId = other.funcionarioOrganogramaId == null ? null : other.funcionarioOrganogramaId.copy();
        this.complianceInternoProcessoId = other.complianceInternoProcessoId == null ? null : other.complianceInternoProcessoId.copy();
        this.fornecedorInternoProcessoId = other.fornecedorInternoProcessoId == null ? null : other.fornecedorInternoProcessoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UsuarioCriteria copy() {
        return new UsuarioCriteria(this);
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

    public StringFilter getIdnVarUsuario() {
        return idnVarUsuario;
    }

    public StringFilter idnVarUsuario() {
        if (idnVarUsuario == null) {
            idnVarUsuario = new StringFilter();
        }
        return idnVarUsuario;
    }

    public void setIdnVarUsuario(StringFilter idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
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

    public StringFilter getIdnVarUsuarioCadastro() {
        return idnVarUsuarioCadastro;
    }

    public StringFilter idnVarUsuarioCadastro() {
        if (idnVarUsuarioCadastro == null) {
            idnVarUsuarioCadastro = new StringFilter();
        }
        return idnVarUsuarioCadastro;
    }

    public void setIdnVarUsuarioCadastro(StringFilter idnVarUsuarioCadastro) {
        this.idnVarUsuarioCadastro = idnVarUsuarioCadastro;
    }

    public StringFilter getnVarSenha() {
        return nVarSenha;
    }

    public StringFilter nVarSenha() {
        if (nVarSenha == null) {
            nVarSenha = new StringFilter();
        }
        return nVarSenha;
    }

    public void setnVarSenha(StringFilter nVarSenha) {
        this.nVarSenha = nVarSenha;
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

    public LongFilter getComplianceExternoProcessoId() {
        return complianceExternoProcessoId;
    }

    public LongFilter complianceExternoProcessoId() {
        if (complianceExternoProcessoId == null) {
            complianceExternoProcessoId = new LongFilter();
        }
        return complianceExternoProcessoId;
    }

    public void setComplianceExternoProcessoId(LongFilter complianceExternoProcessoId) {
        this.complianceExternoProcessoId = complianceExternoProcessoId;
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

    public LongFilter getComplianceInternoProcessoId() {
        return complianceInternoProcessoId;
    }

    public LongFilter complianceInternoProcessoId() {
        if (complianceInternoProcessoId == null) {
            complianceInternoProcessoId = new LongFilter();
        }
        return complianceInternoProcessoId;
    }

    public void setComplianceInternoProcessoId(LongFilter complianceInternoProcessoId) {
        this.complianceInternoProcessoId = complianceInternoProcessoId;
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
        final UsuarioCriteria that = (UsuarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarUsuario, that.idnVarUsuario) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnVarUsuarioCadastro, that.idnVarUsuarioCadastro) &&
            Objects.equals(nVarSenha, that.nVarSenha) &&
            Objects.equals(clienteExternoId, that.clienteExternoId) &&
            Objects.equals(fornecedorExternoId, that.fornecedorExternoId) &&
            Objects.equals(complianceExternoId, that.complianceExternoId) &&
            Objects.equals(complianceInternoId, that.complianceInternoId) &&
            Objects.equals(funcionarioId, that.funcionarioId) &&
            Objects.equals(macroProcessoId, that.macroProcessoId) &&
            Objects.equals(macroProcessoOrganogramaId, that.macroProcessoOrganogramaId) &&
            Objects.equals(organogramaId, that.organogramaId) &&
            Objects.equals(processoId, that.processoId) &&
            Objects.equals(clienteExternoProcessoId, that.clienteExternoProcessoId) &&
            Objects.equals(complianceExternoProcessoId, that.complianceExternoProcessoId) &&
            Objects.equals(fornecedorExternoProcessoId, that.fornecedorExternoProcessoId) &&
            Objects.equals(clienteInternoProcessoId, that.clienteInternoProcessoId) &&
            Objects.equals(funcionarioOrganogramaId, that.funcionarioOrganogramaId) &&
            Objects.equals(complianceInternoProcessoId, that.complianceInternoProcessoId) &&
            Objects.equals(fornecedorInternoProcessoId, that.fornecedorInternoProcessoId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarUsuario,
            nVarNome,
            idnVarEmpresa,
            idnVarUsuarioCadastro,
            nVarSenha,
            clienteExternoId,
            fornecedorExternoId,
            complianceExternoId,
            complianceInternoId,
            funcionarioId,
            macroProcessoId,
            macroProcessoOrganogramaId,
            organogramaId,
            processoId,
            clienteExternoProcessoId,
            complianceExternoProcessoId,
            fornecedorExternoProcessoId,
            clienteInternoProcessoId,
            funcionarioOrganogramaId,
            complianceInternoProcessoId,
            fornecedorInternoProcessoId,
            empresaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarUsuario != null ? "idnVarUsuario=" + idnVarUsuario + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnVarUsuarioCadastro != null ? "idnVarUsuarioCadastro=" + idnVarUsuarioCadastro + ", " : "") +
            (nVarSenha != null ? "nVarSenha=" + nVarSenha + ", " : "") +
            (clienteExternoId != null ? "clienteExternoId=" + clienteExternoId + ", " : "") +
            (fornecedorExternoId != null ? "fornecedorExternoId=" + fornecedorExternoId + ", " : "") +
            (complianceExternoId != null ? "complianceExternoId=" + complianceExternoId + ", " : "") +
            (complianceInternoId != null ? "complianceInternoId=" + complianceInternoId + ", " : "") +
            (funcionarioId != null ? "funcionarioId=" + funcionarioId + ", " : "") +
            (macroProcessoId != null ? "macroProcessoId=" + macroProcessoId + ", " : "") +
            (macroProcessoOrganogramaId != null ? "macroProcessoOrganogramaId=" + macroProcessoOrganogramaId + ", " : "") +
            (organogramaId != null ? "organogramaId=" + organogramaId + ", " : "") +
            (processoId != null ? "processoId=" + processoId + ", " : "") +
            (clienteExternoProcessoId != null ? "clienteExternoProcessoId=" + clienteExternoProcessoId + ", " : "") +
            (complianceExternoProcessoId != null ? "complianceExternoProcessoId=" + complianceExternoProcessoId + ", " : "") +
            (fornecedorExternoProcessoId != null ? "fornecedorExternoProcessoId=" + fornecedorExternoProcessoId + ", " : "") +
            (clienteInternoProcessoId != null ? "clienteInternoProcessoId=" + clienteInternoProcessoId + ", " : "") +
            (funcionarioOrganogramaId != null ? "funcionarioOrganogramaId=" + funcionarioOrganogramaId + ", " : "") +
            (complianceInternoProcessoId != null ? "complianceInternoProcessoId=" + complianceInternoProcessoId + ", " : "") +
            (fornecedorInternoProcessoId != null ? "fornecedorInternoProcessoId=" + fornecedorInternoProcessoId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
