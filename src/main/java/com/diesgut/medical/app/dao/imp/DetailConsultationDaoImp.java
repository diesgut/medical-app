package com.diesgut.medical.app.dao.imp;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.diesgut.medical.app.dao.EasyDAO;
import com.diesgut.medical.app.dao.IDetailConsultationDao;
import com.diesgut.medical.model.DetailConsultation;
import com.diesgut.medical.model.MedicalConsultation;

@Repository
public class DetailConsultationDaoImp extends AbstractEasyDao<DetailConsultation>
		implements IDetailConsultationDao, EasyDAO<DetailConsultation> {

	public DetailConsultationDaoImp() {
		super();
		setClazz(DetailConsultation.class);
	}

	@Override
	public List<DetailConsultation> allByMedicalConsultation(MedicalConsultation medicalConsultation) {
		String strQuery = "from DetailConsultation dc join fetch dc.medicalConsultation mc where mc.id=:MED_CONS";
		Query query = em.createQuery(strQuery);
		query.setParameter("MED_CONS", medicalConsultation.getId());
		return (List<DetailConsultation>) query.getResultList();
	}
}