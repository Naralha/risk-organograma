package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.ClienteExterno;
import io.sld.riskcomplianceservice.domain.repository.ClienteExternoRepository;
import io.sld.riskcomplianceservice.domain.service.ClienteExternoQueryService;
import io.sld.riskcomplianceservice.domain.service.ClienteExternoService;
import io.sld.riskcomplianceservice.domain.service.criteria.ClienteExternoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoDTO;
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
 * REST controller for managing {@link ClienteExterno}.
 */
@RestController
@RequestMapping("/api")
public class ClienteExternoResource {

    private final Logger log = LoggerFactory.getLogger(ClienteExternoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceClienteExterno";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ClienteExternoService clienteExternoService;

    private final ClienteExternoRepository clienteExternoRepository;

    private final ClienteExternoQueryService clienteExternoQueryService;

    public ClienteExternoResource(
        ClienteExternoService clienteExternoService,
        ClienteExternoRepository clienteExternoRepository,
        ClienteExternoQueryService clienteExternoQueryService
    ) {
        this.clienteExternoService = clienteExternoService;
        this.clienteExternoRepository = clienteExternoRepository;
        this.clienteExternoQueryService = clienteExternoQueryService;
    }

    /**
     * {@code POST  /cliente-externos} : Create a new clienteExterno.
     *
     * @param clienteExternoDTO the clienteExternoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clienteExternoDTO, or with status {@code 400 (Bad Request)} if the clienteExterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cliente-externos")
    public ResponseEntity<ClienteExternoDTO> createClienteExterno(@Valid @RequestBody ClienteExternoDTO clienteExternoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ClienteExterno : {}", clienteExternoDTO);
        if (clienteExternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new clienteExterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClienteExternoDTO result = clienteExternoService.save(clienteExternoDTO);
        return ResponseEntity
            .created(new URI("/api/cliente-externos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cliente-externos/:id} : Updates an existing clienteExterno.
     *
     * @param id the id of the clienteExternoDTO to save.
     * @param clienteExternoDTO the clienteExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteExternoDTO,
     * or with status {@code 400 (Bad Request)} if the clienteExternoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clienteExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cliente-externos/{id}")
    public ResponseEntity<ClienteExternoDTO> updateClienteExterno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ClienteExternoDTO clienteExternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ClienteExterno : {}, {}", id, clienteExternoDTO);
        if (clienteExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clienteExternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteExternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClienteExternoDTO result = clienteExternoService.update(clienteExternoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteExternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cliente-externos/:id} : Partial updates given fields of an existing clienteExterno, field will ignore if it is null
     *
     * @param id the id of the clienteExternoDTO to save.
     * @param clienteExternoDTO the clienteExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clienteExternoDTO,
     * or with status {@code 400 (Bad Request)} if the clienteExternoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the clienteExternoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the clienteExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cliente-externos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClienteExternoDTO> partialUpdateClienteExterno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ClienteExternoDTO clienteExternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClienteExterno partially : {}, {}", id, clienteExternoDTO);
        if (clienteExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clienteExternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clienteExternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClienteExternoDTO> result = clienteExternoService.partialUpdate(clienteExternoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clienteExternoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cliente-externos} : get all the clienteExternos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clienteExternos in body.
     */
    @GetMapping("/cliente-externos")
    public ResponseEntity<List<ClienteExternoDTO>> getAllClienteExternos(
        ClienteExternoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ClienteExternos by criteria: {}", criteria);
        Page<ClienteExternoDTO> page = clienteExternoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cliente-externos/count} : count all the clienteExternos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/cliente-externos/count")
    public ResponseEntity<Long> countClienteExternos(ClienteExternoCriteria criteria) {
        log.debug("REST request to count ClienteExternos by criteria: {}", criteria);
        return ResponseEntity.ok().body(clienteExternoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cliente-externos/:id} : get the "id" clienteExterno.
     *
     * @param id the id of the clienteExternoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clienteExternoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cliente-externos/{id}")
    public ResponseEntity<ClienteExternoDTO> getClienteExterno(@PathVariable Long id) {
        log.debug("REST request to get ClienteExterno : {}", id);
        Optional<ClienteExternoDTO> clienteExternoDTO = clienteExternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clienteExternoDTO);
    }

    /**
     * {@code DELETE  /cliente-externos/:id} : delete the "id" clienteExterno.
     *
     * @param id the id of the clienteExternoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cliente-externos/{id}")
    public ResponseEntity<Void> deleteClienteExterno(@PathVariable Long id) {
        log.debug("REST request to delete ClienteExterno : {}", id);
        clienteExternoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
