package com.diesgut.medical.app.controller.general.patient;

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
import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.Patient;

@Controller
@RequestMapping("/general/patients")
public class PatientController {

	@Autowired
	IPatientService service;

	@GetMapping
	public ModelAndView getIndex() {
		ModelAndView modelAndView = new ModelAndView("/general/patient/searchPatient");
		return modelAndView;
	}

	@GetMapping("patients")
	public ResponseEntity<List<Patient>> patients() {
		List<Patient> list = service.allPatients();
		list.forEach(x -> x.setMedicalsConsultations(null));
		return new ResponseEntity<List<Patient>>(list, HttpStatus.OK);
	}

	@GetMapping("register")
	public String register() {
		return "/general/patient/registerPatient";
	}

	@PostMapping("save")
	public ResponseEntity<Patient> save(@RequestBody Patient patient) {
		System.out.println("save");
		if (patient.getId() == null) {
			service.save(patient);
		} else {
			service.update(patient);
		}
		ResponseEntity<Patient> response = new ResponseEntity<Patient>(patient, HttpStatus.OK);
		return response;
	}

	@GetMapping("modify/{id}")
	public String modificar(@PathVariable(name = "id") Long idPatient, Model model) {
		Patient patient = service.findPatient(idPatient);
		if (patient != null) {
			patient.setMedicalsConsultations(null);
		}
		String jPatient = JsonHelper.toJson(patient);
		model.addAttribute("jPatient", jPatient);
		return "/general/patient/registerPatient";
	}

}
