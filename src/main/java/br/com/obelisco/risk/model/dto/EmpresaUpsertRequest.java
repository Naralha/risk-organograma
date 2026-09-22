package br.com.obelisco.risk.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EmpresaUpsertRequest(@NotBlank @Size(max=40) String codigo, @NotBlank @Size(max=180) String nome, @Size(max=1000) String descricao) {}
