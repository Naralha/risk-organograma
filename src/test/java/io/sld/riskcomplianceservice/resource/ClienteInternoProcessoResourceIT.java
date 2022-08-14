package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.ClienteInternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.ClienteInternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.ClienteInternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.ClienteInternoProcessoMapper;
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
 * Integration tests for the {@link ClienteInternoProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClienteInternoProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_ORGANOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_ORGANOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cliente-interno-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClienteInternoProcessoRepository clienteInternoProcessoRepository;

    @Autowired
    private ClienteInternoProcessoMapper clienteInternoProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClienteInternoProcessoMockMvc;

    private ClienteInternoProcesso clienteInternoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteInternoProcesso createEntity(EntityManager em) {
        ClienteInternoProcesso clienteInternoProcesso = new ClienteInternoProcesso()
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnVarOrganograma(DEFAULT_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return clienteInternoProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClienteInternoProcesso createUpdatedEntity(EntityManager em) {
        ClienteInternoProcesso clienteInternoProcesso = new ClienteInternoProcesso()
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return clienteInternoProcesso;
    }

    @BeforeEach
    public void initTest() {
        clienteInternoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeCreate = clienteInternoProcessoRepository.findAll().size();
        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);
        restClienteInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        ClienteInternoProcesso testClienteInternoProcesso = clienteInternoProcessoList.get(clienteInternoProcessoList.size() - 1);
        assertThat(testClienteInternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testClienteInternoProcesso.getIdnVarOrganograma()).isEqualTo(DEFAULT_IDN_VAR_ORGANOGRAMA);
        assertThat(testClienteInternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createClienteInternoProcessoWithExistingId() throws Exception {
        // Create the ClienteInternoProcesso with an existing ID
        clienteInternoProcesso.setId(1L);
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        int databaseSizeBeforeCreate = clienteInternoProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteInternoProcessoRepository.findAll().size();
        // set the field null
        clienteInternoProcesso.setIdnVarProcesso(null);

        // Create the ClienteInternoProcesso, which fails.
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        restClienteInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarOrganogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteInternoProcessoRepository.findAll().size();
        // set the field null
        clienteInternoProcesso.setIdnVarOrganograma(null);

        // Create the ClienteInternoProcesso, which fails.
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        restClienteInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteInternoProcessoRepository.findAll().size();
        // set the field null
        clienteInternoProcesso.setIdnvarUsuario(null);

        // Create the ClienteInternoProcesso, which fails.
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        restClienteInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessos() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList
        restClienteInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteInternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getClienteInternoProcesso() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get the clienteInternoProcesso
        restClienteInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, clienteInternoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clienteInternoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnVarOrganograma").value(DEFAULT_IDN_VAR_ORGANOGRAMA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getClienteInternoProcessosByIdFiltering() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        Long id = clienteInternoProcesso.getId();

        defaultClienteInternoProcessoShouldBeFound("id.equals=" + id);
        defaultClienteInternoProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultClienteInternoProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClienteInternoProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultClienteInternoProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClienteInternoProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteInternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteInternoProcessoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the clienteInternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarProcesso is not null
        defaultClienteInternoProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the clienteInternoProcessoList where idnVarProcesso is null
        defaultClienteInternoProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteInternoProcessoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the clienteInternoProcessoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultClienteInternoProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarOrganograma equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldBeFound("idnVarOrganograma.equals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the clienteInternoProcessoList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldNotBeFound("idnVarOrganograma.equals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarOrganogramaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarOrganograma not equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldNotBeFound("idnVarOrganograma.notEquals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the clienteInternoProcessoList where idnVarOrganograma not equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldBeFound("idnVarOrganograma.notEquals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarOrganogramaIsInShouldWork() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarOrganograma in DEFAULT_IDN_VAR_ORGANOGRAMA or UPDATED_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldBeFound(
            "idnVarOrganograma.in=" + DEFAULT_IDN_VAR_ORGANOGRAMA + "," + UPDATED_IDN_VAR_ORGANOGRAMA
        );

        // Get all the clienteInternoProcessoList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldNotBeFound("idnVarOrganograma.in=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarOrganogramaIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarOrganograma is not null
        defaultClienteInternoProcessoShouldBeFound("idnVarOrganograma.specified=true");

        // Get all the clienteInternoProcessoList where idnVarOrganograma is null
        defaultClienteInternoProcessoShouldNotBeFound("idnVarOrganograma.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarOrganogramaContainsSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarOrganograma contains DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldBeFound("idnVarOrganograma.contains=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the clienteInternoProcessoList where idnVarOrganograma contains UPDATED_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldNotBeFound("idnVarOrganograma.contains=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnVarOrganogramaNotContainsSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnVarOrganograma does not contain DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldNotBeFound("idnVarOrganograma.doesNotContain=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the clienteInternoProcessoList where idnVarOrganograma does not contain UPDATED_IDN_VAR_ORGANOGRAMA
        defaultClienteInternoProcessoShouldBeFound("idnVarOrganograma.doesNotContain=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteInternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteInternoProcessoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the clienteInternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnvarUsuario is not null
        defaultClienteInternoProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the clienteInternoProcessoList where idnvarUsuario is null
        defaultClienteInternoProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteInternoProcessoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        // Get all the clienteInternoProcessoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the clienteInternoProcessoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultClienteInternoProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);
        Organograma organograma;
        if (TestUtil.findAll(em, Organograma.class).isEmpty()) {
            organograma = OrganogramaResourceIT.createEntity(em);
            em.persist(organograma);
            em.flush();
        } else {
            organograma = TestUtil.findAll(em, Organograma.class).get(0);
        }
        em.persist(organograma);
        em.flush();
        clienteInternoProcesso.setOrganograma(organograma);
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);
        Long organogramaId = organograma.getId();

        // Get all the clienteInternoProcessoList where organograma equals to organogramaId
        defaultClienteInternoProcessoShouldBeFound("organogramaId.equals=" + organogramaId);

        // Get all the clienteInternoProcessoList where organograma equals to (organogramaId + 1)
        defaultClienteInternoProcessoShouldNotBeFound("organogramaId.equals=" + (organogramaId + 1));
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);
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
        clienteInternoProcesso.setProcesso(processo);
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);
        Long processoId = processo.getId();

        // Get all the clienteInternoProcessoList where processo equals to processoId
        defaultClienteInternoProcessoShouldBeFound("processoId.equals=" + processoId);

        // Get all the clienteInternoProcessoList where processo equals to (processoId + 1)
        defaultClienteInternoProcessoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllClienteInternoProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);
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
        clienteInternoProcesso.setUsuario(usuario);
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);
        Long usuarioId = usuario.getId();

        // Get all the clienteInternoProcessoList where usuario equals to usuarioId
        defaultClienteInternoProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the clienteInternoProcessoList where usuario equals to (usuarioId + 1)
        defaultClienteInternoProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteInternoProcessoShouldBeFound(String filter) throws Exception {
        restClienteInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clienteInternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restClienteInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteInternoProcessoShouldNotBeFound(String filter) throws Exception {
        restClienteInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClienteInternoProcesso() throws Exception {
        // Get the clienteInternoProcesso
        restClienteInternoProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClienteInternoProcesso() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();

        // Update the clienteInternoProcesso
        ClienteInternoProcesso updatedClienteInternoProcesso = clienteInternoProcessoRepository
            .findById(clienteInternoProcesso.getId())
            .get();
        // Disconnect from session so that the updates on updatedClienteInternoProcesso are not directly saved in db
        em.detach(updatedClienteInternoProcesso);
        updatedClienteInternoProcesso
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(updatedClienteInternoProcesso);

        restClienteInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteInternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ClienteInternoProcesso testClienteInternoProcesso = clienteInternoProcessoList.get(clienteInternoProcessoList.size() - 1);
        assertThat(testClienteInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testClienteInternoProcesso.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testClienteInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();
        clienteInternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clienteInternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();
        clienteInternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();
        clienteInternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClienteInternoProcessoWithPatch() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();

        // Update the clienteInternoProcesso using partial update
        ClienteInternoProcesso partialUpdatedClienteInternoProcesso = new ClienteInternoProcesso();
        partialUpdatedClienteInternoProcesso.setId(clienteInternoProcesso.getId());

        partialUpdatedClienteInternoProcesso.idnVarProcesso(UPDATED_IDN_VAR_PROCESSO).idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA);

        restClienteInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClienteInternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClienteInternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ClienteInternoProcesso testClienteInternoProcesso = clienteInternoProcessoList.get(clienteInternoProcessoList.size() - 1);
        assertThat(testClienteInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testClienteInternoProcesso.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testClienteInternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateClienteInternoProcessoWithPatch() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();

        // Update the clienteInternoProcesso using partial update
        ClienteInternoProcesso partialUpdatedClienteInternoProcesso = new ClienteInternoProcesso();
        partialUpdatedClienteInternoProcesso.setId(clienteInternoProcesso.getId());

        partialUpdatedClienteInternoProcesso
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restClienteInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClienteInternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClienteInternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        ClienteInternoProcesso testClienteInternoProcesso = clienteInternoProcessoList.get(clienteInternoProcessoList.size() - 1);
        assertThat(testClienteInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testClienteInternoProcesso.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testClienteInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();
        clienteInternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clienteInternoProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();
        clienteInternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClienteInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = clienteInternoProcessoRepository.findAll().size();
        clienteInternoProcesso.setId(count.incrementAndGet());

        // Create the ClienteInternoProcesso
        ClienteInternoProcessoDTO clienteInternoProcessoDTO = clienteInternoProcessoMapper.toDto(clienteInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClienteInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clienteInternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClienteInternoProcesso in the database
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClienteInternoProcesso() throws Exception {
        // Initialize the database
        clienteInternoProcessoRepository.saveAndFlush(clienteInternoProcesso);

        int databaseSizeBeforeDelete = clienteInternoProcessoRepository.findAll().size();

        // Delete the clienteInternoProcesso
        restClienteInternoProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, clienteInternoProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClienteInternoProcesso> clienteInternoProcessoList = clienteInternoProcessoRepository.findAll();
        assertThat(clienteInternoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
