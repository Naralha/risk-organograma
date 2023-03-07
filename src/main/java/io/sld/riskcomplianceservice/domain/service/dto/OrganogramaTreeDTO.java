package io.sld.riskcomplianceservice.domain.service.dto;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class OrganogramaTreeDTO {

    private Long id;
    private String name;
    private Empresa empresa;
    private String parentId;
    private List<OrganogramaTreeDTO> children;

    public OrganogramaTreeDTO(Long id, String name, Long empresaId) {
        Empresa empresa = new Empresa();
        this.id = id;
        this.name = name;
        this.empresa = empresa;
        this.empresa.setId(empresaId);
        this.children = new ArrayList<>();
    }

}
