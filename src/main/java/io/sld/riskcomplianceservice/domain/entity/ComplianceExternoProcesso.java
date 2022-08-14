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
 * A ComplianceExternoProcesso.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "compliance_externo_processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ComplianceExternoProcesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_compliance_externo", nullable = false)
    private String idnVarComplianceExterno;

    @NotNull
    @Column(name = "idn_var_processo", nullable = false)
    private String idnVarProcesso;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "complianceExternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private ComplianceExterno complianceExterno;

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

    public ComplianceExternoProcesso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarComplianceExterno() {
        return this.idnVarComplianceExterno;
    }

    public ComplianceExternoProcesso idnVarComplianceExterno(String idnVarComplianceExterno) {
        this.setIdnVarComplianceExterno(idnVarComplianceExterno);
        return this;
    }

    public void setIdnVarComplianceExterno(String idnVarComplianceExterno) {
        this.idnVarComplianceExterno = idnVarComplianceExterno;
    }

    public String getIdnVarProcesso() {
        return this.idnVarProcesso;
    }

    public ComplianceExternoProcesso idnVarProcesso(String idnVarProcesso) {
        this.setIdnVarProcesso(idnVarProcesso);
        return this;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public ComplianceExternoProcesso idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public ComplianceExterno getComplianceExterno() {
        return this.complianceExterno;
    }

    public void setComplianceExterno(ComplianceExterno complianceExterno) {
        this.complianceExterno = complianceExterno;
    }

    public ComplianceExternoProcesso complianceExterno(ComplianceExterno complianceExterno) {
        this.setComplianceExterno(complianceExterno);
        return this;
    }

    public Processo getProcesso() {
        return this.processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public ComplianceExternoProcesso processo(Processo processo) {
        this.setProcesso(processo);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ComplianceExternoProcesso usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplianceExternoProcesso)) {
            return false;
        }
        return id != null && id.equals(((ComplianceExternoProcesso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceExternoProcesso{" +
            "id=" + getId() +
            ", idnVarComplianceExterno='" + getIdnVarComplianceExterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
