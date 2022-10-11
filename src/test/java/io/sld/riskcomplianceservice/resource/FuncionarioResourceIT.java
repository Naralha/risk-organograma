package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.TestUtil;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Funcionario;
import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.FuncionarioRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FuncionarioDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.FuncionarioMapper;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link FuncionarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
//@WithMockUser
class FuncionarioResourceIT {

//    private static final String DEFAULT_IDN_VAR_FUNCIONARIO = "AAAAAAAAAA";
    private static final UUID DEFAULT_IDN_VAR_FUNCIONARIO = UUID.randomUUID();
//    private static final String UPDATED_IDN_VAR_FUNCIONARIO = "BBBBBBBBBB";
    private static final UUID UPDATED_IDN_VAR_FUNCIONARIO = UUID.randomUUID();
    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/funcionarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .idnVarFuncionario(DEFAULT_IDN_VAR_FUNCIONARIO)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarEmail(DEFAULT_N_VAR_EMAIL)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO)
//            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return funcionario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarEmail(UPDATED_N_VAR_EMAIL)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
//            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();
        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);
        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getIdnVarFuncionario()).isEqualTo(DEFAULT_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionario.getNVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testFuncionario.getNVarEmail()).isEqualTo(DEFAULT_N_VAR_EMAIL);
        assertThat(testFuncionario.getNVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
//        assertThat(testFuncionario.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testFuncionario.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createFuncionarioWithExistingId() throws Exception {
        // Create the Funcionario with an existing ID
        funcionario.setId(1L);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarFuncionarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setIdnVarFuncionario(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setNVarNome(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setNVarEmail(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
//        funcionario.setIdnVarEmpresa(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setIdnvarUsuario(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        restFuncionarioMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFuncionario").value(hasItem(DEFAULT_IDN_VAR_FUNCIONARIO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarEmail").value(hasItem(DEFAULT_N_VAR_EMAIL)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.idnVarFuncionario").value(DEFAULT_IDN_VAR_FUNCIONARIO))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarEmail").value(DEFAULT_N_VAR_EMAIL))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getFuncionariosByIdFiltering() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        Long id = funcionario.getId();

        defaultFuncionarioShouldBeFound("id.equals=" + id);
        defaultFuncionarioShouldNotBeFound("id.notEquals=" + id);

        defaultFuncionarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFuncionarioShouldNotBeFound("id.greaterThan=" + id);

        defaultFuncionarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFuncionarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarFuncionario equals to DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldBeFound("idnVarFuncionario.equals=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioList where idnVarFuncionario equals to UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldNotBeFound("idnVarFuncionario.equals=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarFuncionarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarFuncionario not equals to DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldNotBeFound("idnVarFuncionario.notEquals=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioList where idnVarFuncionario not equals to UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldBeFound("idnVarFuncionario.notEquals=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarFuncionarioIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarFuncionario in DEFAULT_IDN_VAR_FUNCIONARIO or UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldBeFound("idnVarFuncionario.in=" + DEFAULT_IDN_VAR_FUNCIONARIO + "," + UPDATED_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioList where idnVarFuncionario equals to UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldNotBeFound("idnVarFuncionario.in=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarFuncionarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarFuncionario is not null
        defaultFuncionarioShouldBeFound("idnVarFuncionario.specified=true");

        // Get all the funcionarioList where idnVarFuncionario is null
        defaultFuncionarioShouldNotBeFound("idnVarFuncionario.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarFuncionarioContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarFuncionario contains DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldBeFound("idnVarFuncionario.contains=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioList where idnVarFuncionario contains UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldNotBeFound("idnVarFuncionario.contains=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarFuncionarioNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarFuncionario does not contain DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldNotBeFound("idnVarFuncionario.doesNotContain=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioList where idnVarFuncionario does not contain UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioShouldBeFound("idnVarFuncionario.doesNotContain=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultFuncionarioShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the funcionarioList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultFuncionarioShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultFuncionarioShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the funcionarioList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultFuncionarioShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultFuncionarioShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the funcionarioList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultFuncionarioShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarNome is not null
        defaultFuncionarioShouldBeFound("nVarNome.specified=true");

        // Get all the funcionarioList where nVarNome is null
        defaultFuncionarioShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultFuncionarioShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the funcionarioList where nVarNome contains UPDATED_N_VAR_NOME
        defaultFuncionarioShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultFuncionarioShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the funcionarioList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultFuncionarioShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarEmail equals to DEFAULT_N_VAR_EMAIL
        defaultFuncionarioShouldBeFound("nVarEmail.equals=" + DEFAULT_N_VAR_EMAIL);

        // Get all the funcionarioList where nVarEmail equals to UPDATED_N_VAR_EMAIL
        defaultFuncionarioShouldNotBeFound("nVarEmail.equals=" + UPDATED_N_VAR_EMAIL);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarEmail not equals to DEFAULT_N_VAR_EMAIL
        defaultFuncionarioShouldNotBeFound("nVarEmail.notEquals=" + DEFAULT_N_VAR_EMAIL);

        // Get all the funcionarioList where nVarEmail not equals to UPDATED_N_VAR_EMAIL
        defaultFuncionarioShouldBeFound("nVarEmail.notEquals=" + UPDATED_N_VAR_EMAIL);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarEmailIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarEmail in DEFAULT_N_VAR_EMAIL or UPDATED_N_VAR_EMAIL
        defaultFuncionarioShouldBeFound("nVarEmail.in=" + DEFAULT_N_VAR_EMAIL + "," + UPDATED_N_VAR_EMAIL);

        // Get all the funcionarioList where nVarEmail equals to UPDATED_N_VAR_EMAIL
        defaultFuncionarioShouldNotBeFound("nVarEmail.in=" + UPDATED_N_VAR_EMAIL);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarEmail is not null
        defaultFuncionarioShouldBeFound("nVarEmail.specified=true");

        // Get all the funcionarioList where nVarEmail is null
        defaultFuncionarioShouldNotBeFound("nVarEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarEmailContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarEmail contains DEFAULT_N_VAR_EMAIL
        defaultFuncionarioShouldBeFound("nVarEmail.contains=" + DEFAULT_N_VAR_EMAIL);

        // Get all the funcionarioList where nVarEmail contains UPDATED_N_VAR_EMAIL
        defaultFuncionarioShouldNotBeFound("nVarEmail.contains=" + UPDATED_N_VAR_EMAIL);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarEmailNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarEmail does not contain DEFAULT_N_VAR_EMAIL
        defaultFuncionarioShouldNotBeFound("nVarEmail.doesNotContain=" + DEFAULT_N_VAR_EMAIL);

        // Get all the funcionarioList where nVarEmail does not contain UPDATED_N_VAR_EMAIL
        defaultFuncionarioShouldBeFound("nVarEmail.doesNotContain=" + UPDATED_N_VAR_EMAIL);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultFuncionarioShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the funcionarioList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultFuncionarioShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultFuncionarioShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the funcionarioList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultFuncionarioShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultFuncionarioShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the funcionarioList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultFuncionarioShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarDescricao is not null
        defaultFuncionarioShouldBeFound("nVarDescricao.specified=true");

        // Get all the funcionarioList where nVarDescricao is null
        defaultFuncionarioShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultFuncionarioShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the funcionarioList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultFuncionarioShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultFuncionarioShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the funcionarioList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultFuncionarioShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultFuncionarioShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the funcionarioList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultFuncionarioShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultFuncionarioShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the funcionarioList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultFuncionarioShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultFuncionarioShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the funcionarioList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultFuncionarioShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarEmpresa is not null
        defaultFuncionarioShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the funcionarioList where idnVarEmpresa is null
        defaultFuncionarioShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultFuncionarioShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the funcionarioList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultFuncionarioShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultFuncionarioShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the funcionarioList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultFuncionarioShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFuncionarioShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultFuncionarioShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultFuncionarioShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the funcionarioList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFuncionarioShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnvarUsuario is not null
        defaultFuncionarioShouldBeFound("idnvarUsuario.specified=true");

        // Get all the funcionarioList where idnvarUsuario is null
        defaultFuncionarioShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultFuncionarioShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultFuncionarioShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionariosByFuncionarioOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);
        FuncionarioOrganograma funcionarioOrganograma;
        if (TestUtil.findAll(em, FuncionarioOrganograma.class).isEmpty()) {
            funcionarioOrganograma = FuncionarioOrganogramaResourceIT.createEntity(em);
            em.persist(funcionarioOrganograma);
            em.flush();
        } else {
            funcionarioOrganograma = TestUtil.findAll(em, FuncionarioOrganograma.class).get(0);
        }
        em.persist(funcionarioOrganograma);
        em.flush();
        funcionario.addFuncionarioOrganograma(funcionarioOrganograma);
        funcionarioRepository.saveAndFlush(funcionario);
        Long funcionarioOrganogramaId = funcionarioOrganograma.getId();

        // Get all the funcionarioList where funcionarioOrganograma equals to funcionarioOrganogramaId
        defaultFuncionarioShouldBeFound("funcionarioOrganogramaId.equals=" + funcionarioOrganogramaId);

        // Get all the funcionarioList where funcionarioOrganograma equals to (funcionarioOrganogramaId + 1)
        defaultFuncionarioShouldNotBeFound("funcionarioOrganogramaId.equals=" + (funcionarioOrganogramaId + 1));
    }

    @Test
    @Transactional
    void getAllFuncionariosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);
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
        funcionario.setEmpresa(empresa);
        funcionarioRepository.saveAndFlush(funcionario);
        Long empresaId = empresa.getId();

        // Get all the funcionarioList where empresa equals to empresaId
        defaultFuncionarioShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the funcionarioList where empresa equals to (empresaId + 1)
        defaultFuncionarioShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllFuncionariosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);
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
        funcionario.setUsuario(usuario);
        funcionarioRepository.saveAndFlush(funcionario);
        Long usuarioId = usuario.getId();

        // Get all the funcionarioList where usuario equals to usuarioId
        defaultFuncionarioShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the funcionarioList where usuario equals to (usuarioId + 1)
        defaultFuncionarioShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuncionarioShouldBeFound(String filter) throws Exception {
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFuncionario").value(hasItem(DEFAULT_IDN_VAR_FUNCIONARIO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarEmail").value(hasItem(DEFAULT_N_VAR_EMAIL)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuncionarioShouldNotBeFound(String filter) throws Exception {
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuncionarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarEmail(UPDATED_N_VAR_EMAIL)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
//            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(updatedFuncionario);

        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioDTO.getIdnVarFuncionario())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getIdnVarFuncionario()).isEqualTo(UPDATED_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionario.getNVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testFuncionario.getNVarEmail()).isEqualTo(UPDATED_N_VAR_EMAIL);
        assertThat(testFuncionario.getNVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
//        assertThat(testFuncionario.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testFuncionario.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioDTO.getIdnVarFuncionario())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

//        partialUpdatedFuncionario.nVarEmail(UPDATED_N_VAR_EMAIL).idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getIdnVarFuncionario()).isEqualTo(DEFAULT_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionario.getNVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testFuncionario.getNVarEmail()).isEqualTo(UPDATED_N_VAR_EMAIL);
        assertThat(testFuncionario.getNVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
//        assertThat(testFuncionario.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testFuncionario.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateFuncionarioWithPatch() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario using partial update
        Funcionario partialUpdatedFuncionario = new Funcionario();
        partialUpdatedFuncionario.setId(funcionario.getId());

        partialUpdatedFuncionario
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarEmail(UPDATED_N_VAR_EMAIL)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
//            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionario))
            )
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getIdnVarFuncionario()).isEqualTo(UPDATED_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionario.getNVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testFuncionario.getNVarEmail()).isEqualTo(UPDATED_N_VAR_EMAIL);
        assertThat(testFuncionario.getNVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
//        assertThat(testFuncionario.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testFuncionario.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionarioDTO.getIdnVarFuncionario())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();
        funcionario.setId(count.incrementAndGet());

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(funcionarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Delete the funcionario
        restFuncionarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
