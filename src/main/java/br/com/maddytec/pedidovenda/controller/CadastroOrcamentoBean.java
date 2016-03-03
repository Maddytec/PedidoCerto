package br.com.maddytec.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.event.SelectEvent;

import br.com.maddytec.orcamentovenda.model.Fornecedor;
import br.com.maddytec.orcamentovenda.model.FormaPagamento;
import br.com.maddytec.orcamentovenda.model.ItemOrcamento;
import br.com.maddytec.orcamentovenda.model.Orcamento;
import br.com.maddytec.orcamentovenda.model.Produto;
import br.com.maddytec.orcamentovenda.model.Usuario;
import br.com.maddytec.orcamentovenda.repository.Fornecedores;
import br.com.maddytec.orcamentovenda.repository.Produtos;
import br.com.maddytec.orcamentovenda.repository.Usuarios;
import br.com.maddytec.orcamentovenda.service.CadastroOrcamentoService;
import br.com.maddytec.orcamentovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroOrcamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Inject
	private Fornecedores fornecedores;

	@Inject
	private Produtos produtos;

	@Inject
	private CadastroOrcamentoService cadastroOrcamentoService;

	
	@Produces
	@OrcamentoEdicao
	private Orcamento orcamento;

	private String sku;

	private boolean edicao = false;
	private boolean baixa = false;

	private List<Usuario> solicitantes;

	private Produto produtoLinhaEditavel;

	public CadastroOrcamentoBean() {
		limpar();
	}

	public void fornecedorSelecionado(SelectEvent event) {
		orcamento.setFornecedor((Fornecedor) event.getObject());
	}

	private void limpar() {
		orcamento = new Orcamento();
	}

	public void orcamentoAlterado(@Observes OrcamentoAlteradoEvent event) {
		this.orcamento = event.getOrcamento();
	}

	public void inicializar() {
				
		if (this.orcamento == null) {
			limpar();
		}
		
			this.solicitantes = usuarios.solicitantes();

			this.orcamento.adicionarItemVazio();

			this.recalcularOrcamento();
		
	}

	public void salvar() {
		this.orcamento.removerItemVazio();

		try {
			this.orcamento = this.cadastroOrcamentoService.salvar(this.orcamento);

			FacesUtil.addInfoMessage("Orcamento " + orcamento.getId()
					+ " salvo com sucesso!");
		} finally {
			this.orcamento.adicionarItemVazio();
		}
	}

	public void recalcularOrcamento() {
		if (orcamento != null) {
			this.orcamento.recalcularValorTotal();
		}
	}

	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			produtoLinhaEditavel = this.produtos.porSku(sku);
			this.carregarProdutoLinhaEditavel();
		}
	}

	public void carregarProdutoLinhaEditavel() {
		ItemOrcamento item = this.orcamento.getItens().get(0);

		if (produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(produtoLinhaEditavel)) {
				FacesUtil
						.addErrorMessage("Já exite um item no orcamento com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				

				this.orcamento.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

				this.recalcularOrcamento();
			}
		}
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemOrcamento item : this.getOrcamento().getItens()) {
			if (produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}

		return existeItem;
	}

	public List<Produto> completarProduto(String nome) {
		return this.produtos.porNome(nome);
	}

	public void atualizarQuantidade(ItemOrcamento item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getOrcamento().getItens().remove(linha);
			}

		}

		this.orcamento.recalcularValorTotal();
	}

	public FormaPagamento[] getFormaPagamento() {
		return FormaPagamento.values();
	}

	public List<Fornecedor> completarFornecedor(String nome) {
		return this.fornecedores.porNome(nome);
	}

	public void isEditando() {
		this.edicao = true;
	}

	public void isNaoEditando() {
		this.edicao = false;
	}

	public void isBaixando() {
		baixa = true;
	}

	public void isNaoBaixando() {
		baixa = false;
	}

	public void isEdicao() {
		edicao = true;
	}

	public void isNaoEdicao() {
		edicao = false;
	}

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public List<Usuario> getSolicitantes() {
		return solicitantes;
	}

	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}

	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}

	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public boolean getEdicao() {
		return edicao;
	}

	public void setEdicao(boolean edicao) {
		this.edicao = edicao;
	}

	public boolean isBaixa() {
		return baixa;
	}

	@NotBlank
	public String getNomeFornecedor() {
		return orcamento.getFornecedor() == null ? null : orcamento.getFornecedor()
				.getNome();
	}
	
	public void setNomeFornecedor(String nomeFornecedor){
	//corrige erro do readonly	
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

}
