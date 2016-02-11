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

import br.com.maddytec.pedidovenda.model.Cliente;
import br.com.maddytec.pedidovenda.repository.filter.ClienteFilter;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class Clientes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Cliente porId(Long id) {
		return this.manager.find(Cliente.class, id);
	}

	public List<Cliente> porNome(String nome){
		
		return this.manager.createQuery("from Cliente where upper(nome) like :nome", Cliente.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}

	
	public Cliente guardar(Cliente cliente) {
		return manager.merge(cliente);
	}

	@Transactional
	public void remover(Cliente cliente) {
		cliente = porId(cliente.getId());
		manager.remove(cliente);
		manager.flush();
	}

	public Cliente porDocumentoReceitaFederal(String documentoReceitaFederal) {
		try {
			return manager
					.createQuery(
							"from Cliente where upper(documentoReceitaFederal) = :documentoReceitaFederal",
							Cliente.class)
					.setParameter("documentoReceitaFederal",
							documentoReceitaFederal.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Cliente porEmail(String email) {
		try {
			return manager
					.createQuery("from Cliente where upper(email) = :email",
							Cliente.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> filtrados(ClienteFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Cliente.class);

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(),
					MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(filtro.getDocumentoReceitaFederal())) {
			criteria.add(Restrictions.eq("documentoReceitaFederal",
					filtro.getDocumentoReceitaFederal()));
		}

		return criteria.addOrder(Order.asc("nome")).list();
	}

}
