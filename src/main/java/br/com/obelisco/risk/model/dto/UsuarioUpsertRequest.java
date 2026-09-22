package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record UsuarioUpsertRequest(@NotBlank String login, @NotBlank String nome, @Size(min=8) String senha, @NotBlank String perfil, @NotNull UUID empresaId, boolean ativo) {}
