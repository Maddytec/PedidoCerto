package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.UnidadeDeMedida;
import br.com.maddytec.pedidovenda.repository.UnidadeDeMedidas;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroUnidadeDeMedidaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeDeMedidas unidadeDeMedidas;

	@Transactional
	public UnidadeDeMedida salvar(UnidadeDeMedida unidadeDeMedida) {
		UnidadeDeMedida unidadeDeMedidaExistente = unidadeDeMedidas.porDescricao(unidadeDeMedida.getDescricao());

		if (unidadeDeMedidaExistente != null && !unidadeDeMedidaExistente.equals(unidadeDeMedida)) {
			throw new NegocioException("Já existe a unidade de medida informada.");
		}

		return unidadeDeMedidas.guardar(unidadeDeMedida);

	}
	
	
	public List<UnidadeDeMedida> getAllUnidadeDeMedidas(){
		return unidadeDeMedidas.lista();
	}
}
