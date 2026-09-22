package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record ProcessoUpsertRequest(@NotBlank String codigo, @NotBlank String nome, @NotBlank String objetivo, String limiteInicial, String limiteFinal, String entradas, String saidas, String caminhoArquivo, @NotBlank String status, @NotNull LocalDate inicio, LocalDate fim, @NotNull UUID empresaId, @NotNull UUID macroProcessoId, UUID unidadeId) {}
