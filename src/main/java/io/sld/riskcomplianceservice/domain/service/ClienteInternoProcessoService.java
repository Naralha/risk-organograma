package io.sld.riskcomplianceservice.domain.service;

import io.sld.riskcomplianceservice.domain.entity.ClienteInternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.ClienteInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ClienteInternoProcessoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ClienteInternoProcesso}.
 */
@Service
@Transactional
public class ClienteInternoProcessoService {

    private final Logger log = LoggerFactory.getLogger(ClienteInternoProcessoService.class);

    private final ClienteInternoProcessoRepository clienteInternoProcessoRepository;

    private final ClienteInternoProcessoMapper clienteInternoProcessoMapper;

    public ClienteInternoProcessoService(
        ClienteInternoProcessoRepository clienteInternoProcessoRepository,
        ClienteInternoProcessoMapper clienteInternoProcessoMapper
    ) {
        this.clienteInternoProcessoRepository = clienteInternoProcessoRepository;
        this.clienteInternoProcessoMapper = clienteInternoProcessoMapper;
    }

    /**
     * Save a clienteInternoProcesso.
     *
     * @param clienteInternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteInternoProcessoDTO save(ClienteInternoProcessoDTO clienteInternoProcessoDTO) {
        log.debug("Request to save ClienteInternoProcesso : {}", clienteInternoProcessoDTO);
        ClienteInternoProcesso clienteInternoProcesso = clienteInternoProcessoMapper.toEntity(clienteInternoProcessoDTO);
        clienteInternoProcesso = clienteInternoProcessoRepository.save(clienteInternoProcesso);
        return clienteInternoProcessoMapper.toDto(clienteInternoProcesso);
    }

    /**
     * Update a clienteInternoProcesso.
     *
     * @param clienteInternoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    public ClienteInternoProcessoDTO update(ClienteInternoProcessoDTO clienteInternoProcessoDTO) {
        log.debug("Request to save ClienteInternoProcesso : {}", clienteInternoProcessoDTO);
        ClienteInternoProcesso clienteInternoProcesso = clienteInternoProcessoMapper.toEntity(clienteInternoProcessoDTO);
        clienteInternoProcesso = clienteInternoProcessoRepository.save(clienteInternoProcesso);
        return clienteInternoProcessoMapper.toDto(clienteInternoProcesso);
    }

    /**
     * Partially update a clienteInternoProcesso.
     *
     * @param clienteInternoProcessoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClienteInternoProcessoDTO> partialUpdate(ClienteInternoProcessoDTO clienteInternoProcessoDTO) {
        log.debug("Request to partially update ClienteInternoProcesso : {}", clienteInternoProcessoDTO);

        return clienteInternoProcessoRepository
            .findById(clienteInternoProcessoDTO.getId())
            .map(existingClienteInternoProcesso -> {
                clienteInternoProcessoMapper.partialUpdate(existingClienteInternoProcesso, clienteInternoProcessoDTO);

                return existingClienteInternoProcesso;
            })
            .map(clienteInternoProcessoRepository::save)
            .map(clienteInternoProcessoMapper::toDto);
    }

    /**
     * Get all the clienteInternoProcessos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClienteInternoProcessoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClienteInternoProcessos");
        return clienteInternoProcessoRepository.findAll(pageable).map(clienteInternoProcessoMapper::toDto);
    }

    /**
     * Get one clienteInternoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClienteInternoProcessoDTO> findOne(Long id) {
        log.debug("Request to get ClienteInternoProcesso : {}", id);
        return clienteInternoProcessoRepository.findById(id).map(clienteInternoProcessoMapper::toDto);
    }

    /**
     * Delete the clienteInternoProcesso by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClienteInternoProcesso : {}", id);
        clienteInternoProcessoRepository.deleteById(id);
    }
}
