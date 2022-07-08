package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.ComplianceExternoProcessoRepository;
import io.sld.riskcomplianceservice.service.ComplianceExternoProcessoQueryService;
import io.sld.riskcomplianceservice.service.ComplianceExternoProcessoService;
import io.sld.riskcomplianceservice.service.criteria.ComplianceExternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceExternoProcessoDTO;
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
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.ComplianceExternoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class ComplianceExternoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ComplianceExternoProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceComplianceExternoProcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplianceExternoProcessoService complianceExternoProcessoService;

    private final ComplianceExternoProcessoRepository complianceExternoProcessoRepository;

    private final ComplianceExternoProcessoQueryService complianceExternoProcessoQueryService;

    public ComplianceExternoProcessoResource(
        ComplianceExternoProcessoService complianceExternoProcessoService,
        ComplianceExternoProcessoRepository complianceExternoProcessoRepository,
        ComplianceExternoProcessoQueryService complianceExternoProcessoQueryService
    ) {
        this.complianceExternoProcessoService = complianceExternoProcessoService;
        this.complianceExternoProcessoRepository = complianceExternoProcessoRepository;
        this.complianceExternoProcessoQueryService = complianceExternoProcessoQueryService;
    }

    /**
     * {@code POST  /compliance-externo-processos} : Create a new complianceExternoProcesso.
     *
     * @param complianceExternoProcessoDTO the complianceExternoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complianceExternoProcessoDTO, or with status {@code 400 (Bad Request)} if the complianceExternoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compliance-externo-processos")
    public ResponseEntity<ComplianceExternoProcessoDTO> createComplianceExternoProcesso(
        @Valid @RequestBody ComplianceExternoProcessoDTO complianceExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ComplianceExternoProcesso : {}", complianceExternoProcessoDTO);
        if (complianceExternoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new complianceExternoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComplianceExternoProcessoDTO result = complianceExternoProcessoService.save(complianceExternoProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/compliance-externo-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compliance-externo-processos/:id} : Updates an existing complianceExternoProcesso.
     *
     * @param id the id of the complianceExternoProcessoDTO to save.
     * @param complianceExternoProcessoDTO the complianceExternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceExternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceExternoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complianceExternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compliance-externo-processos/{id}")
    public ResponseEntity<ComplianceExternoProcessoDTO> updateComplianceExternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComplianceExternoProcessoDTO complianceExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComplianceExternoProcesso : {}, {}", id, complianceExternoProcessoDTO);
        if (complianceExternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceExternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceExternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComplianceExternoProcessoDTO result = complianceExternoProcessoService.update(complianceExternoProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceExternoProcessoDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /compliance-externo-processos/:id} : Partial updates given fields of an existing complianceExternoProcesso, field will ignore if it is null
     *
     * @param id the id of the complianceExternoProcessoDTO to save.
     * @param complianceExternoProcessoDTO the complianceExternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceExternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceExternoProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the complianceExternoProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the complianceExternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compliance-externo-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComplianceExternoProcessoDTO> partialUpdateComplianceExternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComplianceExternoProcessoDTO complianceExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComplianceExternoProcesso partially : {}, {}", id, complianceExternoProcessoDTO);
        if (complianceExternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceExternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceExternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComplianceExternoProcessoDTO> result = complianceExternoProcessoService.partialUpdate(complianceExternoProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceExternoProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /compliance-externo-processos} : get all the complianceExternoProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complianceExternoProcessos in body.
     */
    @GetMapping("/compliance-externo-processos")
    public ResponseEntity<List<ComplianceExternoProcessoDTO>> getAllComplianceExternoProcessos(
        ComplianceExternoProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ComplianceExternoProcessos by criteria: {}", criteria);
        Page<ComplianceExternoProcessoDTO> page = complianceExternoProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compliance-externo-processos/count} : count all the complianceExternoProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/compliance-externo-processos/count")
    public ResponseEntity<Long> countComplianceExternoProcessos(ComplianceExternoProcessoCriteria criteria) {
        log.debug("REST request to count ComplianceExternoProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(complianceExternoProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compliance-externo-processos/:id} : get the "id" complianceExternoProcesso.
     *
     * @param id the id of the complianceExternoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complianceExternoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compliance-externo-processos/{id}")
    public ResponseEntity<ComplianceExternoProcessoDTO> getComplianceExternoProcesso(@PathVariable Long id) {
        log.debug("REST request to get ComplianceExternoProcesso : {}", id);
        Optional<ComplianceExternoProcessoDTO> complianceExternoProcessoDTO = complianceExternoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complianceExternoProcessoDTO);
    }

    /**
     * {@code DELETE  /compliance-externo-processos/:id} : delete the "id" complianceExternoProcesso.
     *
     * @param id the id of the complianceExternoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compliance-externo-processos/{id}")
    public ResponseEntity<Void> deleteComplianceExternoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete ComplianceExternoProcesso : {}", id);
        complianceExternoProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
