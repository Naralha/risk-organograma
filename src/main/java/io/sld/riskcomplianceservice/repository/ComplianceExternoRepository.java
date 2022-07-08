package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.ComplianceExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComplianceExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComplianceExternoRepository extends JpaRepository<ComplianceExterno, Long>, JpaSpecificationExecutor<ComplianceExterno> {}
