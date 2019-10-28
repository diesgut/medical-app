package com.diesgut.medical.app.controller.general.doctor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.diesgut.medical.model.Speciality;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/general/doctors")
public class DoctorController {

	@Autowired
	IDoctorService service;

	@GetMapping
	public ModelAndView getIndex() {
		ModelAndView modelAndView = new ModelAndView("/general/doctor/searchDoctor");
		return modelAndView;
	}

	@GetMapping("doctors")
	public ResponseEntity<List<Doctor>> specialities() {
		List<Doctor> list = service.allDoctors();
		return new ResponseEntity<List<Doctor>>(list, HttpStatus.OK);
	}

	@GetMapping("register")
	public String register() {
		return "/general/doctor/registerDoctor";
	}

	@PostMapping("save")
	public ResponseEntity<Doctor> save(@RequestBody Doctor doctor) {
		System.out.println("save");
		if (doctor.getId() == null) {
			service.save(doctor);
		} else {
			service.update(doctor);
		}
		ResponseEntity<Doctor> response = new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
		return response;
	}

	@GetMapping("modify/{id}")
	public String modificar(@PathVariable(name = "id") Long idDoctor, Model model) {
		Doctor doctor = service.findDoctor(idDoctor);
//		if (doctor != null) {
//			speciality.setDoctors(null);
//		}
		String jDoctor = JsonHelper.toJson(doctor);
		model.addAttribute("jDoctor", jDoctor);
		return "/general/doctor/registerDoctor";
	}

}
