package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.FuncionarioOrganogramaRepository;
import io.sld.riskcomplianceservice.service.FuncionarioOrganogramaQueryService;
import io.sld.riskcomplianceservice.service.FuncionarioOrganogramaService;
import io.sld.riskcomplianceservice.service.criteria.FuncionarioOrganogramaCriteria;
import io.sld.riskcomplianceservice.service.dto.FuncionarioOrganogramaDTO;
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
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.FuncionarioOrganograma}.
 */
@RestController
@RequestMapping("/api")
public class FuncionarioOrganogramaResource {

    private final Logger log = LoggerFactory.getLogger(FuncionarioOrganogramaResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceFuncionarioOrganograma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuncionarioOrganogramaService funcionarioOrganogramaService;

    private final FuncionarioOrganogramaRepository funcionarioOrganogramaRepository;

    private final FuncionarioOrganogramaQueryService funcionarioOrganogramaQueryService;

    public FuncionarioOrganogramaResource(
        FuncionarioOrganogramaService funcionarioOrganogramaService,
        FuncionarioOrganogramaRepository funcionarioOrganogramaRepository,
        FuncionarioOrganogramaQueryService funcionarioOrganogramaQueryService
    ) {
        this.funcionarioOrganogramaService = funcionarioOrganogramaService;
        this.funcionarioOrganogramaRepository = funcionarioOrganogramaRepository;
        this.funcionarioOrganogramaQueryService = funcionarioOrganogramaQueryService;
    }

    /**
     * {@code POST  /funcionario-organogramas} : Create a new funcionarioOrganograma.
     *
     * @param funcionarioOrganogramaDTO the funcionarioOrganogramaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionarioOrganogramaDTO, or with status {@code 400 (Bad Request)} if the funcionarioOrganograma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcionario-organogramas")
    public ResponseEntity<FuncionarioOrganogramaDTO> createFuncionarioOrganograma(
        @Valid @RequestBody FuncionarioOrganogramaDTO funcionarioOrganogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save FuncionarioOrganograma : {}", funcionarioOrganogramaDTO);
        if (funcionarioOrganogramaDTO.getId() != null) {
            throw new BadRequestAlertException("A new funcionarioOrganograma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuncionarioOrganogramaDTO result = funcionarioOrganogramaService.save(funcionarioOrganogramaDTO);
        return ResponseEntity
            .created(new URI("/api/funcionario-organogramas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcionario-organogramas/:id} : Updates an existing funcionarioOrganograma.
     *
     * @param id the id of the funcionarioOrganogramaDTO to save.
     * @param funcionarioOrganogramaDTO the funcionarioOrganogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionarioOrganogramaDTO,
     * or with status {@code 400 (Bad Request)} if the funcionarioOrganogramaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionarioOrganogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcionario-organogramas/{id}")
    public ResponseEntity<FuncionarioOrganogramaDTO> updateFuncionarioOrganograma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuncionarioOrganogramaDTO funcionarioOrganogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FuncionarioOrganograma : {}, {}", id, funcionarioOrganogramaDTO);
        if (funcionarioOrganogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionarioOrganogramaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionarioOrganogramaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuncionarioOrganogramaDTO result = funcionarioOrganogramaService.update(funcionarioOrganogramaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionarioOrganogramaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funcionario-organogramas/:id} : Partial updates given fields of an existing funcionarioOrganograma, field will ignore if it is null
     *
     * @param id the id of the funcionarioOrganogramaDTO to save.
     * @param funcionarioOrganogramaDTO the funcionarioOrganogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionarioOrganogramaDTO,
     * or with status {@code 400 (Bad Request)} if the funcionarioOrganogramaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the funcionarioOrganogramaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionarioOrganogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funcionario-organogramas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuncionarioOrganogramaDTO> partialUpdateFuncionarioOrganograma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuncionarioOrganogramaDTO funcionarioOrganogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FuncionarioOrganograma partially : {}, {}", id, funcionarioOrganogramaDTO);
        if (funcionarioOrganogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionarioOrganogramaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionarioOrganogramaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuncionarioOrganogramaDTO> result = funcionarioOrganogramaService.partialUpdate(funcionarioOrganogramaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionarioOrganogramaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionario-organogramas} : get all the funcionarioOrganogramas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionarioOrganogramas in body.
     */
    @GetMapping("/funcionario-organogramas")
    public ResponseEntity<List<FuncionarioOrganogramaDTO>> getAllFuncionarioOrganogramas(
        FuncionarioOrganogramaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get FuncionarioOrganogramas by criteria: {}", criteria);
        Page<FuncionarioOrganogramaDTO> page = funcionarioOrganogramaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funcionario-organogramas/count} : count all the funcionarioOrganogramas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/funcionario-organogramas/count")
    public ResponseEntity<Long> countFuncionarioOrganogramas(FuncionarioOrganogramaCriteria criteria) {
        log.debug("REST request to count FuncionarioOrganogramas by criteria: {}", criteria);
        return ResponseEntity.ok().body(funcionarioOrganogramaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funcionario-organogramas/:id} : get the "id" funcionarioOrganograma.
     *
     * @param id the id of the funcionarioOrganogramaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionarioOrganogramaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcionario-organogramas/{id}")
    public ResponseEntity<FuncionarioOrganogramaDTO> getFuncionarioOrganograma(@PathVariable Long id) {
        log.debug("REST request to get FuncionarioOrganograma : {}", id);
        Optional<FuncionarioOrganogramaDTO> funcionarioOrganogramaDTO = funcionarioOrganogramaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionarioOrganogramaDTO);
    }

    /**
     * {@code DELETE  /funcionario-organogramas/:id} : delete the "id" funcionarioOrganograma.
     *
     * @param id the id of the funcionarioOrganogramaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcionario-organogramas/{id}")
    public ResponseEntity<Void> deleteFuncionarioOrganograma(@PathVariable Long id) {
        log.debug("REST request to delete FuncionarioOrganograma : {}", id);
        funcionarioOrganogramaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
