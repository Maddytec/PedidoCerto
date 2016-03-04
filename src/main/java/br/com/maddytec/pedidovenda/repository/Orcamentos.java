package br.com.maddytec.pedidovenda.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

import br.com.maddytec.pedidovenda.model.Orcamento;
import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.model.vo.DataValor;
import br.com.maddytec.pedidovenda.repository.filter.OrcamentoFilter;

public class Orcamentos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@SuppressWarnings({ "unchecked" })
	public Map<Date, BigDecimal> valoresTotaisPorData(Integer numeroDeDias,
			Usuario criadoPor) {
		Session session = manager.unwrap(Session.class);

		numeroDeDias -= 1;

		Calendar dataInicial = Calendar.getInstance();
		dataInicial = DateUtils.truncate(dataInicial, Calendar.DAY_OF_MONTH);
		dataInicial.add(Calendar.DAY_OF_MONTH, numeroDeDias * -1);

		Map<Date, BigDecimal> resultado = criarMapaVazio(numeroDeDias,
				dataInicial);

		Criteria criteria = session.createCriteria(Orcamento.class);

		// select date(data_criacao) as data, sum(valor_total) as valor
		// from orcamento where data_criacao >= :dataInicial and vendedor_id = :
		// criadorPor
		// group by date(data_cricao)

		criteria.setProjection(
				Projections
						.projectionList()
						.add(Projections.sqlGroupProjection(
								"date(data_criacao) as data",
								"date(data_criacao)", new String[] { "data" },
								new Type[] { StandardBasicTypes.DATE }))
						.add(Projections.sum("valorTotal").as("valor"))).add(
				Restrictions.ge("dataCriacao", dataInicial.getTime()));

		if (criadoPor != null) {
			criteria.add(Restrictions.eq("solicitante", criadoPor));
		}

		List<DataValor> valoresPorData = criteria.setResultTransformer(
				Transformers.aliasToBean(DataValor.class)).list();

		for (DataValor dataValor : valoresPorData) {
			resultado.put(dataValor.getData(), dataValor.getValor());
		}

		return resultado;
	}

	private Map<Date, BigDecimal> criarMapaVazio(Integer numeroDeDias,
			Calendar dataInicial) {
		dataInicial = (Calendar) dataInicial.clone();

		Map<Date, BigDecimal> mapaInicial = new TreeMap<>();

		for (int i = 0; i <= numeroDeDias; i++) {
			mapaInicial.put(dataInicial.getTime(), BigDecimal.ZERO);
			dataInicial.add(Calendar.DAY_OF_MONTH, 1);
		}

		return mapaInicial;
	}

	private Criteria criarCriteriaParaFiltro(OrcamentoFilter filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Orcamento.class)
				.createAlias("fornecedor", "f")
				.createAlias("solicitante", "s");

		if (filtro.getNumeroDe() != null) {
			// id deve ser maior ou igual (ge = greater or equal ) a
			// filtro.numeroDe
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}

		if (filtro.getNumeroAte() != null) {
			// id deve ser menor ou igual (le = lower or equal ) a
			// filtro.numeroAte
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}

		if (filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao",
					filtro.getDataCriacaoDe()));
		}

		if (filtro.getDataCriacaoAte() != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(filtro.getDataCriacaoAte());
			calendar.set(Calendar.MILLISECOND, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.HOUR, 23);

			criteria.add(Restrictions.le("dataCriacao", calendar.getTime()));
		}

		if (StringUtils.isNotBlank(filtro.getNomeFornecedor())) {
			// acessamos o nome do fornecedor associado ao orcamento pelo alias "f"
			criteria.add(Restrictions.ilike("f.nome", filtro.getNomeFornecedor(),
					MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getNomeSolicitante())) {
			// acessamos o nome do solicitante associado ao orcamento pelo alias "s"
			criteria.add(Restrictions.ilike("s.nome", filtro.getNomeSolicitante(),
					MatchMode.ANYWHERE));
		}

		if (filtro.getStatuses() != null && filtro.getStatuses().length > 0) {
			// adicionamos a restrição "in", passando array de constantes da
			// enum StatusOrcamento
			criteria.add(Restrictions.in("status", filtro.getStatuses()));
		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Orcamento> filtrados(OrcamentoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	public int quantidadeFiltrados(OrcamentoFilter filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	public Orcamento guardar(Orcamento orcamento) {
		return this.manager.merge(orcamento);
	}

	public Orcamento porId(Long id) {
		return this.manager.find(Orcamento.class, id);
	}

}
