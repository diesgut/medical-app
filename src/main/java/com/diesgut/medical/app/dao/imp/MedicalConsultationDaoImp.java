package com.diesgut.medical.app.dao.imp;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.app.dao.IMedicalConsultationDao;
import com.diesgut.medical.model.DetailConsultation;
import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;

@Repository
public class MedicalConsultationDaoImp extends AbstractEasyDAO<MedicalConsultation>
		implements IMedicalConsultationDao, EasyDAO<MedicalConsultation> {

	public MedicalConsultationDaoImp() {
		super();
		setClazz(MedicalConsultation.class);
	}

	@Override
	public List<MedicalConsultation> allByPatient(Patient patient) {
		String strQuery = "from MedicalConsultation mc  where mc.patient.id=:PATIENT";
		Query query = em.createQuery(strQuery);
		query.setParameter("PATIENT", patient.getId());
		return (List<MedicalConsultation>) query.getResultList();
	}
}
