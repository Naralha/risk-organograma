package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.ComplianceExterno;
import io.sld.riskcomplianceservice.domain.entity.ComplianceExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComplianceExternoProcesso} and its DTO {@link ComplianceExternoProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComplianceExternoProcessoMapper extends EntityMapper<ComplianceExternoProcessoDTO, ComplianceExternoProcesso> {
    @Mapping(target = "complianceExterno", source = "complianceExterno", qualifiedByName = "complianceExternoId")
    @Mapping(target = "processo", source = "processo", qualifiedByName = "processoId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    ComplianceExternoProcessoDTO toDto(ComplianceExternoProcesso s);

    @Named("complianceExternoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ComplianceExternoDTO toDtoComplianceExternoId(ComplianceExterno complianceExterno);

    @Named("processoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessoDTO toDtoProcessoId(Processo processo);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
