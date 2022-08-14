package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.repository.ProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.ProcessoQueryService;
import io.sld.riskcomplianceservice.domain.service.ProcessoService;
import io.sld.riskcomplianceservice.domain.service.criteria.ProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.resource.errors.BadRequestAlertException;

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
import io.sld.riskcomplianceservice.resource.utils.HeaderUtil;
import io.sld.riskcomplianceservice.resource.utils.PaginationUtil;
import io.sld.riskcomplianceservice.resource.utils.ResponseUtil;

/**
 * REST controller for managing {@link Processo}.
 */
@RestController
@RequestMapping("/api")
public class ProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceProcesso";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ProcessoService processoService;

    private final ProcessoRepository processoRepository;

    private final ProcessoQueryService processoQueryService;

    public ProcessoResource(
        ProcessoService processoService,
        ProcessoRepository processoRepository,
        ProcessoQueryService processoQueryService
    ) {
        this.processoService = processoService;
        this.processoRepository = processoRepository;
        this.processoQueryService = processoQueryService;
    }

    /**
     * {@code POST  /processos} : Create a new processo.
     *
     * @param processoDTO the processoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processoDTO, or with status {@code 400 (Bad Request)} if the processo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/processos")
    public ResponseEntity<ProcessoDTO> createProcesso(@Valid @RequestBody ProcessoDTO processoDTO) throws URISyntaxException {
        log.debug("REST request to save Processo : {}", processoDTO);
        if (processoDTO.getId() != null) {
            throw new BadRequestAlertException("A new processo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessoDTO result = processoService.save(processoDTO);
        return ResponseEntity
            .created(new URI("/api/processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /processos/:id} : Updates an existing processo.
     *
     * @param id the id of the processoDTO to save.
     * @param processoDTO the processoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoDTO,
     * or with status {@code 400 (Bad Request)} if the processoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/processos/{id}")
    public ResponseEntity<ProcessoDTO> updateProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcessoDTO processoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Processo : {}, {}", id, processoDTO);
        if (processoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessoDTO result = processoService.update(processoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /processos/:id} : Partial updates given fields of an existing processo, field will ignore if it is null
     *
     * @param id the id of the processoDTO to save.
     * @param processoDTO the processoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processoDTO,
     * or with status {@code 400 (Bad Request)} if the processoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessoDTO> partialUpdateProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcessoDTO processoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Processo partially : {}, {}", id, processoDTO);
        if (processoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessoDTO> result = processoService.partialUpdate(processoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /processos} : get all the processos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processos in body.
     */
    @GetMapping("/processos")
    public ResponseEntity<List<ProcessoDTO>> getAllProcessos(
        ProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Processos by criteria: {}", criteria);
        Page<ProcessoDTO> page = processoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /processos/count} : count all the processos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/processos/count")
    public ResponseEntity<Long> countProcessos(ProcessoCriteria criteria) {
        log.debug("REST request to count Processos by criteria: {}", criteria);
        return ResponseEntity.ok().body(processoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /processos/:id} : get the "id" processo.
     *
     * @param id the id of the processoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/processos/{id}")
    public ResponseEntity<ProcessoDTO> getProcesso(@PathVariable Long id) {
        log.debug("REST request to get Processo : {}", id);
        Optional<ProcessoDTO> processoDTO = processoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processoDTO);
    }

    /**
     * {@code DELETE  /processos/:id} : delete the "id" processo.
     *
     * @param id the id of the processoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/processos/{id}")
    public ResponseEntity<Void> deleteProcesso(@PathVariable Long id) {
        log.debug("REST request to delete Processo : {}", id);
        processoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
