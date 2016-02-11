package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import br.com.maddytec.pedidovenda.model.Categoria;
import br.com.maddytec.pedidovenda.repository.Categorias;
import br.com.maddytec.pedidovenda.repository.filter.CategoriaFilter;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaCategoriasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;

	private CategoriaFilter filtro;

	private List<Categoria> categoriasFiltrados;

	private Categoria categoriaSelecionado;

	public PesquisaCategoriasBean() {
		filtro = new CategoriaFilter();
	}

	public void pesquisar() {
		categoriasFiltrados = categorias.filtrados(filtro);
	}

	public void excluirCategoria() {
		if (categoriaSelecionado != null) {
			try {
				categorias.remover(categoriaSelecionado);
				categoriasFiltrados.remove(categoriaSelecionado);

				FacesUtil.addInfoMessage("Categoria " + categoriaSelecionado.getDescricao()
						+ " foi excluído com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("A categoria "
						+ categoriaSelecionado.getDescricao()
						+ " não pode ser excluída.");
			}
		}
	}
	
	public void excluirSubcategoria() {
		if (categoriaSelecionado != null) {
			try {
				categorias.remover(categoriaSelecionado);
				categoriasFiltrados.remove(categoriaSelecionado);

				FacesUtil.addInfoMessage("Subcategoria " + categoriaSelecionado.getDescricao()
						+ " foi excluída com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("A subcategoria "
						+ categoriaSelecionado.getDescricao()
						+ " não pode ser excluída.");
			}
		}
	}

	
	

	public List<Categoria> getCategoriasFiltrados() {
		return categoriasFiltrados;
	}

	public CategoriaFilter getFiltro() {
		return filtro;
	}

	public Categoria getCategoriaSelecionado() {
		return categoriaSelecionado;
	}

	public void setCategoriaSelecionado(Categoria categoriaSelecionado) {
		this.categoriaSelecionado = categoriaSelecionado;
	}

}