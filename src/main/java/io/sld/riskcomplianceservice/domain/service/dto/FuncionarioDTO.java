package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.Funcionario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link Funcionario} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class FuncionarioDTO implements Serializable {

    private UUID idnVarFuncionario;

//    @NotNull
    private String nVarNome;

//    @NotNull
    private String nVarEmail;

    private String nVarDescricao;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncionarioDTO)) {
            return false;
        }

        FuncionarioDTO funcionarioDTO = (FuncionarioDTO) o;
        if (this.idnVarFuncionario == null) {
            return false;
        }
        return Objects.equals(this.idnVarFuncionario, funcionarioDTO.idnVarFuncionario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idnVarFuncionario);
    }


}
