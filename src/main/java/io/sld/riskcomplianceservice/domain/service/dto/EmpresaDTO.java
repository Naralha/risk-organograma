package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link Empresa} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class EmpresaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String nVarNome;

    private String nVarDescricao;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmpresaDTO)) {
            return false;
        }

        EmpresaDTO empresaDTO = (EmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, empresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
