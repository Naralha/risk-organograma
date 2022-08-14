package io.sld.riskcomplianceservice.resource;

import io.sld.riskcomplianceservice.domain.entity.Funcionario;
import io.sld.riskcomplianceservice.domain.repository.FuncionarioRepository;
import io.sld.riskcomplianceservice.domain.service.FuncionarioQueryService;
import io.sld.riskcomplianceservice.domain.service.FuncionarioService;
import io.sld.riskcomplianceservice.domain.service.criteria.FuncionarioCriteria;
import io.sld.riskcomplianceservice.domain.service.dto.FuncionarioDTO;
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
 * REST controller for managing {@link Funcionario}.
 */
@RestController
@RequestMapping("/api")
public class FuncionarioResource {

    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private static final String ENTITY_NAME = "riskcomplianceserviceFuncionario";

    @Value("${spring.application.name}")
    private String applicationName;

    private final FuncionarioService funcionarioService;

    private final FuncionarioRepository funcionarioRepository;

    private final FuncionarioQueryService funcionarioQueryService;

    public FuncionarioResource(
        FuncionarioService funcionarioService,
        FuncionarioRepository funcionarioRepository,
        FuncionarioQueryService funcionarioQueryService
    ) {
        this.funcionarioService = funcionarioService;
        this.funcionarioRepository = funcionarioRepository;
        this.funcionarioQueryService = funcionarioQueryService;
    }

    /**
     * {@code POST  /funcionarios} : Create a new funcionario.
     *
     * @param funcionarioDTO the funcionarioDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new funcionarioDTO, or with status {@code 400 (Bad Request)} if the funcionario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/funcionarios")
    public ResponseEntity<FuncionarioDTO> createFuncionario(@Valid @RequestBody FuncionarioDTO funcionarioDTO) throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionarioDTO);
        if (funcionarioDTO.getId() != null) {
            throw new BadRequestAlertException("A new funcionario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FuncionarioDTO result = funcionarioService.save(funcionarioDTO);
        return ResponseEntity
            .created(new URI("/api/funcionarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /funcionarios/:id} : Updates an existing funcionario.
     *
     * @param id the id of the funcionarioDTO to save.
     * @param funcionarioDTO the funcionarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionarioDTO,
     * or with status {@code 400 (Bad Request)} if the funcionarioDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the funcionarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioDTO> updateFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FuncionarioDTO funcionarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}, {}", id, funcionarioDTO);
        if (funcionarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FuncionarioDTO result = funcionarioService.update(funcionarioDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionarioDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /funcionarios/:id} : Partial updates given fields of an existing funcionario, field will ignore if it is null
     *
     * @param id the id of the funcionarioDTO to save.
     * @param funcionarioDTO the funcionarioDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated funcionarioDTO,
     * or with status {@code 400 (Bad Request)} if the funcionarioDTO is not valid,
     * or with status {@code 404 (Not Found)} if the funcionarioDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the funcionarioDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/funcionarios/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuncionarioDTO> partialUpdateFuncionario(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FuncionarioDTO funcionarioDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Funcionario partially : {}, {}", id, funcionarioDTO);
        if (funcionarioDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, funcionarioDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!funcionarioRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuncionarioDTO> result = funcionarioService.partialUpdate(funcionarioDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, funcionarioDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /funcionarios} : get all the funcionarios.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of funcionarios in body.
     */
    @GetMapping("/funcionarios")
    public ResponseEntity<List<FuncionarioDTO>> getAllFuncionarios(
        FuncionarioCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Funcionarios by criteria: {}", criteria);
        Page<FuncionarioDTO> page = funcionarioQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /funcionarios/count} : count all the funcionarios.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/funcionarios/count")
    public ResponseEntity<Long> countFuncionarios(FuncionarioCriteria criteria) {
        log.debug("REST request to count Funcionarios by criteria: {}", criteria);
        return ResponseEntity.ok().body(funcionarioQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /funcionarios/:id} : get the "id" funcionario.
     *
     * @param id the id of the funcionarioDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the funcionarioDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioDTO> getFuncionario(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        Optional<FuncionarioDTO> funcionarioDTO = funcionarioService.findOne(id);
        return ResponseUtil.wrapOrNotFound(funcionarioDTO);
    }

    /**
     * {@code DELETE  /funcionarios/:id} : delete the "id" funcionario.
     *
     * @param id the id of the funcionarioDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/funcionarios/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);
        funcionarioService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
