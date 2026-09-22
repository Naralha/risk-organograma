package br.com.obelisco.risk.model.dto;

import java.util.List;
import java.util.UUID;

public record UnidadeResponse(UUID id, String codigo, String nome, String descricao, UUID empresaId, UUID parentId, long funcionarios, List<UnidadeResponse> children) {}
