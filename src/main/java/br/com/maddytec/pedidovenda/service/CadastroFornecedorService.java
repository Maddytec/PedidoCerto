package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;


import br.com.maddytec.pedidovenda.model.Fornecedor;
import br.com.maddytec.pedidovenda.repository.Fornecedores;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroFornecedorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Fornecedores fornecedores;

	@Transactional
	public Fornecedor salvar(Fornecedor fornecedor) {
		
		Fornecedor fornecedorExistente = fornecedores.porDocumentoReceitaFederal(fornecedor.getDocumentoReceitaFederal());
		
		if (fornecedorExistente != null && !fornecedorExistente.equals(fornecedor)) {
			throw new NegocioException("Já existe o documento informado.");
		}
		
		
		fornecedorExistente = fornecedores.porEmail(fornecedor.getEmail());
		
		if (fornecedorExistente != null && !fornecedorExistente.equals(fornecedor)) {
			throw new NegocioException("Já existe o E-mail informado.");
		}

		return fornecedores.guardar(fornecedor);

	}

}
