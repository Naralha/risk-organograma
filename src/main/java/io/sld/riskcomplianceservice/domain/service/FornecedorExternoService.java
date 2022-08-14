package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FornecedorExterno}.
 */
@Service
@Transactional
public class FornecedorExternoService {

    private final Logger log = LoggerFactory.getLogger(FornecedorExternoService.class);

    private final FornecedorExternoRepository fornecedorExternoRepository;

    private final FornecedorExternoMapper fornecedorExternoMapper;

    public FornecedorExternoService(
        FornecedorExternoRepository fornecedorExternoRepository,
        FornecedorExternoMapper fornecedorExternoMapper
    ) {
        this.fornecedorExternoRepository = fornecedorExternoRepository;
        this.fornecedorExternoMapper = fornecedorExternoMapper;
    }

    /**
     * Save a fornecedorExterno.
     *
     * @param fornecedorExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorExternoDTO save(FornecedorExternoDTO fornecedorExternoDTO) {
        log.debug("Request to save FornecedorExterno : {}", fornecedorExternoDTO);
        FornecedorExterno fornecedorExterno = fornecedorExternoMapper.toEntity(fornecedorExternoDTO);
        fornecedorExterno = fornecedorExternoRepository.save(fornecedorExterno);
        return fornecedorExternoMapper.toDto(fornecedorExterno);
    }

    /**
     * Update a fornecedorExterno.
     *
     * @param fornecedorExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public FornecedorExternoDTO update(FornecedorExternoDTO fornecedorExternoDTO) {
        log.debug("Request to save FornecedorExterno : {}", fornecedorExternoDTO);
        FornecedorExterno fornecedorExterno = fornecedorExternoMapper.toEntity(fornecedorExternoDTO);
        fornecedorExterno = fornecedorExternoRepository.save(fornecedorExterno);
        return fornecedorExternoMapper.toDto(fornecedorExterno);
    }

    /**
     * Partially update a fornecedorExterno.
     *
     * @param fornecedorExternoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FornecedorExternoDTO> partialUpdate(FornecedorExternoDTO fornecedorExternoDTO) {
        log.debug("Request to partially update FornecedorExterno : {}", fornecedorExternoDTO);

        return fornecedorExternoRepository
            .findById(fornecedorExternoDTO.getId())
            .map(existingFornecedorExterno -> {
                fornecedorExternoMapper.partialUpdate(existingFornecedorExterno, fornecedorExternoDTO);

                return existingFornecedorExterno;
            })
            .map(fornecedorExternoRepository::save)
            .map(fornecedorExternoMapper::toDto);
    }

    /**
     * Get all the fornecedorExternos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FornecedorExternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FornecedorExternos");
        return fornecedorExternoRepository.findAll(pageable).map(fornecedorExternoMapper::toDto);
    }

    /**
     * Get one fornecedorExterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FornecedorExternoDTO> findOne(Long id) {
        log.debug("Request to get FornecedorExterno : {}", id);
        return fornecedorExternoRepository.findById(id).map(fornecedorExternoMapper::toDto);
    }

    /**
     * Delete the fornecedorExterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FornecedorExterno : {}", id);
        fornecedorExternoRepository.deleteById(id);
    }
}
