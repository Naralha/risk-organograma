package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.Organograma;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link Organograma} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class OrganogramaDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarOrganograma;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String nVarNome;

    private String nVarDescricao;

    private String idnVarPaiOrganograma;

    @NotNull
    private String idnvarUsuario;

    @NotNull
    private String idnVarRoofTop;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganogramaDTO)) {
            return false;
        }

        OrganogramaDTO organogramaDTO = (OrganogramaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, organogramaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
