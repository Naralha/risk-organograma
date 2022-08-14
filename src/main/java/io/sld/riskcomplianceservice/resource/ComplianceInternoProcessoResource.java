package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.ComplianceInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.ComplianceInternoProcessoQueryService;
import io.sld.riskcomplianceservice.domain.service.ComplianceInternoProcessoService;
import io.sld.riskcomplianceservice.domain.service.criteria.ComplianceInternoProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceInternoProcessoDTO;
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
 * REST controller for managing {@link ComplianceInternoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class ComplianceInternoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(ComplianceInternoProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceComplianceInternoProcesso";

    @Value("${spring.application.name}")
    private String applicationName;

    private final ComplianceInternoProcessoService complianceInternoProcessoService;

    private final ComplianceInternoProcessoRepository complianceInternoProcessoRepository;

    private final ComplianceInternoProcessoQueryService complianceInternoProcessoQueryService;

    public ComplianceInternoProcessoResource(
        ComplianceInternoProcessoService complianceInternoProcessoService,
        ComplianceInternoProcessoRepository complianceInternoProcessoRepository,
        ComplianceInternoProcessoQueryService complianceInternoProcessoQueryService
    ) {
        this.complianceInternoProcessoService = complianceInternoProcessoService;
        this.complianceInternoProcessoRepository = complianceInternoProcessoRepository;
        this.complianceInternoProcessoQueryService = complianceInternoProcessoQueryService;
    }

    /**
     * {@code POST  /compliance-interno-processos} : Create a new complianceInternoProcesso.
     *
     * @param complianceInternoProcessoDTO the complianceInternoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new complianceInternoProcessoDTO, or with status {@code 400 (Bad Request)} if the complianceInternoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compliance-interno-processos")
    public ResponseEntity<ComplianceInternoProcessoDTO> createComplianceInternoProcesso(
        @Valid @RequestBody ComplianceInternoProcessoDTO complianceInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ComplianceInternoProcesso : {}", complianceInternoProcessoDTO);
        if (complianceInternoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new complianceInternoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComplianceInternoProcessoDTO result = complianceInternoProcessoService.save(complianceInternoProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/compliance-interno-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compliance-interno-processos/:id} : Updates an existing complianceInternoProcesso.
     *
     * @param id the id of the complianceInternoProcessoDTO to save.
     * @param complianceInternoProcessoDTO the complianceInternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceInternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceInternoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the complianceInternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compliance-interno-processos/{id}")
    public ResponseEntity<ComplianceInternoProcessoDTO> updateComplianceInternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComplianceInternoProcessoDTO complianceInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ComplianceInternoProcesso : {}, {}", id, complianceInternoProcessoDTO);
        if (complianceInternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceInternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceInternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComplianceInternoProcessoDTO result = complianceInternoProcessoService.update(complianceInternoProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceInternoProcessoDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /compliance-interno-processos/:id} : Partial updates given fields of an existing complianceInternoProcesso, field will ignore if it is null
     *
     * @param id the id of the complianceInternoProcessoDTO to save.
     * @param complianceInternoProcessoDTO the complianceInternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated complianceInternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the complianceInternoProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the complianceInternoProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the complianceInternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/compliance-interno-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ComplianceInternoProcessoDTO> partialUpdateComplianceInternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComplianceInternoProcessoDTO complianceInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComplianceInternoProcesso partially : {}, {}", id, complianceInternoProcessoDTO);
        if (complianceInternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, complianceInternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!complianceInternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComplianceInternoProcessoDTO> result = complianceInternoProcessoService.partialUpdate(complianceInternoProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, complianceInternoProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /compliance-interno-processos} : get all the complianceInternoProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of complianceInternoProcessos in body.
     */
    @GetMapping("/compliance-interno-processos")
    public ResponseEntity<List<ComplianceInternoProcessoDTO>> getAllComplianceInternoProcessos(
        ComplianceInternoProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ComplianceInternoProcessos by criteria: {}", criteria);
        Page<ComplianceInternoProcessoDTO> page = complianceInternoProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compliance-interno-processos/count} : count all the complianceInternoProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/compliance-interno-processos/count")
    public ResponseEntity<Long> countComplianceInternoProcessos(ComplianceInternoProcessoCriteria criteria) {
        log.debug("REST request to count ComplianceInternoProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(complianceInternoProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /compliance-interno-processos/:id} : get the "id" complianceInternoProcesso.
     *
     * @param id the id of the complianceInternoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the complianceInternoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compliance-interno-processos/{id}")
    public ResponseEntity<ComplianceInternoProcessoDTO> getComplianceInternoProcesso(@PathVariable Long id) {
        log.debug("REST request to get ComplianceInternoProcesso : {}", id);
        Optional<ComplianceInternoProcessoDTO> complianceInternoProcessoDTO = complianceInternoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(complianceInternoProcessoDTO);
    }

    /**
     * {@code DELETE  /compliance-interno-processos/:id} : delete the "id" complianceInternoProcesso.
     *
     * @param id the id of the complianceInternoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compliance-interno-processos/{id}")
    public ResponseEntity<Void> deleteComplianceInternoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete ComplianceInternoProcesso : {}", id);
        complianceInternoProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
