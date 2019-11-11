package com.diesgut.medical.app.dao.security;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.model.security.User;

public interface IUserDao extends EasyDAO<User> {

	User findByUsername(String username);

}
