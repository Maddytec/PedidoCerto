package br.com.maddytec.pedidovenda.service;

import java.io.Serializable;

import javax.inject.Inject;


import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.repository.Usuarios;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class CadastroUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());

		if (usuarioExistente != null && !usuarioExistente.equals(usuario)) {
			throw new NegocioException("Já existe o usuário informado.");
		}

		return usuarios.guardar(usuario);

	}

}
