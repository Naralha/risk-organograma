package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.Processo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link Processo} entity.
 */
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ProcessoDTO implements Serializable {

    private Long id;

    @NotNull
    private String idnVarProcesso;

    @NotNull
    private String idVarMacroProcesso;

    @NotNull
    private String nVarNomeProcesso;

    @NotNull
    private String nVarObjetivoProcesso;

    private String nVarLimitrofeInicial;

    private String nVarLimitrofeFinal;

    private String nVarPathFile;

    private String nVarEntradas;

    private String nVarSaidas;

    @NotNull
    private Instant dtInicio;

    private Instant dtFim;

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
        if (!(o instanceof ProcessoDTO)) {
            return false;
        }

        ProcessoDTO processoDTO = (ProcessoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }


}
