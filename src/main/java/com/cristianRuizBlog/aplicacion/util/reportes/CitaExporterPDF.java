package com.cristianRuizBlog.aplicacion.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class CitaExporterPDF {

	private List<Cita> listaCita;

	public CitaExporterPDF(List<Cita> listaCita) {
		super();
		this.listaCita = listaCita;
	}
	
	private void escribirCabecera(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("ID",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Documento",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Nombre",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Apellido",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Fecha",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Hora",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Servicio",fuente));
		tabla.addCell(celda);
	}
	
	private void escribirDatos(PdfPTable tabla) {
		for(Cita cita : listaCita) {
			tabla.addCell(String.valueOf(cita.getId()));
			tabla.addCell(String.valueOf(cita.getDocumento()));
			tabla.addCell(String.valueOf(cita.getNombre()));
			tabla.addCell(String.valueOf(cita.getApellido()));
			tabla.addCell(String.valueOf(cita.getFecha()));
			tabla.addCell(String.valueOf(cita.getHora()));
			tabla.addCell(String.valueOf(cita.getServicios()));
		}
	}
	
	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Citas Registradas",fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(7);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {1f,3f,3f,3f,2.3f,2.3f,6f,});
		tabla.setWidthPercentage(110);
		
		escribirCabecera(tabla);
		escribirDatos(tabla);
		
		documento.add(tabla);
		documento.close();
	}
}
