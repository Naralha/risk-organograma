package br.com.obelisco.risk.repository;

import br.com.obelisco.risk.model.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplianceExternoRepository extends JpaRepository<ComplianceExterno, UUID> {
    @EntityGraph(attributePaths = "empresa") Page<ComplianceExterno> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}

