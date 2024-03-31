package com.cristianRuizBlog.aplicacion.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.repository.ServicioRepository;
import com.cristianRuizBlog.aplicacion.service.CitaService;
import com.cristianRuizBlog.aplicacion.util.reportes.CitaExporterExcel;
import com.cristianRuizBlog.aplicacion.util.reportes.CitaExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class CitaController {

	@Autowired
	CitaService citaService;
	
	@Autowired
	ServicioRepository servicioRepository;
	
	@GetMapping("/inicioC")
	public String inicioC() {
		return "cita-form/inicio";
	}
	
	@GetMapping("/citaForm")
	public String citaForm(Model model) {
		model.addAttribute("citaForm", new Cita());
		model.addAttribute("citasList", citaService.getAllCitas());
		model.addAttribute("servicios", servicioRepository.findAll());
		model.addAttribute("listTab","active");
		return"cita-form/cita-view";
	}
	
	@PostMapping("/citaForm")
	public String createCita(@Valid @ModelAttribute("citaForm")Cita cita, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("citaForm", cita);
			model.addAttribute("formTab","active");
		}else {
			try {
				citaService.createCita(cita);
				model.addAttribute("citaForm", new Cita());
				model.addAttribute("listTab","active");
			} catch (Exception c) {
				model.addAttribute("errorMenssage", c.getMessage());
				model.addAttribute("citaForm",cita);
				model.addAttribute("citasList", citaService.getAllCitas());
				model.addAttribute("servicios", servicioRepository.findAll());
				model.addAttribute("formTab","active");
			}
		}
			model.addAttribute("citasList", citaService.getAllCitas());
			model.addAttribute("servicios", servicioRepository.findAll());
			return"cita-form/cita-view";
	}
	@GetMapping("/editCita/{id}")
	public String getEditCitaForm(Model model, @PathVariable(name="id")Long id) throws Exception{
		Cita citaToEdit = citaService.getCitaById(id);
		model.addAttribute("citaForm", citaToEdit);
		model.addAttribute("citasList", citaService.getAllCitas());
		model.addAttribute("servicios", servicioRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		return"cita-form/cita-view";
	}
	
	@PostMapping("/editCita")
	public String postEditCitaForm(@Valid @ModelAttribute("citaForm")Cita cita, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("citaForm", cita);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				citaService.updateCita(cita);
				model.addAttribute("citaForm", new Cita());
				model.addAttribute("listTab","active");
			} catch (Exception c) {
				model.addAttribute("errorMenssage", c.getMessage());
				model.addAttribute("citaForm",cita);
				model.addAttribute("citasList", citaService.getAllCitas());
				model.addAttribute("servicios", servicioRepository.findAll());
				model.addAttribute("formTab","active");
				model.addAttribute("editMode","true");
			}
		}
			model.addAttribute("citasList", citaService.getAllCitas());
			model.addAttribute("servicios", servicioRepository.findAll());
			return"cita-form/cita-view";
}
	@GetMapping("/editCita/cancel")
	public String cancelEditCita(ModelMap model) {
		return "redirect:/citaForm";
	}
	
	
	 
	 @GetMapping("/deleteCita/{id}")
		public String deleteCita(@PathVariable("id")Long id) throws Exception {
			citaService.deleteCita(id);
			return "redirect:/citaForm";
		}
	 
	 @GetMapping("/ver/{id}")
	 public String verDetalles(@PathVariable("id")Long id,Map<String,Object> modelo,RedirectAttributes flash) throws Exception {
		 Cita cita = citaService.getCitaById(id);
		 if(cita == null) {
			 flash.addFlashAttribute("error","La cita no existe");
			 return "redirect:/citaForm";
		 }
		modelo.put("cita", cita);
		modelo.put("titulo", "Detalles de la cita "  + cita.getNombre());
		return "cita-form/detalles";
	 }
	 
	 @GetMapping("/exportarPDF")
	 public void exportarListadoC(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/pdf");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Cita" + fechaActual + ".pdf";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<Cita> cita = citaService.listar();
		 
		 CitaExporterPDF exporter = new CitaExporterPDF(cita);
		 
		 exporter.exportar(response);
		 
		 
	 }
	 
	 @GetMapping("/exportarExcelC")
	 public void exportarListadoExcel(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/octet-stream");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Cita" + fechaActual + ".xlsx";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<Cita> cita = citaService.listar();
		 
		 CitaExporterExcel exporter = new CitaExporterExcel
				 (cita);
		 
		 exporter.exportar(response);
		 
		 
	 }
}
