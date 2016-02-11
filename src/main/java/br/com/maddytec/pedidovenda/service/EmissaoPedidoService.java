package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.model.StatusPedido;
import br.com.maddytec.pedidovenda.repository.Pedidos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class EmissaoPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroPedidoService cadastroPedidoService;

	@Inject
	private Pedidos pedidos;

	@Inject
	private EstoqueService estoqueService;
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		pedido = this.cadastroPedidoService.salvar(pedido);

		if (pedido.isNaoEmissivel()) {
			throw new NegocioException(
					"Pedido não pode ser emitido com status "
							+ pedido.getStatus().getDescricao() + ".");
		}

		this.estoqueService.baixarItensEstoque(pedido);
		
		pedido.setStatus(StatusPedido.EMITIDO);
		
		pedido = this.pedidos.guardar(pedido);
		
		return pedido;
	}

}
