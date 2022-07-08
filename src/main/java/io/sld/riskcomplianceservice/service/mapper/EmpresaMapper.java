package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Empresa} and its DTO {@link EmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmpresaMapper extends EntityMapper<EmpresaDTO, Empresa> {}
