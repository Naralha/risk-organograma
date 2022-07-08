package io.sld.riskcomplianceservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MacroProcessoOrganograma.
 */
@Entity
@Table(name = "macro_processo_organograma")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MacroProcessoOrganograma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "idn_var_macro_processo", nullable = false)
    private String idnVarMacroProcesso;

    @NotNull
    @Column(name = "idn_var_organograma", nullable = false)
    private String idnVarOrganograma;

    @NotNull
    @Column(name = "idn_var_processo", nullable = false)
    private String idnVarProcesso;

    @NotNull
    @Column(name = "idn_var_usuario", nullable = false)
    private String idnVarUsuario;

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

    @Column(name = "idn_var_usuario_cadastro")
    private String idnVarUsuarioCadastro;

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

    @ManyToOne
    @JsonIgnoreProperties(value = { "macroProcessoOrganogramas", "empresa", "usuario" }, allowSetters = true)
    private MacroProcesso macroProcesso;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public MacroProcessoOrganograma id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarMacroProcesso() {
        return this.idnVarMacroProcesso;
    }

    public MacroProcessoOrganograma idnVarMacroProcesso(String idnVarMacroProcesso) {
        this.setIdnVarMacroProcesso(idnVarMacroProcesso);
        return this;
    }

    public void setIdnVarMacroProcesso(String idnVarMacroProcesso) {
        this.idnVarMacroProcesso = idnVarMacroProcesso;
    }

    public String getIdnVarOrganograma() {
        return this.idnVarOrganograma;
    }

    public MacroProcessoOrganograma idnVarOrganograma(String idnVarOrganograma) {
        this.setIdnVarOrganograma(idnVarOrganograma);
        return this;
    }

    public void setIdnVarOrganograma(String idnVarOrganograma) {
        this.idnVarOrganograma = idnVarOrganograma;
    }

    public String getIdnVarProcesso() {
        return this.idnVarProcesso;
    }

    public MacroProcessoOrganograma idnVarProcesso(String idnVarProcesso) {
        this.setIdnVarProcesso(idnVarProcesso);
        return this;
    }

    public void setIdnVarProcesso(String idnVarProcesso) {
        this.idnVarProcesso = idnVarProcesso;
    }

    public String getIdnVarUsuario() {
        return this.idnVarUsuario;
    }

    public MacroProcessoOrganograma idnVarUsuario(String idnVarUsuario) {
        this.setIdnVarUsuario(idnVarUsuario);
        return this;
    }

    public void setIdnVarUsuario(String idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public MacroProcessoOrganograma idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnVarUsuarioCadastro() {
        return this.idnVarUsuarioCadastro;
    }

    public MacroProcessoOrganograma idnVarUsuarioCadastro(String idnVarUsuarioCadastro) {
        this.setIdnVarUsuarioCadastro(idnVarUsuarioCadastro);
        return this;
    }

    public void setIdnVarUsuarioCadastro(String idnVarUsuarioCadastro) {
        this.idnVarUsuarioCadastro = idnVarUsuarioCadastro;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public MacroProcessoOrganograma empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MacroProcessoOrganograma usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    public MacroProcesso getMacroProcesso() {
        return this.macroProcesso;
    }

    public void setMacroProcesso(MacroProcesso macroProcesso) {
        this.macroProcesso = macroProcesso;
    }

    public MacroProcessoOrganograma macroProcesso(MacroProcesso macroProcesso) {
        this.setMacroProcesso(macroProcesso);
        return this;
    }

    public Organograma getOrganograma() {
        return this.organograma;
    }

    public void setOrganograma(Organograma organograma) {
        this.organograma = organograma;
    }

    public MacroProcessoOrganograma organograma(Organograma organograma) {
        this.setOrganograma(organograma);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MacroProcessoOrganograma)) {
            return false;
        }
        return id != null && id.equals(((MacroProcessoOrganograma) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MacroProcessoOrganograma{" +
            "id=" + getId() +
            ", idnVarMacroProcesso='" + getIdnVarMacroProcesso() + "'" +
            ", idnVarOrganograma='" + getIdnVarOrganograma() + "'" +
            ", idnVarProcesso='" + getIdnVarProcesso() + "'" +
            ", idnVarUsuario='" + getIdnVarUsuario() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnVarUsuarioCadastro='" + getIdnVarUsuarioCadastro() + "'" +
            "}";
    }
}
