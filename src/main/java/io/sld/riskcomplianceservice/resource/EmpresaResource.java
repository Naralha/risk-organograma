package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.repository.EmpresaRepository;
import io.sld.riskcomplianceservice.domain.service.EmpresaQueryService;
import io.sld.riskcomplianceservice.domain.service.EmpresaService;
import io.sld.riskcomplianceservice.domain.service.criteria.EmpresaCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.EmpresaDTO;
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
 * REST controller for managing {@link Empresa}.
 */
@RestController
@RequestMapping("/api")
public class EmpresaResource {

    private final Logger log = LoggerFactory.getLogger(EmpresaResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceEmpresa";

    @Value("${spring.application.name}")
    private String applicationName;

    private final EmpresaService empresaService;

    private final EmpresaRepository empresaRepository;

    private final EmpresaQueryService empresaQueryService;

    public EmpresaResource(EmpresaService empresaService, EmpresaRepository empresaRepository, EmpresaQueryService empresaQueryService) {
        this.empresaService = empresaService;
        this.empresaRepository = empresaRepository;
        this.empresaQueryService = empresaQueryService;
    }

    /**
     * {@code POST  /empresas} : Create a new empresa.
     *
     * @param empresaDTO the empresaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new empresaDTO, or with status {@code 400 (Bad Request)} if the empresa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/empresas")
    public ResponseEntity<EmpresaDTO> createEmpresa(@Valid @RequestBody EmpresaDTO empresaDTO) throws URISyntaxException {
        log.debug("REST request to save Empresa : {}", empresaDTO);
        if (empresaDTO.getId() != null) {
            throw new BadRequestAlertException("A new empresa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmpresaDTO result = empresaService.save(empresaDTO);
        return ResponseEntity
            .created(new URI("/api/empresas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /empresas/:id} : Updates an existing empresa.
     *
     * @param id the id of the empresaDTO to save.
     * @param empresaDTO the empresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaDTO,
     * or with status {@code 400 (Bad Request)} if the empresaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the empresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/empresas/{id}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmpresaDTO empresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Empresa : {}, {}", id, empresaDTO);
        if (empresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmpresaDTO result = empresaService.update(empresaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /empresas/:id} : Partial updates given fields of an existing empresa, field will ignore if it is null
     *
     * @param id the id of the empresaDTO to save.
     * @param empresaDTO the empresaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated empresaDTO,
     * or with status {@code 400 (Bad Request)} if the empresaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the empresaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the empresaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/empresas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmpresaDTO> partialUpdateEmpresa(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmpresaDTO empresaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Empresa partially : {}, {}", id, empresaDTO);
        if (empresaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, empresaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!empresaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmpresaDTO> result = empresaService.partialUpdate(empresaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, empresaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /empresas} : get all the empresas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of empresas in body.
     */
    @GetMapping("/empresas")
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas(
        EmpresaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Empresas by criteria: {}", criteria);
        Page<EmpresaDTO> page = empresaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /empresas/count} : count all the empresas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/empresas/count")
    public ResponseEntity<Long> countEmpresas(EmpresaCriteria criteria) {
        log.debug("REST request to count Empresas by criteria: {}", criteria);
        return ResponseEntity.ok().body(empresaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /empresas/:id} : get the "id" empresa.
     *
     * @param id the id of the empresaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the empresaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/empresas/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresa(@PathVariable Long id) {
        log.debug("REST request to get Empresa : {}", id);
        Optional<EmpresaDTO> empresaDTO = empresaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(empresaDTO);
    }

    /**
     * {@code DELETE  /empresas/:id} : delete the "id" empresa.
     *
     * @param id the id of the empresaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/empresas/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Long id) {
        log.debug("REST request to delete Empresa : {}", id);
        empresaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
