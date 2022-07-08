package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MacroProcessoOrganograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MacroProcessoOrganogramaRepository
    extends JpaRepository<MacroProcessoOrganograma, Long>, JpaSpecificationExecutor<MacroProcessoOrganograma> {}
