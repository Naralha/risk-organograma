package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.domain.repository.MacroProcessoOrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.MacroProcessoOrganogramaQueryService;
import io.sld.riskcomplianceservice.domain.service.MacroProcessoOrganogramaService;
import io.sld.riskcomplianceservice.domain.service.criteria.MacroProcessoOrganogramaCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.MacroProcessoOrganogramaDTO;
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
 * REST controller for managing {@link MacroProcessoOrganograma}.
 */
@RestController
@RequestMapping("/api")
public class MacroProcessoOrganogramaResource {

    private final Logger log = LoggerFactory.getLogger(MacroProcessoOrganogramaResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceMacroProcessoOrganograma";

    @Value("${spring.application.name}")
    private String applicationName;

    private final MacroProcessoOrganogramaService macroProcessoOrganogramaService;

    private final MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository;

    private final MacroProcessoOrganogramaQueryService macroProcessoOrganogramaQueryService;

    public MacroProcessoOrganogramaResource(
        MacroProcessoOrganogramaService macroProcessoOrganogramaService,
        MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository,
        MacroProcessoOrganogramaQueryService macroProcessoOrganogramaQueryService
    ) {
        this.macroProcessoOrganogramaService = macroProcessoOrganogramaService;
        this.macroProcessoOrganogramaRepository = macroProcessoOrganogramaRepository;
        this.macroProcessoOrganogramaQueryService = macroProcessoOrganogramaQueryService;
    }

    /**
     * {@code POST  /macro-processo-organogramas} : Create a new macroProcessoOrganograma.
     *
     * @param macroProcessoOrganogramaDTO the macroProcessoOrganogramaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new macroProcessoOrganogramaDTO, or with status {@code 400 (Bad Request)} if the macroProcessoOrganograma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/macro-processo-organogramas")
    public ResponseEntity<MacroProcessoOrganogramaDTO> createMacroProcessoOrganograma(
        @Valid @RequestBody MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save MacroProcessoOrganograma : {}", macroProcessoOrganogramaDTO);
        if (macroProcessoOrganogramaDTO.getId() != null) {
            throw new BadRequestAlertException("A new macroProcessoOrganograma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MacroProcessoOrganogramaDTO result = macroProcessoOrganogramaService.save(macroProcessoOrganogramaDTO);
        return ResponseEntity
            .created(new URI("/api/macro-processo-organogramas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /macro-processo-organogramas/:id} : Updates an existing macroProcessoOrganograma.
     *
     * @param id the id of the macroProcessoOrganogramaDTO to save.
     * @param macroProcessoOrganogramaDTO the macroProcessoOrganogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated macroProcessoOrganogramaDTO,
     * or with status {@code 400 (Bad Request)} if the macroProcessoOrganogramaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the macroProcessoOrganogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/macro-processo-organogramas/{id}")
    public ResponseEntity<MacroProcessoOrganogramaDTO> updateMacroProcessoOrganograma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MacroProcessoOrganograma : {}, {}", id, macroProcessoOrganogramaDTO);
        if (macroProcessoOrganogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, macroProcessoOrganogramaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!macroProcessoOrganogramaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MacroProcessoOrganogramaDTO result = macroProcessoOrganogramaService.update(macroProcessoOrganogramaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, macroProcessoOrganogramaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /macro-processo-organogramas/:id} : Partial updates given fields of an existing macroProcessoOrganograma, field will ignore if it is null
     *
     * @param id the id of the macroProcessoOrganogramaDTO to save.
     * @param macroProcessoOrganogramaDTO the macroProcessoOrganogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated macroProcessoOrganogramaDTO,
     * or with status {@code 400 (Bad Request)} if the macroProcessoOrganogramaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the macroProcessoOrganogramaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the macroProcessoOrganogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/macro-processo-organogramas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MacroProcessoOrganogramaDTO> partialUpdateMacroProcessoOrganograma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MacroProcessoOrganograma partially : {}, {}", id, macroProcessoOrganogramaDTO);
        if (macroProcessoOrganogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, macroProcessoOrganogramaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!macroProcessoOrganogramaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MacroProcessoOrganogramaDTO> result = macroProcessoOrganogramaService.partialUpdate(macroProcessoOrganogramaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, macroProcessoOrganogramaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /macro-processo-organogramas} : get all the macroProcessoOrganogramas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of macroProcessoOrganogramas in body.
     */
    @GetMapping("/macro-processo-organogramas")
    public ResponseEntity<List<MacroProcessoOrganogramaDTO>> getAllMacroProcessoOrganogramas(
        MacroProcessoOrganogramaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MacroProcessoOrganogramas by criteria: {}", criteria);
        Page<MacroProcessoOrganogramaDTO> page = macroProcessoOrganogramaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /macro-processo-organogramas/count} : count all the macroProcessoOrganogramas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/macro-processo-organogramas/count")
    public ResponseEntity<Long> countMacroProcessoOrganogramas(MacroProcessoOrganogramaCriteria criteria) {
        log.debug("REST request to count MacroProcessoOrganogramas by criteria: {}", criteria);
        return ResponseEntity.ok().body(macroProcessoOrganogramaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /macro-processo-organogramas/:id} : get the "id" macroProcessoOrganograma.
     *
     * @param id the id of the macroProcessoOrganogramaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the macroProcessoOrganogramaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/macro-processo-organogramas/{id}")
    public ResponseEntity<MacroProcessoOrganogramaDTO> getMacroProcessoOrganograma(@PathVariable Long id) {
        log.debug("REST request to get MacroProcessoOrganograma : {}", id);
        Optional<MacroProcessoOrganogramaDTO> macroProcessoOrganogramaDTO = macroProcessoOrganogramaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(macroProcessoOrganogramaDTO);
    }

    /**
     * {@code DELETE  /macro-processo-organogramas/:id} : delete the "id" macroProcessoOrganograma.
     *
     * @param id the id of the macroProcessoOrganogramaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/macro-processo-organogramas/{id}")
    public ResponseEntity<Void> deleteMacroProcessoOrganograma(@PathVariable Long id) {
        log.debug("REST request to delete MacroProcessoOrganograma : {}", id);
        macroProcessoOrganogramaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
