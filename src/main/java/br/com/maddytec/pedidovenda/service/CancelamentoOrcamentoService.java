package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Orcamento;
import br.com.maddytec.pedidovenda.model.StatusOrcamento;
import br.com.maddytec.pedidovenda.repository.Orcamentos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CancelamentoOrcamentoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Orcamentos orcamentos;

	@Transactional
	public Orcamento cancelar(Orcamento orcamento) {
		orcamento = this.orcamentos.porId(orcamento.getId());

		if (orcamento.isNaoCancelavel()) {
			throw new NegocioException(
					"Orcamento não pode ser cancelado no status "
							+ orcamento.getStatus().getDescricao() + ".");
		}

		orcamento.setStatus(StatusOrcamento.CANCELADO);

		orcamento = this.orcamentos.guardar(orcamento);

		return orcamento;
	}
}
