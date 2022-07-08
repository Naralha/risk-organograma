package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.FornecedorExternoProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FornecedorExternoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FornecedorExternoProcessoRepository
    extends JpaRepository<FornecedorExternoProcesso, Long>, JpaSpecificationExecutor<FornecedorExternoProcesso> {}
