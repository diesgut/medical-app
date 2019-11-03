package com.diesgut.medical.app.document.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class UEventoPaginaPdf extends PdfPageEventHelper {

	private String titulo1;

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		PdfPTable tableHeader = new PdfPTable(new float[] { 100f });
		try {
			tableHeader.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);

			Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
			PdfPCell cell = new PdfPCell(new Phrase(titulo1, font));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			cell.setBorder(PdfPCell.NO_BORDER);
			tableHeader.addCell(cell);

			PdfContentByte cb = writer.getDirectContent();
			ColumnText ct = new ColumnText(cb);
			ct.addElement(tableHeader);
			ct.setSimpleColumn(0, 0, 559, 806); // Position goes here
			ct.go();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEndPage(PdfWriter writer, Document documento) {
		PdfContentByte cb = writer.getDirectContent();
		Rectangle rectangulo = writer.getBoxSize("art");

		Font fuentePiePagina = new Font();
		fuentePiePagina.setSize(7f);
		fuentePiePagina.setFamily("Arial");
		fuentePiePagina.setColor(BaseColor.BLACK);
		fuentePiePagina.setStyle(Font.BOLD);

		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
				new Phrase("" + String.valueOf(writer.getPageNumber()), fuentePiePagina),
				(rectangulo.getLeft() + rectangulo.getRight()) / 2, rectangulo.getBottom() - 30, 0);
	}

	public String getTitulo1() {
		return titulo1;
	}

	public void setTitulo1(String titulo1) {
		this.titulo1 = titulo1;
	}

}
