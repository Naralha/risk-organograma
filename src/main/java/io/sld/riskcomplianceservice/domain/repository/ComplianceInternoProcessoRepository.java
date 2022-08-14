package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComplianceInternoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplianceInternoProcessoRepository
    extends JpaRepository<ComplianceInternoProcesso, Long>, JpaSpecificationExecutor<ComplianceInternoProcesso> {}
