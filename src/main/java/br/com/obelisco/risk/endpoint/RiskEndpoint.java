package br.com.obelisco.risk.endpoint;

import br.com.obelisco.risk.business.RiskService;
import br.com.obelisco.risk.model.dto.*;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RiskEndpoint {
    private final RiskService service;

    @GetMapping("/dashboard") public DashboardResponse dashboard() { return service.dashboard(); }

    @GetMapping("/empresas") public Page<EmpresaResponse> empresas(@RequestParam(defaultValue="") String busca, @PageableDefault(size=20, sort="nome") Pageable page) { return service.empresas(busca,page); }
    @PostMapping("/empresas") public ResponseEntity<EmpresaResponse> criarEmpresa(@Valid @RequestBody EmpresaUpsertRequest in) { EmpresaResponse out=service.salvarEmpresa(null,in); return created("empresas",out.id(),out); }
    @PutMapping("/empresas/{id}") public EmpresaResponse editarEmpresa(@PathVariable UUID id,@Valid @RequestBody EmpresaUpsertRequest in) { return service.salvarEmpresa(id,in); }
    @DeleteMapping("/empresas/{id}") public ResponseEntity<Void> excluirEmpresa(@PathVariable UUID id) { service.excluirEmpresa(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/usuarios") public Page<UsuarioResponse> usuarios(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.usuarios(busca,p); }
    @PostMapping("/usuarios") public ResponseEntity<UsuarioResponse> criarUsuario(@Valid @RequestBody UsuarioUpsertRequest in) { UsuarioResponse out=service.salvarUsuario(null,in); return created("usuarios",out.id(),out); }
    @PutMapping("/usuarios/{id}") public UsuarioResponse editarUsuario(@PathVariable UUID id,@Valid @RequestBody UsuarioUpsertRequest in) { return service.salvarUsuario(id,in); }
    @DeleteMapping("/usuarios/{id}") public ResponseEntity<Void> excluirUsuario(@PathVariable UUID id) { service.excluirUsuario(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/unidades/tree") public List<UnidadeResponse> arvore(@RequestParam UUID empresaId) { return service.arvore(empresaId); }
    @PostMapping("/unidades") public ResponseEntity<UnidadeResponse> criarUnidade(@Valid @RequestBody UnidadeUpsertRequest in) { UnidadeResponse out=service.salvarUnidade(null,in); return created("unidades",out.id(),out); }
    @PutMapping("/unidades/{id}") public UnidadeResponse editarUnidade(@PathVariable UUID id,@Valid @RequestBody UnidadeUpsertRequest in) { return service.salvarUnidade(id,in); }
    @DeleteMapping("/unidades/{id}") public ResponseEntity<Void> excluirUnidade(@PathVariable UUID id) { service.excluirUnidade(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/funcionarios") public Page<FuncionarioResponse> funcionarios(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.funcionarios(busca,p); }
    @PostMapping("/funcionarios") public ResponseEntity<FuncionarioResponse> criarFuncionario(@Valid @RequestBody FuncionarioUpsertRequest in) { FuncionarioResponse out=service.salvarFuncionario(null,in); return created("funcionarios",out.id(),out); }
    @PutMapping("/funcionarios/{id}") public FuncionarioResponse editarFuncionario(@PathVariable UUID id,@Valid @RequestBody FuncionarioUpsertRequest in) { return service.salvarFuncionario(id,in); }
    @DeleteMapping("/funcionarios/{id}") public ResponseEntity<Void> excluirFuncionario(@PathVariable UUID id) { service.excluirFuncionario(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/funcionario-unidades") public ResponseEntity<VinculoResponse> vincularFuncionario(@Valid @RequestBody FuncionarioUnidadeUpsertRequest in) { VinculoResponse out=service.vincularFuncionario(in); return created("funcionario-unidades",out.id(),out); }

    @GetMapping("/macroprocessos") public Page<MacroProcessoResponse> macroProcessos(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.macroProcessos(busca,p); }
    @PostMapping("/macroprocessos") public ResponseEntity<MacroProcessoResponse> criarMacro(@Valid @RequestBody MacroProcessoUpsertRequest in) { MacroProcessoResponse out=service.salvarMacroProcesso(null,in); return created("macroprocessos",out.id(),out); }
    @PutMapping("/macroprocessos/{id}") public MacroProcessoResponse editarMacro(@PathVariable UUID id,@Valid @RequestBody MacroProcessoUpsertRequest in) { return service.salvarMacroProcesso(id,in); }
    @DeleteMapping("/macroprocessos/{id}") public ResponseEntity<Void> excluirMacro(@PathVariable UUID id) { service.excluirMacroProcesso(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/processos") public Page<ProcessoResponse> processos(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.processos(busca,p); }
    @GetMapping("/processos/{id}/detalhes") public ProcessoDetalheResponse detalheProcesso(@PathVariable UUID id) { return service.detalheProcesso(id); }
    @PostMapping("/processos") public ResponseEntity<ProcessoResponse> criarProcesso(@Valid @RequestBody ProcessoUpsertRequest in) { ProcessoResponse out=service.salvarProcesso(null,in); return created("processos",out.id(),out); }
    @PutMapping("/processos/{id}") public ProcessoResponse editarProcesso(@PathVariable UUID id,@Valid @RequestBody ProcessoUpsertRequest in) { return service.salvarProcesso(id,in); }
    @DeleteMapping("/processos/{id}") public ResponseEntity<Void> excluirProcesso(@PathVariable UUID id) { service.excluirProcesso(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/clientes") public Page<CatalogoResponse> clientes(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.clientes(busca,p); }
    @PostMapping("/clientes") public ResponseEntity<CatalogoResponse> criarCliente(@Valid @RequestBody CatalogoUpsertRequest in) { CatalogoResponse out=service.salvarCliente(null,in); return created("clientes",out.id(),out); }
    @PutMapping("/clientes/{id}") public CatalogoResponse editarCliente(@PathVariable UUID id,@Valid @RequestBody CatalogoUpsertRequest in) { return service.salvarCliente(id,in); }
    @DeleteMapping("/clientes/{id}") public ResponseEntity<Void> excluirCliente(@PathVariable UUID id) { service.excluirCliente(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/fornecedores") public Page<CatalogoResponse> fornecedores(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.fornecedores(busca,p); }
    @PostMapping("/fornecedores") public ResponseEntity<CatalogoResponse> criarFornecedor(@Valid @RequestBody CatalogoUpsertRequest in) { CatalogoResponse out=service.salvarFornecedor(null,in); return created("fornecedores",out.id(),out); }
    @PutMapping("/fornecedores/{id}") public CatalogoResponse editarFornecedor(@PathVariable UUID id,@Valid @RequestBody CatalogoUpsertRequest in) { return service.salvarFornecedor(id,in); }
    @DeleteMapping("/fornecedores/{id}") public ResponseEntity<Void> excluirFornecedor(@PathVariable UUID id) { service.excluirFornecedor(id); return ResponseEntity.noContent().build(); }

    @GetMapping("/compliance/internos") public Page<CatalogoResponse> complianceInternos(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.complianceInternos(busca,p); }
    @PostMapping("/compliance/internos") public ResponseEntity<CatalogoResponse> criarComplianceInterno(@Valid @RequestBody CatalogoUpsertRequest in) { CatalogoResponse out=service.salvarComplianceInterno(null,in); return created("compliance/internos",out.id(),out); }
    @PutMapping("/compliance/internos/{id}") public CatalogoResponse editarComplianceInterno(@PathVariable UUID id,@Valid @RequestBody CatalogoUpsertRequest in) { return service.salvarComplianceInterno(id,in); }
    @DeleteMapping("/compliance/internos/{id}") public ResponseEntity<Void> excluirComplianceInterno(@PathVariable UUID id) { service.excluirComplianceInterno(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/compliance/externos") public Page<CatalogoResponse> complianceExternos(@RequestParam(defaultValue="") String busca,@PageableDefault(size=20,sort="nome") Pageable p) { return service.complianceExternos(busca,p); }
    @PostMapping("/compliance/externos") public ResponseEntity<CatalogoResponse> criarComplianceExterno(@Valid @RequestBody CatalogoUpsertRequest in) { CatalogoResponse out=service.salvarComplianceExterno(null,in); return created("compliance/externos",out.id(),out); }
    @PutMapping("/compliance/externos/{id}") public CatalogoResponse editarComplianceExterno(@PathVariable UUID id,@Valid @RequestBody CatalogoUpsertRequest in) { return service.salvarComplianceExterno(id,in); }
    @DeleteMapping("/compliance/externos/{id}") public ResponseEntity<Void> excluirComplianceExterno(@PathVariable UUID id) { service.excluirComplianceExterno(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/processos/vinculos/{tipo}") public ResponseEntity<VinculoResponse> vincular(@PathVariable String tipo,@Valid @RequestBody ProcessoVinculoUpsertRequest in) { VinculoResponse out=service.vincular(tipo,in); return created("processos/vinculos/"+tipo,out.id(),out); }
    @DeleteMapping("/processos/vinculos/{tipo}/{id}") public ResponseEntity<Void> excluirVinculo(@PathVariable String tipo,@PathVariable UUID id) { service.excluirVinculo(tipo,id); return ResponseEntity.noContent().build(); }

    private <T> ResponseEntity<T> created(String path, UUID id, T body) { return ResponseEntity.created(URI.create("/api/"+path+"/"+id)).body(body); }
}
