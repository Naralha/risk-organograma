package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FornecedorExternoProcesso}.
 */
@Service
@Transactional
public class FornecedorExternoProcessoService {

    private final Logger log = LoggerFactory.getLogger(FornecedorExternoProcessoService.class);

    private final FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository;

    private final FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper;

    public FornecedorExternoProcessoService(
        FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository,
        FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper
    ) {
        this.fornecedorExternoProcessoRepository = fornecedorExternoProcessoRepository;
        this.fornecedorExternoProcessoMapper = fornecedorExternoProcessoMapper;
    }

    /**
     * Save a fornecedorExternoProcesso.
     *
     * @param fornecedorExternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorExternoProcessoDTO save(FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO) {
        log.debug("Request to save FornecedorExternoProcesso : {}", fornecedorExternoProcessoDTO);
        FornecedorExternoProcesso fornecedorExternoProcesso = fornecedorExternoProcessoMapper.toEntity(fornecedorExternoProcessoDTO);
        fornecedorExternoProcesso = fornecedorExternoProcessoRepository.save(fornecedorExternoProcesso);
        return fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);
    }

    /**
     * Update a fornecedorExternoProcesso.
     *
     * @param fornecedorExternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorExternoProcessoDTO update(FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO) {
        log.debug("Request to save FornecedorExternoProcesso : {}", fornecedorExternoProcessoDTO);
        FornecedorExternoProcesso fornecedorExternoProcesso = fornecedorExternoProcessoMapper.toEntity(fornecedorExternoProcessoDTO);
        fornecedorExternoProcesso = fornecedorExternoProcessoRepository.save(fornecedorExternoProcesso);
        return fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);
    }

    /**
     * Partially update a fornecedorExternoProcesso.
     *
     * @param fornecedorExternoProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FornecedorExternoProcessoDTO> partialUpdate(FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO) {
        log.debug("Request to partially update FornecedorExternoProcesso : {}", fornecedorExternoProcessoDTO);

        return fornecedorExternoProcessoRepository
            .findById(fornecedorExternoProcessoDTO.getId())
            .map(existingFornecedorExternoProcesso -> {
                fornecedorExternoProcessoMapper.partialUpdate(existingFornecedorExternoProcesso, fornecedorExternoProcessoDTO);

                return existingFornecedorExternoProcesso;
            })
            .map(fornecedorExternoProcessoRepository::save)
            .map(fornecedorExternoProcessoMapper::toDto);
    }

    /**
     * Get all the fornecedorExternoProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorExternoProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FornecedorExternoProcessos");
        return fornecedorExternoProcessoRepository.findAll(pageable).map(fornecedorExternoProcessoMapper::toDto);
    }

    /**
     * Get one fornecedorExternoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorExternoProcessoDTO> findOne(Long id) {
        log.debug("Request to get FornecedorExternoProcesso : {}", id);
        return fornecedorExternoProcessoRepository.findById(id).map(fornecedorExternoProcessoMapper::toDto);
    }

    /**
     * Delete the fornecedorExternoProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FornecedorExternoProcesso : {}", id);
        fornecedorExternoProcessoRepository.deleteById(id);
    }
}
