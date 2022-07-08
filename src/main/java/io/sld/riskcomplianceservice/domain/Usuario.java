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
 * A Usuario.
 */
@Entity
@Table(name = "usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_usuario", nullable = false)
    private String idnVarUsuario;

    @NotNull
    @Column(name = "n_var_nome", nullable = false)
    private String nVarNome;

    @Column(name = "idn_var_empresa")
    private String idnVarEmpresa;

    @Column(name = "idn_var_usuario_cadastro")
    private String idnVarUsuarioCadastro;

    @NotNull
    @Column(name = "n_var_senha", nullable = false)
    private String nVarSenha;

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clienteExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<ClienteExterno> clienteExternos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fornecedorExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<FornecedorExterno> fornecedorExternos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<ComplianceExterno> complianceExternos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceInternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private Set<ComplianceInterno> complianceInternos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionarioOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "macroProcessoOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private Set<MacroProcesso> macroProcessos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "usuario", "macroProcesso", "organograma" }, allowSetters = true)
    private Set<MacroProcessoOrganograma> macroProcessoOrganogramas = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
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

    @OneToMany(mappedBy = "usuario")
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

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clienteExterno", "processo", "usuario" }, allowSetters = true)
    private Set<ClienteExternoProcesso> clienteExternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceExterno", "processo", "usuario" }, allowSetters = true)
    private Set<ComplianceExternoProcesso> complianceExternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fornecedorExterno", "processo", "usuario" }, allowSetters = true)
    private Set<FornecedorExternoProcesso> fornecedorExternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organograma", "processo", "usuario" }, allowSetters = true)
    private Set<ClienteInternoProcesso> clienteInternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "organograma", "usuario" }, allowSetters = true)
    private Set<FuncionarioOrganograma> funcionarioOrganogramas = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceInterno", "processo", "usuario" }, allowSetters = true)
    private Set<ComplianceInternoProcesso> complianceInternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "usuario")
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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarUsuario() {
        return this.idnVarUsuario;
    }

    public Usuario idnVarUsuario(String idnVarUsuario) {
        this.setIdnVarUsuario(idnVarUsuario);
        return this;
    }

    public void setIdnVarUsuario(String idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public Usuario nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public Usuario idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnVarUsuarioCadastro() {
        return this.idnVarUsuarioCadastro;
    }

    public Usuario idnVarUsuarioCadastro(String idnVarUsuarioCadastro) {
        this.setIdnVarUsuarioCadastro(idnVarUsuarioCadastro);
        return this;
    }

    public void setIdnVarUsuarioCadastro(String idnVarUsuarioCadastro) {
        this.idnVarUsuarioCadastro = idnVarUsuarioCadastro;
    }

    public String getnVarSenha() {
        return this.nVarSenha;
    }

    public Usuario nVarSenha(String nVarSenha) {
        this.setnVarSenha(nVarSenha);
        return this;
    }

    public void setnVarSenha(String nVarSenha) {
        this.nVarSenha = nVarSenha;
    }

    public Set<ClienteExterno> getClienteExternos() {
        return this.clienteExternos;
    }

    public void setClienteExternos(Set<ClienteExterno> clienteExternos) {
        if (this.clienteExternos != null) {
            this.clienteExternos.forEach(i -> i.setUsuario(null));
        }
        if (clienteExternos != null) {
            clienteExternos.forEach(i -> i.setUsuario(this));
        }
        this.clienteExternos = clienteExternos;
    }

    public Usuario clienteExternos(Set<ClienteExterno> clienteExternos) {
        this.setClienteExternos(clienteExternos);
        return this;
    }

    public Usuario addClienteExterno(ClienteExterno clienteExterno) {
        this.clienteExternos.add(clienteExterno);
        clienteExterno.setUsuario(this);
        return this;
    }

    public Usuario removeClienteExterno(ClienteExterno clienteExterno) {
        this.clienteExternos.remove(clienteExterno);
        clienteExterno.setUsuario(null);
        return this;
    }

    public Set<FornecedorExterno> getFornecedorExternos() {
        return this.fornecedorExternos;
    }

    public void setFornecedorExternos(Set<FornecedorExterno> fornecedorExternos) {
        if (this.fornecedorExternos != null) {
            this.fornecedorExternos.forEach(i -> i.setUsuario(null));
        }
        if (fornecedorExternos != null) {
            fornecedorExternos.forEach(i -> i.setUsuario(this));
        }
        this.fornecedorExternos = fornecedorExternos;
    }

    public Usuario fornecedorExternos(Set<FornecedorExterno> fornecedorExternos) {
        this.setFornecedorExternos(fornecedorExternos);
        return this;
    }

    public Usuario addFornecedorExterno(FornecedorExterno fornecedorExterno) {
        this.fornecedorExternos.add(fornecedorExterno);
        fornecedorExterno.setUsuario(this);
        return this;
    }

    public Usuario removeFornecedorExterno(FornecedorExterno fornecedorExterno) {
        this.fornecedorExternos.remove(fornecedorExterno);
        fornecedorExterno.setUsuario(null);
        return this;
    }

    public Set<ComplianceExterno> getComplianceExternos() {
        return this.complianceExternos;
    }

    public void setComplianceExternos(Set<ComplianceExterno> complianceExternos) {
        if (this.complianceExternos != null) {
            this.complianceExternos.forEach(i -> i.setUsuario(null));
        }
        if (complianceExternos != null) {
            complianceExternos.forEach(i -> i.setUsuario(this));
        }
        this.complianceExternos = complianceExternos;
    }

    public Usuario complianceExternos(Set<ComplianceExterno> complianceExternos) {
        this.setComplianceExternos(complianceExternos);
        return this;
    }

    public Usuario addComplianceExterno(ComplianceExterno complianceExterno) {
        this.complianceExternos.add(complianceExterno);
        complianceExterno.setUsuario(this);
        return this;
    }

    public Usuario removeComplianceExterno(ComplianceExterno complianceExterno) {
        this.complianceExternos.remove(complianceExterno);
        complianceExterno.setUsuario(null);
        return this;
    }

    public Set<ComplianceInterno> getComplianceInternos() {
        return this.complianceInternos;
    }

    public void setComplianceInternos(Set<ComplianceInterno> complianceInternos) {
        if (this.complianceInternos != null) {
            this.complianceInternos.forEach(i -> i.setUsuario(null));
        }
        if (complianceInternos != null) {
            complianceInternos.forEach(i -> i.setUsuario(this));
        }
        this.complianceInternos = complianceInternos;
    }

    public Usuario complianceInternos(Set<ComplianceInterno> complianceInternos) {
        this.setComplianceInternos(complianceInternos);
        return this;
    }

    public Usuario addComplianceInterno(ComplianceInterno complianceInterno) {
        this.complianceInternos.add(complianceInterno);
        complianceInterno.setUsuario(this);
        return this;
    }

    public Usuario removeComplianceInterno(ComplianceInterno complianceInterno) {
        this.complianceInternos.remove(complianceInterno);
        complianceInterno.setUsuario(null);
        return this;
    }

    public Set<Funcionario> getFuncionarios() {
        return this.funcionarios;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        if (this.funcionarios != null) {
            this.funcionarios.forEach(i -> i.setUsuario(null));
        }
        if (funcionarios != null) {
            funcionarios.forEach(i -> i.setUsuario(this));
        }
        this.funcionarios = funcionarios;
    }

    public Usuario funcionarios(Set<Funcionario> funcionarios) {
        this.setFuncionarios(funcionarios);
        return this;
    }

    public Usuario addFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setUsuario(this);
        return this;
    }

    public Usuario removeFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setUsuario(null);
        return this;
    }

    public Set<MacroProcesso> getMacroProcessos() {
        return this.macroProcessos;
    }

    public void setMacroProcessos(Set<MacroProcesso> macroProcessos) {
        if (this.macroProcessos != null) {
            this.macroProcessos.forEach(i -> i.setUsuario(null));
        }
        if (macroProcessos != null) {
            macroProcessos.forEach(i -> i.setUsuario(this));
        }
        this.macroProcessos = macroProcessos;
    }

    public Usuario macroProcessos(Set<MacroProcesso> macroProcessos) {
        this.setMacroProcessos(macroProcessos);
        return this;
    }

    public Usuario addMacroProcesso(MacroProcesso macroProcesso) {
        this.macroProcessos.add(macroProcesso);
        macroProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeMacroProcesso(MacroProcesso macroProcesso) {
        this.macroProcessos.remove(macroProcesso);
        macroProcesso.setUsuario(null);
        return this;
    }

    public Set<MacroProcessoOrganograma> getMacroProcessoOrganogramas() {
        return this.macroProcessoOrganogramas;
    }

    public void setMacroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        if (this.macroProcessoOrganogramas != null) {
            this.macroProcessoOrganogramas.forEach(i -> i.setUsuario(null));
        }
        if (macroProcessoOrganogramas != null) {
            macroProcessoOrganogramas.forEach(i -> i.setUsuario(this));
        }
        this.macroProcessoOrganogramas = macroProcessoOrganogramas;
    }

    public Usuario macroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        this.setMacroProcessoOrganogramas(macroProcessoOrganogramas);
        return this;
    }

    public Usuario addMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.add(macroProcessoOrganograma);
        macroProcessoOrganograma.setUsuario(this);
        return this;
    }

    public Usuario removeMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.remove(macroProcessoOrganograma);
        macroProcessoOrganograma.setUsuario(null);
        return this;
    }

    public Set<Organograma> getOrganogramas() {
        return this.organogramas;
    }

    public void setOrganogramas(Set<Organograma> organogramas) {
        if (this.organogramas != null) {
            this.organogramas.forEach(i -> i.setUsuario(null));
        }
        if (organogramas != null) {
            organogramas.forEach(i -> i.setUsuario(this));
        }
        this.organogramas = organogramas;
    }

    public Usuario organogramas(Set<Organograma> organogramas) {
        this.setOrganogramas(organogramas);
        return this;
    }

    public Usuario addOrganograma(Organograma organograma) {
        this.organogramas.add(organograma);
        organograma.setUsuario(this);
        return this;
    }

    public Usuario removeOrganograma(Organograma organograma) {
        this.organogramas.remove(organograma);
        organograma.setUsuario(null);
        return this;
    }

    public Set<Processo> getProcessos() {
        return this.processos;
    }

    public void setProcessos(Set<Processo> processos) {
        if (this.processos != null) {
            this.processos.forEach(i -> i.setUsuario(null));
        }
        if (processos != null) {
            processos.forEach(i -> i.setUsuario(this));
        }
        this.processos = processos;
    }

    public Usuario processos(Set<Processo> processos) {
        this.setProcessos(processos);
        return this;
    }

    public Usuario addProcesso(Processo processo) {
        this.processos.add(processo);
        processo.setUsuario(this);
        return this;
    }

    public Usuario removeProcesso(Processo processo) {
        this.processos.remove(processo);
        processo.setUsuario(null);
        return this;
    }

    public Set<ClienteExternoProcesso> getClienteExternoProcessos() {
        return this.clienteExternoProcessos;
    }

    public void setClienteExternoProcessos(Set<ClienteExternoProcesso> clienteExternoProcessos) {
        if (this.clienteExternoProcessos != null) {
            this.clienteExternoProcessos.forEach(i -> i.setUsuario(null));
        }
        if (clienteExternoProcessos != null) {
            clienteExternoProcessos.forEach(i -> i.setUsuario(this));
        }
        this.clienteExternoProcessos = clienteExternoProcessos;
    }

    public Usuario clienteExternoProcessos(Set<ClienteExternoProcesso> clienteExternoProcessos) {
        this.setClienteExternoProcessos(clienteExternoProcessos);
        return this;
    }

    public Usuario addClienteExternoProcesso(ClienteExternoProcesso clienteExternoProcesso) {
        this.clienteExternoProcessos.add(clienteExternoProcesso);
        clienteExternoProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeClienteExternoProcesso(ClienteExternoProcesso clienteExternoProcesso) {
        this.clienteExternoProcessos.remove(clienteExternoProcesso);
        clienteExternoProcesso.setUsuario(null);
        return this;
    }

    public Set<ComplianceExternoProcesso> getComplianceExternoProcessos() {
        return this.complianceExternoProcessos;
    }

    public void setComplianceExternoProcessos(Set<ComplianceExternoProcesso> complianceExternoProcessos) {
        if (this.complianceExternoProcessos != null) {
            this.complianceExternoProcessos.forEach(i -> i.setUsuario(null));
        }
        if (complianceExternoProcessos != null) {
            complianceExternoProcessos.forEach(i -> i.setUsuario(this));
        }
        this.complianceExternoProcessos = complianceExternoProcessos;
    }

    public Usuario complianceExternoProcessos(Set<ComplianceExternoProcesso> complianceExternoProcessos) {
        this.setComplianceExternoProcessos(complianceExternoProcessos);
        return this;
    }

    public Usuario addComplianceExternoProcesso(ComplianceExternoProcesso complianceExternoProcesso) {
        this.complianceExternoProcessos.add(complianceExternoProcesso);
        complianceExternoProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeComplianceExternoProcesso(ComplianceExternoProcesso complianceExternoProcesso) {
        this.complianceExternoProcessos.remove(complianceExternoProcesso);
        complianceExternoProcesso.setUsuario(null);
        return this;
    }

    public Set<FornecedorExternoProcesso> getFornecedorExternoProcessos() {
        return this.fornecedorExternoProcessos;
    }

    public void setFornecedorExternoProcessos(Set<FornecedorExternoProcesso> fornecedorExternoProcessos) {
        if (this.fornecedorExternoProcessos != null) {
            this.fornecedorExternoProcessos.forEach(i -> i.setUsuario(null));
        }
        if (fornecedorExternoProcessos != null) {
            fornecedorExternoProcessos.forEach(i -> i.setUsuario(this));
        }
        this.fornecedorExternoProcessos = fornecedorExternoProcessos;
    }

    public Usuario fornecedorExternoProcessos(Set<FornecedorExternoProcesso> fornecedorExternoProcessos) {
        this.setFornecedorExternoProcessos(fornecedorExternoProcessos);
        return this;
    }

    public Usuario addFornecedorExternoProcesso(FornecedorExternoProcesso fornecedorExternoProcesso) {
        this.fornecedorExternoProcessos.add(fornecedorExternoProcesso);
        fornecedorExternoProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeFornecedorExternoProcesso(FornecedorExternoProcesso fornecedorExternoProcesso) {
        this.fornecedorExternoProcessos.remove(fornecedorExternoProcesso);
        fornecedorExternoProcesso.setUsuario(null);
        return this;
    }

    public Set<ClienteInternoProcesso> getClienteInternoProcessos() {
        return this.clienteInternoProcessos;
    }

    public void setClienteInternoProcessos(Set<ClienteInternoProcesso> clienteInternoProcessos) {
        if (this.clienteInternoProcessos != null) {
            this.clienteInternoProcessos.forEach(i -> i.setUsuario(null));
        }
        if (clienteInternoProcessos != null) {
            clienteInternoProcessos.forEach(i -> i.setUsuario(this));
        }
        this.clienteInternoProcessos = clienteInternoProcessos;
    }

    public Usuario clienteInternoProcessos(Set<ClienteInternoProcesso> clienteInternoProcessos) {
        this.setClienteInternoProcessos(clienteInternoProcessos);
        return this;
    }

    public Usuario addClienteInternoProcesso(ClienteInternoProcesso clienteInternoProcesso) {
        this.clienteInternoProcessos.add(clienteInternoProcesso);
        clienteInternoProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeClienteInternoProcesso(ClienteInternoProcesso clienteInternoProcesso) {
        this.clienteInternoProcessos.remove(clienteInternoProcesso);
        clienteInternoProcesso.setUsuario(null);
        return this;
    }

    public Set<FuncionarioOrganograma> getFuncionarioOrganogramas() {
        return this.funcionarioOrganogramas;
    }

    public void setFuncionarioOrganogramas(Set<FuncionarioOrganograma> funcionarioOrganogramas) {
        if (this.funcionarioOrganogramas != null) {
            this.funcionarioOrganogramas.forEach(i -> i.setUsuario(null));
        }
        if (funcionarioOrganogramas != null) {
            funcionarioOrganogramas.forEach(i -> i.setUsuario(this));
        }
        this.funcionarioOrganogramas = funcionarioOrganogramas;
    }

    public Usuario funcionarioOrganogramas(Set<FuncionarioOrganograma> funcionarioOrganogramas) {
        this.setFuncionarioOrganogramas(funcionarioOrganogramas);
        return this;
    }

    public Usuario addFuncionarioOrganograma(FuncionarioOrganograma funcionarioOrganograma) {
        this.funcionarioOrganogramas.add(funcionarioOrganograma);
        funcionarioOrganograma.setUsuario(this);
        return this;
    }

    public Usuario removeFuncionarioOrganograma(FuncionarioOrganograma funcionarioOrganograma) {
        this.funcionarioOrganogramas.remove(funcionarioOrganograma);
        funcionarioOrganograma.setUsuario(null);
        return this;
    }

    public Set<ComplianceInternoProcesso> getComplianceInternoProcessos() {
        return this.complianceInternoProcessos;
    }

    public void setComplianceInternoProcessos(Set<ComplianceInternoProcesso> complianceInternoProcessos) {
        if (this.complianceInternoProcessos != null) {
            this.complianceInternoProcessos.forEach(i -> i.setUsuario(null));
        }
        if (complianceInternoProcessos != null) {
            complianceInternoProcessos.forEach(i -> i.setUsuario(this));
        }
        this.complianceInternoProcessos = complianceInternoProcessos;
    }

    public Usuario complianceInternoProcessos(Set<ComplianceInternoProcesso> complianceInternoProcessos) {
        this.setComplianceInternoProcessos(complianceInternoProcessos);
        return this;
    }

    public Usuario addComplianceInternoProcesso(ComplianceInternoProcesso complianceInternoProcesso) {
        this.complianceInternoProcessos.add(complianceInternoProcesso);
        complianceInternoProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeComplianceInternoProcesso(ComplianceInternoProcesso complianceInternoProcesso) {
        this.complianceInternoProcessos.remove(complianceInternoProcesso);
        complianceInternoProcesso.setUsuario(null);
        return this;
    }

    public Set<FornecedorInternoProcesso> getFornecedorInternoProcessos() {
        return this.fornecedorInternoProcessos;
    }

    public void setFornecedorInternoProcessos(Set<FornecedorInternoProcesso> fornecedorInternoProcessos) {
        if (this.fornecedorInternoProcessos != null) {
            this.fornecedorInternoProcessos.forEach(i -> i.setUsuario(null));
        }
        if (fornecedorInternoProcessos != null) {
            fornecedorInternoProcessos.forEach(i -> i.setUsuario(this));
        }
        this.fornecedorInternoProcessos = fornecedorInternoProcessos;
    }

    public Usuario fornecedorInternoProcessos(Set<FornecedorInternoProcesso> fornecedorInternoProcessos) {
        this.setFornecedorInternoProcessos(fornecedorInternoProcessos);
        return this;
    }

    public Usuario addFornecedorInternoProcesso(FornecedorInternoProcesso fornecedorInternoProcesso) {
        this.fornecedorInternoProcessos.add(fornecedorInternoProcesso);
        fornecedorInternoProcesso.setUsuario(this);
        return this;
    }

    public Usuario removeFornecedorInternoProcesso(FornecedorInternoProcesso fornecedorInternoProcesso) {
        this.fornecedorInternoProcessos.remove(fornecedorInternoProcesso);
        fornecedorInternoProcesso.setUsuario(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Usuario empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Usuario)) {
            return false;
        }
        return id != null && id.equals(((Usuario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Usuario{" +
            "id=" + getId() +
            ", idnVarUsuario='" + getIdnVarUsuario() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnVarUsuarioCadastro='" + getIdnVarUsuarioCadastro() + "'" +
            ", nVarSenha='" + getnVarSenha() + "'" +
            "}";
    }
}
