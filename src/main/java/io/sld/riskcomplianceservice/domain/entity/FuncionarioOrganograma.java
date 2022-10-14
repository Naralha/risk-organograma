package io.sld.riskcomplianceservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuncionarioOrganograma.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "funcionario_organograma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FuncionarioOrganograma implements Serializable {

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
    @Column(name = "idn_var_organograma", nullable = false)
    private String idnVarOrganograma;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "funcionarioOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private Funcionario funcionario;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "funcionarioOrganogramas",
            "empresa",
            "usuario",
        },
        allowSetters = true
    )
    private Organograma organograma;

    @ManyToOne
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "organogramas",
            "funcionarioOrganogramas",
            "empresa",
        },
        allowSetters = true
    )
    private Usuario usuario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuncionarioOrganograma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarFuncionario() {
        return this.idnVarFuncionario;
    }

    public FuncionarioOrganograma idnVarFuncionario(String idnVarFuncionario) {
        this.setIdnVarFuncionario(idnVarFuncionario);
        return this;
    }

    public void setIdnVarFuncionario(String idnVarFuncionario) {
        this.idnVarFuncionario = idnVarFuncionario;
    }

    public String getIdnVarOrganograma() {
        return this.idnVarOrganograma;
    }

    public FuncionarioOrganograma idnVarOrganograma(String idnVarOrganograma) {
        this.setIdnVarOrganograma(idnVarOrganograma);
        return this;
    }

    public void setIdnVarOrganograma(String idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public FuncionarioOrganograma idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public FuncionarioOrganograma funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    public Organograma getOrganograma() {
        return this.organograma;
    }

    public void setOrganograma(Organograma organograma) {
        this.organograma = organograma;
    }

    public FuncionarioOrganograma organograma(Organograma organograma) {
        this.setOrganograma(organograma);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FuncionarioOrganograma usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncionarioOrganograma)) {
            return false;
        }
        return id != null && id.equals(((FuncionarioOrganograma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionarioOrganograma{" +
            "id=" + getId() +
            ", idnVarFuncionario='" + getIdnVarFuncionario() + "'" +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
