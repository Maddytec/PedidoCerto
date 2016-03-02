package br.com.maddytec.pedidovenda.repository.filter;

import java.io.Serializable;

public class CategoriaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String categoria;

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria == null ? null : categoria.toLowerCase();
	}
	
}
