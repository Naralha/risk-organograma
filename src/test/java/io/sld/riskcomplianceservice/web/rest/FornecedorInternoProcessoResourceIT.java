package io.sld.riskcomplianceservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.FornecedorInternoProcesso;
import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.repository.FornecedorInternoProcessoRepository;
import io.sld.riskcomplianceservice.service.criteria.FornecedorInternoProcessoCriteria;
import io.sld.riskcomplianceservice.service.dto.FornecedorInternoProcessoDTO;
import io.sld.riskcomplianceservice.service.mapper.FornecedorInternoProcessoMapper;
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
 * Integration tests for the {@link FornecedorInternoProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FornecedorInternoProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_ORGANOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_ORGANOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fornecedor-interno-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FornecedorInternoProcessoRepository fornecedorInternoProcessoRepository;

    @Autowired
    private FornecedorInternoProcessoMapper fornecedorInternoProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorInternoProcessoMockMvc;

    private FornecedorInternoProcesso fornecedorInternoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorInternoProcesso createEntity(EntityManager em) {
        FornecedorInternoProcesso fornecedorInternoProcesso = new FornecedorInternoProcesso()
            .idnVarOrganograma(DEFAULT_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return fornecedorInternoProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorInternoProcesso createUpdatedEntity(EntityManager em) {
        FornecedorInternoProcesso fornecedorInternoProcesso = new FornecedorInternoProcesso()
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return fornecedorInternoProcesso;
    }

    @BeforeEach
    public void initTest() {
        fornecedorInternoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeCreate = fornecedorInternoProcessoRepository.findAll().size();
        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);
        restFornecedorInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        FornecedorInternoProcesso testFornecedorInternoProcesso = fornecedorInternoProcessoList.get(
            fornecedorInternoProcessoList.size() - 1
        );
        assertThat(testFornecedorInternoProcesso.getIdnVarOrganograma()).isEqualTo(DEFAULT_IDN_VAR_ORGANOGRAMA);
        assertThat(testFornecedorInternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testFornecedorInternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createFornecedorInternoProcessoWithExistingId() throws Exception {
        // Create the FornecedorInternoProcesso with an existing ID
        fornecedorInternoProcesso.setId(1L);
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        int databaseSizeBeforeCreate = fornecedorInternoProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarOrganogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorInternoProcessoRepository.findAll().size();
        // set the field null
        fornecedorInternoProcesso.setIdnVarOrganograma(null);

        // Create the FornecedorInternoProcesso, which fails.
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        restFornecedorInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorInternoProcessoRepository.findAll().size();
        // set the field null
        fornecedorInternoProcesso.setIdnVarProcesso(null);

        // Create the FornecedorInternoProcesso, which fails.
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        restFornecedorInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorInternoProcessoRepository.findAll().size();
        // set the field null
        fornecedorInternoProcesso.setIdnvarUsuario(null);

        // Create the FornecedorInternoProcesso, which fails.
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        restFornecedorInternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessos() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList
        restFornecedorInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorInternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getFornecedorInternoProcesso() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get the fornecedorInternoProcesso
        restFornecedorInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedorInternoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedorInternoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarOrganograma").value(DEFAULT_IDN_VAR_ORGANOGRAMA))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getFornecedorInternoProcessosByIdFiltering() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        Long id = fornecedorInternoProcesso.getId();

        defaultFornecedorInternoProcessoShouldBeFound("id.equals=" + id);
        defaultFornecedorInternoProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultFornecedorInternoProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFornecedorInternoProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultFornecedorInternoProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFornecedorInternoProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldBeFound("idnVarOrganograma.equals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarOrganograma.equals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarOrganogramaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma not equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarOrganograma.notEquals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma not equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldBeFound("idnVarOrganograma.notEquals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarOrganogramaIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma in DEFAULT_IDN_VAR_ORGANOGRAMA or UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldBeFound(
            "idnVarOrganograma.in=" + DEFAULT_IDN_VAR_ORGANOGRAMA + "," + UPDATED_IDN_VAR_ORGANOGRAMA
        );

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarOrganograma.in=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarOrganogramaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma is not null
        defaultFornecedorInternoProcessoShouldBeFound("idnVarOrganograma.specified=true");

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma is null
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarOrganograma.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarOrganogramaContainsSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma contains DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldBeFound("idnVarOrganograma.contains=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma contains UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarOrganograma.contains=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarOrganogramaNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma does not contain DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarOrganograma.doesNotContain=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the fornecedorInternoProcessoList where idnVarOrganograma does not contain UPDATED_IDN_VAR_ORGANOGRAMA
        defaultFornecedorInternoProcessoShouldBeFound("idnVarOrganograma.doesNotContain=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso is not null
        defaultFornecedorInternoProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the fornecedorInternoProcessoList where idnVarProcesso is null
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorInternoProcessoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorInternoProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario is not null
        defaultFornecedorInternoProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the fornecedorInternoProcessoList where idnvarUsuario is null
        defaultFornecedorInternoProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorInternoProcessoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultFornecedorInternoProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);
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
        fornecedorInternoProcesso.setOrganograma(organograma);
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);
        Long organogramaId = organograma.getId();

        // Get all the fornecedorInternoProcessoList where organograma equals to organogramaId
        defaultFornecedorInternoProcessoShouldBeFound("organogramaId.equals=" + organogramaId);

        // Get all the fornecedorInternoProcessoList where organograma equals to (organogramaId + 1)
        defaultFornecedorInternoProcessoShouldNotBeFound("organogramaId.equals=" + (organogramaId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);
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
        fornecedorInternoProcesso.setProcesso(processo);
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);
        Long processoId = processo.getId();

        // Get all the fornecedorInternoProcessoList where processo equals to processoId
        defaultFornecedorInternoProcessoShouldBeFound("processoId.equals=" + processoId);

        // Get all the fornecedorInternoProcessoList where processo equals to (processoId + 1)
        defaultFornecedorInternoProcessoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorInternoProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);
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
        fornecedorInternoProcesso.setUsuario(usuario);
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);
        Long usuarioId = usuario.getId();

        // Get all the fornecedorInternoProcessoList where usuario equals to usuarioId
        defaultFornecedorInternoProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the fornecedorInternoProcessoList where usuario equals to (usuarioId + 1)
        defaultFornecedorInternoProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFornecedorInternoProcessoShouldBeFound(String filter) throws Exception {
        restFornecedorInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorInternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restFornecedorInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFornecedorInternoProcessoShouldNotBeFound(String filter) throws Exception {
        restFornecedorInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFornecedorInternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFornecedorInternoProcesso() throws Exception {
        // Get the fornecedorInternoProcesso
        restFornecedorInternoProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFornecedorInternoProcesso() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();

        // Update the fornecedorInternoProcesso
        FornecedorInternoProcesso updatedFornecedorInternoProcesso = fornecedorInternoProcessoRepository
            .findById(fornecedorInternoProcesso.getId())
            .get();
        // Disconnect from session so that the updates on updatedFornecedorInternoProcesso are not directly saved in db
        em.detach(updatedFornecedorInternoProcesso);
        updatedFornecedorInternoProcesso
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(updatedFornecedorInternoProcesso);

        restFornecedorInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorInternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorInternoProcesso testFornecedorInternoProcesso = fornecedorInternoProcessoList.get(
            fornecedorInternoProcessoList.size() - 1
        );
        assertThat(testFornecedorInternoProcesso.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testFornecedorInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testFornecedorInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();
        fornecedorInternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorInternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();
        fornecedorInternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();
        fornecedorInternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorInternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorInternoProcessoWithPatch() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();

        // Update the fornecedorInternoProcesso using partial update
        FornecedorInternoProcesso partialUpdatedFornecedorInternoProcesso = new FornecedorInternoProcesso();
        partialUpdatedFornecedorInternoProcesso.setId(fornecedorInternoProcesso.getId());

        partialUpdatedFornecedorInternoProcesso.idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA).idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFornecedorInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorInternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedorInternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorInternoProcesso testFornecedorInternoProcesso = fornecedorInternoProcessoList.get(
            fornecedorInternoProcessoList.size() - 1
        );
        assertThat(testFornecedorInternoProcesso.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testFornecedorInternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testFornecedorInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateFornecedorInternoProcessoWithPatch() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();

        // Update the fornecedorInternoProcesso using partial update
        FornecedorInternoProcesso partialUpdatedFornecedorInternoProcesso = new FornecedorInternoProcesso();
        partialUpdatedFornecedorInternoProcesso.setId(fornecedorInternoProcesso.getId());

        partialUpdatedFornecedorInternoProcesso
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFornecedorInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorInternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedorInternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorInternoProcesso testFornecedorInternoProcesso = fornecedorInternoProcessoList.get(
            fornecedorInternoProcessoList.size() - 1
        );
        assertThat(testFornecedorInternoProcesso.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testFornecedorInternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testFornecedorInternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();
        fornecedorInternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorInternoProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();
        fornecedorInternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedorInternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorInternoProcessoRepository.findAll().size();
        fornecedorInternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorInternoProcesso
        FornecedorInternoProcessoDTO fornecedorInternoProcessoDTO = fornecedorInternoProcessoMapper.toDto(fornecedorInternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorInternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorInternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorInternoProcesso in the database
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedorInternoProcesso() throws Exception {
        // Initialize the database
        fornecedorInternoProcessoRepository.saveAndFlush(fornecedorInternoProcesso);

        int databaseSizeBeforeDelete = fornecedorInternoProcessoRepository.findAll().size();

        // Delete the fornecedorInternoProcesso
        restFornecedorInternoProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedorInternoProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FornecedorInternoProcesso> fornecedorInternoProcessoList = fornecedorInternoProcessoRepository.findAll();
        assertThat(fornecedorInternoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
