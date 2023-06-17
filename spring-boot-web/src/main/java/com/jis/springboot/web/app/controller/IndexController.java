package com.jis.springboot.web.app.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jis.springboot.web.app.models.Usuario;



@Controller
@RequestMapping("/app")
public class IndexController {
	
	@Value("${indexcontroller.index.titulo}")
	private String tituloIndex;
	
	@Value("${indexcontroller.perfil.titulo}")
	private String tituloPerfil;
	
	@Value("${indexcontroller.listar.titulo}")
	private String tituloListar;

	@GetMapping({"/index","/","","/home"})
	public String index(Model model) {
		model.addAttribute("titulo", tituloIndex);
		return "index";
	}
	
	
	@RequestMapping("/perfil")
	public String perfil(Model model) {
		Usuario usuario = new Usuario();
		usuario.setNombre("Joaquin");
		usuario.setApellido("Sanchez");
		usuario.setEmail("jis199422@gmail.com");
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", tituloPerfil.concat(usuario.getNombre()));
		
		return "perfil";
	}
	
	
	@RequestMapping("/listar")
	public String listar(Model model) {
		model.addAttribute("titulo", tituloListar);
		return "listar";
	}
	
	@ModelAttribute("usuarios")
	public List<Usuario> getUsuario(){
		List<Usuario> usuarios = Arrays.asList(new Usuario("Joaquin","Sanchez","jis199422@gmail.com"),
				   new Usuario("Juan","Muñoz","jmuños@gmail.com"),
				   new Usuario("Erika","Mejia","erikamejia@gmail.com"));
		return usuarios;
	}
}
