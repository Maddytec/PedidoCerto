package br.com.maddytec.pedidovenda.model;

public enum TipoPessoa {

	FISICA("Física"),
	JURIDICA("Jurídica");
	
	private String descricao;
	
	TipoPessoa(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}