package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.FornecedorExternoProcesso;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.FornecedorExternoProcessoQueryService;
import io.sld.riskcomplianceservice.domain.service.FornecedorExternoProcessoService;
import io.sld.riskcomplianceservice.domain.service.criteria.FornecedorExternoProcessoCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoProcessoDTO;
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
 * REST controller for managing {@link FornecedorExternoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class FornecedorExternoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(FornecedorExternoProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceFornecedorExternoProcesso";

    @Value("${spring.application.name}")
    private String applicationName;

    private final FornecedorExternoProcessoService fornecedorExternoProcessoService;

    private final FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository;

    private final FornecedorExternoProcessoQueryService fornecedorExternoProcessoQueryService;

    public FornecedorExternoProcessoResource(
        FornecedorExternoProcessoService fornecedorExternoProcessoService,
        FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository,
        FornecedorExternoProcessoQueryService fornecedorExternoProcessoQueryService
    ) {
        this.fornecedorExternoProcessoService = fornecedorExternoProcessoService;
        this.fornecedorExternoProcessoRepository = fornecedorExternoProcessoRepository;
        this.fornecedorExternoProcessoQueryService = fornecedorExternoProcessoQueryService;
    }

    /**
     * {@code POST  /fornecedor-externo-processos} : Create a new fornecedorExternoProcesso.
     *
     * @param fornecedorExternoProcessoDTO the fornecedorExternoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fornecedorExternoProcessoDTO, or with status {@code 400 (Bad Request)} if the fornecedorExternoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fornecedor-externo-processos")
    public ResponseEntity<FornecedorExternoProcessoDTO> createFornecedorExternoProcesso(
        @Valid @RequestBody FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FornecedorExternoProcesso : {}", fornecedorExternoProcessoDTO);
        if (fornecedorExternoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornecedorExternoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FornecedorExternoProcessoDTO result = fornecedorExternoProcessoService.save(fornecedorExternoProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/fornecedor-externo-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fornecedor-externo-processos/:id} : Updates an existing fornecedorExternoProcesso.
     *
     * @param id the id of the fornecedorExternoProcessoDTO to save.
     * @param fornecedorExternoProcessoDTO the fornecedorExternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorExternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorExternoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorExternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fornecedor-externo-processos/{id}")
    public ResponseEntity<FornecedorExternoProcessoDTO> updateFornecedorExternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FornecedorExternoProcesso : {}, {}", id, fornecedorExternoProcessoDTO);
        if (fornecedorExternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorExternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorExternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FornecedorExternoProcessoDTO result = fornecedorExternoProcessoService.update(fornecedorExternoProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorExternoProcessoDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /fornecedor-externo-processos/:id} : Partial updates given fields of an existing fornecedorExternoProcesso, field will ignore if it is null
     *
     * @param id the id of the fornecedorExternoProcessoDTO to save.
     * @param fornecedorExternoProcessoDTO the fornecedorExternoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fornecedorExternoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the fornecedorExternoProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fornecedorExternoProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fornecedorExternoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fornecedor-externo-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FornecedorExternoProcessoDTO> partialUpdateFornecedorExternoProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FornecedorExternoProcesso partially : {}, {}", id, fornecedorExternoProcessoDTO);
        if (fornecedorExternoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fornecedorExternoProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fornecedorExternoProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FornecedorExternoProcessoDTO> result = fornecedorExternoProcessoService.partialUpdate(fornecedorExternoProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fornecedorExternoProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fornecedor-externo-processos} : get all the fornecedorExternoProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fornecedorExternoProcessos in body.
     */
    @GetMapping("/fornecedor-externo-processos")
    public ResponseEntity<List<FornecedorExternoProcessoDTO>> getAllFornecedorExternoProcessos(
        FornecedorExternoProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FornecedorExternoProcessos by criteria: {}", criteria);
        Page<FornecedorExternoProcessoDTO> page = fornecedorExternoProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fornecedor-externo-processos/count} : count all the fornecedorExternoProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/fornecedor-externo-processos/count")
    public ResponseEntity<Long> countFornecedorExternoProcessos(FornecedorExternoProcessoCriteria criteria) {
        log.debug("REST request to count FornecedorExternoProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(fornecedorExternoProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fornecedor-externo-processos/:id} : get the "id" fornecedorExternoProcesso.
     *
     * @param id the id of the fornecedorExternoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fornecedorExternoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fornecedor-externo-processos/{id}")
    public ResponseEntity<FornecedorExternoProcessoDTO> getFornecedorExternoProcesso(@PathVariable Long id) {
        log.debug("REST request to get FornecedorExternoProcesso : {}", id);
        Optional<FornecedorExternoProcessoDTO> fornecedorExternoProcessoDTO = fornecedorExternoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fornecedorExternoProcessoDTO);
    }

    /**
     * {@code DELETE  /fornecedor-externo-processos/:id} : delete the "id" fornecedorExternoProcesso.
     *
     * @param id the id of the fornecedorExternoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fornecedor-externo-processos/{id}")
    public ResponseEntity<Void> deleteFornecedorExternoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete FornecedorExternoProcesso : {}", id);
        fornecedorExternoProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
