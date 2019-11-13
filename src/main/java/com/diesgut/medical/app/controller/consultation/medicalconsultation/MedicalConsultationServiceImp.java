package com.diesgut.medical.app.controller.consultation.medicalconsultation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diesgut.medical.app.dao.IDetailConsultationDao;
import com.diesgut.medical.app.dao.IMedicalConsultationDao;
import com.diesgut.medical.model.DetailConsultation;
import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;

@Service
@Transactional(readOnly = true)
public class MedicalConsultationServiceImp implements iMedicalConsultationService {

	@Autowired
	IMedicalConsultationDao iMedicalConsultationDao;

	@Autowired
	IDetailConsultationDao iDetailConsultationDao;

	@Override
	@Transactional
	public void save(MedicalConsultation medicalConsultation) {
		medicalConsultation.getPatient().setMedicalsConsultations(null);
		for (DetailConsultation det : medicalConsultation.getDetailConsultations()) {
			det.setMedicalConsultation(medicalConsultation);
		}

		medicalConsultation.setCreateDate(LocalDateTime.now());
		iMedicalConsultationDao.save(medicalConsultation);
	}

	@Override
	@Transactional
	public void update(MedicalConsultation medicalConsultation) {
		List<DetailConsultation> detalleForm = medicalConsultation.getDetailConsultations();
		for (DetailConsultation det : medicalConsultation.getDetailConsultations()) {
			det.setMedicalConsultation(medicalConsultation);
		}
		List<DetailConsultation> detalleDB = iDetailConsultationDao.allByMedicalConsultation(medicalConsultation);
		for (DetailConsultation det : detalleDB) {
			if (!detalleForm.contains(det)) {
				iDetailConsultationDao.delete(det.getId());
			}
		}
		iMedicalConsultationDao.update(medicalConsultation);
	}

	@Override
	public List<MedicalConsultation> allMedicalConsultations() {
		return iMedicalConsultationDao.all();
	}

	@Override
	public MedicalConsultation findMedicalConsultation(Long idMedicalConsultation) {
		MedicalConsultation medicalConsultation = iMedicalConsultationDao.find(idMedicalConsultation);
		medicalConsultation.getDoctor().setSpeciality(null);
		medicalConsultation.getPatient().setMedicalsConsultations(null);
		medicalConsultation.getDetailConsultations().forEach(x -> x.setMedicalConsultation(null));
		return medicalConsultation;
	}

	@Override
	public List<MedicalConsultation> allMedicalConsultationByPatient(Patient patient) {
		return iMedicalConsultationDao.allByPatient(patient);
	}

}
