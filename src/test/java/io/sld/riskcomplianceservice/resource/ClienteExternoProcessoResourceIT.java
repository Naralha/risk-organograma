package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.TestUtil;
import io.sld.riskcomplianceservice.domain.entity.ClienteExterno;
import io.sld.riskcomplianceservice.domain.entity.ClienteExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.ClienteExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ClienteExternoProcessoMapper;
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
 * Integration tests for the {@link ClienteExternoProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
//@WithMockUser
class ClienteExternoProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_CLIENTE_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_CLIENTE_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cliente-externo-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteExternoProcessoRepository clienteExternoProcessoRepository;

    @Autowired
    private ClienteExternoProcessoMapper clienteExternoProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteExternoProcessoMockMvc;

    private ClienteExternoProcesso clienteExternoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteExternoProcesso createEntity(EntityManager em) {
        ClienteExternoProcesso clienteExternoProcesso = new ClienteExternoProcesso()
            .idnVarClienteExterno(DEFAULT_IDN_VAR_CLIENTE_EXTERNO)
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return clienteExternoProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteExternoProcesso createUpdatedEntity(EntityManager em) {
        ClienteExternoProcesso clienteExternoProcesso = new ClienteExternoProcesso()
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return clienteExternoProcesso;
    }

    @BeforeEach
    public void initTest() {
        clienteExternoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeCreate = clienteExternoProcessoRepository.findAll().size();
        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);
        restClienteExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteExternoProcesso testClienteExternoProcesso = clienteExternoProcessoList.get(clienteExternoProcessoList.size() - 1);
        assertThat(testClienteExternoProcesso.getIdnVarClienteExterno()).isEqualTo(DEFAULT_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testClienteExternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createClienteExternoProcessoWithExistingId() throws Exception {
        // Create the ClienteExternoProcesso with an existing ID
        clienteExternoProcesso.setId(1L);
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        int databaseSizeBeforeCreate = clienteExternoProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarClienteExternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoProcessoRepository.findAll().size();
        // set the field null
        clienteExternoProcesso.setIdnVarClienteExterno(null);

        // Create the ClienteExternoProcesso, which fails.
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        restClienteExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoProcessoRepository.findAll().size();
        // set the field null
        clienteExternoProcesso.setIdnVarProcesso(null);

        // Create the ClienteExternoProcesso, which fails.
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        restClienteExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteExternoProcessoRepository.findAll().size();
        // set the field null
        clienteExternoProcesso.setIdnvarUsuario(null);

        // Create the ClienteExternoProcesso, which fails.
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        restClienteExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessos() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList
        restClienteExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteExternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarClienteExterno").value(hasItem(DEFAULT_IDN_VAR_CLIENTE_EXTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getClienteExternoProcesso() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get the clienteExternoProcesso
        restClienteExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, clienteExternoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clienteExternoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarClienteExterno").value(DEFAULT_IDN_VAR_CLIENTE_EXTERNO))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getClienteExternoProcessosByIdFiltering() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        Long id = clienteExternoProcesso.getId();

        defaultClienteExternoProcessoShouldBeFound("id.equals=" + id);
        defaultClienteExternoProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultClienteExternoProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteExternoProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteExternoProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteExternoProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarClienteExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno equals to DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldBeFound("idnVarClienteExterno.equals=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno equals to UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarClienteExterno.equals=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarClienteExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno not equals to DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarClienteExterno.notEquals=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno not equals to UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldBeFound("idnVarClienteExterno.notEquals=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarClienteExternoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno in DEFAULT_IDN_VAR_CLIENTE_EXTERNO or UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldBeFound(
            "idnVarClienteExterno.in=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO + "," + UPDATED_IDN_VAR_CLIENTE_EXTERNO
        );

        // Get all the clienteExternoProcessoList where idnVarClienteExterno equals to UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarClienteExterno.in=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarClienteExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno is not null
        defaultClienteExternoProcessoShouldBeFound("idnVarClienteExterno.specified=true");

        // Get all the clienteExternoProcessoList where idnVarClienteExterno is null
        defaultClienteExternoProcessoShouldNotBeFound("idnVarClienteExterno.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarClienteExternoContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno contains DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldBeFound("idnVarClienteExterno.contains=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno contains UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarClienteExterno.contains=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarClienteExternoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno does not contain DEFAULT_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarClienteExterno.doesNotContain=" + DEFAULT_IDN_VAR_CLIENTE_EXTERNO);

        // Get all the clienteExternoProcessoList where idnVarClienteExterno does not contain UPDATED_IDN_VAR_CLIENTE_EXTERNO
        defaultClienteExternoProcessoShouldBeFound("idnVarClienteExterno.doesNotContain=" + UPDATED_IDN_VAR_CLIENTE_EXTERNO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteExternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteExternoProcessoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the clienteExternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarProcesso is not null
        defaultClienteExternoProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the clienteExternoProcessoList where idnVarProcesso is null
        defaultClienteExternoProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteExternoProcessoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteExternoProcessoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultClienteExternoProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoProcessoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the clienteExternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnvarUsuario is not null
        defaultClienteExternoProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the clienteExternoProcessoList where idnvarUsuario is null
        defaultClienteExternoProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoProcessoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        // Get all the clienteExternoProcessoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteExternoProcessoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultClienteExternoProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByClienteExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);
        ClienteExterno clienteExterno;
        if (TestUtil.findAll(em, ClienteExterno.class).isEmpty()) {
            clienteExterno = ClienteExternoResourceIT.createEntity(em);
            em.persist(clienteExterno);
            em.flush();
        } else {
            clienteExterno = TestUtil.findAll(em, ClienteExterno.class).get(0);
        }
        em.persist(clienteExterno);
        em.flush();
        clienteExternoProcesso.setClienteExterno(clienteExterno);
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);
        Long clienteExternoId = clienteExterno.getId();

        // Get all the clienteExternoProcessoList where clienteExterno equals to clienteExternoId
        defaultClienteExternoProcessoShouldBeFound("clienteExternoId.equals=" + clienteExternoId);

        // Get all the clienteExternoProcessoList where clienteExterno equals to (clienteExternoId + 1)
        defaultClienteExternoProcessoShouldNotBeFound("clienteExternoId.equals=" + (clienteExternoId + 1));
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);
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
        clienteExternoProcesso.setProcesso(processo);
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);
        Long processoId = processo.getId();

        // Get all the clienteExternoProcessoList where processo equals to processoId
        defaultClienteExternoProcessoShouldBeFound("processoId.equals=" + processoId);

        // Get all the clienteExternoProcessoList where processo equals to (processoId + 1)
        defaultClienteExternoProcessoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllClienteExternoProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);
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
        clienteExternoProcesso.setUsuario(usuario);
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);
        Long usuarioId = usuario.getId();

        // Get all the clienteExternoProcessoList where usuario equals to usuarioId
        defaultClienteExternoProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the clienteExternoProcessoList where usuario equals to (usuarioId + 1)
        defaultClienteExternoProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteExternoProcessoShouldBeFound(String filter) throws Exception {
        restClienteExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteExternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarClienteExterno").value(hasItem(DEFAULT_IDN_VAR_CLIENTE_EXTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restClienteExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteExternoProcessoShouldNotBeFound(String filter) throws Exception {
        restClienteExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClienteExternoProcesso() throws Exception {
        // Get the clienteExternoProcesso
        restClienteExternoProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClienteExternoProcesso() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();

        // Update the clienteExternoProcesso
        ClienteExternoProcesso updatedClienteExternoProcesso = clienteExternoProcessoRepository
            .findById(clienteExternoProcesso.getId())
            .get();
        // Disconnect from session so that the updates on updatedClienteExternoProcesso are not directly saved in db
        em.detach(updatedClienteExternoProcesso);
        updatedClienteExternoProcesso
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(updatedClienteExternoProcesso);

        restClienteExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteExternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ClienteExternoProcesso testClienteExternoProcesso = clienteExternoProcessoList.get(clienteExternoProcessoList.size() - 1);
        assertThat(testClienteExternoProcesso.getIdnVarClienteExterno()).isEqualTo(UPDATED_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testClienteExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();
        clienteExternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteExternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();
        clienteExternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();
        clienteExternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteExternoProcessoWithPatch() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();

        // Update the clienteExternoProcesso using partial update
        ClienteExternoProcesso partialUpdatedClienteExternoProcesso = new ClienteExternoProcesso();
        partialUpdatedClienteExternoProcesso.setId(clienteExternoProcesso.getId());

        partialUpdatedClienteExternoProcesso.idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restClienteExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClienteExternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClienteExternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ClienteExternoProcesso testClienteExternoProcesso = clienteExternoProcessoList.get(clienteExternoProcessoList.size() - 1);
        assertThat(testClienteExternoProcesso.getIdnVarClienteExterno()).isEqualTo(DEFAULT_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testClienteExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateClienteExternoProcessoWithPatch() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();

        // Update the clienteExternoProcesso using partial update
        ClienteExternoProcesso partialUpdatedClienteExternoProcesso = new ClienteExternoProcesso();
        partialUpdatedClienteExternoProcesso.setId(clienteExternoProcesso.getId());

        partialUpdatedClienteExternoProcesso
            .idnVarClienteExterno(UPDATED_IDN_VAR_CLIENTE_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restClienteExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClienteExternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClienteExternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ClienteExternoProcesso testClienteExternoProcesso = clienteExternoProcessoList.get(clienteExternoProcessoList.size() - 1);
        assertThat(testClienteExternoProcesso.getIdnVarClienteExterno()).isEqualTo(UPDATED_IDN_VAR_CLIENTE_EXTERNO);
        assertThat(testClienteExternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testClienteExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();
        clienteExternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteExternoProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();
        clienteExternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClienteExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteExternoProcessoRepository.findAll().size();
        clienteExternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteExternoProcesso
        ClienteExternoProcessoDTO clienteExternoProcessoDTO = clienteExternoProcessoMapper.toDto(clienteExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteExternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClienteExternoProcesso in the database
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClienteExternoProcesso() throws Exception {
        // Initialize the database
        clienteExternoProcessoRepository.saveAndFlush(clienteExternoProcesso);

        int databaseSizeBeforeDelete = clienteExternoProcessoRepository.findAll().size();

        // Delete the clienteExternoProcesso
        restClienteExternoProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, clienteExternoProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClienteExternoProcesso> clienteExternoProcessoList = clienteExternoProcessoRepository.findAll();
        assertThat(clienteExternoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
