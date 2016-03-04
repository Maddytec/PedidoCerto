package br.com.maddytec.pedidovenda.controller;

import br.com.maddytec.pedidovenda.model.Orcamento;

public class OrcamentoAlteradoEvent {

	private Orcamento orcamento;

	public OrcamentoAlteradoEvent(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}
}
