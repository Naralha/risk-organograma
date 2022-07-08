package io.sld.riskcomplianceservice.service;

import io.sld.riskcomplianceservice.domain.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.repository.ClienteExternoProcessoRepository;
import io.sld.riskcomplianceservice.service.dto.ClienteExternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.ClienteExternoProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClienteExternoProcesso}.
 */
@Service
@Transactional
public class ClienteExternoProcessoService {

    private final Logger log = LoggerFactory.getLogger(ClienteExternoProcessoService.class);

    private final ClienteExternoProcessoRepository clienteExternoProcessoRepository;

    private final ClienteExternoProcessoMapper clienteExternoProcessoMapper;

    public ClienteExternoProcessoService(
        ClienteExternoProcessoRepository clienteExternoProcessoRepository,
        ClienteExternoProcessoMapper clienteExternoProcessoMapper
    ) {
        this.clienteExternoProcessoRepository = clienteExternoProcessoRepository;
        this.clienteExternoProcessoMapper = clienteExternoProcessoMapper;
    }

    /**
     * Save a clienteExternoProcesso.
     *
     * @param clienteExternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteExternoProcessoDTO save(ClienteExternoProcessoDTO clienteExternoProcessoDTO) {
        log.debug("Request to save ClienteExternoProcesso : {}", clienteExternoProcessoDTO);
        ClienteExternoProcesso clienteExternoProcesso = clienteExternoProcessoMapper.toEntity(clienteExternoProcessoDTO);
        clienteExternoProcesso = clienteExternoProcessoRepository.save(clienteExternoProcesso);
        return clienteExternoProcessoMapper.toDto(clienteExternoProcesso);
    }

    /**
     * Update a clienteExternoProcesso.
     *
     * @param clienteExternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteExternoProcessoDTO update(ClienteExternoProcessoDTO clienteExternoProcessoDTO) {
        log.debug("Request to save ClienteExternoProcesso : {}", clienteExternoProcessoDTO);
        ClienteExternoProcesso clienteExternoProcesso = clienteExternoProcessoMapper.toEntity(clienteExternoProcessoDTO);
        clienteExternoProcesso = clienteExternoProcessoRepository.save(clienteExternoProcesso);
        return clienteExternoProcessoMapper.toDto(clienteExternoProcesso);
    }

    /**
     * Partially update a clienteExternoProcesso.
     *
     * @param clienteExternoProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClienteExternoProcessoDTO> partialUpdate(ClienteExternoProcessoDTO clienteExternoProcessoDTO) {
        log.debug("Request to partially update ClienteExternoProcesso : {}", clienteExternoProcessoDTO);

        return clienteExternoProcessoRepository
            .findById(clienteExternoProcessoDTO.getId())
            .map(existingClienteExternoProcesso -> {
                clienteExternoProcessoMapper.partialUpdate(existingClienteExternoProcesso, clienteExternoProcessoDTO);

                return existingClienteExternoProcesso;
            })
            .map(clienteExternoProcessoRepository::save)
            .map(clienteExternoProcessoMapper::toDto);
    }

    /**
     * Get all the clienteExternoProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteExternoProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteExternoProcessos");
        return clienteExternoProcessoRepository.findAll(pageable).map(clienteExternoProcessoMapper::toDto);
    }

    /**
     * Get one clienteExternoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteExternoProcessoDTO> findOne(Long id) {
        log.debug("Request to get ClienteExternoProcesso : {}", id);
        return clienteExternoProcessoRepository.findById(id).map(clienteExternoProcessoMapper::toDto);
    }

    /**
     * Delete the clienteExternoProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClienteExternoProcesso : {}", id);
        clienteExternoProcessoRepository.deleteById(id);
    }
}
