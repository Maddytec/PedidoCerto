package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.maddytec.pedidovenda.model.Orcamento;
import br.com.maddytec.pedidovenda.model.StatusOrcamento;
import br.com.maddytec.pedidovenda.repository.Orcamentos;
import br.com.maddytec.pedidovenda.repository.filter.OrcamentoFilter;

@Named
@ViewScoped
public class PesquisaOrcamentosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Orcamentos orcamentos;

	private OrcamentoFilter filtro;

	private LazyDataModel<Orcamento> model;

	public PesquisaOrcamentosBean() {
		filtro = new OrcamentoFilter();

		model = new LazyDataModel<Orcamento>() {
			private static final long serialVersionUID = 1L;

			@Override
			public List<Orcamento> load(int first, int pageSize, String sortField,
					SortOrder sortOrder, Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setPropriedadeOrdenacao(sortField);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));

				setRowCount(orcamentos.quantidadeFiltrados(filtro));

				return orcamentos.filtrados(filtro);
			}
		};
	}

	public void posProcessarXls(Object documento) {
		HSSFWorkbook planilha = (HSSFWorkbook) documento;
		HSSFSheet folha = planilha.getSheetAt(0);
		HSSFRow cabecalho = folha.getRow(0);
		HSSFCellStyle estiloCelula = planilha.createCellStyle();
		Font fonteCabecalho = planilha.createFont();

		fonteCabecalho.setColor(IndexedColors.WHITE.getIndex());
		fonteCabecalho.setBold(true);
		fonteCabecalho.setFontHeightInPoints((short) 16);

		estiloCelula.setFont(fonteCabecalho);
		estiloCelula.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		estiloCelula.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < cabecalho.getPhysicalNumberOfCells(); i++) {
			cabecalho.getCell(i).setCellStyle(estiloCelula);
		}
	}

	public StatusOrcamento[] getStatuses() {
		return StatusOrcamento.values();
	}

	public OrcamentoFilter getFiltro() {
		return filtro;
	}

	public Orcamentos getOrcamentos() {
		return orcamentos;
	}

	public void setOrcamentos(Orcamentos orcamentos) {
		this.orcamentos = orcamentos;
	}

	public LazyDataModel<Orcamento> getModel() {
		return model;
	}

	public void setModel(LazyDataModel<Orcamento> model) {
		this.model = model;
	}

	public void setFiltro(OrcamentoFilter filtro) {
		this.filtro = filtro;
	}

}