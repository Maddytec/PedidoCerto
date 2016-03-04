package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Orcamento;
import br.com.maddytec.pedidovenda.service.CancelamentoOrcamentoService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@RequestScoped
public class CancelamentoOrcamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CancelamentoOrcamentoService cancelamentoOrcamentoService;

	@Inject
	private Event<OrcamentoAlteradoEvent> orcamentoAlteradoEvent;

	@Inject
	@OrcamentoEdicao
	private Orcamento orcamento;

	public void cancelarOrcamento() {
		this.orcamento = this.cancelamentoOrcamentoService.cancelar(this.orcamento);
		this.orcamentoAlteradoEvent.fire(new OrcamentoAlteradoEvent(this.orcamento));

		FacesUtil.addInfoMessage("Orcamento cancelado com sucesso!");
	}
}
