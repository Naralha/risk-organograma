package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.ComplianceInternoRepository;
import io.sld.riskcomplianceservice.service.ComplianceInternoQueryService;
import io.sld.riskcomplianceservice.service.ComplianceInternoService;
import io.sld.riskcomplianceservice.service.criteria.ComplianceInternoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceInternoDTO;
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
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.ComplianceInterno}.
 */
@RestController
@RequestMapping("/api")
public class ComplianceInternoResource {

    private final Logger log = LoggerFactory.getLogger(ComplianceInternoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceComplianceInterno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplianceInternoService complianceInternoService;

    private final ComplianceInternoRepository complianceInternoRepository;

    private final ComplianceInternoQueryService complianceInternoQueryService;

    public ComplianceInternoResource(
        ComplianceInternoService complianceInternoService,
        ComplianceInternoRepository complianceInternoRepository,
        ComplianceInternoQueryService complianceInternoQueryService
    ) {
        this.complianceInternoService = complianceInternoService;
        this.complianceInternoRepository = complianceInternoRepository;
        this.complianceInternoQueryService = complianceInternoQueryService;
    }

    /**
     * {@code POST  /compliance-internos} : Create a new complianceInterno.
     *
     * @param complianceInternoDTO the complianceInternoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complianceInternoDTO, or with status {@code 400 (Bad Request)} if the complianceInterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compliance-internos")
    public ResponseEntity<ComplianceInternoDTO> createComplianceInterno(@Valid @RequestBody ComplianceInternoDTO complianceInternoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ComplianceInterno : {}", complianceInternoDTO);
        if (complianceInternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new complianceInterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComplianceInternoDTO result = complianceInternoService.save(complianceInternoDTO);
        return ResponseEntity
            .created(new URI("/api/compliance-internos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compliance-internos/:id} : Updates an existing complianceInterno.
     *
     * @param id the id of the complianceInternoDTO to save.
     * @param complianceInternoDTO the complianceInternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceInternoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceInternoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complianceInternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compliance-internos/{id}")
    public ResponseEntity<ComplianceInternoDTO> updateComplianceInterno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComplianceInternoDTO complianceInternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComplianceInterno : {}, {}", id, complianceInternoDTO);
        if (complianceInternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceInternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceInternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComplianceInternoDTO result = complianceInternoService.update(complianceInternoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceInternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compliance-internos/:id} : Partial updates given fields of an existing complianceInterno, field will ignore if it is null
     *
     * @param id the id of the complianceInternoDTO to save.
     * @param complianceInternoDTO the complianceInternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceInternoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceInternoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the complianceInternoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the complianceInternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compliance-internos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComplianceInternoDTO> partialUpdateComplianceInterno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComplianceInternoDTO complianceInternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComplianceInterno partially : {}, {}", id, complianceInternoDTO);
        if (complianceInternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceInternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceInternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComplianceInternoDTO> result = complianceInternoService.partialUpdate(complianceInternoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceInternoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /compliance-internos} : get all the complianceInternos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complianceInternos in body.
     */
    @GetMapping("/compliance-internos")
    public ResponseEntity<List<ComplianceInternoDTO>> getAllComplianceInternos(
        ComplianceInternoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ComplianceInternos by criteria: {}", criteria);
        Page<ComplianceInternoDTO> page = complianceInternoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compliance-internos/count} : count all the complianceInternos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/compliance-internos/count")
    public ResponseEntity<Long> countComplianceInternos(ComplianceInternoCriteria criteria) {
        log.debug("REST request to count ComplianceInternos by criteria: {}", criteria);
        return ResponseEntity.ok().body(complianceInternoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compliance-internos/:id} : get the "id" complianceInterno.
     *
     * @param id the id of the complianceInternoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complianceInternoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compliance-internos/{id}")
    public ResponseEntity<ComplianceInternoDTO> getComplianceInterno(@PathVariable Long id) {
        log.debug("REST request to get ComplianceInterno : {}", id);
        Optional<ComplianceInternoDTO> complianceInternoDTO = complianceInternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complianceInternoDTO);
    }

    /**
     * {@code DELETE  /compliance-internos/:id} : delete the "id" complianceInterno.
     *
     * @param id the id of the complianceInternoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compliance-internos/{id}")
    public ResponseEntity<Void> deleteComplianceInterno(@PathVariable Long id) {
        log.debug("REST request to delete ComplianceInterno : {}", id);
        complianceInternoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
