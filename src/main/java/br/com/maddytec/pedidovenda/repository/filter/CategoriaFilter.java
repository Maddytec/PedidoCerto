package br.com.maddytec.pedidovenda.repository.filter;

import java.io.Serializable;

public class CategoriaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subcategoria;
	private String categoria;

	public String getSubcategoria() {
		return subcategoria;
	}

	public void setSubcategoria(String subcategoria) {
		this.subcategoria = subcategoria == null ? null : subcategoria.toLowerCase();
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria == null ? null : categoria.toLowerCase();
	}
	
}
