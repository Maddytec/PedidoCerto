package br.com.maddytec.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.maddytec.pedidovenda.model.Endereco;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class Enderecos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Endereco porId(Long id) {
		return manager.find(Endereco.class, id);
	}

	public List<Endereco> enderecos() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return manager.createQuery("from Endereco", Endereco.class)
				.getResultList();
	}

	@Transactional
	public Endereco guardar(Endereco endereco) {
		return manager.merge(endereco);
	}

	@Transactional
	public void remover(Endereco endereco) {
		endereco = porId(endereco.getId());
		manager.remove(endereco);
		manager.flush();
	}

}
