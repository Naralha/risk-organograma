package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.ComplianceExterno;
import io.sld.riskcomplianceservice.domain.entity.ComplianceExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.ComplianceExternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ComplianceExternoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ComplianceExternoMapper;
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
 * Integration tests for the {@link ComplianceExternoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComplianceExternoResourceIT {

    private static final String DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_COMPLIANCE_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/compliance-externos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComplianceExternoRepository complianceExternoRepository;

    @Autowired
    private ComplianceExternoMapper complianceExternoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplianceExternoMockMvc;

    private ComplianceExterno complianceExterno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceExterno createEntity(EntityManager em) {
        ComplianceExterno complianceExterno = new ComplianceExterno()
            .idnVarComplianceExterno(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return complianceExterno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceExterno createUpdatedEntity(EntityManager em) {
        ComplianceExterno complianceExterno = new ComplianceExterno()
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return complianceExterno;
    }

    @BeforeEach
    public void initTest() {
        complianceExterno = createEntity(em);
    }

    @Test
    @Transactional
    void createComplianceExterno() throws Exception {
        int databaseSizeBeforeCreate = complianceExternoRepository.findAll().size();
        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);
        restComplianceExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeCreate + 1);
        ComplianceExterno testComplianceExterno = complianceExternoList.get(complianceExternoList.size() - 1);
        assertThat(testComplianceExterno.getIdnVarComplianceExterno()).isEqualTo(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExterno.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testComplianceExterno.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
        assertThat(testComplianceExterno.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testComplianceExterno.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createComplianceExternoWithExistingId() throws Exception {
        // Create the ComplianceExterno with an existing ID
        complianceExterno.setId(1L);
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        int databaseSizeBeforeCreate = complianceExternoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplianceExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarComplianceExternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoRepository.findAll().size();
        // set the field null
        complianceExterno.setIdnVarComplianceExterno(null);

        // Create the ComplianceExterno, which fails.
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        restComplianceExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoRepository.findAll().size();
        // set the field null
        complianceExterno.setnVarNome(null);

        // Create the ComplianceExterno, which fails.
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        restComplianceExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoRepository.findAll().size();
        // set the field null
        complianceExterno.setIdnVarEmpresa(null);

        // Create the ComplianceExterno, which fails.
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        restComplianceExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceExternoRepository.findAll().size();
        // set the field null
        complianceExterno.setIdnvarUsuario(null);

        // Create the ComplianceExterno, which fails.
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        restComplianceExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComplianceExternos() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList
        restComplianceExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianceExterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getComplianceExterno() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get the complianceExterno
        restComplianceExternoMockMvc
            .perform(get(ENTITY_API_URL_ID, complianceExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complianceExterno.getId().intValue()))
            .andExpect(jsonPath("$.idnVarComplianceExterno").value(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getComplianceExternosByIdFiltering() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        Long id = complianceExterno.getId();

        defaultComplianceExternoShouldBeFound("id.equals=" + id);
        defaultComplianceExternoShouldNotBeFound("id.notEquals=" + id);

        defaultComplianceExternoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComplianceExternoShouldNotBeFound("id.greaterThan=" + id);

        defaultComplianceExternoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComplianceExternoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarComplianceExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarComplianceExterno equals to DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldBeFound("idnVarComplianceExterno.equals=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoList where idnVarComplianceExterno equals to UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldNotBeFound("idnVarComplianceExterno.equals=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarComplianceExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarComplianceExterno not equals to DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldNotBeFound("idnVarComplianceExterno.notEquals=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoList where idnVarComplianceExterno not equals to UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldBeFound("idnVarComplianceExterno.notEquals=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarComplianceExternoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarComplianceExterno in DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO or UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldBeFound(
            "idnVarComplianceExterno.in=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO + "," + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        );

        // Get all the complianceExternoList where idnVarComplianceExterno equals to UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldNotBeFound("idnVarComplianceExterno.in=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarComplianceExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarComplianceExterno is not null
        defaultComplianceExternoShouldBeFound("idnVarComplianceExterno.specified=true");

        // Get all the complianceExternoList where idnVarComplianceExterno is null
        defaultComplianceExternoShouldNotBeFound("idnVarComplianceExterno.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarComplianceExternoContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarComplianceExterno contains DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldBeFound("idnVarComplianceExterno.contains=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoList where idnVarComplianceExterno contains UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldNotBeFound("idnVarComplianceExterno.contains=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarComplianceExternoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarComplianceExterno does not contain DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldNotBeFound("idnVarComplianceExterno.doesNotContain=" + DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO);

        // Get all the complianceExternoList where idnVarComplianceExterno does not contain UPDATED_IDN_VAR_COMPLIANCE_EXTERNO
        defaultComplianceExternoShouldBeFound("idnVarComplianceExterno.doesNotContain=" + UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultComplianceExternoShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceExternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultComplianceExternoShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultComplianceExternoShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceExternoList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultComplianceExternoShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultComplianceExternoShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the complianceExternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultComplianceExternoShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarNome is not null
        defaultComplianceExternoShouldBeFound("nVarNome.specified=true");

        // Get all the complianceExternoList where nVarNome is null
        defaultComplianceExternoShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultComplianceExternoShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceExternoList where nVarNome contains UPDATED_N_VAR_NOME
        defaultComplianceExternoShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultComplianceExternoShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceExternoList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultComplianceExternoShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultComplianceExternoShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceExternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultComplianceExternoShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultComplianceExternoShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceExternoList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultComplianceExternoShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultComplianceExternoShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the complianceExternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultComplianceExternoShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarDescricao is not null
        defaultComplianceExternoShouldBeFound("nVarDescricao.specified=true");

        // Get all the complianceExternoList where nVarDescricao is null
        defaultComplianceExternoShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultComplianceExternoShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceExternoList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultComplianceExternoShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultComplianceExternoShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceExternoList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultComplianceExternoShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceExternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceExternoList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the complianceExternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarEmpresa is not null
        defaultComplianceExternoShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the complianceExternoList where idnVarEmpresa is null
        defaultComplianceExternoShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceExternoList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceExternoList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultComplianceExternoShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the complianceExternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnvarUsuario is not null
        defaultComplianceExternoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the complianceExternoList where idnvarUsuario is null
        defaultComplianceExternoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        // Get all the complianceExternoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultComplianceExternoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceExternoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultComplianceExternoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceExternosByComplianceExternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);
        ComplianceExternoProcesso complianceExternoProcesso;
        if (TestUtil.findAll(em, ComplianceExternoProcesso.class).isEmpty()) {
            complianceExternoProcesso = ComplianceExternoProcessoResourceIT.createEntity(em);
            em.persist(complianceExternoProcesso);
            em.flush();
        } else {
            complianceExternoProcesso = TestUtil.findAll(em, ComplianceExternoProcesso.class).get(0);
        }
        em.persist(complianceExternoProcesso);
        em.flush();
        complianceExterno.addComplianceExternoProcesso(complianceExternoProcesso);
        complianceExternoRepository.saveAndFlush(complianceExterno);
        Long complianceExternoProcessoId = complianceExternoProcesso.getId();

        // Get all the complianceExternoList where complianceExternoProcesso equals to complianceExternoProcessoId
        defaultComplianceExternoShouldBeFound("complianceExternoProcessoId.equals=" + complianceExternoProcessoId);

        // Get all the complianceExternoList where complianceExternoProcesso equals to (complianceExternoProcessoId + 1)
        defaultComplianceExternoShouldNotBeFound("complianceExternoProcessoId.equals=" + (complianceExternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceExternosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);
        Empresa empresa;
        if (TestUtil.findAll(em, Empresa.class).isEmpty()) {
            empresa = EmpresaResourceIT.createEntity(em);
            em.persist(empresa);
            em.flush();
        } else {
            empresa = TestUtil.findAll(em, Empresa.class).get(0);
        }
        em.persist(empresa);
        em.flush();
        complianceExterno.setEmpresa(empresa);
        complianceExternoRepository.saveAndFlush(complianceExterno);
        Long empresaId = empresa.getId();

        // Get all the complianceExternoList where empresa equals to empresaId
        defaultComplianceExternoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the complianceExternoList where empresa equals to (empresaId + 1)
        defaultComplianceExternoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceExternosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);
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
        complianceExterno.setUsuario(usuario);
        complianceExternoRepository.saveAndFlush(complianceExterno);
        Long usuarioId = usuario.getId();

        // Get all the complianceExternoList where usuario equals to usuarioId
        defaultComplianceExternoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the complianceExternoList where usuario equals to (usuarioId + 1)
        defaultComplianceExternoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComplianceExternoShouldBeFound(String filter) throws Exception {
        restComplianceExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianceExterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANCE_EXTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restComplianceExternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComplianceExternoShouldNotBeFound(String filter) throws Exception {
        restComplianceExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComplianceExternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComplianceExterno() throws Exception {
        // Get the complianceExterno
        restComplianceExternoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComplianceExterno() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();

        // Update the complianceExterno
        ComplianceExterno updatedComplianceExterno = complianceExternoRepository.findById(complianceExterno.getId()).get();
        // Disconnect from session so that the updates on updatedComplianceExterno are not directly saved in db
        em.detach(updatedComplianceExterno);
        updatedComplianceExterno
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(updatedComplianceExterno);

        restComplianceExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceExternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceExterno testComplianceExterno = complianceExternoList.get(complianceExternoList.size() - 1);
        assertThat(testComplianceExterno.getIdnVarComplianceExterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testComplianceExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testComplianceExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testComplianceExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingComplianceExterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();
        complianceExterno.setId(count.incrementAndGet());

        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceExternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComplianceExterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();
        complianceExterno.setId(count.incrementAndGet());

        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComplianceExterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();
        complianceExterno.setId(count.incrementAndGet());

        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComplianceExternoWithPatch() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();

        // Update the complianceExterno using partial update
        ComplianceExterno partialUpdatedComplianceExterno = new ComplianceExterno();
        partialUpdatedComplianceExterno.setId(complianceExterno.getId());

        partialUpdatedComplianceExterno
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA);

        restComplianceExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceExterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceExterno))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceExterno testComplianceExterno = complianceExternoList.get(complianceExternoList.size() - 1);
        assertThat(testComplianceExterno.getIdnVarComplianceExterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testComplianceExterno.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
        assertThat(testComplianceExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testComplianceExterno.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateComplianceExternoWithPatch() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();

        // Update the complianceExterno using partial update
        ComplianceExterno partialUpdatedComplianceExterno = new ComplianceExterno();
        partialUpdatedComplianceExterno.setId(complianceExterno.getId());

        partialUpdatedComplianceExterno
            .idnVarComplianceExterno(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restComplianceExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceExterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceExterno))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceExterno testComplianceExterno = complianceExternoList.get(complianceExternoList.size() - 1);
        assertThat(testComplianceExterno.getIdnVarComplianceExterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANCE_EXTERNO);
        assertThat(testComplianceExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testComplianceExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testComplianceExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testComplianceExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingComplianceExterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();
        complianceExterno.setId(count.incrementAndGet());

        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, complianceExternoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComplianceExterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();
        complianceExterno.setId(count.incrementAndGet());

        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComplianceExterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceExternoRepository.findAll().size();
        complianceExterno.setId(count.incrementAndGet());

        // Create the ComplianceExterno
        ComplianceExternoDTO complianceExternoDTO = complianceExternoMapper.toDto(complianceExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceExternoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceExternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceExterno in the database
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComplianceExterno() throws Exception {
        // Initialize the database
        complianceExternoRepository.saveAndFlush(complianceExterno);

        int databaseSizeBeforeDelete = complianceExternoRepository.findAll().size();

        // Delete the complianceExterno
        restComplianceExternoMockMvc
            .perform(delete(ENTITY_API_URL_ID, complianceExterno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComplianceExterno> complianceExternoList = complianceExternoRepository.findAll();
        assertThat(complianceExternoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
