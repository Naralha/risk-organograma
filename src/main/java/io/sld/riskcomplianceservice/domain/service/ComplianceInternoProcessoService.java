package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.ComplianceInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceInternoProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ComplianceInternoProcesso}.
 */
@Service
@Transactional
public class ComplianceInternoProcessoService {

    private final Logger log = LoggerFactory.getLogger(ComplianceInternoProcessoService.class);

    private final ComplianceInternoProcessoRepository complianceInternoProcessoRepository;

    private final ComplianceInternoProcessoMapper complianceInternoProcessoMapper;

    public ComplianceInternoProcessoService(
        ComplianceInternoProcessoRepository complianceInternoProcessoRepository,
        ComplianceInternoProcessoMapper complianceInternoProcessoMapper
    ) {
        this.complianceInternoProcessoRepository = complianceInternoProcessoRepository;
        this.complianceInternoProcessoMapper = complianceInternoProcessoMapper;
    }

    /**
     * Save a complianceInternoProcesso.
     *
     * @param complianceInternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceInternoProcessoDTO save(ComplianceInternoProcessoDTO complianceInternoProcessoDTO) {
        log.debug("Request to save ComplianceInternoProcesso : {}", complianceInternoProcessoDTO);
        ComplianceInternoProcesso complianceInternoProcesso = complianceInternoProcessoMapper.toEntity(complianceInternoProcessoDTO);
        complianceInternoProcesso = complianceInternoProcessoRepository.save(complianceInternoProcesso);
        return complianceInternoProcessoMapper.toDto(complianceInternoProcesso);
    }

    /**
     * Update a complianceInternoProcesso.
     *
     * @param complianceInternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ComplianceInternoProcessoDTO update(ComplianceInternoProcessoDTO complianceInternoProcessoDTO) {
        log.debug("Request to save ComplianceInternoProcesso : {}", complianceInternoProcessoDTO);
        ComplianceInternoProcesso complianceInternoProcesso = complianceInternoProcessoMapper.toEntity(complianceInternoProcessoDTO);
        complianceInternoProcesso = complianceInternoProcessoRepository.save(complianceInternoProcesso);
        return complianceInternoProcessoMapper.toDto(complianceInternoProcesso);
    }

    /**
     * Partially update a complianceInternoProcesso.
     *
     * @param complianceInternoProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ComplianceInternoProcessoDTO> partialUpdate(ComplianceInternoProcessoDTO complianceInternoProcessoDTO) {
        log.debug("Request to partially update ComplianceInternoProcesso : {}", complianceInternoProcessoDTO);

        return complianceInternoProcessoRepository
            .findById(complianceInternoProcessoDTO.getId())
            .map(existingComplianceInternoProcesso -> {
                complianceInternoProcessoMapper.partialUpdate(existingComplianceInternoProcesso, complianceInternoProcessoDTO);

                return existingComplianceInternoProcesso;
            })
            .map(complianceInternoProcessoRepository::save)
            .map(complianceInternoProcessoMapper::toDto);
    }

    /**
     * Get all the complianceInternoProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ComplianceInternoProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ComplianceInternoProcessos");
        return complianceInternoProcessoRepository.findAll(pageable).map(complianceInternoProcessoMapper::toDto);
    }

    /**
     * Get one complianceInternoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ComplianceInternoProcessoDTO> findOne(Long id) {
        log.debug("Request to get ComplianceInternoProcesso : {}", id);
        return complianceInternoProcessoRepository.findById(id).map(complianceInternoProcessoMapper::toDto);
    }

    /**
     * Delete the complianceInternoProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ComplianceInternoProcesso : {}", id);
        complianceInternoProcessoRepository.deleteById(id);
    }
}
