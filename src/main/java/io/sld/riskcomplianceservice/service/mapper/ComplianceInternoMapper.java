package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.ComplianceInterno;
import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.ComplianceInternoDTO;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ComplianceInterno} and its DTO {@link ComplianceInternoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComplianceInternoMapper extends EntityMapper<ComplianceInternoDTO, ComplianceInterno> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    ComplianceInternoDTO toDto(ComplianceInterno s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
