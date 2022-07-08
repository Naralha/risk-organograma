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
 * A Organograma.
 */
@Entity
@Table(name = "organograma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organograma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_organograma", nullable = false)
    private String idnVarOrganograma;

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

    @NotNull
    @Column(name = "n_var_nome", nullable = false)
    private String nVarNome;

    @Column(name = "n_var_descricao")
    private String nVarDescricao;

    @Column(name = "idn_var_pai_organograma")
    private String idnVarPaiOrganograma;

    @NotNull
    @Column(name = "idnvar_usuario", nullable = false)
    private String idnvarUsuario;

    @NotNull
    @Column(name = "idn_var_roof_top", nullable = false)
    private String idnVarRoofTop;

    @OneToMany(mappedBy = "organograma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "usuario", "macroProcesso", "organograma" }, allowSetters = true)
    private Set<MacroProcessoOrganograma> macroProcessoOrganogramas = new HashSet<>();

    @OneToMany(mappedBy = "organograma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organograma", "processo", "usuario" }, allowSetters = true)
    private Set<ClienteInternoProcesso> clienteInternoProcessos = new HashSet<>();

    @OneToMany(mappedBy = "organograma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "organograma", "usuario" }, allowSetters = true)
    private Set<FuncionarioOrganograma> funcionarioOrganogramas = new HashSet<>();

    @OneToMany(mappedBy = "organograma")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "organograma", "processo", "usuario" }, allowSetters = true)
    private Set<FornecedorInternoProcesso> fornecedorInternoProcessos = new HashSet<>();

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

    public Organograma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarOrganograma() {
        return this.idnVarOrganograma;
    }

    public Organograma idnVarOrganograma(String idnVarOrganograma) {
        this.setIdnVarOrganograma(idnVarOrganograma);
        return this;
    }

    public void setIdnVarOrganograma(String idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public Organograma idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public Organograma nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public Organograma nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarPaiOrganograma() {
        return this.idnVarPaiOrganograma;
    }

    public Organograma idnVarPaiOrganograma(String idnVarPaiOrganograma) {
        this.setIdnVarPaiOrganograma(idnVarPaiOrganograma);
        return this;
    }

    public void setIdnVarPaiOrganograma(String idnVarPaiOrganograma) {
        this.idnVarPaiOrganograma = idnVarPaiOrganograma;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public Organograma idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public String getIdnVarRoofTop() {
        return this.idnVarRoofTop;
    }

    public Organograma idnVarRoofTop(String idnVarRoofTop) {
        this.setIdnVarRoofTop(idnVarRoofTop);
        return this;
    }

    public void setIdnVarRoofTop(String idnVarRoofTop) {
        this.idnVarRoofTop = idnVarRoofTop;
    }

    public Set<MacroProcessoOrganograma> getMacroProcessoOrganogramas() {
        return this.macroProcessoOrganogramas;
    }

    public void setMacroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        if (this.macroProcessoOrganogramas != null) {
            this.macroProcessoOrganogramas.forEach(i -> i.setOrganograma(null));
        }
        if (macroProcessoOrganogramas != null) {
            macroProcessoOrganogramas.forEach(i -> i.setOrganograma(this));
        }
        this.macroProcessoOrganogramas = macroProcessoOrganogramas;
    }

    public Organograma macroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        this.setMacroProcessoOrganogramas(macroProcessoOrganogramas);
        return this;
    }

    public Organograma addMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.add(macroProcessoOrganograma);
        macroProcessoOrganograma.setOrganograma(this);
        return this;
    }

    public Organograma removeMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.remove(macroProcessoOrganograma);
        macroProcessoOrganograma.setOrganograma(null);
        return this;
    }

    public Set<ClienteInternoProcesso> getClienteInternoProcessos() {
        return this.clienteInternoProcessos;
    }

    public void setClienteInternoProcessos(Set<ClienteInternoProcesso> clienteInternoProcessos) {
        if (this.clienteInternoProcessos != null) {
            this.clienteInternoProcessos.forEach(i -> i.setOrganograma(null));
        }
        if (clienteInternoProcessos != null) {
            clienteInternoProcessos.forEach(i -> i.setOrganograma(this));
        }
        this.clienteInternoProcessos = clienteInternoProcessos;
    }

    public Organograma clienteInternoProcessos(Set<ClienteInternoProcesso> clienteInternoProcessos) {
        this.setClienteInternoProcessos(clienteInternoProcessos);
        return this;
    }

    public Organograma addClienteInternoProcesso(ClienteInternoProcesso clienteInternoProcesso) {
        this.clienteInternoProcessos.add(clienteInternoProcesso);
        clienteInternoProcesso.setOrganograma(this);
        return this;
    }

    public Organograma removeClienteInternoProcesso(ClienteInternoProcesso clienteInternoProcesso) {
        this.clienteInternoProcessos.remove(clienteInternoProcesso);
        clienteInternoProcesso.setOrganograma(null);
        return this;
    }

    public Set<FuncionarioOrganograma> getFuncionarioOrganogramas() {
        return this.funcionarioOrganogramas;
    }

    public void setFuncionarioOrganogramas(Set<FuncionarioOrganograma> funcionarioOrganogramas) {
        if (this.funcionarioOrganogramas != null) {
            this.funcionarioOrganogramas.forEach(i -> i.setOrganograma(null));
        }
        if (funcionarioOrganogramas != null) {
            funcionarioOrganogramas.forEach(i -> i.setOrganograma(this));
        }
        this.funcionarioOrganogramas = funcionarioOrganogramas;
    }

    public Organograma funcionarioOrganogramas(Set<FuncionarioOrganograma> funcionarioOrganogramas) {
        this.setFuncionarioOrganogramas(funcionarioOrganogramas);
        return this;
    }

    public Organograma addFuncionarioOrganograma(FuncionarioOrganograma funcionarioOrganograma) {
        this.funcionarioOrganogramas.add(funcionarioOrganograma);
        funcionarioOrganograma.setOrganograma(this);
        return this;
    }

    public Organograma removeFuncionarioOrganograma(FuncionarioOrganograma funcionarioOrganograma) {
        this.funcionarioOrganogramas.remove(funcionarioOrganograma);
        funcionarioOrganograma.setOrganograma(null);
        return this;
    }

    public Set<FornecedorInternoProcesso> getFornecedorInternoProcessos() {
        return this.fornecedorInternoProcessos;
    }

    public void setFornecedorInternoProcessos(Set<FornecedorInternoProcesso> fornecedorInternoProcessos) {
        if (this.fornecedorInternoProcessos != null) {
            this.fornecedorInternoProcessos.forEach(i -> i.setOrganograma(null));
        }
        if (fornecedorInternoProcessos != null) {
            fornecedorInternoProcessos.forEach(i -> i.setOrganograma(this));
        }
        this.fornecedorInternoProcessos = fornecedorInternoProcessos;
    }

    public Organograma fornecedorInternoProcessos(Set<FornecedorInternoProcesso> fornecedorInternoProcessos) {
        this.setFornecedorInternoProcessos(fornecedorInternoProcessos);
        return this;
    }

    public Organograma addFornecedorInternoProcesso(FornecedorInternoProcesso fornecedorInternoProcesso) {
        this.fornecedorInternoProcessos.add(fornecedorInternoProcesso);
        fornecedorInternoProcesso.setOrganograma(this);
        return this;
    }

    public Organograma removeFornecedorInternoProcesso(FornecedorInternoProcesso fornecedorInternoProcesso) {
        this.fornecedorInternoProcessos.remove(fornecedorInternoProcesso);
        fornecedorInternoProcesso.setOrganograma(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Organograma empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Organograma usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organograma)) {
            return false;
        }
        return id != null && id.equals(((Organograma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organograma{" +
            "id=" + getId() +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarPaiOrganograma='" + getIdnVarPaiOrganograma() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            ", idnVarRoofTop='" + getIdnVarRoofTop() + "'" +
            "}";
    }
}
