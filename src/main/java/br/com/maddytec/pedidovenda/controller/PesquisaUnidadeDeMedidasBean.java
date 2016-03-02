package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import br.com.maddytec.pedidovenda.model.UnidadeDeMedida;
import br.com.maddytec.pedidovenda.repository.UnidadeDeMedidas;
import br.com.maddytec.pedidovenda.repository.filter.UnidadeDeMedidaFilter;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUnidadeDeMedidasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeDeMedidas unidadeDeMedidas;

	private UnidadeDeMedidaFilter filtro;

	private List<UnidadeDeMedida> unidadeDeMedidasFiltrados;

	private UnidadeDeMedida unidadeDeMedidaSelecionado;

	public PesquisaUnidadeDeMedidasBean() {
		filtro = new UnidadeDeMedidaFilter();
	}

	public void pesquisar() {
		unidadeDeMedidasFiltrados = unidadeDeMedidas.filtrados(filtro);
	}

	public void excluirUnidadeDeMedida() {
		if (unidadeDeMedidaSelecionado != null) {
			try {
				unidadeDeMedidas.remover(unidadeDeMedidaSelecionado);
				unidadeDeMedidasFiltrados.remove(unidadeDeMedidaSelecionado);

				FacesUtil.addInfoMessage("Unidade de medida " + unidadeDeMedidaSelecionado.getDescricao()
						+ " foi excluído com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("A unidadeDeMedida "
						+ unidadeDeMedidaSelecionado.getDescricao()
						+ " não pode ser excluída.");
			}
		}
	}
	
	public List<UnidadeDeMedida> getUnidadeDeMedidasFiltrados() {
		return unidadeDeMedidasFiltrados;
	}

	public UnidadeDeMedidaFilter getFiltro() {
		return filtro;
	}

	public UnidadeDeMedida getUnidadeDeMedidaSelecionado() {
		return unidadeDeMedidaSelecionado;
	}

	public void setUnidadeDeMedidaSelecionado(UnidadeDeMedida unidadeDeMedidaSelecionado) {
		this.unidadeDeMedidaSelecionado = unidadeDeMedidaSelecionado;
	}

}