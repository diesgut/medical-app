package com.diesgut.medical.app.dao.imp;

import org.springframework.stereotype.Repository;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.app.dao.IPatientDao;
import com.diesgut.medical.model.Patient;

@Repository
public class PatientDaoImp extends AbstractEasyDAO<Patient> implements IPatientDao, EasyDAO<Patient> {

	public PatientDaoImp() {
		super();
		setClazz(Patient.class);
	}
}
