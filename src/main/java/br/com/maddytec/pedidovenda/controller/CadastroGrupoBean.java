package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Grupo;
import br.com.maddytec.pedidovenda.service.CadastroGrupoService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroGrupoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroGrupoService cadastroGrupoService;

	private Grupo grupo;

	public CadastroGrupoBean() {
		limpar();
	}

	private void limpar() {
		grupo = new Grupo();
	}

	public void inicializar() {
		System.out.println("Inicializando...");

		if (this.grupo == null) {
			limpar();
		}
	}

	public void salvar() {
		this.grupo = cadastroGrupoService.salvar(this.grupo);
		limpar();

		FacesUtil.addInfoMessage("Grupo salvo com sucesso!");
	}

	public boolean isEditando() {
		return this.grupo.getId() != null;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

}
