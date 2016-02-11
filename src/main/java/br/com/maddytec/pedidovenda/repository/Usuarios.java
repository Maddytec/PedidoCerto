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

import br.com.maddytec.pedidovenda.model.Usuario;
import br.com.maddytec.pedidovenda.repository.filter.UsuarioFilter;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class Usuarios implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Usuario porId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public List<Usuario> vendedores() {
		// TODO filtrar apenas vendedores (por um grupo específico)
		return manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
	}

	public Usuario guardar(Usuario usuario) {
		return manager.merge(usuario);
	}

	@Transactional
	public void remover(Usuario usuario) {
		usuario = porId(usuario.getId());
		manager.remove(usuario);
		manager.flush();
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		try {
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			//nenhum usuario encontrado
		}
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getEmail())) {
			criteria.add(Restrictions.ilike("email", filtro.getEmail(),
					MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("nome")).list();
	}

}
