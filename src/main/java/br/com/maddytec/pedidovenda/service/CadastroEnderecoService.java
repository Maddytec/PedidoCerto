package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;


import br.com.maddytec.pedidovenda.model.Endereco;
import br.com.maddytec.pedidovenda.repository.Enderecos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroEnderecoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Enderecos enderecos;

	@Transactional
	public Endereco salvar(Endereco endereco) {
		Endereco enderecoExistente = enderecos.porId(endereco.getId());

		if (enderecoExistente != null && !enderecoExistente.equals(endereco)) {
			throw new NegocioException("Já existe o endereço informado.");
		}

		return enderecos.guardar(endereco);

	}

}
