package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.MacroProcesso;
import io.sld.riskcomplianceservice.repository.MacroProcessoRepository;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.MacroProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MacroProcesso}.
 */
@Service
@Transactional
public class MacroProcessoService {

    private final Logger log = LoggerFactory.getLogger(MacroProcessoService.class);

    private final MacroProcessoRepository macroProcessoRepository;

    private final MacroProcessoMapper macroProcessoMapper;

    public MacroProcessoService(MacroProcessoRepository macroProcessoRepository, MacroProcessoMapper macroProcessoMapper) {
        this.macroProcessoRepository = macroProcessoRepository;
        this.macroProcessoMapper = macroProcessoMapper;
    }

    /**
     * Save a macroProcesso.
     *
     * @param macroProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public MacroProcessoDTO save(MacroProcessoDTO macroProcessoDTO) {
        log.debug("Request to save MacroProcesso : {}", macroProcessoDTO);
        MacroProcesso macroProcesso = macroProcessoMapper.toEntity(macroProcessoDTO);
        macroProcesso = macroProcessoRepository.save(macroProcesso);
        return macroProcessoMapper.toDto(macroProcesso);
    }

    /**
     * Update a macroProcesso.
     *
     * @param macroProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public MacroProcessoDTO update(MacroProcessoDTO macroProcessoDTO) {
        log.debug("Request to save MacroProcesso : {}", macroProcessoDTO);
        MacroProcesso macroProcesso = macroProcessoMapper.toEntity(macroProcessoDTO);
        macroProcesso = macroProcessoRepository.save(macroProcesso);
        return macroProcessoMapper.toDto(macroProcesso);
    }

    /**
     * Partially update a macroProcesso.
     *
     * @param macroProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MacroProcessoDTO> partialUpdate(MacroProcessoDTO macroProcessoDTO) {
        log.debug("Request to partially update MacroProcesso : {}", macroProcessoDTO);

        return macroProcessoRepository
            .findById(macroProcessoDTO.getId())
            .map(existingMacroProcesso -> {
                macroProcessoMapper.partialUpdate(existingMacroProcesso, macroProcessoDTO);

                return existingMacroProcesso;
            })
            .map(macroProcessoRepository::save)
            .map(macroProcessoMapper::toDto);
    }

    /**
     * Get all the macroProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MacroProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MacroProcessos");
        return macroProcessoRepository.findAll(pageable).map(macroProcessoMapper::toDto);
    }

    /**
     * Get one macroProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MacroProcessoDTO> findOne(Long id) {
        log.debug("Request to get MacroProcesso : {}", id);
        return macroProcessoRepository.findById(id).map(macroProcessoMapper::toDto);
    }

    /**
     * Delete the macroProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MacroProcesso : {}", id);
        macroProcessoRepository.deleteById(id);
    }
}
