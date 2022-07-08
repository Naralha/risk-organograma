package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.ComplianceExternoProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComplianceExternoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplianceExternoProcessoRepository
    extends JpaRepository<ComplianceExternoProcesso, Long>, JpaSpecificationExecutor<ComplianceExternoProcesso> {}
