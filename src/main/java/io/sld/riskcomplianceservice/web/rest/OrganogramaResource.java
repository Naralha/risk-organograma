package io.sld.riskcomplianceservice.web.rest;

import io.sld.riskcomplianceservice.repository.OrganogramaRepository;
import io.sld.riskcomplianceservice.service.OrganogramaQueryService;
import io.sld.riskcomplianceservice.service.OrganogramaService;
import io.sld.riskcomplianceservice.service.criteria.OrganogramaCriteria;
import io.sld.riskcomplianceservice.service.dto.OrganogramaDTO;
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
 * REST controller for managing {@link io.sld.riskcomplianceservice.domain.Organograma}.
 */
@RestController
@RequestMapping("/api")
public class OrganogramaResource {

    private final Logger log = LoggerFactory.getLogger(OrganogramaResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceOrganograma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganogramaService organogramaService;

    private final OrganogramaRepository organogramaRepository;

    private final OrganogramaQueryService organogramaQueryService;

    public OrganogramaResource(
        OrganogramaService organogramaService,
        OrganogramaRepository organogramaRepository,
        OrganogramaQueryService organogramaQueryService
    ) {
        this.organogramaService = organogramaService;
        this.organogramaRepository = organogramaRepository;
        this.organogramaQueryService = organogramaQueryService;
    }

    /**
     * {@code POST  /organogramas} : Create a new organograma.
     *
     * @param organogramaDTO the organogramaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organogramaDTO, or with status {@code 400 (Bad Request)} if the organograma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organogramas")
    public ResponseEntity<OrganogramaDTO> createOrganograma(@Valid @RequestBody OrganogramaDTO organogramaDTO) throws URISyntaxException {
        log.debug("REST request to save Organograma : {}", organogramaDTO);
        if (organogramaDTO.getId() != null) {
            throw new BadRequestAlertException("A new organograma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganogramaDTO result = organogramaService.save(organogramaDTO);
        return ResponseEntity
            .created(new URI("/api/organogramas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organogramas/:id} : Updates an existing organograma.
     *
     * @param id the id of the organogramaDTO to save.
     * @param organogramaDTO the organogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organogramaDTO,
     * or with status {@code 400 (Bad Request)} if the organogramaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organogramas/{id}")
    public ResponseEntity<OrganogramaDTO> updateOrganograma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrganogramaDTO organogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Organograma : {}, {}", id, organogramaDTO);
        if (organogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organogramaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organogramaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrganogramaDTO result = organogramaService.update(organogramaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organogramaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /organogramas/:id} : Partial updates given fields of an existing organograma, field will ignore if it is null
     *
     * @param id the id of the organogramaDTO to save.
     * @param organogramaDTO the organogramaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organogramaDTO,
     * or with status {@code 400 (Bad Request)} if the organogramaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the organogramaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the organogramaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/organogramas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrganogramaDTO> partialUpdateOrganograma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrganogramaDTO organogramaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Organograma partially : {}, {}", id, organogramaDTO);
        if (organogramaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, organogramaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!organogramaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrganogramaDTO> result = organogramaService.partialUpdate(organogramaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organogramaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /organogramas} : get all the organogramas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organogramas in body.
     */
    @GetMapping("/organogramas")
    public ResponseEntity<List<OrganogramaDTO>> getAllOrganogramas(
        OrganogramaCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Organogramas by criteria: {}", criteria);
        Page<OrganogramaDTO> page = organogramaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organogramas/count} : count all the organogramas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/organogramas/count")
    public ResponseEntity<Long> countOrganogramas(OrganogramaCriteria criteria) {
        log.debug("REST request to count Organogramas by criteria: {}", criteria);
        return ResponseEntity.ok().body(organogramaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /organogramas/:id} : get the "id" organograma.
     *
     * @param id the id of the organogramaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organogramaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organogramas/{id}")
    public ResponseEntity<OrganogramaDTO> getOrganograma(@PathVariable Long id) {
        log.debug("REST request to get Organograma : {}", id);
        Optional<OrganogramaDTO> organogramaDTO = organogramaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organogramaDTO);
    }

    /**
     * {@code DELETE  /organogramas/:id} : delete the "id" organograma.
     *
     * @param id the id of the organogramaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organogramas/{id}")
    public ResponseEntity<Void> deleteOrganograma(@PathVariable Long id) {
        log.debug("REST request to delete Organograma : {}", id);
        organogramaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
