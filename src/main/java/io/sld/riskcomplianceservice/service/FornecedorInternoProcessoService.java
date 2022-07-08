package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.FornecedorInternoProcesso;
import io.sld.riskcomplianceservice.repository.FornecedorInternoProcessoRepository;
import io.sld.riskcomplianceservice.service.dto.FornecedorInternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.FornecedorInternoProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FornecedorInternoProcesso}.
 */
@Service
@Transactional
public class FornecedorInternoProcessoService {

    private final Logger log = LoggerFactory.getLogger(FornecedorInternoProcessoService.class);

    private final FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository;

    private final FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper;

    public FornecedorInternoProcessoService(
        FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository,
        FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper
    ) {
        this.fornecedorInternoProcessoRepository = fornecedorInternoProcessoRepository;
        this.fornecedorInternoProcessoMapper = fornecedorInternoProcessoMapper;
    }

    /**
     * Save a fornecedorInternoProcesso.
     *
     * @param fornecedorInternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorInternoProcessoDTO save(FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO) {
        log.debug("Request to save FornecedorInternoProcesso : {}", fornecedorInternoProcessoDTO);
        FornecedorInternoProcesso fornecedorInternoProcesso = fornecedorInternoProcessoMapper.toEntity(fornecedorInternoProcessoDTO);
        fornecedorInternoProcesso = fornecedorInternoProcessoRepository.save(fornecedorInternoProcesso);
        return fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);
    }

    /**
     * Update a fornecedorInternoProcesso.
     *
     * @param fornecedorInternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorInternoProcessoDTO update(FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO) {
        log.debug("Request to save FornecedorInternoProcesso : {}", fornecedorInternoProcessoDTO);
        FornecedorInternoProcesso fornecedorInternoProcesso = fornecedorInternoProcessoMapper.toEntity(fornecedorInternoProcessoDTO);
        fornecedorInternoProcesso = fornecedorInternoProcessoRepository.save(fornecedorInternoProcesso);
        return fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);
    }

    /**
     * Partially update a fornecedorInternoProcesso.
     *
     * @param fornecedorInternoProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FornecedorInternoProcessoDTO> partialUpdate(FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO) {
        log.debug("Request to partially update FornecedorInternoProcesso : {}", fornecedorInternoProcessoDTO);

        return fornecedorInternoProcessoRepository
            .findById(fornecedorInternoProcessoDTO.getId())
            .map(existingFornecedorInternoProcesso -> {
                fornecedorInternoProcessoMapper.partialUpdate(existingFornecedorInternoProcesso, fornecedorInternoProcessoDTO);

                return existingFornecedorInternoProcesso;
            })
            .map(fornecedorInternoProcessoRepository::save)
            .map(fornecedorInternoProcessoMapper::toDto);
    }

    /**
     * Get all the fornecedorInternoProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorInternoProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FornecedorInternoProcessos");
        return fornecedorInternoProcessoRepository.findAll(pageable).map(fornecedorInternoProcessoMapper::toDto);
    }

    /**
     * Get one fornecedorInternoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorInternoProcessoDTO> findOne(Long id) {
        log.debug("Request to get FornecedorInternoProcesso : {}", id);
        return fornecedorInternoProcessoRepository.findById(id).map(fornecedorInternoProcessoMapper::toDto);
    }

    /**
     * Delete the fornecedorInternoProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FornecedorInternoProcesso : {}", id);
        fornecedorInternoProcessoRepository.deleteById(id);
    }
}
