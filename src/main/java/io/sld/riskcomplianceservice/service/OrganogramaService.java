package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.repository.OrganogramaRepository;
import io.sld.riskcomplianceservice.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.service.mapper.OrganogramaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Organograma}.
 */
@Service
@Transactional
public class OrganogramaService {

    private final Logger log = LoggerFactory.getLogger(OrganogramaService.class);

    private final OrganogramaRepository organogramaRepository;

    private final OrganogramaMapper organogramaMapper;

    public OrganogramaService(OrganogramaRepository organogramaRepository, OrganogramaMapper organogramaMapper) {
        this.organogramaRepository = organogramaRepository;
        this.organogramaMapper = organogramaMapper;
    }

    /**
     * Save a organograma.
     *
     * @param organogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganogramaDTO save(OrganogramaDTO organogramaDTO) {
        log.debug("Request to save Organograma : {}", organogramaDTO);
        Organograma organograma = organogramaMapper.toEntity(organogramaDTO);
        organograma = organogramaRepository.save(organograma);
        return organogramaMapper.toDto(organograma);
    }

    /**
     * Update a organograma.
     *
     * @param organogramaDTO the entity to save.
     * @return the persisted entity.
     */
    public OrganogramaDTO update(OrganogramaDTO organogramaDTO) {
        log.debug("Request to save Organograma : {}", organogramaDTO);
        Organograma organograma = organogramaMapper.toEntity(organogramaDTO);
        organograma = organogramaRepository.save(organograma);
        return organogramaMapper.toDto(organograma);
    }

    /**
     * Partially update a organograma.
     *
     * @param organogramaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrganogramaDTO> partialUpdate(OrganogramaDTO organogramaDTO) {
        log.debug("Request to partially update Organograma : {}", organogramaDTO);

        return organogramaRepository
            .findById(organogramaDTO.getId())
            .map(existingOrganograma -> {
                organogramaMapper.partialUpdate(existingOrganograma, organogramaDTO);

                return existingOrganograma;
            })
            .map(organogramaRepository::save)
            .map(organogramaMapper::toDto);
    }

    /**
     * Get all the organogramas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganogramaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Organogramas");
        return organogramaRepository.findAll(pageable).map(organogramaMapper::toDto);
    }

    /**
     * Get one organograma by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrganogramaDTO> findOne(Long id) {
        log.debug("Request to get Organograma : {}", id);
        return organogramaRepository.findById(id).map(organogramaMapper::toDto);
    }

    /**
     * Delete the organograma by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Organograma : {}", id);
        organogramaRepository.deleteById(id);
    }
}
