package com.diesgut.medical.app.dao.imp;

import org.springframework.stereotype.Repository;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.app.dao.IDoctorDao;
import com.diesgut.medical.model.Doctor;

@Repository
public class DoctorDaoImp extends AbstractEasyDAO<Doctor> implements IDoctorDao, EasyDAO<Doctor> {

	public DoctorDaoImp() {
		super();
		setClazz(Doctor.class);
	}
}
