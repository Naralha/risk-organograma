package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record FuncionarioUnidadeUpsertRequest(@NotNull UUID funcionarioId, @NotNull UUID unidadeId, @NotNull LocalDate inicio, LocalDate fim, boolean responsavel) {}
