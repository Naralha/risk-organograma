package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExternoProcesso;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link FornecedorExternoProcesso} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class FornecedorExternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarFornecedorExterno;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private FornecedorExternoDTO fornecedorExterno;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorExternoProcessoDTO)) {
            return false;
        }

        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = (FornecedorExternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fornecedorExternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
