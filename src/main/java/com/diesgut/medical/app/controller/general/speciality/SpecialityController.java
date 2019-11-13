package com.diesgut.medical.app.controller.general.speciality;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.diesgut.medical.app.misc.JsonHelper;
import com.diesgut.medical.model.Speciality;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/general/specialities")
public class SpecialityController {

	@Autowired
	ISpecialityService service;

	@GetMapping
	public ModelAndView getIndex() {
		ModelAndView modelAndView = new ModelAndView("/general/speciality/searchspeciality");
		return modelAndView;
	}

	@GetMapping("specialities")
	public ResponseEntity<List<Speciality>> specialities() {
		List<Speciality> list = service.allSpecialities();
		list.forEach(x -> x.setDoctors(null));
		return new ResponseEntity<List<Speciality>>(list, HttpStatus.OK);
	}

	@GetMapping("register")
	public String register() {
		return "/general/speciality/registerspeciality";
	}

	@PostMapping("save")
	public ResponseEntity<Speciality> save(@RequestBody Speciality speciality) {
		System.out.println("save");
		if (speciality.getId() == null) {
			service.save(speciality);
		} else {
			service.update(speciality);
		}
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Speciality> response = new ResponseEntity<Speciality>(speciality, headers, HttpStatus.OK);
		return response;
	}

	@GetMapping("modify/{id}")
	public String modificar(@PathVariable(name = "id") Long idSpeciality, Model model) {
		Speciality speciality = service.findSpeciality(idSpeciality);
		ObjectMapper mapper = new ObjectMapper();
		if (speciality != null) {
			speciality.setDoctors(null);
		}
		String jSpeciality = JsonHelper.toJson(speciality);
		model.addAttribute("jSpeciality", jSpeciality);
		return "/general/speciality/registerspeciality";
	}

	@DeleteMapping("delete")
	public ResponseEntity<Object> delete(@RequestBody Speciality speciality) {
		System.out.println("delete");
		service.deleteSpeciality(speciality);
		return new ResponseEntity<Object>("Registro eliminado", HttpStatus.OK);
	}

}
