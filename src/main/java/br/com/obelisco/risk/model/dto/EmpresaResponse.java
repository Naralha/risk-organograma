package br.com.obelisco.risk.model.dto;

import java.util.UUID;

public record EmpresaResponse(UUID id, String codigo, String nome, String descricao) {}
