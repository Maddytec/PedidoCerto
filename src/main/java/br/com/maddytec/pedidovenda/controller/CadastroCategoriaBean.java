package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Categoria;
import br.com.maddytec.pedidovenda.repository.Categorias;
import br.com.maddytec.pedidovenda.service.CadastroCategoriaService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroCategoriaService cadastroCategoriaService;

	@Inject
	private Categorias categorias;

	private Categoria categoria;
	private List<Categoria> categoriasRaizes;

	public CadastroCategoriaBean() {
		limpar();
	}

	private void limpar() {
		categoria = new Categoria();
	}

	public void inicializar() {

		if (this.categoria == null) {
			limpar();
		}
		
		categoriasRaizes = categorias.raizes();

	}

	public void salvar() {
		this.categoria = cadastroCategoriaService.salvar(this.categoria);
		limpar();

		FacesUtil.addInfoMessage("Categoria salvada com sucesso!");
	}

	public boolean isEditando() {
		return this.categoria.getId() != null;
	}

	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	public Categorias getCategorias() {
		return categorias;
	}

	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}

	public void setCategoriasRaizes(List<Categoria> categoriasRaizes) {
		this.categoriasRaizes = categoriasRaizes;
	}
}