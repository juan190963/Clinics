package com.cristianRuizBlog.aplicacion.util.reportes;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.User;
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

public class UserExporterPDF {

	private List<User> listaUser;

	public UserExporterPDF(List<User> listaUser) {
		super();
		this.listaUser = listaUser;
	}
	
	private void escribirCabecera(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		
		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		celda.setPhrase(new Phrase("ID",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Nombre",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Apellido",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("UserName",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("E-mail",fuente));
		tabla.addCell(celda);
		
		celda.setPhrase(new Phrase("Rol",fuente));
		tabla.addCell(celda);
	}
	
	private void escribirDatos(PdfPTable tabla) {
		for(User user : listaUser) {
			tabla.addCell(String.valueOf(user.getId()));
			tabla.addCell(String.valueOf(user.getFirstName()));
			tabla.addCell(String.valueOf(user.getLastName()));
			tabla.addCell(String.valueOf(user.getUsername()));
			tabla.addCell(String.valueOf(user.getEmail()));
			tabla.addCell(String.valueOf(user.getRoles()));
		}
	}
	public void exportar(HttpServletResponse response) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());
		
		documento.open();
		
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);
		
		Paragraph titulo = new Paragraph("Lista de Usuarios Registrados",fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);
		
		PdfPTable tabla = new PdfPTable(6);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] {1f,3f,3f,4f,7f,7f,});
		tabla.setWidthPercentage(110);
		
		escribirCabecera(tabla);
		escribirDatos(tabla);
		
		documento.add(tabla);
		documento.close();
	}
}
