package br.com.maddytec.pedidovenda.repository.filter;

import java.io.Serializable;

public class GrupoFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao == null ? null : descricao.toLowerCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome == null ? null : nome.toLowerCase();
	}

}
