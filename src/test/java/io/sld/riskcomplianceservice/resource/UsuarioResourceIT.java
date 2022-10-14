package io.sld.riskcomplianceservice.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.sld.riskcomplianceservice.IntegrationTest;
import io.sld.riskcomplianceservice.TestUtil;
import io.sld.riskcomplianceservice.domain.entity.Empresa;
import io.sld.riskcomplianceservice.domain.entity.Funcionario;
import io.sld.riskcomplianceservice.domain.entity.FuncionarioOrganograma;
import io.sld.riskcomplianceservice.domain.entity.Organograma;
import io.sld.riskcomplianceservice.domain.entity.Usuario;
import io.sld.riskcomplianceservice.domain.repository.UsuarioRepository;
import io.sld.riskcomplianceservice.domain.service.dto.UsuarioDTO;
import io.sld.riskcomplianceservice.domain.service.mapper.UsuarioMapper;
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
 * Integration tests for the {@link UsuarioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
//@WithMockUser
class UsuarioResourceIT {

    private static final String DEFAULT_IDN_VAR_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_USUARIO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_NOME = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_IDN_VAR_USUARIO_CADASTRO = "AAAAAAAAAA";
    private static final String UPDATED_IDN_VAR_USUARIO_CADASTRO = "BBBBBBBBBB";

    private static final String DEFAULT_N_VAR_SENHA = "AAAAAAAAAA";
    private static final String UPDATED_N_VAR_SENHA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/usuarios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuarioMockMvc;

    private Usuario usuario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .idnVarUsuario(DEFAULT_IDN_VAR_USUARIO)
            .nVarNome(DEFAULT_N_VAR_NOME)
            .idnVarUsuarioCadastro(DEFAULT_IDN_VAR_USUARIO_CADASTRO)
            .nVarSenha(DEFAULT_N_VAR_SENHA);
        return usuario;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuario createUpdatedEntity(EntityManager em) {
        Usuario usuario = new Usuario()
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO)
            .nVarSenha(UPDATED_N_VAR_SENHA);
        return usuario;
    }

    @BeforeEach
    public void initTest() {
        usuario = createEntity(em);
    }

    @Test
    @Transactional
    void createUsuario() throws Exception {
        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();
        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate + 1);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getIdnVarUsuario()).isEqualTo(DEFAULT_IDN_VAR_USUARIO);
        assertThat(testUsuario.getNVarNome()).isEqualTo(DEFAULT_N_VAR_NOME);
        assertThat(testUsuario.getIdnVarUsuarioCadastro()).isEqualTo(DEFAULT_IDN_VAR_USUARIO_CADASTRO);
        assertThat(testUsuario.getNVarSenha()).isEqualTo(DEFAULT_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void createUsuarioWithExistingId() throws Exception {
        // Create the Usuario with an existing ID
        usuario.setId(1L);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdnVarUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setIdnVarUsuario(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNVarNome(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checknVarSenhaIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuarioRepository.findAll().size();
        // set the field null
        usuario.setNVarSenha(null);

        // Create the Usuario, which fails.
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        restUsuarioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isBadRequest());

        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUsuarios() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarUsuario").value(hasItem(DEFAULT_IDN_VAR_USUARIO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnVarUsuarioCadastro").value(hasItem(DEFAULT_IDN_VAR_USUARIO_CADASTRO)))
            .andExpect(jsonPath("$.[*].nVarSenha").value(hasItem(DEFAULT_N_VAR_SENHA)));
    }

    @Test
    @Transactional
    void getUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get the usuario
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL_ID, usuario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuario.getId().intValue()))
            .andExpect(jsonPath("$.idnVarUsuario").value(DEFAULT_IDN_VAR_USUARIO))
            .andExpect(jsonPath("$.nVarNome").value(DEFAULT_N_VAR_NOME))
            .andExpect(jsonPath("$.idnVarEmpresa").value(DEFAULT_IDN_VAR_EMPRESA))
            .andExpect(jsonPath("$.idnVarUsuarioCadastro").value(DEFAULT_IDN_VAR_USUARIO_CADASTRO))
            .andExpect(jsonPath("$.nVarSenha").value(DEFAULT_N_VAR_SENHA));
    }

    @Test
    @Transactional
    void getUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        Long id = usuario.getId();

        defaultUsuarioShouldBeFound("id.equals=" + id);
        defaultUsuarioShouldNotBeFound("id.notEquals=" + id);

        defaultUsuarioShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsuarioShouldNotBeFound("id.greaterThan=" + id);

        defaultUsuarioShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsuarioShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuario equals to DEFAULT_IDN_VAR_USUARIO
        defaultUsuarioShouldBeFound("idnVarUsuario.equals=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the usuarioList where idnVarUsuario equals to UPDATED_IDN_VAR_USUARIO
        defaultUsuarioShouldNotBeFound("idnVarUsuario.equals=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuario not equals to DEFAULT_IDN_VAR_USUARIO
        defaultUsuarioShouldNotBeFound("idnVarUsuario.notEquals=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the usuarioList where idnVarUsuario not equals to UPDATED_IDN_VAR_USUARIO
        defaultUsuarioShouldBeFound("idnVarUsuario.notEquals=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuario in DEFAULT_IDN_VAR_USUARIO or UPDATED_IDN_VAR_USUARIO
        defaultUsuarioShouldBeFound("idnVarUsuario.in=" + DEFAULT_IDN_VAR_USUARIO + "," + UPDATED_IDN_VAR_USUARIO);

        // Get all the usuarioList where idnVarUsuario equals to UPDATED_IDN_VAR_USUARIO
        defaultUsuarioShouldNotBeFound("idnVarUsuario.in=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuario is not null
        defaultUsuarioShouldBeFound("idnVarUsuario.specified=true");

        // Get all the usuarioList where idnVarUsuario is null
        defaultUsuarioShouldNotBeFound("idnVarUsuario.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuario contains DEFAULT_IDN_VAR_USUARIO
        defaultUsuarioShouldBeFound("idnVarUsuario.contains=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the usuarioList where idnVarUsuario contains UPDATED_IDN_VAR_USUARIO
        defaultUsuarioShouldNotBeFound("idnVarUsuario.contains=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuario does not contain DEFAULT_IDN_VAR_USUARIO
        defaultUsuarioShouldNotBeFound("idnVarUsuario.doesNotContain=" + DEFAULT_IDN_VAR_USUARIO);

        // Get all the usuarioList where idnVarUsuario does not contain UPDATED_IDN_VAR_USUARIO
        defaultUsuarioShouldBeFound("idnVarUsuario.doesNotContain=" + UPDATED_IDN_VAR_USUARIO);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarNome equals to DEFAULT_N_VAR_NOME
        defaultUsuarioShouldBeFound("nVarNome.equals=" + DEFAULT_N_VAR_NOME);

        // Get all the usuarioList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultUsuarioShouldNotBeFound("nVarNome.equals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarNomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarNome not equals to DEFAULT_N_VAR_NOME
        defaultUsuarioShouldNotBeFound("nVarNome.notEquals=" + DEFAULT_N_VAR_NOME);

        // Get all the usuarioList where nVarNome not equals to UPDATED_N_VAR_NOME
        defaultUsuarioShouldBeFound("nVarNome.notEquals=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarNomeIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarNome in DEFAULT_N_VAR_NOME or UPDATED_N_VAR_NOME
        defaultUsuarioShouldBeFound("nVarNome.in=" + DEFAULT_N_VAR_NOME + "," + UPDATED_N_VAR_NOME);

        // Get all the usuarioList where nVarNome equals to UPDATED_N_VAR_NOME
        defaultUsuarioShouldNotBeFound("nVarNome.in=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarNome is not null
        defaultUsuarioShouldBeFound("nVarNome.specified=true");

        // Get all the usuarioList where nVarNome is null
        defaultUsuarioShouldNotBeFound("nVarNome.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarNomeContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarNome contains DEFAULT_N_VAR_NOME
        defaultUsuarioShouldBeFound("nVarNome.contains=" + DEFAULT_N_VAR_NOME);

        // Get all the usuarioList where nVarNome contains UPDATED_N_VAR_NOME
        defaultUsuarioShouldNotBeFound("nVarNome.contains=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarNomeNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarNome does not contain DEFAULT_N_VAR_NOME
        defaultUsuarioShouldNotBeFound("nVarNome.doesNotContain=" + DEFAULT_N_VAR_NOME);

        // Get all the usuarioList where nVarNome does not contain UPDATED_N_VAR_NOME
        defaultUsuarioShouldBeFound("nVarNome.doesNotContain=" + UPDATED_N_VAR_NOME);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarEmpresa equals to DEFAULT_IDN_VAR_EMPRESA
        defaultUsuarioShouldBeFound("idnVarEmpresa.equals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the usuarioList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultUsuarioShouldNotBeFound("idnVarEmpresa.equals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarEmpresaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarEmpresa not equals to DEFAULT_IDN_VAR_EMPRESA
        defaultUsuarioShouldNotBeFound("idnVarEmpresa.notEquals=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the usuarioList where idnVarEmpresa not equals to UPDATED_IDN_VAR_EMPRESA
        defaultUsuarioShouldBeFound("idnVarEmpresa.notEquals=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarEmpresaIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarEmpresa in DEFAULT_IDN_VAR_EMPRESA or UPDATED_IDN_VAR_EMPRESA
        defaultUsuarioShouldBeFound("idnVarEmpresa.in=" + DEFAULT_IDN_VAR_EMPRESA + "," + UPDATED_IDN_VAR_EMPRESA);

        // Get all the usuarioList where idnVarEmpresa equals to UPDATED_IDN_VAR_EMPRESA
        defaultUsuarioShouldNotBeFound("idnVarEmpresa.in=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarEmpresaIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarEmpresa is not null
        defaultUsuarioShouldBeFound("idnVarEmpresa.specified=true");

        // Get all the usuarioList where idnVarEmpresa is null
        defaultUsuarioShouldNotBeFound("idnVarEmpresa.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarEmpresaContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarEmpresa contains DEFAULT_IDN_VAR_EMPRESA
        defaultUsuarioShouldBeFound("idnVarEmpresa.contains=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the usuarioList where idnVarEmpresa contains UPDATED_IDN_VAR_EMPRESA
        defaultUsuarioShouldNotBeFound("idnVarEmpresa.contains=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarEmpresaNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarEmpresa does not contain DEFAULT_IDN_VAR_EMPRESA
        defaultUsuarioShouldNotBeFound("idnVarEmpresa.doesNotContain=" + DEFAULT_IDN_VAR_EMPRESA);

        // Get all the usuarioList where idnVarEmpresa does not contain UPDATED_IDN_VAR_EMPRESA
        defaultUsuarioShouldBeFound("idnVarEmpresa.doesNotContain=" + UPDATED_IDN_VAR_EMPRESA);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioCadastroIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuarioCadastro equals to DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldBeFound("idnVarUsuarioCadastro.equals=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the usuarioList where idnVarUsuarioCadastro equals to UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldNotBeFound("idnVarUsuarioCadastro.equals=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioCadastroIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuarioCadastro not equals to DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldNotBeFound("idnVarUsuarioCadastro.notEquals=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the usuarioList where idnVarUsuarioCadastro not equals to UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldBeFound("idnVarUsuarioCadastro.notEquals=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioCadastroIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuarioCadastro in DEFAULT_IDN_VAR_USUARIO_CADASTRO or UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldBeFound(
            "idnVarUsuarioCadastro.in=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO + "," + UPDATED_IDN_VAR_USUARIO_CADASTRO
        );

        // Get all the usuarioList where idnVarUsuarioCadastro equals to UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldNotBeFound("idnVarUsuarioCadastro.in=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioCadastroIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuarioCadastro is not null
        defaultUsuarioShouldBeFound("idnVarUsuarioCadastro.specified=true");

        // Get all the usuarioList where idnVarUsuarioCadastro is null
        defaultUsuarioShouldNotBeFound("idnVarUsuarioCadastro.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioCadastroContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuarioCadastro contains DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldBeFound("idnVarUsuarioCadastro.contains=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the usuarioList where idnVarUsuarioCadastro contains UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldNotBeFound("idnVarUsuarioCadastro.contains=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllUsuariosByIdnVarUsuarioCadastroNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where idnVarUsuarioCadastro does not contain DEFAULT_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldNotBeFound("idnVarUsuarioCadastro.doesNotContain=" + DEFAULT_IDN_VAR_USUARIO_CADASTRO);

        // Get all the usuarioList where idnVarUsuarioCadastro does not contain UPDATED_IDN_VAR_USUARIO_CADASTRO
        defaultUsuarioShouldBeFound("idnVarUsuarioCadastro.doesNotContain=" + UPDATED_IDN_VAR_USUARIO_CADASTRO);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarSenhaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarSenha equals to DEFAULT_N_VAR_SENHA
        defaultUsuarioShouldBeFound("nVarSenha.equals=" + DEFAULT_N_VAR_SENHA);

        // Get all the usuarioList where nVarSenha equals to UPDATED_N_VAR_SENHA
        defaultUsuarioShouldNotBeFound("nVarSenha.equals=" + UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarSenhaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarSenha not equals to DEFAULT_N_VAR_SENHA
        defaultUsuarioShouldNotBeFound("nVarSenha.notEquals=" + DEFAULT_N_VAR_SENHA);

        // Get all the usuarioList where nVarSenha not equals to UPDATED_N_VAR_SENHA
        defaultUsuarioShouldBeFound("nVarSenha.notEquals=" + UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarSenhaIsInShouldWork() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarSenha in DEFAULT_N_VAR_SENHA or UPDATED_N_VAR_SENHA
        defaultUsuarioShouldBeFound("nVarSenha.in=" + DEFAULT_N_VAR_SENHA + "," + UPDATED_N_VAR_SENHA);

        // Get all the usuarioList where nVarSenha equals to UPDATED_N_VAR_SENHA
        defaultUsuarioShouldNotBeFound("nVarSenha.in=" + UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarSenhaIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarSenha is not null
        defaultUsuarioShouldBeFound("nVarSenha.specified=true");

        // Get all the usuarioList where nVarSenha is null
        defaultUsuarioShouldNotBeFound("nVarSenha.specified=false");
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarSenhaContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarSenha contains DEFAULT_N_VAR_SENHA
        defaultUsuarioShouldBeFound("nVarSenha.contains=" + DEFAULT_N_VAR_SENHA);

        // Get all the usuarioList where nVarSenha contains UPDATED_N_VAR_SENHA
        defaultUsuarioShouldNotBeFound("nVarSenha.contains=" + UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void getAllUsuariosBynVarSenhaNotContainsSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        // Get all the usuarioList where nVarSenha does not contain DEFAULT_N_VAR_SENHA
        defaultUsuarioShouldNotBeFound("nVarSenha.doesNotContain=" + DEFAULT_N_VAR_SENHA);

        // Get all the usuarioList where nVarSenha does not contain UPDATED_N_VAR_SENHA
        defaultUsuarioShouldBeFound("nVarSenha.doesNotContain=" + UPDATED_N_VAR_SENHA);
    }









    @Test
    @Transactional
    void getAllUsuariosByFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);
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
        usuario.addFuncionario(funcionario);
        usuarioRepository.saveAndFlush(usuario);
        Long funcionarioId = funcionario.getId();

        // Get all the usuarioList where funcionario equals to funcionarioId
        defaultUsuarioShouldBeFound("funcionarioId.equals=" + funcionarioId);

        // Get all the usuarioList where funcionario equals to (funcionarioId + 1)
        defaultUsuarioShouldNotBeFound("funcionarioId.equals=" + (funcionarioId + 1));
    }





    @Test
    @Transactional
    void getAllUsuariosByOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);
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
        usuario.addOrganograma(organograma);
        usuarioRepository.saveAndFlush(usuario);
        Long organogramaId = organograma.getId();

        // Get all the usuarioList where organograma equals to organogramaId
        defaultUsuarioShouldBeFound("organogramaId.equals=" + organogramaId);

        // Get all the usuarioList where organograma equals to (organogramaId + 1)
        defaultUsuarioShouldNotBeFound("organogramaId.equals=" + (organogramaId + 1));
    }











    @Test
    @Transactional
    void getAllUsuariosByFuncionarioOrganogramaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);
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
        usuario.addFuncionarioOrganograma(funcionarioOrganograma);
        usuarioRepository.saveAndFlush(usuario);
        Long funcionarioOrganogramaId = funcionarioOrganograma.getId();

        // Get all the usuarioList where funcionarioOrganograma equals to funcionarioOrganogramaId
        defaultUsuarioShouldBeFound("funcionarioOrganogramaId.equals=" + funcionarioOrganogramaId);

        // Get all the usuarioList where funcionarioOrganograma equals to (funcionarioOrganogramaId + 1)
        defaultUsuarioShouldNotBeFound("funcionarioOrganogramaId.equals=" + (funcionarioOrganogramaId + 1));
    }

    @Test
    @Transactional
    void getAllUsuariosByEmpresaIsEqualToSomething() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);
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
        usuario.setEmpresa(empresa);
        usuarioRepository.saveAndFlush(usuario);
        Long empresaId = empresa.getId();

        // Get all the usuarioList where empresa equals to empresaId
        defaultUsuarioShouldBeFound("empresaId.equals=" + empresaId);

        // Get all the usuarioList where empresa equals to (empresaId + 1)
        defaultUsuarioShouldNotBeFound("empresaId.equals=" + (empresaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuarioShouldBeFound(String filter) throws Exception {
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuario.getId().intValue())))
            .andExpect(jsonPath("$.[*].idnVarUsuario").value(hasItem(DEFAULT_IDN_VAR_USUARIO)))
            .andExpect(jsonPath("$.[*].nVarNome").value(hasItem(DEFAULT_N_VAR_NOME)))
            .andExpect(jsonPath("$.[*].idnVarEmpresa").value(hasItem(DEFAULT_IDN_VAR_EMPRESA)))
            .andExpect(jsonPath("$.[*].idnVarUsuarioCadastro").value(hasItem(DEFAULT_IDN_VAR_USUARIO_CADASTRO)))
            .andExpect(jsonPath("$.[*].nVarSenha").value(hasItem(DEFAULT_N_VAR_SENHA)));

        // Check, that the count call also returns 1
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuarioShouldNotBeFound(String filter) throws Exception {
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuarioMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUsuario() throws Exception {
        // Get the usuario
        restUsuarioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario
        Usuario updatedUsuario = usuarioRepository.findById(usuario.getId()).get();
        // Disconnect from session so that the updates on updatedUsuario are not directly saved in db
        em.detach(updatedUsuario);
        updatedUsuario
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO)
            .nVarSenha(UPDATED_N_VAR_SENHA);
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(updatedUsuario);

        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
        assertThat(testUsuario.getNVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testUsuario.getIdnVarUsuarioCadastro()).isEqualTo(UPDATED_IDN_VAR_USUARIO_CADASTRO);
        assertThat(testUsuario.getNVarSenha()).isEqualTo(UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void putNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, usuarioDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(usuarioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario.idnVarUsuario(UPDATED_IDN_VAR_USUARIO).nVarNome(UPDATED_N_VAR_NOME).nVarSenha(UPDATED_N_VAR_SENHA);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
        assertThat(testUsuario.getNVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testUsuario.getIdnVarUsuarioCadastro()).isEqualTo(DEFAULT_IDN_VAR_USUARIO_CADASTRO);
        assertThat(testUsuario.getNVarSenha()).isEqualTo(UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void fullUpdateUsuarioWithPatch() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();

        // Update the usuario using partial update
        Usuario partialUpdatedUsuario = new Usuario();
        partialUpdatedUsuario.setId(usuario.getId());

        partialUpdatedUsuario
            .idnVarUsuario(UPDATED_IDN_VAR_USUARIO)
            .nVarNome(UPDATED_N_VAR_NOME)
            .idnVarUsuarioCadastro(UPDATED_IDN_VAR_USUARIO_CADASTRO)
            .nVarSenha(UPDATED_N_VAR_SENHA);

        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUsuario.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUsuario))
            )
            .andExpect(status().isOk());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
        Usuario testUsuario = usuarioList.get(usuarioList.size() - 1);
        assertThat(testUsuario.getIdnVarUsuario()).isEqualTo(UPDATED_IDN_VAR_USUARIO);
        assertThat(testUsuario.getNVarNome()).isEqualTo(UPDATED_N_VAR_NOME);
        assertThat(testUsuario.getIdnVarUsuarioCadastro()).isEqualTo(UPDATED_IDN_VAR_USUARIO_CADASTRO);
        assertThat(testUsuario.getNVarSenha()).isEqualTo(UPDATED_N_VAR_SENHA);
    }

    @Test
    @Transactional
    void patchNonExistingUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, usuarioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(usuarioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUsuario() throws Exception {
        int databaseSizeBeforeUpdate = usuarioRepository.findAll().size();
        usuario.setId(count.incrementAndGet());

        // Create the Usuario
        UsuarioDTO usuarioDTO = usuarioMapper.toDto(usuario);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUsuarioMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(usuarioDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Usuario in the database
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUsuario() throws Exception {
        // Initialize the database
        usuarioRepository.saveAndFlush(usuario);

        int databaseSizeBeforeDelete = usuarioRepository.findAll().size();

        // Delete the usuario
        restUsuarioMockMvc
            .perform(delete(ENTITY_API_URL_ID, usuario.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuario> usuarioList = usuarioRepository.findAll();
        assertThat(usuarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
