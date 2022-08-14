package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.MacroProcesso;
import io.sld.riskcomplianceservice.domain.entity.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.MacroProcessoOrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.dto.MacroProcessoOrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.MacroProcessoOrganogramaMapper;
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
 * Integration tests for the {@link MacroProcessoOrganogramaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MacroProcessoOrganogramaResourceIT {

    private static final String DEFAULT_IDN_VAR_MACRO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_MACRO_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_ORGANOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_ORGANOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_USUARIO_CADASTRO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_USUARIO_CADASTRO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/macro-processo-organogramas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MacroProcessoOrganogramaRepository macroProcessoOrganogramaRepository;

    @Autowired
    private MacroProcessoOrganogramaMapper macroProcessoOrganogramaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMacroProcessoOrganogramaMockMvc;

    private MacroProcessoOrganograma macroProcessoOrganograma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MacroProcessoOrganograma createEntity(EntityManager em) {
        MacroProcessoOrganograma macroProcessoOrganograma = new MacroProcessoOrganograma()
            .idnVarMacroProcesso(DEFAULT_IDN_VAR_MACRO_PROCESSO)
            .idnVarOrganograma(DEFAULT_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(DEFAULT_IDN_VAR_PROCESSO)
            .idnVarUsuario(DEFAULT_IDN_VAR_USUARIO)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnVarUsuarioCadastro(DEFAULT_IDN_VAR_USUARIO_CADASTRO);
        return macroProcessoOrganograma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MacroProcessoOrganograma createUpdatedEntity(EntityManager em) {
        MacroProcessoOrganograma macroProcessoOrganograma = new MacroProcessoOrganograma()
            .idnVarMacroProcesso(UPDATED_IDN_VAR_MACRO_PROCESSO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO);
        return macroProcessoOrganograma;
    }

    @BeforeEach
    public void initTest() {
        macroProcessoOrganograma = createEntity(em);
    }

    @Test
    @Transactional
    void createMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeCreate = macroProcessoOrganogramaRepository.findAll().size();
        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);
        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeCreate + 1);
        MacroProcessoOrganograma testMacroProcessoOrganograma = macroProcessoOrganogramaList.get(macroProcessoOrganogramaList.size() - 1);
        assertThat(testMacroProcessoOrganograma.getIdnVarMacroProcesso()).isEqualTo(DEFAULT_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarOrganograma()).isEqualTo(DEFAULT_IDN_VAR_ORGANOGRAMA);
        assertThat(testMacroProcessoOrganograma.getIdnVarProcesso()).isEqualTo(DEFAULT_IDN_VAR_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuario()).isEqualTo(DEFAULT_IDN_VAR_USUARIO);
        assertThat(testMacroProcessoOrganograma.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuarioCadastro()).isEqualTo(DEFAULT_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void createMacroProcessoOrganogramaWithExistingId() throws Exception {
        // Create the MacroProcessoOrganograma with an existing ID
        macroProcessoOrganograma.setId(1L);
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        int databaseSizeBeforeCreate = macroProcessoOrganogramaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarMacroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoOrganogramaRepository.findAll().size();
        // set the field null
        macroProcessoOrganograma.setIdnVarMacroProcesso(null);

        // Create the MacroProcessoOrganograma, which fails.
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarOrganogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoOrganogramaRepository.findAll().size();
        // set the field null
        macroProcessoOrganograma.setIdnVarOrganograma(null);

        // Create the MacroProcessoOrganograma, which fails.
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoOrganogramaRepository.findAll().size();
        // set the field null
        macroProcessoOrganograma.setIdnVarProcesso(null);

        // Create the MacroProcessoOrganograma, which fails.
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoOrganogramaRepository.findAll().size();
        // set the field null
        macroProcessoOrganograma.setIdnVarUsuario(null);

        // Create the MacroProcessoOrganograma, which fails.
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoOrganogramaRepository.findAll().size();
        // set the field null
        macroProcessoOrganograma.setIdnVarEmpresa(null);

        // Create the MacroProcessoOrganograma, which fails.
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramas() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList
        restMacroProcessoOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(macroProcessoOrganograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarMacroProcesso").value(hasItem(DEFAULT_IDN_VAR_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarUsuario").value(hasItem(DEFAULT_IDN_VAR_USUARIO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnVarUsuarioCadastro").value(hasItem(DEFAULT_IDN_VAR_USUARIO_CADASTRO)));
    }

    @Test
    @Transactional
    void getMacroProcessoOrganograma() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get the macroProcessoOrganograma
        restMacroProcessoOrganogramaMockMvc
            .perform(get(ENTITY_API_URL_ID, macroProcessoOrganograma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(macroProcessoOrganograma.getId().intValue()))
            .andExpect(jsonPath("$.idnVarMacroProcesso").value(DEFAULT_IDN_VAR_MACRO_PROCESSO))
            .andExpect(jsonPath("$.idnVarOrganograma").value(DEFAULT_IDN_VAR_ORGANOGRAMA))
            .andExpect(jsonPath("$.idnVarProcesso").value(DEFAULT_IDN_VAR_PROCESSO))
            .andExpect(jsonPath("$.idnVarUsuario").value(DEFAULT_IDN_VAR_USUARIO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnVarUsuarioCadastro").value(DEFAULT_IDN_VAR_USUARIO_CADASTRO));
    }

    @Test
    @Transactional
    void getMacroProcessoOrganogramasByIdFiltering() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        Long id = macroProcessoOrganograma.getId();

        defaultMacroProcessoOrganogramaShouldBeFound("id.equals=" + id);
        defaultMacroProcessoOrganogramaShouldNotBeFound("id.notEquals=" + id);

        defaultMacroProcessoOrganogramaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMacroProcessoOrganogramaShouldNotBeFound("id.greaterThan=" + id);

        defaultMacroProcessoOrganogramaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMacroProcessoOrganogramaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarMacroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso equals to DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarMacroProcesso.equals=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso equals to UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarMacroProcesso.equals=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarMacroProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso not equals to DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarMacroProcesso.notEquals=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso not equals to UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarMacroProcesso.notEquals=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarMacroProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso in DEFAULT_IDN_VAR_MACRO_PROCESSO or UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound(
            "idnVarMacroProcesso.in=" + DEFAULT_IDN_VAR_MACRO_PROCESSO + "," + UPDATED_IDN_VAR_MACRO_PROCESSO
        );

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso equals to UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarMacroProcesso.in=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarMacroProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso is not null
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarMacroProcesso.specified=true");

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso is null
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarMacroProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarMacroProcessoContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso contains DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarMacroProcesso.contains=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso contains UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarMacroProcesso.contains=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarMacroProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso does not contain DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarMacroProcesso.doesNotContain=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarMacroProcesso does not contain UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarMacroProcesso.doesNotContain=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarOrganograma.equals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarOrganograma.equals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarOrganogramaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma not equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarOrganograma.notEquals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma not equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarOrganograma.notEquals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarOrganogramaIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma in DEFAULT_IDN_VAR_ORGANOGRAMA or UPDATED_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldBeFound(
            "idnVarOrganograma.in=" + DEFAULT_IDN_VAR_ORGANOGRAMA + "," + UPDATED_IDN_VAR_ORGANOGRAMA
        );

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarOrganograma.in=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarOrganogramaIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma is not null
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarOrganograma.specified=true");

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma is null
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarOrganograma.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarOrganogramaContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma contains DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarOrganograma.contains=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma contains UPDATED_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarOrganograma.contains=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarOrganogramaNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma does not contain DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarOrganograma.doesNotContain=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the macroProcessoOrganogramaList where idnVarOrganograma does not contain UPDATED_IDN_VAR_ORGANOGRAMA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarOrganograma.doesNotContain=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso equals to DEFAULT_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarProcesso.equals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarProcesso.equals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso not equals to DEFAULT_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarProcesso.notEquals=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso not equals to UPDATED_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarProcesso.notEquals=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso in DEFAULT_IDN_VAR_PROCESSO or UPDATED_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarProcesso.in=" + DEFAULT_IDN_VAR_PROCESSO + "," + UPDATED_IDN_VAR_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso equals to UPDATED_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarProcesso.in=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso is not null
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarProcesso.specified=true");

        // Get all the macroProcessoOrganogramaList where idnVarProcesso is null
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarProcessoContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso contains DEFAULT_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarProcesso.contains=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso contains UPDATED_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarProcesso.contains=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso does not contain DEFAULT_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarProcesso.doesNotContain=" + DEFAULT_IDN_VAR_PROCESSO);

        // Get all the macroProcessoOrganogramaList where idnVarProcesso does not contain UPDATED_IDN_VAR_PROCESSO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarProcesso.doesNotContain=" + UPDATED_IDN_VAR_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario equals to DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuario.equals=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario equals to UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuario.equals=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario not equals to DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuario.notEquals=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario not equals to UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuario.notEquals=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario in DEFAULT_IDN_VAR_USUARIO or UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuario.in=" + DEFAULT_IDN_VAR_USUARIO + "," + UPDATED_IDN_VAR_USUARIO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario equals to UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuario.in=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario is not null
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuario.specified=true");

        // Get all the macroProcessoOrganogramaList where idnVarUsuario is null
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario contains DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuario.contains=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario contains UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuario.contains=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario does not contain DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuario.doesNotContain=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuario does not contain UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuario.doesNotContain=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa is not null
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa is null
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoOrganogramaList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro equals to DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuarioCadastro.equals=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro equals to UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuarioCadastro.equals=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro not equals to DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuarioCadastro.notEquals=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro not equals to UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuarioCadastro.notEquals=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro in DEFAULT_IDN_VAR_USUARIO_CADASTRO or UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldBeFound(
            "idnVarUsuarioCadastro.in=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO + "," + UPDATED_IDN_VAR_USUARIO_CADASTRO
        );

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro equals to UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuarioCadastro.in=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro is not null
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuarioCadastro.specified=true");

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro is null
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuarioCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioCadastroContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro contains DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuarioCadastro.contains=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro contains UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuarioCadastro.contains=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByIdnVarUsuarioCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro does not contain DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldNotBeFound("idnVarUsuarioCadastro.doesNotContain=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the macroProcessoOrganogramaList where idnVarUsuarioCadastro does not contain UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultMacroProcessoOrganogramaShouldBeFound("idnVarUsuarioCadastro.doesNotContain=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
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
        macroProcessoOrganograma.setEmpresa(empresa);
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
        Long empresaId = empresa.getId();

        // Get all the macroProcessoOrganogramaList where empresa equals to empresaId
        defaultMacroProcessoOrganogramaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the macroProcessoOrganogramaList where empresa equals to (empresaId + 1)
        defaultMacroProcessoOrganogramaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
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
        macroProcessoOrganograma.setUsuario(usuario);
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
        Long usuarioId = usuario.getId();

        // Get all the macroProcessoOrganogramaList where usuario equals to usuarioId
        defaultMacroProcessoOrganogramaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the macroProcessoOrganogramaList where usuario equals to (usuarioId + 1)
        defaultMacroProcessoOrganogramaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByMacroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
        MacroProcesso macroProcesso;
        if (TestUtil.findAll(em, MacroProcesso.class).isEmpty()) {
            macroProcesso = MacroProcessoResourceIT.createEntity(em);
            em.persist(macroProcesso);
            em.flush();
        } else {
            macroProcesso = TestUtil.findAll(em, MacroProcesso.class).get(0);
        }
        em.persist(macroProcesso);
        em.flush();
        macroProcessoOrganograma.setMacroProcesso(macroProcesso);
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
        Long macroProcessoId = macroProcesso.getId();

        // Get all the macroProcessoOrganogramaList where macroProcesso equals to macroProcessoId
        defaultMacroProcessoOrganogramaShouldBeFound("macroProcessoId.equals=" + macroProcessoId);

        // Get all the macroProcessoOrganogramaList where macroProcesso equals to (macroProcessoId + 1)
        defaultMacroProcessoOrganogramaShouldNotBeFound("macroProcessoId.equals=" + (macroProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllMacroProcessoOrganogramasByOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
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
        macroProcessoOrganograma.setOrganograma(organograma);
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);
        Long organogramaId = organograma.getId();

        // Get all the macroProcessoOrganogramaList where organograma equals to organogramaId
        defaultMacroProcessoOrganogramaShouldBeFound("organogramaId.equals=" + organogramaId);

        // Get all the macroProcessoOrganogramaList where organograma equals to (organogramaId + 1)
        defaultMacroProcessoOrganogramaShouldNotBeFound("organogramaId.equals=" + (organogramaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMacroProcessoOrganogramaShouldBeFound(String filter) throws Exception {
        restMacroProcessoOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(macroProcessoOrganograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarMacroProcesso").value(hasItem(DEFAULT_IDN_VAR_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnVarProcesso").value(hasItem(DEFAULT_IDN_VAR_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarUsuario").value(hasItem(DEFAULT_IDN_VAR_USUARIO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnVarUsuarioCadastro").value(hasItem(DEFAULT_IDN_VAR_USUARIO_CADASTRO)));

        // Check, that the count call also returns 1
        restMacroProcessoOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMacroProcessoOrganogramaShouldNotBeFound(String filter) throws Exception {
        restMacroProcessoOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMacroProcessoOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMacroProcessoOrganograma() throws Exception {
        // Get the macroProcessoOrganograma
        restMacroProcessoOrganogramaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMacroProcessoOrganograma() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();

        // Update the macroProcessoOrganograma
        MacroProcessoOrganograma updatedMacroProcessoOrganograma = macroProcessoOrganogramaRepository
            .findById(macroProcessoOrganograma.getId())
            .get();
        // Disconnect from session so that the updates on updatedMacroProcessoOrganograma are not directly saved in db
        em.detach(updatedMacroProcessoOrganograma);
        updatedMacroProcessoOrganograma
            .idnVarMacroProcesso(UPDATED_IDN_VAR_MACRO_PROCESSO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO);
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(updatedMacroProcessoOrganograma);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, macroProcessoOrganogramaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isOk());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
        MacroProcessoOrganograma testMacroProcessoOrganograma = macroProcessoOrganogramaList.get(macroProcessoOrganogramaList.size() - 1);
        assertThat(testMacroProcessoOrganograma.getIdnVarMacroProcesso()).isEqualTo(UPDATED_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testMacroProcessoOrganograma.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
        assertThat(testMacroProcessoOrganograma.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuarioCadastro()).isEqualTo(UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void putNonExistingMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();
        macroProcessoOrganograma.setId(count.incrementAndGet());

        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMacroProcessoOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, macroProcessoOrganogramaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();
        macroProcessoOrganograma.setId(count.incrementAndGet());

        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();
        macroProcessoOrganograma.setId(count.incrementAndGet());

        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMacroProcessoOrganogramaWithPatch() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();

        // Update the macroProcessoOrganograma using partial update
        MacroProcessoOrganograma partialUpdatedMacroProcessoOrganograma = new MacroProcessoOrganograma();
        partialUpdatedMacroProcessoOrganograma.setId(macroProcessoOrganograma.getId());

        partialUpdatedMacroProcessoOrganograma
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMacroProcessoOrganograma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMacroProcessoOrganograma))
            )
            .andExpect(status().isOk());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
        MacroProcessoOrganograma testMacroProcessoOrganograma = macroProcessoOrganogramaList.get(macroProcessoOrganogramaList.size() - 1);
        assertThat(testMacroProcessoOrganograma.getIdnVarMacroProcesso()).isEqualTo(DEFAULT_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarOrganograma()).isEqualTo(DEFAULT_IDN_VAR_ORGANOGRAMA);
        assertThat(testMacroProcessoOrganograma.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuario()).isEqualTo(DEFAULT_IDN_VAR_USUARIO);
        assertThat(testMacroProcessoOrganograma.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuarioCadastro()).isEqualTo(UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void fullUpdateMacroProcessoOrganogramaWithPatch() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();

        // Update the macroProcessoOrganograma using partial update
        MacroProcessoOrganograma partialUpdatedMacroProcessoOrganograma = new MacroProcessoOrganograma();
        partialUpdatedMacroProcessoOrganograma.setId(macroProcessoOrganograma.getId());

        partialUpdatedMacroProcessoOrganograma
            .idnVarMacroProcesso(UPDATED_IDN_VAR_MACRO_PROCESSO)
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarProcesso(UPDATED_IDN_VAR_PROCESSO)
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO);

        restMacroProcessoOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMacroProcessoOrganograma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMacroProcessoOrganograma))
            )
            .andExpect(status().isOk());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
        MacroProcessoOrganograma testMacroProcessoOrganograma = macroProcessoOrganogramaList.get(macroProcessoOrganogramaList.size() - 1);
        assertThat(testMacroProcessoOrganograma.getIdnVarMacroProcesso()).isEqualTo(UPDATED_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testMacroProcessoOrganograma.getIdnVarProcesso()).isEqualTo(UPDATED_IDN_VAR_PROCESSO);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
        assertThat(testMacroProcessoOrganograma.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testMacroProcessoOrganograma.getIdnVarUsuarioCadastro()).isEqualTo(UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void patchNonExistingMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();
        macroProcessoOrganograma.setId(count.incrementAndGet());

        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMacroProcessoOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, macroProcessoOrganogramaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();
        macroProcessoOrganograma.setId(count.incrementAndGet());

        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMacroProcessoOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoOrganogramaRepository.findAll().size();
        macroProcessoOrganograma.setId(count.incrementAndGet());

        // Create the MacroProcessoOrganograma
        MacroProcessoOrganogramaDTO macroProcessoOrganogramaDTO = macroProcessoOrganogramaMapper.toDto(macroProcessoOrganograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoOrganogramaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MacroProcessoOrganograma in the database
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMacroProcessoOrganograma() throws Exception {
        // Initialize the database
        macroProcessoOrganogramaRepository.saveAndFlush(macroProcessoOrganograma);

        int databaseSizeBeforeDelete = macroProcessoOrganogramaRepository.findAll().size();

        // Delete the macroProcessoOrganograma
        restMacroProcessoOrganogramaMockMvc
            .perform(delete(ENTITY_API_URL_ID, macroProcessoOrganograma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MacroProcessoOrganograma> macroProcessoOrganogramaList = macroProcessoOrganogramaRepository.findAll();
        assertThat(macroProcessoOrganogramaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
