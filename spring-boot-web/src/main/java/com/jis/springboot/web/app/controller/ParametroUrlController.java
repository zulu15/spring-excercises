package com.jis.springboot.web.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/variables")
public class ParametroUrlController {
	
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("titulo", "Enviar parametros por url");
		return "/variables/index";
	}

	@GetMapping("/string/{param}")
	public String recibirParamUrl(@PathVariable(name="param") String texto, Model model) {
		model.addAttribute("titulo", "Recibir parámetros por url");
		model.addAttribute("path", "La variable recibida es "+texto);
		return "/variables/ver";
	}
	
	@GetMapping("/string/{nombre}/{edad}")
	public String recibirMultiplesParams(@PathVariable String nombre,@PathVariable Integer edad, Model model) {
		model.addAttribute("path", "Params recibidos por url; nombre: ".concat(nombre).concat(" y edad: ").concat(String.valueOf(edad)));
		return "/variables/ver";
	}
}
