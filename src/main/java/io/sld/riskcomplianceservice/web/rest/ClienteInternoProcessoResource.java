package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.ClienteInternoProcessoRepository;
import io.sld.riskcomplianceservice.service.ClienteInternoProcessoQueryService;
import io.sld.riskcomplianceservice.service.ClienteInternoProcessoService;
import io.sld.riskcomplianceservice.service.criteria.ClienteInternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ClienteInternoProcessoDTO;
import io.sld.riskcomplianceservice.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.ClienteInternoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class ClienteInternoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ClienteInternoProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceClienteInternoProcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClienteInternoProcessoService clienteInternoProcessoService;

    private final ClienteInternoProcessoRepository clienteInternoProcessoRepository;

    private final ClienteInternoProcessoQueryService clienteInternoProcessoQueryService;

    public ClienteInternoProcessoResource(
        ClienteInternoProcessoService clienteInternoProcessoService,
        ClienteInternoProcessoRepository clienteInternoProcessoRepository,
        ClienteInternoProcessoQueryService clienteInternoProcessoQueryService
    ) {
        this.clienteInternoProcessoService = clienteInternoProcessoService;
        this.clienteInternoProcessoRepository = clienteInternoProcessoRepository;
        this.clienteInternoProcessoQueryService = clienteInternoProcessoQueryService;
    }

    /**
     * {@code POST  /cliente-interno-processos} : Create a new clienteInternoProcesso.
     *
     * @param clienteInternoProcessoDTO the clienteInternoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clienteInternoProcessoDTO, or with status {@code 400 (Bad Request)} if the clienteInternoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cliente-interno-processos")
    public ResponseEntity<ClienteInternoProcessoDTO> createClienteInternoProcesso(
        @Valid @RequestBody ClienteInternoProcessoDTO clienteInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ClienteInternoProcesso : {}", clienteInternoProcessoDTO);
        if (clienteInternoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new clienteInternoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteInternoProcessoDTO result = clienteInternoProcessoService.save(clienteInternoProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/cliente-interno-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cliente-interno-processos/:id} : Updates an existing clienteInternoProcesso.
     *
     * @param id the id of the clienteInternoProcessoDTO to save.
     * @param clienteInternoProcessoDTO the clienteInternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteInternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the clienteInternoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clienteInternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cliente-interno-processos/{id}")
    public ResponseEntity<ClienteInternoProcessoDTO> updateClienteInternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClienteInternoProcessoDTO clienteInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClienteInternoProcesso : {}, {}", id, clienteInternoProcessoDTO);
        if (clienteInternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clienteInternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteInternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClienteInternoProcessoDTO result = clienteInternoProcessoService.update(clienteInternoProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteInternoProcessoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cliente-interno-processos/:id} : Partial updates given fields of an existing clienteInternoProcesso, field will ignore if it is null
     *
     * @param id the id of the clienteInternoProcessoDTO to save.
     * @param clienteInternoProcessoDTO the clienteInternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteInternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the clienteInternoProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clienteInternoProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clienteInternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cliente-interno-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClienteInternoProcessoDTO> partialUpdateClienteInternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClienteInternoProcessoDTO clienteInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClienteInternoProcesso partially : {}, {}", id, clienteInternoProcessoDTO);
        if (clienteInternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clienteInternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteInternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClienteInternoProcessoDTO> result = clienteInternoProcessoService.partialUpdate(clienteInternoProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteInternoProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cliente-interno-processos} : get all the clienteInternoProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clienteInternoProcessos in body.
     */
    @GetMapping("/cliente-interno-processos")
    public ResponseEntity<List<ClienteInternoProcessoDTO>> getAllClienteInternoProcessos(
        ClienteInternoProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClienteInternoProcessos by criteria: {}", criteria);
        Page<ClienteInternoProcessoDTO> page = clienteInternoProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cliente-interno-processos/count} : count all the clienteInternoProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cliente-interno-processos/count")
    public ResponseEntity<Long> countClienteInternoProcessos(ClienteInternoProcessoCriteria criteria) {
        log.debug("REST request to count ClienteInternoProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(clienteInternoProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cliente-interno-processos/:id} : get the "id" clienteInternoProcesso.
     *
     * @param id the id of the clienteInternoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clienteInternoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cliente-interno-processos/{id}")
    public ResponseEntity<ClienteInternoProcessoDTO> getClienteInternoProcesso(@PathVariable Long id) {
        log.debug("REST request to get ClienteInternoProcesso : {}", id);
        Optional<ClienteInternoProcessoDTO> clienteInternoProcessoDTO = clienteInternoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteInternoProcessoDTO);
    }

    /**
     * {@code DELETE  /cliente-interno-processos/:id} : delete the "id" clienteInternoProcesso.
     *
     * @param id the id of the clienteInternoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cliente-interno-processos/{id}")
    public ResponseEntity<Void> deleteClienteInternoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete ClienteInternoProcesso : {}", id);
        clienteInternoProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
