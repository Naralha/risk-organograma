package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.entity.FornecedorExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoMapper;
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
 * Integration tests for the {@link FornecedorExternoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FornecedorExternoResourceIT {

    private static final String DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_FORNECEDOR_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fornecedor-externos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FornecedorExternoRepository fornecedorExternoRepository;

    @Autowired
    private FornecedorExternoMapper fornecedorExternoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorExternoMockMvc;

    private FornecedorExterno fornecedorExterno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorExterno createEntity(EntityManager em) {
        FornecedorExterno fornecedorExterno = new FornecedorExterno()
            .idnVarFornecedorExterno(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return fornecedorExterno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorExterno createUpdatedEntity(EntityManager em) {
        FornecedorExterno fornecedorExterno = new FornecedorExterno()
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return fornecedorExterno;
    }

    @BeforeEach
    public void initTest() {
        fornecedorExterno = createEntity(em);
    }

    @Test
    @Transactional
    void createFornecedorExterno() throws Exception {
        int databaseSizeBeforeCreate = fornecedorExternoRepository.findAll().size();
        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);
        restFornecedorExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeCreate + 1);
        FornecedorExterno testFornecedorExterno = fornecedorExternoList.get(fornecedorExternoList.size() - 1);
        assertThat(testFornecedorExterno.getIdnVarFornecedorExterno()).isEqualTo(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExterno.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testFornecedorExterno.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
        assertThat(testFornecedorExterno.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testFornecedorExterno.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createFornecedorExternoWithExistingId() throws Exception {
        // Create the FornecedorExterno with an existing ID
        fornecedorExterno.setId(1L);
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        int databaseSizeBeforeCreate = fornecedorExternoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarFornecedorExternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoRepository.findAll().size();
        // set the field null
        fornecedorExterno.setIdnVarFornecedorExterno(null);

        // Create the FornecedorExterno, which fails.
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        restFornecedorExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoRepository.findAll().size();
        // set the field null
        fornecedorExterno.setnVarNome(null);

        // Create the FornecedorExterno, which fails.
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        restFornecedorExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoRepository.findAll().size();
        // set the field null
        fornecedorExterno.setIdnVarEmpresa(null);

        // Create the FornecedorExterno, which fails.
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        restFornecedorExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoRepository.findAll().size();
        // set the field null
        fornecedorExterno.setIdnvarUsuario(null);

        // Create the FornecedorExterno, which fails.
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        restFornecedorExternoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedorExternos() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList
        restFornecedorExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFornecedorExterno").value(hasItem(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getFornecedorExterno() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get the fornecedorExterno
        restFornecedorExternoMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedorExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedorExterno.getId().intValue()))
            .andExpect(jsonPath("$.idnVarFornecedorExterno").value(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getFornecedorExternosByIdFiltering() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        Long id = fornecedorExterno.getId();

        defaultFornecedorExternoShouldBeFound("id.equals=" + id);
        defaultFornecedorExternoShouldNotBeFound("id.notEquals=" + id);

        defaultFornecedorExternoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFornecedorExternoShouldNotBeFound("id.greaterThan=" + id);

        defaultFornecedorExternoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFornecedorExternoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarFornecedorExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno equals to DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldBeFound("idnVarFornecedorExterno.equals=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno equals to UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldNotBeFound("idnVarFornecedorExterno.equals=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarFornecedorExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno not equals to DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldNotBeFound("idnVarFornecedorExterno.notEquals=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno not equals to UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldBeFound("idnVarFornecedorExterno.notEquals=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarFornecedorExternoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno in DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO or UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldBeFound(
            "idnVarFornecedorExterno.in=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO + "," + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        );

        // Get all the fornecedorExternoList where idnVarFornecedorExterno equals to UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldNotBeFound("idnVarFornecedorExterno.in=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarFornecedorExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno is not null
        defaultFornecedorExternoShouldBeFound("idnVarFornecedorExterno.specified=true");

        // Get all the fornecedorExternoList where idnVarFornecedorExterno is null
        defaultFornecedorExternoShouldNotBeFound("idnVarFornecedorExterno.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarFornecedorExternoContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno contains DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldBeFound("idnVarFornecedorExterno.contains=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno contains UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldNotBeFound("idnVarFornecedorExterno.contains=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarFornecedorExternoNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno does not contain DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldNotBeFound("idnVarFornecedorExterno.doesNotContain=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoList where idnVarFornecedorExterno does not contain UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoShouldBeFound("idnVarFornecedorExterno.doesNotContain=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultFornecedorExternoShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the fornecedorExternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultFornecedorExternoShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultFornecedorExternoShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the fornecedorExternoList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultFornecedorExternoShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultFornecedorExternoShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the fornecedorExternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultFornecedorExternoShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarNome is not null
        defaultFornecedorExternoShouldBeFound("nVarNome.specified=true");

        // Get all the fornecedorExternoList where nVarNome is null
        defaultFornecedorExternoShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultFornecedorExternoShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the fornecedorExternoList where nVarNome contains UPDATED_N_VAR_NOME
        defaultFornecedorExternoShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultFornecedorExternoShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the fornecedorExternoList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultFornecedorExternoShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the fornecedorExternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the fornecedorExternoList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the fornecedorExternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarDescricao is not null
        defaultFornecedorExternoShouldBeFound("nVarDescricao.specified=true");

        // Get all the fornecedorExternoList where nVarDescricao is null
        defaultFornecedorExternoShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the fornecedorExternoList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the fornecedorExternoList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultFornecedorExternoShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the fornecedorExternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the fornecedorExternoList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the fornecedorExternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarEmpresa is not null
        defaultFornecedorExternoShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the fornecedorExternoList where idnVarEmpresa is null
        defaultFornecedorExternoShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the fornecedorExternoList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the fornecedorExternoList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultFornecedorExternoShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the fornecedorExternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnvarUsuario is not null
        defaultFornecedorExternoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the fornecedorExternoList where idnvarUsuario is null
        defaultFornecedorExternoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        // Get all the fornecedorExternoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByFornecedorExternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);
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
        fornecedorExterno.addFornecedorExternoProcesso(fornecedorExternoProcesso);
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);
        Long fornecedorExternoProcessoId = fornecedorExternoProcesso.getId();

        // Get all the fornecedorExternoList where fornecedorExternoProcesso equals to fornecedorExternoProcessoId
        defaultFornecedorExternoShouldBeFound("fornecedorExternoProcessoId.equals=" + fornecedorExternoProcessoId);

        // Get all the fornecedorExternoList where fornecedorExternoProcesso equals to (fornecedorExternoProcessoId + 1)
        defaultFornecedorExternoShouldNotBeFound("fornecedorExternoProcessoId.equals=" + (fornecedorExternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);
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
        fornecedorExterno.setEmpresa(empresa);
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);
        Long empresaId = empresa.getId();

        // Get all the fornecedorExternoList where empresa equals to empresaId
        defaultFornecedorExternoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the fornecedorExternoList where empresa equals to (empresaId + 1)
        defaultFornecedorExternoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorExternosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);
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
        fornecedorExterno.setUsuario(usuario);
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);
        Long usuarioId = usuario.getId();

        // Get all the fornecedorExternoList where usuario equals to usuarioId
        defaultFornecedorExternoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the fornecedorExternoList where usuario equals to (usuarioId + 1)
        defaultFornecedorExternoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFornecedorExternoShouldBeFound(String filter) throws Exception {
        restFornecedorExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFornecedorExterno").value(hasItem(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restFornecedorExternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFornecedorExternoShouldNotBeFound(String filter) throws Exception {
        restFornecedorExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFornecedorExternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFornecedorExterno() throws Exception {
        // Get the fornecedorExterno
        restFornecedorExternoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFornecedorExterno() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();

        // Update the fornecedorExterno
        FornecedorExterno updatedFornecedorExterno = fornecedorExternoRepository.findById(fornecedorExterno.getId()).get();
        // Disconnect from session so that the updates on updatedFornecedorExterno are not directly saved in db
        em.detach(updatedFornecedorExterno);
        updatedFornecedorExterno
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(updatedFornecedorExterno);

        restFornecedorExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorExternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorExterno testFornecedorExterno = fornecedorExternoList.get(fornecedorExternoList.size() - 1);
        assertThat(testFornecedorExterno.getIdnVarFornecedorExterno()).isEqualTo(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testFornecedorExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testFornecedorExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testFornecedorExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingFornecedorExterno() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();
        fornecedorExterno.setId(count.incrementAndGet());

        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorExternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedorExterno() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();
        fornecedorExterno.setId(count.incrementAndGet());

        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedorExterno() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();
        fornecedorExterno.setId(count.incrementAndGet());

        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorExternoWithPatch() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();

        // Update the fornecedorExterno using partial update
        FornecedorExterno partialUpdatedFornecedorExterno = new FornecedorExterno();
        partialUpdatedFornecedorExterno.setId(fornecedorExterno.getId());

        partialUpdatedFornecedorExterno
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFornecedorExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorExterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedorExterno))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorExterno testFornecedorExterno = fornecedorExternoList.get(fornecedorExternoList.size() - 1);
        assertThat(testFornecedorExterno.getIdnVarFornecedorExterno()).isEqualTo(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExterno.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testFornecedorExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testFornecedorExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testFornecedorExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateFornecedorExternoWithPatch() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();

        // Update the fornecedorExterno using partial update
        FornecedorExterno partialUpdatedFornecedorExterno = new FornecedorExterno();
        partialUpdatedFornecedorExterno.setId(fornecedorExterno.getId());

        partialUpdatedFornecedorExterno
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFornecedorExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorExterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedorExterno))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorExterno testFornecedorExterno = fornecedorExternoList.get(fornecedorExternoList.size() - 1);
        assertThat(testFornecedorExterno.getIdnVarFornecedorExterno()).isEqualTo(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testFornecedorExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testFornecedorExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testFornecedorExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingFornecedorExterno() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();
        fornecedorExterno.setId(count.incrementAndGet());

        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorExternoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedorExterno() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();
        fornecedorExterno.setId(count.incrementAndGet());

        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedorExterno() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoRepository.findAll().size();
        fornecedorExterno.setId(count.incrementAndGet());

        // Create the FornecedorExterno
        FornecedorExternoDTO fornecedorExternoDTO = fornecedorExternoMapper.toDto(fornecedorExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorExterno in the database
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedorExterno() throws Exception {
        // Initialize the database
        fornecedorExternoRepository.saveAndFlush(fornecedorExterno);

        int databaseSizeBeforeDelete = fornecedorExternoRepository.findAll().size();

        // Delete the fornecedorExterno
        restFornecedorExternoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedorExterno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FornecedorExterno> fornecedorExternoList = fornecedorExternoRepository.findAll();
        assertThat(fornecedorExternoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
