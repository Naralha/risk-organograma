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
 * A FornecedorExternoProcesso.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "fornecedor_externo_processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FornecedorExternoProcesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_fornecedor_externo", nullable = false)
    private String idnVarFornecedorExterno;

    @NotNull
    @Column(name = "idn_var_processo", nullable = false)
    private String idnVarProcesso;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fornecedorExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private FornecedorExterno fornecedorExterno;

    @ManyToOne
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
    private Processo processo;

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

    public FornecedorExternoProcesso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarFornecedorExterno() {
        return this.idnVarFornecedorExterno;
    }

    public FornecedorExternoProcesso idnVarFornecedorExterno(String idnVarFornecedorExterno) {
        this.setIdnVarFornecedorExterno(idnVarFornecedorExterno);
        return this;
    }

    public void setIdnVarFornecedorExterno(String idnVarFornecedorExterno) {
        this.idnVarFornecedorExterno = idnVarFornecedorExterno;
    }

    public String getIdnVarProcesso() {
        return this.idnVarProcesso;
    }

    public FornecedorExternoProcesso idnVarProcesso(String idnVarProcesso) {
        this.setIdnVarProcesso(idnVarProcesso);
        return this;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public FornecedorExternoProcesso idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public FornecedorExterno getFornecedorExterno() {
        return this.fornecedorExterno;
    }

    public void setFornecedorExterno(FornecedorExterno fornecedorExterno) {
        this.fornecedorExterno = fornecedorExterno;
    }

    public FornecedorExternoProcesso fornecedorExterno(FornecedorExterno fornecedorExterno) {
        this.setFornecedorExterno(fornecedorExterno);
        return this;
    }

    public Processo getProcesso() {
        return this.processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public FornecedorExternoProcesso processo(Processo processo) {
        this.setProcesso(processo);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FornecedorExternoProcesso usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorExternoProcesso)) {
            return false;
        }
        return id != null && id.equals(((FornecedorExternoProcesso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorExternoProcesso{" +
            "id=" + getId() +
            ", idnVarFornecedorExterno='" + getIdnVarFornecedorExterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
