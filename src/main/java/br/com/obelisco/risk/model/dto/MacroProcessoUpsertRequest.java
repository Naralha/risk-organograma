package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record MacroProcessoUpsertRequest(@NotBlank String codigo, @NotBlank String nome, String descricao, @NotNull UUID empresaId) {}
