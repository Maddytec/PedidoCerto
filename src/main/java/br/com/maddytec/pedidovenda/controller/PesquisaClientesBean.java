package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.com.maddytec.pedidovenda.model.Cliente;
import br.com.maddytec.pedidovenda.repository.Clientes;
import br.com.maddytec.pedidovenda.repository.filter.ClienteFilter;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class PesquisaClientesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Clientes clientes;

	private ClienteFilter filtro;

	private List<Cliente> clientesFiltrados;

	private Cliente clienteSelecionado;

	public PesquisaClientesBean() {
		filtro = new ClienteFilter();
	}

	public void pesquisar() {
		clientesFiltrados = clientes.filtrados(filtro);
	}

	public void excluir() {
		if (clienteSelecionado != null) {
			try {
				clientes.remover(clienteSelecionado);
				clientesFiltrados.remove(clienteSelecionado);

				FacesUtil.addInfoMessage("Cliente "
						+ clienteSelecionado.getNome()
						+ " excluído com sucesso.");
			} catch (PersistenceException e) {
				FacesUtil.addErrorMessage("O cliente "
						+ clienteSelecionado.getNome()
						+ " não pode ser excluído.");
			}
		}

	}

	public List<Cliente> getClientesFiltrados() {
		return clientesFiltrados;
	}

	public ClienteFilter getFiltro() {
		return filtro;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}