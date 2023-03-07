package io.sld.riskcomplianceservice.domain.service.mapper;

import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaTreeDTO;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;

public class OrganogramaArrayMapper {

    OrganogramaDTO toDTO(OrganogramaTreeDTO organogramaTreeDTO){
        OrganogramaDTO organogramaDTO = new OrganogramaDTO();
//        organogramaDTO.setNVarNome();
        return organogramaDTO;
    }
}
