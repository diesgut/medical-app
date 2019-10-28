package com.diesgut.medical.app.dao.imp;

import org.springframework.stereotype.Repository;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.app.dao.ISpecialityDao;
import com.diesgut.medical.model.Speciality;

@Repository
public class SpecialityDaoImp extends AbstractEasyDAO<Speciality> implements ISpecialityDao, EasyDAO<Speciality> {

	public SpecialityDaoImp() {
		super();
		setClazz(Speciality.class);
	}
}
