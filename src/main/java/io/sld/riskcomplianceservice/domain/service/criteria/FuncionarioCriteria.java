package io.sld.riskcomplianceservice.domain.service.criteria;

import java.io.Serializable;
import java.util.Objects;

import io.sld.riskcomplianceservice.domain.entity.Funcionario;
import io.sld.riskcomplianceservice.domain.service.filter.*;
import io.sld.riskcomplianceservice.resource.FuncionarioResource;
import org.springdoc.api.annotations.ParameterObject;
import io.sld.riskcomplianceservice.domain.service.criteria.Criteria;

/**
 * Criteria class for the {@link Funcionario} entity. This class is used
 * in {@link FuncionarioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /funcionarios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class FuncionarioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

//    private StringFilter idnVarFuncionario;
    private UUIDFilter idnVarFuncionario;

    private StringFilter nVarNome;

    private StringFilter nVarEmail;

    private StringFilter nVarDescricao;

    private StringFilter idnVarEmpresa;

    private StringFilter idnvarUsuario;

    private LongFilter funcionarioOrganogramaId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public FuncionarioCriteria() {}

    public FuncionarioCriteria(FuncionarioCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarFuncionario = other.idnVarFuncionario == null ? null : other.idnVarFuncionario.copy();
        this.nVarNome = other.nVarNome == null ? null : other.nVarNome.copy();
        this.nVarEmail = other.nVarEmail == null ? null : other.nVarEmail.copy();
        this.nVarDescricao = other.nVarDescricao == null ? null : other.nVarDescricao.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.funcionarioOrganogramaId = other.funcionarioOrganogramaId == null ? null : other.funcionarioOrganogramaId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FuncionarioCriteria copy() {
        return new FuncionarioCriteria(this);
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

    public UUIDFilter getIdnVarFuncionario() {
        return idnVarFuncionario;
    }

    public UUIDFilter idnVarFuncionario() {
        if (idnVarFuncionario == null) {
            idnVarFuncionario = new UUIDFilter();
        }
        return idnVarFuncionario;
    }

    public void setIdnVarFuncionario(UUIDFilter idnVarFuncionario) {
        this.idnVarFuncionario = idnVarFuncionario;
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

    public StringFilter getnVarEmail() {
        return nVarEmail;
    }

    public StringFilter nVarEmail() {
        if (nVarEmail == null) {
            nVarEmail = new StringFilter();
        }
        return nVarEmail;
    }

    public void setnVarEmail(StringFilter nVarEmail) {
        this.nVarEmail = nVarEmail;
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
        final FuncionarioCriteria that = (FuncionarioCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarFuncionario, that.idnVarFuncionario) &&
            Objects.equals(nVarNome, that.nVarNome) &&
            Objects.equals(nVarEmail, that.nVarEmail) &&
            Objects.equals(nVarDescricao, that.nVarDescricao) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(funcionarioOrganogramaId, that.funcionarioOrganogramaId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(usuarioId, that.usuarioId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            idnVarFuncionario,
            nVarNome,
            nVarEmail,
            nVarDescricao,
            idnVarEmpresa,
            idnvarUsuario,
            funcionarioOrganogramaId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarFuncionario != null ? "idnVarFuncionario=" + idnVarFuncionario + ", " : "") +
            (nVarNome != null ? "nVarNome=" + nVarNome + ", " : "") +
            (nVarEmail != null ? "nVarEmail=" + nVarEmail + ", " : "") +
            (nVarDescricao != null ? "nVarDescricao=" + nVarDescricao + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (funcionarioOrganogramaId != null ? "funcionarioOrganogramaId=" + funcionarioOrganogramaId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
