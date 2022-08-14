package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.ComplianceInterno;
import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.ComplianceInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceInternoProcessoMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ComplianceInternoProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComplianceInternoProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_COMPLIANCE_INTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_COMPLIANCE_INTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/compliance-interno-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComplianceInternoProcessoRepository complianceInternoProcessoRepository;

    @Autowired
    private ComplianceInternoProcessoMapper complianceInternoProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplianceInternoProcessoMockMvc;

    private ComplianceInternoProcesso complianceInternoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceInternoProcesso createEntity(EntityManager em) {
        ComplianceInternoProcesso complianceInternoProcesso = new ComplianceInternoProcesso()
            .idnVarComplianceInterno(DEFAULT_IDN_VAR_COMPLIANCE_INTERNO)
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return complianceInternoProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceInternoProcesso createUpdatedEntity(EntityManager em) {
        ComplianceInternoProcesso complianceInternoProcesso = new ComplianceInternoProcesso()
            .idnVarComplianceInterno(UPDATED_IDN_VAR_COMPLIANCE_INTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return complianceInternoProcesso;
    }

    @BeforeEach
    public void initTest() {
        complianceInternoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeCreate = complianceInternoProcessoRepository.findAll().size();
        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);
        restComplianceInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        ComplianceInternoProcesso testComplianceInternoProcesso = complianceInternoProcessoList.get(
            complianceInternoProcessoList.size() - 1
        );
        assertThat(testComplianceInternoProcesso.getIdnVarComplianceInterno()).isEqualTo(DEFAULT_IDN_VAR_COMPLIANCE_INTERNO);
        assertThat(testComplianceInternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testComplianceInternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createComplianceInternoProcessoWithExistingId() throws Exception {
        // Create the ComplianceInternoProcesso with an existing ID
        complianceInternoProcesso.setId(1L);
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        int databaseSizeBeforeCreate = complianceInternoProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplianceInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarComplianceInternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoProcessoRepository.findAll().size();
        // set the field null
        complianceInternoProcesso.setIdnVarComplianceInterno(null);

        // Create the ComplianceInternoProcesso, which fails.
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        restComplianceInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoProcessoRepository.findAll().size();
        // set the field null
        complianceInternoProcesso.setIdnVarProcesso(null);

        // Create the ComplianceInternoProcesso, which fails.
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        restComplianceInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoProcessoRepository.findAll().size();
        // set the field null
        complianceInternoProcesso.setIdnvarUsuario(null);

        // Create the ComplianceInternoProcesso, which fails.
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        restComplianceInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessos() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList
        restComplianceInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceInternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianceInterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANCE_INTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getComplianceInternoProcesso() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get the complianceInternoProcesso
        restComplianceInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, complianceInternoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complianceInternoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarComplianceInterno").value(DEFAULT_IDN_VAR_COMPLIANCE_INTERNO))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getComplianceInternoProcessosByIdFiltering() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        Long id = complianceInternoProcesso.getId();

        defaultComplianceInternoProcessoShouldBeFound("id.equals=" + id);
        defaultComplianceInternoProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultComplianceInternoProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComplianceInternoProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultComplianceInternoProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComplianceInternoProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarComplianceInternoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno equals to DEFAULT_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldBeFound("idnVarComplianceInterno.equals=" + DEFAULT_IDN_VAR_COMPLIANCE_INTERNO);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno equals to UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarComplianceInterno.equals=" + UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarComplianceInternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno not equals to DEFAULT_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarComplianceInterno.notEquals=" + DEFAULT_IDN_VAR_COMPLIANCE_INTERNO);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno not equals to UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldBeFound("idnVarComplianceInterno.notEquals=" + UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarComplianceInternoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno in DEFAULT_IDN_VAR_COMPLIANCE_INTERNO or UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldBeFound(
            "idnVarComplianceInterno.in=" + DEFAULT_IDN_VAR_COMPLIANCE_INTERNO + "," + UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        );

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno equals to UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarComplianceInterno.in=" + UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarComplianceInternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno is not null
        defaultComplianceInternoProcessoShouldBeFound("idnVarComplianceInterno.specified=true");

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno is null
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarComplianceInterno.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarComplianceInternoContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno contains DEFAULT_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldBeFound("idnVarComplianceInterno.contains=" + DEFAULT_IDN_VAR_COMPLIANCE_INTERNO);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno contains UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarComplianceInterno.contains=" + UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarComplianceInternoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno does not contain DEFAULT_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarComplianceInterno.doesNotContain=" + DEFAULT_IDN_VAR_COMPLIANCE_INTERNO);

        // Get all the complianceInternoProcessoList where idnVarComplianceInterno does not contain UPDATED_IDN_VAR_COMPLIANCE_INTERNO
        defaultComplianceInternoProcessoShouldBeFound("idnVarComplianceInterno.doesNotContain=" + UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceInternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceInternoProcessoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the complianceInternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarProcesso is not null
        defaultComplianceInternoProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the complianceInternoProcessoList where idnVarProcesso is null
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceInternoProcessoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the complianceInternoProcessoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultComplianceInternoProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoProcessoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the complianceInternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnvarUsuario is not null
        defaultComplianceInternoProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the complianceInternoProcessoList where idnvarUsuario is null
        defaultComplianceInternoProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoProcessoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        // Get all the complianceInternoProcessoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoProcessoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByComplianceInternoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);
        ComplianceInterno complianceInterno;
        if (TestUtil.findAll(em, ComplianceInterno.class).isEmpty()) {
            complianceInterno = ComplianceInternoResourceIT.createEntity(em);
            em.persist(complianceInterno);
            em.flush();
        } else {
            complianceInterno = TestUtil.findAll(em, ComplianceInterno.class).get(0);
        }
        em.persist(complianceInterno);
        em.flush();
        complianceInternoProcesso.setComplianceInterno(complianceInterno);
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);
        Long complianceInternoId = complianceInterno.getId();

        // Get all the complianceInternoProcessoList where complianceInterno equals to complianceInternoId
        defaultComplianceInternoProcessoShouldBeFound("complianceInternoId.equals=" + complianceInternoId);

        // Get all the complianceInternoProcessoList where complianceInterno equals to (complianceInternoId + 1)
        defaultComplianceInternoProcessoShouldNotBeFound("complianceInternoId.equals=" + (complianceInternoId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);
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
        complianceInternoProcesso.setProcesso(processo);
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);
        Long processoId = processo.getId();

        // Get all the complianceInternoProcessoList where processo equals to processoId
        defaultComplianceInternoProcessoShouldBeFound("processoId.equals=" + processoId);

        // Get all the complianceInternoProcessoList where processo equals to (processoId + 1)
        defaultComplianceInternoProcessoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceInternoProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);
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
        complianceInternoProcesso.setUsuario(usuario);
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);
        Long usuarioId = usuario.getId();

        // Get all the complianceInternoProcessoList where usuario equals to usuarioId
        defaultComplianceInternoProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the complianceInternoProcessoList where usuario equals to (usuarioId + 1)
        defaultComplianceInternoProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComplianceInternoProcessoShouldBeFound(String filter) throws Exception {
        restComplianceInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceInternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianceInterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANCE_INTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restComplianceInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComplianceInternoProcessoShouldNotBeFound(String filter) throws Exception {
        restComplianceInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComplianceInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComplianceInternoProcesso() throws Exception {
        // Get the complianceInternoProcesso
        restComplianceInternoProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComplianceInternoProcesso() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();

        // Update the complianceInternoProcesso
        ComplianceInternoProcesso updatedComplianceInternoProcesso = complianceInternoProcessoRepository
            .findById(complianceInternoProcesso.getId())
            .get();
        // Disconnect from session so that the updates on updatedComplianceInternoProcesso are not directly saved in db
        em.detach(updatedComplianceInternoProcesso);
        updatedComplianceInternoProcesso
            .idnVarComplianceInterno(UPDATED_IDN_VAR_COMPLIANCE_INTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(updatedComplianceInternoProcesso);

        restComplianceInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceInternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceInternoProcesso testComplianceInternoProcesso = complianceInternoProcessoList.get(
            complianceInternoProcessoList.size() - 1
        );
        assertThat(testComplianceInternoProcesso.getIdnVarComplianceInterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
        assertThat(testComplianceInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testComplianceInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();
        complianceInternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceInternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();
        complianceInternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();
        complianceInternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComplianceInternoProcessoWithPatch() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();

        // Update the complianceInternoProcesso using partial update
        ComplianceInternoProcesso partialUpdatedComplianceInternoProcesso = new ComplianceInternoProcesso();
        partialUpdatedComplianceInternoProcesso.setId(complianceInternoProcesso.getId());

        partialUpdatedComplianceInternoProcesso
            .idnVarComplianceInterno(UPDATED_IDN_VAR_COMPLIANCE_INTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO);

        restComplianceInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceInternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceInternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceInternoProcesso testComplianceInternoProcesso = complianceInternoProcessoList.get(
            complianceInternoProcessoList.size() - 1
        );
        assertThat(testComplianceInternoProcesso.getIdnVarComplianceInterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
        assertThat(testComplianceInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testComplianceInternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateComplianceInternoProcessoWithPatch() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();

        // Update the complianceInternoProcesso using partial update
        ComplianceInternoProcesso partialUpdatedComplianceInternoProcesso = new ComplianceInternoProcesso();
        partialUpdatedComplianceInternoProcesso.setId(complianceInternoProcesso.getId());

        partialUpdatedComplianceInternoProcesso
            .idnVarComplianceInterno(UPDATED_IDN_VAR_COMPLIANCE_INTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restComplianceInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceInternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceInternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceInternoProcesso testComplianceInternoProcesso = complianceInternoProcessoList.get(
            complianceInternoProcessoList.size() - 1
        );
        assertThat(testComplianceInternoProcesso.getIdnVarComplianceInterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_INTERNO);
        assertThat(testComplianceInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testComplianceInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();
        complianceInternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, complianceInternoProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();
        complianceInternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComplianceInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoProcessoRepository.findAll().size();
        complianceInternoProcesso.setId(count.incrementAndGet());

        // Create the ComplianceInternoProcesso
        ComplianceInternoProcessoDTO complianceInternoProcessoDTO = complianceInternoProcessoMapper.toDto(complianceInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceInternoProcesso in the database
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComplianceInternoProcesso() throws Exception {
        // Initialize the database
        complianceInternoProcessoRepository.saveAndFlush(complianceInternoProcesso);

        int databaseSizeBeforeDelete = complianceInternoProcessoRepository.findAll().size();

        // Delete the complianceInternoProcesso
        restComplianceInternoProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, complianceInternoProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComplianceInternoProcesso> complianceInternoProcessoList = complianceInternoProcessoRepository.findAll();
        assertThat(complianceInternoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
