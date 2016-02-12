package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.maddytec.pedidovenda.model.Fornecedor;
import br.com.maddytec.pedidovenda.repository.Fornecedores;
import br.com.maddytec.pedidovenda.repository.filter.FornecedorFilter;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaFornecedoresBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Fornecedores fornecedores;

	private FornecedorFilter filtro;

	private List<Fornecedor> fornecedoresFiltrados;

	private Fornecedor fornecedorSelecionado;

	public PesquisaFornecedoresBean() {
		filtro = new FornecedorFilter();
	}

	public void pesquisar() {
		fornecedoresFiltrados = fornecedores.filtrados(filtro);
	}

	public void excluir() {
		if (fornecedorSelecionado != null) {
			try {
				fornecedores.remover(fornecedorSelecionado);
				fornecedoresFiltrados.remove(fornecedorSelecionado);

				FacesUtil.addInfoMessage("Fornecedor "
						+ fornecedorSelecionado.getNome()
						+ " excluído com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("O fornecedor "
						+ fornecedorSelecionado.getNome()
						+ " não pode ser excluído.");
			}
		}

	}

	public List<Fornecedor> getFornecedoresFiltrados() {
		return fornecedoresFiltrados;
	}

	public FornecedorFilter getFiltro() {
		return filtro;
	}

	public Fornecedor getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}

	public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
	}

}