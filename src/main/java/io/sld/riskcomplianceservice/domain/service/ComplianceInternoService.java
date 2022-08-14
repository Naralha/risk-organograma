package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInterno;
import io.sld.riskcomplianceservice.domain.repository.ComplianceInternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceInternoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceInternoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComplianceInterno}.
 */
@Service
@Transactional
public class ComplianceInternoService {

    private final Logger log = LoggerFactory.getLogger(ComplianceInternoService.class);

    private final ComplianceInternoRepository complianceInternoRepository;

    private final ComplianceInternoMapper complianceInternoMapper;

    public ComplianceInternoService(
        ComplianceInternoRepository complianceInternoRepository,
        ComplianceInternoMapper complianceInternoMapper
    ) {
        this.complianceInternoRepository = complianceInternoRepository;
        this.complianceInternoMapper = complianceInternoMapper;
    }

    /**
     * Save a complianceInterno.
     *
     * @param complianceInternoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceInternoDTO save(ComplianceInternoDTO complianceInternoDTO) {
        log.debug("Request to save ComplianceInterno : {}", complianceInternoDTO);
        ComplianceInterno complianceInterno = complianceInternoMapper.toEntity(complianceInternoDTO);
        complianceInterno = complianceInternoRepository.save(complianceInterno);
        return complianceInternoMapper.toDto(complianceInterno);
    }

    /**
     * Update a complianceInterno.
     *
     * @param complianceInternoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceInternoDTO update(ComplianceInternoDTO complianceInternoDTO) {
        log.debug("Request to save ComplianceInterno : {}", complianceInternoDTO);
        ComplianceInterno complianceInterno = complianceInternoMapper.toEntity(complianceInternoDTO);
        complianceInterno = complianceInternoRepository.save(complianceInterno);
        return complianceInternoMapper.toDto(complianceInterno);
    }

    /**
     * Partially update a complianceInterno.
     *
     * @param complianceInternoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComplianceInternoDTO> partialUpdate(ComplianceInternoDTO complianceInternoDTO) {
        log.debug("Request to partially update ComplianceInterno : {}", complianceInternoDTO);

        return complianceInternoRepository
            .findById(complianceInternoDTO.getId())
            .map(existingComplianceInterno -> {
                complianceInternoMapper.partialUpdate(existingComplianceInterno, complianceInternoDTO);

                return existingComplianceInterno;
            })
            .map(complianceInternoRepository::save)
            .map(complianceInternoMapper::toDto);
    }

    /**
     * Get all the complianceInternos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceInternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComplianceInternos");
        return complianceInternoRepository.findAll(pageable).map(complianceInternoMapper::toDto);
    }

    /**
     * Get one complianceInterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComplianceInternoDTO> findOne(Long id) {
        log.debug("Request to get ComplianceInterno : {}", id);
        return complianceInternoRepository.findById(id).map(complianceInternoMapper::toDto);
    }

    /**
     * Delete the complianceInterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComplianceInterno : {}", id);
        complianceInternoRepository.deleteById(id);
    }
}
