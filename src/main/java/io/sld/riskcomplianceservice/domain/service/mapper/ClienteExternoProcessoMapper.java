package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.ClienteExterno;
import io.sld.riskcomplianceservice.domain.entity.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
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
