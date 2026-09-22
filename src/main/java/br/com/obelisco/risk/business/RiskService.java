package br.com.obelisco.risk.business;

import br.com.obelisco.risk.model.*;
import br.com.obelisco.risk.model.dto.*;
import br.com.obelisco.risk.repository.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class RiskService {
    private final EmpresaRepository empresas;
    private final UsuarioRepository usuarios;
    private final UnidadeRepository unidades;
    private final FuncionarioRepository funcionarios;
    private final FuncionarioUnidadeRepository funcionarioUnidades;
    private final MacroProcessoRepository macroProcessos;
    private final ProcessoRepository processos;
    private final ClienteRepository clientes;
    private final FornecedorRepository fornecedores;
    private final ComplianceInternoRepository complianceInternos;
    private final ComplianceExternoRepository complianceExternos;
    private final ClienteProcessoRepository clienteProcessos;
    private final FornecedorProcessoRepository fornecedorProcessos;
    private final UnidadeProcessoPapelRepository unidadeProcessos;
    private final ComplianceInternoProcessoRepository complianceInternoProcessos;
    private final ComplianceExternoProcessoRepository complianceExternoProcessos;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<EmpresaResponse> empresas(String busca, Pageable page) {
        return empresas.findByNomeContainingIgnoreCase(text(busca), page).map(this::view);
    }
    public EmpresaResponse salvarEmpresa(UUID id, EmpresaUpsertRequest in) {
        Empresa e = id == null ? new Empresa() : get(empresas, id, "Empresa");
        e.setCodigo(in.codigo()); e.setNome(in.nome()); e.setDescricao(in.descricao());
        return view(empresas.save(e));
    }
    public void excluirEmpresa(UUID id) { empresas.delete(get(empresas, id, "Empresa")); }

    @Transactional(readOnly = true)
    public Page<UsuarioResponse> usuarios(String busca, Pageable page) {
        return usuarios.findByNomeContainingIgnoreCase(text(busca), page).map(this::view);
    }
    public UsuarioResponse salvarUsuario(UUID id, UsuarioUpsertRequest in) {
        Usuario u = id == null ? new Usuario() : get(usuarios, id, "Usuário");
        u.setLogin(in.login()); u.setNome(in.nome()); u.setPerfil(in.perfil()); u.setAtivo(in.ativo());
        u.setEmpresa(get(empresas, in.empresaId(), "Empresa"));
        if (id == null && (in.senha() == null || in.senha().isBlank())) throw invalid("Senha é obrigatória");
        if (in.senha() != null && !in.senha().isBlank()) u.setSenhaHash(passwordEncoder.encode(in.senha()));
        return view(usuarios.save(u));
    }
    public void excluirUsuario(UUID id) { usuarios.delete(get(usuarios, id, "Usuário")); }
    @Transactional(readOnly = true)
    public UserDetails carregarUsuario(String login) {
        Usuario u = usuarios.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));
        return User.withUsername(u.getLogin()).password(u.getSenhaHash()).roles(u.getPerfil()).disabled(!u.isAtivo()).build();
    }

    @Transactional(readOnly = true)
    public List<UnidadeResponse> arvore(UUID empresaId) {
        List<UnidadeOrganizacional> items = unidades.findByEmpresaIdOrderByNome(empresaId);
        Map<UUID, Long> counts = funcionarioUnidades.findAll().stream()
            .filter(v -> v.getUnidade().getEmpresa().getId().equals(empresaId))
            .collect(Collectors.groupingBy(v -> v.getUnidade().getId(), Collectors.counting()));
        Map<UUID, List<UnidadeOrganizacional>> children = items.stream().filter(u -> u.getParent() != null)
            .collect(Collectors.groupingBy(u -> u.getParent().getId()));
        return items.stream().filter(u -> u.getParent() == null)
            .map(u -> tree(u, children, counts)).toList();
    }
    public UnidadeResponse salvarUnidade(UUID id, UnidadeUpsertRequest in) {
        UnidadeOrganizacional u = id == null ? new UnidadeOrganizacional() : get(unidades, id, "Unidade");
        u.setCodigo(in.codigo()); u.setNome(in.nome()); u.setDescricao(in.descricao());
        u.setEmpresa(get(empresas, in.empresaId(), "Empresa"));
        u.setParent(in.parentId() == null ? null : get(unidades, in.parentId(), "Unidade pai"));
        if (u.getParent() != null && u.getParent().getId().equals(id)) throw invalid("Uma unidade não pode ser pai de si mesma");
        return unitView(unidades.save(u), 0, List.of());
    }
    public void excluirUnidade(UUID id) { unidades.delete(get(unidades, id, "Unidade")); }

    @Transactional(readOnly = true)
    public Page<FuncionarioResponse> funcionarios(String busca, Pageable page) {
        return funcionarios.findByNomeContainingIgnoreCase(text(busca), page).map(this::view);
    }
    public FuncionarioResponse salvarFuncionario(UUID id, FuncionarioUpsertRequest in) {
        Funcionario f = id == null ? new Funcionario() : get(funcionarios, id, "Funcionário");
        f.setCodigo(in.codigo()); f.setNome(in.nome()); f.setEmail(in.email()); f.setDescricao(in.descricao());
        f.setEmpresa(get(empresas, in.empresaId(), "Empresa"));
        return view(funcionarios.save(f));
    }
    public void excluirFuncionario(UUID id) { funcionarios.delete(get(funcionarios, id, "Funcionário")); }
    public VinculoResponse vincularFuncionario(FuncionarioUnidadeUpsertRequest in) {
        FuncionarioUnidade v = new FuncionarioUnidade();
        v.setFuncionario(get(funcionarios, in.funcionarioId(), "Funcionário"));
        v.setUnidade(get(unidades, in.unidadeId(), "Unidade")); v.setInicio(in.inicio());
        v.setFim(in.fim()); v.setResponsavel(in.responsavel()); v = funcionarioUnidades.save(v);
        return new VinculoResponse(v.getId(), v.getFuncionario().getId(), v.getUnidade().getId(), v.isResponsavel() ? "RESPONSAVEL" : "MEMBRO");
    }

    @Transactional(readOnly = true)
    public Page<MacroProcessoResponse> macroProcessos(String busca, Pageable page) {
        return macroProcessos.findByNomeContainingIgnoreCase(text(busca), page).map(this::view);
    }
    public MacroProcessoResponse salvarMacroProcesso(UUID id, MacroProcessoUpsertRequest in) {
        MacroProcesso m = id == null ? new MacroProcesso() : get(macroProcessos, id, "Macroprocesso");
        m.setCodigo(in.codigo()); m.setNome(in.nome()); m.setDescricao(in.descricao()); m.setEmpresa(get(empresas, in.empresaId(), "Empresa"));
        return view(macroProcessos.save(m));
    }
    public void excluirMacroProcesso(UUID id) { macroProcessos.delete(get(macroProcessos, id, "Macroprocesso")); }

    @Transactional(readOnly = true)
    public Page<ProcessoResponse> processos(String busca, Pageable page) {
        return processos.findByNomeContainingIgnoreCase(text(busca), page).map(this::view);
    }
    public ProcessoResponse salvarProcesso(UUID id, ProcessoUpsertRequest in) {
        Processo p = id == null ? new Processo() : get(processos, id, "Processo");
        p.setCodigo(in.codigo()); p.setNome(in.nome()); p.setObjetivo(in.objetivo()); p.setLimiteInicial(in.limiteInicial());
        p.setLimiteFinal(in.limiteFinal()); p.setEntradas(in.entradas()); p.setSaidas(in.saidas()); p.setCaminhoArquivo(in.caminhoArquivo());
        p.setStatus(in.status()); p.setInicio(in.inicio()); p.setFim(in.fim()); p.setEmpresa(get(empresas, in.empresaId(), "Empresa"));
        p.setMacroProcesso(get(macroProcessos, in.macroProcessoId(), "Macroprocesso"));
        p.setUnidade(in.unidadeId() == null ? null : get(unidades, in.unidadeId(), "Unidade"));
        if (p.getFim() != null && p.getFim().isBefore(p.getInicio())) throw invalid("A data final não pode preceder a inicial");
        return view(processos.save(p));
    }
    public void excluirProcesso(UUID id) { processos.delete(get(processos, id, "Processo")); }

    @Transactional(readOnly = true)
    public ProcessoDetalheResponse detalheProcesso(UUID id) {
        ProcessoResponse processo = view(get(processos, id, "Processo"));
        List<RelacionamentoResponse> clientesView = clienteProcessos.findByProcessoId(id).stream()
            .map(v -> new RelacionamentoResponse(v.getId(), v.getCliente().getId(), v.getCliente().getNome(), "CLIENTE_EXTERNO")).toList();
        List<RelacionamentoResponse> fornecedoresView = fornecedorProcessos.findByProcessoId(id).stream()
            .map(v -> new RelacionamentoResponse(v.getId(), v.getFornecedor().getId(), v.getFornecedor().getNome(), "FORNECEDOR_EXTERNO")).toList();
        List<RelacionamentoResponse> complianceView = new ArrayList<>();
        complianceInternoProcessos.findByProcessoId(id).forEach(v -> complianceView.add(new RelacionamentoResponse(v.getId(), v.getCompliance().getId(), v.getCompliance().getNome(), "COMPLIANCE_INTERNO")));
        complianceExternoProcessos.findByProcessoId(id).forEach(v -> complianceView.add(new RelacionamentoResponse(v.getId(), v.getCompliance().getId(), v.getCompliance().getNome(), "COMPLIANCE_EXTERNO")));
        List<RelacionamentoResponse> unidadesView = unidadeProcessos.findByProcessoId(id).stream()
            .map(v -> new RelacionamentoResponse(v.getId(), v.getUnidade().getId(), v.getUnidade().getNome(), v.getPapel().name())).toList();
        return new ProcessoDetalheResponse(processo, clientesView, fornecedoresView, complianceView, unidadesView);
    }

    @Transactional(readOnly = true) public Page<CatalogoResponse> clientes(String q, Pageable p) { return clientes.findByNomeContainingIgnoreCase(text(q), p).map(this::view); }
    @Transactional(readOnly = true) public Page<CatalogoResponse> fornecedores(String q, Pageable p) { return fornecedores.findByNomeContainingIgnoreCase(text(q), p).map(this::view); }
    @Transactional(readOnly = true) public Page<CatalogoResponse> complianceInternos(String q, Pageable p) { return complianceInternos.findByNomeContainingIgnoreCase(text(q), p).map(this::view); }
    @Transactional(readOnly = true) public Page<CatalogoResponse> complianceExternos(String q, Pageable p) { return complianceExternos.findByNomeContainingIgnoreCase(text(q), p).map(this::view); }

    public CatalogoResponse salvarCliente(UUID id, CatalogoUpsertRequest in) {
        ClienteExterno e = id == null ? new ClienteExterno() : get(clientes, id, "Cliente"); fill(e, in); return view(clientes.save(e));
    }
    public CatalogoResponse salvarFornecedor(UUID id, CatalogoUpsertRequest in) {
        FornecedorExterno e = id == null ? new FornecedorExterno() : get(fornecedores, id, "Fornecedor"); fill(e, in); return view(fornecedores.save(e));
    }
    public CatalogoResponse salvarComplianceInterno(UUID id, CatalogoUpsertRequest in) {
        ComplianceInterno e = id == null ? new ComplianceInterno() : get(complianceInternos, id, "Compliance interno"); fill(e, in); return view(complianceInternos.save(e));
    }
    public CatalogoResponse salvarComplianceExterno(UUID id, CatalogoUpsertRequest in) {
        ComplianceExterno e = id == null ? new ComplianceExterno() : get(complianceExternos, id, "Compliance externo"); fill(e, in); return view(complianceExternos.save(e));
    }
    public void excluirCliente(UUID id) { clientes.delete(get(clientes, id, "Cliente")); }
    public void excluirFornecedor(UUID id) { fornecedores.delete(get(fornecedores, id, "Fornecedor")); }
    public void excluirComplianceInterno(UUID id) { complianceInternos.delete(get(complianceInternos, id, "Compliance interno")); }
    public void excluirComplianceExterno(UUID id) { complianceExternos.delete(get(complianceExternos, id, "Compliance externo")); }

    public VinculoResponse vincular(String tipo, ProcessoVinculoUpsertRequest in) {
        Processo p = get(processos, in.processoId(), "Processo");
        return switch (tipo) {
            case "cliente" -> { ClienteProcesso v = new ClienteProcesso(); v.setCliente(get(clientes, in.origemId(), "Cliente")); v.setProcesso(p); v=clienteProcessos.save(v); yield new VinculoResponse(v.getId(), in.origemId(), p.getId(), "CLIENTE_EXTERNO"); }
            case "fornecedor" -> { FornecedorProcesso v = new FornecedorProcesso(); v.setFornecedor(get(fornecedores, in.origemId(), "Fornecedor")); v.setProcesso(p); v=fornecedorProcessos.save(v); yield new VinculoResponse(v.getId(), in.origemId(), p.getId(), "FORNECEDOR_EXTERNO"); }
            case "compliance-interno" -> { ComplianceInternoProcesso v = new ComplianceInternoProcesso(); v.setCompliance(get(complianceInternos, in.origemId(), "Compliance interno")); v.setProcesso(p); v=complianceInternoProcessos.save(v); yield new VinculoResponse(v.getId(), in.origemId(), p.getId(), "COMPLIANCE_INTERNO"); }
            case "compliance-externo" -> { ComplianceExternoProcesso v = new ComplianceExternoProcesso(); v.setCompliance(get(complianceExternos, in.origemId(), "Compliance externo")); v.setProcesso(p); v=complianceExternoProcessos.save(v); yield new VinculoResponse(v.getId(), in.origemId(), p.getId(), "COMPLIANCE_EXTERNO"); }
            case "cliente-interno", "fornecedor-interno" -> { UnidadeProcessoPapel v = new UnidadeProcessoPapel(); v.setUnidade(get(unidades, in.origemId(), "Unidade")); v.setProcesso(p); v.setPapel(UnidadeProcessoPapel.Papel.valueOf(tipo.replace('-', '_').toUpperCase())); v=unidadeProcessos.save(v); yield new VinculoResponse(v.getId(), in.origemId(), p.getId(), v.getPapel().name()); }
            default -> throw invalid("Tipo de vínculo inválido");
        };
    }

    public void excluirVinculo(String tipo, UUID id) {
        switch (tipo) {
            case "cliente" -> clienteProcessos.deleteById(id);
            case "fornecedor" -> fornecedorProcessos.deleteById(id);
            case "compliance-interno" -> complianceInternoProcessos.deleteById(id);
            case "compliance-externo" -> complianceExternoProcessos.deleteById(id);
            case "cliente-interno", "fornecedor-interno" -> unidadeProcessos.deleteById(id);
            default -> throw invalid("Tipo de vínculo inválido");
        }
    }

    @Transactional(readOnly = true)
    public DashboardResponse dashboard() {
        return new DashboardResponse(empresas.count(), funcionarios.count(), unidades.count(), macroProcessos.count(), processos.count(), clientes.count(), fornecedores.count(), complianceInternos.count() + complianceExternos.count());
    }

    private String text(String s) { return s == null ? "" : s.trim(); }
    private <T> T get(org.springframework.data.jpa.repository.JpaRepository<T, UUID> repo, UUID id, String nome) { return repo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, nome + " não encontrado")); }
    private ResponseStatusException invalid(String msg) { return new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, msg); }
    private EmpresaResponse view(Empresa e) { return new EmpresaResponse(e.getId(), e.getCodigo(), e.getNome(), e.getDescricao()); }
    private UsuarioResponse view(Usuario u) { return new UsuarioResponse(u.getId(), u.getLogin(), u.getNome(), u.getPerfil(), u.isAtivo(), u.getEmpresa().getId(), u.getEmpresa().getNome()); }
    private FuncionarioResponse view(Funcionario f) { return new FuncionarioResponse(f.getId(), f.getCodigo(), f.getNome(), f.getEmail(), f.getDescricao(), f.getEmpresa().getId(), f.getEmpresa().getNome()); }
    private MacroProcessoResponse view(MacroProcesso m) { return new MacroProcessoResponse(m.getId(), m.getCodigo(), m.getNome(), m.getDescricao(), m.getEmpresa().getId(), m.getEmpresa().getNome()); }
    private ProcessoResponse view(Processo p) { return new ProcessoResponse(p.getId(), p.getCodigo(), p.getNome(), p.getObjetivo(), p.getStatus(), p.getInicio(), p.getFim(), p.getEmpresa().getId(), p.getMacroProcesso().getId(), p.getMacroProcesso().getNome(), p.getUnidade() == null ? null : p.getUnidade().getId(), p.getUnidade() == null ? null : p.getUnidade().getNome()); }
    private CatalogoResponse view(ClienteExterno e) { return catalog(e.getId(),e.getCodigo(),e.getNome(),e.getDescricao(),e.getEmpresa()); }
    private CatalogoResponse view(FornecedorExterno e) { return catalog(e.getId(),e.getCodigo(),e.getNome(),e.getDescricao(),e.getEmpresa()); }
    private CatalogoResponse view(ComplianceInterno e) { return catalog(e.getId(),e.getCodigo(),e.getNome(),e.getDescricao(),e.getEmpresa()); }
    private CatalogoResponse view(ComplianceExterno e) { return catalog(e.getId(),e.getCodigo(),e.getNome(),e.getDescricao(),e.getEmpresa()); }
    private CatalogoResponse catalog(UUID id,String codigo,String nome,String descricao,Empresa e) { return new CatalogoResponse(id,codigo,nome,descricao,e.getId(),e.getNome()); }
    private void fill(ClienteExterno e, CatalogoUpsertRequest in) { e.setCodigo(in.codigo());e.setNome(in.nome());e.setDescricao(in.descricao());e.setEmpresa(get(empresas,in.empresaId(),"Empresa")); }
    private void fill(FornecedorExterno e, CatalogoUpsertRequest in) { e.setCodigo(in.codigo());e.setNome(in.nome());e.setDescricao(in.descricao());e.setEmpresa(get(empresas,in.empresaId(),"Empresa")); }
    private void fill(ComplianceInterno e, CatalogoUpsertRequest in) { e.setCodigo(in.codigo());e.setNome(in.nome());e.setDescricao(in.descricao());e.setEmpresa(get(empresas,in.empresaId(),"Empresa")); }
    private void fill(ComplianceExterno e, CatalogoUpsertRequest in) { e.setCodigo(in.codigo());e.setNome(in.nome());e.setDescricao(in.descricao());e.setEmpresa(get(empresas,in.empresaId(),"Empresa")); }
    private UnidadeResponse tree(UnidadeOrganizacional u, Map<UUID,List<UnidadeOrganizacional>> map, Map<UUID,Long> counts) { return unitView(u, counts.getOrDefault(u.getId(),0L), map.getOrDefault(u.getId(),List.of()).stream().map(c -> tree(c,map,counts)).toList()); }
    private UnidadeResponse unitView(UnidadeOrganizacional u,long count,List<UnidadeResponse> children) { return new UnidadeResponse(u.getId(),u.getCodigo(),u.getNome(),u.getDescricao(),u.getEmpresa().getId(),u.getParent()==null?null:u.getParent().getId(),count,children); }
}
