package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import org.hibernate.Session;

import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;
import br.com.maddytec.pedidovenda.util.report.ExecutorRelatorio;

@Named
@RequestScoped
public class RelatorioPedidosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date dataInicio = null;
	private Date dataFim = null;
	private Long pedidoId = null;
	private String nomeCliente = null;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	private EntityManager manager;

	public void emitir(String caminhoDoRelatorio, String nomeDoRelatorio) throws ParseException {

		Date date = new Date();
		DateFormat df = new SimpleDateFormat("dd/MM/YYYY_HH:mm:ss");
		String dataFormatada = df.format(date);
				
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("data_inicio", this.dataInicio);
		parametros.put("data_fim", this.dataFim);
		parametros.put("pedido_id", this.pedidoId);
		parametros.put("cliente", this.nomeCliente);

		ExecutorRelatorio executor = new ExecutorRelatorio(caminhoDoRelatorio,
				this.response, parametros, nomeDoRelatorio+"_"+dataFormatada+".pdf");

		Session session = manager.unwrap(Session.class);
		session.doWork(executor);

		if (executor.isRelatorioGerado()) {
			facesContext.responseComplete();
		} else {
			FacesUtil
					.addErrorMessage("A execução do relatório não retornou dados.");
		}
	}

	@NotNull
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@NotNull
	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	@NotNull
	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	@NotNull
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

}