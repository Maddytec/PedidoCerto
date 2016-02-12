package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Fornecedor;
import br.com.maddytec.pedidovenda.model.TipoPessoa;
import br.com.maddytec.pedidovenda.service.CadastroFornecedorService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroFornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroFornecedorService cadastroFornecedorService;

	@Produces
	@FornecedorEdicao
	private Fornecedor fornecedor;

	public CadastroFornecedorBean() {
		limpar();
	}

	public void inicializar() {
		System.out.println("Inicializando...");
		
		if (this.fornecedor == null) {
			limpar();
		}
		
	}

	public void fornecedorAlterado(@Observes FornecedorAlteradoEvent event) {
		this.fornecedor = event.getFornecedor();
	}

	private void limpar() {
		fornecedor = new Fornecedor();
	}

	public void salvar() {
		this.fornecedor = cadastroFornecedorService.salvar(this.fornecedor);
		
		limpar();

		FacesUtil.addInfoMessage("Fornecedor salvo com sucesso!");
	}

	public boolean isEditando() {
		return this.fornecedor.getId() != null;
	}

	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
}
