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

import br.com.maddytec.pedidovenda.model.Categoria;
import br.com.maddytec.pedidovenda.repository.filter.CategoriaFilter;
import br.com.maddytec.pedidovenda.util.jpa.Transactional;

public class Categorias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Categoria guardar(Categoria categoria) {
		return manager.merge(categoria);
	}

	@Transactional
	public void remover(Categoria categoria) {
		categoria = porId(categoria.getId());
		manager.remove(categoria);
		manager.flush();
	}

	public Categoria porId(Long id) {
		return manager.find(Categoria.class, id);
	}

	public List<Categoria> lista() {
		return manager.createQuery("from Categoria", Categoria.class)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> filtrados(CategoriaFilter filtro) {

		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Categoria.class);

		
		System.out.println("Subcategoria: " + filtro.getSubcategoria());
		System.out.println("Categoria: " + filtro.getCategoria());
		
		
		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getSubcategoria())) {
			criteria.add(Restrictions.ilike("descricao",
					filtro.getSubcategoria(), MatchMode.ANYWHERE));
			// Somente as subcategorias
			criteria.add(Restrictions.isNotNull("categoriaPai"));
		}
		
		if(filtro.getSubcategoria() == null && filtro.getCategoria().equals("") ){
			criteria.add(Restrictions.isNull("categoriaPai"));
		}
		
		if(filtro.getCategoria() == null && filtro.getSubcategoria().equals("") ){
			criteria.add(Restrictions.isNotNull("categoriaPai"));
		}
		
		

		// MatchMode.ANYWHERE determina o like '%%'
		if (StringUtils.isNotBlank(filtro.getCategoria())) {
			criteria.add(Restrictions.ilike("descricao", filtro.getCategoria(),
					MatchMode.ANYWHERE));
			// Somente as subcategorias
			criteria.add(Restrictions.isNull("categoriaPai"));
		}

		return criteria.addOrder(Order.asc("descricao")).list();
	}

	public List<Categoria> raizes() {
		return manager.createQuery("from Categoria where categoriaPai is null",
				Categoria.class).getResultList();
	}

	public List<Categoria> subcategoriasDe(Categoria categoriaPai) {
		return manager
				.createQuery("from Categoria where categoriaPai = :raiz",
						Categoria.class).setParameter("raiz", categoriaPai)
				.getResultList();
	}

	public Categoria porDescricao(String descricao) {
		try {
			return manager
					.createQuery(
							"from Categoria where upper(descricao) = :descricao",
							Categoria.class)
					.setParameter("descricao", descricao.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}