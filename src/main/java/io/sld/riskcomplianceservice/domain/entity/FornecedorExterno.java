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
 * A FornecedorExterno.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "fornecedor_externo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FornecedorExterno implements Serializable {

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

    @OneToMany(mappedBy = "fornecedorExterno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fornecedorExterno", "processo", "usuario" }, allowSetters = true)
    private Set<FornecedorExternoProcesso> fornecedorExternoProcessos = new HashSet<>();

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

    public FornecedorExterno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarFornecedorExterno() {
        return this.idnVarFornecedorExterno;
    }

    public FornecedorExterno idnVarFornecedorExterno(String idnVarFornecedorExterno) {
        this.setIdnVarFornecedorExterno(idnVarFornecedorExterno);
        return this;
    }

    public void setIdnVarFornecedorExterno(String idnVarFornecedorExterno) {
        this.idnVarFornecedorExterno = idnVarFornecedorExterno;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public FornecedorExterno nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public FornecedorExterno nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public FornecedorExterno idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public FornecedorExterno idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Set<FornecedorExternoProcesso> getFornecedorExternoProcessos() {
        return this.fornecedorExternoProcessos;
    }

    public void setFornecedorExternoProcessos(Set<FornecedorExternoProcesso> fornecedorExternoProcessos) {
        if (this.fornecedorExternoProcessos != null) {
            this.fornecedorExternoProcessos.forEach(i -> i.setFornecedorExterno(null));
        }
        if (fornecedorExternoProcessos != null) {
            fornecedorExternoProcessos.forEach(i -> i.setFornecedorExterno(this));
        }
        this.fornecedorExternoProcessos = fornecedorExternoProcessos;
    }

    public FornecedorExterno fornecedorExternoProcessos(Set<FornecedorExternoProcesso> fornecedorExternoProcessos) {
        this.setFornecedorExternoProcessos(fornecedorExternoProcessos);
        return this;
    }

    public FornecedorExterno addFornecedorExternoProcesso(FornecedorExternoProcesso fornecedorExternoProcesso) {
        this.fornecedorExternoProcessos.add(fornecedorExternoProcesso);
        fornecedorExternoProcesso.setFornecedorExterno(this);
        return this;
    }

    public FornecedorExterno removeFornecedorExternoProcesso(FornecedorExternoProcesso fornecedorExternoProcesso) {
        this.fornecedorExternoProcessos.remove(fornecedorExternoProcesso);
        fornecedorExternoProcesso.setFornecedorExterno(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public FornecedorExterno empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public FornecedorExterno usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorExterno)) {
            return false;
        }
        return id != null && id.equals(((FornecedorExterno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorExterno{" +
            "id=" + getId() +
            ", idnVarFornecedorExterno='" + getIdnVarFornecedorExterno() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
