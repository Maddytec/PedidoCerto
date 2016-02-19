package br.com.maddytec.pedidovenda.controller;

import br.com.maddytec.pedidovenda.model.Pedido;

public class PedidoAlteradoEvent {

	private Pedido pedido;

	public PedidoAlteradoEvent(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedido() {
		return pedido;
	}
}
