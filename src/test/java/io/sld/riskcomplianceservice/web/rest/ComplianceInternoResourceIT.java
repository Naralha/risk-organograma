package io.sld.riskcomplianceservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.ComplianceInterno;
import io.sld.riskcomplianceservice.domain.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.repository.ComplianceInternoRepository;
import io.sld.riskcomplianceservice.service.criteria.ComplianceInternoCriteria;
import io.sld.riskcomplianceservice.service.dto.ComplianceInternoDTO;
import io.sld.riskcomplianceservice.service.mapper.ComplianceInternoMapper;
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
 * Integration tests for the {@link ComplianceInternoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComplianceInternoResourceIT {

    private static final String DEFAULT_IDN_VAR_COMPLIANTE_INTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_COMPLIANTE_INTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/compliance-internos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComplianceInternoRepository complianceInternoRepository;

    @Autowired
    private ComplianceInternoMapper complianceInternoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComplianceInternoMockMvc;

    private ComplianceInterno complianceInterno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceInterno createEntity(EntityManager em) {
        ComplianceInterno complianceInterno = new ComplianceInterno()
            .idnVarComplianteInterno(DEFAULT_IDN_VAR_COMPLIANTE_INTERNO)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return complianceInterno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComplianceInterno createUpdatedEntity(EntityManager em) {
        ComplianceInterno complianceInterno = new ComplianceInterno()
            .idnVarComplianteInterno(UPDATED_IDN_VAR_COMPLIANTE_INTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return complianceInterno;
    }

    @BeforeEach
    public void initTest() {
        complianceInterno = createEntity(em);
    }

    @Test
    @Transactional
    void createComplianceInterno() throws Exception {
        int databaseSizeBeforeCreate = complianceInternoRepository.findAll().size();
        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);
        restComplianceInternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeCreate + 1);
        ComplianceInterno testComplianceInterno = complianceInternoList.get(complianceInternoList.size() - 1);
        assertThat(testComplianceInterno.getIdnVarComplianteInterno()).isEqualTo(DEFAULT_IDN_VAR_COMPLIANTE_INTERNO);
        assertThat(testComplianceInterno.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testComplianceInterno.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
        assertThat(testComplianceInterno.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testComplianceInterno.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createComplianceInternoWithExistingId() throws Exception {
        // Create the ComplianceInterno with an existing ID
        complianceInterno.setId(1L);
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        int databaseSizeBeforeCreate = complianceInternoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComplianceInternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarComplianteInternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoRepository.findAll().size();
        // set the field null
        complianceInterno.setIdnVarComplianteInterno(null);

        // Create the ComplianceInterno, which fails.
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        restComplianceInternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoRepository.findAll().size();
        // set the field null
        complianceInterno.setnVarNome(null);

        // Create the ComplianceInterno, which fails.
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        restComplianceInternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoRepository.findAll().size();
        // set the field null
        complianceInterno.setIdnVarEmpresa(null);

        // Create the ComplianceInterno, which fails.
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        restComplianceInternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = complianceInternoRepository.findAll().size();
        // set the field null
        complianceInterno.setIdnvarUsuario(null);

        // Create the ComplianceInterno, which fails.
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        restComplianceInternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComplianceInternos() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList
        restComplianceInternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceInterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianteInterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANTE_INTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getComplianceInterno() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get the complianceInterno
        restComplianceInternoMockMvc
            .perform(get(ENTITY_API_URL_ID, complianceInterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(complianceInterno.getId().intValue()))
            .andExpect(jsonPath("$.idnVarComplianteInterno").value(DEFAULT_IDN_VAR_COMPLIANTE_INTERNO))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getComplianceInternosByIdFiltering() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        Long id = complianceInterno.getId();

        defaultComplianceInternoShouldBeFound("id.equals=" + id);
        defaultComplianceInternoShouldNotBeFound("id.notEquals=" + id);

        defaultComplianceInternoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComplianceInternoShouldNotBeFound("id.greaterThan=" + id);

        defaultComplianceInternoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComplianceInternoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarComplianteInternoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarComplianteInterno equals to DEFAULT_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldBeFound("idnVarComplianteInterno.equals=" + DEFAULT_IDN_VAR_COMPLIANTE_INTERNO);

        // Get all the complianceInternoList where idnVarComplianteInterno equals to UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldNotBeFound("idnVarComplianteInterno.equals=" + UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarComplianteInternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarComplianteInterno not equals to DEFAULT_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldNotBeFound("idnVarComplianteInterno.notEquals=" + DEFAULT_IDN_VAR_COMPLIANTE_INTERNO);

        // Get all the complianceInternoList where idnVarComplianteInterno not equals to UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldBeFound("idnVarComplianteInterno.notEquals=" + UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarComplianteInternoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarComplianteInterno in DEFAULT_IDN_VAR_COMPLIANTE_INTERNO or UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldBeFound(
            "idnVarComplianteInterno.in=" + DEFAULT_IDN_VAR_COMPLIANTE_INTERNO + "," + UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        );

        // Get all the complianceInternoList where idnVarComplianteInterno equals to UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldNotBeFound("idnVarComplianteInterno.in=" + UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarComplianteInternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarComplianteInterno is not null
        defaultComplianceInternoShouldBeFound("idnVarComplianteInterno.specified=true");

        // Get all the complianceInternoList where idnVarComplianteInterno is null
        defaultComplianceInternoShouldNotBeFound("idnVarComplianteInterno.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarComplianteInternoContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarComplianteInterno contains DEFAULT_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldBeFound("idnVarComplianteInterno.contains=" + DEFAULT_IDN_VAR_COMPLIANTE_INTERNO);

        // Get all the complianceInternoList where idnVarComplianteInterno contains UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldNotBeFound("idnVarComplianteInterno.contains=" + UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarComplianteInternoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarComplianteInterno does not contain DEFAULT_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldNotBeFound("idnVarComplianteInterno.doesNotContain=" + DEFAULT_IDN_VAR_COMPLIANTE_INTERNO);

        // Get all the complianceInternoList where idnVarComplianteInterno does not contain UPDATED_IDN_VAR_COMPLIANTE_INTERNO
        defaultComplianceInternoShouldBeFound("idnVarComplianteInterno.doesNotContain=" + UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultComplianceInternoShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceInternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultComplianceInternoShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultComplianceInternoShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceInternoList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultComplianceInternoShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultComplianceInternoShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the complianceInternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultComplianceInternoShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarNome is not null
        defaultComplianceInternoShouldBeFound("nVarNome.specified=true");

        // Get all the complianceInternoList where nVarNome is null
        defaultComplianceInternoShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultComplianceInternoShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceInternoList where nVarNome contains UPDATED_N_VAR_NOME
        defaultComplianceInternoShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultComplianceInternoShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the complianceInternoList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultComplianceInternoShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultComplianceInternoShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceInternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultComplianceInternoShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultComplianceInternoShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceInternoList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultComplianceInternoShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultComplianceInternoShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the complianceInternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultComplianceInternoShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarDescricao is not null
        defaultComplianceInternoShouldBeFound("nVarDescricao.specified=true");

        // Get all the complianceInternoList where nVarDescricao is null
        defaultComplianceInternoShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultComplianceInternoShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceInternoList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultComplianceInternoShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultComplianceInternoShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the complianceInternoList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultComplianceInternoShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceInternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceInternoList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the complianceInternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarEmpresa is not null
        defaultComplianceInternoShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the complianceInternoList where idnVarEmpresa is null
        defaultComplianceInternoShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceInternoList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the complianceInternoList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultComplianceInternoShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the complianceInternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnvarUsuario is not null
        defaultComplianceInternoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the complianceInternoList where idnvarUsuario is null
        defaultComplianceInternoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        // Get all the complianceInternoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultComplianceInternoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the complianceInternoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultComplianceInternoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllComplianceInternosByComplianceInternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);
        ComplianceInternoProcesso complianceInternoProcesso;
        if (TestUtil.findAll(em, ComplianceInternoProcesso.class).isEmpty()) {
            complianceInternoProcesso = ComplianceInternoProcessoResourceIT.createEntity(em);
            em.persist(complianceInternoProcesso);
            em.flush();
        } else {
            complianceInternoProcesso = TestUtil.findAll(em, ComplianceInternoProcesso.class).get(0);
        }
        em.persist(complianceInternoProcesso);
        em.flush();
        complianceInterno.addComplianceInternoProcesso(complianceInternoProcesso);
        complianceInternoRepository.saveAndFlush(complianceInterno);
        Long complianceInternoProcessoId = complianceInternoProcesso.getId();

        // Get all the complianceInternoList where complianceInternoProcesso equals to complianceInternoProcessoId
        defaultComplianceInternoShouldBeFound("complianceInternoProcessoId.equals=" + complianceInternoProcessoId);

        // Get all the complianceInternoList where complianceInternoProcesso equals to (complianceInternoProcessoId + 1)
        defaultComplianceInternoShouldNotBeFound("complianceInternoProcessoId.equals=" + (complianceInternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceInternosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);
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
        complianceInterno.setEmpresa(empresa);
        complianceInternoRepository.saveAndFlush(complianceInterno);
        Long empresaId = empresa.getId();

        // Get all the complianceInternoList where empresa equals to empresaId
        defaultComplianceInternoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the complianceInternoList where empresa equals to (empresaId + 1)
        defaultComplianceInternoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllComplianceInternosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);
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
        complianceInterno.setUsuario(usuario);
        complianceInternoRepository.saveAndFlush(complianceInterno);
        Long usuarioId = usuario.getId();

        // Get all the complianceInternoList where usuario equals to usuarioId
        defaultComplianceInternoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the complianceInternoList where usuario equals to (usuarioId + 1)
        defaultComplianceInternoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComplianceInternoShouldBeFound(String filter) throws Exception {
        restComplianceInternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(complianceInterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarComplianteInterno").value(hasItem(DEFAULT_IDN_VAR_COMPLIANTE_INTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restComplianceInternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComplianceInternoShouldNotBeFound(String filter) throws Exception {
        restComplianceInternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComplianceInternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComplianceInterno() throws Exception {
        // Get the complianceInterno
        restComplianceInternoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComplianceInterno() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();

        // Update the complianceInterno
        ComplianceInterno updatedComplianceInterno = complianceInternoRepository.findById(complianceInterno.getId()).get();
        // Disconnect from session so that the updates on updatedComplianceInterno are not directly saved in db
        em.detach(updatedComplianceInterno);
        updatedComplianceInterno
            .idnVarComplianteInterno(UPDATED_IDN_VAR_COMPLIANTE_INTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(updatedComplianceInterno);

        restComplianceInternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceInternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceInterno testComplianceInterno = complianceInternoList.get(complianceInternoList.size() - 1);
        assertThat(testComplianceInterno.getIdnVarComplianteInterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
        assertThat(testComplianceInterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testComplianceInterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testComplianceInterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testComplianceInterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingComplianceInterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();
        complianceInterno.setId(count.incrementAndGet());

        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceInternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, complianceInternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComplianceInterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();
        complianceInterno.setId(count.incrementAndGet());

        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComplianceInterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();
        complianceInterno.setId(count.incrementAndGet());

        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComplianceInternoWithPatch() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();

        // Update the complianceInterno using partial update
        ComplianceInterno partialUpdatedComplianceInterno = new ComplianceInterno();
        partialUpdatedComplianceInterno.setId(complianceInterno.getId());

        partialUpdatedComplianceInterno
            .idnVarComplianteInterno(UPDATED_IDN_VAR_COMPLIANTE_INTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA);

        restComplianceInternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceInterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceInterno))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceInterno testComplianceInterno = complianceInternoList.get(complianceInternoList.size() - 1);
        assertThat(testComplianceInterno.getIdnVarComplianteInterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
        assertThat(testComplianceInterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testComplianceInterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testComplianceInterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testComplianceInterno.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateComplianceInternoWithPatch() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();

        // Update the complianceInterno using partial update
        ComplianceInterno partialUpdatedComplianceInterno = new ComplianceInterno();
        partialUpdatedComplianceInterno.setId(complianceInterno.getId());

        partialUpdatedComplianceInterno
            .idnVarComplianteInterno(UPDATED_IDN_VAR_COMPLIANTE_INTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restComplianceInternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComplianceInterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComplianceInterno))
            )
            .andExpect(status().isOk());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
        ComplianceInterno testComplianceInterno = complianceInternoList.get(complianceInternoList.size() - 1);
        assertThat(testComplianceInterno.getIdnVarComplianteInterno()).isEqualTo(UPDATED_IDN_VAR_COMPLIANTE_INTERNO);
        assertThat(testComplianceInterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testComplianceInterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testComplianceInterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testComplianceInterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingComplianceInterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();
        complianceInterno.setId(count.incrementAndGet());

        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComplianceInternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, complianceInternoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComplianceInterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();
        complianceInterno.setId(count.incrementAndGet());

        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComplianceInterno() throws Exception {
        int databaseSizeBeforeUpdate = complianceInternoRepository.findAll().size();
        complianceInterno.setId(count.incrementAndGet());

        // Create the ComplianceInterno
        ComplianceInternoDTO complianceInternoDTO = complianceInternoMapper.toDto(complianceInterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComplianceInternoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(complianceInternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ComplianceInterno in the database
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComplianceInterno() throws Exception {
        // Initialize the database
        complianceInternoRepository.saveAndFlush(complianceInterno);

        int databaseSizeBeforeDelete = complianceInternoRepository.findAll().size();

        // Delete the complianceInterno
        restComplianceInternoMockMvc
            .perform(delete(ENTITY_API_URL_ID, complianceInterno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ComplianceInterno> complianceInternoList = complianceInternoRepository.findAll();
        assertThat(complianceInternoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
