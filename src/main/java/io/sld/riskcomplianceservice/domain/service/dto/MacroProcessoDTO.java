package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.MacroProcesso;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link MacroProcesso} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class MacroProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarMacroProcesso;

    @NotNull
    private String nVarNomeMacroProcesso;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String idnVarUsuario;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MacroProcessoDTO)) {
            return false;
        }

        MacroProcessoDTO macroProcessoDTO = (MacroProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, macroProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
