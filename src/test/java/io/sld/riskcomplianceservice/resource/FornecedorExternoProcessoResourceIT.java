package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.entity.FornecedorExternoProcesso;
import io.sld.riskcomplianceservice.domain.entity.Processo;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.FornecedorExternoProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.FornecedorExternoProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.FornecedorExternoProcessoMapper;
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
 * Integration tests for the {@link FornecedorExternoProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FornecedorExternoProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_FORNECEDOR_EXTERNO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fornecedor-externo-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FornecedorExternoProcessoRepository fornecedorExternoProcessoRepository;

    @Autowired
    private FornecedorExternoProcessoMapper fornecedorExternoProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFornecedorExternoProcessoMockMvc;

    private FornecedorExternoProcesso fornecedorExternoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorExternoProcesso createEntity(EntityManager em) {
        FornecedorExternoProcesso fornecedorExternoProcesso = new FornecedorExternoProcesso()
            .idnVarFornecedorExterno(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO)
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO);
        return fornecedorExternoProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FornecedorExternoProcesso createUpdatedEntity(EntityManager em) {
        FornecedorExternoProcesso fornecedorExternoProcesso = new FornecedorExternoProcesso()
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        return fornecedorExternoProcesso;
    }

    @BeforeEach
    public void initTest() {
        fornecedorExternoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeCreate = fornecedorExternoProcessoRepository.findAll().size();
        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);
        restFornecedorExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        FornecedorExternoProcesso testFornecedorExternoProcesso = fornecedorExternoProcessoList.get(
            fornecedorExternoProcessoList.size() - 1
        );
        assertThat(testFornecedorExternoProcesso.getIdnVarFornecedorExterno()).isEqualTo(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testFornecedorExternoProcesso.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void createFornecedorExternoProcessoWithExistingId() throws Exception {
        // Create the FornecedorExternoProcesso with an existing ID
        fornecedorExternoProcesso.setId(1L);
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        int databaseSizeBeforeCreate = fornecedorExternoProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFornecedorExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarFornecedorExternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoProcessoRepository.findAll().size();
        // set the field null
        fornecedorExternoProcesso.setIdnVarFornecedorExterno(null);

        // Create the FornecedorExternoProcesso, which fails.
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        restFornecedorExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoProcessoRepository.findAll().size();
        // set the field null
        fornecedorExternoProcesso.setIdnVarProcesso(null);

        // Create the FornecedorExternoProcesso, which fails.
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        restFornecedorExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = fornecedorExternoProcessoRepository.findAll().size();
        // set the field null
        fornecedorExternoProcesso.setIdnvarUsuario(null);

        // Create the FornecedorExternoProcesso, which fails.
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        restFornecedorExternoProcessoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessos() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList
        restFornecedorExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorExternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFornecedorExterno").value(hasItem(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));
    }

    @Test
    @Transactional
    void getFornecedorExternoProcesso() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get the fornecedorExternoProcesso
        restFornecedorExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, fornecedorExternoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornecedorExternoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarFornecedorExterno").value(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO));
    }

    @Test
    @Transactional
    void getFornecedorExternoProcessosByIdFiltering() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        Long id = fornecedorExternoProcesso.getId();

        defaultFornecedorExternoProcessoShouldBeFound("id.equals=" + id);
        defaultFornecedorExternoProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultFornecedorExternoProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFornecedorExternoProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultFornecedorExternoProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFornecedorExternoProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarFornecedorExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno equals to DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarFornecedorExterno.equals=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno equals to UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarFornecedorExterno.equals=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarFornecedorExternoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno not equals to DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarFornecedorExterno.notEquals=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno not equals to UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarFornecedorExterno.notEquals=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarFornecedorExternoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno in DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO or UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldBeFound(
            "idnVarFornecedorExterno.in=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO + "," + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        );

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno equals to UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarFornecedorExterno.in=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarFornecedorExternoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno is not null
        defaultFornecedorExternoProcessoShouldBeFound("idnVarFornecedorExterno.specified=true");

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno is null
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarFornecedorExterno.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarFornecedorExternoContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno contains DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarFornecedorExterno.contains=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno contains UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarFornecedorExterno.contains=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarFornecedorExternoNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno does not contain DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarFornecedorExterno.doesNotContain=" + DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO);

        // Get all the fornecedorExternoProcessoList where idnVarFornecedorExterno does not contain UPDATED_IDN_VAR_FORNECEDOR_EXTERNO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarFornecedorExterno.doesNotContain=" + UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso is not null
        defaultFornecedorExternoProcessoShouldBeFound("idnVarProcesso.specified=true");

        // Get all the fornecedorExternoProcessoList where idnVarProcesso is null
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the fornecedorExternoProcessoList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultFornecedorExternoProcessoShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario is not null
        defaultFornecedorExternoProcessoShouldBeFound("idnvarUsuario.specified=true");

        // Get all the fornecedorExternoProcessoList where idnvarUsuario is null
        defaultFornecedorExternoProcessoShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the fornecedorExternoProcessoList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultFornecedorExternoProcessoShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByFornecedorExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);
        FornecedorExterno fornecedorExterno;
        if (TestUtil.findAll(em, FornecedorExterno.class).isEmpty()) {
            fornecedorExterno = FornecedorExternoResourceIT.createEntity(em);
            em.persist(fornecedorExterno);
            em.flush();
        } else {
            fornecedorExterno = TestUtil.findAll(em, FornecedorExterno.class).get(0);
        }
        em.persist(fornecedorExterno);
        em.flush();
        fornecedorExternoProcesso.setFornecedorExterno(fornecedorExterno);
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);
        Long fornecedorExternoId = fornecedorExterno.getId();

        // Get all the fornecedorExternoProcessoList where fornecedorExterno equals to fornecedorExternoId
        defaultFornecedorExternoProcessoShouldBeFound("fornecedorExternoId.equals=" + fornecedorExternoId);

        // Get all the fornecedorExternoProcessoList where fornecedorExterno equals to (fornecedorExternoId + 1)
        defaultFornecedorExternoProcessoShouldNotBeFound("fornecedorExternoId.equals=" + (fornecedorExternoId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);
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
        fornecedorExternoProcesso.setProcesso(processo);
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);
        Long processoId = processo.getId();

        // Get all the fornecedorExternoProcessoList where processo equals to processoId
        defaultFornecedorExternoProcessoShouldBeFound("processoId.equals=" + processoId);

        // Get all the fornecedorExternoProcessoList where processo equals to (processoId + 1)
        defaultFornecedorExternoProcessoShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllFornecedorExternoProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);
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
        fornecedorExternoProcesso.setUsuario(usuario);
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);
        Long usuarioId = usuario.getId();

        // Get all the fornecedorExternoProcessoList where usuario equals to usuarioId
        defaultFornecedorExternoProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the fornecedorExternoProcessoList where usuario equals to (usuarioId + 1)
        defaultFornecedorExternoProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFornecedorExternoProcessoShouldBeFound(String filter) throws Exception {
        restFornecedorExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornecedorExternoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarFornecedorExterno").value(hasItem(DEFAULT_IDN_VAR_FORNECEDOR_EXTERNO)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)));

        // Check, that the count call also returns 1
        restFornecedorExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFornecedorExternoProcessoShouldNotBeFound(String filter) throws Exception {
        restFornecedorExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFornecedorExternoProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFornecedorExternoProcesso() throws Exception {
        // Get the fornecedorExternoProcesso
        restFornecedorExternoProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFornecedorExternoProcesso() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();

        // Update the fornecedorExternoProcesso
        FornecedorExternoProcesso updatedFornecedorExternoProcesso = fornecedorExternoProcessoRepository
            .findById(fornecedorExternoProcesso.getId())
            .get();
        // Disconnect from session so that the updates on updatedFornecedorExternoProcesso are not directly saved in db
        em.detach(updatedFornecedorExternoProcesso);
        updatedFornecedorExternoProcesso
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(updatedFornecedorExternoProcesso);

        restFornecedorExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorExternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorExternoProcesso testFornecedorExternoProcesso = fornecedorExternoProcessoList.get(
            fornecedorExternoProcessoList.size() - 1
        );
        assertThat(testFornecedorExternoProcesso.getIdnVarFornecedorExterno()).isEqualTo(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testFornecedorExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();
        fornecedorExternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fornecedorExternoProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();
        fornecedorExternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();
        fornecedorExternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoProcessoMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFornecedorExternoProcessoWithPatch() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();

        // Update the fornecedorExternoProcesso using partial update
        FornecedorExternoProcesso partialUpdatedFornecedorExternoProcesso = new FornecedorExternoProcesso();
        partialUpdatedFornecedorExternoProcesso.setId(fornecedorExternoProcesso.getId());

        partialUpdatedFornecedorExternoProcesso
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFornecedorExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorExternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedorExternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorExternoProcesso testFornecedorExternoProcesso = fornecedorExternoProcessoList.get(
            fornecedorExternoProcessoList.size() - 1
        );
        assertThat(testFornecedorExternoProcesso.getIdnVarFornecedorExterno()).isEqualTo(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExternoProcesso.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testFornecedorExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateFornecedorExternoProcessoWithPatch() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();

        // Update the fornecedorExternoProcesso using partial update
        FornecedorExternoProcesso partialUpdatedFornecedorExternoProcesso = new FornecedorExternoProcesso();
        partialUpdatedFornecedorExternoProcesso.setId(fornecedorExternoProcesso.getId());

        partialUpdatedFornecedorExternoProcesso
            .idnVarFornecedorExterno(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restFornecedorExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFornecedorExternoProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFornecedorExternoProcesso))
            )
            .andExpect(status().isOk());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
        FornecedorExternoProcesso testFornecedorExternoProcesso = fornecedorExternoProcessoList.get(
            fornecedorExternoProcessoList.size() - 1
        );
        assertThat(testFornecedorExternoProcesso.getIdnVarFornecedorExterno()).isEqualTo(UPDATED_IDN_VAR_FORNECEDOR_EXTERNO);
        assertThat(testFornecedorExternoProcesso.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testFornecedorExternoProcesso.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();
        fornecedorExternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFornecedorExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fornecedorExternoProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();
        fornecedorExternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFornecedorExternoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = fornecedorExternoProcessoRepository.findAll().size();
        fornecedorExternoProcesso.setId(count.incrementAndGet());

        // Create the FornecedorExternoProcesso
        FornecedorExternoProcessoDTO fornecedorExternoProcessoDTO = fornecedorExternoProcessoMapper.toDto(fornecedorExternoProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFornecedorExternoProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fornecedorExternoProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FornecedorExternoProcesso in the database
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFornecedorExternoProcesso() throws Exception {
        // Initialize the database
        fornecedorExternoProcessoRepository.saveAndFlush(fornecedorExternoProcesso);

        int databaseSizeBeforeDelete = fornecedorExternoProcessoRepository.findAll().size();

        // Delete the fornecedorExternoProcesso
        restFornecedorExternoProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fornecedorExternoProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FornecedorExternoProcesso> fornecedorExternoProcessoList = fornecedorExternoProcessoRepository.findAll();
        assertThat(fornecedorExternoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
