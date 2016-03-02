package br.com.maddytec.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.maddytec.pedidovenda.model.UnidadeDeMedida;
import br.com.maddytec.pedidovenda.repository.filter.UnidadeDeMedidaFilter;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class UnidadeDeMedidas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public UnidadeDeMedida guardar(UnidadeDeMedida unidadeDeMedida) {
		return manager.merge(unidadeDeMedida);
	}

	@Transactional
	public void remover(UnidadeDeMedida unidadeDeMedida) {
		unidadeDeMedida = porId(unidadeDeMedida.getId());
		manager.remove(unidadeDeMedida);
		manager.flush();
	}

	public UnidadeDeMedida porDescricao(String descricao) {
		try {
			return manager
					.createQuery("from UnidadeDeMedida where upper(descricao) = :descricao",
							UnidadeDeMedida.class)
					.setParameter("descricao", descricao.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public UnidadeDeMedida porId(Long id) {
		return manager.find(UnidadeDeMedida.class, id);
	}

	public List<UnidadeDeMedida> lista() {
		return manager.createQuery("from UnidadeDeMedida", UnidadeDeMedida.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<UnidadeDeMedida> filtrados(UnidadeDeMedidaFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(UnidadeDeMedida.class);

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getUnidadeDeMedida())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getUnidadeDeMedida(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("descricao")).list();
	}

}
