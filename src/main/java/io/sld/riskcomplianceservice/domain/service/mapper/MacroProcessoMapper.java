package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.MacroProcesso;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import io.sld.riskcomplianceservice.domain.service.dto.MacroProcessoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MacroProcesso} and its DTO {@link MacroProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface MacroProcessoMapper extends EntityMapper<MacroProcessoDTO, MacroProcesso> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    MacroProcessoDTO toDto(MacroProcesso s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
