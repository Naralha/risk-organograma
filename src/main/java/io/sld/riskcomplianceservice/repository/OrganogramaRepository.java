package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.Organograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Organograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganogramaRepository extends JpaRepository<Organograma, Long>, JpaSpecificationExecutor<Organograma> {}
