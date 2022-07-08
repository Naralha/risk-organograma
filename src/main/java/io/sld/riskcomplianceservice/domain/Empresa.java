package io.sld.riskcomplianceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Empresa.
 */
@Entity
@Table(name = "empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

    @NotNull
    @Column(name = "n_var_nome", nullable = false)
    private String nVarNome;

    @Column(name = "n_var_descricao")
    private String nVarDescricao;

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clienteExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<ClienteExterno> clienteExternos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fornecedorExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<FornecedorExterno> fornecedorExternos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<ComplianceExterno> complianceExternos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceInternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<ComplianceInterno> complianceInternos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionarioOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "macroProcessoOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private Set<MacroProcesso> macroProcessos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "usuario", "macroProcesso", "organograma" }, allowSetters = true)
    private Set<MacroProcessoOrganograma> macroProcessoOrganogramas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "macroProcessoOrganogramas",
            "clienteInternoProcessos",
            "funcionarioOrganogramas",
            "fornecedorInternoProcessos",
            "empresa",
            "usuario",
        },
        allowSetters = true
    )
    private Set<Organograma> organogramas = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "clienteExternoProcessos",
            "complianceExternoProcessos",
            "fornecedorExternoProcessos",
            "clienteInternoProcessos",
            "complianceInternoProcessos",
            "fornecedorInternoProcessos",
            "empresa",
            "usuario",
        },
        allowSetters = true
    )
    private Set<Processo> processos = new HashSet<>();

    @OneToMany(mappedBy = "empresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<Usuario> usuarios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Empresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public Empresa idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public Empresa nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public Empresa nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public Set<ClienteExterno> getClienteExternos() {
        return this.clienteExternos;
    }

    public void setClienteExternos(Set<ClienteExterno> clienteExternos) {
        if (this.clienteExternos != null) {
            this.clienteExternos.forEach(i -> i.setEmpresa(null));
        }
        if (clienteExternos != null) {
            clienteExternos.forEach(i -> i.setEmpresa(this));
        }
        this.clienteExternos = clienteExternos;
    }

    public Empresa clienteExternos(Set<ClienteExterno> clienteExternos) {
        this.setClienteExternos(clienteExternos);
        return this;
    }

    public Empresa addClienteExterno(ClienteExterno clienteExterno) {
        this.clienteExternos.add(clienteExterno);
        clienteExterno.setEmpresa(this);
        return this;
    }

    public Empresa removeClienteExterno(ClienteExterno clienteExterno) {
        this.clienteExternos.remove(clienteExterno);
        clienteExterno.setEmpresa(null);
        return this;
    }

    public Set<FornecedorExterno> getFornecedorExternos() {
        return this.fornecedorExternos;
    }

    public void setFornecedorExternos(Set<FornecedorExterno> fornecedorExternos) {
        if (this.fornecedorExternos != null) {
            this.fornecedorExternos.forEach(i -> i.setEmpresa(null));
        }
        if (fornecedorExternos != null) {
            fornecedorExternos.forEach(i -> i.setEmpresa(this));
        }
        this.fornecedorExternos = fornecedorExternos;
    }

    public Empresa fornecedorExternos(Set<FornecedorExterno> fornecedorExternos) {
        this.setFornecedorExternos(fornecedorExternos);
        return this;
    }

    public Empresa addFornecedorExterno(FornecedorExterno fornecedorExterno) {
        this.fornecedorExternos.add(fornecedorExterno);
        fornecedorExterno.setEmpresa(this);
        return this;
    }

    public Empresa removeFornecedorExterno(FornecedorExterno fornecedorExterno) {
        this.fornecedorExternos.remove(fornecedorExterno);
        fornecedorExterno.setEmpresa(null);
        return this;
    }

    public Set<ComplianceExterno> getComplianceExternos() {
        return this.complianceExternos;
    }

    public void setComplianceExternos(Set<ComplianceExterno> complianceExternos) {
        if (this.complianceExternos != null) {
            this.complianceExternos.forEach(i -> i.setEmpresa(null));
        }
        if (complianceExternos != null) {
            complianceExternos.forEach(i -> i.setEmpresa(this));
        }
        this.complianceExternos = complianceExternos;
    }

    public Empresa complianceExternos(Set<ComplianceExterno> complianceExternos) {
        this.setComplianceExternos(complianceExternos);
        return this;
    }

    public Empresa addComplianceExterno(ComplianceExterno complianceExterno) {
        this.complianceExternos.add(complianceExterno);
        complianceExterno.setEmpresa(this);
        return this;
    }

    public Empresa removeComplianceExterno(ComplianceExterno complianceExterno) {
        this.complianceExternos.remove(complianceExterno);
        complianceExterno.setEmpresa(null);
        return this;
    }

    public Set<ComplianceInterno> getComplianceInternos() {
        return this.complianceInternos;
    }

    public void setComplianceInternos(Set<ComplianceInterno> complianceInternos) {
        if (this.complianceInternos != null) {
            this.complianceInternos.forEach(i -> i.setEmpresa(null));
        }
        if (complianceInternos != null) {
            complianceInternos.forEach(i -> i.setEmpresa(this));
        }
        this.complianceInternos = complianceInternos;
    }

    public Empresa complianceInternos(Set<ComplianceInterno> complianceInternos) {
        this.setComplianceInternos(complianceInternos);
        return this;
    }

    public Empresa addComplianceInterno(ComplianceInterno complianceInterno) {
        this.complianceInternos.add(complianceInterno);
        complianceInterno.setEmpresa(this);
        return this;
    }

    public Empresa removeComplianceInterno(ComplianceInterno complianceInterno) {
        this.complianceInternos.remove(complianceInterno);
        complianceInterno.setEmpresa(null);
        return this;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        if (this.funcionarios != null) {
            this.funcionarios.forEach(i -> i.setEmpresa(null));
        }
        if (funcionarios != null) {
            funcionarios.forEach(i -> i.setEmpresa(this));
        }
        this.funcionarios = funcionarios;
    }

    public Empresa funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public Empresa addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setEmpresa(this);
        return this;
    }

    public Empresa removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setEmpresa(null);
        return this;
    }

    public Set<MacroProcesso> getMacroProcessos() {
        return this.macroProcessos;
    }

    public void setMacroProcessos(Set<MacroProcesso> macroProcessos) {
        if (this.macroProcessos != null) {
            this.macroProcessos.forEach(i -> i.setEmpresa(null));
        }
        if (macroProcessos != null) {
            macroProcessos.forEach(i -> i.setEmpresa(this));
        }
        this.macroProcessos = macroProcessos;
    }

    public Empresa macroProcessos(Set<MacroProcesso> macroProcessos) {
        this.setMacroProcessos(macroProcessos);
        return this;
    }

    public Empresa addMacroProcesso(MacroProcesso macroProcesso) {
        this.macroProcessos.add(macroProcesso);
        macroProcesso.setEmpresa(this);
        return this;
    }

    public Empresa removeMacroProcesso(MacroProcesso macroProcesso) {
        this.macroProcessos.remove(macroProcesso);
        macroProcesso.setEmpresa(null);
        return this;
    }

    public Set<MacroProcessoOrganograma> getMacroProcessoOrganogramas() {
        return this.macroProcessoOrganogramas;
    }

    public void setMacroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        if (this.macroProcessoOrganogramas != null) {
            this.macroProcessoOrganogramas.forEach(i -> i.setEmpresa(null));
        }
        if (macroProcessoOrganogramas != null) {
            macroProcessoOrganogramas.forEach(i -> i.setEmpresa(this));
        }
        this.macroProcessoOrganogramas = macroProcessoOrganogramas;
    }

    public Empresa macroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        this.setMacroProcessoOrganogramas(macroProcessoOrganogramas);
        return this;
    }

    public Empresa addMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.add(macroProcessoOrganograma);
        macroProcessoOrganograma.setEmpresa(this);
        return this;
    }

    public Empresa removeMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.remove(macroProcessoOrganograma);
        macroProcessoOrganograma.setEmpresa(null);
        return this;
    }

    public Set<Organograma> getOrganogramas() {
        return this.organogramas;
    }

    public void setOrganogramas(Set<Organograma> organogramas) {
        if (this.organogramas != null) {
            this.organogramas.forEach(i -> i.setEmpresa(null));
        }
        if (organogramas != null) {
            organogramas.forEach(i -> i.setEmpresa(this));
        }
        this.organogramas = organogramas;
    }

    public Empresa organogramas(Set<Organograma> organogramas) {
        this.setOrganogramas(organogramas);
        return this;
    }

    public Empresa addOrganograma(Organograma organograma) {
        this.organogramas.add(organograma);
        organograma.setEmpresa(this);
        return this;
    }

    public Empresa removeOrganograma(Organograma organograma) {
        this.organogramas.remove(organograma);
        organograma.setEmpresa(null);
        return this;
    }

    public Set<Processo> getProcessos() {
        return this.processos;
    }

    public void setProcessos(Set<Processo> processos) {
        if (this.processos != null) {
            this.processos.forEach(i -> i.setEmpresa(null));
        }
        if (processos != null) {
            processos.forEach(i -> i.setEmpresa(this));
        }
        this.processos = processos;
    }

    public Empresa processos(Set<Processo> processos) {
        this.setProcessos(processos);
        return this;
    }

    public Empresa addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.setEmpresa(this);
        return this;
    }

    public Empresa removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.setEmpresa(null);
        return this;
    }

    public Set<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        if (this.usuarios != null) {
            this.usuarios.forEach(i -> i.setEmpresa(null));
        }
        if (usuarios != null) {
            usuarios.forEach(i -> i.setEmpresa(this));
        }
        this.usuarios = usuarios;
    }

    public Empresa usuarios(Set<Usuario> usuarios) {
        this.setUsuarios(usuarios);
        return this;
    }

    public Empresa addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.setEmpresa(this);
        return this;
    }

    public Empresa removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.setEmpresa(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Empresa)) {
            return false;
        }
        return id != null && id.equals(((Empresa) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Empresa{" +
            "id=" + getId() +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            "}";
    }
}
