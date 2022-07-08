package io.sld.riskcomplianceservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.Funcionario;
import io.sld.riskcomplianceservice.domain.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.repository.FuncionarioOrganogramaRepository;
import io.sld.riskcomplianceservice.service.criteria.FuncionarioOrganogramaCriteria;
import io.sld.riskcomplianceservice.service.dto.FuncionarioOrganogramaDTO;
import io.sld.riskcomplianceservice.service.mapper.FuncionarioOrganogramaMapper;
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
 * Integration tests for the {@link FuncionarioOrganogramaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuncionarioOrganogramaResourceIT {

    private static final String DEFAULT_IDN_VAR_FUNCIONARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_FUNCIONARIO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_ORGANOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_ORGANOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/funcionario-organogramas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FuncionarioOrganogramaRepository funcionarioOrganogramaRepository;

    @Autowired
    private FuncionarioOrganogramaMapper funcionarioOrganogramaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioOrganogramaMockMvc;

    private FuncionarioOrganograma funcionarioOrganograma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncionarioOrganograma createEntity(EntityManager em) {
        FuncionarioOrganograma funcionarioOrganograma = new FuncionarioOrganograma()
            .idnVarFuncionario(DEFAULT_IDN_VAR_FUNCIONARIO)
            .idnVarOrganograma(DEFAULT_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return funcionarioOrganograma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuncionarioOrganograma createUpdatedEntity(EntityManager em) {
        FuncionarioOrganograma funcionarioOrganograma = new FuncionarioOrganograma()
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return funcionarioOrganograma;
    }

    @BeforeEach
    public void initTest() {
        funcionarioOrganograma = createEntity(em);
    }

    @Test
    @Transactional
    void createFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeCreate = funcionarioOrganogramaRepository.findAll().size();
        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);
        restFuncionarioOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeCreate + 1);
        FuncionarioOrganograma testFuncionarioOrganograma = funcionarioOrganogramaList.get(funcionarioOrganogramaList.size() - 1);
        assertThat(testFuncionarioOrganograma.getIdnVarFuncionario()).isEqualTo(DEFAULT_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionarioOrganograma.getIdnVarOrganograma()).isEqualTo(DEFAULT_IDN_VAR_ORGANOGRAMA);
        assertThat(testFuncionarioOrganograma.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createFuncionarioOrganogramaWithExistingId() throws Exception {
        // Create the FuncionarioOrganograma with an existing ID
        funcionarioOrganograma.setId(1L);
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        int databaseSizeBeforeCreate = funcionarioOrganogramaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarFuncionarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioOrganogramaRepository.findAll().size();
        // set the field null
        funcionarioOrganograma.setIdnVarFuncionario(null);

        // Create the FuncionarioOrganograma, which fails.
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        restFuncionarioOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarOrganogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioOrganogramaRepository.findAll().size();
        // set the field null
        funcionarioOrganograma.setIdnVarOrganograma(null);

        // Create the FuncionarioOrganograma, which fails.
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        restFuncionarioOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioOrganogramaRepository.findAll().size();
        // set the field null
        funcionarioOrganograma.setIdnvarUsuario(null);

        // Create the FuncionarioOrganograma, which fails.
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        restFuncionarioOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramas() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList
        restFuncionarioOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionarioOrganograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFuncionario").value(hasItem(DEFAULT_IDN_VAR_FUNCIONARIO)))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getFuncionarioOrganograma() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get the funcionarioOrganograma
        restFuncionarioOrganogramaMockMvc
            .perform(get(ENTITY_API_URL_ID, funcionarioOrganograma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionarioOrganograma.getId().intValue()))
            .andExpect(jsonPath("$.idnVarFuncionario").value(DEFAULT_IDN_VAR_FUNCIONARIO))
            .andExpect(jsonPath("$.idnVarOrganograma").value(DEFAULT_IDN_VAR_ORGANOGRAMA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getFuncionarioOrganogramasByIdFiltering() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        Long id = funcionarioOrganograma.getId();

        defaultFuncionarioOrganogramaShouldBeFound("id.equals=" + id);
        defaultFuncionarioOrganogramaShouldNotBeFound("id.notEquals=" + id);

        defaultFuncionarioOrganogramaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFuncionarioOrganogramaShouldNotBeFound("id.greaterThan=" + id);

        defaultFuncionarioOrganogramaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFuncionarioOrganogramaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario equals to DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnVarFuncionario.equals=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario equals to UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarFuncionario.equals=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarFuncionarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario not equals to DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarFuncionario.notEquals=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario not equals to UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnVarFuncionario.notEquals=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarFuncionarioIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario in DEFAULT_IDN_VAR_FUNCIONARIO or UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldBeFound(
            "idnVarFuncionario.in=" + DEFAULT_IDN_VAR_FUNCIONARIO + "," + UPDATED_IDN_VAR_FUNCIONARIO
        );

        // Get all the funcionarioOrganogramaList where idnVarFuncionario equals to UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarFuncionario.in=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarFuncionarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario is not null
        defaultFuncionarioOrganogramaShouldBeFound("idnVarFuncionario.specified=true");

        // Get all the funcionarioOrganogramaList where idnVarFuncionario is null
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarFuncionario.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarFuncionarioContainsSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario contains DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnVarFuncionario.contains=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario contains UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarFuncionario.contains=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarFuncionarioNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario does not contain DEFAULT_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarFuncionario.doesNotContain=" + DEFAULT_IDN_VAR_FUNCIONARIO);

        // Get all the funcionarioOrganogramaList where idnVarFuncionario does not contain UPDATED_IDN_VAR_FUNCIONARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnVarFuncionario.doesNotContain=" + UPDATED_IDN_VAR_FUNCIONARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldBeFound("idnVarOrganograma.equals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarOrganograma.equals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarOrganogramaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma not equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarOrganograma.notEquals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma not equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldBeFound("idnVarOrganograma.notEquals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarOrganogramaIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma in DEFAULT_IDN_VAR_ORGANOGRAMA or UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldBeFound(
            "idnVarOrganograma.in=" + DEFAULT_IDN_VAR_ORGANOGRAMA + "," + UPDATED_IDN_VAR_ORGANOGRAMA
        );

        // Get all the funcionarioOrganogramaList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarOrganograma.in=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarOrganogramaIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma is not null
        defaultFuncionarioOrganogramaShouldBeFound("idnVarOrganograma.specified=true");

        // Get all the funcionarioOrganogramaList where idnVarOrganograma is null
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarOrganograma.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarOrganogramaContainsSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma contains DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldBeFound("idnVarOrganograma.contains=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma contains UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarOrganograma.contains=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnVarOrganogramaNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma does not contain DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldNotBeFound("idnVarOrganograma.doesNotContain=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the funcionarioOrganogramaList where idnVarOrganograma does not contain UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFuncionarioOrganogramaShouldBeFound("idnVarOrganograma.doesNotContain=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioOrganogramaList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioOrganogramaList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the funcionarioOrganogramaList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnvarUsuario is not null
        defaultFuncionarioOrganogramaShouldBeFound("idnvarUsuario.specified=true");

        // Get all the funcionarioOrganogramaList where idnvarUsuario is null
        defaultFuncionarioOrganogramaShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioOrganogramaList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        // Get all the funcionarioOrganogramaList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the funcionarioOrganogramaList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultFuncionarioOrganogramaShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);
        Funcionario funcionario;
        if (TestUtil.findAll(em, Funcionario.class).isEmpty()) {
            funcionario = FuncionarioResourceIT.createEntity(em);
            em.persist(funcionario);
            em.flush();
        } else {
            funcionario = TestUtil.findAll(em, Funcionario.class).get(0);
        }
        em.persist(funcionario);
        em.flush();
        funcionarioOrganograma.setFuncionario(funcionario);
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);
        Long funcionarioId = funcionario.getId();

        // Get all the funcionarioOrganogramaList where funcionario equals to funcionarioId
        defaultFuncionarioOrganogramaShouldBeFound("funcionarioId.equals=" + funcionarioId);

        // Get all the funcionarioOrganogramaList where funcionario equals to (funcionarioId + 1)
        defaultFuncionarioOrganogramaShouldNotBeFound("funcionarioId.equals=" + (funcionarioId + 1));
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);
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
        funcionarioOrganograma.setOrganograma(organograma);
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);
        Long organogramaId = organograma.getId();

        // Get all the funcionarioOrganogramaList where organograma equals to organogramaId
        defaultFuncionarioOrganogramaShouldBeFound("organogramaId.equals=" + organogramaId);

        // Get all the funcionarioOrganogramaList where organograma equals to (organogramaId + 1)
        defaultFuncionarioOrganogramaShouldNotBeFound("organogramaId.equals=" + (organogramaId + 1));
    }

    @Test
    @Transactional
    void getAllFuncionarioOrganogramasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);
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
        funcionarioOrganograma.setUsuario(usuario);
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);
        Long usuarioId = usuario.getId();

        // Get all the funcionarioOrganogramaList where usuario equals to usuarioId
        defaultFuncionarioOrganogramaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the funcionarioOrganogramaList where usuario equals to (usuarioId + 1)
        defaultFuncionarioOrganogramaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuncionarioOrganogramaShouldBeFound(String filter) throws Exception {
        restFuncionarioOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionarioOrganograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFuncionario").value(hasItem(DEFAULT_IDN_VAR_FUNCIONARIO)))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restFuncionarioOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuncionarioOrganogramaShouldNotBeFound(String filter) throws Exception {
        restFuncionarioOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuncionarioOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFuncionarioOrganograma() throws Exception {
        // Get the funcionarioOrganograma
        restFuncionarioOrganogramaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFuncionarioOrganograma() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();

        // Update the funcionarioOrganograma
        FuncionarioOrganograma updatedFuncionarioOrganograma = funcionarioOrganogramaRepository
            .findById(funcionarioOrganograma.getId())
            .get();
        // Disconnect from session so that the updates on updatedFuncionarioOrganograma are not directly saved in db
        em.detach(updatedFuncionarioOrganograma);
        updatedFuncionarioOrganograma
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(updatedFuncionarioOrganograma);

        restFuncionarioOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioOrganogramaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isOk());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
        FuncionarioOrganograma testFuncionarioOrganograma = funcionarioOrganogramaList.get(funcionarioOrganogramaList.size() - 1);
        assertThat(testFuncionarioOrganograma.getIdnVarFuncionario()).isEqualTo(UPDATED_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionarioOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testFuncionarioOrganograma.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();
        funcionarioOrganograma.setId(count.incrementAndGet());

        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, funcionarioOrganogramaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();
        funcionarioOrganograma.setId(count.incrementAndGet());

        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();
        funcionarioOrganograma.setId(count.incrementAndGet());

        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuncionarioOrganogramaWithPatch() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();

        // Update the funcionarioOrganograma using partial update
        FuncionarioOrganograma partialUpdatedFuncionarioOrganograma = new FuncionarioOrganograma();
        partialUpdatedFuncionarioOrganograma.setId(funcionarioOrganograma.getId());

        partialUpdatedFuncionarioOrganograma
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFuncionarioOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionarioOrganograma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionarioOrganograma))
            )
            .andExpect(status().isOk());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
        FuncionarioOrganograma testFuncionarioOrganograma = funcionarioOrganogramaList.get(funcionarioOrganogramaList.size() - 1);
        assertThat(testFuncionarioOrganograma.getIdnVarFuncionario()).isEqualTo(UPDATED_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionarioOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testFuncionarioOrganograma.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateFuncionarioOrganogramaWithPatch() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();

        // Update the funcionarioOrganograma using partial update
        FuncionarioOrganograma partialUpdatedFuncionarioOrganograma = new FuncionarioOrganograma();
        partialUpdatedFuncionarioOrganograma.setId(funcionarioOrganograma.getId());

        partialUpdatedFuncionarioOrganograma
            .idnVarFuncionario(UPDATED_IDN_VAR_FUNCIONARIO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFuncionarioOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuncionarioOrganograma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFuncionarioOrganograma))
            )
            .andExpect(status().isOk());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
        FuncionarioOrganograma testFuncionarioOrganograma = funcionarioOrganogramaList.get(funcionarioOrganogramaList.size() - 1);
        assertThat(testFuncionarioOrganograma.getIdnVarFuncionario()).isEqualTo(UPDATED_IDN_VAR_FUNCIONARIO);
        assertThat(testFuncionarioOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testFuncionarioOrganograma.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();
        funcionarioOrganograma.setId(count.incrementAndGet());

        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, funcionarioOrganogramaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();
        funcionarioOrganograma.setId(count.incrementAndGet());

        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuncionarioOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioOrganogramaRepository.findAll().size();
        funcionarioOrganograma.setId(count.incrementAndGet());

        // Create the FuncionarioOrganograma
        FuncionarioOrganogramaDTO funcionarioOrganogramaDTO = funcionarioOrganogramaMapper.toDto(funcionarioOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuncionarioOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(funcionarioOrganogramaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuncionarioOrganograma in the database
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuncionarioOrganograma() throws Exception {
        // Initialize the database
        funcionarioOrganogramaRepository.saveAndFlush(funcionarioOrganograma);

        int databaseSizeBeforeDelete = funcionarioOrganogramaRepository.findAll().size();

        // Delete the funcionarioOrganograma
        restFuncionarioOrganogramaMockMvc
            .perform(delete(ENTITY_API_URL_ID, funcionarioOrganograma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FuncionarioOrganograma> funcionarioOrganogramaList = funcionarioOrganogramaRepository.findAll();
        assertThat(funcionarioOrganogramaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
