package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FornecedorExterno} and its DTO {@link FornecedorExternoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FornecedorExternoMapper extends EntityMapper<FornecedorExternoDTO, FornecedorExterno> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    FornecedorExternoDTO toDto(FornecedorExterno s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
