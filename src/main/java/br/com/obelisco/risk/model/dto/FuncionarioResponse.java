package br.com.obelisco.risk.model.dto;

import java.util.UUID;

public record FuncionarioResponse(UUID id, String codigo, String nome, String email, String descricao, UUID empresaId, String empresaNome) {}
