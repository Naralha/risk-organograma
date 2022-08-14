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
@ToString(callSuper = true)
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

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

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

    public Long getId() {
        return this.id;
    }

    public Funcionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarFuncionario() {
        return this.idnVarFuncionario;
    }

    public Funcionario idnVarFuncionario(String idnVarFuncionario) {
        this.setIdnVarFuncionario(idnVarFuncionario);
        return this;
    }

    public void setIdnVarFuncionario(String idnVarFuncionario) {
        this.idnVarFuncionario = idnVarFuncionario;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public Funcionario nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarEmail() {
        return this.nVarEmail;
    }

    public Funcionario nVarEmail(String nVarEmail) {
        this.setnVarEmail(nVarEmail);
        return this;
    }

    public void setnVarEmail(String nVarEmail) {
        this.nVarEmail = nVarEmail;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public Funcionario nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public Funcionario idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public Funcionario idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Set<FuncionarioOrganograma> getFuncionarioOrganogramas() {
        return this.funcionarioOrganogramas;
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

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Funcionario empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarEmail='" + getnVarEmail() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
