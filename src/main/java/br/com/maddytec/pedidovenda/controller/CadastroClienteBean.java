package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Cliente;
import br.com.maddytec.pedidovenda.model.TipoPessoa;
import br.com.maddytec.pedidovenda.service.CadastroClienteService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CadastroClienteService cadastroClienteService;

	@Produces
	@ClienteEdicao
	private Cliente cliente;

	public CadastroClienteBean() {
		limpar();
	}

	public void inicializar() {
		System.out.println("Inicializando...");
		
		if (this.cliente == null) {
			limpar();
		}
		
	}

	public void clienteAlterado(@Observes ClienteAlteradoEvent event) {
		this.cliente = event.getCliente();
	}

	private void limpar() {
		cliente = new Cliente();
	}

	public void salvar() {
		this.cliente = cadastroClienteService.salvar(this.cliente);
		
		limpar();

		FacesUtil.addInfoMessage("Cliente salvo com sucesso!");
	}

	public boolean isEditando() {
		return this.cliente.getId() != null;
	}

	public TipoPessoa[] getTipoPessoa() {
		return TipoPessoa.values();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
