package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.ClienteExterno;
import io.sld.riskcomplianceservice.domain.repository.ClienteExternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ClienteExternoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClienteExterno}.
 */
@Service
@Transactional
public class ClienteExternoService {

    private final Logger log = LoggerFactory.getLogger(ClienteExternoService.class);

    private final ClienteExternoRepository clienteExternoRepository;

    private final ClienteExternoMapper clienteExternoMapper;

    public ClienteExternoService(ClienteExternoRepository clienteExternoRepository, ClienteExternoMapper clienteExternoMapper) {
        this.clienteExternoRepository = clienteExternoRepository;
        this.clienteExternoMapper = clienteExternoMapper;
    }

    /**
     * Save a clienteExterno.
     *
     * @param clienteExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteExternoDTO save(ClienteExternoDTO clienteExternoDTO) {
        log.debug("Request to save ClienteExterno : {}", clienteExternoDTO);
        ClienteExterno clienteExterno = clienteExternoMapper.toEntity(clienteExternoDTO);
        clienteExterno = clienteExternoRepository.save(clienteExterno);
        return clienteExternoMapper.toDto(clienteExterno);
    }

    /**
     * Update a clienteExterno.
     *
     * @param clienteExternoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteExternoDTO update(ClienteExternoDTO clienteExternoDTO) {
        log.debug("Request to save ClienteExterno : {}", clienteExternoDTO);
        ClienteExterno clienteExterno = clienteExternoMapper.toEntity(clienteExternoDTO);
        clienteExterno = clienteExternoRepository.save(clienteExterno);
        return clienteExternoMapper.toDto(clienteExterno);
    }

    /**
     * Partially update a clienteExterno.
     *
     * @param clienteExternoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClienteExternoDTO> partialUpdate(ClienteExternoDTO clienteExternoDTO) {
        log.debug("Request to partially update ClienteExterno : {}", clienteExternoDTO);

        return clienteExternoRepository
            .findById(clienteExternoDTO.getId())
            .map(existingClienteExterno -> {
                clienteExternoMapper.partialUpdate(existingClienteExterno, clienteExternoDTO);

                return existingClienteExterno;
            })
            .map(clienteExternoRepository::save)
            .map(clienteExternoMapper::toDto);
    }

    /**
     * Get all the clienteExternos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteExternoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteExternos");
        return clienteExternoRepository.findAll(pageable).map(clienteExternoMapper::toDto);
    }

    /**
     * Get one clienteExterno by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteExternoDTO> findOne(Long id) {
        log.debug("Request to get ClienteExterno : {}", id);
        return clienteExternoRepository.findById(id).map(clienteExternoMapper::toDto);
    }

    /**
     * Delete the clienteExterno by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClienteExterno : {}", id);
        clienteExternoRepository.deleteById(id);
    }
}
