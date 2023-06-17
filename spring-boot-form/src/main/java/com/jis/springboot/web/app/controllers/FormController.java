package com.jis.springboot.web.app.controllers;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.jis.springboot.web.app.PaisEditor;
import com.jis.springboot.web.app.RoleEditor;
import com.jis.springboot.web.app.TextoMayusculaEditor;
import com.jis.springboot.web.app.models.Pais;
import com.jis.springboot.web.app.models.Role;
import com.jis.springboot.web.app.models.Usuario;
import com.jis.springboot.web.app.services.PaisService;
import com.jis.springboot.web.app.services.RoleService;
import com.jis.springboot.web.app.validator.UsuarioValidator;

import jakarta.validation.Valid;

@Controller
@SessionAttributes("usuario")
public class FormController {

	@Autowired
	private RoleEditor roleEditor;
	
	@Autowired
	private PaisService paisService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PaisEditor paisEditor;
	
	@InitBinder
	private void iniciarBinder(WebDataBinder binder) {
		binder.addValidators(usuarioValidator);
		binder.registerCustomEditor(String.class, "username",  new TextoMayusculaEditor());
		//Se encarga de transformar el id del pais a un objeto de tipo pais
		binder.registerCustomEditor(Pais.class,"pais", paisEditor);
		binder.registerCustomEditor(Role.class,"roles", roleEditor);
	}
	
	@ModelAttribute("rolesObjects")
	public List<Role> listarRoles() {
		return roleService.listarRoles();
	}
	
	
	@ModelAttribute("rolesList")
	public List<String> setRoles() {
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_ADMIN");
		roles.add("ROLE_MODERATOR");
		roles.add("ROLE_USER");
		return roles;
	}
	
	@ModelAttribute("rolesMap")
	public Map<String,String> setRolesMap() {
		Map<String,String> roles = new HashMap<>();
		roles.put("ROLE_ADMIN", "Administrador");
		roles.put("ROLE_MODERATOR", "Moderador");
		roles.put("ROLE_USER", "Usuario");
		return roles;
	}
	
	
	
	@ModelAttribute("generos")
	public List<String> setGeneros() {
		return Arrays.asList("Hombre","Mujer");
	}
	
	
	
	@ModelAttribute("paises")
	public List<Pais> setPaises() {
		return paisService.listarPaises();
	}
	
	@ModelAttribute("paisesMap")
	public Map<String,String> setPaisesMap() {
		Map<String,String> paises = new HashMap<>();
		paises.put("AR", "Argentina");
		paises.put("CL", "Chile");
		paises.put("CO", "Colombia");
		paises.put("PE", "Perú");
		paises.put("BOL", "Bolivia");
		return paises;
	}
	
	@GetMapping("/form")
	public String mostrarForm(Model model) {
		Usuario usuario = new Usuario();
		usuario.setId("38.415.607-M");
		usuario.setEmail("jis1994222@gmail.com");
		usuario.setUsername("Joaquin");
		usuario.setPassword("j1234");
		usuario.setCuenta(8);
		usuario.setIsHabilitado(true);
		usuario.setFechaNacimiento(new Date());
		usuario.setPais(paisService.obtenerPorId(2));
		usuario.setRoles(Arrays.asList(roleService.buscarPorId(1),roleService.buscarPorId(2)));
		usuario.setGenero("Hombre");
		model.addAttribute("usuario", usuario);
		return "form";
	}
	
	@PostMapping("/form")
	public String procesarForm(@Valid Usuario usuario, BindingResult validationResult,Model model) {
		//usuarioValidator.validate(usuario, validationResult);
		if(validationResult.hasErrors()) {
			/*
			Map<String,String> errores = new HashMap<>();
			validationResult.getFieldErrors().forEach(error -> {
				errores.put(error.getField(), error.getField().concat(" ").concat(error.getDefaultMessage()));
			});
			model.addAttribute("errores", errores);
			*/
			return "form";
		}
		
		return "redirect:/procesado";
	}
	
	
	@GetMapping("/procesado")
	public String mostrarResultado(@SessionAttribute(required = false, name = "usuario") Usuario usuario,SessionStatus sessionStatus) {
		if(usuario == null) return "redirect:/form";
		
		sessionStatus.setComplete();
		return "resultado";
	}
	
	
}
