package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.ComplianceExterno;
import io.sld.riskcomplianceservice.domain.repository.ComplianceExternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceExternoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComplianceExterno}.
 */
@Service
@Transactional
public class ComplianceExternoService {

    private final Logger log = LoggerFactory.getLogger(ComplianceExternoService.class);

    private final ComplianceExternoRepository complianceExternoRepository;

    private final ComplianceExternoMapper complianceExternoMapper;

    public ComplianceExternoService(
        ComplianceExternoRepository complianceExternoRepository,
        ComplianceExternoMapper complianceExternoMapper
    ) {
        this.complianceExternoRepository = complianceExternoRepository;
        this.complianceExternoMapper = complianceExternoMapper;
    }

    /**
     * Save a complianceExterno.
     *
     * @param complianceExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceExternoDTO save(ComplianceExternoDTO complianceExternoDTO) {
        log.debug("Request to save ComplianceExterno : {}", complianceExternoDTO);
        ComplianceExterno complianceExterno = complianceExternoMapper.toEntity(complianceExternoDTO);
        complianceExterno = complianceExternoRepository.save(complianceExterno);
        return complianceExternoMapper.toDto(complianceExterno);
    }

    /**
     * Update a complianceExterno.
     *
     * @param complianceExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceExternoDTO update(ComplianceExternoDTO complianceExternoDTO) {
        log.debug("Request to save ComplianceExterno : {}", complianceExternoDTO);
        ComplianceExterno complianceExterno = complianceExternoMapper.toEntity(complianceExternoDTO);
        complianceExterno = complianceExternoRepository.save(complianceExterno);
        return complianceExternoMapper.toDto(complianceExterno);
    }

    /**
     * Partially update a complianceExterno.
     *
     * @param complianceExternoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComplianceExternoDTO> partialUpdate(ComplianceExternoDTO complianceExternoDTO) {
        log.debug("Request to partially update ComplianceExterno : {}", complianceExternoDTO);

        return complianceExternoRepository
            .findById(complianceExternoDTO.getId())
            .map(existingComplianceExterno -> {
                complianceExternoMapper.partialUpdate(existingComplianceExterno, complianceExternoDTO);

                return existingComplianceExterno;
            })
            .map(complianceExternoRepository::save)
            .map(complianceExternoMapper::toDto);
    }

    /**
     * Get all the complianceExternos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceExternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComplianceExternos");
        return complianceExternoRepository.findAll(pageable).map(complianceExternoMapper::toDto);
    }

    /**
     * Get one complianceExterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComplianceExternoDTO> findOne(Long id) {
        log.debug("Request to get ComplianceExterno : {}", id);
        return complianceExternoRepository.findById(id).map(complianceExternoMapper::toDto);
    }

    /**
     * Delete the complianceExterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComplianceExterno : {}", id);
        complianceExternoRepository.deleteById(id);
    }
}
