package br.com.obelisco.risk.model.dto;

import java.util.UUID;

public record UsuarioResponse(UUID id, String login, String nome, String perfil, boolean ativo, UUID empresaId, String empresaNome) {}
