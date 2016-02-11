package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.maddytec.pedidovenda.model.Grupo;
import br.com.maddytec.pedidovenda.repository.Grupos;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;

	@Transactional
	public Grupo salvar(Grupo grupo) {
		Grupo grupoExistente = grupos.porNome(grupo.getNome());

		if (grupoExistente != null && !grupoExistente.equals(grupo)) {
			throw new NegocioException("Já existe o grupo informado.");
		}

		return grupos.guardar(grupo);

	}
	
	
	public List<Grupo> getAllGrupos(){
		return grupos.lista();
	}
	

}
