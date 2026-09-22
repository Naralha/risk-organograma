package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record FuncionarioUpsertRequest(@NotBlank String codigo, @NotBlank String nome, @NotBlank @Email String email, String descricao, @NotNull UUID empresaId) {}
