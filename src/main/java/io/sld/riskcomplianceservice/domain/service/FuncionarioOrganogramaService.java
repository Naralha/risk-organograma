package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.domain.repository.FuncionarioOrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FuncionarioOrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.FuncionarioOrganogramaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FuncionarioOrganograma}.
 */
@Service
@Transactional
public class FuncionarioOrganogramaService {

    private final Logger log = LoggerFactory.getLogger(FuncionarioOrganogramaService.class);

    private final FuncionarioOrganogramaRepository funcionarioOrganogramaRepository;

    private final FuncionarioOrganogramaMapper funcionarioOrganogramaMapper;

    public FuncionarioOrganogramaService(
        FuncionarioOrganogramaRepository funcionarioOrganogramaRepository,
        FuncionarioOrganogramaMapper funcionarioOrganogramaMapper
    ) {
        this.funcionarioOrganogramaRepository = funcionarioOrganogramaRepository;
        this.funcionarioOrganogramaMapper = funcionarioOrganogramaMapper;
    }

    /**
     * Save a funcionarioOrganograma.
     *
     * @param funcionarioOrganogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioOrganogramaDTO save(FuncionarioOrganogramaDTO funcionarioOrganogramaDTO) {
        log.debug("Request to save FuncionarioOrganograma : {}", funcionarioOrganogramaDTO);
        FuncionarioOrganograma funcionarioOrganograma = funcionarioOrganogramaMapper.toEntity(funcionarioOrganogramaDTO);
        funcionarioOrganograma = funcionarioOrganogramaRepository.save(funcionarioOrganograma);
        return funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);
    }

    /**
     * Update a funcionarioOrganograma.
     *
     * @param funcionarioOrganogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public FuncionarioOrganogramaDTO update(FuncionarioOrganogramaDTO funcionarioOrganogramaDTO) {
        log.debug("Request to save FuncionarioOrganograma : {}", funcionarioOrganogramaDTO);
        FuncionarioOrganograma funcionarioOrganograma = funcionarioOrganogramaMapper.toEntity(funcionarioOrganogramaDTO);
        funcionarioOrganograma = funcionarioOrganogramaRepository.save(funcionarioOrganograma);
        return funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);
    }

    /**
     * Partially update a funcionarioOrganograma.
     *
     * @param funcionarioOrganogramaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FuncionarioOrganogramaDTO> partialUpdate(FuncionarioOrganogramaDTO funcionarioOrganogramaDTO) {
        log.debug("Request to partially update FuncionarioOrganograma : {}", funcionarioOrganogramaDTO);

        return funcionarioOrganogramaRepository
            .findById(funcionarioOrganogramaDTO.getId())
            .map(existingFuncionarioOrganograma -> {
                funcionarioOrganogramaMapper.partialUpdate(existingFuncionarioOrganograma, funcionarioOrganogramaDTO);

                return existingFuncionarioOrganograma;
            })
            .map(funcionarioOrganogramaRepository::save)
            .map(funcionarioOrganogramaMapper::toDto);
    }

    /**
     * Get all the funcionarioOrganogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FuncionarioOrganogramaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FuncionarioOrganogramas");
        return funcionarioOrganogramaRepository.findAll(pageable).map(funcionarioOrganogramaMapper::toDto);
    }

    /**
     * Get one funcionarioOrganograma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FuncionarioOrganogramaDTO> findOne(Long id) {
        log.debug("Request to get FuncionarioOrganograma : {}", id);
        return funcionarioOrganogramaRepository.findById(id).map(funcionarioOrganogramaMapper::toDto);
    }

    /**
     * Delete the funcionarioOrganograma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FuncionarioOrganograma : {}", id);
        funcionarioOrganogramaRepository.deleteById(id);
    }
}
