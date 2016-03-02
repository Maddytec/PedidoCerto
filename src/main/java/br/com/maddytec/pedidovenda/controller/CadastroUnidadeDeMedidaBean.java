package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.converter.UnidadeDeMedidaConverter;
import br.com.maddytec.pedidovenda.model.UnidadeDeMedida;
import br.com.maddytec.pedidovenda.repository.UnidadeDeMedidas;
import br.com.maddytec.pedidovenda.service.CadastroUnidadeDeMedidaService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroUnidadeDeMedidaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UnidadeDeMedidas unidadeDeMedidas;	
	
	@Inject
	private CadastroUnidadeDeMedidaService cadastroUnidadeDeMedidaService;

	private UnidadeDeMedidaConverter unidadeDeMedidaConverter;
	
	private UnidadeDeMedida unidadeDeMedida;

	public CadastroUnidadeDeMedidaBean() {
		limpar();
	}

	private void limpar() {
		unidadeDeMedida = new UnidadeDeMedida();
	}

	public void inicializar() {
		System.out.println("Inicializando...");

		if (this.unidadeDeMedida == null) {
			limpar();
		}
	}

	public void salvar() {
		this.unidadeDeMedida = cadastroUnidadeDeMedidaService.salvar(this.unidadeDeMedida);
		limpar();

		FacesUtil.addInfoMessage("Unidade de medida salva com sucesso!");
	}

	public boolean isEditando() {
		return this.unidadeDeMedida.getId() != null;
	}

	public UnidadeDeMedidaConverter getUnidadeDeMedidaConverter() {
		return unidadeDeMedidaConverter;
	}

	public void setUnidadeDeMedidaConverter(UnidadeDeMedidaConverter unidadeDeMedidaConverter) {
		this.unidadeDeMedidaConverter = unidadeDeMedidaConverter;
	}

	public UnidadeDeMedida getUnidadeDeMedida() {
		return unidadeDeMedida;
	}

	public void setUnidadeDeMedida(UnidadeDeMedida unidadeDeMedida) {
		this.unidadeDeMedida = unidadeDeMedida;
	}

	public UnidadeDeMedidas getUnidadeDeMedidas() {
		return unidadeDeMedidas;
	}

	public void setUnidadeDeMedidas(UnidadeDeMedidas unidadeDeMedidas) {
		this.unidadeDeMedidas = unidadeDeMedidas;
	}

	
	
	
	
}
