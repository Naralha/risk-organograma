package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    UsuarioDTO toDto(Usuario s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);
}
