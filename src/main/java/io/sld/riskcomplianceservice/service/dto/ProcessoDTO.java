package io.sld.riskcomplianceservice.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link io.sld.riskcomplianceservice.domain.Processo} entity.
 */
public class ProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idVarMacroProcesso;

    @NotNull
    private String nVarNomeProcesso;

    @NotNull
    private String nVarObjetivoProcesso;

    private String nVarLimitrofeInicial;

    private String nVarLimitrofeFinal;

    private String nVarPathFile;

    private String nVarEntradas;

    private String nVarSaidas;

    @NotNull
    private Instant dtInicio;

    private Instant dtFim;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String idnvarUsuario;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarProcesso() {
        return idnVarProcesso;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdVarMacroProcesso() {
        return idVarMacroProcesso;
    }

    public void setIdVarMacroProcesso(String idVarMacroProcesso) {
        this.idVarMacroProcesso = idVarMacroProcesso;
    }

    public String getnVarNomeProcesso() {
        return nVarNomeProcesso;
    }

    public void setnVarNomeProcesso(String nVarNomeProcesso) {
        this.nVarNomeProcesso = nVarNomeProcesso;
    }

    public String getnVarObjetivoProcesso() {
        return nVarObjetivoProcesso;
    }

    public void setnVarObjetivoProcesso(String nVarObjetivoProcesso) {
        this.nVarObjetivoProcesso = nVarObjetivoProcesso;
    }

    public String getnVarLimitrofeInicial() {
        return nVarLimitrofeInicial;
    }

    public void setnVarLimitrofeInicial(String nVarLimitrofeInicial) {
        this.nVarLimitrofeInicial = nVarLimitrofeInicial;
    }

    public String getnVarLimitrofeFinal() {
        return nVarLimitrofeFinal;
    }

    public void setnVarLimitrofeFinal(String nVarLimitrofeFinal) {
        this.nVarLimitrofeFinal = nVarLimitrofeFinal;
    }

    public String getnVarPathFile() {
        return nVarPathFile;
    }

    public void setnVarPathFile(String nVarPathFile) {
        this.nVarPathFile = nVarPathFile;
    }

    public String getnVarEntradas() {
        return nVarEntradas;
    }

    public void setnVarEntradas(String nVarEntradas) {
        this.nVarEntradas = nVarEntradas;
    }

    public String getnVarSaidas() {
        return nVarSaidas;
    }

    public void setnVarSaidas(String nVarSaidas) {
        this.nVarSaidas = nVarSaidas;
    }

    public Instant getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Instant dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Instant getDtFim() {
        return dtFim;
    }

    public void setDtFim(Instant dtFim) {
        this.dtFim = dtFim;
    }

    public String getIdnVarEmpresa() {
        return idnVarEmpresa;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return idnvarUsuario;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessoDTO)) {
            return false;
        }

        ProcessoDTO processoDTO = (ProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoDTO{" +
            "id=" + getId() +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idVarMacroProcesso='" + getIdVarMacroProcesso() + "'" +
            ", nVarNomeProcesso='" + getnVarNomeProcesso() + "'" +
            ", nVarObjetivoProcesso='" + getnVarObjetivoProcesso() + "'" +
            ", nVarLimitrofeInicial='" + getnVarLimitrofeInicial() + "'" +
            ", nVarLimitrofeFinal='" + getnVarLimitrofeFinal() + "'" +
            ", nVarPathFile='" + getnVarPathFile() + "'" +
            ", nVarEntradas='" + getnVarEntradas() + "'" +
            ", nVarSaidas='" + getnVarSaidas() + "'" +
            ", dtInicio='" + getDtInicio() + "'" +
            ", dtFim='" + getDtFim() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", empresa=" + getEmpresa() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
