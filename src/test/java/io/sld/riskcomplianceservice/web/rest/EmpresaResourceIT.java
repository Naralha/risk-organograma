package io.sld.riskcomplianceservice.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.domain.ClienteExterno;
import io.sld.riskcomplianceservice.domain.ComplianceExterno;
import io.sld.riskcomplianceservice.domain.ComplianceInterno;
import io.sld.riskcomplianceservice.domain.Empresa;
import io.sld.riskcomplianceservice.domain.FornecedorExterno;
import io.sld.riskcomplianceservice.domain.Funcionario;
import io.sld.riskcomplianceservice.domain.MacroProcesso;
import io.sld.riskcomplianceservice.domain.MacroProcessoOrganograma;
import io.sld.riskcomplianceservice.domain.Organograma;
import io.sld.riskcomplianceservice.domain.Processo;
import io.sld.riskcomplianceservice.domain.Usuario;
import io.sld.riskcomplianceservice.repository.EmpresaRepository;
import io.sld.riskcomplianceservice.service.criteria.EmpresaCriteria;
import io.sld.riskcomplianceservice.service.dto.EmpresaDTO;
import io.sld.riskcomplianceservice.service.mapper.EmpresaMapper;
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
 * Integration tests for the {@link EmpresaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmpresaResourceIT {

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_DESCRICAO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/empresas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaMapper empresaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmpresaMockMvc;

    private Empresa empresa;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .idnVarEmpresa(DEFAULT_IDN_VAR_EMPRESA)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .nVarDescricao(DEFAULT_N_VAR_DESCRICAO);
        return empresa;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Empresa createUpdatedEntity(EntityManager em) {
        Empresa empresa = new Empresa()
            .idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA)
            .nVarNome(UPDATED_N_VAR_NOME)
            .nVarDescricao(UPDATED_N_VAR_DESCRICAO);
        return empresa;
    }

    @BeforeEach
    public void initTest() {
        empresa = createEntity(em);
    }

    @Test
    @Transactional
    void createEmpresa() throws Exception {
        int databaseSizeBeforeCreate = empresaRepository.findAll().size();
        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isCreated());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate + 1);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getIdnVarEmpresa()).isEqualTo(DEFAULT_IDN_VAR_EMPRESA);
        assertThat(testEmpresa.getnVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testEmpresa.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void createEmpresaWithExistingId() throws Exception {
        // Create the Empresa with an existing ID
        empresa.setId(1L);
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        int databaseSizeBeforeCreate = empresaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setIdnVarEmpresa(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = empresaRepository.findAll().size();
        // set the field null
        empresa.setnVarNome(null);

        // Create the Empresa, which fails.
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        restEmpresaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isBadRequest());

        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmpresas() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)));
    }

    @Test
    @Transactional
    void getEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get the empresa
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL_ID, empresa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(empresa.getId().intValue()))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.nVarDescricao").value(DEFAULT_N_VAR_DESCRICAO));
    }

    @Test
    @Transactional
    void getEmpresasByIdFiltering() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        Long id = empresa.getId();

        defaultEmpresaShouldBeFound("id.equals=" + id);
        defaultEmpresaShouldNotBeFound("id.notEquals=" + id);

        defaultEmpresaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.greaterThan=" + id);

        defaultEmpresaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmpresaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmpresasByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultEmpresaShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the empresaList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultEmpresaShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllEmpresasByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultEmpresaShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the empresaList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultEmpresaShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllEmpresasByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultEmpresaShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the empresaList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultEmpresaShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllEmpresasByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where idnVarEmpresa is not null
        defaultEmpresaShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the empresaList where idnVarEmpresa is null
        defaultEmpresaShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultEmpresaShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the empresaList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultEmpresaShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllEmpresasByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultEmpresaShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the empresaList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultEmpresaShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultEmpresaShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the empresaList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultEmpresaShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultEmpresaShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the empresaList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultEmpresaShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultEmpresaShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the empresaList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultEmpresaShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarNome is not null
        defaultEmpresaShouldBeFound("nVarNome.specified=true");

        // Get all the empresaList where nVarNome is null
        defaultEmpresaShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultEmpresaShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the empresaList where nVarNome contains UPDATED_N_VAR_NOME
        defaultEmpresaShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultEmpresaShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the empresaList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultEmpresaShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarDescricaoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarDescricao equals to DEFAULT_N_VAR_DESCRICAO
        defaultEmpresaShouldBeFound("nVarDescricao.equals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the empresaList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultEmpresaShouldNotBeFound("nVarDescricao.equals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarDescricaoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarDescricao not equals to DEFAULT_N_VAR_DESCRICAO
        defaultEmpresaShouldNotBeFound("nVarDescricao.notEquals=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the empresaList where nVarDescricao not equals to UPDATED_N_VAR_DESCRICAO
        defaultEmpresaShouldBeFound("nVarDescricao.notEquals=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarDescricaoIsInShouldWork() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarDescricao in DEFAULT_N_VAR_DESCRICAO or UPDATED_N_VAR_DESCRICAO
        defaultEmpresaShouldBeFound("nVarDescricao.in=" + DEFAULT_N_VAR_DESCRICAO + "," + UPDATED_N_VAR_DESCRICAO);

        // Get all the empresaList where nVarDescricao equals to UPDATED_N_VAR_DESCRICAO
        defaultEmpresaShouldNotBeFound("nVarDescricao.in=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarDescricaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarDescricao is not null
        defaultEmpresaShouldBeFound("nVarDescricao.specified=true");

        // Get all the empresaList where nVarDescricao is null
        defaultEmpresaShouldNotBeFound("nVarDescricao.specified=false");
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarDescricaoContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarDescricao contains DEFAULT_N_VAR_DESCRICAO
        defaultEmpresaShouldBeFound("nVarDescricao.contains=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the empresaList where nVarDescricao contains UPDATED_N_VAR_DESCRICAO
        defaultEmpresaShouldNotBeFound("nVarDescricao.contains=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmpresasBynVarDescricaoNotContainsSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        // Get all the empresaList where nVarDescricao does not contain DEFAULT_N_VAR_DESCRICAO
        defaultEmpresaShouldNotBeFound("nVarDescricao.doesNotContain=" + DEFAULT_N_VAR_DESCRICAO);

        // Get all the empresaList where nVarDescricao does not contain UPDATED_N_VAR_DESCRICAO
        defaultEmpresaShouldBeFound("nVarDescricao.doesNotContain=" + UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void getAllEmpresasByClienteExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addClienteExterno(clienteExterno);
        empresaRepository.saveAndFlush(empresa);
        Long clienteExternoId = clienteExterno.getId();

        // Get all the empresaList where clienteExterno equals to clienteExternoId
        defaultEmpresaShouldBeFound("clienteExternoId.equals=" + clienteExternoId);

        // Get all the empresaList where clienteExterno equals to (clienteExternoId + 1)
        defaultEmpresaShouldNotBeFound("clienteExternoId.equals=" + (clienteExternoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByFornecedorExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addFornecedorExterno(fornecedorExterno);
        empresaRepository.saveAndFlush(empresa);
        Long fornecedorExternoId = fornecedorExterno.getId();

        // Get all the empresaList where fornecedorExterno equals to fornecedorExternoId
        defaultEmpresaShouldBeFound("fornecedorExternoId.equals=" + fornecedorExternoId);

        // Get all the empresaList where fornecedorExterno equals to (fornecedorExternoId + 1)
        defaultEmpresaShouldNotBeFound("fornecedorExternoId.equals=" + (fornecedorExternoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByComplianceExternoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        ComplianceExterno complianceExterno;
        if (TestUtil.findAll(em, ComplianceExterno.class).isEmpty()) {
            complianceExterno = ComplianceExternoResourceIT.createEntity(em);
            em.persist(complianceExterno);
            em.flush();
        } else {
            complianceExterno = TestUtil.findAll(em, ComplianceExterno.class).get(0);
        }
        em.persist(complianceExterno);
        em.flush();
        empresa.addComplianceExterno(complianceExterno);
        empresaRepository.saveAndFlush(empresa);
        Long complianceExternoId = complianceExterno.getId();

        // Get all the empresaList where complianceExterno equals to complianceExternoId
        defaultEmpresaShouldBeFound("complianceExternoId.equals=" + complianceExternoId);

        // Get all the empresaList where complianceExterno equals to (complianceExternoId + 1)
        defaultEmpresaShouldNotBeFound("complianceExternoId.equals=" + (complianceExternoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByComplianceInternoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
        ComplianceInterno complianceInterno;
        if (TestUtil.findAll(em, ComplianceInterno.class).isEmpty()) {
            complianceInterno = ComplianceInternoResourceIT.createEntity(em);
            em.persist(complianceInterno);
            em.flush();
        } else {
            complianceInterno = TestUtil.findAll(em, ComplianceInterno.class).get(0);
        }
        em.persist(complianceInterno);
        em.flush();
        empresa.addComplianceInterno(complianceInterno);
        empresaRepository.saveAndFlush(empresa);
        Long complianceInternoId = complianceInterno.getId();

        // Get all the empresaList where complianceInterno equals to complianceInternoId
        defaultEmpresaShouldBeFound("complianceInternoId.equals=" + complianceInternoId);

        // Get all the empresaList where complianceInterno equals to (complianceInternoId + 1)
        defaultEmpresaShouldNotBeFound("complianceInternoId.equals=" + (complianceInternoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addFuncionario(funcionario);
        empresaRepository.saveAndFlush(empresa);
        Long funcionarioId = funcionario.getId();

        // Get all the empresaList where funcionario equals to funcionarioId
        defaultEmpresaShouldBeFound("funcionarioId.equals=" + funcionarioId);

        // Get all the empresaList where funcionario equals to (funcionarioId + 1)
        defaultEmpresaShouldNotBeFound("funcionarioId.equals=" + (funcionarioId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByMacroProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addMacroProcesso(macroProcesso);
        empresaRepository.saveAndFlush(empresa);
        Long macroProcessoId = macroProcesso.getId();

        // Get all the empresaList where macroProcesso equals to macroProcessoId
        defaultEmpresaShouldBeFound("macroProcessoId.equals=" + macroProcessoId);

        // Get all the empresaList where macroProcesso equals to (macroProcessoId + 1)
        defaultEmpresaShouldNotBeFound("macroProcessoId.equals=" + (macroProcessoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByMacroProcessoOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addMacroProcessoOrganograma(macroProcessoOrganograma);
        empresaRepository.saveAndFlush(empresa);
        Long macroProcessoOrganogramaId = macroProcessoOrganograma.getId();

        // Get all the empresaList where macroProcessoOrganograma equals to macroProcessoOrganogramaId
        defaultEmpresaShouldBeFound("macroProcessoOrganogramaId.equals=" + macroProcessoOrganogramaId);

        // Get all the empresaList where macroProcessoOrganograma equals to (macroProcessoOrganogramaId + 1)
        defaultEmpresaShouldNotBeFound("macroProcessoOrganogramaId.equals=" + (macroProcessoOrganogramaId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addOrganograma(organograma);
        empresaRepository.saveAndFlush(empresa);
        Long organogramaId = organograma.getId();

        // Get all the empresaList where organograma equals to organogramaId
        defaultEmpresaShouldBeFound("organogramaId.equals=" + organogramaId);

        // Get all the empresaList where organograma equals to (organogramaId + 1)
        defaultEmpresaShouldNotBeFound("organogramaId.equals=" + (organogramaId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByProcessoIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addProcesso(processo);
        empresaRepository.saveAndFlush(empresa);
        Long processoId = processo.getId();

        // Get all the empresaList where processo equals to processoId
        defaultEmpresaShouldBeFound("processoId.equals=" + processoId);

        // Get all the empresaList where processo equals to (processoId + 1)
        defaultEmpresaShouldNotBeFound("processoId.equals=" + (processoId + 1));
    }

    @Test
    @Transactional
    void getAllEmpresasByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);
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
        empresa.addUsuario(usuario);
        empresaRepository.saveAndFlush(empresa);
        Long usuarioId = usuario.getId();

        // Get all the empresaList where usuario equals to usuarioId
        defaultEmpresaShouldBeFound("usuarioId.equals=" + usuarioId);

        // Get all the empresaList where usuario equals to (usuarioId + 1)
        defaultEmpresaShouldNotBeFound("usuarioId.equals=" + (usuarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmpresaShouldBeFound(String filter) throws Exception {
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(empresa.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].nVarDescricao").value(hasItem(DEFAULT_N_VAR_DESCRICAO)));

        // Check, that the count call also returns 1
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmpresaShouldNotBeFound(String filter) throws Exception {
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmpresaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmpresa() throws Exception {
        // Get the empresa
        restEmpresaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa
        Empresa updatedEmpresa = empresaRepository.findById(empresa.getId()).get();
        // Disconnect from session so that the updates on updatedEmpresa are not directly saved in db
        em.detach(updatedEmpresa);
        updatedEmpresa.idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA).nVarNome(UPDATED_N_VAR_NOME).nVarDescricao(UPDATED_N_VAR_DESCRICAO);
        EmpresaDTO empresaDTO = empresaMapper.toDto(updatedEmpresa);

        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testEmpresa.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testEmpresa.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void putNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(empresaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa.idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA).nVarNome(UPDATED_N_VAR_NOME);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testEmpresa.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testEmpresa.getnVarDescricao()).isEqualTo(DEFAULT_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void fullUpdateEmpresaWithPatch() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();

        // Update the empresa using partial update
        Empresa partialUpdatedEmpresa = new Empresa();
        partialUpdatedEmpresa.setId(empresa.getId());

        partialUpdatedEmpresa.idnVarEmpresa(UPDATED_IDN_VAR_EMPRESA).nVarNome(UPDATED_N_VAR_NOME).nVarDescricao(UPDATED_N_VAR_DESCRICAO);

        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmpresa.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmpresa))
            )
            .andExpect(status().isOk());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
        Empresa testEmpresa = empresaList.get(empresaList.size() - 1);
        assertThat(testEmpresa.getIdnVarEmpresa()).isEqualTo(UPDATED_IDN_VAR_EMPRESA);
        assertThat(testEmpresa.getnVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testEmpresa.getnVarDescricao()).isEqualTo(UPDATED_N_VAR_DESCRICAO);
    }

    @Test
    @Transactional
    void patchNonExistingEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, empresaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmpresa() throws Exception {
        int databaseSizeBeforeUpdate = empresaRepository.findAll().size();
        empresa.setId(count.incrementAndGet());

        // Create the Empresa
        EmpresaDTO empresaDTO = empresaMapper.toDto(empresa);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmpresaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(empresaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Empresa in the database
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmpresa() throws Exception {
        // Initialize the database
        empresaRepository.saveAndFlush(empresa);

        int databaseSizeBeforeDelete = empresaRepository.findAll().size();

        // Delete the empresa
        restEmpresaMockMvc
            .perform(delete(ENTITY_API_URL_ID, empresa.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Empresa> empresaList = empresaRepository.findAll();
        assertThat(empresaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
