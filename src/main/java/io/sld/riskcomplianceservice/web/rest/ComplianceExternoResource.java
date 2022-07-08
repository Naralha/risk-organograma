package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.ComplianceExternoRepository;
import io.sld.riskcomplianceservice.service.ComplianceExternoQueryService;
import io.sld.riskcomplianceservice.service.ComplianceExternoService;
import io.sld.riskcomplianceservice.service.criteria.ComplianceExternoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceExternoDTO;
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
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.ComplianceExterno}.
 */
@RestController
@RequestMapping("/api")
public class ComplianceExternoResource {

    private final Logger log = LoggerFactory.getLogger(ComplianceExternoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceComplianceExterno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplianceExternoService complianceExternoService;

    private final ComplianceExternoRepository complianceExternoRepository;

    private final ComplianceExternoQueryService complianceExternoQueryService;

    public ComplianceExternoResource(
        ComplianceExternoService complianceExternoService,
        ComplianceExternoRepository complianceExternoRepository,
        ComplianceExternoQueryService complianceExternoQueryService
    ) {
        this.complianceExternoService = complianceExternoService;
        this.complianceExternoRepository = complianceExternoRepository;
        this.complianceExternoQueryService = complianceExternoQueryService;
    }

    /**
     * {@code POST  /compliance-externos} : Create a new complianceExterno.
     *
     * @param complianceExternoDTO the complianceExternoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complianceExternoDTO, or with status {@code 400 (Bad Request)} if the complianceExterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compliance-externos")
    public ResponseEntity<ComplianceExternoDTO> createComplianceExterno(@Valid @RequestBody ComplianceExternoDTO complianceExternoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ComplianceExterno : {}", complianceExternoDTO);
        if (complianceExternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new complianceExterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComplianceExternoDTO result = complianceExternoService.save(complianceExternoDTO);
        return ResponseEntity
            .created(new URI("/api/compliance-externos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compliance-externos/:id} : Updates an existing complianceExterno.
     *
     * @param id the id of the complianceExternoDTO to save.
     * @param complianceExternoDTO the complianceExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceExternoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceExternoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complianceExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compliance-externos/{id}")
    public ResponseEntity<ComplianceExternoDTO> updateComplianceExterno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComplianceExternoDTO complianceExternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComplianceExterno : {}, {}", id, complianceExternoDTO);
        if (complianceExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceExternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceExternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComplianceExternoDTO result = complianceExternoService.update(complianceExternoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceExternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /compliance-externos/:id} : Partial updates given fields of an existing complianceExterno, field will ignore if it is null
     *
     * @param id the id of the complianceExternoDTO to save.
     * @param complianceExternoDTO the complianceExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceExternoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceExternoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the complianceExternoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the complianceExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compliance-externos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComplianceExternoDTO> partialUpdateComplianceExterno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComplianceExternoDTO complianceExternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComplianceExterno partially : {}, {}", id, complianceExternoDTO);
        if (complianceExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceExternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceExternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComplianceExternoDTO> result = complianceExternoService.partialUpdate(complianceExternoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceExternoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /compliance-externos} : get all the complianceExternos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complianceExternos in body.
     */
    @GetMapping("/compliance-externos")
    public ResponseEntity<List<ComplianceExternoDTO>> getAllComplianceExternos(
        ComplianceExternoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ComplianceExternos by criteria: {}", criteria);
        Page<ComplianceExternoDTO> page = complianceExternoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compliance-externos/count} : count all the complianceExternos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/compliance-externos/count")
    public ResponseEntity<Long> countComplianceExternos(ComplianceExternoCriteria criteria) {
        log.debug("REST request to count ComplianceExternos by criteria: {}", criteria);
        return ResponseEntity.ok().body(complianceExternoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compliance-externos/:id} : get the "id" complianceExterno.
     *
     * @param id the id of the complianceExternoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complianceExternoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compliance-externos/{id}")
    public ResponseEntity<ComplianceExternoDTO> getComplianceExterno(@PathVariable Long id) {
        log.debug("REST request to get ComplianceExterno : {}", id);
        Optional<ComplianceExternoDTO> complianceExternoDTO = complianceExternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complianceExternoDTO);
    }

    /**
     * {@code DELETE  /compliance-externos/:id} : delete the "id" complianceExterno.
     *
     * @param id the id of the complianceExternoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compliance-externos/{id}")
    public ResponseEntity<Void> deleteComplianceExterno(@PathVariable Long id) {
        log.debug("REST request to delete ComplianceExterno : {}", id);
        complianceExternoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
