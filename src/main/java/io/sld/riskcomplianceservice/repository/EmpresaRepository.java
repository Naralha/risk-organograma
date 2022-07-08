package io.sld.riskcomplianceservice.repository;

import io.sld.riskcomplianceservice.domain.Empresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Empresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>, JpaSpecificationExecutor<Empresa> {}
