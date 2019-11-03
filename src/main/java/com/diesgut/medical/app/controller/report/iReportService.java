package com.diesgut.medical.app.controller.report;

import java.util.List;

import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.Patient;
import com.diesgut.medical.model.Speciality;

public interface iReportService {

	List<Doctor> allDoctorsBySpeciality(Speciality speciality);

	Patient findPatient(Patient patient);
}
