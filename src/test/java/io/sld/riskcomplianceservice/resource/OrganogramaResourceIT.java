package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.TestUtil;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.OrganogramaRepository;
import io.sld.riskcomplianceservice.domain.service.dto.OrganogramaDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.OrganogramaMapper;
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
 * Integration tests for the {@link OrganogramaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
//@WithMockUser
class OrganogramaResourceIT {

    private static final String DEFAULT_IDN_VAR_ORGANOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_ORGANOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_PAI_ORGANOGRAMA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_PAI_ORGANOGRAMA = "BBBBBBBBBB";

    private static final String DEFAULT_IDNVAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDNVAR_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_ROOF_TOP = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_ROOF_TOP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organogramas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganogramaRepository organogramaRepository;

    @Autowired
    private OrganogramaMapper organogramaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganogramaMockMvc;

    private Organograma organograma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organograma createEntity(EntityManager em) {
        Organograma organograma = new Organograma()
            .idnVarOrganograma(DEFAULT_IDN_VAR_ORGANOGRAMA)
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO)
            .idnVarPaiOrganograma(DEFAULT_IDN_VAR_PAI_ORGANOGRAMA)
            .idnvarUsuario(DEFAULT_IDNVAR_USUARIO)
            .idnVarRoofTop(DEFAULT_IDN_VAR_ROOF_TOP);
        return organograma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organograma createUpdatedEntity(EntityManager em) {
        Organograma organograma = new Organograma()
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarPaiOrganograma(UPDATED_IDN_VAR_PAI_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO)
            .idnVarRoofTop(UPDATED_IDN_VAR_ROOF_TOP);
        return organograma;
    }

    @BeforeEach
    public void initTest() {
        organograma = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganograma() throws Exception {
        int databaseSizeBeforeCreate = organogramaRepository.findAll().size();
        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);
        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeCreate + 1);
        Organograma testOrganograma = organogramaList.get(organogramaList.size() - 1);
        assertThat(testOrganograma.getIdnVarOrganograma()).isEqualTo(DEFAULT_IDN_VAR_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testOrganograma.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testOrganograma.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
        assertThat(testOrganograma.getIdnVarPaiOrganograma()).isEqualTo(DEFAULT_IDN_VAR_PAI_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnvarUsuario()).isEqualTo(DEFAULT_IDNVAR_USUARIO);
        assertThat(testOrganograma.getIdnVarRoofTop()).isEqualTo(DEFAULT_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void createOrganogramaWithExistingId() throws Exception {
        // Create the Organograma with an existing ID
        organograma.setId(1L);
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        int databaseSizeBeforeCreate = organogramaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarOrganogramaIsRequired() throws Exception {
        int databaseSizeBeforeTest = organogramaRepository.findAll().size();
        // set the field null
        organograma.setIdnVarOrganograma(null);

        // Create the Organograma, which fails.
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = organogramaRepository.findAll().size();
        // set the field null
        organograma.setIdnVarEmpresa(null);

        // Create the Organograma, which fails.
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = organogramaRepository.findAll().size();
        // set the field null
        organograma.setnVarNome(null);

        // Create the Organograma, which fails.
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnvarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = organogramaRepository.findAll().size();
        // set the field null
        organograma.setIdnvarUsuario(null);

        // Create the Organograma, which fails.
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdnVarRoofTopIsRequired() throws Exception {
        int databaseSizeBeforeTest = organogramaRepository.findAll().size();
        // set the field null
        organograma.setIdnVarRoofTop(null);

        // Create the Organograma, which fails.
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        restOrganogramaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganogramas() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList
        restOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarPaiOrganograma").value(hasItem(DEFAULT_IDN_VAR_PAI_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)))
            .andExpect(jsonPath("$.[*].idnVarRoofTop").value(hasItem(DEFAULT_IDN_VAR_ROOF_TOP)));
    }

    @Test
    @Transactional
    void getOrganograma() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get the organograma
        restOrganogramaMockMvc
            .perform(get(ENTITY_API_URL_ID, organograma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organograma.getId().intValue()))
            .andExpect(jsonPath("$.idnVarOrganograma").value(DEFAULT_IDN_VAR_ORGANOGRAMA))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO))
            .andExpect(jsonPath("$.idnVarPaiOrganograma").value(DEFAULT_IDN_VAR_PAI_ORGANOGRAMA))
            .andExpect(jsonPath("$.idnvarUsuario").value(DEFAULT_IDNVAR_USUARIO))
            .andExpect(jsonPath("$.idnVarRoofTop").value(DEFAULT_IDN_VAR_ROOF_TOP));
    }

    @Test
    @Transactional
    void getOrganogramasByIdFiltering() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        Long id = organograma.getId();

        defaultOrganogramaShouldBeFound("id.equals=" + id);
        defaultOrganogramaShouldNotBeFound("id.notEquals=" + id);

        defaultOrganogramaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrganogramaShouldNotBeFound("id.greaterThan=" + id);

        defaultOrganogramaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrganogramaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarOrganograma equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarOrganograma.equals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the organogramaList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarOrganograma.equals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarOrganogramaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarOrganograma not equals to DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarOrganograma.notEquals=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the organogramaList where idnVarOrganograma not equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarOrganograma.notEquals=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarOrganogramaIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarOrganograma in DEFAULT_IDN_VAR_ORGANOGRAMA or UPDATED_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarOrganograma.in=" + DEFAULT_IDN_VAR_ORGANOGRAMA + "," + UPDATED_IDN_VAR_ORGANOGRAMA);

        // Get all the organogramaList where idnVarOrganograma equals to UPDATED_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarOrganograma.in=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarOrganogramaIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarOrganograma is not null
        defaultOrganogramaShouldBeFound("idnVarOrganograma.specified=true");

        // Get all the organogramaList where idnVarOrganograma is null
        defaultOrganogramaShouldNotBeFound("idnVarOrganograma.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarOrganogramaContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarOrganograma contains DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarOrganograma.contains=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the organogramaList where idnVarOrganograma contains UPDATED_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarOrganograma.contains=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarOrganogramaNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarOrganograma does not contain DEFAULT_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarOrganograma.doesNotContain=" + DEFAULT_IDN_VAR_ORGANOGRAMA);

        // Get all the organogramaList where idnVarOrganograma does not contain UPDATED_IDN_VAR_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarOrganograma.doesNotContain=" + UPDATED_IDN_VAR_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultOrganogramaShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the organogramaList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultOrganogramaShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultOrganogramaShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the organogramaList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultOrganogramaShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultOrganogramaShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the organogramaList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultOrganogramaShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarEmpresa is not null
        defaultOrganogramaShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the organogramaList where idnVarEmpresa is null
        defaultOrganogramaShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultOrganogramaShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the organogramaList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultOrganogramaShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultOrganogramaShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the organogramaList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultOrganogramaShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultOrganogramaShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the organogramaList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultOrganogramaShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultOrganogramaShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the organogramaList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultOrganogramaShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultOrganogramaShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the organogramaList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultOrganogramaShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarNome is not null
        defaultOrganogramaShouldBeFound("nVarNome.specified=true");

        // Get all the organogramaList where nVarNome is null
        defaultOrganogramaShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultOrganogramaShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the organogramaList where nVarNome contains UPDATED_N_VAR_NOME
        defaultOrganogramaShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultOrganogramaShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the organogramaList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultOrganogramaShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultOrganogramaShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the organogramaList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultOrganogramaShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultOrganogramaShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the organogramaList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultOrganogramaShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultOrganogramaShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the organogramaList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultOrganogramaShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarDescricao is not null
        defaultOrganogramaShouldBeFound("nVarDescricao.specified=true");

        // Get all the organogramaList where nVarDescricao is null
        defaultOrganogramaShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultOrganogramaShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the organogramaList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultOrganogramaShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOrganogramasBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultOrganogramaShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the organogramaList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultOrganogramaShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarPaiOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarPaiOrganograma equals to DEFAULT_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarPaiOrganograma.equals=" + DEFAULT_IDN_VAR_PAI_ORGANOGRAMA);

        // Get all the organogramaList where idnVarPaiOrganograma equals to UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarPaiOrganograma.equals=" + UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarPaiOrganogramaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarPaiOrganograma not equals to DEFAULT_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarPaiOrganograma.notEquals=" + DEFAULT_IDN_VAR_PAI_ORGANOGRAMA);

        // Get all the organogramaList where idnVarPaiOrganograma not equals to UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarPaiOrganograma.notEquals=" + UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarPaiOrganogramaIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarPaiOrganograma in DEFAULT_IDN_VAR_PAI_ORGANOGRAMA or UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldBeFound(
            "idnVarPaiOrganograma.in=" + DEFAULT_IDN_VAR_PAI_ORGANOGRAMA + "," + UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        );

        // Get all the organogramaList where idnVarPaiOrganograma equals to UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarPaiOrganograma.in=" + UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarPaiOrganogramaIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarPaiOrganograma is not null
        defaultOrganogramaShouldBeFound("idnVarPaiOrganograma.specified=true");

        // Get all the organogramaList where idnVarPaiOrganograma is null
        defaultOrganogramaShouldNotBeFound("idnVarPaiOrganograma.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarPaiOrganogramaContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarPaiOrganograma contains DEFAULT_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarPaiOrganograma.contains=" + DEFAULT_IDN_VAR_PAI_ORGANOGRAMA);

        // Get all the organogramaList where idnVarPaiOrganograma contains UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarPaiOrganograma.contains=" + UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarPaiOrganogramaNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarPaiOrganograma does not contain DEFAULT_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldNotBeFound("idnVarPaiOrganograma.doesNotContain=" + DEFAULT_IDN_VAR_PAI_ORGANOGRAMA);

        // Get all the organogramaList where idnVarPaiOrganograma does not contain UPDATED_IDN_VAR_PAI_ORGANOGRAMA
        defaultOrganogramaShouldBeFound("idnVarPaiOrganograma.doesNotContain=" + UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnvarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnvarUsuario equals to DEFAULT_IDNVAR_USUARIO
        defaultOrganogramaShouldBeFound("idnvarUsuario.equals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the organogramaList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultOrganogramaShouldNotBeFound("idnvarUsuario.equals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnvarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnvarUsuario not equals to DEFAULT_IDNVAR_USUARIO
        defaultOrganogramaShouldNotBeFound("idnvarUsuario.notEquals=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the organogramaList where idnvarUsuario not equals to UPDATED_IDNVAR_USUARIO
        defaultOrganogramaShouldBeFound("idnvarUsuario.notEquals=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnvarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnvarUsuario in DEFAULT_IDNVAR_USUARIO or UPDATED_IDNVAR_USUARIO
        defaultOrganogramaShouldBeFound("idnvarUsuario.in=" + DEFAULT_IDNVAR_USUARIO + "," + UPDATED_IDNVAR_USUARIO);

        // Get all the organogramaList where idnvarUsuario equals to UPDATED_IDNVAR_USUARIO
        defaultOrganogramaShouldNotBeFound("idnvarUsuario.in=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnvarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnvarUsuario is not null
        defaultOrganogramaShouldBeFound("idnvarUsuario.specified=true");

        // Get all the organogramaList where idnvarUsuario is null
        defaultOrganogramaShouldNotBeFound("idnvarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnvarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnvarUsuario contains DEFAULT_IDNVAR_USUARIO
        defaultOrganogramaShouldBeFound("idnvarUsuario.contains=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the organogramaList where idnvarUsuario contains UPDATED_IDNVAR_USUARIO
        defaultOrganogramaShouldNotBeFound("idnvarUsuario.contains=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnvarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnvarUsuario does not contain DEFAULT_IDNVAR_USUARIO
        defaultOrganogramaShouldNotBeFound("idnvarUsuario.doesNotContain=" + DEFAULT_IDNVAR_USUARIO);

        // Get all the organogramaList where idnvarUsuario does not contain UPDATED_IDNVAR_USUARIO
        defaultOrganogramaShouldBeFound("idnvarUsuario.doesNotContain=" + UPDATED_IDNVAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarRoofTopIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarRoofTop equals to DEFAULT_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldBeFound("idnVarRoofTop.equals=" + DEFAULT_IDN_VAR_ROOF_TOP);

        // Get all the organogramaList where idnVarRoofTop equals to UPDATED_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldNotBeFound("idnVarRoofTop.equals=" + UPDATED_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarRoofTopIsNotEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarRoofTop not equals to DEFAULT_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldNotBeFound("idnVarRoofTop.notEquals=" + DEFAULT_IDN_VAR_ROOF_TOP);

        // Get all the organogramaList where idnVarRoofTop not equals to UPDATED_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldBeFound("idnVarRoofTop.notEquals=" + UPDATED_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarRoofTopIsInShouldWork() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarRoofTop in DEFAULT_IDN_VAR_ROOF_TOP or UPDATED_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldBeFound("idnVarRoofTop.in=" + DEFAULT_IDN_VAR_ROOF_TOP + "," + UPDATED_IDN_VAR_ROOF_TOP);

        // Get all the organogramaList where idnVarRoofTop equals to UPDATED_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldNotBeFound("idnVarRoofTop.in=" + UPDATED_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarRoofTopIsNullOrNotNull() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarRoofTop is not null
        defaultOrganogramaShouldBeFound("idnVarRoofTop.specified=true");

        // Get all the organogramaList where idnVarRoofTop is null
        defaultOrganogramaShouldNotBeFound("idnVarRoofTop.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarRoofTopContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarRoofTop contains DEFAULT_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldBeFound("idnVarRoofTop.contains=" + DEFAULT_IDN_VAR_ROOF_TOP);

        // Get all the organogramaList where idnVarRoofTop contains UPDATED_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldNotBeFound("idnVarRoofTop.contains=" + UPDATED_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void getAllOrganogramasByIdnVarRoofTopNotContainsSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        // Get all the organogramaList where idnVarRoofTop does not contain DEFAULT_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldNotBeFound("idnVarRoofTop.doesNotContain=" + DEFAULT_IDN_VAR_ROOF_TOP);

        // Get all the organogramaList where idnVarRoofTop does not contain UPDATED_IDN_VAR_ROOF_TOP
        defaultOrganogramaShouldBeFound("idnVarRoofTop.doesNotContain=" + UPDATED_IDN_VAR_ROOF_TOP);
    }





    @Test
    @Transactional
    void getAllOrganogramasByFuncionarioOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);
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
        organograma.addFuncionarioOrganograma(funcionarioOrganograma);
        organogramaRepository.saveAndFlush(organograma);
        Long funcionarioOrganogramaId = funcionarioOrganograma.getId();

        // Get all the organogramaList where funcionarioOrganograma equals to funcionarioOrganogramaId
        defaultOrganogramaShouldBeFound("funcionarioOrganogramaId.equals=" + funcionarioOrganogramaId);

        // Get all the organogramaList where funcionarioOrganograma equals to (funcionarioOrganogramaId + 1)
        defaultOrganogramaShouldNotBeFound("funcionarioOrganogramaId.equals=" + (funcionarioOrganogramaId + 1));
    }

    @Test
    @Transactional
    void getAllOrganogramasByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);
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
        organograma.setEmpresa(empresa);
        organogramaRepository.saveAndFlush(organograma);
        Long empresaId = empresa.getId();

        // Get all the organogramaList where empresa equals to empresaId
        defaultOrganogramaShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the organogramaList where empresa equals to (empresaId + 1)
        defaultOrganogramaShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    @Test
    @Transactional
    void getAllOrganogramasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);
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
        organograma.setUsuario(usuario);
        organogramaRepository.saveAndFlush(organograma);
        Long usuarioId = usuario.getId();

        // Get all the organogramaList where usuario equals to usuarioId
        defaultOrganogramaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the organogramaList where usuario equals to (usuarioId + 1)
        defaultOrganogramaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganogramaShouldBeFound(String filter) throws Exception {
        restOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organograma.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarOrganograma").value(hasItem(DEFAULT_IDN_VAR_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)))
            .andExpect(jsonPath("$.[*].idnVarPaiOrganograma").value(hasItem(DEFAULT_IDN_VAR_PAI_ORGANOGRAMA)))
            .andExpect(jsonPath("$.[*].idnvarUsuario").value(hasItem(DEFAULT_IDNVAR_USUARIO)))
            .andExpect(jsonPath("$.[*].idnVarRoofTop").value(hasItem(DEFAULT_IDN_VAR_ROOF_TOP)));

        // Check, that the count call also returns 1
        restOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganogramaShouldNotBeFound(String filter) throws Exception {
        restOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganogramaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganograma() throws Exception {
        // Get the organograma
        restOrganogramaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrganograma() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();

        // Update the organograma
        Organograma updatedOrganograma = organogramaRepository.findById(organograma.getId()).get();
        // Disconnect from session so that the updates on updatedOrganograma are not directly saved in db
        em.detach(updatedOrganograma);
        updatedOrganograma
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarPaiOrganograma(UPDATED_IDN_VAR_PAI_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO)
            .idnVarRoofTop(UPDATED_IDN_VAR_ROOF_TOP);
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(updatedOrganograma);

        restOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organogramaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
        Organograma testOrganograma = organogramaList.get(organogramaList.size() - 1);
        assertThat(testOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testOrganograma.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testOrganograma.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testOrganograma.getIdnVarPaiOrganograma()).isEqualTo(UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
        assertThat(testOrganograma.getIdnVarRoofTop()).isEqualTo(UPDATED_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void putNonExistingOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();
        organograma.setId(count.incrementAndGet());

        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organogramaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();
        organograma.setId(count.incrementAndGet());

        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganogramaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();
        organograma.setId(count.incrementAndGet());

        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganogramaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organogramaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganogramaWithPatch() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();

        // Update the organograma using partial update
        Organograma partialUpdatedOrganograma = new Organograma();
        partialUpdatedOrganograma.setId(organograma.getId());

        partialUpdatedOrganograma
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO);

        restOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganograma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganograma))
            )
            .andExpect(status().isOk());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
        Organograma testOrganograma = organogramaList.get(organogramaList.size() - 1);
        assertThat(testOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testOrganograma.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testOrganograma.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testOrganograma.getIdnVarPaiOrganograma()).isEqualTo(DEFAULT_IDN_VAR_PAI_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
        assertThat(testOrganograma.getIdnVarRoofTop()).isEqualTo(DEFAULT_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void fullUpdateOrganogramaWithPatch() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();

        // Update the organograma using partial update
        Organograma partialUpdatedOrganograma = new Organograma();
        partialUpdatedOrganograma.setId(organograma.getId());

        partialUpdatedOrganograma
            .idnVarOrganograma(UPDATED_IDN_VAR_ORGANOGRAMA)
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO)
            .idnVarPaiOrganograma(UPDATED_IDN_VAR_PAI_ORGANOGRAMA)
            .idnvarUsuario(UPDATED_IDNVAR_USUARIO)
            .idnVarRoofTop(UPDATED_IDN_VAR_ROOF_TOP);

        restOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganograma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganograma))
            )
            .andExpect(status().isOk());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
        Organograma testOrganograma = organogramaList.get(organogramaList.size() - 1);
        assertThat(testOrganograma.getIdnVarOrganograma()).isEqualTo(UPDATED_IDN_VAR_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testOrganograma.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testOrganograma.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
        assertThat(testOrganograma.getIdnVarPaiOrganograma()).isEqualTo(UPDATED_IDN_VAR_PAI_ORGANOGRAMA);
        assertThat(testOrganograma.getIdnvarUsuario()).isEqualTo(UPDATED_IDNVAR_USUARIO);
        assertThat(testOrganograma.getIdnVarRoofTop()).isEqualTo(UPDATED_IDN_VAR_ROOF_TOP);
    }

    @Test
    @Transactional
    void patchNonExistingOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();
        organograma.setId(count.incrementAndGet());

        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organogramaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();
        organograma.setId(count.incrementAndGet());

        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganograma() throws Exception {
        int databaseSizeBeforeUpdate = organogramaRepository.findAll().size();
        organograma.setId(count.incrementAndGet());

        // Create the Organograma
        OrganogramaDTO organogramaDTO = organogramaMapper.toDto(organograma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganogramaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(organogramaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organograma in the database
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganograma() throws Exception {
        // Initialize the database
        organogramaRepository.saveAndFlush(organograma);

        int databaseSizeBeforeDelete = organogramaRepository.findAll().size();

        // Delete the organograma
        restOrganogramaMockMvc
            .perform(delete(ENTITY_API_URL_ID, organograma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organograma> organogramaList = organogramaRepository.findAll();
        assertThat(organogramaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
