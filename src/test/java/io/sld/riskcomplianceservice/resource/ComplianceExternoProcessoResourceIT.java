package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.TestUtil;
import io.sld.riskcomplianceservice.domain.entity.ComplianceExterno;
import io.sld.riskcomplianceservice.domain.entity.ComplianceExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.ComplianceExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceExternoProcessoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ComplianceExternoProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
//@WithMockUser
class ComplianceExternoProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_COMPLIANCE_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/compliance-externo-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComplianceExternoProcessoRepository complianceExternoProcessoRepository;

    @Autowired
    private ComplianceExternoProcessoMapper complianceExternoProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplianceExternoProcessoMockMvc;

    private ComplianceExternoProcesso complianceExternoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceExternoProcesso createEntity(EntityManager em) {
        ComplianceExternoProcesso complianceExternoProcesso = new ComplianceExternoProcesso()
            .idnVarComplianceExterno(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO)
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return complianceExternoProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceExternoProcesso createUpdatedEntity(EntityManager em) {
        ComplianceExternoProcesso complianceExternoProcesso = new ComplianceExternoProcesso()
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return complianceExternoProcesso;
    }

    @BeforeEach
    public void initTest() {
        complianceExternoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeCreate = complianceExternoProcessoRepository.findAll().size();
        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);
        restComplianceExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        ComplianceExternoProcesso testComplianceExternoProcesso = complianceExternoProcessoList.get(
            complianceExternoProcessoList.size() - 1
        );
        assertThat(testComplianceExternoProcesso.getIdnVarComplianceExterno()).isEqualTo(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testComplianceExternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createComplianceExternoProcessoWithExistingId() throws Exception {
        // Create the ComplianceExternoProcesso with an existing ID
        complianceExternoProcesso.setId(1L);
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        int databaseSizeBeforeCreate = complianceExternoProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplianceExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarComplianceExternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoProcessoRepository.findAll().size();
        // set the field null
        complianceExternoProcesso.setIdnVarComplianceExterno(null);

        // Create the ComplianceExternoProcesso, which fails.
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        restComplianceExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoProcessoRepository.findAll().size();
        // set the field null
        complianceExternoProcesso.setIdnVarProcesso(null);

        // Create the ComplianceExternoProcesso, which fails.
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        restComplianceExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoProcessoRepository.findAll().size();
        // set the field null
        complianceExternoProcesso.setIdnvarUsuario(null);

        // Create the ComplianceExternoProcesso, which fails.
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        restComplianceExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessos() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList
        restComplianceExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceExternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianceExterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getComplianceExternoProcesso() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get the complianceExternoProcesso
        restComplianceExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, complianceExternoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complianceExternoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarComplianceExterno").value(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getComplianceExternoProcessosByIdFiltering() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        Long id = complianceExternoProcesso.getId();

        defaultComplianceExternoProcessoShouldBeFound("id.equals=" + id);
        defaultComplianceExternoProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultComplianceExternoProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComplianceExternoProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultComplianceExternoProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComplianceExternoProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarComplianceExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno equals to DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldBeFound("idnVarComplianceExterno.equals=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno equals to UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarComplianceExterno.equals=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarComplianceExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno not equals to DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarComplianceExterno.notEquals=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno not equals to UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldBeFound("idnVarComplianceExterno.notEquals=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarComplianceExternoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno in DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO or UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldBeFound(
            "idnVarComplianceExterno.in=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO + "," + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        );

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno equals to UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarComplianceExterno.in=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarComplianceExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno is not null
        defaultComplianceExternoProcessoShouldBeFound("idnVarComplianceExterno.specified=true");

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno is null
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarComplianceExterno.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarComplianceExternoContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno contains DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldBeFound("idnVarComplianceExterno.contains=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno contains UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarComplianceExterno.contains=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarComplianceExternoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno does not contain DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarComplianceExterno.doesNotContain=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoProcessoList where idnVarComplianceExterno does not contain UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoProcessoShouldBeFound("idnVarComplianceExterno.doesNotContain=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceExternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceExternoProcessoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the complianceExternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarProcesso is not null
        defaultComplianceExternoProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the complianceExternoProcessoList where idnVarProcesso is null
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceExternoProcessoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceExternoProcessoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultComplianceExternoProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoProcessoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the complianceExternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnvarUsuario is not null
        defaultComplianceExternoProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the complianceExternoProcessoList where idnvarUsuario is null
        defaultComplianceExternoProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoProcessoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        // Get all the complianceExternoProcessoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoProcessoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByComplianceExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);
        ComplianceExterno complianceExterno;
        if (TestUtil.findAll(em, ComplianceExterno.class).isEmpty()) {
            complianceExterno = ComplianceExternoResourceIT.createEntity(em);
            em.persist(complianceExterno);
            em.flush();
        } else {
            complianceExterno = TestUtil.findAll(em, ComplianceExterno.class).get(0);
        }
        em.persist(complianceExterno);
        em.flush();
        complianceExternoProcesso.setComplianceExterno(complianceExterno);
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);
        Long complianceExternoId = complianceExterno.getId();

        // Get all the complianceExternoProcessoList where complianceExterno equals to complianceExternoId
        defaultComplianceExternoProcessoShouldBeFound("complianceExternoId.equals=" + complianceExternoId);

        // Get all the complianceExternoProcessoList where complianceExterno equals to (complianceExternoId + 1)
        defaultComplianceExternoProcessoShouldNotBeFound("complianceExternoId.equals=" + (complianceExternoId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);
        Processo processo;
        if (TestUtil.findAll(em, Processo.class).isEmpty()) {
            processo = ProcessoResourceIT.createEntity(em);
            em.persist(processo);
            em.flush();
        } else {
            processo = TestUtil.findAll(em, Processo.class).get(0);
        }
        em.persist(processo);
        em.flush();
        complianceExternoProcesso.setProcesso(processo);
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);
        Long processoId = processo.getId();

        // Get all the complianceExternoProcessoList where processo equals to processoId
        defaultComplianceExternoProcessoShouldBeFound("processoId.equals=" + processoId);

        // Get all the complianceExternoProcessoList where processo equals to (processoId + 1)
        defaultComplianceExternoProcessoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceExternoProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);
        Usuario usuario;
        if (TestUtil.findAll(em, Usuario.class).isEmpty()) {
            usuario = UsuarioResourceIT.createEntity(em);
            em.persist(usuario);
            em.flush();
        } else {
            usuario = TestUtil.findAll(em, Usuario.class).get(0);
        }
        em.persist(usuario);
        em.flush();
        complianceExternoProcesso.setUsuario(usuario);
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);
        Long usuarioId = usuario.getId();

        // Get all the complianceExternoProcessoList where usuario equals to usuarioId
        defaultComplianceExternoProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the complianceExternoProcessoList where usuario equals to (usuarioId + 1)
        defaultComplianceExternoProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComplianceExternoProcessoShouldBeFound(String filter) throws Exception {
        restComplianceExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceExternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianceExterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restComplianceExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComplianceExternoProcessoShouldNotBeFound(String filter) throws Exception {
        restComplianceExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComplianceExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComplianceExternoProcesso() throws Exception {
        // Get the complianceExternoProcesso
        restComplianceExternoProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComplianceExternoProcesso() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();

        // Update the complianceExternoProcesso
        ComplianceExternoProcesso updatedComplianceExternoProcesso = complianceExternoProcessoRepository
            .findById(complianceExternoProcesso.getId())
            .get();
        // Disconnect from session so that the updates on updatedComplianceExternoProcesso are not directly saved in db
        em.detach(updatedComplianceExternoProcesso);
        updatedComplianceExternoProcesso
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(updatedComplianceExternoProcesso);

        restComplianceExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceExternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceExternoProcesso testComplianceExternoProcesso = complianceExternoProcessoList.get(
            complianceExternoProcessoList.size() - 1
        );
        assertThat(testComplianceExternoProcesso.getIdnVarComplianceExterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testComplianceExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();
        complianceExternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceExternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();
        complianceExternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();
        complianceExternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComplianceExternoProcessoWithPatch() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();

        // Update the complianceExternoProcesso using partial update
        ComplianceExternoProcesso partialUpdatedComplianceExternoProcesso = new ComplianceExternoProcesso();
        partialUpdatedComplianceExternoProcesso.setId(complianceExternoProcesso.getId());

        partialUpdatedComplianceExternoProcesso
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restComplianceExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceExternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceExternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceExternoProcesso testComplianceExternoProcesso = complianceExternoProcessoList.get(
            complianceExternoProcessoList.size() - 1
        );
        assertThat(testComplianceExternoProcesso.getIdnVarComplianceExterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testComplianceExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateComplianceExternoProcessoWithPatch() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();

        // Update the complianceExternoProcesso using partial update
        ComplianceExternoProcesso partialUpdatedComplianceExternoProcesso = new ComplianceExternoProcesso();
        partialUpdatedComplianceExternoProcesso.setId(complianceExternoProcesso.getId());

        partialUpdatedComplianceExternoProcesso
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restComplianceExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceExternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceExternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceExternoProcesso testComplianceExternoProcesso = complianceExternoProcessoList.get(
            complianceExternoProcessoList.size() - 1
        );
        assertThat(testComplianceExternoProcesso.getIdnVarComplianceExterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testComplianceExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();
        complianceExternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, complianceExternoProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();
        complianceExternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComplianceExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoProcessoRepository.findAll().size();
        complianceExternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceExternoProcesso
        ComplianceExternoProcessoDTO complianceExternoProcessoDTO = complianceExternoProcessoMapper.toDto(complianceExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceExternoProcesso in the database
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComplianceExternoProcesso() throws Exception {
        // Initialize the database
        complianceExternoProcessoRepository.saveAndFlush(complianceExternoProcesso);

        int databaseSizeBeforeDelete = complianceExternoProcessoRepository.findAll().size();

        // Delete the complianceExternoProcesso
        restComplianceExternoProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, complianceExternoProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComplianceExternoProcesso> complianceExternoProcessoList = complianceExternoProcessoRepository.findAll();
        assertThat(complianceExternoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
