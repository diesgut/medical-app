package com.diesgut.medical.app.dao;

import java.util.List;

import com.diesgut.medical.model.DetailConsultation;
import com.diesgut.medical.model.MedicalConsultation;

public interface IDetailConsultationDao extends EasyDAO<DetailConsultation> {

	List<DetailConsultation> allByMedicalConsultation(MedicalConsultation medicalConsultation);

}

