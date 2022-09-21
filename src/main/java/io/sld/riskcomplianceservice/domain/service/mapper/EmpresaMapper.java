package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {

    @Mapping(target = "nVarNome", source = "NVarNome")
    @Mapping(target = "nVarDescricao", source = "NVarDescricao")
    EmpresaDTO toDto(Empresa s);
}
