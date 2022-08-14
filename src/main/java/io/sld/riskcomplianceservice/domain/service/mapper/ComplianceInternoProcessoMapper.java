package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInterno;
import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceInternoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComplianceInternoProcesso} and its DTO {@link ComplianceInternoProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComplianceInternoProcessoMapper extends EntityMapper<ComplianceInternoProcessoDTO, ComplianceInternoProcesso> {
    @Mapping(target = "complianceInterno", source = "complianceInterno", qualifiedByName = "complianceInternoId")
    @Mapping(target = "processo", source = "processo", qualifiedByName = "processoId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    ComplianceInternoProcessoDTO toDto(ComplianceInternoProcesso s);

    @Named("complianceInternoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ComplianceInternoDTO toDtoComplianceInternoId(ComplianceInterno complianceInterno);

    @Named("processoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessoDTO toDtoProcessoId(Processo processo);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
