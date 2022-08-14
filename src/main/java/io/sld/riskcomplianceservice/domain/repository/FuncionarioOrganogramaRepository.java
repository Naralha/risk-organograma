package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FuncionarioOrganograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuncionarioOrganogramaRepository
    extends JpaRepository<FuncionarioOrganograma, Long>, JpaSpecificationExecutor<FuncionarioOrganograma> {}
