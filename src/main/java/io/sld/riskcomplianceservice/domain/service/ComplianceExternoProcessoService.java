package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.ComplianceExternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.ComplianceExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceExternoProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComplianceExternoProcesso}.
 */
@Service
@Transactional
public class ComplianceExternoProcessoService {

    private final Logger log = LoggerFactory.getLogger(ComplianceExternoProcessoService.class);

    private final ComplianceExternoProcessoRepository complianceExternoProcessoRepository;

    private final ComplianceExternoProcessoMapper complianceExternoProcessoMapper;

    public ComplianceExternoProcessoService(
        ComplianceExternoProcessoRepository complianceExternoProcessoRepository,
        ComplianceExternoProcessoMapper complianceExternoProcessoMapper
    ) {
        this.complianceExternoProcessoRepository = complianceExternoProcessoRepository;
        this.complianceExternoProcessoMapper = complianceExternoProcessoMapper;
    }

    /**
     * Save a complianceExternoProcesso.
     *
     * @param complianceExternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceExternoProcessoDTO save(ComplianceExternoProcessoDTO complianceExternoProcessoDTO) {
        log.debug("Request to save ComplianceExternoProcesso : {}", complianceExternoProcessoDTO);
        ComplianceExternoProcesso complianceExternoProcesso = complianceExternoProcessoMapper.toEntity(complianceExternoProcessoDTO);
        complianceExternoProcesso = complianceExternoProcessoRepository.save(complianceExternoProcesso);
        return complianceExternoProcessoMapper.toDto(complianceExternoProcesso);
    }

    /**
     * Update a complianceExternoProcesso.
     *
     * @param complianceExternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceExternoProcessoDTO update(ComplianceExternoProcessoDTO complianceExternoProcessoDTO) {
        log.debug("Request to save ComplianceExternoProcesso : {}", complianceExternoProcessoDTO);
        ComplianceExternoProcesso complianceExternoProcesso = complianceExternoProcessoMapper.toEntity(complianceExternoProcessoDTO);
        complianceExternoProcesso = complianceExternoProcessoRepository.save(complianceExternoProcesso);
        return complianceExternoProcessoMapper.toDto(complianceExternoProcesso);
    }

    /**
     * Partially update a complianceExternoProcesso.
     *
     * @param complianceExternoProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComplianceExternoProcessoDTO> partialUpdate(ComplianceExternoProcessoDTO complianceExternoProcessoDTO) {
        log.debug("Request to partially update ComplianceExternoProcesso : {}", complianceExternoProcessoDTO);

        return complianceExternoProcessoRepository
            .findById(complianceExternoProcessoDTO.getId())
            .map(existingComplianceExternoProcesso -> {
                complianceExternoProcessoMapper.partialUpdate(existingComplianceExternoProcesso, complianceExternoProcessoDTO);

                return existingComplianceExternoProcesso;
            })
            .map(complianceExternoProcessoRepository::save)
            .map(complianceExternoProcessoMapper::toDto);
    }

    /**
     * Get all the complianceExternoProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceExternoProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComplianceExternoProcessos");
        return complianceExternoProcessoRepository.findAll(pageable).map(complianceExternoProcessoMapper::toDto);
    }

    /**
     * Get one complianceExternoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComplianceExternoProcessoDTO> findOne(Long id) {
        log.debug("Request to get ComplianceExternoProcesso : {}", id);
        return complianceExternoProcessoRepository.findById(id).map(complianceExternoProcessoMapper::toDto);
    }

    /**
     * Delete the complianceExternoProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComplianceExternoProcesso : {}", id);
        complianceExternoProcessoRepository.deleteById(id);
    }
}
