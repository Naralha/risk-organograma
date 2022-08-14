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
 * A ComplianceInternoProcesso.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "compliance_interno_processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ComplianceInternoProcesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_compliance_interno", nullable = false)
    private String idnVarComplianceInterno;

    @NotNull
    @Column(name = "idn_var_processo", nullable = false)
    private String idnVarProcesso;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @ManyToOne
    @JsonIgnoreProperties(value = { "complianceInternoProcessos", "empresa", "usuario" }, allowSetters = true)
    private ComplianceInterno complianceInterno;

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

    public ComplianceInternoProcesso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarComplianceInterno() {
        return this.idnVarComplianceInterno;
    }

    public ComplianceInternoProcesso idnVarComplianceInterno(String idnVarComplianceInterno) {
        this.setIdnVarComplianceInterno(idnVarComplianceInterno);
        return this;
    }

    public void setIdnVarComplianceInterno(String idnVarComplianceInterno) {
        this.idnVarComplianceInterno = idnVarComplianceInterno;
    }

    public String getIdnVarProcesso() {
        return this.idnVarProcesso;
    }

    public ComplianceInternoProcesso idnVarProcesso(String idnVarProcesso) {
        this.setIdnVarProcesso(idnVarProcesso);
        return this;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public ComplianceInternoProcesso idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public ComplianceInterno getComplianceInterno() {
        return this.complianceInterno;
    }

    public void setComplianceInterno(ComplianceInterno complianceInterno) {
        this.complianceInterno = complianceInterno;
    }

    public ComplianceInternoProcesso complianceInterno(ComplianceInterno complianceInterno) {
        this.setComplianceInterno(complianceInterno);
        return this;
    }

    public Processo getProcesso() {
        return this.processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public ComplianceInternoProcesso processo(Processo processo) {
        this.setProcesso(processo);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ComplianceInternoProcesso usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplianceInternoProcesso)) {
            return false;
        }
        return id != null && id.equals(((ComplianceInternoProcesso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceInternoProcesso{" +
            "id=" + getId() +
            ", idnVarComplianceInterno='" + getIdnVarComplianceInterno() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
