package com.diesgut.medical.app.controller.general.doctor;

import java.util.List;

import com.diesgut.medical.model.Doctor;

interface IDoctorService {

	void save(Doctor doctor);

	void update(Doctor doctor);

	void delete(Doctor doctor);

	List<Doctor> allDoctors();

	Doctor findDoctor(Long idDoctor);

}
