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

import br.com.maddytec.pedidovenda.model.Fornecedor;
import br.com.maddytec.pedidovenda.repository.filter.FornecedorFilter;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class Fornecedores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Fornecedor porId(Long id) {
		return this.manager.find(Fornecedor.class, id);
	}

	public List<Fornecedor> porNome(String nome){
		
		return this.manager.createQuery("from Fornecedor where upper(nome) like :nome", Fornecedor.class)
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}

	
	public Fornecedor guardar(Fornecedor fornecedor) {
		return manager.merge(fornecedor);
	}

	@Transactional
	public void remover(Fornecedor fornecedor) {
		fornecedor = porId(fornecedor.getId());
		manager.remove(fornecedor);
		manager.flush();
	}

	public Fornecedor porDocumentoReceitaFederal(String documentoReceitaFederal) {
		try {
			return manager
					.createQuery(
							"from Fornecedor where upper(documentoReceitaFederal) = :documentoReceitaFederal",
							Fornecedor.class)
					.setParameter("documentoReceitaFederal",
							documentoReceitaFederal.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Fornecedor porEmail(String email) {
		try {
			return manager
					.createQuery("from Fornecedor where upper(email) = :email",
							Fornecedor.class)
					.setParameter("email", email.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Fornecedor> filtrados(FornecedorFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Fornecedor.class);

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
