package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Event;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.maddytec.pedidovenda.model.Cliente;
import br.com.maddytec.pedidovenda.model.Endereco;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroEnderecoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Endereco endereco;
	private Endereco enderecoAEditar;
	private Endereco enderecoAExcluir;

	@Inject
	@ClienteEdicao
	private Cliente cliente;

	public CadastroEnderecoBean() {
		this.endereco = new Endereco();
		this.setEnderecoAEditar(new Endereco());
		this.setEnderecoAExcluir(new Endereco());
	}

	@Inject
	private Event<ClienteAlteradoEvent> clienteAlteradoEvent;

	public void adicionar() {
		 if (endereco != null) {
		 this.cliente.getEnderecos().add(endereco);
		 this.endereco.setCliente(cliente);
		
		 endereco = new Endereco();
		 this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));
		}
	}
	

	public void excluir() {
		if (enderecoAExcluir != null) {
			this.cliente.getEnderecos().remove(this.enderecoAExcluir);

			this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));

			FacesUtil.addInfoMessage("Endereço do cliente "
					+ enderecoAExcluir.getLogradouro()
					+ " excluído com sucesso.");

			this.enderecoAExcluir = new Endereco();
		}
	}

	public void alterar() {
		if (enderecoAEditar != null) {
			this.clienteAlteradoEvent.fire(new ClienteAlteradoEvent(cliente));

			FacesUtil.addInfoMessage("Endereço do cliente "
					+ enderecoAEditar.getLogradouro()
					+ " alterado com sucesso.");

			this.enderecoAEditar = new Endereco();
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Endereco> getEnderecosIncluidos() {
		return this.cliente.getEnderecos();
	}

	public Endereco getEnderecoAEditar() {
		return enderecoAEditar;
	}

	public void setEnderecoAEditar(Endereco enderecoAEditar) {
		this.enderecoAEditar = enderecoAEditar;
	}

	public Endereco getEnderecoAExcluir() {
		return enderecoAExcluir;
	}

	public void setEnderecoAExcluir(Endereco enderecoAExcluir) {
		this.enderecoAExcluir = enderecoAExcluir;
	}

}