package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.MacroProcesso;
import io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoOrganogramaDTO;
import io.sld.riskcomplianceservice.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MacroProcessoOrganograma} and its DTO {@link MacroProcessoOrganogramaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MacroProcessoOrganogramaMapper extends EntityMapper<MacroProcessoOrganogramaDTO, MacroProcessoOrganograma> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    @Mapping(target = "macroProcesso", source = "macroProcesso", qualifiedByName = "macroProcessoId")
    @Mapping(target = "organograma", source = "organograma", qualifiedByName = "organogramaId")
    MacroProcessoOrganogramaDTO toDto(MacroProcessoOrganograma s);

    @Named("empresaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmpresaDTO toDtoEmpresaId(Empresa empresa);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);

    @Named("macroProcessoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MacroProcessoDTO toDtoMacroProcessoId(MacroProcesso macroProcesso);

    @Named("organogramaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganogramaDTO toDtoOrganogramaId(Organograma organograma);
}
