package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Orcamento;
import br.com.maddytec.pedidovenda.model.StatusOrcamento;
import br.com.maddytec.pedidovenda.repository.Orcamentos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroOrcamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Orcamentos orcamentos;

	@Transactional
	public Orcamento salvar(Orcamento orcamento) {

		if (orcamento.isNovo()) {
			orcamento.setDataCriacao(new Date());
			orcamento.setStatus(StatusOrcamento.ORCAMENTO);
		}

		if (orcamento.isNaoAlteravel()) {
			throw new NegocioException(
					"Orcamento não pode ser alterado no status "
							+ orcamento.getStatus().getDescricao() + ".");
		}

		if (orcamento.getItens().isEmpty()) {
			throw new NegocioException(
					"O orcamento deve possuir pelo menos um item.");
		}

		orcamento = this.orcamentos.guardar(orcamento);
		return orcamento;
	}

}
