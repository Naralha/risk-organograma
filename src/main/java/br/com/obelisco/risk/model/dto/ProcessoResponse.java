package br.com.obelisco.risk.model.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ProcessoResponse(UUID id, String codigo, String nome, String objetivo, String status, LocalDate inicio, LocalDate fim, UUID empresaId, UUID macroProcessoId, String macroProcessoNome, UUID unidadeId, String unidadeNome) {}
