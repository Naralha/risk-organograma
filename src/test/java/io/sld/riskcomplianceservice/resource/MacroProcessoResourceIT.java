package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.TestUtil;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.MacroProcesso;
import io.sld.riskcomplianceservice.domain.entity.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.MacroProcessoRepository;
import io.sld.riskcomplianceservice.domain.service.dto.MacroProcessoDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.MacroProcessoMapper;
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
 * Integration tests for the {@link MacroProcessoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
//@WithMockUser
class MacroProcessoResourceIT {

    private static final String DEFAULT_IDN_VAR_MACRO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_MACRO_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME_MACRO_PROCESSO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME_MACRO_PROCESSO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/macro-processos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MacroProcessoRepository macroProcessoRepository;

    @Autowired
    private MacroProcessoMapper macroProcessoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMacroProcessoMockMvc;

    private MacroProcesso macroProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MacroProcesso createEntity(EntityManager em) {
        MacroProcesso macroProcesso = new MacroProcesso()
            .idnVarMacroProcesso(DEFAULT_IDN_VAR_MACRO_PROCESSO)
            .nVarNomeMacroProcesso(DEFAULT_N_VAR_NOME_MACRO_PROCESSO)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .idnVarUsuario(DEFAULT_IDN_VAR_USUARIO);
        return macroProcesso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MacroProcesso createUpdatedEntity(EntityManager em) {
        MacroProcesso macroProcesso = new MacroProcesso()
            .idnVarMacroProcesso(UPDATED_IDN_VAR_MACRO_PROCESSO)
            .nVarNomeMacroProcesso(UPDATED_N_VAR_NOME_MACRO_PROCESSO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO);
        return macroProcesso;
    }

    @BeforeEach
    public void initTest() {
        macroProcesso = createEntity(em);
    }

    @Test
    @Transactional
    void createMacroProcesso() throws Exception {
        int databaseSizeBeforeCreate = macroProcessoRepository.findAll().size();
        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);
        restMacroProcessoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        MacroProcesso testMacroProcesso = macroProcessoList.get(macroProcessoList.size() - 1);
        assertThat(testMacroProcesso.getIdnVarMacroProcesso()).isEqualTo(DEFAULT_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getnVarNomeMacroProcesso()).isEqualTo(DEFAULT_N_VAR_NOME_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testMacroProcesso.getIdnVarUsuario()).isEqualTo(DEFAULT_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void createMacroProcessoWithExistingId() throws Exception {
        // Create the MacroProcesso with an existing ID
        macroProcesso.setId(1L);
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        int databaseSizeBeforeCreate = macroProcessoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMacroProcessoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarMacroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoRepository.findAll().size();
        // set the field null
        macroProcesso.setIdnVarMacroProcesso(null);

        // Create the MacroProcesso, which fails.
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        restMacroProcessoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeMacroProcessoIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoRepository.findAll().size();
        // set the field null
        macroProcesso.setnVarNomeMacroProcesso(null);

        // Create the MacroProcesso, which fails.
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        restMacroProcessoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoRepository.findAll().size();
        // set the field null
        macroProcesso.setIdnVarEmpresa(null);

        // Create the MacroProcesso, which fails.
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        restMacroProcessoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = macroProcessoRepository.findAll().size();
        // set the field null
        macroProcesso.setIdnVarUsuario(null);

        // Create the MacroProcesso, which fails.
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        restMacroProcessoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMacroProcessos() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList
        restMacroProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(macroProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarMacroProcesso").value(hasItem(DEFAULT_IDN_VAR_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarNomeMacroProcesso").value(hasItem(DEFAULT_N_VAR_NOME_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnVarUsuario").value(hasItem(DEFAULT_IDN_VAR_USUARIO)));
    }

    @Test
    @Transactional
    void getMacroProcesso() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get the macroProcesso
        restMacroProcessoMockMvc
            .perform(get(ENTITY_API_URL_ID, macroProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(macroProcesso.getId().intValue()))
            .andExpect(jsonPath("$.idnVarMacroProcesso").value(DEFAULT_IDN_VAR_MACRO_PROCESSO))
            .andExpect(jsonPath("$.nVarNomeMacroProcesso").value(DEFAULT_N_VAR_NOME_MACRO_PROCESSO))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnVarUsuario").value(DEFAULT_IDN_VAR_USUARIO));
    }

    @Test
    @Transactional
    void getMacroProcessosByIdFiltering() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        Long id = macroProcesso.getId();

        defaultMacroProcessoShouldBeFound("id.equals=" + id);
        defaultMacroProcessoShouldNotBeFound("id.notEquals=" + id);

        defaultMacroProcessoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMacroProcessoShouldNotBeFound("id.greaterThan=" + id);

        defaultMacroProcessoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMacroProcessoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarMacroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarMacroProcesso equals to DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("idnVarMacroProcesso.equals=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoList where idnVarMacroProcesso equals to UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("idnVarMacroProcesso.equals=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarMacroProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarMacroProcesso not equals to DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("idnVarMacroProcesso.notEquals=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoList where idnVarMacroProcesso not equals to UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("idnVarMacroProcesso.notEquals=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarMacroProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarMacroProcesso in DEFAULT_IDN_VAR_MACRO_PROCESSO or UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound(
            "idnVarMacroProcesso.in=" + DEFAULT_IDN_VAR_MACRO_PROCESSO + "," + UPDATED_IDN_VAR_MACRO_PROCESSO
        );

        // Get all the macroProcessoList where idnVarMacroProcesso equals to UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("idnVarMacroProcesso.in=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarMacroProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarMacroProcesso is not null
        defaultMacroProcessoShouldBeFound("idnVarMacroProcesso.specified=true");

        // Get all the macroProcessoList where idnVarMacroProcesso is null
        defaultMacroProcessoShouldNotBeFound("idnVarMacroProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarMacroProcessoContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarMacroProcesso contains DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("idnVarMacroProcesso.contains=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoList where idnVarMacroProcesso contains UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("idnVarMacroProcesso.contains=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarMacroProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarMacroProcesso does not contain DEFAULT_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("idnVarMacroProcesso.doesNotContain=" + DEFAULT_IDN_VAR_MACRO_PROCESSO);

        // Get all the macroProcessoList where idnVarMacroProcesso does not contain UPDATED_IDN_VAR_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("idnVarMacroProcesso.doesNotContain=" + UPDATED_IDN_VAR_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosBynVarNomeMacroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where nVarNomeMacroProcesso equals to DEFAULT_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("nVarNomeMacroProcesso.equals=" + DEFAULT_N_VAR_NOME_MACRO_PROCESSO);

        // Get all the macroProcessoList where nVarNomeMacroProcesso equals to UPDATED_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("nVarNomeMacroProcesso.equals=" + UPDATED_N_VAR_NOME_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosBynVarNomeMacroProcessoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where nVarNomeMacroProcesso not equals to DEFAULT_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("nVarNomeMacroProcesso.notEquals=" + DEFAULT_N_VAR_NOME_MACRO_PROCESSO);

        // Get all the macroProcessoList where nVarNomeMacroProcesso not equals to UPDATED_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("nVarNomeMacroProcesso.notEquals=" + UPDATED_N_VAR_NOME_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosBynVarNomeMacroProcessoIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where nVarNomeMacroProcesso in DEFAULT_N_VAR_NOME_MACRO_PROCESSO or UPDATED_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound(
            "nVarNomeMacroProcesso.in=" + DEFAULT_N_VAR_NOME_MACRO_PROCESSO + "," + UPDATED_N_VAR_NOME_MACRO_PROCESSO
        );

        // Get all the macroProcessoList where nVarNomeMacroProcesso equals to UPDATED_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("nVarNomeMacroProcesso.in=" + UPDATED_N_VAR_NOME_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosBynVarNomeMacroProcessoIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where nVarNomeMacroProcesso is not null
        defaultMacroProcessoShouldBeFound("nVarNomeMacroProcesso.specified=true");

        // Get all the macroProcessoList where nVarNomeMacroProcesso is null
        defaultMacroProcessoShouldNotBeFound("nVarNomeMacroProcesso.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessosBynVarNomeMacroProcessoContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where nVarNomeMacroProcesso contains DEFAULT_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("nVarNomeMacroProcesso.contains=" + DEFAULT_N_VAR_NOME_MACRO_PROCESSO);

        // Get all the macroProcessoList where nVarNomeMacroProcesso contains UPDATED_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("nVarNomeMacroProcesso.contains=" + UPDATED_N_VAR_NOME_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosBynVarNomeMacroProcessoNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where nVarNomeMacroProcesso does not contain DEFAULT_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldNotBeFound("nVarNomeMacroProcesso.doesNotContain=" + DEFAULT_N_VAR_NOME_MACRO_PROCESSO);

        // Get all the macroProcessoList where nVarNomeMacroProcesso does not contain UPDATED_N_VAR_NOME_MACRO_PROCESSO
        defaultMacroProcessoShouldBeFound("nVarNomeMacroProcesso.doesNotContain=" + UPDATED_N_VAR_NOME_MACRO_PROCESSO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the macroProcessoList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarEmpresa is not null
        defaultMacroProcessoShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the macroProcessoList where idnVarEmpresa is null
        defaultMacroProcessoShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the macroProcessoList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultMacroProcessoShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarUsuario equals to DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoShouldBeFound("idnVarUsuario.equals=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoList where idnVarUsuario equals to UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoShouldNotBeFound("idnVarUsuario.equals=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarUsuario not equals to DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoShouldNotBeFound("idnVarUsuario.notEquals=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoList where idnVarUsuario not equals to UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoShouldBeFound("idnVarUsuario.notEquals=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarUsuario in DEFAULT_IDN_VAR_USUARIO or UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoShouldBeFound("idnVarUsuario.in=" + DEFAULT_IDN_VAR_USUARIO + "," + UPDATED_IDN_VAR_USUARIO);

        // Get all the macroProcessoList where idnVarUsuario equals to UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoShouldNotBeFound("idnVarUsuario.in=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarUsuario is not null
        defaultMacroProcessoShouldBeFound("idnVarUsuario.specified=true");

        // Get all the macroProcessoList where idnVarUsuario is null
        defaultMacroProcessoShouldNotBeFound("idnVarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarUsuario contains DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoShouldBeFound("idnVarUsuario.contains=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoList where idnVarUsuario contains UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoShouldNotBeFound("idnVarUsuario.contains=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByIdnVarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        // Get all the macroProcessoList where idnVarUsuario does not contain DEFAULT_IDN_VAR_USUARIO
        defaultMacroProcessoShouldNotBeFound("idnVarUsuario.doesNotContain=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the macroProcessoList where idnVarUsuario does not contain UPDATED_IDN_VAR_USUARIO
        defaultMacroProcessoShouldBeFound("idnVarUsuario.doesNotContain=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllMacroProcessosByMacroProcessoOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);
        MacroProcessoOrganograma macroProcessoOrganograma;
        if (TestUtil.findAll(em, MacroProcessoOrganograma.class).isEmpty()) {
            macroProcessoOrganograma = MacroProcessoOrganogramaResourceIT.createEntity(em);
            em.persist(macroProcessoOrganograma);
            em.flush();
        } else {
            macroProcessoOrganograma = TestUtil.findAll(em, MacroProcessoOrganograma.class).get(0);
        }
        em.persist(macroProcessoOrganograma);
        em.flush();
        macroProcesso.addMacroProcessoOrganograma(macroProcessoOrganograma);
        macroProcessoRepository.saveAndFlush(macroProcesso);
        Long macroProcessoOrganogramaId = macroProcessoOrganograma.getId();

        // Get all the macroProcessoList where macroProcessoOrganograma equals to macroProcessoOrganogramaId
        defaultMacroProcessoShouldBeFound("macroProcessoOrganogramaId.equals=" + macroProcessoOrganogramaId);

        // Get all the macroProcessoList where macroProcessoOrganograma equals to (macroProcessoOrganogramaId + 1)
        defaultMacroProcessoShouldNotBeFound("macroProcessoOrganogramaId.equals=" + (macroProcessoOrganogramaId + 1));
    }

    @Test
    @Transactional
    void getAllMacroProcessosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);
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
        macroProcesso.setEmpresa(empresa);
        macroProcessoRepository.saveAndFlush(macroProcesso);
        Long empresaId = empresa.getId();

        // Get all the macroProcessoList where empresa equals to empresaId
        defaultMacroProcessoShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the macroProcessoList where empresa equals to (empresaId + 1)
        defaultMacroProcessoShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllMacroProcessosByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);
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
        macroProcesso.setUsuario(usuario);
        macroProcessoRepository.saveAndFlush(macroProcesso);
        Long usuarioId = usuario.getId();

        // Get all the macroProcessoList where usuario equals to usuarioId
        defaultMacroProcessoShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the macroProcessoList where usuario equals to (usuarioId + 1)
        defaultMacroProcessoShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMacroProcessoShouldBeFound(String filter) throws Exception {
        restMacroProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(macroProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarMacroProcesso").value(hasItem(DEFAULT_IDN_VAR_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].nVarNomeMacroProcesso").value(hasItem(DEFAULT_N_VAR_NOME_MACRO_PROCESSO)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnVarUsuario").value(hasItem(DEFAULT_IDN_VAR_USUARIO)));

        // Check, that the count call also returns 1
        restMacroProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMacroProcessoShouldNotBeFound(String filter) throws Exception {
        restMacroProcessoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMacroProcessoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMacroProcesso() throws Exception {
        // Get the macroProcesso
        restMacroProcessoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMacroProcesso() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();

        // Update the macroProcesso
        MacroProcesso updatedMacroProcesso = macroProcessoRepository.findById(macroProcesso.getId()).get();
        // Disconnect from session so that the updates on updatedMacroProcesso are not directly saved in db
        em.detach(updatedMacroProcesso);
        updatedMacroProcesso
            .idnVarMacroProcesso(UPDATED_IDN_VAR_MACRO_PROCESSO)
            .nVarNomeMacroProcesso(UPDATED_N_VAR_NOME_MACRO_PROCESSO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO);
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(updatedMacroProcesso);

        restMacroProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, macroProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isOk());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
        MacroProcesso testMacroProcesso = macroProcessoList.get(macroProcessoList.size() - 1);
        assertThat(testMacroProcesso.getIdnVarMacroProcesso()).isEqualTo(UPDATED_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getnVarNomeMacroProcesso()).isEqualTo(UPDATED_N_VAR_NOME_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testMacroProcesso.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingMacroProcesso() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();
        macroProcesso.setId(count.incrementAndGet());

        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMacroProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, macroProcessoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMacroProcesso() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();
        macroProcesso.setId(count.incrementAndGet());

        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMacroProcesso() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();
        macroProcesso.setId(count.incrementAndGet());

        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMacroProcessoWithPatch() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();

        // Update the macroProcesso using partial update
        MacroProcesso partialUpdatedMacroProcesso = new MacroProcesso();
        partialUpdatedMacroProcesso.setId(macroProcesso.getId());

        partialUpdatedMacroProcesso.idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA).idnVarUsuario(UPDATED_IDN_VAR_USUARIO);

        restMacroProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMacroProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMacroProcesso))
            )
            .andExpect(status().isOk());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
        MacroProcesso testMacroProcesso = macroProcessoList.get(macroProcessoList.size() - 1);
        assertThat(testMacroProcesso.getIdnVarMacroProcesso()).isEqualTo(DEFAULT_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getnVarNomeMacroProcesso()).isEqualTo(DEFAULT_N_VAR_NOME_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testMacroProcesso.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateMacroProcessoWithPatch() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();

        // Update the macroProcesso using partial update
        MacroProcesso partialUpdatedMacroProcesso = new MacroProcesso();
        partialUpdatedMacroProcesso.setId(macroProcesso.getId());

        partialUpdatedMacroProcesso
            .idnVarMacroProcesso(UPDATED_IDN_VAR_MACRO_PROCESSO)
            .nVarNomeMacroProcesso(UPDATED_N_VAR_NOME_MACRO_PROCESSO)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO);

        restMacroProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMacroProcesso.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMacroProcesso))
            )
            .andExpect(status().isOk());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
        MacroProcesso testMacroProcesso = macroProcessoList.get(macroProcessoList.size() - 1);
        assertThat(testMacroProcesso.getIdnVarMacroProcesso()).isEqualTo(UPDATED_IDN_VAR_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getnVarNomeMacroProcesso()).isEqualTo(UPDATED_N_VAR_NOME_MACRO_PROCESSO);
        assertThat(testMacroProcesso.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testMacroProcesso.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingMacroProcesso() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();
        macroProcesso.setId(count.incrementAndGet());

        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMacroProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, macroProcessoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMacroProcesso() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();
        macroProcesso.setId(count.incrementAndGet());

        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMacroProcesso() throws Exception {
        int databaseSizeBeforeUpdate = macroProcessoRepository.findAll().size();
        macroProcesso.setId(count.incrementAndGet());

        // Create the MacroProcesso
        MacroProcessoDTO macroProcessoDTO = macroProcessoMapper.toDto(macroProcesso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMacroProcessoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(macroProcessoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MacroProcesso in the database
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMacroProcesso() throws Exception {
        // Initialize the database
        macroProcessoRepository.saveAndFlush(macroProcesso);

        int databaseSizeBeforeDelete = macroProcessoRepository.findAll().size();

        // Delete the macroProcesso
        restMacroProcessoMockMvc
            .perform(delete(ENTITY_API_URL_ID, macroProcesso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MacroProcesso> macroProcessoList = macroProcessoRepository.findAll();
        assertThat(macroProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
