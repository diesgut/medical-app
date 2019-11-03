package com.diesgut.medical.app.controller.report.pdf;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.diesgut.medical.app.controller.report.iReportService;
import com.diesgut.medical.app.document.pdf.AbstractOnlyPdfView;
import com.diesgut.medical.app.document.pdf.UEventoPaginaPdf;
import com.diesgut.medical.model.DetailConsultation;
import com.diesgut.medical.model.MedicalConsultation;
import com.diesgut.medical.model.Patient;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import pe.albatross.zelpers.pdf.document.PdfDocumentGenerator;

@Component
public class MedicalHistoryByPatientPDF extends AbstractOnlyPdfView {

	@Autowired
	iReportService iReportService;

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
		eventoPagina.setTitulo1("Historial Médico");
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
		// TODO Auto-generated method stub

		Long idPatient = Long.parseLong(model.get("idPatient").toString());
		Patient patient = iReportService.findPatient(new Patient(idPatient));

		PdfDocumentGenerator uDocumentoPdf = new PdfDocumentGenerator();
		float[] columnWidths = new float[] { 25f, 25f, 25f, 25f };

		PdfPTable table = new PdfPTable(columnWidths);
		// table.setHeaderRows(1);
		table.getDefaultCell().setBorder(PdfPCell.RECTANGLE);
		table.setWidthPercentage(100);

		uDocumentoPdf.addTitleCellTable("Id", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("Paciente", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("DNI", table, 1, Element.ALIGN_CENTER);
		uDocumentoPdf.addTitleCellTable("N. Historial", table, 1, Element.ALIGN_CENTER);
		//
		uDocumentoPdf.addBodyCellTable(patient.getId().toString(), table, 1, Element.ALIGN_LEFT,
				PdfDocumentGenerator.FUENTE_9);
		uDocumentoPdf.addBodyCellTable(patient.getFullName(), table, 1, Element.ALIGN_LEFT,
				PdfDocumentGenerator.FUENTE_9);
		uDocumentoPdf.addBodyCellTable(patient.getDni(), table, 1, Element.ALIGN_LEFT, PdfDocumentGenerator.FUENTE_9);
		uDocumentoPdf.addBodyCellTable(patient.getNumberClinicalHistory(), table, 1, Element.ALIGN_LEFT,
				PdfDocumentGenerator.FUENTE_9);
		document.add(table);
		//
		document.add(uDocumentoPdf.agregarEnter(2));
		//
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		for (MedicalConsultation medicalConsultation : patient.getMedicalsConsultations()) {
			table = new PdfPTable(3);
			// table.setHeaderRows(1);
			table.getDefaultCell().setBorder(PdfPCell.RECTANGLE);
			table.setWidthPercentage(100);

			uDocumentoPdf.addTitleCellTable("Fecha Consulta", table, 1, Element.ALIGN_CENTER);
			uDocumentoPdf.addTitleCellTable("Doctor", table, 1, Element.ALIGN_CENTER);
			uDocumentoPdf.addTitleCellTable("Especialidad", table, 1, Element.ALIGN_CENTER);

			uDocumentoPdf.addBodyCellTable(medicalConsultation.getCreateDate().format(formatter), table, 1,
					Element.ALIGN_LEFT, PdfDocumentGenerator.FUENTE_9);
			uDocumentoPdf.addBodyCellTable(medicalConsultation.getDoctor().getFullName(), table, 1, Element.ALIGN_LEFT,
					PdfDocumentGenerator.FUENTE_9);
			uDocumentoPdf.addBodyCellTable(medicalConsultation.getDoctor().getSpeciality().getName(), table, 1,
					Element.ALIGN_LEFT, PdfDocumentGenerator.FUENTE_9);
			document.add(table);

			table = new PdfPTable(2);
			table.getDefaultCell().setBorder(PdfPCell.RECTANGLE);
			table.setWidthPercentage(100);
			uDocumentoPdf.addTitleCellTable("Diagnostico", table, 1, Element.ALIGN_CENTER);
			uDocumentoPdf.addTitleCellTable("Tratamiento", table, 1, Element.ALIGN_CENTER);
			for (DetailConsultation detail : medicalConsultation.getDetailConsultations()) {
				uDocumentoPdf.addBodyCellTable(detail.getDiagnostic(), table, 1, Element.ALIGN_LEFT,
						PdfDocumentGenerator.FUENTE_9);
				uDocumentoPdf.addBodyCellTable(detail.getTreatment(), table, 1, Element.ALIGN_LEFT,
						PdfDocumentGenerator.FUENTE_9);
			}
			document.add(table);
			document.add(uDocumentoPdf.agregarEnter(2));
		}

		String filename = "historial-medico";
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + ".pdf\"");
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
	}

	@Override
	protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
		document.addAuthor("MedicalApp");
		document.addCreationDate();
		document.addCreator("MedicalApp");
		document.addTitle("Historial Medico");
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
