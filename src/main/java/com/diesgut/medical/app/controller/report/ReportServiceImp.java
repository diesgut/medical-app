package com.diesgut.medical.app.controller.report;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diesgut.medical.app.controller.consultation.medicalconsultation.iMedicalConsultationService;
import com.diesgut.medical.app.dao.IDoctorDao;
import com.diesgut.medical.app.dao.IMedicalConsultationDao;
import com.diesgut.medical.app.dao.IPatientDao;
import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;
import com.diesgut.medical.model.Speciality;

@Service
@Transactional(readOnly = true)
public class ReportServiceImp implements iReportService {

	@Autowired
	IDoctorDao iDoctorDao;

	@Autowired
	IPatientDao iPatientDao;

	@Autowired
	IMedicalConsultationDao iMedicalConsultationService;

	@Override
	public List<Doctor> allDoctorsBySpeciality(Speciality speciality) {
		List<Doctor> list = iDoctorDao.allBySpeciality(speciality);
		return list;
	}

	@Override
	public Patient findPatient(Patient patient) {
		patient = iPatientDao.find(patient.getId());
		List<MedicalConsultation> medicalsConsultations = iMedicalConsultationService.allByPatient(patient);
		Collections.sort(medicalsConsultations, (x1, x2) -> x1.getCreateDate().compareTo(x2.getCreateDate()));
		patient.setMedicalsConsultations(medicalsConsultations);
		return patient;
	}

}
