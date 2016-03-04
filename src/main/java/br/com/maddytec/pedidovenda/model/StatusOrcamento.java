package br.com.maddytec.pedidovenda.model;

public enum StatusOrcamento {
	
	ORCAMENTO("Orçamento"),
	CANCELADO("Cancelado");
		
	private String descricao;
	
	StatusOrcamento(String descricao) {
	this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
