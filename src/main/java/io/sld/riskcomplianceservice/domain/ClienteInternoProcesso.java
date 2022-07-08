package io.sld.riskcomplianceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ClienteInternoProcesso.
 */
@Entity
@Table(name = "cliente_interno_processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClienteInternoProcesso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_processo", nullable = false)
    private String idnVarProcesso;

    @NotNull
    @Column(name = "idn_var_organograma", nullable = false)
    private String idnVarOrganograma;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @ManyToOne
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
    private Organograma organograma;

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

    public ClienteInternoProcesso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarProcesso() {
        return this.idnVarProcesso;
    }

    public ClienteInternoProcesso idnVarProcesso(String idnVarProcesso) {
        this.setIdnVarProcesso(idnVarProcesso);
        return this;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdnVarOrganograma() {
        return this.idnVarOrganograma;
    }

    public ClienteInternoProcesso idnVarOrganograma(String idnVarOrganograma) {
        this.setIdnVarOrganograma(idnVarOrganograma);
        return this;
    }

    public void setIdnVarOrganograma(String idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public ClienteInternoProcesso idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Organograma getOrganograma() {
        return this.organograma;
    }

    public void setOrganograma(Organograma organograma) {
        this.organograma = organograma;
    }

    public ClienteInternoProcesso organograma(Organograma organograma) {
        this.setOrganograma(organograma);
        return this;
    }

    public Processo getProcesso() {
        return this.processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public ClienteInternoProcesso processo(Processo processo) {
        this.setProcesso(processo);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ClienteInternoProcesso usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteInternoProcesso)) {
            return false;
        }
        return id != null && id.equals(((ClienteInternoProcesso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteInternoProcesso{" +
            "id=" + getId() +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
