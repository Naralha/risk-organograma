package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.ClienteExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClienteExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteExternoRepository extends JpaRepository<ClienteExterno, Long>, JpaSpecificationExecutor<ClienteExterno> {}
