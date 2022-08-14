package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.ClienteExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClienteExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteExternoRepository extends JpaRepository<ClienteExterno, Long>, JpaSpecificationExecutor<ClienteExterno> {}
