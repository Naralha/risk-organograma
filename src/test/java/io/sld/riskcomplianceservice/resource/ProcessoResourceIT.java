package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.ClienteInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.ComplianceExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.ComplianceInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.FornecedorExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.FornecedorInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.ProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ProcessoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_VAR_MACRO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_ID_VAR_MACRO_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_OBJETIVO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_OBJETIVO_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_LIMITROFE_INICIAL = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_LIMITROFE_INICIAL = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_LIMITROFE_FINAL = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_LIMITROFE_FINAL = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_PATH_FILE = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_PATH_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_ENTRADAS = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_ENTRADAS = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_SAIDAS = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_SAIDAS = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DT_FIM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT_FIM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProcessoMapper processoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoMockMvc;

    private Processo processo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createEntity(EntityManager em) {
        Processo processo = new Processo()
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idVarMacroProcesso(DEFAULT_ID_VAR_MACRO_PROCESSO)
            .nVarNomeProcesso(DEFAULT_N_VAR_NOME_PROCESSO)
            .nVarObjetivoProcesso(DEFAULT_N_VAR_OBJETIVO_PROCESSO)
            .nVarLimitrofeInicial(DEFAULT_N_VAR_LIMITROFE_INICIAL)
            .nVarLimitrofeFinal(DEFAULT_N_VAR_LIMITROFE_FINAL)
            .nVarPathFile(DEFAULT_N_VAR_PATH_FILE)
            .nVarEntradas(DEFAULT_N_VAR_ENTRADAS)
            .nVarSaidas(DEFAULT_N_VAR_SAIDAS)
            .dtInicio(DEFAULT_DT_INICIO)
            .dtFim(DEFAULT_DT_FIM)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return processo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createUpdatedEntity(EntityManager em) {
        Processo processo = new Processo()
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idVarMacroProcesso(UPDATED_ID_VAR_MACRO_PROCESSO)
            .nVarNomeProcesso(UPDATED_N_VAR_NOME_PROCESSO)
            .nVarObjetivoProcesso(UPDATED_N_VAR_OBJETIVO_PROCESSO)
            .nVarLimitrofeInicial(UPDATED_N_VAR_LIMITROFE_INICIAL)
            .nVarLimitrofeFinal(UPDATED_N_VAR_LIMITROFE_FINAL)
            .nVarPathFile(UPDATED_N_VAR_PATH_FILE)
            .nVarEntradas(UPDATED_N_VAR_ENTRADAS)
            .nVarSaidas(UPDATED_N_VAR_SAIDAS)
            .dtInicio(UPDATED_DT_INICIO)
            .dtFim(UPDATED_DT_FIM)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return processo;
    }

    @BeforeEach
    public void initTest() {
        processo = createEntity(em);
    }

    @Test
    @Transactional
    void createProcesso() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();
        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);
        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testProcesso.getIdVarMacroProcesso()).isEqualTo(DEFAULT_ID_VAR_MACRO_PROCESSO);
        assertThat(testProcesso.getnVarNomeProcesso()).isEqualTo(DEFAULT_N_VAR_NOME_PROCESSO);
        assertThat(testProcesso.getnVarObjetivoProcesso()).isEqualTo(DEFAULT_N_VAR_OBJETIVO_PROCESSO);
        assertThat(testProcesso.getnVarLimitrofeInicial()).isEqualTo(DEFAULT_N_VAR_LIMITROFE_INICIAL);
        assertThat(testProcesso.getnVarLimitrofeFinal()).isEqualTo(DEFAULT_N_VAR_LIMITROFE_FINAL);
        assertThat(testProcesso.getnVarPathFile()).isEqualTo(DEFAULT_N_VAR_PATH_FILE);
        assertThat(testProcesso.getnVarEntradas()).isEqualTo(DEFAULT_N_VAR_ENTRADAS);
        assertThat(testProcesso.getnVarSaidas()).isEqualTo(DEFAULT_N_VAR_SAIDAS);
        assertThat(testProcesso.getDtInicio()).isEqualTo(DEFAULT_DT_INICIO);
        assertThat(testProcesso.getDtFim()).isEqualTo(DEFAULT_DT_FIM);
        assertThat(testProcesso.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createProcessoWithExistingId() throws Exception {
        // Create the Processo with an existing ID
        processo.setId(1L);
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setIdnVarProcesso(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdVarMacroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setIdVarMacroProcesso(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setnVarNomeProcesso(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarObjetivoProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setnVarObjetivoProcesso(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDtInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setDtInicio(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setIdnVarEmpresa(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = processoRepository.findAll().size();
        // set the field null
        processo.setIdnvarUsuario(null);

        // Create the Processo, which fails.
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        restProcessoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcessos() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idVarMacroProcesso").value(hasItem(DEFAULT_ID_VAR_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarNomeProcesso").value(hasItem(DEFAULT_N_VAR_NOME_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarObjetivoProcesso").value(hasItem(DEFAULT_N_VAR_OBJETIVO_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarLimitrofeInicial").value(hasItem(DEFAULT_N_VAR_LIMITROFE_INICIAL)))
            .andExpect(jsonPath("$.[*].nVarLimitrofeFinal").value(hasItem(DEFAULT_N_VAR_LIMITROFE_FINAL)))
            .andExpect(jsonPath("$.[*].nVarPathFile").value(hasItem(DEFAULT_N_VAR_PATH_FILE)))
            .andExpect(jsonPath("$.[*].nVarEntradas").value(hasItem(DEFAULT_N_VAR_ENTRADAS)))
            .andExpect(jsonPath("$.[*].nVarSaidas").value(hasItem(DEFAULT_N_VAR_SAIDAS)))
            .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dtFim").value(hasItem(DEFAULT_DT_FIM.toString())))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get the processo
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, processo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idVarMacroProcesso").value(DEFAULT_ID_VAR_MACRO_PROCESSO))
            .andExpect(jsonPath("$.nVarNomeProcesso").value(DEFAULT_N_VAR_NOME_PROCESSO))
            .andExpect(jsonPath("$.nVarObjetivoProcesso").value(DEFAULT_N_VAR_OBJETIVO_PROCESSO))
            .andExpect(jsonPath("$.nVarLimitrofeInicial").value(DEFAULT_N_VAR_LIMITROFE_INICIAL))
            .andExpect(jsonPath("$.nVarLimitrofeFinal").value(DEFAULT_N_VAR_LIMITROFE_FINAL))
            .andExpect(jsonPath("$.nVarPathFile").value(DEFAULT_N_VAR_PATH_FILE))
            .andExpect(jsonPath("$.nVarEntradas").value(DEFAULT_N_VAR_ENTRADAS))
            .andExpect(jsonPath("$.nVarSaidas").value(DEFAULT_N_VAR_SAIDAS))
            .andExpect(jsonPath("$.dtInicio").value(DEFAULT_DT_INICIO.toString()))
            .andExpect(jsonPath("$.dtFim").value(DEFAULT_DT_FIM.toString()))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getProcessosByIdFiltering() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        Long id = processo.getId();

        defaultProcessoShouldBeFound("id.equals=" + id);
        defaultProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the processoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the processoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the processoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarProcesso is not null
        defaultProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the processoList where idnVarProcesso is null
        defaultProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the processoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the processoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdVarMacroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idVarMacroProcesso equals to DEFAULT_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldBeFound("idVarMacroProcesso.equals=" + DEFAULT_ID_VAR_MACRO_PROCESSO);

        // Get all the processoList where idVarMacroProcesso equals to UPDATED_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldNotBeFound("idVarMacroProcesso.equals=" + UPDATED_ID_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdVarMacroProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idVarMacroProcesso not equals to DEFAULT_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldNotBeFound("idVarMacroProcesso.notEquals=" + DEFAULT_ID_VAR_MACRO_PROCESSO);

        // Get all the processoList where idVarMacroProcesso not equals to UPDATED_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldBeFound("idVarMacroProcesso.notEquals=" + UPDATED_ID_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdVarMacroProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idVarMacroProcesso in DEFAULT_ID_VAR_MACRO_PROCESSO or UPDATED_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldBeFound("idVarMacroProcesso.in=" + DEFAULT_ID_VAR_MACRO_PROCESSO + "," + UPDATED_ID_VAR_MACRO_PROCESSO);

        // Get all the processoList where idVarMacroProcesso equals to UPDATED_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldNotBeFound("idVarMacroProcesso.in=" + UPDATED_ID_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdVarMacroProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idVarMacroProcesso is not null
        defaultProcessoShouldBeFound("idVarMacroProcesso.specified=true");

        // Get all the processoList where idVarMacroProcesso is null
        defaultProcessoShouldNotBeFound("idVarMacroProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByIdVarMacroProcessoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idVarMacroProcesso contains DEFAULT_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldBeFound("idVarMacroProcesso.contains=" + DEFAULT_ID_VAR_MACRO_PROCESSO);

        // Get all the processoList where idVarMacroProcesso contains UPDATED_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldNotBeFound("idVarMacroProcesso.contains=" + UPDATED_ID_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdVarMacroProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idVarMacroProcesso does not contain DEFAULT_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldNotBeFound("idVarMacroProcesso.doesNotContain=" + DEFAULT_ID_VAR_MACRO_PROCESSO);

        // Get all the processoList where idVarMacroProcesso does not contain UPDATED_ID_VAR_MACRO_PROCESSO
        defaultProcessoShouldBeFound("idVarMacroProcesso.doesNotContain=" + UPDATED_ID_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarNomeProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarNomeProcesso equals to DEFAULT_N_VAR_NOME_PROCESSO
        defaultProcessoShouldBeFound("nVarNomeProcesso.equals=" + DEFAULT_N_VAR_NOME_PROCESSO);

        // Get all the processoList where nVarNomeProcesso equals to UPDATED_N_VAR_NOME_PROCESSO
        defaultProcessoShouldNotBeFound("nVarNomeProcesso.equals=" + UPDATED_N_VAR_NOME_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarNomeProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarNomeProcesso not equals to DEFAULT_N_VAR_NOME_PROCESSO
        defaultProcessoShouldNotBeFound("nVarNomeProcesso.notEquals=" + DEFAULT_N_VAR_NOME_PROCESSO);

        // Get all the processoList where nVarNomeProcesso not equals to UPDATED_N_VAR_NOME_PROCESSO
        defaultProcessoShouldBeFound("nVarNomeProcesso.notEquals=" + UPDATED_N_VAR_NOME_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarNomeProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarNomeProcesso in DEFAULT_N_VAR_NOME_PROCESSO or UPDATED_N_VAR_NOME_PROCESSO
        defaultProcessoShouldBeFound("nVarNomeProcesso.in=" + DEFAULT_N_VAR_NOME_PROCESSO + "," + UPDATED_N_VAR_NOME_PROCESSO);

        // Get all the processoList where nVarNomeProcesso equals to UPDATED_N_VAR_NOME_PROCESSO
        defaultProcessoShouldNotBeFound("nVarNomeProcesso.in=" + UPDATED_N_VAR_NOME_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarNomeProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarNomeProcesso is not null
        defaultProcessoShouldBeFound("nVarNomeProcesso.specified=true");

        // Get all the processoList where nVarNomeProcesso is null
        defaultProcessoShouldNotBeFound("nVarNomeProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarNomeProcessoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarNomeProcesso contains DEFAULT_N_VAR_NOME_PROCESSO
        defaultProcessoShouldBeFound("nVarNomeProcesso.contains=" + DEFAULT_N_VAR_NOME_PROCESSO);

        // Get all the processoList where nVarNomeProcesso contains UPDATED_N_VAR_NOME_PROCESSO
        defaultProcessoShouldNotBeFound("nVarNomeProcesso.contains=" + UPDATED_N_VAR_NOME_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarNomeProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarNomeProcesso does not contain DEFAULT_N_VAR_NOME_PROCESSO
        defaultProcessoShouldNotBeFound("nVarNomeProcesso.doesNotContain=" + DEFAULT_N_VAR_NOME_PROCESSO);

        // Get all the processoList where nVarNomeProcesso does not contain UPDATED_N_VAR_NOME_PROCESSO
        defaultProcessoShouldBeFound("nVarNomeProcesso.doesNotContain=" + UPDATED_N_VAR_NOME_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarObjetivoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarObjetivoProcesso equals to DEFAULT_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldBeFound("nVarObjetivoProcesso.equals=" + DEFAULT_N_VAR_OBJETIVO_PROCESSO);

        // Get all the processoList where nVarObjetivoProcesso equals to UPDATED_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldNotBeFound("nVarObjetivoProcesso.equals=" + UPDATED_N_VAR_OBJETIVO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarObjetivoProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarObjetivoProcesso not equals to DEFAULT_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldNotBeFound("nVarObjetivoProcesso.notEquals=" + DEFAULT_N_VAR_OBJETIVO_PROCESSO);

        // Get all the processoList where nVarObjetivoProcesso not equals to UPDATED_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldBeFound("nVarObjetivoProcesso.notEquals=" + UPDATED_N_VAR_OBJETIVO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarObjetivoProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarObjetivoProcesso in DEFAULT_N_VAR_OBJETIVO_PROCESSO or UPDATED_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldBeFound("nVarObjetivoProcesso.in=" + DEFAULT_N_VAR_OBJETIVO_PROCESSO + "," + UPDATED_N_VAR_OBJETIVO_PROCESSO);

        // Get all the processoList where nVarObjetivoProcesso equals to UPDATED_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldNotBeFound("nVarObjetivoProcesso.in=" + UPDATED_N_VAR_OBJETIVO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarObjetivoProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarObjetivoProcesso is not null
        defaultProcessoShouldBeFound("nVarObjetivoProcesso.specified=true");

        // Get all the processoList where nVarObjetivoProcesso is null
        defaultProcessoShouldNotBeFound("nVarObjetivoProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarObjetivoProcessoContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarObjetivoProcesso contains DEFAULT_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldBeFound("nVarObjetivoProcesso.contains=" + DEFAULT_N_VAR_OBJETIVO_PROCESSO);

        // Get all the processoList where nVarObjetivoProcesso contains UPDATED_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldNotBeFound("nVarObjetivoProcesso.contains=" + UPDATED_N_VAR_OBJETIVO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarObjetivoProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarObjetivoProcesso does not contain DEFAULT_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldNotBeFound("nVarObjetivoProcesso.doesNotContain=" + DEFAULT_N_VAR_OBJETIVO_PROCESSO);

        // Get all the processoList where nVarObjetivoProcesso does not contain UPDATED_N_VAR_OBJETIVO_PROCESSO
        defaultProcessoShouldBeFound("nVarObjetivoProcesso.doesNotContain=" + UPDATED_N_VAR_OBJETIVO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeInicialIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeInicial equals to DEFAULT_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldBeFound("nVarLimitrofeInicial.equals=" + DEFAULT_N_VAR_LIMITROFE_INICIAL);

        // Get all the processoList where nVarLimitrofeInicial equals to UPDATED_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeInicial.equals=" + UPDATED_N_VAR_LIMITROFE_INICIAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeInicialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeInicial not equals to DEFAULT_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeInicial.notEquals=" + DEFAULT_N_VAR_LIMITROFE_INICIAL);

        // Get all the processoList where nVarLimitrofeInicial not equals to UPDATED_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldBeFound("nVarLimitrofeInicial.notEquals=" + UPDATED_N_VAR_LIMITROFE_INICIAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeInicialIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeInicial in DEFAULT_N_VAR_LIMITROFE_INICIAL or UPDATED_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldBeFound("nVarLimitrofeInicial.in=" + DEFAULT_N_VAR_LIMITROFE_INICIAL + "," + UPDATED_N_VAR_LIMITROFE_INICIAL);

        // Get all the processoList where nVarLimitrofeInicial equals to UPDATED_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeInicial.in=" + UPDATED_N_VAR_LIMITROFE_INICIAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeInicialIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeInicial is not null
        defaultProcessoShouldBeFound("nVarLimitrofeInicial.specified=true");

        // Get all the processoList where nVarLimitrofeInicial is null
        defaultProcessoShouldNotBeFound("nVarLimitrofeInicial.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeInicialContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeInicial contains DEFAULT_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldBeFound("nVarLimitrofeInicial.contains=" + DEFAULT_N_VAR_LIMITROFE_INICIAL);

        // Get all the processoList where nVarLimitrofeInicial contains UPDATED_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeInicial.contains=" + UPDATED_N_VAR_LIMITROFE_INICIAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeInicialNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeInicial does not contain DEFAULT_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeInicial.doesNotContain=" + DEFAULT_N_VAR_LIMITROFE_INICIAL);

        // Get all the processoList where nVarLimitrofeInicial does not contain UPDATED_N_VAR_LIMITROFE_INICIAL
        defaultProcessoShouldBeFound("nVarLimitrofeInicial.doesNotContain=" + UPDATED_N_VAR_LIMITROFE_INICIAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeFinalIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeFinal equals to DEFAULT_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldBeFound("nVarLimitrofeFinal.equals=" + DEFAULT_N_VAR_LIMITROFE_FINAL);

        // Get all the processoList where nVarLimitrofeFinal equals to UPDATED_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeFinal.equals=" + UPDATED_N_VAR_LIMITROFE_FINAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeFinalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeFinal not equals to DEFAULT_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeFinal.notEquals=" + DEFAULT_N_VAR_LIMITROFE_FINAL);

        // Get all the processoList where nVarLimitrofeFinal not equals to UPDATED_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldBeFound("nVarLimitrofeFinal.notEquals=" + UPDATED_N_VAR_LIMITROFE_FINAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeFinalIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeFinal in DEFAULT_N_VAR_LIMITROFE_FINAL or UPDATED_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldBeFound("nVarLimitrofeFinal.in=" + DEFAULT_N_VAR_LIMITROFE_FINAL + "," + UPDATED_N_VAR_LIMITROFE_FINAL);

        // Get all the processoList where nVarLimitrofeFinal equals to UPDATED_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeFinal.in=" + UPDATED_N_VAR_LIMITROFE_FINAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeFinalIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeFinal is not null
        defaultProcessoShouldBeFound("nVarLimitrofeFinal.specified=true");

        // Get all the processoList where nVarLimitrofeFinal is null
        defaultProcessoShouldNotBeFound("nVarLimitrofeFinal.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeFinalContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeFinal contains DEFAULT_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldBeFound("nVarLimitrofeFinal.contains=" + DEFAULT_N_VAR_LIMITROFE_FINAL);

        // Get all the processoList where nVarLimitrofeFinal contains UPDATED_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeFinal.contains=" + UPDATED_N_VAR_LIMITROFE_FINAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarLimitrofeFinalNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarLimitrofeFinal does not contain DEFAULT_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldNotBeFound("nVarLimitrofeFinal.doesNotContain=" + DEFAULT_N_VAR_LIMITROFE_FINAL);

        // Get all the processoList where nVarLimitrofeFinal does not contain UPDATED_N_VAR_LIMITROFE_FINAL
        defaultProcessoShouldBeFound("nVarLimitrofeFinal.doesNotContain=" + UPDATED_N_VAR_LIMITROFE_FINAL);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarPathFileIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarPathFile equals to DEFAULT_N_VAR_PATH_FILE
        defaultProcessoShouldBeFound("nVarPathFile.equals=" + DEFAULT_N_VAR_PATH_FILE);

        // Get all the processoList where nVarPathFile equals to UPDATED_N_VAR_PATH_FILE
        defaultProcessoShouldNotBeFound("nVarPathFile.equals=" + UPDATED_N_VAR_PATH_FILE);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarPathFileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarPathFile not equals to DEFAULT_N_VAR_PATH_FILE
        defaultProcessoShouldNotBeFound("nVarPathFile.notEquals=" + DEFAULT_N_VAR_PATH_FILE);

        // Get all the processoList where nVarPathFile not equals to UPDATED_N_VAR_PATH_FILE
        defaultProcessoShouldBeFound("nVarPathFile.notEquals=" + UPDATED_N_VAR_PATH_FILE);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarPathFileIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarPathFile in DEFAULT_N_VAR_PATH_FILE or UPDATED_N_VAR_PATH_FILE
        defaultProcessoShouldBeFound("nVarPathFile.in=" + DEFAULT_N_VAR_PATH_FILE + "," + UPDATED_N_VAR_PATH_FILE);

        // Get all the processoList where nVarPathFile equals to UPDATED_N_VAR_PATH_FILE
        defaultProcessoShouldNotBeFound("nVarPathFile.in=" + UPDATED_N_VAR_PATH_FILE);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarPathFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarPathFile is not null
        defaultProcessoShouldBeFound("nVarPathFile.specified=true");

        // Get all the processoList where nVarPathFile is null
        defaultProcessoShouldNotBeFound("nVarPathFile.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarPathFileContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarPathFile contains DEFAULT_N_VAR_PATH_FILE
        defaultProcessoShouldBeFound("nVarPathFile.contains=" + DEFAULT_N_VAR_PATH_FILE);

        // Get all the processoList where nVarPathFile contains UPDATED_N_VAR_PATH_FILE
        defaultProcessoShouldNotBeFound("nVarPathFile.contains=" + UPDATED_N_VAR_PATH_FILE);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarPathFileNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarPathFile does not contain DEFAULT_N_VAR_PATH_FILE
        defaultProcessoShouldNotBeFound("nVarPathFile.doesNotContain=" + DEFAULT_N_VAR_PATH_FILE);

        // Get all the processoList where nVarPathFile does not contain UPDATED_N_VAR_PATH_FILE
        defaultProcessoShouldBeFound("nVarPathFile.doesNotContain=" + UPDATED_N_VAR_PATH_FILE);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarEntradasIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarEntradas equals to DEFAULT_N_VAR_ENTRADAS
        defaultProcessoShouldBeFound("nVarEntradas.equals=" + DEFAULT_N_VAR_ENTRADAS);

        // Get all the processoList where nVarEntradas equals to UPDATED_N_VAR_ENTRADAS
        defaultProcessoShouldNotBeFound("nVarEntradas.equals=" + UPDATED_N_VAR_ENTRADAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarEntradasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarEntradas not equals to DEFAULT_N_VAR_ENTRADAS
        defaultProcessoShouldNotBeFound("nVarEntradas.notEquals=" + DEFAULT_N_VAR_ENTRADAS);

        // Get all the processoList where nVarEntradas not equals to UPDATED_N_VAR_ENTRADAS
        defaultProcessoShouldBeFound("nVarEntradas.notEquals=" + UPDATED_N_VAR_ENTRADAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarEntradasIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarEntradas in DEFAULT_N_VAR_ENTRADAS or UPDATED_N_VAR_ENTRADAS
        defaultProcessoShouldBeFound("nVarEntradas.in=" + DEFAULT_N_VAR_ENTRADAS + "," + UPDATED_N_VAR_ENTRADAS);

        // Get all the processoList where nVarEntradas equals to UPDATED_N_VAR_ENTRADAS
        defaultProcessoShouldNotBeFound("nVarEntradas.in=" + UPDATED_N_VAR_ENTRADAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarEntradasIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarEntradas is not null
        defaultProcessoShouldBeFound("nVarEntradas.specified=true");

        // Get all the processoList where nVarEntradas is null
        defaultProcessoShouldNotBeFound("nVarEntradas.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarEntradasContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarEntradas contains DEFAULT_N_VAR_ENTRADAS
        defaultProcessoShouldBeFound("nVarEntradas.contains=" + DEFAULT_N_VAR_ENTRADAS);

        // Get all the processoList where nVarEntradas contains UPDATED_N_VAR_ENTRADAS
        defaultProcessoShouldNotBeFound("nVarEntradas.contains=" + UPDATED_N_VAR_ENTRADAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarEntradasNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarEntradas does not contain DEFAULT_N_VAR_ENTRADAS
        defaultProcessoShouldNotBeFound("nVarEntradas.doesNotContain=" + DEFAULT_N_VAR_ENTRADAS);

        // Get all the processoList where nVarEntradas does not contain UPDATED_N_VAR_ENTRADAS
        defaultProcessoShouldBeFound("nVarEntradas.doesNotContain=" + UPDATED_N_VAR_ENTRADAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarSaidasIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarSaidas equals to DEFAULT_N_VAR_SAIDAS
        defaultProcessoShouldBeFound("nVarSaidas.equals=" + DEFAULT_N_VAR_SAIDAS);

        // Get all the processoList where nVarSaidas equals to UPDATED_N_VAR_SAIDAS
        defaultProcessoShouldNotBeFound("nVarSaidas.equals=" + UPDATED_N_VAR_SAIDAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarSaidasIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarSaidas not equals to DEFAULT_N_VAR_SAIDAS
        defaultProcessoShouldNotBeFound("nVarSaidas.notEquals=" + DEFAULT_N_VAR_SAIDAS);

        // Get all the processoList where nVarSaidas not equals to UPDATED_N_VAR_SAIDAS
        defaultProcessoShouldBeFound("nVarSaidas.notEquals=" + UPDATED_N_VAR_SAIDAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarSaidasIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarSaidas in DEFAULT_N_VAR_SAIDAS or UPDATED_N_VAR_SAIDAS
        defaultProcessoShouldBeFound("nVarSaidas.in=" + DEFAULT_N_VAR_SAIDAS + "," + UPDATED_N_VAR_SAIDAS);

        // Get all the processoList where nVarSaidas equals to UPDATED_N_VAR_SAIDAS
        defaultProcessoShouldNotBeFound("nVarSaidas.in=" + UPDATED_N_VAR_SAIDAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarSaidasIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarSaidas is not null
        defaultProcessoShouldBeFound("nVarSaidas.specified=true");

        // Get all the processoList where nVarSaidas is null
        defaultProcessoShouldNotBeFound("nVarSaidas.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosBynVarSaidasContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarSaidas contains DEFAULT_N_VAR_SAIDAS
        defaultProcessoShouldBeFound("nVarSaidas.contains=" + DEFAULT_N_VAR_SAIDAS);

        // Get all the processoList where nVarSaidas contains UPDATED_N_VAR_SAIDAS
        defaultProcessoShouldNotBeFound("nVarSaidas.contains=" + UPDATED_N_VAR_SAIDAS);
    }

    @Test
    @Transactional
    void getAllProcessosBynVarSaidasNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where nVarSaidas does not contain DEFAULT_N_VAR_SAIDAS
        defaultProcessoShouldNotBeFound("nVarSaidas.doesNotContain=" + DEFAULT_N_VAR_SAIDAS);

        // Get all the processoList where nVarSaidas does not contain UPDATED_N_VAR_SAIDAS
        defaultProcessoShouldBeFound("nVarSaidas.doesNotContain=" + UPDATED_N_VAR_SAIDAS);
    }

    @Test
    @Transactional
    void getAllProcessosByDtInicioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtInicio equals to DEFAULT_DT_INICIO
        defaultProcessoShouldBeFound("dtInicio.equals=" + DEFAULT_DT_INICIO);

        // Get all the processoList where dtInicio equals to UPDATED_DT_INICIO
        defaultProcessoShouldNotBeFound("dtInicio.equals=" + UPDATED_DT_INICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByDtInicioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtInicio not equals to DEFAULT_DT_INICIO
        defaultProcessoShouldNotBeFound("dtInicio.notEquals=" + DEFAULT_DT_INICIO);

        // Get all the processoList where dtInicio not equals to UPDATED_DT_INICIO
        defaultProcessoShouldBeFound("dtInicio.notEquals=" + UPDATED_DT_INICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByDtInicioIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtInicio in DEFAULT_DT_INICIO or UPDATED_DT_INICIO
        defaultProcessoShouldBeFound("dtInicio.in=" + DEFAULT_DT_INICIO + "," + UPDATED_DT_INICIO);

        // Get all the processoList where dtInicio equals to UPDATED_DT_INICIO
        defaultProcessoShouldNotBeFound("dtInicio.in=" + UPDATED_DT_INICIO);
    }

    @Test
    @Transactional
    void getAllProcessosByDtInicioIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtInicio is not null
        defaultProcessoShouldBeFound("dtInicio.specified=true");

        // Get all the processoList where dtInicio is null
        defaultProcessoShouldNotBeFound("dtInicio.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByDtFimIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtFim equals to DEFAULT_DT_FIM
        defaultProcessoShouldBeFound("dtFim.equals=" + DEFAULT_DT_FIM);

        // Get all the processoList where dtFim equals to UPDATED_DT_FIM
        defaultProcessoShouldNotBeFound("dtFim.equals=" + UPDATED_DT_FIM);
    }

    @Test
    @Transactional
    void getAllProcessosByDtFimIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtFim not equals to DEFAULT_DT_FIM
        defaultProcessoShouldNotBeFound("dtFim.notEquals=" + DEFAULT_DT_FIM);

        // Get all the processoList where dtFim not equals to UPDATED_DT_FIM
        defaultProcessoShouldBeFound("dtFim.notEquals=" + UPDATED_DT_FIM);
    }

    @Test
    @Transactional
    void getAllProcessosByDtFimIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtFim in DEFAULT_DT_FIM or UPDATED_DT_FIM
        defaultProcessoShouldBeFound("dtFim.in=" + DEFAULT_DT_FIM + "," + UPDATED_DT_FIM);

        // Get all the processoList where dtFim equals to UPDATED_DT_FIM
        defaultProcessoShouldNotBeFound("dtFim.in=" + UPDATED_DT_FIM);
    }

    @Test
    @Transactional
    void getAllProcessosByDtFimIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where dtFim is not null
        defaultProcessoShouldBeFound("dtFim.specified=true");

        // Get all the processoList where dtFim is null
        defaultProcessoShouldNotBeFound("dtFim.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultProcessoShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the processoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultProcessoShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultProcessoShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the processoList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultProcessoShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultProcessoShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the processoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultProcessoShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarEmpresa is not null
        defaultProcessoShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the processoList where idnVarEmpresa is null
        defaultProcessoShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultProcessoShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the processoList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultProcessoShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultProcessoShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the processoList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultProcessoShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the processoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the processoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the processoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnvarUsuario is not null
        defaultProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the processoList where idnvarUsuario is null
        defaultProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the processoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the processoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllProcessosByClienteExternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ClienteExternoProcesso clienteExternoProcesso;
        if (TestUtil.findAll(em, ClienteExternoProcesso.class).isEmpty()) {
            clienteExternoProcesso = ClienteExternoProcessoResourceIT.createEntity(em);
            em.persist(clienteExternoProcesso);
            em.flush();
        } else {
            clienteExternoProcesso = TestUtil.findAll(em, ClienteExternoProcesso.class).get(0);
        }
        em.persist(clienteExternoProcesso);
        em.flush();
        processo.addClienteExternoProcesso(clienteExternoProcesso);
        processoRepository.saveAndFlush(processo);
        Long clienteExternoProcessoId = clienteExternoProcesso.getId();

        // Get all the processoList where clienteExternoProcesso equals to clienteExternoProcessoId
        defaultProcessoShouldBeFound("clienteExternoProcessoId.equals=" + clienteExternoProcessoId);

        // Get all the processoList where clienteExternoProcesso equals to (clienteExternoProcessoId + 1)
        defaultProcessoShouldNotBeFound("clienteExternoProcessoId.equals=" + (clienteExternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByComplianceExternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
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
        processo.addComplianceExternoProcesso(complianceExternoProcesso);
        processoRepository.saveAndFlush(processo);
        Long complianceExternoProcessoId = complianceExternoProcesso.getId();

        // Get all the processoList where complianceExternoProcesso equals to complianceExternoProcessoId
        defaultProcessoShouldBeFound("complianceExternoProcessoId.equals=" + complianceExternoProcessoId);

        // Get all the processoList where complianceExternoProcesso equals to (complianceExternoProcessoId + 1)
        defaultProcessoShouldNotBeFound("complianceExternoProcessoId.equals=" + (complianceExternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByFornecedorExternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        FornecedorExternoProcesso fornecedorExternoProcesso;
        if (TestUtil.findAll(em, FornecedorExternoProcesso.class).isEmpty()) {
            fornecedorExternoProcesso = FornecedorExternoProcessoResourceIT.createEntity(em);
            em.persist(fornecedorExternoProcesso);
            em.flush();
        } else {
            fornecedorExternoProcesso = TestUtil.findAll(em, FornecedorExternoProcesso.class).get(0);
        }
        em.persist(fornecedorExternoProcesso);
        em.flush();
        processo.addFornecedorExternoProcesso(fornecedorExternoProcesso);
        processoRepository.saveAndFlush(processo);
        Long fornecedorExternoProcessoId = fornecedorExternoProcesso.getId();

        // Get all the processoList where fornecedorExternoProcesso equals to fornecedorExternoProcessoId
        defaultProcessoShouldBeFound("fornecedorExternoProcessoId.equals=" + fornecedorExternoProcessoId);

        // Get all the processoList where fornecedorExternoProcesso equals to (fornecedorExternoProcessoId + 1)
        defaultProcessoShouldNotBeFound("fornecedorExternoProcessoId.equals=" + (fornecedorExternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByClienteInternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        ClienteInternoProcesso clienteInternoProcesso;
        if (TestUtil.findAll(em, ClienteInternoProcesso.class).isEmpty()) {
            clienteInternoProcesso = ClienteInternoProcessoResourceIT.createEntity(em);
            em.persist(clienteInternoProcesso);
            em.flush();
        } else {
            clienteInternoProcesso = TestUtil.findAll(em, ClienteInternoProcesso.class).get(0);
        }
        em.persist(clienteInternoProcesso);
        em.flush();
        processo.addClienteInternoProcesso(clienteInternoProcesso);
        processoRepository.saveAndFlush(processo);
        Long clienteInternoProcessoId = clienteInternoProcesso.getId();

        // Get all the processoList where clienteInternoProcesso equals to clienteInternoProcessoId
        defaultProcessoShouldBeFound("clienteInternoProcessoId.equals=" + clienteInternoProcessoId);

        // Get all the processoList where clienteInternoProcesso equals to (clienteInternoProcessoId + 1)
        defaultProcessoShouldNotBeFound("clienteInternoProcessoId.equals=" + (clienteInternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByComplianceInternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
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
        processo.addComplianceInternoProcesso(complianceInternoProcesso);
        processoRepository.saveAndFlush(processo);
        Long complianceInternoProcessoId = complianceInternoProcesso.getId();

        // Get all the processoList where complianceInternoProcesso equals to complianceInternoProcessoId
        defaultProcessoShouldBeFound("complianceInternoProcessoId.equals=" + complianceInternoProcessoId);

        // Get all the processoList where complianceInternoProcesso equals to (complianceInternoProcessoId + 1)
        defaultProcessoShouldNotBeFound("complianceInternoProcessoId.equals=" + (complianceInternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByFornecedorInternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
        FornecedorInternoProcesso fornecedorInternoProcesso;
        if (TestUtil.findAll(em, FornecedorInternoProcesso.class).isEmpty()) {
            fornecedorInternoProcesso = FornecedorInternoProcessoResourceIT.createEntity(em);
            em.persist(fornecedorInternoProcesso);
            em.flush();
        } else {
            fornecedorInternoProcesso = TestUtil.findAll(em, FornecedorInternoProcesso.class).get(0);
        }
        em.persist(fornecedorInternoProcesso);
        em.flush();
        processo.addFornecedorInternoProcesso(fornecedorInternoProcesso);
        processoRepository.saveAndFlush(processo);
        Long fornecedorInternoProcessoId = fornecedorInternoProcesso.getId();

        // Get all the processoList where fornecedorInternoProcesso equals to fornecedorInternoProcessoId
        defaultProcessoShouldBeFound("fornecedorInternoProcessoId.equals=" + fornecedorInternoProcessoId);

        // Get all the processoList where fornecedorInternoProcesso equals to (fornecedorInternoProcessoId + 1)
        defaultProcessoShouldNotBeFound("fornecedorInternoProcessoId.equals=" + (fornecedorInternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
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
        processo.setEmpresa(empresa);
        processoRepository.saveAndFlush(processo);
        Long empresaId = empresa.getId();

        // Get all the processoList where empresa equals to empresaId
        defaultProcessoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the processoList where empresa equals to (empresaId + 1)
        defaultProcessoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);
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
        processo.setUsuario(usuario);
        processoRepository.saveAndFlush(processo);
        Long usuarioId = usuario.getId();

        // Get all the processoList where usuario equals to usuarioId
        defaultProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the processoList where usuario equals to (usuarioId + 1)
        defaultProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProcessoShouldBeFound(String filter) throws Exception {
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idVarMacroProcesso").value(hasItem(DEFAULT_ID_VAR_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarNomeProcesso").value(hasItem(DEFAULT_N_VAR_NOME_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarObjetivoProcesso").value(hasItem(DEFAULT_N_VAR_OBJETIVO_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarLimitrofeInicial").value(hasItem(DEFAULT_N_VAR_LIMITROFE_INICIAL)))
            .andExpect(jsonPath("$.[*].nVarLimitrofeFinal").value(hasItem(DEFAULT_N_VAR_LIMITROFE_FINAL)))
            .andExpect(jsonPath("$.[*].nVarPathFile").value(hasItem(DEFAULT_N_VAR_PATH_FILE)))
            .andExpect(jsonPath("$.[*].nVarEntradas").value(hasItem(DEFAULT_N_VAR_ENTRADAS)))
            .andExpect(jsonPath("$.[*].nVarSaidas").value(hasItem(DEFAULT_N_VAR_SAIDAS)))
            .andExpect(jsonPath("$.[*].dtInicio").value(hasItem(DEFAULT_DT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dtFim").value(hasItem(DEFAULT_DT_FIM.toString())))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProcessoShouldNotBeFound(String filter) throws Exception {
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProcesso() throws Exception {
        // Get the processo
        restProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findById(processo.getId()).get();
        // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
        em.detach(updatedProcesso);
        updatedProcesso
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idVarMacroProcesso(UPDATED_ID_VAR_MACRO_PROCESSO)
            .nVarNomeProcesso(UPDATED_N_VAR_NOME_PROCESSO)
            .nVarObjetivoProcesso(UPDATED_N_VAR_OBJETIVO_PROCESSO)
            .nVarLimitrofeInicial(UPDATED_N_VAR_LIMITROFE_INICIAL)
            .nVarLimitrofeFinal(UPDATED_N_VAR_LIMITROFE_FINAL)
            .nVarPathFile(UPDATED_N_VAR_PATH_FILE)
            .nVarEntradas(UPDATED_N_VAR_ENTRADAS)
            .nVarSaidas(UPDATED_N_VAR_SAIDAS)
            .dtInicio(UPDATED_DT_INICIO)
            .dtFim(UPDATED_DT_FIM)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ProcessoDTO processoDTO = processoMapper.toDto(updatedProcesso);

        restProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testProcesso.getIdVarMacroProcesso()).isEqualTo(UPDATED_ID_VAR_MACRO_PROCESSO);
        assertThat(testProcesso.getnVarNomeProcesso()).isEqualTo(UPDATED_N_VAR_NOME_PROCESSO);
        assertThat(testProcesso.getnVarObjetivoProcesso()).isEqualTo(UPDATED_N_VAR_OBJETIVO_PROCESSO);
        assertThat(testProcesso.getnVarLimitrofeInicial()).isEqualTo(UPDATED_N_VAR_LIMITROFE_INICIAL);
        assertThat(testProcesso.getnVarLimitrofeFinal()).isEqualTo(UPDATED_N_VAR_LIMITROFE_FINAL);
        assertThat(testProcesso.getnVarPathFile()).isEqualTo(UPDATED_N_VAR_PATH_FILE);
        assertThat(testProcesso.getnVarEntradas()).isEqualTo(UPDATED_N_VAR_ENTRADAS);
        assertThat(testProcesso.getnVarSaidas()).isEqualTo(UPDATED_N_VAR_SAIDAS);
        assertThat(testProcesso.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
        assertThat(testProcesso.getDtFim()).isEqualTo(UPDATED_DT_FIM);
        assertThat(testProcesso.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessoWithPatch() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo using partial update
        Processo partialUpdatedProcesso = new Processo();
        partialUpdatedProcesso.setId(processo.getId());

        partialUpdatedProcesso
            .nVarObjetivoProcesso(UPDATED_N_VAR_OBJETIVO_PROCESSO)
            .nVarLimitrofeInicial(UPDATED_N_VAR_LIMITROFE_INICIAL)
            .nVarLimitrofeFinal(UPDATED_N_VAR_LIMITROFE_FINAL)
            .nVarSaidas(UPDATED_N_VAR_SAIDAS)
            .dtInicio(UPDATED_DT_INICIO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesso))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testProcesso.getIdVarMacroProcesso()).isEqualTo(DEFAULT_ID_VAR_MACRO_PROCESSO);
        assertThat(testProcesso.getnVarNomeProcesso()).isEqualTo(DEFAULT_N_VAR_NOME_PROCESSO);
        assertThat(testProcesso.getnVarObjetivoProcesso()).isEqualTo(UPDATED_N_VAR_OBJETIVO_PROCESSO);
        assertThat(testProcesso.getnVarLimitrofeInicial()).isEqualTo(UPDATED_N_VAR_LIMITROFE_INICIAL);
        assertThat(testProcesso.getnVarLimitrofeFinal()).isEqualTo(UPDATED_N_VAR_LIMITROFE_FINAL);
        assertThat(testProcesso.getnVarPathFile()).isEqualTo(DEFAULT_N_VAR_PATH_FILE);
        assertThat(testProcesso.getnVarEntradas()).isEqualTo(DEFAULT_N_VAR_ENTRADAS);
        assertThat(testProcesso.getnVarSaidas()).isEqualTo(UPDATED_N_VAR_SAIDAS);
        assertThat(testProcesso.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
        assertThat(testProcesso.getDtFim()).isEqualTo(DEFAULT_DT_FIM);
        assertThat(testProcesso.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateProcessoWithPatch() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo using partial update
        Processo partialUpdatedProcesso = new Processo();
        partialUpdatedProcesso.setId(processo.getId());

        partialUpdatedProcesso
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idVarMacroProcesso(UPDATED_ID_VAR_MACRO_PROCESSO)
            .nVarNomeProcesso(UPDATED_N_VAR_NOME_PROCESSO)
            .nVarObjetivoProcesso(UPDATED_N_VAR_OBJETIVO_PROCESSO)
            .nVarLimitrofeInicial(UPDATED_N_VAR_LIMITROFE_INICIAL)
            .nVarLimitrofeFinal(UPDATED_N_VAR_LIMITROFE_FINAL)
            .nVarPathFile(UPDATED_N_VAR_PATH_FILE)
            .nVarEntradas(UPDATED_N_VAR_ENTRADAS)
            .nVarSaidas(UPDATED_N_VAR_SAIDAS)
            .dtInicio(UPDATED_DT_INICIO)
            .dtFim(UPDATED_DT_FIM)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesso))
            )
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testProcesso.getIdVarMacroProcesso()).isEqualTo(UPDATED_ID_VAR_MACRO_PROCESSO);
        assertThat(testProcesso.getnVarNomeProcesso()).isEqualTo(UPDATED_N_VAR_NOME_PROCESSO);
        assertThat(testProcesso.getnVarObjetivoProcesso()).isEqualTo(UPDATED_N_VAR_OBJETIVO_PROCESSO);
        assertThat(testProcesso.getnVarLimitrofeInicial()).isEqualTo(UPDATED_N_VAR_LIMITROFE_INICIAL);
        assertThat(testProcesso.getnVarLimitrofeFinal()).isEqualTo(UPDATED_N_VAR_LIMITROFE_FINAL);
        assertThat(testProcesso.getnVarPathFile()).isEqualTo(UPDATED_N_VAR_PATH_FILE);
        assertThat(testProcesso.getnVarEntradas()).isEqualTo(UPDATED_N_VAR_ENTRADAS);
        assertThat(testProcesso.getnVarSaidas()).isEqualTo(UPDATED_N_VAR_SAIDAS);
        assertThat(testProcesso.getDtInicio()).isEqualTo(UPDATED_DT_INICIO);
        assertThat(testProcesso.getDtFim()).isEqualTo(UPDATED_DT_FIM);
        assertThat(testProcesso.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();
        processo.setId(count.incrementAndGet());

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(processoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeDelete = processoRepository.findAll().size();

        // Delete the processo
        restProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, processo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
