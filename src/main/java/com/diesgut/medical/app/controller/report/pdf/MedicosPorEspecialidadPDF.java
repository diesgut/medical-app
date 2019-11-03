package com.diesgut.medical.app.controller.report.pdf;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diesgut.medical.app.dao.IDoctorDao;
import com.diesgut.medical.app.document.pdf.AbstractOnlyPdfView;
import com.diesgut.medical.app.document.pdf.UEventoPaginaPdf;
import com.diesgut.medical.model.Doctor;
import com.diesgut.medical.model.Speciality;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pe.albatross.zelpers.pdf.document.PdfDocumentGenerator;

@Component
public class MedicosPorEspecialidadPDF extends AbstractOnlyPdfView {

	@Autowired
	IDoctorDao iDoctorDao;

	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// IE workaround: write into byte array first.
		ByteArrayOutputStream baos = createTemporaryOutputStream();

		// Apply preferences and build metadata.
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		prepareWriter(model, writer, request);
		buildPdfMetadata(model, document, request);

		// Build PDF document.
		writer.setInitialLeading(16);

		UEventoPaginaPdf eventoPagina = new UEventoPaginaPdf();
		eventoPagina.setTitulo1("Médicos por Especialidad");
		document.setMargins(36, 36, 60, 36);
		this.generarPlantillaAgrariaPdf(document, writer, eventoPagina);

		document.open();
		buildPdfDocument(model, document, writer, request, response);
		document.close();

		writeToResponse(response, baos);
	}

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		PdfDocumentGenerator uDocumentoPdf = new PdfDocumentGenerator();
		float[] columnWidths = new float[] { 10f, 40f, 30f, 10f, 10f };

		PdfPTable table = new PdfPTable(columnWidths);
		table.setHeaderRows(1);
		table.getDefaultCell().setBorder(PdfPCell.RECTANGLE);
		table.setWidthPercentage(100);

		uDocumentoPdf.addTitleCellTable("ID", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("NOMBRES", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("ESPECIALIDAD", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("DNI", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("CMP", table, 1, Element.ALIGN_CENTER);

		Long specialityId = Long.parseLong(model.get("idSpeciality").toString());
		List<Doctor> doctorsBySpeciality = iDoctorDao.allBySpeciality(new Speciality(specialityId));
		for (Doctor doctor : doctorsBySpeciality) {
			uDocumentoPdf.addBodyCellTable(doctor.getId().toString(), table, 1, Element.ALIGN_LEFT,
					PdfDocumentGenerator.FUENTE_9);
			uDocumentoPdf.addBodyCellTable(doctor.getFullName(), table, 1, Element.ALIGN_LEFT,
					PdfDocumentGenerator.FUENTE_9);
			uDocumentoPdf.addBodyCellTable(doctor.getSpeciality().getName(), table, 1, Element.ALIGN_LEFT,
					PdfDocumentGenerator.FUENTE_9);
			uDocumentoPdf.addBodyCellTable(doctor.getDni(), table, 1, Element.ALIGN_LEFT,
					PdfDocumentGenerator.FUENTE_9);
			uDocumentoPdf.addBodyCellTable(doctor.getCmp(), table, 1, Element.ALIGN_LEFT,
					PdfDocumentGenerator.FUENTE_9);
		}

		document.add(table);

		String filename = "doctor-especialidad";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".pdf\"");
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	}

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		document.addAuthor("MedicalApp");
		document.addCreationDate();
		document.addCreator("MedicalApp");
		document.addTitle("Médicos por Especialidad");
		document.addSubject("");
		document.setPageSize(PageSize.A4);
	}

	public Document generarPlantillaAgrariaPdf(Document documentoPDF, PdfWriter escritor, UEventoPaginaPdf eventoPagina)
			throws Exception {
		Rectangle rct = new Rectangle(36, 54, 559, 788);
		escritor.setBoxSize("art", rct);
		escritor.setPageEvent(eventoPagina);
		return documentoPDF;
	}

}
