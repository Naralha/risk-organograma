package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.MacroProcessoRepository;
import io.sld.riskcomplianceservice.service.MacroProcessoQueryService;
import io.sld.riskcomplianceservice.service.MacroProcessoService;
import io.sld.riskcomplianceservice.service.criteria.MacroProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.MacroProcessoDTO;
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
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.MacroProcesso}.
 */
@RestController
@RequestMapping("/api")
public class MacroProcessoResource {

    private final Logger log = LoggerFactory.getLogger(MacroProcessoResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceMacroProcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MacroProcessoService macroProcessoService;

    private final MacroProcessoRepository macroProcessoRepository;

    private final MacroProcessoQueryService macroProcessoQueryService;

    public MacroProcessoResource(
        MacroProcessoService macroProcessoService,
        MacroProcessoRepository macroProcessoRepository,
        MacroProcessoQueryService macroProcessoQueryService
    ) {
        this.macroProcessoService = macroProcessoService;
        this.macroProcessoRepository = macroProcessoRepository;
        this.macroProcessoQueryService = macroProcessoQueryService;
    }

    /**
     * {@code POST  /macro-processos} : Create a new macroProcesso.
     *
     * @param macroProcessoDTO the macroProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new macroProcessoDTO, or with status {@code 400 (Bad Request)} if the macroProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/macro-processos")
    public ResponseEntity<MacroProcessoDTO> createMacroProcesso(@Valid @RequestBody MacroProcessoDTO macroProcessoDTO)
        throws URISyntaxException {
        log.debug("REST request to save MacroProcesso : {}", macroProcessoDTO);
        if (macroProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new macroProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MacroProcessoDTO result = macroProcessoService.save(macroProcessoDTO);
        return ResponseEntity
            .created(new URI("/api/macro-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /macro-processos/:id} : Updates an existing macroProcesso.
     *
     * @param id the id of the macroProcessoDTO to save.
     * @param macroProcessoDTO the macroProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated macroProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the macroProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the macroProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/macro-processos/{id}")
    public ResponseEntity<MacroProcessoDTO> updateMacroProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MacroProcessoDTO macroProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MacroProcesso : {}, {}", id, macroProcessoDTO);
        if (macroProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, macroProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!macroProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MacroProcessoDTO result = macroProcessoService.update(macroProcessoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, macroProcessoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /macro-processos/:id} : Partial updates given fields of an existing macroProcesso, field will ignore if it is null
     *
     * @param id the id of the macroProcessoDTO to save.
     * @param macroProcessoDTO the macroProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated macroProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the macroProcessoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the macroProcessoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the macroProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/macro-processos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MacroProcessoDTO> partialUpdateMacroProcesso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MacroProcessoDTO macroProcessoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MacroProcesso partially : {}, {}", id, macroProcessoDTO);
        if (macroProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, macroProcessoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!macroProcessoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MacroProcessoDTO> result = macroProcessoService.partialUpdate(macroProcessoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, macroProcessoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /macro-processos} : get all the macroProcessos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of macroProcessos in body.
     */
    @GetMapping("/macro-processos")
    public ResponseEntity<List<MacroProcessoDTO>> getAllMacroProcessos(
        MacroProcessoCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MacroProcessos by criteria: {}", criteria);
        Page<MacroProcessoDTO> page = macroProcessoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /macro-processos/count} : count all the macroProcessos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/macro-processos/count")
    public ResponseEntity<Long> countMacroProcessos(MacroProcessoCriteria criteria) {
        log.debug("REST request to count MacroProcessos by criteria: {}", criteria);
        return ResponseEntity.ok().body(macroProcessoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /macro-processos/:id} : get the "id" macroProcesso.
     *
     * @param id the id of the macroProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the macroProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/macro-processos/{id}")
    public ResponseEntity<MacroProcessoDTO> getMacroProcesso(@PathVariable Long id) {
        log.debug("REST request to get MacroProcesso : {}", id);
        Optional<MacroProcessoDTO> macroProcessoDTO = macroProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(macroProcessoDTO);
    }

    /**
     * {@code DELETE  /macro-processos/:id} : delete the "id" macroProcesso.
     *
     * @param id the id of the macroProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/macro-processos/{id}")
    public ResponseEntity<Void> deleteMacroProcesso(@PathVariable Long id) {
        log.debug("REST request to delete MacroProcesso : {}", id);
        macroProcessoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
