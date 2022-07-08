package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.ClienteExternoProcesso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClienteExternoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteExternoProcessoRepository
    extends JpaRepository<ClienteExternoProcesso, Long>, JpaSpecificationExecutor<ClienteExternoProcesso> {}
