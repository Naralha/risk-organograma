package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.repository.MacroProcessoOrganogramaRepository;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoOrganogramaDTO;
import io.sld.riskcomplianceservice.service.mapper.MacroProcessoOrganogramaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MacroProcessoOrganograma}.
 */
@Service
@Transactional
public class MacroProcessoOrganogramaService {

    private final Logger log = LoggerFactory.getLogger(MacroProcessoOrganogramaService.class);

    private final MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository;

    private final MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper;

    public MacroProcessoOrganogramaService(
        MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository,
        MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper
    ) {
        this.macroProcessoOrganogramaRepository = macroProcessoOrganogramaRepository;
        this.macroProcessoOrganogramaMapper = macroProcessoOrganogramaMapper;
    }

    /**
     * Save a macroProcessoOrganograma.
     *
     * @param macroProcessoOrganogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public MacroProcessoOrganogramaDTO save(MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO) {
        log.debug("Request to save MacroProcessoOrganograma : {}", macroProcessoOrganogramaDTO);
        MacroProcessoOrganograma macroProcessoOrganograma = macroProcessoOrganogramaMapper.toEntity(macroProcessoOrganogramaDTO);
        macroProcessoOrganograma = macroProcessoOrganogramaRepository.save(macroProcessoOrganograma);
        return macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);
    }

    /**
     * Update a macroProcessoOrganograma.
     *
     * @param macroProcessoOrganogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public MacroProcessoOrganogramaDTO update(MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO) {
        log.debug("Request to save MacroProcessoOrganograma : {}", macroProcessoOrganogramaDTO);
        MacroProcessoOrganograma macroProcessoOrganograma = macroProcessoOrganogramaMapper.toEntity(macroProcessoOrganogramaDTO);
        macroProcessoOrganograma = macroProcessoOrganogramaRepository.save(macroProcessoOrganograma);
        return macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);
    }

    /**
     * Partially update a macroProcessoOrganograma.
     *
     * @param macroProcessoOrganogramaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MacroProcessoOrganogramaDTO> partialUpdate(MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO) {
        log.debug("Request to partially update MacroProcessoOrganograma : {}", macroProcessoOrganogramaDTO);

        return macroProcessoOrganogramaRepository
            .findById(macroProcessoOrganogramaDTO.getId())
            .map(existingMacroProcessoOrganograma -> {
                macroProcessoOrganogramaMapper.partialUpdate(existingMacroProcessoOrganograma, macroProcessoOrganogramaDTO);

                return existingMacroProcessoOrganograma;
            })
            .map(macroProcessoOrganogramaRepository::save)
            .map(macroProcessoOrganogramaMapper::toDto);
    }

    /**
     * Get all the macroProcessoOrganogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MacroProcessoOrganogramaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MacroProcessoOrganogramas");
        return macroProcessoOrganogramaRepository.findAll(pageable).map(macroProcessoOrganogramaMapper::toDto);
    }

    /**
     * Get one macroProcessoOrganograma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MacroProcessoOrganogramaDTO> findOne(Long id) {
        log.debug("Request to get MacroProcessoOrganograma : {}", id);
        return macroProcessoOrganogramaRepository.findById(id).map(macroProcessoOrganogramaMapper::toDto);
    }

    /**
     * Delete the macroProcessoOrganograma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MacroProcessoOrganograma : {}", id);
        macroProcessoOrganogramaRepository.deleteById(id);
    }
}
