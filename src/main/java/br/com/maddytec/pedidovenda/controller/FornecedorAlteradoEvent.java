package br.com.maddytec.pedidovenda.controller;

import br.com.maddytec.pedidovenda.model.Fornecedor;

public class FornecedorAlteradoEvent {

	private Fornecedor fornecedor;

	public FornecedorAlteradoEvent(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}
}
