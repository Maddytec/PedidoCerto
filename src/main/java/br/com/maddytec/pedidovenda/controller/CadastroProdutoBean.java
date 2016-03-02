package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import br.com.maddytec.pedidovenda.model.Categoria;
import br.com.maddytec.pedidovenda.model.Produto;
import br.com.maddytec.pedidovenda.model.UnidadeDeMedida;
import br.com.maddytec.pedidovenda.repository.Categorias;
import br.com.maddytec.pedidovenda.repository.UnidadeDeMedidas;
import br.com.maddytec.pedidovenda.service.CadastroProdutoService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Categorias categorias;
	
	@Inject
	private UnidadeDeMedidas unidadeDeMedidas;
	
	@Inject
	private CadastroProdutoService cadastroProdutoService;

	private Produto produto;

	private Categoria categoria;
	
	private UnidadeDeMedida unidadeDeMedida;

	private List<Categoria> allCategorias;

	public CadastroProdutoBean() {
		limpar();
	}

	public void inicializar() {

		if (this.produto == null) {
			limpar();
		}

		this.allCategorias = categorias.lista();

	}

	private void limpar() {
		produto = new Produto();
		categoria = null;
		unidadeDeMedida = null;
	}

	public void salvar() {
		this.produto.setUnidadeDeMedida(unidadeDeMedida);
		this.produto.setCategoria(categoria);
		this.produto = cadastroProdutoService.salvar(this.produto);
		limpar();

		FacesUtil.addInfoMessage("Produto salvo com sucesso!");
	}

	public boolean isEditando() {
		return this.produto.getId() != null;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;

		if (produto != null) {
			this.categoria = this.produto.getCategoria();
			this.unidadeDeMedida = this.produto.getUnidadeDeMedida();
		}
	}

	public List<Categoria> getAllCategorias() {
		return allCategorias;
	}

	public Categorias getCategorias() {
		return categorias;
	}

	public void setCategorias(Categorias categorias) {
		this.categorias = categorias;
	}

	@NotNull
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@NotNull
	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public UnidadeDeMedidas getUnidadeDeMedidas() {
		return unidadeDeMedidas;
	}

	public void setUnidadeDeMedidas(UnidadeDeMedidas unidadeDeMedidas) {
		this.unidadeDeMedidas = unidadeDeMedidas;
	}
	
	
	
	
	

}