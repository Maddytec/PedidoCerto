package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.repository.Usuarios;
import br.com.maddytec.pedidovenda.repository.filter.UsuarioFilter;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaUsuariosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	private UsuarioFilter filtro;

	private List<Usuario> usuariosFiltrados;

	private Usuario usuarioSelecionado;

	public PesquisaUsuariosBean() {
		filtro = new UsuarioFilter();
	}

	public void pesquisar() {
		usuariosFiltrados = usuarios.filtrados(filtro);
	}

	public void excluir() {
		if (usuarioSelecionado != null) {
			try {
				usuarios.remover(usuarioSelecionado);
				usuariosFiltrados.remove(usuarioSelecionado);

				FacesUtil.addInfoMessage("Usuario "
						+ usuarioSelecionado.getNome()
						+ " excluído com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("O produto "
						+ usuarioSelecionado.getNome()
						+ " não pode ser excluído.");
			}
		}
	}

	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public UsuarioFilter getFiltro() {
		return filtro;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}