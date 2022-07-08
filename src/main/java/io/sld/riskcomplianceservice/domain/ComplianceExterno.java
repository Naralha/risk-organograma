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
 * A ComplianceExterno.
 */
@Entity
@Table(name = "compliance_externo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ComplianceExterno implements Serializable {

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
    @Column(name = "n_var_nome", nullable = false)
    private String nVarNome;

    @Column(name = "n_var_descricao")
    private String nVarDescricao;

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @OneToMany(mappedBy = "complianceExterno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceExterno", "processo", "usuario" }, allowSetters = true)
    private Set<ComplianceExternoProcesso> complianceExternoProcessos = new HashSet<>();

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

    public ComplianceExterno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarComplianceExterno() {
        return this.idnVarComplianceExterno;
    }

    public ComplianceExterno idnVarComplianceExterno(String idnVarComplianceExterno) {
        this.setIdnVarComplianceExterno(idnVarComplianceExterno);
        return this;
    }

    public void setIdnVarComplianceExterno(String idnVarComplianceExterno) {
        this.idnVarComplianceExterno = idnVarComplianceExterno;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public ComplianceExterno nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public ComplianceExterno nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public ComplianceExterno idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public ComplianceExterno idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Set<ComplianceExternoProcesso> getComplianceExternoProcessos() {
        return this.complianceExternoProcessos;
    }

    public void setComplianceExternoProcessos(Set<ComplianceExternoProcesso> complianceExternoProcessos) {
        if (this.complianceExternoProcessos != null) {
            this.complianceExternoProcessos.forEach(i -> i.setComplianceExterno(null));
        }
        if (complianceExternoProcessos != null) {
            complianceExternoProcessos.forEach(i -> i.setComplianceExterno(this));
        }
        this.complianceExternoProcessos = complianceExternoProcessos;
    }

    public ComplianceExterno complianceExternoProcessos(Set<ComplianceExternoProcesso> complianceExternoProcessos) {
        this.setComplianceExternoProcessos(complianceExternoProcessos);
        return this;
    }

    public ComplianceExterno addComplianceExternoProcesso(ComplianceExternoProcesso complianceExternoProcesso) {
        this.complianceExternoProcessos.add(complianceExternoProcesso);
        complianceExternoProcesso.setComplianceExterno(this);
        return this;
    }

    public ComplianceExterno removeComplianceExternoProcesso(ComplianceExternoProcesso complianceExternoProcesso) {
        this.complianceExternoProcessos.remove(complianceExternoProcesso);
        complianceExternoProcesso.setComplianceExterno(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ComplianceExterno empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ComplianceExterno usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplianceExterno)) {
            return false;
        }
        return id != null && id.equals(((ComplianceExterno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceExterno{" +
            "id=" + getId() +
            ", idnVarComplianceExterno='" + getIdnVarComplianceExterno() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
