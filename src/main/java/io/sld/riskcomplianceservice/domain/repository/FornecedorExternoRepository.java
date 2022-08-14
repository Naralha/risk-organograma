package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FornecedorExterno entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FornecedorExternoRepository extends JpaRepository<FornecedorExterno, Long>, JpaSpecificationExecutor<FornecedorExterno> {}
