package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.FornecedorInternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.FornecedorInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.FornecedorInternoProcessoQueryService;
import io.sld.riskcomplianceservice.domain.service.FornecedorInternoProcessoService;
import io.sld.riskcomplianceservice.domain.service.criteria.FornecedorInternoProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorInternoProcessoDTO;
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
 * REST controller for managing {@link FornecedorInternoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class FornecedorInternoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorInternoProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceFornecedorInternoProcesso";

    @Value("${spring.application.name}")
    private String applicationName;

    private final FornecedorInternoProcessoService fornecedorInternoProcessoService;

    private final FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository;

    private final FornecedorInternoProcessoQueryService fornecedorInternoProcessoQueryService;

    public FornecedorInternoProcessoResource(
        FornecedorInternoProcessoService fornecedorInternoProcessoService,
        FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository,
        FornecedorInternoProcessoQueryService fornecedorInternoProcessoQueryService
    ) {
        this.fornecedorInternoProcessoService = fornecedorInternoProcessoService;
        this.fornecedorInternoProcessoRepository = fornecedorInternoProcessoRepository;
        this.fornecedorInternoProcessoQueryService = fornecedorInternoProcessoQueryService;
    }

    /**
     * {@code POST  /fornecedor-interno-processos} : Create a new fornecedorInternoProcesso.
     *
     * @param fornecedorInternoProcessoDTO the fornecedorInternoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedorInternoProcessoDTO, or with status {@code 400 (Bad Request)} if the fornecedorInternoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fornecedor-interno-processos")
    public ResponseEntity<FornecedorInternoProcessoDTO> createFornecedorInternoProcesso(
        @Valid @RequestBody FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FornecedorInternoProcesso : {}", fornecedorInternoProcessoDTO);
        if (fornecedorInternoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedorInternoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FornecedorInternoProcessoDTO result = fornecedorInternoProcessoService.save(fornecedorInternoProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/fornecedor-interno-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fornecedor-interno-processos/:id} : Updates an existing fornecedorInternoProcesso.
     *
     * @param id the id of the fornecedorInternoProcessoDTO to save.
     * @param fornecedorInternoProcessoDTO the fornecedorInternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorInternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorInternoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorInternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fornecedor-interno-processos/{id}")
    public ResponseEntity<FornecedorInternoProcessoDTO> updateFornecedorInternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FornecedorInternoProcesso : {}, {}", id, fornecedorInternoProcessoDTO);
        if (fornecedorInternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorInternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorInternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FornecedorInternoProcessoDTO result = fornecedorInternoProcessoService.update(fornecedorInternoProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorInternoProcessoDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /fornecedor-interno-processos/:id} : Partial updates given fields of an existing fornecedorInternoProcesso, field will ignore if it is null
     *
     * @param id the id of the fornecedorInternoProcessoDTO to save.
     * @param fornecedorInternoProcessoDTO the fornecedorInternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorInternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorInternoProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedorInternoProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorInternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fornecedor-interno-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FornecedorInternoProcessoDTO> partialUpdateFornecedorInternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FornecedorInternoProcesso partially : {}, {}", id, fornecedorInternoProcessoDTO);
        if (fornecedorInternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorInternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorInternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FornecedorInternoProcessoDTO> result = fornecedorInternoProcessoService.partialUpdate(fornecedorInternoProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorInternoProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fornecedor-interno-processos} : get all the fornecedorInternoProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedorInternoProcessos in body.
     */
    @GetMapping("/fornecedor-interno-processos")
    public ResponseEntity<List<FornecedorInternoProcessoDTO>> getAllFornecedorInternoProcessos(
        FornecedorInternoProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FornecedorInternoProcessos by criteria: {}", criteria);
        Page<FornecedorInternoProcessoDTO> page = fornecedorInternoProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fornecedor-interno-processos/count} : count all the fornecedorInternoProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fornecedor-interno-processos/count")
    public ResponseEntity<Long> countFornecedorInternoProcessos(FornecedorInternoProcessoCriteria criteria) {
        log.debug("REST request to count FornecedorInternoProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fornecedorInternoProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fornecedor-interno-processos/:id} : get the "id" fornecedorInternoProcesso.
     *
     * @param id the id of the fornecedorInternoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedorInternoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fornecedor-interno-processos/{id}")
    public ResponseEntity<FornecedorInternoProcessoDTO> getFornecedorInternoProcesso(@PathVariable Long id) {
        log.debug("REST request to get FornecedorInternoProcesso : {}", id);
        Optional<FornecedorInternoProcessoDTO> fornecedorInternoProcessoDTO = fornecedorInternoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorInternoProcessoDTO);
    }

    /**
     * {@code DELETE  /fornecedor-interno-processos/:id} : delete the "id" fornecedorInternoProcesso.
     *
     * @param id the id of the fornecedorInternoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fornecedor-interno-processos/{id}")
    public ResponseEntity<Void> deleteFornecedorInternoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete FornecedorInternoProcesso : {}", id);
        fornecedorInternoProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
