package io.sld.riskcomplianceservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.ClienteExterno;
import io.sld.riskcomplianceservice.domain.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.repository.ClienteExternoRepository;
import io.sld.riskcomplianceservice.service.criteria.ClienteExternoCriteria;
import io.sld.riskcomplianceservice.service.dto.ClienteExternoDTO;
import io.sld.riskcomplianceservice.service.mapper.ClienteExternoMapper;
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
 * Integration tests for the {@link ClienteExternoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClienteExternoResourceIT {

    private static final String DEFAULT_IDN_VAR_CLIENTE_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_CLIENTE_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cliente-externos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteExternoRepository clienteExternoRepository;

    @Autowired
    private ClienteExternoMapper clienteExternoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteExternoMockMvc;

    private ClienteExterno clienteExterno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteExterno createEntity(EntityManager em) {
        ClienteExterno clienteExterno = new ClienteExterno()
            .idnVarClienteExterno(DEFAULT_IDN_VAR_CLIENTE_EXTERNO)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return clienteExterno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteExterno createUpdatedEntity(EntityManager em) {
        ClienteExterno clienteExterno = new ClienteExterno()
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return clienteExterno;
    }

    @BeforeEach
    public void initTest() {
        clienteExterno = createEntity(em);
    }

    @Test
    @Transactional
    void createClienteExterno() throws Exception {
        int databaseSizeBeforeCreate = clienteExternoRepository.findAll().size();
        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);
        restClienteExternoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteExterno testClienteExterno = clienteExternoList.get(clienteExternoList.size() - 1);
        assertThat(testClienteExterno.getIdnVarClienteExterno()).isEqualTo(DEFAULT_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExterno.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testClienteExterno.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
        assertThat(testClienteExterno.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testClienteExterno.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createClienteExternoWithExistingId() throws Exception {
        // Create the ClienteExterno with an existing ID
        clienteExterno.setId(1L);
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        int databaseSizeBeforeCreate = clienteExternoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteExternoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarClienteExternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoRepository.findAll().size();
        // set the field null
        clienteExterno.setIdnVarClienteExterno(null);

        // Create the ClienteExterno, which fails.
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        restClienteExternoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoRepository.findAll().size();
        // set the field null
        clienteExterno.setnVarNome(null);

        // Create the ClienteExterno, which fails.
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        restClienteExternoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoRepository.findAll().size();
        // set the field null
        clienteExterno.setIdnVarEmpresa(null);

        // Create the ClienteExterno, which fails.
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        restClienteExternoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoRepository.findAll().size();
        // set the field null
        clienteExterno.setIdnvarUsuario(null);

        // Create the ClienteExterno, which fails.
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        restClienteExternoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClienteExternos() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList
        restClienteExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarClienteExterno").value(hasItem(DEFAULT_IDN_VAR_CLIENTE_EXTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getClienteExterno() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get the clienteExterno
        restClienteExternoMockMvc
            .perform(get(ENTITY_API_URL_ID, clienteExterno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clienteExterno.getId().intValue()))
            .andExpect(jsonPath("$.idnVarClienteExterno").value(DEFAULT_IDN_VAR_CLIENTE_EXTERNO))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getClienteExternosByIdFiltering() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        Long id = clienteExterno.getId();

        defaultClienteExternoShouldBeFound("id.equals=" + id);
        defaultClienteExternoShouldNotBeFound("id.notEquals=" + id);

        defaultClienteExternoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteExternoShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteExternoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteExternoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarClienteExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarClienteExterno equals to DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldBeFound("idnVarClienteExterno.equals=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoList where idnVarClienteExterno equals to UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldNotBeFound("idnVarClienteExterno.equals=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarClienteExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarClienteExterno not equals to DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldNotBeFound("idnVarClienteExterno.notEquals=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoList where idnVarClienteExterno not equals to UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldBeFound("idnVarClienteExterno.notEquals=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarClienteExternoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarClienteExterno in DEFAULT_IDN_VAR_CLIENTE_EXTERNO or UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldBeFound(
            "idnVarClienteExterno.in=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO + "," + UPDATED_IDN_VAR_CLIENTE_EXTERNO
        );

        // Get all the clienteExternoList where idnVarClienteExterno equals to UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldNotBeFound("idnVarClienteExterno.in=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarClienteExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarClienteExterno is not null
        defaultClienteExternoShouldBeFound("idnVarClienteExterno.specified=true");

        // Get all the clienteExternoList where idnVarClienteExterno is null
        defaultClienteExternoShouldNotBeFound("idnVarClienteExterno.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarClienteExternoContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarClienteExterno contains DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldBeFound("idnVarClienteExterno.contains=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoList where idnVarClienteExterno contains UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldNotBeFound("idnVarClienteExterno.contains=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarClienteExternoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarClienteExterno does not contain DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldNotBeFound("idnVarClienteExterno.doesNotContain=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoList where idnVarClienteExterno does not contain UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoShouldBeFound("idnVarClienteExterno.doesNotContain=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultClienteExternoShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the clienteExternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultClienteExternoShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultClienteExternoShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the clienteExternoList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultClienteExternoShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultClienteExternoShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the clienteExternoList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultClienteExternoShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarNome is not null
        defaultClienteExternoShouldBeFound("nVarNome.specified=true");

        // Get all the clienteExternoList where nVarNome is null
        defaultClienteExternoShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultClienteExternoShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the clienteExternoList where nVarNome contains UPDATED_N_VAR_NOME
        defaultClienteExternoShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultClienteExternoShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the clienteExternoList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultClienteExternoShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultClienteExternoShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the clienteExternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultClienteExternoShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultClienteExternoShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the clienteExternoList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultClienteExternoShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultClienteExternoShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the clienteExternoList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultClienteExternoShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarDescricao is not null
        defaultClienteExternoShouldBeFound("nVarDescricao.specified=true");

        // Get all the clienteExternoList where nVarDescricao is null
        defaultClienteExternoShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultClienteExternoShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the clienteExternoList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultClienteExternoShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllClienteExternosBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultClienteExternoShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the clienteExternoList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultClienteExternoShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultClienteExternoShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the clienteExternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultClienteExternoShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultClienteExternoShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the clienteExternoList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultClienteExternoShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultClienteExternoShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the clienteExternoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultClienteExternoShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarEmpresa is not null
        defaultClienteExternoShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the clienteExternoList where idnVarEmpresa is null
        defaultClienteExternoShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultClienteExternoShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the clienteExternoList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultClienteExternoShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultClienteExternoShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the clienteExternoList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultClienteExternoShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultClienteExternoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultClienteExternoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultClienteExternoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the clienteExternoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultClienteExternoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnvarUsuario is not null
        defaultClienteExternoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the clienteExternoList where idnvarUsuario is null
        defaultClienteExternoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultClienteExternoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        // Get all the clienteExternoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultClienteExternoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternosByClienteExternoProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);
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
        clienteExterno.addClienteExternoProcesso(clienteExternoProcesso);
        clienteExternoRepository.saveAndFlush(clienteExterno);
        Long clienteExternoProcessoId = clienteExternoProcesso.getId();

        // Get all the clienteExternoList where clienteExternoProcesso equals to clienteExternoProcessoId
        defaultClienteExternoShouldBeFound("clienteExternoProcessoId.equals=" + clienteExternoProcessoId);

        // Get all the clienteExternoList where clienteExternoProcesso equals to (clienteExternoProcessoId + 1)
        defaultClienteExternoShouldNotBeFound("clienteExternoProcessoId.equals=" + (clienteExternoProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllClienteExternosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);
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
        clienteExterno.setEmpresa(empresa);
        clienteExternoRepository.saveAndFlush(clienteExterno);
        Long empresaId = empresa.getId();

        // Get all the clienteExternoList where empresa equals to empresaId
        defaultClienteExternoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the clienteExternoList where empresa equals to (empresaId + 1)
        defaultClienteExternoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllClienteExternosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);
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
        clienteExterno.setUsuario(usuario);
        clienteExternoRepository.saveAndFlush(clienteExterno);
        Long usuarioId = usuario.getId();

        // Get all the clienteExternoList where usuario equals to usuarioId
        defaultClienteExternoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the clienteExternoList where usuario equals to (usuarioId + 1)
        defaultClienteExternoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteExternoShouldBeFound(String filter) throws Exception {
        restClienteExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteExterno.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarClienteExterno").value(hasItem(DEFAULT_IDN_VAR_CLIENTE_EXTERNO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restClienteExternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteExternoShouldNotBeFound(String filter) throws Exception {
        restClienteExternoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteExternoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClienteExterno() throws Exception {
        // Get the clienteExterno
        restClienteExternoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClienteExterno() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();

        // Update the clienteExterno
        ClienteExterno updatedClienteExterno = clienteExternoRepository.findById(clienteExterno.getId()).get();
        // Disconnect from session so that the updates on updatedClienteExterno are not directly saved in db
        em.detach(updatedClienteExterno);
        updatedClienteExterno
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(updatedClienteExterno);

        restClienteExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteExternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
        ClienteExterno testClienteExterno = clienteExternoList.get(clienteExternoList.size() - 1);
        assertThat(testClienteExterno.getIdnVarClienteExterno()).isEqualTo(UPDATED_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testClienteExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testClienteExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testClienteExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingClienteExterno() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();
        clienteExterno.setId(count.incrementAndGet());

        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteExternoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClienteExterno() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();
        clienteExterno.setId(count.incrementAndGet());

        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClienteExterno() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();
        clienteExterno.setId(count.incrementAndGet());

        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteExternoWithPatch() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();

        // Update the clienteExterno using partial update
        ClienteExterno partialUpdatedClienteExterno = new ClienteExterno();
        partialUpdatedClienteExterno.setId(clienteExterno.getId());

        partialUpdatedClienteExterno
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restClienteExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClienteExterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClienteExterno))
            )
            .andExpect(status().isOk());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
        ClienteExterno testClienteExterno = clienteExternoList.get(clienteExternoList.size() - 1);
        assertThat(testClienteExterno.getIdnVarClienteExterno()).isEqualTo(UPDATED_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExterno.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testClienteExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testClienteExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testClienteExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateClienteExternoWithPatch() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();

        // Update the clienteExterno using partial update
        ClienteExterno partialUpdatedClienteExterno = new ClienteExterno();
        partialUpdatedClienteExterno.setId(clienteExterno.getId());

        partialUpdatedClienteExterno
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restClienteExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClienteExterno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClienteExterno))
            )
            .andExpect(status().isOk());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
        ClienteExterno testClienteExterno = clienteExternoList.get(clienteExternoList.size() - 1);
        assertThat(testClienteExterno.getIdnVarClienteExterno()).isEqualTo(UPDATED_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExterno.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testClienteExterno.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testClienteExterno.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testClienteExterno.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingClienteExterno() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();
        clienteExterno.setId(count.incrementAndGet());

        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteExternoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClienteExterno() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();
        clienteExterno.setId(count.incrementAndGet());

        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClienteExterno() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoRepository.findAll().size();
        clienteExterno.setId(count.incrementAndGet());

        // Create the ClienteExterno
        ClienteExternoDTO clienteExternoDTO = clienteExternoMapper.toDto(clienteExterno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClienteExterno in the database
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClienteExterno() throws Exception {
        // Initialize the database
        clienteExternoRepository.saveAndFlush(clienteExterno);

        int databaseSizeBeforeDelete = clienteExternoRepository.findAll().size();

        // Delete the clienteExterno
        restClienteExternoMockMvc
            .perform(delete(ENTITY_API_URL_ID, clienteExterno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClienteExterno> clienteExternoList = clienteExternoRepository.findAll();
        assertThat(clienteExternoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
