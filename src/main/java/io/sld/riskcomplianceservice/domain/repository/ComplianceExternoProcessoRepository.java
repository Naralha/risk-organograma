package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.ComplianceExternoProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComplianceExternoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplianceExternoProcessoRepository
    extends JpaRepository<ComplianceExternoProcesso, Long>, JpaSpecificationExecutor<ComplianceExternoProcesso> {}
