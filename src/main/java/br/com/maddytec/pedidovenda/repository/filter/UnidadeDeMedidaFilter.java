package br.com.maddytec.pedidovenda.repository.filter;

import java.io.Serializable;

public class UnidadeDeMedidaFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String unidadeDeMedida;

	public String getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(String unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida == null ? null : unidadeDeMedida.toLowerCase();
	}
	
}
