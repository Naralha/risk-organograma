package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.ComplianceInterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComplianceInterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplianceInternoRepository extends JpaRepository<ComplianceInterno, Long>, JpaSpecificationExecutor<ComplianceInterno> {}
