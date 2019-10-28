package com.diesgut.medical.app.dao.imp;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.diesgut.medical.app.dao.EasyDAO;
import com.google.common.base.Preconditions;

public class AbstractEasyDAO<T extends Serializable> implements EasyDAO<T> {
	private Class<T> clazz;
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityManager em;

//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}

	protected void setClazz(final Class<T> clazzToSet) {
		clazz = Preconditions.checkNotNull(clazzToSet);
	}

	@Override
	public T find(final long id) {
		return em.find(clazz, id);
	}

	@Override
	public List<T> all() {
		String strQuery = "from " + clazz.getName();
		Query query = em.createQuery(strQuery);
		return (List<T>) query.getResultList();
	}

	@Override
	public void save(final T entity) {
		Preconditions.checkNotNull(entity);
		em.persist(entity);
	}

	@Override
	public void update(final T entity) {
		Preconditions.checkNotNull(entity);
		em.merge(entity);
	}

	@Override
	public void delete(final T entity) {
		Preconditions.checkNotNull(entity);
		em.remove(entity);
	}

	@Override
	public void delete(final long entityId) {
		final T entity = find(entityId);
		Preconditions.checkState(entity != null);
		delete(entity);
	}

//	protected Session getCurrentSession() {
//		return sessionFactory.getCurrentSession();
//	}
}
