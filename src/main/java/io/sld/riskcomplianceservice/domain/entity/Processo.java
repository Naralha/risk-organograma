package io.sld.riskcomplianceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Processo.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_processo", nullable = false)
    private String idnVarProcesso;

    @NotNull
    @Column(name = "id_var_macro_processo", nullable = false)
    private String idVarMacroProcesso;

    @NotNull
    @Column(name = "n_var_nome_processo", nullable = false)
    private String nVarNomeProcesso;

    @NotNull
    @Column(name = "n_var_objetivo_processo", nullable = false)
    private String nVarObjetivoProcesso;

    @Column(name = "n_var_limitrofe_inicial")
    private String nVarLimitrofeInicial;

    @Column(name = "n_var_limitrofe_final")
    private String nVarLimitrofeFinal;

    @Column(name = "n_var_path_file")
    private String nVarPathFile;

    @Column(name = "n_var_entradas")
    private String nVarEntradas;

    @Column(name = "n_var_saidas")
    private String nVarSaidas;

    @NotNull
    @Column(name = "dt_inicio", nullable = false)
    private Instant dtInicio;

    @Column(name = "dt_fim")
    private Instant dtFim;

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clienteExterno", "processo", "usuario" }, allowSetters = true)
    private Set<ClienteExternoProcesso> clienteExternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceExterno", "processo", "usuario" }, allowSetters = true)
    private Set<ComplianceExternoProcesso> complianceExternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fornecedorExterno", "processo", "usuario" }, allowSetters = true)
    private Set<FornecedorExternoProcesso> fornecedorExternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organograma", "processo", "usuario" }, allowSetters = true)
    private Set<ClienteInternoProcesso> clienteInternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceInterno", "processo", "usuario" }, allowSetters = true)
    private Set<ComplianceInternoProcesso> complianceInternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "processo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organograma", "processo", "usuario" }, allowSetters = true)
    private Set<FornecedorInternoProcesso> fornecedorInternoProcessos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "clienteExternos",
            "fornecedorExternos",
            "complianceExternos",
            "complianceInternos",
            "funcionarios",
            "macroProcessos",
            "macroProcessoOrganogramas",
            "organogramas",
            "processos",
            "usuarios",
        },
        allowSetters = true
    )
    private Empresa empresa;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "clienteExternos",
            "fornecedorExternos",
            "complianceExternos",
            "complianceInternos",
            "funcionarios",
            "macroProcessos",
            "macroProcessoOrganogramas",
            "organogramas",
            "processos",
            "clienteExternoProcessos",
            "complianceExternoProcessos",
            "fornecedorExternoProcessos",
            "clienteInternoProcessos",
            "funcionarioOrganogramas",
            "complianceInternoProcessos",
            "fornecedorInternoProcessos",
            "empresa",
        },
        allowSetters = true
    )
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Processo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarProcesso() {
        return this.idnVarProcesso;
    }

    public Processo idnVarProcesso(String idnVarProcesso) {
        this.setIdnVarProcesso(idnVarProcesso);
        return this;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdVarMacroProcesso() {
        return this.idVarMacroProcesso;
    }

    public Processo idVarMacroProcesso(String idVarMacroProcesso) {
        this.setIdVarMacroProcesso(idVarMacroProcesso);
        return this;
    }

    public void setIdVarMacroProcesso(String idVarMacroProcesso) {
        this.idVarMacroProcesso = idVarMacroProcesso;
    }

    public String getnVarNomeProcesso() {
        return this.nVarNomeProcesso;
    }

    public Processo nVarNomeProcesso(String nVarNomeProcesso) {
        this.setnVarNomeProcesso(nVarNomeProcesso);
        return this;
    }

    public void setnVarNomeProcesso(String nVarNomeProcesso) {
        this.nVarNomeProcesso = nVarNomeProcesso;
    }

    public String getnVarObjetivoProcesso() {
        return this.nVarObjetivoProcesso;
    }

    public Processo nVarObjetivoProcesso(String nVarObjetivoProcesso) {
        this.setnVarObjetivoProcesso(nVarObjetivoProcesso);
        return this;
    }

    public void setnVarObjetivoProcesso(String nVarObjetivoProcesso) {
        this.nVarObjetivoProcesso = nVarObjetivoProcesso;
    }

    public String getnVarLimitrofeInicial() {
        return this.nVarLimitrofeInicial;
    }

    public Processo nVarLimitrofeInicial(String nVarLimitrofeInicial) {
        this.setnVarLimitrofeInicial(nVarLimitrofeInicial);
        return this;
    }

    public void setnVarLimitrofeInicial(String nVarLimitrofeInicial) {
        this.nVarLimitrofeInicial = nVarLimitrofeInicial;
    }

    public String getnVarLimitrofeFinal() {
        return this.nVarLimitrofeFinal;
    }

    public Processo nVarLimitrofeFinal(String nVarLimitrofeFinal) {
        this.setnVarLimitrofeFinal(nVarLimitrofeFinal);
        return this;
    }

    public void setnVarLimitrofeFinal(String nVarLimitrofeFinal) {
        this.nVarLimitrofeFinal = nVarLimitrofeFinal;
    }

    public String getnVarPathFile() {
        return this.nVarPathFile;
    }

    public Processo nVarPathFile(String nVarPathFile) {
        this.setnVarPathFile(nVarPathFile);
        return this;
    }

    public void setnVarPathFile(String nVarPathFile) {
        this.nVarPathFile = nVarPathFile;
    }

    public String getnVarEntradas() {
        return this.nVarEntradas;
    }

    public Processo nVarEntradas(String nVarEntradas) {
        this.setnVarEntradas(nVarEntradas);
        return this;
    }

    public void setnVarEntradas(String nVarEntradas) {
        this.nVarEntradas = nVarEntradas;
    }

    public String getnVarSaidas() {
        return this.nVarSaidas;
    }

    public Processo nVarSaidas(String nVarSaidas) {
        this.setnVarSaidas(nVarSaidas);
        return this;
    }

    public void setnVarSaidas(String nVarSaidas) {
        this.nVarSaidas = nVarSaidas;
    }

    public Instant getDtInicio() {
        return this.dtInicio;
    }

    public Processo dtInicio(Instant dtInicio) {
        this.setDtInicio(dtInicio);
        return this;
    }

    public void setDtInicio(Instant dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Instant getDtFim() {
        return this.dtFim;
    }

    public Processo dtFim(Instant dtFim) {
        this.setDtFim(dtFim);
        return this;
    }

    public void setDtFim(Instant dtFim) {
        this.dtFim = dtFim;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public Processo idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public Processo idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Set<ClienteExternoProcesso> getClienteExternoProcessos() {
        return this.clienteExternoProcessos;
    }

    public void setClienteExternoProcessos(Set<ClienteExternoProcesso> clienteExternoProcessos) {
        if (this.clienteExternoProcessos != null) {
            this.clienteExternoProcessos.forEach(i -> i.setProcesso(null));
        }
        if (clienteExternoProcessos != null) {
            clienteExternoProcessos.forEach(i -> i.setProcesso(this));
        }
        this.clienteExternoProcessos = clienteExternoProcessos;
    }

    public Processo clienteExternoProcessos(Set<ClienteExternoProcesso> clienteExternoProcessos) {
        this.setClienteExternoProcessos(clienteExternoProcessos);
        return this;
    }

    public Processo addClienteExternoProcesso(ClienteExternoProcesso clienteExternoProcesso) {
        this.clienteExternoProcessos.add(clienteExternoProcesso);
        clienteExternoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeClienteExternoProcesso(ClienteExternoProcesso clienteExternoProcesso) {
        this.clienteExternoProcessos.remove(clienteExternoProcesso);
        clienteExternoProcesso.setProcesso(null);
        return this;
    }

    public Set<ComplianceExternoProcesso> getComplianceExternoProcessos() {
        return this.complianceExternoProcessos;
    }

    public void setComplianceExternoProcessos(Set<ComplianceExternoProcesso> complianceExternoProcessos) {
        if (this.complianceExternoProcessos != null) {
            this.complianceExternoProcessos.forEach(i -> i.setProcesso(null));
        }
        if (complianceExternoProcessos != null) {
            complianceExternoProcessos.forEach(i -> i.setProcesso(this));
        }
        this.complianceExternoProcessos = complianceExternoProcessos;
    }

    public Processo complianceExternoProcessos(Set<ComplianceExternoProcesso> complianceExternoProcessos) {
        this.setComplianceExternoProcessos(complianceExternoProcessos);
        return this;
    }

    public Processo addComplianceExternoProcesso(ComplianceExternoProcesso complianceExternoProcesso) {
        this.complianceExternoProcessos.add(complianceExternoProcesso);
        complianceExternoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeComplianceExternoProcesso(ComplianceExternoProcesso complianceExternoProcesso) {
        this.complianceExternoProcessos.remove(complianceExternoProcesso);
        complianceExternoProcesso.setProcesso(null);
        return this;
    }

    public Set<FornecedorExternoProcesso> getFornecedorExternoProcessos() {
        return this.fornecedorExternoProcessos;
    }

    public void setFornecedorExternoProcessos(Set<FornecedorExternoProcesso> fornecedorExternoProcessos) {
        if (this.fornecedorExternoProcessos != null) {
            this.fornecedorExternoProcessos.forEach(i -> i.setProcesso(null));
        }
        if (fornecedorExternoProcessos != null) {
            fornecedorExternoProcessos.forEach(i -> i.setProcesso(this));
        }
        this.fornecedorExternoProcessos = fornecedorExternoProcessos;
    }

    public Processo fornecedorExternoProcessos(Set<FornecedorExternoProcesso> fornecedorExternoProcessos) {
        this.setFornecedorExternoProcessos(fornecedorExternoProcessos);
        return this;
    }

    public Processo addFornecedorExternoProcesso(FornecedorExternoProcesso fornecedorExternoProcesso) {
        this.fornecedorExternoProcessos.add(fornecedorExternoProcesso);
        fornecedorExternoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeFornecedorExternoProcesso(FornecedorExternoProcesso fornecedorExternoProcesso) {
        this.fornecedorExternoProcessos.remove(fornecedorExternoProcesso);
        fornecedorExternoProcesso.setProcesso(null);
        return this;
    }

    public Set<ClienteInternoProcesso> getClienteInternoProcessos() {
        return this.clienteInternoProcessos;
    }

    public void setClienteInternoProcessos(Set<ClienteInternoProcesso> clienteInternoProcessos) {
        if (this.clienteInternoProcessos != null) {
            this.clienteInternoProcessos.forEach(i -> i.setProcesso(null));
        }
        if (clienteInternoProcessos != null) {
            clienteInternoProcessos.forEach(i -> i.setProcesso(this));
        }
        this.clienteInternoProcessos = clienteInternoProcessos;
    }

    public Processo clienteInternoProcessos(Set<ClienteInternoProcesso> clienteInternoProcessos) {
        this.setClienteInternoProcessos(clienteInternoProcessos);
        return this;
    }

    public Processo addClienteInternoProcesso(ClienteInternoProcesso clienteInternoProcesso) {
        this.clienteInternoProcessos.add(clienteInternoProcesso);
        clienteInternoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeClienteInternoProcesso(ClienteInternoProcesso clienteInternoProcesso) {
        this.clienteInternoProcessos.remove(clienteInternoProcesso);
        clienteInternoProcesso.setProcesso(null);
        return this;
    }

    public Set<ComplianceInternoProcesso> getComplianceInternoProcessos() {
        return this.complianceInternoProcessos;
    }

    public void setComplianceInternoProcessos(Set<ComplianceInternoProcesso> complianceInternoProcessos) {
        if (this.complianceInternoProcessos != null) {
            this.complianceInternoProcessos.forEach(i -> i.setProcesso(null));
        }
        if (complianceInternoProcessos != null) {
            complianceInternoProcessos.forEach(i -> i.setProcesso(this));
        }
        this.complianceInternoProcessos = complianceInternoProcessos;
    }

    public Processo complianceInternoProcessos(Set<ComplianceInternoProcesso> complianceInternoProcessos) {
        this.setComplianceInternoProcessos(complianceInternoProcessos);
        return this;
    }

    public Processo addComplianceInternoProcesso(ComplianceInternoProcesso complianceInternoProcesso) {
        this.complianceInternoProcessos.add(complianceInternoProcesso);
        complianceInternoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeComplianceInternoProcesso(ComplianceInternoProcesso complianceInternoProcesso) {
        this.complianceInternoProcessos.remove(complianceInternoProcesso);
        complianceInternoProcesso.setProcesso(null);
        return this;
    }

    public Set<FornecedorInternoProcesso> getFornecedorInternoProcessos() {
        return this.fornecedorInternoProcessos;
    }

    public void setFornecedorInternoProcessos(Set<FornecedorInternoProcesso> fornecedorInternoProcessos) {
        if (this.fornecedorInternoProcessos != null) {
            this.fornecedorInternoProcessos.forEach(i -> i.setProcesso(null));
        }
        if (fornecedorInternoProcessos != null) {
            fornecedorInternoProcessos.forEach(i -> i.setProcesso(this));
        }
        this.fornecedorInternoProcessos = fornecedorInternoProcessos;
    }

    public Processo fornecedorInternoProcessos(Set<FornecedorInternoProcesso> fornecedorInternoProcessos) {
        this.setFornecedorInternoProcessos(fornecedorInternoProcessos);
        return this;
    }

    public Processo addFornecedorInternoProcesso(FornecedorInternoProcesso fornecedorInternoProcesso) {
        this.fornecedorInternoProcessos.add(fornecedorInternoProcesso);
        fornecedorInternoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeFornecedorInternoProcesso(FornecedorInternoProcesso fornecedorInternoProcesso) {
        this.fornecedorInternoProcessos.remove(fornecedorInternoProcesso);
        fornecedorInternoProcesso.setProcesso(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Processo empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Processo usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processo)) {
            return false;
        }
        return id != null && id.equals(((Processo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Processo{" +
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
            "}";
    }
}
