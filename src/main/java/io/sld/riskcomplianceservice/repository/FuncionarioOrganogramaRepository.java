package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.FuncionarioOrganograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FuncionarioOrganograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionarioOrganogramaRepository
    extends JpaRepository<FuncionarioOrganograma, Long>, JpaSpecificationExecutor<FuncionarioOrganograma> {}
