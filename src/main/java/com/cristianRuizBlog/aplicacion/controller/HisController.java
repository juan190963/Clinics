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
import com.cristianRuizBlog.aplicacion.entity.Historial;
import com.cristianRuizBlog.aplicacion.entity.Venta;
import com.cristianRuizBlog.aplicacion.repository.AlergiasRepository;
import com.cristianRuizBlog.aplicacion.repository.GeneroRepository;
import com.cristianRuizBlog.aplicacion.repository.VentaRepository;
import com.cristianRuizBlog.aplicacion.service.HisService;
import com.cristianRuizBlog.aplicacion.util.reportes.CitaExporterPDF;
import com.cristianRuizBlog.aplicacion.util.reportes.HistorialExporterExcel;
import com.cristianRuizBlog.aplicacion.util.reportes.HistorialExporterPDF;
import com.cristianRuizBlog.aplicacion.util.reportes.VentaExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class HisController {

	@Autowired
	HisService hisService;
	
	@Autowired
	VentaRepository ventaRepository;
	
	@Autowired
	GeneroRepository generoRepository;
	
	@Autowired
	AlergiasRepository alergiasRepository;
	
	@GetMapping("/hisForm")
	public String hisForm(Model model) {
		model.addAttribute("hisForm", new Historial());
		model.addAttribute("hisList", hisService.getAllHis());
		model.addAttribute("venta", ventaRepository.findAll());
		model.addAttribute("genero", generoRepository.findAll());
		model.addAttribute("alergias", alergiasRepository.findAll());
		model.addAttribute("listTab","active");
		return"his-form/his-view";
	}
	@PostMapping("/hisForm")
	public String createHis(@Valid @ModelAttribute("hisForm")Historial historial, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("hisForm", historial);
			model.addAttribute("formTab","active");
		}else {
			try {
				hisService.createHis(historial);
				model.addAttribute("hisForm", new Historial());
				model.addAttribute("listTab","active");
			} catch (Exception h) {
				model.addAttribute("errorM",h.getMessage());
				model.addAttribute("hisForm", historial);
				model.addAttribute("hisList", hisService.getAllHis());
				model.addAttribute("venta", ventaRepository.findAll());
				model.addAttribute("genero", generoRepository.findAll());
				model.addAttribute("alergias", alergiasRepository.findAll());
				model.addAttribute("formTab","active");
			}
		}
		model.addAttribute("hisList", hisService.getAllHis());
		model.addAttribute("venta", ventaRepository.findAll());
		model.addAttribute("genero", generoRepository.findAll());
		model.addAttribute("alergias", alergiasRepository.findAll());
		return"his-form/his-view";
	}
	
	@GetMapping("/editHis/{id}")
	public String getEditHisForm(Model model, @PathVariable(name="id")Long id) throws Exception{
		Historial historialToEdit = hisService.getHistorialById(id);
		model.addAttribute("hisForm", historialToEdit);
		model.addAttribute("hisList", hisService.getAllHis());
		model.addAttribute("venta", ventaRepository.findAll());
		model.addAttribute("genero", generoRepository.findAll());
		model.addAttribute("alergias", alergiasRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		
		return"his-form/his-view";
	}
	
	@PostMapping("/editHis")
	public String postEditHisForm(@Valid @ModelAttribute("hisForm")Historial historial, BindingResult result, ModelMap model) {

		if (result.hasErrors()) {
			model.addAttribute("hisForm", historial);
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				hisService.updateHis(historial);
				model.addAttribute("hisForm", new Historial());
				model.addAttribute("listTab","active");
			} catch (Exception h) {
				model.addAttribute("errorM",h.getMessage());
				model.addAttribute("hisForm", historial);
				model.addAttribute("hisList", hisService.getAllHis());
				model.addAttribute("venta", ventaRepository.findAll());
				model.addAttribute("genero", generoRepository.findAll());
				model.addAttribute("alergias", alergiasRepository.findAll());
				model.addAttribute("formTab","active");
				model.addAttribute("editMode","true");
			}
		}
		model.addAttribute("hisList", hisService.getAllHis());
		model.addAttribute("venta", ventaRepository.findAll());
		model.addAttribute("genero", generoRepository.findAll());
		model.addAttribute("alergias", alergiasRepository.findAll());
		return"his-form/his-view";
	
		}
	
	@GetMapping("/editHis/cancel")
	public String cancelEditHis(ModelMap model) {
		return "redirect:/hisForm";
	}
	
	@GetMapping("/deleteHis/{id}")
	public String deleteHis( @PathVariable("id")Long id) throws Exception {
		hisService.deleteHis(id);
		return "redirect:/hisForm";
	}
	 @GetMapping("/verH/{id}")
	 public String verDetallesH(@PathVariable("id")Long id,Map<String,Object> modelo,RedirectAttributes flash) throws Exception {
		 Historial historial =  hisService.getHistorialById(id);

		 if(historial == null) {
			 flash.addFlashAttribute("error","El historial no existe");
			 return "redirect:/hisForm";
		 }
		modelo.put("his", historial);
		modelo.put("titulo", "Detalles del historial " + historial.getNombre() );
		return "his-form/detalles";
		 
	 }
	 
	 @GetMapping("/exportarPDFH")
	 public void exportarListadoV(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/pdf");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Historial" + fechaActual + ".pdf";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<Historial> historials = hisService.listar();
		 
		 HistorialExporterPDF exporter = new HistorialExporterPDF(historials);
		 
		 exporter.exportar(response);
		 
		 
	 }
	 
	 @GetMapping("/exportarExcelH")
	 public void exportarListadoExcel(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/octet-stream");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Historial" + fechaActual + ".xlsx";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<Historial> historials = hisService.listar();
		 
		 HistorialExporterExcel exporter = new HistorialExporterExcel(historials);
		 
		 exporter.exportar(response);
		 
		 
	 }
}