package br.com.maddytec.pedidovenda.security;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@Named
@RequestScoped
public class Seguranca {
	
	@Inject
	private ExternalContext externalContext;
	
	public String getNomeUsuario(){
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
		
		if(usuarioLogado != null){
		nome = usuarioLogado.getUsuario().getNome();
		}
	
		return nome;
	}

	
	public boolean isEmitirPedidoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("SOLICITANTES");
	}
	
	public boolean isEmitirOrcamentoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("COMPRADORES");
	}
	
	
	public boolean isCancelarPedidoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("SOLICITANTES");
	}
	
	public boolean isCancelarOrcamentoPermitido(){
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("SOLICITANTES");
	}
	
	
	
	public boolean isPermitidoSalvarFornecedor(){
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("COMPRADORES");
	}
	
	public boolean isPermitidoExcluirFornecedor(){
		return externalContext.isUserInRole("ADMINISTRADORES") 
				|| externalContext.isUserInRole("COMPRADORES");
	}
	
	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
		FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null){
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		
		return usuario;
	}
}
