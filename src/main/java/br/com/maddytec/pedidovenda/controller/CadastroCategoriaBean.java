package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Categoria;
import br.com.maddytec.pedidovenda.service.CadastroCategoriaService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroCategoriaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroCategoriaService cadastroCategoriaService;

	private Categoria categoria;

	public CadastroCategoriaBean() {
		limpar();
	}

	private void limpar() {
		categoria = new Categoria();
	}

	public void inicializar() {
		System.out.println("Inicializando...");

		if (this.categoria == null) {
			limpar();
		}
	}

	public void salvar() {
		this.categoria = cadastroCategoriaService.salvar(this.categoria);
		limpar();

		FacesUtil.addInfoMessage("Categoria salva com sucesso!");
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

	
	
}
