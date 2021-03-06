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

import br.com.maddytec.pedidovenda.model.Fornecedor;
import br.com.maddytec.pedidovenda.model.FormaPagamento;
import br.com.maddytec.pedidovenda.model.ItemPedido;
import br.com.maddytec.pedidovenda.model.Pedido;
import br.com.maddytec.pedidovenda.model.Produto;
import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.repository.Fornecedores;
import br.com.maddytec.pedidovenda.repository.Produtos;
import br.com.maddytec.pedidovenda.repository.Usuarios;
import br.com.maddytec.pedidovenda.service.CadastroPedidoService;
import br.com.maddytec.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CadastroPedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Usuarios usuarios;

	@Inject
	private Fornecedores fornecedores;

	@Inject
	private Produtos produtos;

	@Inject
	private CadastroPedidoService cadastroPedidoService;

	
	@Produces
	@PedidoEdicao
	private Pedido pedido;

	private String sku;

	private boolean edicao = false;
	private boolean baixa = false;

	private List<Usuario> solicitantes;

	private Produto produtoLinhaEditavel;

	public CadastroPedidoBean() {
		limpar();
	}

	private void limpar() {
		pedido = new Pedido();
	}

	public void pedidoAlterado(@Observes PedidoAlteradoEvent event) {
		this.pedido = event.getPedido();
	}

	public void inicializar() {
				
		if (this.pedido == null) {
			limpar();
		}
		
			this.solicitantes = usuarios.solicitantes();

			this.pedido.adicionarItemVazio();

	}

	public void salvar() {
		this.pedido.removerItemVazio();

		try {
			this.pedido = this.cadastroPedidoService.salvar(this.pedido);

			FacesUtil.addInfoMessage("Pedido " + pedido.getId()
					+ " salvo com sucesso!");
		} finally {
			this.pedido.adicionarItemVazio();
		}
	}

	public void carregarProdutoPorSku() {
		if (StringUtils.isNotEmpty(this.sku)) {
			produtoLinhaEditavel = this.produtos.porSku(sku);
			this.carregarProdutoLinhaEditavel();
		}
	}

	public void carregarProdutoLinhaEditavel() {
		ItemPedido item = this.pedido.getItens().get(0);

		if (produtoLinhaEditavel != null) {
			if (this.existeItemComProduto(produtoLinhaEditavel)) {
				FacesUtil
						.addErrorMessage("Já exite um item no pedido com o produto informado.");
			} else {
				item.setProduto(this.produtoLinhaEditavel);
				

				this.pedido.adicionarItemVazio();
				this.produtoLinhaEditavel = null;
				this.sku = null;

			}
		}
	}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;

		for (ItemPedido item : this.getPedido().getItens()) {
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

	public void atualizarQuantidade(ItemPedido item, int linha) {
		if (item.getQuantidade() < 1) {
			if (linha == 0) {
				item.setQuantidade(1);
			} else {
				this.getPedido().getItens().remove(linha);
			}

		}

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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
