package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.MacroProcessoOrganograma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link MacroProcessoOrganograma} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class MacroProcessoOrganogramaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarMacroProcesso;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnVarUsuario;

    @NotNull
    private String idnVarEmpresa;

    private String idnVarUsuarioCadastro;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;

    private MacroProcessoDTO macroProcesso;

    private OrganogramaDTO organograma;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MacroProcessoOrganogramaDTO)) {
            return false;
        }

        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = (MacroProcessoOrganogramaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, macroProcessoOrganogramaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
