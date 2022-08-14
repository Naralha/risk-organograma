package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.MacroProcessoOrganograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MacroProcessoOrganograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MacroProcessoOrganogramaRepository
    extends JpaRepository<MacroProcessoOrganograma, Long>, JpaSpecificationExecutor<MacroProcessoOrganograma> {}
