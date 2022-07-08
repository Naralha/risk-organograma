package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.MacroProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MacroProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MacroProcessoRepository extends JpaRepository<MacroProcesso, Long>, JpaSpecificationExecutor<MacroProcesso> {}
