package br.com.obelisco.risk.model.dto;

import java.util.UUID;

public record RelacionamentoResponse(UUID id, UUID origemId, String nome, String tipo) {}
