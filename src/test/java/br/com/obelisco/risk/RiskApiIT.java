package br.com.obelisco.risk;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
class RiskApiIT {
    @Autowired MockMvc mvc;
    @Autowired UserDetailsService users;
    @Autowired PasswordEncoder passwords;
    final ObjectMapper json = new ObjectMapper();

    @Test
    void exigeAutenticacaoNosEndpointsDeNegocio() throws Exception {
        mvc.perform(get("/api/dashboard")).andExpect(status().isUnauthorized());
    }

    @Test
    void autenticaComUsuarioCriadoPeloFlyway() throws Exception {
        assertTrue(passwords.matches("password", users.loadUserByUsername("admin").getPassword()));
        mvc.perform(get("/api/dashboard").with(httpBasic("admin", "password")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.empresas").value(greaterThanOrEqualTo(1)))
            .andExpect(jsonPath("$.unidades").value(greaterThanOrEqualTo(3)));
    }

    @Test
    void rejeitaContratoInvalidoComProblemDetails() throws Exception {
        mvc.perform(post("/api/empresas").with(httpBasic("admin", "password"))
                .contentType(MediaType.APPLICATION_JSON).content("{\"codigo\":\"\",\"nome\":\"\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(jsonPath("$.title").value("Falha de validação"))
            .andExpect(jsonPath("$.errors.codigo").exists());
    }

    @Test
    void executaFluxoCompletoDoModeloDeNegocio() throws Exception {
        String empresa = postJson("/api/empresas", Map.of("codigo","ACME","nome","Acme Risk"));
        String empresaId = id(empresa);
        String raiz = postJson("/api/unidades", Map.of("codigo","DIR","nome","Diretoria","empresaId",empresaId));
        String raizId = id(raiz);
        String unidade = postJson("/api/unidades", Map.of("codigo","OPS","nome","Operações","empresaId",empresaId,"parentId",raizId));
        String unidadeId = id(unidade);
        String funcionario = postJson("/api/funcionarios", Map.of("codigo","F001","nome","Ana Silva","email","ana@acme.test","empresaId",empresaId));
        String funcionarioId = id(funcionario);

        postJson("/api/funcionario-unidades", Map.of("funcionarioId",funcionarioId,"unidadeId",unidadeId,"inicio",LocalDate.now().toString(),"responsavel",true));
        String macro = postJson("/api/macroprocessos", Map.of("codigo","MP01","nome","Operação","empresaId",empresaId));
        String macroId = id(macro);
        String processo = postJson("/api/processos", Map.of("codigo","P001","nome","Atender cliente","objetivo","Entregar o serviço contratado","status","ATIVO","inicio",LocalDate.now().toString(),"empresaId",empresaId,"macroProcessoId",macroId,"unidadeId",unidadeId));
        String processoId = id(processo);
        String cliente = postJson("/api/clientes", Map.of("codigo","C001","nome","Cliente X","empresaId",empresaId));
        String compliance = postJson("/api/compliance/internos", Map.of("codigo","POL01","nome","Política de atendimento","empresaId",empresaId));
        String clienteVinculo = postJson("/api/processos/vinculos/cliente", Map.of("origemId",id(cliente),"processoId",processoId));
        postJson("/api/processos/vinculos/compliance-interno", Map.of("origemId",id(compliance),"processoId",processoId));

        mvc.perform(get("/api/unidades/tree").param("empresaId", empresaId).with(httpBasic("admin","password")))
            .andExpect(status().isOk()).andExpect(jsonPath("$[0].children[0].nome").value("Operações"))
            .andExpect(jsonPath("$[0].children[0].funcionarios").value(1));
        mvc.perform(get("/api/processos").param("busca","Atender").with(httpBasic("admin","password")))
            .andExpect(status().isOk()).andExpect(jsonPath("$.content[0].macroProcessoNome").value("Operação"));
        mvc.perform(get("/api/processos/{id}/detalhes", processoId).with(httpBasic("admin","password")))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.clientes[0].nome").value("Cliente X"))
            .andExpect(jsonPath("$.compliance[0].nome").value("Política de atendimento"));
        mvc.perform(delete("/api/processos/vinculos/cliente/{id}", id(clienteVinculo)).with(httpBasic("admin","password")))
            .andExpect(status().isNoContent());
    }

    private String postJson(String url, Object body) throws Exception {
        return mvc.perform(post(url).with(httpBasic("admin","password")).contentType(MediaType.APPLICATION_JSON).content(json.writeValueAsBytes(body)))
            .andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
    }
    private String id(String body) throws Exception { return json.readTree(body).get("id").asText(); }
}
