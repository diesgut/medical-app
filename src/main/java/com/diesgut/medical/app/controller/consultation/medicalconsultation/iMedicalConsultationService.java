package com.diesgut.medical.app.controller.consultation.medicalconsultation;

import java.util.List;

import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;

public interface iMedicalConsultationService {

	void save(MedicalConsultation medicalConsultation);

	void update(MedicalConsultation medicalConsultation);

	List<MedicalConsultation> allMedicalConsultations();

	MedicalConsultation findMedicalConsultation(Long idMedicalConsultation);

	List<MedicalConsultation> allMedicalConsultationByPatient(Patient patient);
}
