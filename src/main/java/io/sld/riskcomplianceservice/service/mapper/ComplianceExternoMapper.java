package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.ComplianceExterno;
import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.ComplianceExternoDTO;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComplianceExterno} and its DTO {@link ComplianceExternoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComplianceExternoMapper extends EntityMapper<ComplianceExternoDTO, ComplianceExterno> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    ComplianceExternoDTO toDto(ComplianceExterno s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
