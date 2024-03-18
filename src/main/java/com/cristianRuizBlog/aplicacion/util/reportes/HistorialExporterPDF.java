package com.cristianRuizBlog.aplicacion.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.Historial;
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

public class HistorialExporterPDF {

	public List<Historial> listaHis;

	
	
	
	public HistorialExporterPDF(List<Historial> listaHis) {
		super();
		this.listaHis = listaHis;
	}

	private void escribirCabecera(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("ID",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Venta",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Documento",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Nombre",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Apellido",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("edad",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Genero",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Peso(kg)",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Altura(cm)",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Antecedentes Medico",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Alergias",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Causa",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Medicamentos Actuales",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Examen",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Diagnostico",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Tratamiento",fuente));
		tabla.addCell(celda);
		
	}
	
	private void escribirDatos(PdfPTable tabla) {
		for(Historial historial : listaHis) {
			tabla.addCell(String.valueOf(historial.getId()));
			tabla.addCell(String.valueOf(historial.getVenta()));
			tabla.addCell(String.valueOf(historial.getDocumento()));
			tabla.addCell(String.valueOf(historial.getNombre()));
			tabla.addCell(String.valueOf(historial.getApellido()));
			tabla.addCell(String.valueOf(historial.getGenero()));
			tabla.addCell(String.valueOf(historial.getPeso()));
			tabla.addCell(String.valueOf(historial.getAltura()));
			tabla.addCell(String.valueOf(historial.getAntecedentes()));
			tabla.addCell(String.valueOf(historial.getAlergias()));
			tabla.addCell(String.valueOf(historial.getReaccion()));
			tabla.addCell(String.valueOf(historial.getMedicamentos()));
			tabla.addCell(String.valueOf(historial.getExamen()));
			tabla.addCell(String.valueOf(historial.getDiagnostico()));
			tabla.addCell(String.valueOf(historial.getTratamiento()));
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
		
		PdfPTable tabla = new PdfPTable(15);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {1f,3f,3f,3f,2.3f,2.3f,6f,3f,3f,3f,2.3f,2.3f,6f,3f,3f,});
		tabla.setWidthPercentage(110);
		
		escribirCabecera(tabla);
		escribirDatos(tabla);
		
		documento.add(tabla);
		documento.close();
	}
}
