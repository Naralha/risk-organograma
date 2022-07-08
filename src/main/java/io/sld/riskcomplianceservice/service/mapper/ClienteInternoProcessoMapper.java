package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.ClienteInternoProcesso;
import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.ClienteInternoProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClienteInternoProcesso} and its DTO {@link ClienteInternoProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteInternoProcessoMapper extends EntityMapper<ClienteInternoProcessoDTO, ClienteInternoProcesso> {
    @Mapping(target = "organograma", source = "organograma", qualifiedByName = "organogramaId")
    @Mapping(target = "processo", source = "processo", qualifiedByName = "processoId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    ClienteInternoProcessoDTO toDto(ClienteInternoProcesso s);

    @Named("organogramaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganogramaDTO toDtoOrganogramaId(Organograma organograma);

    @Named("processoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessoDTO toDtoProcessoId(Processo processo);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
