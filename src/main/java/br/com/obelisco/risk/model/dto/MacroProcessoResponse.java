package br.com.obelisco.risk.model.dto;

import java.util.UUID;

public record MacroProcessoResponse(UUID id, String codigo, String nome, String descricao, UUID empresaId, String empresaNome) {}
