package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ProcessoVinculoUpsertRequest(@NotNull UUID origemId, @NotNull UUID processoId, String papel) {}
