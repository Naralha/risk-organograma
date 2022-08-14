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
 * A MacroProcesso.
 */
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "macro_processo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MacroProcesso implements Serializable {

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
    @Column(name = "n_var_nome_macro_processo", nullable = false)
    private String nVarNomeMacroProcesso;

    @NotNull
    @Column(name = "idn_var_empresa", nullable = false)
    private String idnVarEmpresa;

    @NotNull
    @Column(name = "idn_var_usuario", nullable = false)
    private String idnVarUsuario;

    @OneToMany(mappedBy = "macroProcesso")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "usuario", "macroProcesso", "organograma" }, allowSetters = true)
    private Set<MacroProcessoOrganograma> macroProcessoOrganogramas = new HashSet<>();

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

    public MacroProcesso id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdnVarMacroProcesso() {
        return this.idnVarMacroProcesso;
    }

    public MacroProcesso idnVarMacroProcesso(String idnVarMacroProcesso) {
        this.setIdnVarMacroProcesso(idnVarMacroProcesso);
        return this;
    }

    public void setIdnVarMacroProcesso(String idnVarMacroProcesso) {
        this.idnVarMacroProcesso = idnVarMacroProcesso;
    }

    public String getnVarNomeMacroProcesso() {
        return this.nVarNomeMacroProcesso;
    }

    public MacroProcesso nVarNomeMacroProcesso(String nVarNomeMacroProcesso) {
        this.setnVarNomeMacroProcesso(nVarNomeMacroProcesso);
        return this;
    }

    public void setnVarNomeMacroProcesso(String nVarNomeMacroProcesso) {
        this.nVarNomeMacroProcesso = nVarNomeMacroProcesso;
    }

    public String getIdnVarEmpresa() {
        return this.idnVarEmpresa;
    }

    public MacroProcesso idnVarEmpresa(String idnVarEmpresa) {
        this.setIdnVarEmpresa(idnVarEmpresa);
        return this;
    }

    public void setIdnVarEmpresa(String idnVarEmpresa) {
        this.idnVarEmpresa = idnVarEmpresa;
    }

    public String getIdnVarUsuario() {
        return this.idnVarUsuario;
    }

    public MacroProcesso idnVarUsuario(String idnVarUsuario) {
        this.setIdnVarUsuario(idnVarUsuario);
        return this;
    }

    public void setIdnVarUsuario(String idnVarUsuario) {
        this.idnVarUsuario = idnVarUsuario;
    }

    public Set<MacroProcessoOrganograma> getMacroProcessoOrganogramas() {
        return this.macroProcessoOrganogramas;
    }

    public void setMacroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        if (this.macroProcessoOrganogramas != null) {
            this.macroProcessoOrganogramas.forEach(i -> i.setMacroProcesso(null));
        }
        if (macroProcessoOrganogramas != null) {
            macroProcessoOrganogramas.forEach(i -> i.setMacroProcesso(this));
        }
        this.macroProcessoOrganogramas = macroProcessoOrganogramas;
    }

    public MacroProcesso macroProcessoOrganogramas(Set<MacroProcessoOrganograma> macroProcessoOrganogramas) {
        this.setMacroProcessoOrganogramas(macroProcessoOrganogramas);
        return this;
    }

    public MacroProcesso addMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.add(macroProcessoOrganograma);
        macroProcessoOrganograma.setMacroProcesso(this);
        return this;
    }

    public MacroProcesso removeMacroProcessoOrganograma(MacroProcessoOrganograma macroProcessoOrganograma) {
        this.macroProcessoOrganogramas.remove(macroProcessoOrganograma);
        macroProcessoOrganograma.setMacroProcesso(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public MacroProcesso empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public MacroProcesso usuario(Usuario usuario) {
        this.setUsuario(usuario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MacroProcesso)) {
            return false;
        }
        return id != null && id.equals(((MacroProcesso) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MacroProcesso{" +
            "id=" + getId() +
            ", idnVarMacroProcesso='" + getIdnVarMacroProcesso() + "'" +
            ", nVarNomeMacroProcesso='" + getnVarNomeMacroProcesso() + "'" +
            ", idnVarEmpresa='" + getIdnVarEmpresa() + "'" +
            ", idnVarUsuario='" + getIdnVarUsuario() + "'" +
            "}";
    }
}
