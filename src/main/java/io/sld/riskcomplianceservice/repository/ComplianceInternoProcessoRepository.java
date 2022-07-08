package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.ComplianceInternoProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComplianceInternoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplianceInternoProcessoRepository
    extends JpaRepository<ComplianceInternoProcesso, Long>, JpaSpecificationExecutor<ComplianceInternoProcesso> {}
