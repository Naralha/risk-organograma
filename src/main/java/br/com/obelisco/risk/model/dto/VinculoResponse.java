package br.com.obelisco.risk.model.dto;

import java.util.UUID;

public record VinculoResponse(UUID id, UUID origemId, UUID destinoId, String tipo) {}
