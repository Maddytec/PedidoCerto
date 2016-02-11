package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.service.BaixaPedidoService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class BaixaPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BaixaPedidoService baixaPedidoService;

	@Inject
	private Event<PedidoAlteradoEvent> pedidoAlteradoEvent;

	@Inject
	@PedidoEdicao
	private Pedido pedido;

	public void baixarPedido() {
		this.pedido = this.baixaPedidoService.baixa(this.pedido);
		this.pedidoAlteradoEvent.fire(new PedidoAlteradoEvent(this.pedido));

		FacesUtil.addInfoMessage("Pedido baixado com sucesso!");
	}
}
