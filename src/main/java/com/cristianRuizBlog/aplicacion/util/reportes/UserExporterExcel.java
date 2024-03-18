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

import com.cristianRuizBlog.aplicacion.entity.User;

public class UserExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	
	private List<User> listaUser;

	public UserExporterExcel(List<User> listaUser) {
		super();
		this.listaUser = listaUser;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Usuarios");
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
		celda.setCellValue("Nombre");
		celda.setCellStyle(estilo);


		celda = fila.createCell(2);
		celda.setCellValue("Apellido");
		celda.setCellStyle(estilo);
		
		 celda = fila.createCell(3);
		celda.setCellValue("UserName");
		celda.setCellStyle(estilo);
		
		 celda = fila.createCell(4);
		celda.setCellValue("E-mail");
		celda.setCellStyle(estilo);
		
		 
		
	}
	private void escribirDatos() {
		int numeroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for(User user : listaUser) {
			Row fila = hoja.createRow(numeroFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(user.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(user.getFirstName());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(user.getLastName());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(user.getUsername());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(user.getEmail());
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
