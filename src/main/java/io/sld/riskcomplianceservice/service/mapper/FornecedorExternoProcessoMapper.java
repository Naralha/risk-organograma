package io.sld.riskcomplianceservice.service.mapper;

import io.sld.riskcomplianceservice.domain.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.FornecedorExternoProcesso;
import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.service.dto.FornecedorExternoDTO;
import io.sld.riskcomplianceservice.service.dto.FornecedorExternoProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FornecedorExternoProcesso} and its DTO {@link FornecedorExternoProcessoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FornecedorExternoProcessoMapper extends EntityMapper<FornecedorExternoProcessoDTO, FornecedorExternoProcesso> {
    @Mapping(target = "fornecedorExterno", source = "fornecedorExterno", qualifiedByName = "fornecedorExternoId")
    @Mapping(target = "processo", source = "processo", qualifiedByName = "processoId")
    @Mapping(target = "usuario", source = "usuario", qualifiedByName = "usuarioId")
    FornecedorExternoProcessoDTO toDto(FornecedorExternoProcesso s);

    @Named("fornecedorExternoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FornecedorExternoDTO toDtoFornecedorExternoId(FornecedorExterno fornecedorExterno);

    @Named("processoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessoDTO toDtoProcessoId(Processo processo);

    @Named("usuarioId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsuarioDTO toDtoUsuarioId(Usuario usuario);
}
