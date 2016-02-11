package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Produto;
import br.com.maddytec.pedidovenda.repository.Produtos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Produtos produtos;

	@Transactional
	public Produto salvar(Produto produto) {
		Produto produtoExistente = produtos.porSku(produto.getSku());

		if (produtoExistente != null && !produtoExistente.equals(produto)) {
			throw new NegocioException("Já existe produto com o SKU informado.");
		}

		return produtos.guardar(produto);

	}

}
