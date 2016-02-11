package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.maddytec.pedidovenda.model.Grupo;
import br.com.maddytec.pedidovenda.repository.Grupos;
import br.com.maddytec.pedidovenda.repository.filter.GrupoFilter;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaGruposBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;

	private GrupoFilter filtro;

	private List<Grupo> gruposFiltrados;

	private Grupo grupoSelecionado;

	public PesquisaGruposBean() {
		filtro = new GrupoFilter();
	}

	public void pesquisar() {
		gruposFiltrados = grupos.filtrados(filtro);
	}

	public void excluir() {
		if (grupoSelecionado != null) {
			try {
				grupos.remover(grupoSelecionado);
				gruposFiltrados.remove(grupoSelecionado);

				FacesUtil.addInfoMessage("Grupo " + grupoSelecionado.getNome()
						+ " foi excluído com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("O grupo "
						+ grupoSelecionado.getNome()
						+ " não pode ser excluído.");
			}
		}
	}

	public List<Grupo> getGruposFiltrados() {
		return gruposFiltrados;
	}

	public GrupoFilter getFiltro() {
		return filtro;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

}