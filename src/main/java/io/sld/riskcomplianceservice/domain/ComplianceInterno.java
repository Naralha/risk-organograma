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
 * A ComplianceInterno.
 */
@Entity
@Table(name = "compliance_interno")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ComplianceInterno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_compliante_interno", nullable = false)
    private String idnVarComplianteInterno;

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

    @OneToMany(mappedBy = "complianceInterno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "complianceInterno", "processo", "usuario" }, allowSetters = true)
    private Set<ComplianceInternoProcesso> complianceInternoProcessos = new HashSet<>();

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

    public ComplianceInterno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarComplianteInterno() {
        return this.idnVarComplianteInterno;
    }

    public ComplianceInterno idnVarComplianteInterno(String idnVarComplianteInterno) {
        this.setIdnVarComplianteInterno(idnVarComplianteInterno);
        return this;
    }

    public void setIdnVarComplianteInterno(String idnVarComplianteInterno) {
        this.idnVarComplianteInterno = idnVarComplianteInterno;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public ComplianceInterno nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public ComplianceInterno nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public ComplianceInterno idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public ComplianceInterno idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Set<ComplianceInternoProcesso> getComplianceInternoProcessos() {
        return this.complianceInternoProcessos;
    }

    public void setComplianceInternoProcessos(Set<ComplianceInternoProcesso> complianceInternoProcessos) {
        if (this.complianceInternoProcessos != null) {
            this.complianceInternoProcessos.forEach(i -> i.setComplianceInterno(null));
        }
        if (complianceInternoProcessos != null) {
            complianceInternoProcessos.forEach(i -> i.setComplianceInterno(this));
        }
        this.complianceInternoProcessos = complianceInternoProcessos;
    }

    public ComplianceInterno complianceInternoProcessos(Set<ComplianceInternoProcesso> complianceInternoProcessos) {
        this.setComplianceInternoProcessos(complianceInternoProcessos);
        return this;
    }

    public ComplianceInterno addComplianceInternoProcesso(ComplianceInternoProcesso complianceInternoProcesso) {
        this.complianceInternoProcessos.add(complianceInternoProcesso);
        complianceInternoProcesso.setComplianceInterno(this);
        return this;
    }

    public ComplianceInterno removeComplianceInternoProcesso(ComplianceInternoProcesso complianceInternoProcesso) {
        this.complianceInternoProcessos.remove(complianceInternoProcesso);
        complianceInternoProcesso.setComplianceInterno(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ComplianceInterno empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ComplianceInterno usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComplianceInterno)) {
            return false;
        }
        return id != null && id.equals(((ComplianceInterno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComplianceInterno{" +
            "id=" + getId() +
            ", idnVarComplianteInterno='" + getIdnVarComplianteInterno() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
