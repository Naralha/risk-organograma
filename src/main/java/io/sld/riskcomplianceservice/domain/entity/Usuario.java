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
 * A Usuario.
 */
@Getter
@Setter
@NoArgsConstructor
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

    @Column(name = "idn_var_usuario_cadastro")
    private String idnVarUsuarioCadastro;

    @NotNull
    @Column(name = "n_var_senha", nullable = false)
    private String nVarSenha;

    @OneToMany(mappedBy = "usuario")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionarioOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private Set<Funcionario> funcionarios = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "funcionario", "organograma", "usuario" }, allowSetters = true)
    private Set<FuncionarioOrganograma> funcionarioOrganogramas = new HashSet<>();

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

    public Usuario id(Long id) {
        this.setId(id);
        return this;
    }

    public Usuario idnVarUsuario(String idnVarUsuario) {
        this.setIdnVarUsuario(idnVarUsuario);
        return this;
    }

    public Usuario nVarNome(String nVarNome) {
        this.setNVarNome(nVarNome);
        return this;
    }

    public Usuario idnVarUsuarioCadastro(String idnVarUsuarioCadastro) {
        this.setIdnVarUsuarioCadastro(idnVarUsuarioCadastro);
        return this;
    }

    public Usuario nVarSenha(String nVarSenha) {
        this.setNVarSenha(nVarSenha);
        return this;
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
                ", nVarNome='" + getNVarNome() + "'" +
//            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
                ", idnVarUsuarioCadastro='" + getIdnVarUsuarioCadastro() + "'" +
                ", nVarSenha='" + getNVarSenha() + "'" +
                "}";
    }
}
