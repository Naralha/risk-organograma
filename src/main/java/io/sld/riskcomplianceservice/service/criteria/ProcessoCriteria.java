package io.sld.riskcomplianceservice.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.sld.riskcomplianceservice.domain.Processo} entity. This class is used
 * in {@link io.sld.riskcomplianceservice.web.rest.ProcessoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /processos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class ProcessoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter idnVarProcesso;

    private StringFilter idVarMacroProcesso;

    private StringFilter nVarNomeProcesso;

    private StringFilter nVarObjetivoProcesso;

    private StringFilter nVarLimitrofeInicial;

    private StringFilter nVarLimitrofeFinal;

    private StringFilter nVarPathFile;

    private StringFilter nVarEntradas;

    private StringFilter nVarSaidas;

    private InstantFilter dtInicio;

    private InstantFilter dtFim;

    private StringFilter idnVarEmpresa;

    private StringFilter idnvarUsuario;

    private LongFilter clienteExternoProcessoId;

    private LongFilter complianceExternoProcessoId;

    private LongFilter fornecedorExternoProcessoId;

    private LongFilter clienteInternoProcessoId;

    private LongFilter complianceInternoProcessoId;

    private LongFilter fornecedorInternoProcessoId;

    private LongFilter empresaId;

    private LongFilter usuarioId;

    private Boolean distinct;

    public ProcessoCriteria() {}

    public ProcessoCriteria(ProcessoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.idnVarProcesso = other.idnVarProcesso == null ? null : other.idnVarProcesso.copy();
        this.idVarMacroProcesso = other.idVarMacroProcesso == null ? null : other.idVarMacroProcesso.copy();
        this.nVarNomeProcesso = other.nVarNomeProcesso == null ? null : other.nVarNomeProcesso.copy();
        this.nVarObjetivoProcesso = other.nVarObjetivoProcesso == null ? null : other.nVarObjetivoProcesso.copy();
        this.nVarLimitrofeInicial = other.nVarLimitrofeInicial == null ? null : other.nVarLimitrofeInicial.copy();
        this.nVarLimitrofeFinal = other.nVarLimitrofeFinal == null ? null : other.nVarLimitrofeFinal.copy();
        this.nVarPathFile = other.nVarPathFile == null ? null : other.nVarPathFile.copy();
        this.nVarEntradas = other.nVarEntradas == null ? null : other.nVarEntradas.copy();
        this.nVarSaidas = other.nVarSaidas == null ? null : other.nVarSaidas.copy();
        this.dtInicio = other.dtInicio == null ? null : other.dtInicio.copy();
        this.dtFim = other.dtFim == null ? null : other.dtFim.copy();
        this.idnVarEmpresa = other.idnVarEmpresa == null ? null : other.idnVarEmpresa.copy();
        this.idnvarUsuario = other.idnvarUsuario == null ? null : other.idnvarUsuario.copy();
        this.clienteExternoProcessoId = other.clienteExternoProcessoId == null ? null : other.clienteExternoProcessoId.copy();
        this.complianceExternoProcessoId = other.complianceExternoProcessoId == null ? null : other.complianceExternoProcessoId.copy();
        this.fornecedorExternoProcessoId = other.fornecedorExternoProcessoId == null ? null : other.fornecedorExternoProcessoId.copy();
        this.clienteInternoProcessoId = other.clienteInternoProcessoId == null ? null : other.clienteInternoProcessoId.copy();
        this.complianceInternoProcessoId = other.complianceInternoProcessoId == null ? null : other.complianceInternoProcessoId.copy();
        this.fornecedorInternoProcessoId = other.fornecedorInternoProcessoId == null ? null : other.fornecedorInternoProcessoId.copy();
        this.empresaId = other.empresaId == null ? null : other.empresaId.copy();
        this.usuarioId = other.usuarioId == null ? null : other.usuarioId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProcessoCriteria copy() {
        return new ProcessoCriteria(this);
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

    public StringFilter getIdVarMacroProcesso() {
        return idVarMacroProcesso;
    }

    public StringFilter idVarMacroProcesso() {
        if (idVarMacroProcesso == null) {
            idVarMacroProcesso = new StringFilter();
        }
        return idVarMacroProcesso;
    }

    public void setIdVarMacroProcesso(StringFilter idVarMacroProcesso) {
        this.idVarMacroProcesso = idVarMacroProcesso;
    }

    public StringFilter getnVarNomeProcesso() {
        return nVarNomeProcesso;
    }

    public StringFilter nVarNomeProcesso() {
        if (nVarNomeProcesso == null) {
            nVarNomeProcesso = new StringFilter();
        }
        return nVarNomeProcesso;
    }

    public void setnVarNomeProcesso(StringFilter nVarNomeProcesso) {
        this.nVarNomeProcesso = nVarNomeProcesso;
    }

    public StringFilter getnVarObjetivoProcesso() {
        return nVarObjetivoProcesso;
    }

    public StringFilter nVarObjetivoProcesso() {
        if (nVarObjetivoProcesso == null) {
            nVarObjetivoProcesso = new StringFilter();
        }
        return nVarObjetivoProcesso;
    }

    public void setnVarObjetivoProcesso(StringFilter nVarObjetivoProcesso) {
        this.nVarObjetivoProcesso = nVarObjetivoProcesso;
    }

    public StringFilter getnVarLimitrofeInicial() {
        return nVarLimitrofeInicial;
    }

    public StringFilter nVarLimitrofeInicial() {
        if (nVarLimitrofeInicial == null) {
            nVarLimitrofeInicial = new StringFilter();
        }
        return nVarLimitrofeInicial;
    }

    public void setnVarLimitrofeInicial(StringFilter nVarLimitrofeInicial) {
        this.nVarLimitrofeInicial = nVarLimitrofeInicial;
    }

    public StringFilter getnVarLimitrofeFinal() {
        return nVarLimitrofeFinal;
    }

    public StringFilter nVarLimitrofeFinal() {
        if (nVarLimitrofeFinal == null) {
            nVarLimitrofeFinal = new StringFilter();
        }
        return nVarLimitrofeFinal;
    }

    public void setnVarLimitrofeFinal(StringFilter nVarLimitrofeFinal) {
        this.nVarLimitrofeFinal = nVarLimitrofeFinal;
    }

    public StringFilter getnVarPathFile() {
        return nVarPathFile;
    }

    public StringFilter nVarPathFile() {
        if (nVarPathFile == null) {
            nVarPathFile = new StringFilter();
        }
        return nVarPathFile;
    }

    public void setnVarPathFile(StringFilter nVarPathFile) {
        this.nVarPathFile = nVarPathFile;
    }

    public StringFilter getnVarEntradas() {
        return nVarEntradas;
    }

    public StringFilter nVarEntradas() {
        if (nVarEntradas == null) {
            nVarEntradas = new StringFilter();
        }
        return nVarEntradas;
    }

    public void setnVarEntradas(StringFilter nVarEntradas) {
        this.nVarEntradas = nVarEntradas;
    }

    public StringFilter getnVarSaidas() {
        return nVarSaidas;
    }

    public StringFilter nVarSaidas() {
        if (nVarSaidas == null) {
            nVarSaidas = new StringFilter();
        }
        return nVarSaidas;
    }

    public void setnVarSaidas(StringFilter nVarSaidas) {
        this.nVarSaidas = nVarSaidas;
    }

    public InstantFilter getDtInicio() {
        return dtInicio;
    }

    public InstantFilter dtInicio() {
        if (dtInicio == null) {
            dtInicio = new InstantFilter();
        }
        return dtInicio;
    }

    public void setDtInicio(InstantFilter dtInicio) {
        this.dtInicio = dtInicio;
    }

    public InstantFilter getDtFim() {
        return dtFim;
    }

    public InstantFilter dtFim() {
        if (dtFim == null) {
            dtFim = new InstantFilter();
        }
        return dtFim;
    }

    public void setDtFim(InstantFilter dtFim) {
        this.dtFim = dtFim;
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
        final ProcessoCriteria that = (ProcessoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(idnVarProcesso, that.idnVarProcesso) &&
            Objects.equals(idVarMacroProcesso, that.idVarMacroProcesso) &&
            Objects.equals(nVarNomeProcesso, that.nVarNomeProcesso) &&
            Objects.equals(nVarObjetivoProcesso, that.nVarObjetivoProcesso) &&
            Objects.equals(nVarLimitrofeInicial, that.nVarLimitrofeInicial) &&
            Objects.equals(nVarLimitrofeFinal, that.nVarLimitrofeFinal) &&
            Objects.equals(nVarPathFile, that.nVarPathFile) &&
            Objects.equals(nVarEntradas, that.nVarEntradas) &&
            Objects.equals(nVarSaidas, that.nVarSaidas) &&
            Objects.equals(dtInicio, that.dtInicio) &&
            Objects.equals(dtFim, that.dtFim) &&
            Objects.equals(idnVarEmpresa, that.idnVarEmpresa) &&
            Objects.equals(idnvarUsuario, that.idnvarUsuario) &&
            Objects.equals(clienteExternoProcessoId, that.clienteExternoProcessoId) &&
            Objects.equals(complianceExternoProcessoId, that.complianceExternoProcessoId) &&
            Objects.equals(fornecedorExternoProcessoId, that.fornecedorExternoProcessoId) &&
            Objects.equals(clienteInternoProcessoId, that.clienteInternoProcessoId) &&
            Objects.equals(complianceInternoProcessoId, that.complianceInternoProcessoId) &&
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
            idnVarProcesso,
            idVarMacroProcesso,
            nVarNomeProcesso,
            nVarObjetivoProcesso,
            nVarLimitrofeInicial,
            nVarLimitrofeFinal,
            nVarPathFile,
            nVarEntradas,
            nVarSaidas,
            dtInicio,
            dtFim,
            idnVarEmpresa,
            idnvarUsuario,
            clienteExternoProcessoId,
            complianceExternoProcessoId,
            fornecedorExternoProcessoId,
            clienteInternoProcessoId,
            complianceInternoProcessoId,
            fornecedorInternoProcessoId,
            empresaId,
            usuarioId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (idnVarProcesso != null ? "idnVarProcesso=" + idnVarProcesso + ", " : "") +
            (idVarMacroProcesso != null ? "idVarMacroProcesso=" + idVarMacroProcesso + ", " : "") +
            (nVarNomeProcesso != null ? "nVarNomeProcesso=" + nVarNomeProcesso + ", " : "") +
            (nVarObjetivoProcesso != null ? "nVarObjetivoProcesso=" + nVarObjetivoProcesso + ", " : "") +
            (nVarLimitrofeInicial != null ? "nVarLimitrofeInicial=" + nVarLimitrofeInicial + ", " : "") +
            (nVarLimitrofeFinal != null ? "nVarLimitrofeFinal=" + nVarLimitrofeFinal + ", " : "") +
            (nVarPathFile != null ? "nVarPathFile=" + nVarPathFile + ", " : "") +
            (nVarEntradas != null ? "nVarEntradas=" + nVarEntradas + ", " : "") +
            (nVarSaidas != null ? "nVarSaidas=" + nVarSaidas + ", " : "") +
            (dtInicio != null ? "dtInicio=" + dtInicio + ", " : "") +
            (dtFim != null ? "dtFim=" + dtFim + ", " : "") +
            (idnVarEmpresa != null ? "idnVarEmpresa=" + idnVarEmpresa + ", " : "") +
            (idnvarUsuario != null ? "idnvarUsuario=" + idnvarUsuario + ", " : "") +
            (clienteExternoProcessoId != null ? "clienteExternoProcessoId=" + clienteExternoProcessoId + ", " : "") +
            (complianceExternoProcessoId != null ? "complianceExternoProcessoId=" + complianceExternoProcessoId + ", " : "") +
            (fornecedorExternoProcessoId != null ? "fornecedorExternoProcessoId=" + fornecedorExternoProcessoId + ", " : "") +
            (clienteInternoProcessoId != null ? "clienteInternoProcessoId=" + clienteInternoProcessoId + ", " : "") +
            (complianceInternoProcessoId != null ? "complianceInternoProcessoId=" + complianceInternoProcessoId + ", " : "") +
            (fornecedorInternoProcessoId != null ? "fornecedorInternoProcessoId=" + fornecedorInternoProcessoId + ", " : "") +
            (empresaId != null ? "empresaId=" + empresaId + ", " : "") +
            (usuarioId != null ? "usuarioId=" + usuarioId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
