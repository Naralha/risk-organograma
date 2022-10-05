package io.sld.riskcomplianceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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
 * A Empresa.
 */
@Getter
@Setter
@NoArgsConstructor
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

//    @OneToMany(mappedBy = "empresa")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "macroProcessoOrganogramas", "empresa", "usuario" }, allowSetters = true)
//    private Set<MacroProcesso> macroProcessos = new HashSet<>();

//    @OneToMany(mappedBy = "empresa")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(value = { "empresa", "usuario", "macroProcesso", "organograma" }, allowSetters = true)
//    private Set<MacroProcessoOrganograma> macroProcessoOrganogramas = new HashSet<>();

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

//    @OneToMany(mappedBy = "empresa")
//    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//    @JsonIgnoreProperties(
//        value = {
//            "clienteExternoProcessos",
//            "complianceExternoProcessos",
//            "fornecedorExternoProcessos",
//            "clienteInternoProcessos",
//            "complianceInternoProcessos",
//            "fornecedorInternoProcessos",
//            "empresa",
//            "usuario",
//        },
//        allowSetters = true
//    )
//    private Set<Processo> processos = new HashSet<>();

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

    public Empresa idNVarEmpresa(String idnVarEmpresa){
        setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }
    public Empresa nVarNome(String nVarNome){
        setNVarNome(nVarNome);
        return this;
    }
    public Empresa nVarDescricao(String nVarDescricao){
        setNVarDescricao(nVarDescricao);
        return this;
    }


    public Empresa complianceExternos(Set<ComplianceExterno> complianceExternos) {
        this.setComplianceExternos(complianceExternos);
        return this;
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
            ", nVarNome='" + getNVarNome() + "'" +
            ", nVarDescricao='" + getNVarDescricao() + "'" +
            "}";
    }
}
