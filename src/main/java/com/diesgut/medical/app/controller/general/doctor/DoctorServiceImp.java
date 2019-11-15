package com.diesgut.medical.app.controller.general.doctor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diesgut.medical.app.dao.IDoctorDao;
import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.Speciality;

@Service
@Transactional(readOnly = true)
public class DoctorServiceImp implements IDoctorService {

	@Autowired
	IDoctorDao iDoctorDao;

	@Override
	@Transactional
	public void save(Doctor doctor) {
		iDoctorDao.save(doctor);
	}

	@Override
	@Transactional
	public void update(Doctor doctor) {
		iDoctorDao.update(doctor);
	}

	@Override
	@Transactional
	public void delete(Doctor doctor) {
		iDoctorDao.delete(doctor.getId());
	}

	@Override
	public List<Doctor> allDoctors() {
		return iDoctorDao.all();
	}

	@Override
	public Doctor findDoctor(Long idDoctor) {
		return iDoctorDao.find(idDoctor);
	}

	
}
