package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.repository.ProcessoRepository;
import io.sld.riskcomplianceservice.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Processo}.
 */
@Service
@Transactional
public class ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoService.class);

    private final ProcessoRepository processoRepository;

    private final ProcessoMapper processoMapper;

    public ProcessoService(ProcessoRepository processoRepository, ProcessoMapper processoMapper) {
        this.processoRepository = processoRepository;
        this.processoMapper = processoMapper;
    }

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.toEntity(processoDTO);
        processo = processoRepository.save(processo);
        return processoMapper.toDto(processo);
    }

    /**
     * Update a processo.
     *
     * @param processoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessoDTO update(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.toEntity(processoDTO);
        processo = processoRepository.save(processo);
        return processoMapper.toDto(processo);
    }

    /**
     * Partially update a processo.
     *
     * @param processoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessoDTO> partialUpdate(ProcessoDTO processoDTO) {
        log.debug("Request to partially update Processo : {}", processoDTO);

        return processoRepository
            .findById(processoDTO.getId())
            .map(existingProcesso -> {
                processoMapper.partialUpdate(existingProcesso, processoDTO);

                return existingProcesso;
            })
            .map(processoRepository::save)
            .map(processoMapper::toDto);
    }

    /**
     * Get all the processos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Processos");
        return processoRepository.findAll(pageable).map(processoMapper::toDto);
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessoDTO> findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        return processoRepository.findById(id).map(processoMapper::toDto);
    }

    /**
     * Delete the processo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.deleteById(id);
    }
}
