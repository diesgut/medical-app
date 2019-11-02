package com.diesgut.medical.app.dao;

import java.util.List;

import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;

public interface IMedicalConsultationDao extends EasyDAO<MedicalConsultation> {

	List<MedicalConsultation> allByPatient(Patient patient);

}
