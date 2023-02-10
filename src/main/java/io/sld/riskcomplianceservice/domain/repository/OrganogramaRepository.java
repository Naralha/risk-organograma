package io.sld.riskcomplianceservice.domain.repository;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the Organograma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganogramaRepository extends JpaRepository<Organograma, Long>, JpaSpecificationExecutor<Organograma> {
    Optional<List<Organograma>> findByEmpresa(Empresa empresa);
}
