package br.com.maddytec.pedidovenda.controller;

import br.com.maddytec.pedidovenda.model.Cliente;

public class ClienteAlteradoEvent {

	private Cliente cliente;

	public ClienteAlteradoEvent(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
