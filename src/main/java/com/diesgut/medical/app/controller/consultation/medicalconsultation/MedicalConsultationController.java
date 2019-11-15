package com.diesgut.medical.app.controller.consultation.medicalconsultation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.diesgut.medical.app.misc.JsonHelper;
import com.diesgut.medical.model.DetailConsultation;
import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;

@Controller
@RequestMapping("/consultation/medicalconsultations")
public class MedicalConsultationController {

	@Autowired
	iMedicalConsultationService service;

	@GetMapping
	public String getIndex() {
		return "consultation/medicalConsultation/searchMedicalConsultation";
	}

	@GetMapping({ "medicalConsults" })
	public ResponseEntity<List<MedicalConsultation>> doctors() {
		List<MedicalConsultation> list = service.allMedicalConsultations();
		list.forEach(x -> x.getPatient().setMedicalsConsultations(null));
		list.forEach(x -> x.getDoctor().getSpeciality().setDoctors(null));
		list.forEach(x -> x.getDetailConsultations().forEach(y -> y.setMedicalConsultation(null)));
		return new ResponseEntity<List<MedicalConsultation>>(list, HttpStatus.OK);
	}

	@GetMapping("register")
	public String register() {
		return "consultation/medicalConsultation/registerMedicalConsultation";
	}

	@PostMapping("save")
	public ResponseEntity<MedicalConsultation> save(@RequestBody MedicalConsultation medicalConsultation) {
		System.out.println("save");

		if (medicalConsultation.getId() == null) {
			service.save(medicalConsultation);
		} else {
			service.update(medicalConsultation);
		}
		medicalConsultation = service.findMedicalConsultation(medicalConsultation.getId());
		ResponseEntity<MedicalConsultation> response = new ResponseEntity<MedicalConsultation>(medicalConsultation,
				HttpStatus.OK);
		return response;
	}

	@GetMapping("modify/{id}")
	public String modificar(@PathVariable(name = "id") Long idMedicalConsultation, Model model) {
		MedicalConsultation medicalConsultation = service.findMedicalConsultation(idMedicalConsultation);
		String jMedicalConsultation = JsonHelper.toJson(medicalConsultation);
		model.addAttribute("jMedicalConsultation", jMedicalConsultation);
		return "consultation/medicalConsultation/registerMedicalConsultation";
	}

	@GetMapping("consultsByPatient")
	public String consultByPatient() {
		return "consultation/consults/byPatient";
	}

	@GetMapping("medicalConsultationByPatient/{id}")
	public ResponseEntity<List<MedicalConsultation>> medicalConsultationByPatient(
			@PathVariable(name = "id") Long idPatient, Model model) {
		List<MedicalConsultation> list = service.allMedicalConsultationByPatient(new Patient(idPatient));
//		list.forEach(x -> x.getPatient().setMedicalsConsultations(null));
//		list.forEach(x -> x.getDoctor().getSpeciality().setDoctors(null));
		list.forEach(x -> x.getDetailConsultations().forEach(y -> y.setMedicalConsultation(null)));
		return new ResponseEntity<List<MedicalConsultation>>(list, HttpStatus.OK);
	}

}
