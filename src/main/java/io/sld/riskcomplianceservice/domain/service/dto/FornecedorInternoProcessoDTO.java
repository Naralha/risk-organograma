package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.FornecedorInternoProcesso;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link FornecedorInternoProcesso} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class FornecedorInternoProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idnvarUsuario;

    private OrganogramaDTO organograma;

    private ProcessoDTO processo;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorInternoProcessoDTO)) {
            return false;
        }

        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = (FornecedorInternoProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fornecedorInternoProcessoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
