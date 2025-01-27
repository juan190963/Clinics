package com.cristianRuizBlog.aplicacion.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cristianRuizBlog.aplicacion.Exception.CustomeFieldValidationException;
import com.cristianRuizBlog.aplicacion.Exception.UsernameOrIdNotFound;
import com.cristianRuizBlog.aplicacion.dto.ChangePasswordForm;
import com.cristianRuizBlog.aplicacion.entity.Cita;
import com.cristianRuizBlog.aplicacion.entity.Role;
import com.cristianRuizBlog.aplicacion.entity.User;
import com.cristianRuizBlog.aplicacion.repository.RoleRepository;
import com.cristianRuizBlog.aplicacion.service.EmailService;
import com.cristianRuizBlog.aplicacion.service.UserService;
import com.cristianRuizBlog.aplicacion.util.reportes.CitaExporterPDF;
import com.cristianRuizBlog.aplicacion.util.reportes.UserExporterExcel;
import com.cristianRuizBlog.aplicacion.util.reportes.UserExporterPDF;
import com.lowagie.text.DocumentException;

@Controller
public class UserController {

	private final String TAB_FORM = "formTab";
	private final String TAB_LIST = "listTab";
	

	
	@GetMapping("/solicitud")
	public String solicitud() {
		return "Solicitud";
	}
	

	
    @GetMapping("/loading")
	public String loading() {
		return "loading";
	}
	
	@GetMapping("/exito")
	public String exito() {
		return "Exito";
	}
	
	@GetMapping("/inicioU")
	public String inicioU() {
		return "user-form/inicio";
	}

	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@GetMapping({"/","/rn"})
	public String rn() {
		return "rn";
	}
	
	@GetMapping({"/login"})
	public String index() {
		return "index";
	}
	
	
	
	@GetMapping("/signup")
	public String signup(Model model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		
		model.addAttribute("signup",true);
		model.addAttribute("userForm", new User());
		model.addAttribute("roles",roles);
		return "user-form/user-signup";
	}
	
	@PostMapping("/signup")
	public String signupAction(@Valid @ModelAttribute("userForm")User user, BindingResult result, ModelMap model) {
		Role userRole = roleRepository.findByName("USER");
		List<Role> roles = Arrays.asList(userRole);
		model.addAttribute("userForm", user);
		model.addAttribute("roles",roles);
		model.addAttribute("signup",true);
		
		if(result.hasErrors()) {
			return "user-form/user-signup";
		}else {
			try {
				userService.createUser(user);
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
			}catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
			}
		}
		return index();
	}
	
	private void baseAttributerForUserForm(Model model, User user,String activeTab) {
		model.addAttribute("userForm", user);
		model.addAttribute("userList", userService.getAllUsers());
		model.addAttribute("roles",roleRepository.findAll());
		model.addAttribute(activeTab,"active");
	}
	
	@GetMapping("/userForm")
	public String userForm(Model model) {
		baseAttributerForUserForm(model, new User(), TAB_LIST );
		return "user-form/user-view";
	}
	
	@PostMapping("/userForm")
	public String createUser(@Valid @ModelAttribute("userForm")User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM);
		}else {
			try {
				userService.createUser(user);
				baseAttributerForUserForm(model, new User(), TAB_LIST );
				
			} catch (CustomeFieldValidationException cfve) {
				result.rejectValue(cfve.getFieldName(), null, cfve.getMessage());
				baseAttributerForUserForm(model, user, TAB_FORM );
			}catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				baseAttributerForUserForm(model, user, TAB_FORM );
			}
		}
		return "user-form/user-view";
	}
	
	@GetMapping("/editUser/{id}")
	public String getEditUserForm(Model model, @PathVariable(name ="id")Long id)throws Exception{
		User userToEdit = userService.getUserById(id);

		baseAttributerForUserForm(model, userToEdit, TAB_FORM );
		model.addAttribute("editMode","true");
		model.addAttribute("passwordForm",new ChangePasswordForm(id));
		
		return "user-form/user-view";
	}
	
	@PostMapping("/editUser")
	public String postEditUserForm(@Valid @ModelAttribute("userForm")User user, BindingResult result, Model model) {
		if(result.hasErrors()) {
			baseAttributerForUserForm(model, user, TAB_FORM );
			model.addAttribute("editMode","true");
			model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
		}else {
			try {
				userService.updateUser(user);
				baseAttributerForUserForm(model, new User(), TAB_LIST );
			} catch (Exception e) {
				model.addAttribute("formErrorMessage",e.getMessage());
				
				baseAttributerForUserForm(model, user, TAB_FORM );
				model.addAttribute("editMode","true");
				model.addAttribute("passwordForm",new ChangePasswordForm(user.getId()));
			}
		}
		return "user-form/user-view";
		
	}
	
	@GetMapping("/userForm/cancel")
	public String cancelEditUser(ModelMap model) {
		return "redirect:/userForm";
	}
	
	@GetMapping("/deleteUser/{id}")
	public String deleteUser(Model model, @PathVariable(name="id")Long id) {
		try {
			userService.deleteUser(id);
		} 
		catch (UsernameOrIdNotFound uoin) {
			model.addAttribute("listErrorMessage",uoin.getMessage());
		}
		return userForm(model);
	}
	
	@PostMapping("/editUser/changePassword")
	public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
		try {
			if( errors.hasErrors()) {
				String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

				throw new Exception(result);
			}
			userService.changePassword(form);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok("Success");
	}
	
	@GetMapping("/exportarPDFU")
	 public void exportarListadoU(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/pdf");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Usuarios" + fechaActual + ".pdf";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<User> user = userService.listar();
		 
		 UserExporterPDF exporter = new UserExporterPDF(user);
		 
		 exporter.exportar(response);
		 
		 
	 }
	
	@GetMapping("/exportarExcelU")
	 public void exportarListadoExcel(HttpServletResponse response) throws DocumentException, IOException {
		 response.setContentType("application/octet-stream");
		 
		 DateFormat dateFormatter = new SimpleDateFormat("yyy-MM-dd");
		 String fechaActual = dateFormatter.format(new Date());
		 
		 String cabecera = "Content-Disposition";
		 String valor = "attachment; filename=Usuarios" + fechaActual + ".xlsx";
		 
		 response.setHeader(cabecera, valor);
		 
		 List<User> user = userService.listar();
		 
		 UserExporterExcel exporter = new UserExporterExcel(user);
		 
		 exporter.exportar(response);
		 
		 
	 }
}


