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
import com.cristianRuizBlog.aplicacion.entity.Venta;
import com.cristianRuizBlog.aplicacion.repository.CitaRepository;
import com.cristianRuizBlog.aplicacion.repository.CostoRepository;
import com.cristianRuizBlog.aplicacion.repository.DoctoresRepository;
import com.cristianRuizBlog.aplicacion.service.VentaService;
import com.cristianRuizBlog.aplicacion.util.reportes.CitaExporterPDF;
import com.cristianRuizBlog.aplicacion.util.reportes.VentaExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class VentaController {

	@Autowired
	VentaService ventaService;
	
	@Autowired
	CitaRepository citaRepository;
	
	@Autowired
	DoctoresRepository doctoresRepository;
	
	@Autowired
	CostoRepository costoRepository;
	
	@GetMapping("/ventaForm")
	public String ventaForm(Model model) {
		model.addAttribute("ventaForm", new Venta() );
		model.addAttribute("ventasList", ventaService.getAllVentas());
		model.addAttribute("cita", citaRepository.findAll());
		model.addAttribute("doctores", doctoresRepository.findAll());
		model.addAttribute("costo", costoRepository.findAll());
		model.addAttribute("listTab","active");
		return "venta-form/venta-view";
	}
	
	@PostMapping("/ventaForm")
	public String createVenta(@Valid @ModelAttribute("ventaForm")Venta venta,BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			model.addAttribute("ventaForm", venta );
			model.addAttribute("formTab","active");
		}else {
			try {
				ventaService.createVenta(venta);
				model.addAttribute("ventaForm", new Venta() );
				model.addAttribute("listTab","active");
				
			} catch (Exception v) {
				model.addAttribute("errorMenssage", v.getMessage());
				model.addAttribute("ventaForm", venta );
				model.addAttribute("ventasList", ventaService.getAllVentas());
				model.addAttribute("cita", citaRepository.findAll());
				model.addAttribute("doctores", doctoresRepository.findAll());
				model.addAttribute("costo", costoRepository.findAll());
				model.addAttribute("formTab","active");
			}
			
		}
		model.addAttribute("ventasList", ventaService.getAllVentas());
		model.addAttribute("cita", citaRepository.findAll());
		model.addAttribute("doctores", doctoresRepository.findAll());
		model.addAttribute("costo", costoRepository.findAll());
		return"venta-form/venta-view";

	}
	
	@GetMapping("/editVenta/{id}")
	public String getEditVentaForm(Model model, @PathVariable(name="id")Long id) throws Exception{
		Venta ventaToEdit = ventaService.getVentaById(id);
		model.addAttribute("ventaForm", ventaToEdit);
		model.addAttribute("ventasList", ventaService.getAllVentas());
		model.addAttribute("cita", citaRepository.findAll());
		model.addAttribute("doctores", doctoresRepository.findAll());
		model.addAttribute("costo", costoRepository.findAll());
		model.addAttribute("formTab","active");
		model.addAttribute("editMode","true");
		return"venta-form/venta-view";
	}
	
	@PostMapping("/editVenta")
	public String postEditVenta(@Valid @ModelAttribute("ventaForm")Venta venta,BindingResult result, ModelMap model) {
	
		if(result.hasErrors()) {
			model.addAttribute("ventaForm", venta );
			model.addAttribute("formTab","active");
			model.addAttribute("editMode","true");
		}else {
			try {
				ventaService.createVenta(venta);
				model.addAttribute("ventaForm", new Venta() );
				model.addAttribute("listTab","active");
				
			} catch (Exception v) {
				model.addAttribute("errorMenssage", v.getMessage());
				model.addAttribute("ventaForm", venta );
				model.addAttribute("ventasList", ventaService.getAllVentas());
				model.addAttribute("cita", citaRepository.findAll());
				model.addAttribute("doctores", doctoresRepository.findAll());
				model.addAttribute("costo", costoRepository.findAll());
				model.addAttribute("formTab","active");
				model.addAttribute("editMode","true");
			}
			
		}
		model.addAttribute("ventasList", ventaService.getAllVentas());
		model.addAttribute("cita", citaRepository.findAll());
		model.addAttribute("doctores", doctoresRepository.findAll());
		model.addAttribute("costo", costoRepository.findAll());
		return"venta-form/venta-view";

}
	@GetMapping("/ventaForm/cancel")
	public String cancelEditVenta(ModelMap model) {
		return "redirect:/ventaForm";
	}
	
	@GetMapping("/deleteVenta/{id}")
	public String deleteVenta(@PathVariable("id")Long id) throws Exception {
		ventaService.deleteVenta(id);
		return "redirect:/ventaForm";
	}
	
	 @GetMapping("/verV/{id}")
	 public String verDetallesV(@PathVariable("id")Long id,Map<String,Object> modelo,RedirectAttributes flash) throws Exception {
		 Venta venta = ventaService.getVentaById(id);
		 if(venta == null) {
			 flash.addFlashAttribute("error","La venta no existe");
			 return "redirect:/ventaForm";
		 }
		modelo.put("venta", venta);
		modelo.put("titulo", "Detalles de la Venta " );
		return "venta-form/detalles";
		 
	 }
	 
	 @GetMapping("/exportarPDFV")
	 public void exportarListadoV(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/pdf");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Ventas" + fechaActual + ".pdf";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<Venta> venta = ventaService.listar();
		 
		 VentaExporterPDF exporter = new VentaExporterPDF(venta);
		 
		 exporter.exportar(response);
	 }
	 
	 
}