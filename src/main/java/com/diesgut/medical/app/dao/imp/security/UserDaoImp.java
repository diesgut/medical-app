package com.diesgut.medical.app.dao.imp.security;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.app.dao.imp.AbstractEasyDAO;
import com.diesgut.medical.app.dao.security.IUserDao;
import com.diesgut.medical.model.security.User;

@Repository
public class UserDaoImp extends AbstractEasyDAO<User> implements IUserDao, EasyDAO<User> {

	public UserDaoImp() {
		super();
		setClazz(User.class);
	}

	@Override
	public User findByUsername(String username) {
		String strQuery = "from User u  where u.username=:USERNAME";
		Query query = em.createQuery(strQuery);
		query.setParameter("USERNAME", username);
		return (User) query.getSingleResult();
	}

}
