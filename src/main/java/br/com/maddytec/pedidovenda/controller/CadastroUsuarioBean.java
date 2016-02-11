package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Grupo;
import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.service.CadastroGrupoService;
import br.com.maddytec.pedidovenda.service.CadastroUsuarioService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroUsuarioService cadastroUsuarioService;

	@Inject
	private CadastroGrupoService cadastroGrupoService;

	private Usuario usuario;
	private Grupo grupo;
	private Grupo grupoSelecionado;

	private List<Grupo> grupos;

	public CadastroUsuarioBean() {
		limpar();
	}

	private void limpar() {
		usuario = new Usuario();
		grupos = new ArrayList<>();

	}

	public void inicializar() {
		System.out.println("Inicializando...");

		if (this.usuario== null) {
			limpar();
		}
		
		carregarGrupos();
	}

	public void salvar() {
		this.usuario = cadastroUsuarioService.salvar(this.usuario);
		limpar();

		FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
	}

	public void adicionarGrupo() {
		if (grupo != null) {
			usuario.getGrupos().add(grupo);
			// FacesUtil.addInfoMessage("Grupo incluído!");
			grupo = null;
		}
	}

	public void removerGrupo() {
		if (grupo != null) {
			usuario.getGrupos().remove(grupo);
			// FacesUtil.addInfoMessage("Grupo excluído!");
			grupo = null;
		}
	}

	public boolean isEditando() {
		return this.usuario.getId() != null;
	}

	public void carregarGrupos() {
		grupos = cadastroGrupoService.getAllGrupos();

		for (Grupo g : usuario.getGrupos()) {
			grupos.remove(g);
		}

	}

	public boolean isBotaoAddGrupoDisabled() {
		return grupos.isEmpty() || grupo == null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}
}
