package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.MacroProcesso;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
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
