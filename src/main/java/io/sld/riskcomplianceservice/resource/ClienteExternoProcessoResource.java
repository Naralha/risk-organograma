package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.ClienteExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.ClienteExternoProcessoQueryService;
import io.sld.riskcomplianceservice.domain.service.ClienteExternoProcessoService;
import io.sld.riskcomplianceservice.domain.service.criteria.ClienteExternoProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoProcessoDTO;
import io.sld.riskcomplianceservice.resource.errors.BadRequestAlertException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.sld.riskcomplianceservice.resource.utils.HeaderUtil;
import io.sld.riskcomplianceservice.resource.utils.PaginationUtil;
import io.sld.riskcomplianceservice.resource.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * REST controller for managing {@link ClienteExternoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class ClienteExternoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ClienteExternoProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceClienteExternoProcesso";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ClienteExternoProcessoService clienteExternoProcessoService;

    private final ClienteExternoProcessoRepository clienteExternoProcessoRepository;

    private final ClienteExternoProcessoQueryService clienteExternoProcessoQueryService;

    public ClienteExternoProcessoResource(
        ClienteExternoProcessoService clienteExternoProcessoService,
        ClienteExternoProcessoRepository clienteExternoProcessoRepository,
        ClienteExternoProcessoQueryService clienteExternoProcessoQueryService
    ) {
        this.clienteExternoProcessoService = clienteExternoProcessoService;
        this.clienteExternoProcessoRepository = clienteExternoProcessoRepository;
        this.clienteExternoProcessoQueryService = clienteExternoProcessoQueryService;
    }

    /**
     * {@code POST  /cliente-externo-processos} : Create a new clienteExternoProcesso.
     *
     * @param clienteExternoProcessoDTO the clienteExternoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clienteExternoProcessoDTO, or with status {@code 400 (Bad Request)} if the clienteExternoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cliente-externo-processos")
    public ResponseEntity<ClienteExternoProcessoDTO> createClienteExternoProcesso(
        @Valid @RequestBody ClienteExternoProcessoDTO clienteExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ClienteExternoProcesso : {}", clienteExternoProcessoDTO);
        if (clienteExternoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new clienteExternoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteExternoProcessoDTO result = clienteExternoProcessoService.save(clienteExternoProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/cliente-externo-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cliente-externo-processos/:id} : Updates an existing clienteExternoProcesso.
     *
     * @param id the id of the clienteExternoProcessoDTO to save.
     * @param clienteExternoProcessoDTO the clienteExternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteExternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the clienteExternoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clienteExternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cliente-externo-processos/{id}")
    public ResponseEntity<ClienteExternoProcessoDTO> updateClienteExternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClienteExternoProcessoDTO clienteExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClienteExternoProcesso : {}, {}", id, clienteExternoProcessoDTO);
        if (clienteExternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clienteExternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteExternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClienteExternoProcessoDTO result = clienteExternoProcessoService.update(clienteExternoProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteExternoProcessoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cliente-externo-processos/:id} : Partial updates given fields of an existing clienteExternoProcesso, field will ignore if it is null
     *
     * @param id the id of the clienteExternoProcessoDTO to save.
     * @param clienteExternoProcessoDTO the clienteExternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteExternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the clienteExternoProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clienteExternoProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clienteExternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cliente-externo-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClienteExternoProcessoDTO> partialUpdateClienteExternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClienteExternoProcessoDTO clienteExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClienteExternoProcesso partially : {}, {}", id, clienteExternoProcessoDTO);
        if (clienteExternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clienteExternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteExternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClienteExternoProcessoDTO> result = clienteExternoProcessoService.partialUpdate(clienteExternoProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteExternoProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cliente-externo-processos} : get all the clienteExternoProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clienteExternoProcessos in body.
     */
    @GetMapping("/cliente-externo-processos")
    public ResponseEntity<List<ClienteExternoProcessoDTO>> getAllClienteExternoProcessos(
        ClienteExternoProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClienteExternoProcessos by criteria: {}", criteria);
        Page<ClienteExternoProcessoDTO> page = clienteExternoProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cliente-externo-processos/count} : count all the clienteExternoProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cliente-externo-processos/count")
    public ResponseEntity<Long> countClienteExternoProcessos(ClienteExternoProcessoCriteria criteria) {
        log.debug("REST request to count ClienteExternoProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(clienteExternoProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cliente-externo-processos/:id} : get the "id" clienteExternoProcesso.
     *
     * @param id the id of the clienteExternoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clienteExternoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cliente-externo-processos/{id}")
    public ResponseEntity<ClienteExternoProcessoDTO> getClienteExternoProcesso(@PathVariable Long id) {
        log.debug("REST request to get ClienteExternoProcesso : {}", id);
        Optional<ClienteExternoProcessoDTO> clienteExternoProcessoDTO = clienteExternoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteExternoProcessoDTO);
    }

    /**
     * {@code DELETE  /cliente-externo-processos/:id} : delete the "id" clienteExternoProcesso.
     *
     * @param id the id of the clienteExternoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cliente-externo-processos/{id}")
    public ResponseEntity<Void> deleteClienteExternoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete ClienteExternoProcesso : {}", id);
        clienteExternoProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
