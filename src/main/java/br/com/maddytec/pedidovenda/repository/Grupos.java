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

import br.com.maddytec.pedidovenda.model.Grupo;
import br.com.maddytec.pedidovenda.repository.filter.GrupoFilter;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class Grupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Grupo guardar(Grupo grupo) {
		return manager.merge(grupo);
	}

	@Transactional
	public void remover(Grupo grupo) {
		grupo = porId(grupo.getId());
		manager.remove(grupo);
		manager.flush();
	}

	public Grupo porNome(String nome) {
		try {
			return manager
					.createQuery("from Grupo where upper(nome) = :nome",
							Grupo.class)
					.setParameter("nome", nome.toUpperCase()).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

	public List<Grupo> lista() {
		return manager.createQuery("from Grupo", Grupo.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> filtrados(GrupoFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Grupo.class);

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getDescricao())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getDescricao(),
					MatchMode.ANYWHERE));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

}
