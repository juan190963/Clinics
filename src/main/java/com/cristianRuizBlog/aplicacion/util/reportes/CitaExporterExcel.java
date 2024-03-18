package com.cristianRuizBlog.aplicacion.util.reportes;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.User;
import com.lowagie.text.pdf.PdfPTable;

public class CitaExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Cita> listaCita;

	public CitaExporterExcel(List<Cita> listaCita) {
		super();
		this.listaCita = listaCita;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Citas");
		
	}
	
	private void escribirCabecera() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
	
		Cell celda = fila.createCell(0);
		celda.setCellValue("ID");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Documento");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Apellido");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Fecha");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Hora");
		celda.setCellStyle(estilo);
		
		
	}
	
	private void escribirDatos() {
		int numeroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for(Cita cita : listaCita) {
			Row fila = hoja.createRow(numeroFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(cita.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(cita.getDocumento());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(cita.getNombre());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(cita.getApellido());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(cita.getFecha());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(cita.getHora());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			
		}
	}
	
	public void exportar(HttpServletResponse response) throws IOException {
		escribirCabecera();
		escribirDatos();
		
		ServletOutputStream outputStream = response.getOutputStream();
		libro.write(outputStream);
		libro.close();
		outputStream.close();
	}
}
