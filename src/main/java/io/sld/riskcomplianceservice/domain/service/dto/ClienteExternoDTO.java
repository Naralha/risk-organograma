package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.ClienteExterno;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ClienteExterno} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ClienteExternoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarClienteExterno;

    @NotNull
    private String nVarNome;

    private String nVarDescricao;

    @NotNull
    private String idnVarEmpresa;

    @NotNull
    private String idnvarUsuario;

    private EmpresaDTO empresa;

    private UsuarioDTO usuario;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClienteExternoDTO)) {
            return false;
        }

        ClienteExternoDTO clienteExternoDTO = (ClienteExternoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clienteExternoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
