package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.FornecedorInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FornecedorInternoProcesso} and its DTO {@link FornecedorInternoProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FornecedorInternoProcessoMapper extends EntityMapper<FornecedorInternoProcessoDTO, FornecedorInternoProcesso> {
    @Mapping(target = "organograma", source = "organograma", qualifiedByName = "organogramaId")
    @Mapping(target = "processo", source = "processo", qualifiedByName = "processoId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    FornecedorInternoProcessoDTO toDto(FornecedorInternoProcesso s);

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
