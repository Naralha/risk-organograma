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
 * A Funcionario.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_funcionario", nullable = false)
    private String idnVarFuncionario;

    @NotNull
    @Column(name = "n_var_nome", nullable = false)
    private String nVarNome;

    @NotNull
    @Column(name = "n_var_email", nullable = false)
    private String nVarEmail;

    @Column(name = "n_var_descricao")
    private String nVarDescricao;

//    @NotNull
//    @Column(name = "idn_var_empresa", nullable = false)
//    private String idnVarEmpresa;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @OneToMany(mappedBy = "funcionario")
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



    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }





    public Funcionario idnVarFuncionario(String idnVarFuncionario) {
        this.setIdnVarFuncionario(idnVarFuncionario);
        return this;
    }





    public Funcionario nVarNome(String nVarNome) {
        this.setNVarNome(nVarNome);
        return this;
    }



    public Funcionario nVarEmail(String nVarEmail) {
        this.setNVarEmail(nVarEmail);
        return this;
    }



    public Funcionario nVarDescricao(String nVarDescricao) {
        this.setNVarDescricao(nVarDescricao);
        return this;
    }



//    public Funcionario idnVarEmpresa(String idnVarEmpresa) {
//        this.setIdnVarEmpresa(idnVarEmpresa);
//        return this;
//    }



    public Funcionario idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }



    public void setFuncionarioOrganogramas(Set<FuncionarioOrganograma> funcionarioOrganogramas) {
        if (this.funcionarioOrganogramas != null) {
            this.funcionarioOrganogramas.forEach(i -> i.setFuncionario(null));
        }
        if (funcionarioOrganogramas != null) {
            funcionarioOrganogramas.forEach(i -> i.setFuncionario(this));
        }
        this.funcionarioOrganogramas = funcionarioOrganogramas;
    }

    public Funcionario funcionarioOrganogramas(Set<FuncionarioOrganograma> funcionarioOrganogramas) {
        this.setFuncionarioOrganogramas(funcionarioOrganogramas);
        return this;
    }

    public Funcionario addFuncionarioOrganograma(FuncionarioOrganograma funcionarioOrganograma) {
        this.funcionarioOrganogramas.add(funcionarioOrganograma);
        funcionarioOrganograma.setFuncionario(this);
        return this;
    }

    public Funcionario removeFuncionarioOrganograma(FuncionarioOrganograma funcionarioOrganograma) {
        this.funcionarioOrganogramas.remove(funcionarioOrganograma);
        funcionarioOrganograma.setFuncionario(null);
        return this;
    }



    public Funcionario empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Funcionario usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return id != null && id.equals(((Funcionario) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", idnVarFuncionario='" + getIdnVarFuncionario() + "'" +
            ", nVarNome='" + getNVarNome() + "'" +
            ", nVarEmail='" + getNVarEmail() + "'" +
            ", nVarDescricao='" + getNVarDescricao() + "'" +
//            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
