package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.Processo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Processo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long>, JpaSpecificationExecutor<Processo> {}
