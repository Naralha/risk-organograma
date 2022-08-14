package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link FuncionarioOrganograma} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class FuncionarioOrganogramaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarFuncionario;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnvarUsuario;

    private FuncionarioDTO funcionario;

    private OrganogramaDTO organograma;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncionarioOrganogramaDTO)) {
            return false;
        }

        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = (FuncionarioOrganogramaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, funcionarioOrganogramaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
