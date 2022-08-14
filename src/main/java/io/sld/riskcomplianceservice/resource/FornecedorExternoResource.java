package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoRepository;
import io.sld.riskcomplianceservice.domain.service.FornecedorExternoQueryService;
import io.sld.riskcomplianceservice.domain.service.FornecedorExternoService;
import io.sld.riskcomplianceservice.domain.service.criteria.FornecedorExternoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoDTO;
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
 * REST controller for managing {@link FornecedorExterno}.
 */
@RestController
@RequestMapping("/api")
public class FornecedorExternoResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorExternoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceFornecedorExterno";


    @Value("${spring.application.name}")
    private String applicationName;

    private final FornecedorExternoService fornecedorExternoService;

    private final FornecedorExternoRepository fornecedorExternoRepository;

    private final FornecedorExternoQueryService fornecedorExternoQueryService;

    public FornecedorExternoResource(
        FornecedorExternoService fornecedorExternoService,
        FornecedorExternoRepository fornecedorExternoRepository,
        FornecedorExternoQueryService fornecedorExternoQueryService
    ) {
        this.fornecedorExternoService = fornecedorExternoService;
        this.fornecedorExternoRepository = fornecedorExternoRepository;
        this.fornecedorExternoQueryService = fornecedorExternoQueryService;
    }

    /**
     * {@code POST  /fornecedor-externos} : Create a new fornecedorExterno.
     *
     * @param fornecedorExternoDTO the fornecedorExternoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedorExternoDTO, or with status {@code 400 (Bad Request)} if the fornecedorExterno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fornecedor-externos")
    public ResponseEntity<FornecedorExternoDTO> createFornecedorExterno(@Valid @RequestBody FornecedorExternoDTO fornecedorExternoDTO)
        throws URISyntaxException {
        log.debug("REST request to save FornecedorExterno : {}", fornecedorExternoDTO);
        if (fornecedorExternoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedorExterno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FornecedorExternoDTO result = fornecedorExternoService.save(fornecedorExternoDTO);
        return ResponseEntity
            .created(new URI("/api/fornecedor-externos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fornecedor-externos/:id} : Updates an existing fornecedorExterno.
     *
     * @param id the id of the fornecedorExternoDTO to save.
     * @param fornecedorExternoDTO the fornecedorExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorExternoDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorExternoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fornecedor-externos/{id}")
    public ResponseEntity<FornecedorExternoDTO> updateFornecedorExterno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FornecedorExternoDTO fornecedorExternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FornecedorExterno : {}, {}", id, fornecedorExternoDTO);
        if (fornecedorExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorExternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorExternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FornecedorExternoDTO result = fornecedorExternoService.update(fornecedorExternoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorExternoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fornecedor-externos/:id} : Partial updates given fields of an existing fornecedorExterno, field will ignore if it is null
     *
     * @param id the id of the fornecedorExternoDTO to save.
     * @param fornecedorExternoDTO the fornecedorExternoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorExternoDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorExternoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedorExternoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorExternoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fornecedor-externos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FornecedorExternoDTO> partialUpdateFornecedorExterno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FornecedorExternoDTO fornecedorExternoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FornecedorExterno partially : {}, {}", id, fornecedorExternoDTO);
        if (fornecedorExternoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorExternoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorExternoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FornecedorExternoDTO> result = fornecedorExternoService.partialUpdate(fornecedorExternoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorExternoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fornecedor-externos} : get all the fornecedorExternos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedorExternos in body.
     */
    @GetMapping("/fornecedor-externos")
    public ResponseEntity<List<FornecedorExternoDTO>> getAllFornecedorExternos(
        FornecedorExternoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FornecedorExternos by criteria: {}", criteria);
        Page<FornecedorExternoDTO> page = fornecedorExternoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fornecedor-externos/count} : count all the fornecedorExternos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fornecedor-externos/count")
    public ResponseEntity<Long> countFornecedorExternos(FornecedorExternoCriteria criteria) {
        log.debug("REST request to count FornecedorExternos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fornecedorExternoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fornecedor-externos/:id} : get the "id" fornecedorExterno.
     *
     * @param id the id of the fornecedorExternoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedorExternoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fornecedor-externos/{id}")
    public ResponseEntity<FornecedorExternoDTO> getFornecedorExterno(@PathVariable Long id) {
        log.debug("REST request to get FornecedorExterno : {}", id);
        Optional<FornecedorExternoDTO> fornecedorExternoDTO = fornecedorExternoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorExternoDTO);
    }

    /**
     * {@code DELETE  /fornecedor-externos/:id} : delete the "id" fornecedorExterno.
     *
     * @param id the id of the fornecedorExternoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fornecedor-externos/{id}")
    public ResponseEntity<Void> deleteFornecedorExterno(@PathVariable Long id) {
        log.debug("REST request to delete FornecedorExterno : {}", id);
        fornecedorExternoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
