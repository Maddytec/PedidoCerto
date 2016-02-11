package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.ItemPedido;
import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.repository.Pedidos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class EstoqueService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;

	@Transactional
	public void baixarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for(ItemPedido item : pedido.getItens()){
			item.getProduto().baixarEstoque(item.getQuantidade());
		}
		
	}

	public void retornarItensEstoque(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());
		
		for(ItemPedido item : pedido.getItens()){
			item.getProduto().adicionarEstoque(item.getQuantidade());
		}
	}
	
	
}
