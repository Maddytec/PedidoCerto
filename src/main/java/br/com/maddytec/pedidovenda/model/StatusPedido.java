package br.com.maddytec.pedidovenda.model;

public enum StatusPedido {
	
	ATIVO("Ativo"),
	CANCELADO("Cancelado");
		
	private String descricao;
	
	StatusPedido(String descricao) {
	this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
