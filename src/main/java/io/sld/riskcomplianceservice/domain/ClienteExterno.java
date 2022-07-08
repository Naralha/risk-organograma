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
 * A ClienteExterno.
 */
@Entity
@Table(name = "cliente_externo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClienteExterno implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_cliente_externo", nullable = false)
    private String idnVarClienteExterno;

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

    @OneToMany(mappedBy = "clienteExterno")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "clienteExterno", "processo", "usuario" }, allowSetters = true)
    private Set<ClienteExternoProcesso> clienteExternoProcessos = new HashSet<>();

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

    public ClienteExterno id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarClienteExterno() {
        return this.idnVarClienteExterno;
    }

    public ClienteExterno idnVarClienteExterno(String idnVarClienteExterno) {
        this.setIdnVarClienteExterno(idnVarClienteExterno);
        return this;
    }

    public void setIdnVarClienteExterno(String idnVarClienteExterno) {
        this.idnVarClienteExterno = idnVarClienteExterno;
    }

    public String getnVarNome() {
        return this.nVarNome;
    }

    public ClienteExterno nVarNome(String nVarNome) {
        this.setnVarNome(nVarNome);
        return this;
    }

    public void setnVarNome(String nVarNome) {
        this.nVarNome = nVarNome;
    }

    public String getnVarDescricao() {
        return this.nVarDescricao;
    }

    public ClienteExterno nVarDescricao(String nVarDescricao) {
        this.setnVarDescricao(nVarDescricao);
        return this;
    }

    public void setnVarDescricao(String nVarDescricao) {
        this.nVarDescricao = nVarDescricao;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public ClienteExterno idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnvarUsuario() {
        return this.idnvarUsuario;
    }

    public ClienteExterno idnvarUsuario(String idnvarUsuario) {
        this.setIdnvarUsuario(idnvarUsuario);
        return this;
    }

    public void setIdnvarUsuario(String idnvarUsuario) {
        this.idnvarUsuario = idnvarUsuario;
    }

    public Set<ClienteExternoProcesso> getClienteExternoProcessos() {
        return this.clienteExternoProcessos;
    }

    public void setClienteExternoProcessos(Set<ClienteExternoProcesso> clienteExternoProcessos) {
        if (this.clienteExternoProcessos != null) {
            this.clienteExternoProcessos.forEach(i -> i.setClienteExterno(null));
        }
        if (clienteExternoProcessos != null) {
            clienteExternoProcessos.forEach(i -> i.setClienteExterno(this));
        }
        this.clienteExternoProcessos = clienteExternoProcessos;
    }

    public ClienteExterno clienteExternoProcessos(Set<ClienteExternoProcesso> clienteExternoProcessos) {
        this.setClienteExternoProcessos(clienteExternoProcessos);
        return this;
    }

    public ClienteExterno addClienteExternoProcesso(ClienteExternoProcesso clienteExternoProcesso) {
        this.clienteExternoProcessos.add(clienteExternoProcesso);
        clienteExternoProcesso.setClienteExterno(this);
        return this;
    }

    public ClienteExterno removeClienteExternoProcesso(ClienteExternoProcesso clienteExternoProcesso) {
        this.clienteExternoProcessos.remove(clienteExternoProcesso);
        clienteExternoProcesso.setClienteExterno(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ClienteExterno empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ClienteExterno usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteExterno)) {
            return false;
        }
        return id != null && id.equals(((ClienteExterno) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClienteExterno{" +
            "id=" + getId() +
            ", idnVarClienteExterno='" + getIdnVarClienteExterno() + "'" +
            ", nVarNome='" + getnVarNome() + "'" +
            ", nVarDescricao='" + getnVarDescricao() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnvarUsuario='" + getIdnvarUsuario() + "'" +
            "}";
    }
}
