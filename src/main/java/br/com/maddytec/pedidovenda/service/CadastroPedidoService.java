package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.model.StatusPedido;
import br.com.maddytec.pedidovenda.repository.Pedidos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroPedidoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pedidos pedidos;

	@Transactional
	public Pedido salvar(Pedido pedido) {

		if (pedido.isNovo()) {
			pedido.setDataCriacao(new Date());
			pedido.setStatus(StatusPedido.ORCAMENTO);
		}

		if (pedido.isNaoAlteravel()) {
			throw new NegocioException(
					"Pedido não pode ser alterado no status "
							+ pedido.getStatus().getDescricao() + ".");
		}

		if (pedido.getItens().isEmpty()) {
			throw new NegocioException(
					"O pedido deve possuir pelo menos um item.");
		}

		pedido = this.pedidos.guardar(pedido);
		return pedido;
	}

}
