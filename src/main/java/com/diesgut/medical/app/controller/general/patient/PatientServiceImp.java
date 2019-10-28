package com.diesgut.medical.app.controller.general.patient;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diesgut.medical.app.dao.IPatientDao;
import com.diesgut.medical.model.Patient;

@Service
@Transactional(readOnly = false)
public class PatientServiceImp implements IPatientService {

	@Autowired
	IPatientDao iPatientDao;

	@Override
	public List<Patient> allPatients() {
		return iPatientDao.all();
	}

	@Override
	public void save(Patient patient) {
		iPatientDao.save(patient);
	}

	@Override
	public void update(Patient patient) {
		iPatientDao.update(patient);
	}

	@Override
	public Patient findPatient(Long idPatient) {
		return iPatientDao.find(idPatient);
	}

}
