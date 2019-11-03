package com.diesgut.medical.app.controller.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.diesgut.medical.app.controller.report.pdf.MedicalHistoryByPatientPDF;
import com.diesgut.medical.app.controller.report.pdf.MedicosPorEspecialidadPDF;
import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.Speciality;

@Controller
@RequestMapping("/reports")
public class ReportController {

	@Autowired
	iReportService service;

	@Autowired
	MedicosPorEspecialidadPDF medicosPorEspecialidadPDF;

	@Autowired
	MedicalHistoryByPatientPDF medicalHistoryByPatientPDF;

	@GetMapping("doctor/byspeciality")
	public String patientBySpeciality() {
		return "/report/doctor/bySpeciality";
	}

	@GetMapping("patient/clinicalhistory")
	public String clinicalhistory() {
		return "/report/patient/clinicalHistory";
	}

	@GetMapping("doctor/byspeciality/{id}")
	public ResponseEntity<List<Doctor>> doctorBySpeciality(@PathVariable(name = "id") Long idDpeciality, Model model) {
		List<Doctor> list = service.allDoctorsBySpeciality(new Speciality(idDpeciality));
		return new ResponseEntity<List<Doctor>>(list, HttpStatus.OK);
	}

	@GetMapping("doctor/byspecialitypdf/{id}")
	public ModelAndView doctorBySpecialityPdf(@PathVariable(name = "id") Long idSpeciality, Model model) {
		model.addAttribute("idSpeciality", idSpeciality);
		return new ModelAndView(medicosPorEspecialidadPDF);
	}

	@GetMapping("patient/clinicalhistory/{id}")
	public ModelAndView clinicalhistoryPdf(@PathVariable(name = "id") Long idPatient, Model model) {
		model.addAttribute("idPatient", idPatient);
		return new ModelAndView(medicalHistoryByPatientPDF);
	}

}
