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
import com.cristianRuizBlog.aplicacion.entity.Historial;

public class HistorialExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<Historial> listaHistorial;

	public HistorialExporterExcel(List<Historial> listaHistorial) {
		super();
		this.listaHistorial = listaHistorial;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Historial");
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
		celda.setCellValue("edad");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Peso(kg)");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Altura(cm)");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Antecedentes Medico");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(8);
		celda.setCellValue("Causa");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(9);
		celda.setCellValue("Medicamentos Actuales");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(10);
		celda.setCellValue("Examen");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(11);
		celda.setCellValue("Diagnostico");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(12);
		celda.setCellValue("Tratamiento");
		celda.setCellStyle(estilo);
	}
	
	private void escribirDatos() {
		int numeroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for(Historial historial : listaHistorial) {
			Row fila = hoja.createRow(numeroFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(historial.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(historial.getDocumento());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(historial.getNombre());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(historial.getApellido());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(historial.getEdad());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			
			celda = fila.createCell(5);
			celda.setCellValue(historial.getPeso());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(historial.getAltura());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(7);
			celda.setCellValue(historial.getAntecedentes());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(8);
			celda.setCellValue(historial.getReaccion());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(9);
			celda.setCellValue(historial.getMedicamentos());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(10);
			celda.setCellValue(historial.getExamen());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(11);
			celda.setCellValue(historial.getDiagnostico());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(12);
			celda.setCellValue(historial.getTratamiento());
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
