package br.com.obelisco.risk.model.dto;

import java.util.List;

public record ProcessoDetalheResponse(ProcessoResponse processo, List<RelacionamentoResponse> clientes, List<RelacionamentoResponse> fornecedores, List<RelacionamentoResponse> compliance, List<RelacionamentoResponse> unidades) {}
