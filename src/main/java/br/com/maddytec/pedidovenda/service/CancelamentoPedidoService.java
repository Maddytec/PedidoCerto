package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.model.StatusPedido;
import br.com.maddytec.pedidovenda.repository.Pedidos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CancelamentoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;

	@Inject
	private EstoqueService estoqueService;

	@Transactional
	public Pedido cancelar(Pedido pedido) {
		pedido = this.pedidos.porId(pedido.getId());

		if (pedido.isNaoCancelavel()) {
			throw new NegocioException(
					"Pedido não pode ser cancelado no status "
							+ pedido.getStatus().getDescricao() + ".");
		}

		if(pedido.isEmitido()){
			this.estoqueService.retornarItensEstoque(pedido);
			
		}
		pedido.setStatus(StatusPedido.CANCELADO);
		
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}
}
