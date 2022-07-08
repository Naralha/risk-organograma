package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.ClienteExterno;
import io.sld.riskcomplianceservice.domain.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.ClienteExternoDTO;
import io.sld.riskcomplianceservice.service.dto.ClienteExternoProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClienteExternoProcesso} and its DTO {@link ClienteExternoProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteExternoProcessoMapper extends EntityMapper<ClienteExternoProcessoDTO, ClienteExternoProcesso> {
    @Mapping(target = "clienteExterno", source = "clienteExterno", qualifiedByName = "clienteExternoId")
    @Mapping(target = "processo", source = "processo", qualifiedByName = "processoId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    ClienteExternoProcessoDTO toDto(ClienteExternoProcesso s);

    @Named("clienteExternoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClienteExternoDTO toDtoClienteExternoId(ClienteExterno clienteExterno);

    @Named("processoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessoDTO toDtoProcessoId(Processo processo);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
